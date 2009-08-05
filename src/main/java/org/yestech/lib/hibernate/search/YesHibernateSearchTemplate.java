/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

/*
 *
 * Author:  Artie Copeland
 * Last Modified Date: $DateTime: $
 */
package org.yestech.lib.hibernate.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.hibernate.*;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.event.EventSource;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.hibernate3.HibernateAccessor;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * A wrapper for Hibernate Search.  Modeled after HibernateTemplate.
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings({"unchecked"})
public class YesHibernateSearchTemplate extends HibernateAccessor implements YesHibernateSearchOperations {

    private boolean alwaysUseNewSession = false;

    private boolean exposeNativeSession = false;

    public YesHibernateSearchTemplate() {
    }

    public YesHibernateSearchTemplate(SessionFactory sessionFactory) {
        setSessionFactory(sessionFactory);
        afterPropertiesSet();
    }

    /**
     * Set whether to always use a new Hibernate Session for this template.
     * Default is "false"; if activated, all operations on this template will
     * work on a new Hibernate Session even in case of a pre-bound Session
     * (for example, within a transaction or OpenSessionInViewFilter).
     * <p>Within a transaction, a new Hibernate Session used by this template
     * will participate in the transaction through using the same JDBC
     * Connection. In such a scenario, multiple Sessions will participate
     * in the same database transaction.
     * <p>Turn this on for operations that are supposed to always execute
     * independently, without side effects caused by a shared Hibernate Session.
     */
    public void setAlwaysUseNewSession(boolean alwaysUseNewSession) {
        this.alwaysUseNewSession = alwaysUseNewSession;
    }

    /**
     * Return whether to always use a new Hibernate Session for this template.
     */
    public boolean isAlwaysUseNewSession() {
        return this.alwaysUseNewSession;
    }

    /**
     * Set whether to expose the native Hibernate Session to
     * HibernateCallback code.
     * <p>Default is "false": a Session proxy will be returned, suppressing
     * <code>close</code> calls and automatically applying query cache
     * settings and transaction timeouts.
     *
     * @see org.springframework.orm.hibernate3.HibernateCallback
     * @see org.hibernate.Session
     */
    public void setExposeNativeSession(boolean exposeNativeSession) {
        this.exposeNativeSession = exposeNativeSession;
    }

    /**
     * Return whether to expose the native Hibernate Session to
     * HibernateCallback code, or rather a Session proxy.
     */
    public boolean isExposeNativeSession() {
        return this.exposeNativeSession;
    }

    /**
     * Execute the action specified by the given action object within a
     * native {@link org.hibernate.Session}.
     * <p>This execute variant overrides the template-wide
     * {@link #isExposeNativeSession() "exposeNativeSession"} setting.
     *
     * @param action callback object that specifies the Hibernate action
     * @return a result object returned by the action, or <code>null</code>
     * @throws org.springframework.dao.DataAccessException
     *          in case of Hibernate errors
     */
    public Object executeWithNativeSession(HibernateSearchCallback action) {
        return doExecute(action, false, true);
    }

