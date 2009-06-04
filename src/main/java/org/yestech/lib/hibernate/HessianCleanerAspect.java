package org.yestech.lib.hibernate;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.LazyInitializationException;
import org.hibernate.collection.PersistentBag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.util.*;

@Aspect
public class HessianCleanerAspect {

    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(HessianCleanerAspect.class);


    @Pointcut("execution(public * com.blackbox..*Manager.*(..))")
    private void cleanHibernateObjects() { }


    @Around("cleanHibernateObjects()")
    public Object clean(ProceedingJoinPoint call) {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("clean(ProceedingJoinPoint) - from logging aspect: entering method [" + call.toShortString() + "] with params:" + appendArgs(call.getArgs()));
            }
            Object point = call.proceed();
            point = descendThroughNode(point, new ArrayList<Object>());
            if (logger.isDebugEnabled()) {
                logger.debug("clean(ProceedingJoinPoint) - from logging aspect: exiting method [" + call.toShortString() + "with return as:" + point);
            }
            return point;
        } catch (Throwable t) {
            throw new UnsupportedOperationException(t);
        }
    }

    /**
     *
     * This method is going to presume a couple of things.
     * <ol>
     * <li>Presume that we are not using any kind of bags or collections which
     * shall contain duplicate elements.</li>
     * </ol>
     *
     * @param node
     * @param visited
     *            a {@link List} of node references for the nodes that have
     *            already been visited, this prevents getting stuck on, for
     *            example, circular references.
     * @return
     */
    private Object descendThroughNode(Object node, List visited) {

        // Check to see if we have already visited this node.
        if (visited.contains(node)) {
            // If we have, then just return.
            return node;
        } else {
            // Otherwise add the node to our visited list
            visited.add(node);
        }

        try {
            if (node == null) {
                return null;
            }
            // if (node instanceof PersistentCollection) {
            // return null;
            // }
            // If you got a list back from Hibernate
            if (node instanceof List) {
                return handleList(node, visited);
            }
            PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
            PropertyDescriptor[] propertyDescriptors = propertyUtilsBean.getPropertyDescriptors(node);
            for (PropertyDescriptor p : propertyDescriptors) {
                String name = p.getName();

                if (logger.isDebugEnabled()) {
                    logger.debug("descendThroughPoint(Object) - " + name);
                }
                if (!"class".equals(name)) {
                    Object property = propertyUtilsBean.getProperty(node, name);
                    if (property instanceof Set) {
                        try {
                            ((Set) property).size();
                            // Ok, didn't crash ... must be possible to get the
                            // data from it...
                            Set s = new HashSet();
                            for (Object o : ((Set) property)) {
                                s.add(descendThroughNode(o, visited));
                            }
                            property = s;
                            propertyUtilsBean.setProperty(node, name, s);
                        } catch (LazyInitializationException l) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("descendThroughPoint(Object) - clearing the Set:" + name);
                            }
                            propertyUtilsBean.setProperty(node, name,
                                    new HashSet());
                        }
                    } else if (property instanceof List) {
                        try {
                            if (property instanceof PersistentBag) {
                                if (((PersistentBag) property).wasInitialized()) {
                                    ((List) property).size();
                                    // Ok, didn't crash ... must be possible to
                                    // get the
                                    // data from it...
                                    List s = new ArrayList();
                                    for (Object o : ((List) property)) {
                                        s.add(descendThroughNode(o, visited));
                                    }
                                    propertyUtilsBean.setProperty(node, name, s);
                                } else {
                                    propertyUtilsBean.setProperty(node, name,
                                            new ArrayList());
                                }
                            }
                        } catch (Throwable l) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("descendThroughPoint(Object) - clearing the List:" + name);
                            }
                            propertyUtilsBean.setProperty(node, name,
                                    new ArrayList());
                        }
                    } else if (property instanceof Map) {
                        try {
                            ((Map) property).keySet().size();
                            // Ok, didn't crash ... must be possible to get the
                            // data from it...
                            Map s = new HashMap();
                            for (Object o : ((Map) property).keySet()) {
                                s.put(o, ((Map) property).get(descendThroughNode(o, visited)));
                            }
                            propertyUtilsBean.setProperty(node, name, s);
                        } catch (LazyInitializationException l) {
                            if (logger.isDebugEnabled()) {
                                logger.debug("descendThroughPoint(Object) - clearing  the Map:" + name);
                            }
                            propertyUtilsBean.setProperty(node, name,
                                    new HashMap());
                        }
                    } else {
                        if (logger.isDebugEnabled()) {
                            logger.debug("descendThroughPoint(Object) - have not detected any collection membership .. lets recurse ....");
                        }
                        // If we can think of any other Simple objects that we
                        // shouldn't descend upon .. add them here.
                        if (!isIgnorable(property)) {
                            // property = descendThroughNode(property, visited);

                            propertyUtilsBean.setProperty(node, name,
                                    descendThroughNode(property, visited));
                        }
                    }
                }
            }
            return node;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    /**
     * Can we ignore the property? This method should be modified to just check
     * if the class is annotated with the &and; entity annotation.
     *
     * @param property
     * @return
     */
    private boolean isIgnorable(Object property) {
        return (property == null) || (property instanceof String) || (property instanceof Double) || (property instanceof Long) || (property instanceof java.sql.Timestamp) || (property instanceof Date) || (property instanceof java.sql.Date);
    }

    @SuppressWarnings("unchecked")
    private Object handleList(Object node, List visited) {
        if (node instanceof PersistentBag) {
            if (!((PersistentBag) node).wasInitialized()) {
                return new ArrayList();
            }
        }
        List ret = new ArrayList();
        for (Object o : (List) node) {
            ret.add(descendThroughNode(o, visited));
        }
        return ret;
    }

    private String appendArgs(Object[] args) {
        StringBuffer ret = new StringBuffer();
        for (Object object : args) {
            ret.append(":");
            ret.append(object);
            ret.append(":");
        }
        return ret.toString();
    }
}
