package org.yestech.lib.hibernate.client;

import org.hibernate.HibernateException;
import org.hibernate.collection.PersistentCollection;
import org.hibernate.collection.PersistentSet;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.persister.collection.CollectionPersister;

import java.io.ObjectStreamException;
import java.util.*;


/**
 *
 *
 */
public class RemotableSetType
{
    public static class Wrapper extends PersistentSet
    {

        private static final long serialVersionUID = -4399185767996064350L;

        public Wrapper(SessionImplementor session, Set set)
        {
            super(session, set);
        }

        public Wrapper(SessionImplementor session)
        {
            super(session);
        }

        public Set unwrapp()
        {
            read();
            return set;
        }

        Object writeReplace() throws ObjectStreamException
        {

            if (wasInitialized())
            {
                return set;
            }

            CollectionReference ref = new CollectionReference();
            ref.setId(getKey());
            ref.setRole(getRole());

            return ref;

        }
    }


    public PersistentCollection instantiate(SessionImplementor session,
                                            CollectionPersister persister) throws HibernateException
    {

        return new Wrapper(session);
    }

    public PersistentCollection wrap(SessionImplementor session,
                                     Object collection)
    {

        return new Wrapper(session, (Set) collection);
    }

    public Iterator getElementsIterator(Object collection)
    {

        return ((Collection) collection).iterator();
    }

    public boolean contains(Object collection, Object entity)
    {

        return ((Collection) collection).contains(entity);
    }

    public Object indexOf(Object collection, Object entity)
    {

        return null;
    }

    public Object replaceElements(Object original, Object target,
                                  CollectionPersister persister, Object owner, Map copyCache,
                                  SessionImplementor session) throws HibernateException
    {

        Collection result = (Collection) target;

        result.clear();

        result.addAll((Collection) original);

        return result;

    }

    public Object instantiate()
    {

        return new HashSet();
    }

    public Class<Set> getReturnedClass()
    {

        return Set.class;
    }


}