    /**
     * Execute the action specified by the given action object within a Session.
     *
     * @param action               callback object that specifies the Hibernate action
     * @param enforceNewSession    whether to enforce a new Session for this template
     *                             even if there is a pre-bound transactional Session
     * @param enforceNativeSession whether to enforce exposure of the native
     *                             Hibernate Session to callback code
     * @return a result object returned by the action, or <code>null</code>
     * @throws org.springframework.dao.DataAccessException
     *          in case of Hibernate errors
     */
    public Object doExecute(HibernateSearchCallback action, boolean enforceNewSession, boolean enforceNativeSession)
            throws DataAccessException {

        Assert.notNull(action, "Callback object must not be null");

		Session session = (enforceNewSession ?
				SessionFactoryUtils.getNewSession(getSessionFactory(), getEntityInterceptor()) : getSession());
		boolean existingTransaction = (!enforceNewSession &&
				(SessionFactoryUtils.isSessionTransactional(session, getSessionFactory())));
		if (existingTransaction) {
			logger.debug("Found thread-bound Session for HibernateTemplate");
		}

		FlushMode previousFlushMode = null;
		try {
			previousFlushMode = applyFlushMode(session, existingTransaction);
			enableFilters(session);
			Session sessionToExpose =
					(enforceNativeSession || isExposeNativeSession() ? session : createSessionProxy(session));
			Object result = action.doInHibernate(sessionToExpose);
			flushIfNecessary(session, existingTransaction);
			return result;
		}
		catch (HibernateException ex) {
			throw convertHibernateAccessException(ex);
		}
		finally {
			if (existingTransaction) {
				logger.debug("Not closing pre-bound Hibernate Session after HibernateTemplate");
				disableFilters(session);
				if (previousFlushMode != null) {
					session.setFlushMode(previousFlushMode);
				}
			}
			else {
				// Never use deferred close for an explicitly new Session.
				if (isAlwaysUseNewSession()) {
					SessionFactoryUtils.closeSession(session);
				}
				else {
                    if (SessionFactoryUtils.isDeferredCloseActive(getSessionFactory())) {
                        SessionFactoryUtils.initDeferredClose(getSessionFactory());
                    } else {
                        SessionFactoryUtils.closeSession(session);
                    }

                }
			}
		}
    }

    /**
     * Create a close-suppressing proxy for the given Hibernate Session.
     * The proxy also prepares returned Query and Criteria objects.
     * @param session the Hibernate Session to create a proxy for
     * @return the Session proxy
     * @see org.hibernate.Session#close()
     */
    protected Session createSessionProxy(Session session) {
        Class[] sessionIfcs = null;
        Class mainIfc = (session instanceof org.hibernate.classic.Session ?
                org.hibernate.classic.Session.class : Session.class);
        if (session instanceof EventSource) {
            sessionIfcs = new Class[] {mainIfc, EventSource.class};
        }
        else if (session instanceof SessionImplementor) {
            sessionIfcs = new Class[] {mainIfc, SessionImplementor.class};
        }
        else {
            sessionIfcs = new Class[] {mainIfc};
        }
        return (Session) Proxy.newProxyInstance(
                session.getClass().getClassLoader(), sessionIfcs,
                new CloseSuppressingInvocationHandler(session));
    }

    /**
     * Return a Session for use by this template.
     * <p>Returns a new Session in case of "alwaysUseNewSession" (using the same
     * JDBC Connection as a transactional Session, if applicable), a pre-bound
     * Session in case of "allowCreate" turned off, and a pre-bound or new Session
     * otherwise (new only if no transactional or otherwise pre-bound Session exists).
     *
     * @return the Session to use (never <code>null</code>)
     * @see SessionFactoryUtils#getSession
     * @see SessionFactoryUtils#getNewSession
     * @see #setAlwaysUseNewSession
     */
    protected Session getSession() {
        if (isAlwaysUseNewSession()) {
            return SessionFactoryUtils.getNewSession(getSessionFactory(), getEntityInterceptor());
        } else if (SessionFactoryUtils.hasTransactionalSession(getSessionFactory())) {
            return SessionFactoryUtils.getSession(getSessionFactory(), false);
        } else {
            try {
                return getSessionFactory().getCurrentSession();
            }
            catch (HibernateException ex) {
                throw new DataAccessResourceFailureException("Could not obtain current Hibernate Session", ex);
            }
        }
    }

    public void initialize(Object proxy) throws DataAccessException {
        try {
            Hibernate.initialize(proxy);
        }
        catch (HibernateException ex) {
            throw SessionFactoryUtils.convertHibernateAccessException(ex);
        }
    }

    public Filter enableFilter(String filterName) throws IllegalStateException {
        Session session = SessionFactoryUtils.getSession(getSessionFactory(), false);
        Filter filter = session.getEnabledFilter(filterName);
        if (filter == null) {
            filter = session.enableFilter(filterName);
        }
        return filter;
    }

