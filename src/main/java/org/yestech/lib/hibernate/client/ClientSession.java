package org.yestech.lib.hibernate.client;

import org.springframework.context.ApplicationContext;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.rmi.RemoteException;


/**
 *
 *
 */
public class ClientSession
{
    private static final ThreadLocal<HibernateServerRemote> REF = new ThreadLocal<HibernateServerRemote>();
    private static ApplicationContext applicationContext;

    public static void init(ApplicationContext applicationContext)
    {
        ClientSession.applicationContext = applicationContext;
    }

    private static void start() throws ClassCastException, NamingException, RemoteException
    {
        if (isActive())
        {
            throw new IllegalStateException("nested sessions are not supported");
        }


        HibernateServerRemote home = (HibernateServerRemote) applicationContext.getBean("hibernateServer");

        REF.set(home);
    }

    private static boolean isActive()
    {
        return REF.get() != null;
    }

    /*package*/
    static HibernateServerRemote getLoader()
    {
        if (!isActive())
        {
            throw new IllegalStateException("session is not active");
        }
        return REF.get();
    }

    private static void release() throws RemoteException
    {
        HibernateServerRemote loader = REF.get();
        if (loader == null)
        {
            throw new IllegalStateException("session is not started");
        }
        REF.set(null);
        //loader.remove();
    }

    public static void execute(Runnable cmd) throws Exception
    {

        InitialContext context = new InitialContext();
        try
        {

            /*
            UserTransaction transaction = (UserTransaction)
                    context.lookup("UserTransaction");

            transaction.begin();
            */
            try
            {
                start();
                try
                {
                    cmd.run();
                }
                finally
                {
                    release();
                }
              //  transaction.commit();
            }
            finally
            {
                /*
                if (transaction.getStatus() == Status.STATUS_ACTIVE)
                {
                    transaction.rollback();
                }
                */
            }
        }
        finally
        {
            context.close();
        }
    }


}
