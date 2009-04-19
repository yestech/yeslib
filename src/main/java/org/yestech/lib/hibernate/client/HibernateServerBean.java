package org.yestech.lib.hibernate.client;

import org.hibernate.EntityMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.CollectionKey;
import org.hibernate.engine.PersistenceContext;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.engine.SessionImplementor;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Required;

import javax.naming.NamingException;
import java.io.Serializable;
import java.util.Collection;
import java.rmi.RemoteException;

/**
 *
 *
 */
public class HibernateServerBean implements HibernateServerRemote, Serializable {

    private static final long serialVersionUID = -3354876500675131726L;
    private SessionFactory factory;

    public Object initializeEntity(String className,Serializable id, int token)
    throws ClassNotFoundException, HibernateException, NamingException{
        Class cls = Class.forName(className,false,Thread.currentThread().getContextClassLoader());
        Session currentSession = factory.getCurrentSession();
        if(System.identityHashCode(currentSession) == token){
            Object obj = currentSession.get(cls,id);
            if(obj == null){
                throw new IllegalStateException("object not found " + cls + " " + id);
            }
         return ((HibernateProxy)obj).getHibernateLazyInitializer().getImplementation();
        }else {
            throw new IllegalStateException("invalid session id");
        }
    }

    public Collection initializeCollection(String role,Serializable id)throws ClassNotFoundException, HibernateException, NamingException{

        PersistenceContext context = ((SessionImplementor)factory.getCurrentSession()).getPersistenceContext();

        CollectionPersister persister = ((SessionFactoryImplementor)factory).getCollectionPersister(role);

        CollectionKey key = new CollectionKey(persister,id,EntityMode.POJO);

        RemotableSetType.Wrapper col = (RemotableSetType.Wrapper) context.getCollection(key);

        if(col == null){
            throw new IllegalStateException("collection not found");
        }

        return col.unwrapp();

    }

    @Required
    public void setFactory(SessionFactory factory)
    {
        this.factory = factory;
    }
}