    /**
     * Performs a Search.
     *
     * @param query
     * @param searchClass
     * @param <T>
     * @return
     */
    public <T> List<T> search(final org.apache.lucene.search.Query query, final Class<T> searchClass) {
        return (List<T>) executeWithNativeSession(new HibernateSearchCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                FullTextSession fullTextSession = Search.getFullTextSession(session);
                org.hibernate.Query hibernateQuery = fullTextSession.createFullTextQuery(query, searchClass);
                return hibernateQuery.list();
            }
        });

    }

    /**
     * Performs a Search.
     *
     * @param query
     * @param searchClass
     * @param <T>
     * @return
     */
    public <T> List<T> search(final org.apache.lucene.search.Query query, final Class... searchClass) {
        return (List<T>) executeWithNativeSession(new HibernateSearchCallback() {
            public Object doInHibernate(Session session) throws HibernateException {
                FullTextSession fullTextSession = Search.getFullTextSession(session);
                org.hibernate.Query hibernateQuery = fullTextSession.createFullTextQuery(query, searchClass);
                return hibernateQuery.list();
            }
        });

    }

    /**
     * Performs a Search with a default analyzer of {@link org.apache.lucene.analysis.standard.StandardAnalyzer}.
     * 
     * @param searchText
     * @param searchClass
     * @param fields
     * @param <T>
     * @return
     */
    public <T> List<T> search(final String searchText, final Class<T> searchClass, final String... fields) {
        return search(searchText, searchClass, new StandardAnalyzer(), fields);
    }

    /**
     * Performs a Search with a default analyzer of {@link org.apache.lucene.analysis.standard.StandardAnalyzer}.
     *
     * @param searchText
     * @param searchClass
     * @param fields
     * @param <T>
     * @return
     */
    public <T> List<T> search(final String searchText, final Class[] searchClass, final String... fields) {
        return search(searchText, searchClass, new StandardAnalyzer(), fields);
    }

    /**
     * 
     * @param searchText
     * @param searchClass
     * @param analyzer
     * @param fields
     * @param <T>
     * @return
     */
    public <T> List<T> search(final String searchText, final Class<T> searchClass, final Analyzer analyzer, final String... fields) {
        QueryParser parser = new MultiFieldQueryParser(fields, analyzer);
        org.apache.lucene.search.Query query;
        try {
            query = parser.parse(searchText);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing search text: " + searchText, e);
        }
        return search(query, searchClass);
    }

    /**
     *
     * @param searchText
     * @param searchClass
     * @param analyzer
     * @param fields
     * @param <T>
     * @return
     */
    public <T> List<T> search(final String searchText, final Class[] searchClass, final Analyzer analyzer, final String... fields) {
        QueryParser parser = new MultiFieldQueryParser(fields, analyzer);
        org.apache.lucene.search.Query query;
        try {
            query = parser.parse(searchText);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing search text: " + searchText, e);
        }
        return search(query, searchClass);
    }

    /**
     * Invocation handler that suppresses close calls on Hibernate Sessions.
     * Also prepares returned Query and Criteria objects.
     * @see org.hibernate.Session#close
     */
	private class CloseSuppressingInvocationHandler implements InvocationHandler {

		private final Session target;

		public CloseSuppressingInvocationHandler(Session target) {
			this.target = target;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			// Invocation on Session interface coming in...

			if (method.getName().equals("equals")) {
				// Only consider equal when proxies are identical.
				return (proxy == args[0] ? Boolean.TRUE : Boolean.FALSE);
			}
			else if (method.getName().equals("hashCode")) {
				// Use hashCode of Session proxy.
				return new Integer(System.identityHashCode(proxy));
			}
			else if (method.getName().equals("close")) {
				// Handle close method: suppress, not valid.
				return null;
			}

			// Invoke method on target Session.
			try {
				Object retVal = method.invoke(this.target, args);

//				// If return value is a Query or Criteria, apply transaction timeout.
//				// Applies to createQuery, getNamedQuery, createCriteria.
//				if (retVal instanceof Query) {
//					prepareQuery(((Query) retVal));
//				}
//				if (retVal instanceof Criteria) {
//					prepareCriteria(((Criteria) retVal));
//				}

				return retVal;
			}
			catch (InvocationTargetException ex) {
				throw ex.getTargetException();
			}
		}
	}
 }
