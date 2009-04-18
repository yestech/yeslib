package org.yestech.lib.hibernate.client;

import org.hibernate.HibernateException;

import javax.naming.NamingException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.io.Serializable;
import java.util.Collection;

/**
 *
 *
 */
public interface Initializer extends Remote
{
    Object initializeEntity(String className, Serializable id, int token)
            throws RemoteException,
            ClassNotFoundException,
            HibernateException,
            NamingException;

    Collection initializeCollection(String role, Serializable id)
            throws RemoteException,
            ClassNotFoundException,
            HibernateException,
            NamingException;

}
