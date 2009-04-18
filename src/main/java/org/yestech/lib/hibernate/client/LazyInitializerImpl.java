package org.yestech.lib.hibernate.client;

import net.sf.cglib.proxy.*;
import net.sf.cglib.core.Predicate;
import net.sf.cglib.core.NamingPolicy;
import org.hibernate.proxy.pojo.BasicLazyInitializer;
import org.hibernate.proxy.HibernateProxy;
import org.hibernate.HibernateException;
import org.hibernate.type.AbstractComponentType;
import org.hibernate.engine.SessionImplementor;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 *
 *
 */
public class LazyInitializerImpl extends BasicLazyInitializer implements InvocationHandler
{
    public static final String LOAD_OBJECT_COMMAND = "LO";

    private static final Class[] CALLBACK_TYPES = new Class[]{InvocationHandler.class, NoOp.class};

    private static final CallbackFilter FINALIZE_FILTER = new CallbackFilter()
    {
        public int accept(Method method)
        {
            if (method.getParameterTypes().length == 0 && method.getName().equals("finalize"))
            {

                return 1;
            }
            else
            {
                return 0;
            }
        }
    };

    private LazyInitializerImpl(final String entityName, final Class persistentClass,
                                final Class[] interfaces, final Serializable id, final Method getIdentifierMethod,
                                final Method setIdentifierMethod, final AbstractComponentType componentIdType,
                                final SessionImplementor session)
    {
        super(
                entityName,
                persistentClass,
                id,
                getIdentifierMethod,
                setIdentifierMethod,
                componentIdType,
                session
        );
    }

    protected Object serializableProxy()
    {

        if (isUninitialized())
        {

            LazyReference ref = new LazyReference();
            ref.setClassName(getPersistentClass().getName());
            ref.setId(getIdentifier());
            ref.setToken(System.identityHashCode(getSession()));

            return ref;

        }
        else
        {

            return getTarget();
        }
    }


    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {

        Object result = invoke(method, args, proxy);

        if (result == INVOKE_IMPLEMENTATION)
        {

            Object target = getImplementation();

            if (!method.isAccessible()) method.setAccessible(true);
            Object returnValue;
            try
            {
                returnValue = method.invoke(target, args);
            }
            catch (InvocationTargetException ite)
            {
                throw ite.getTargetException();
            }
            return returnValue == target ? proxy : returnValue;
        }
        else
        {
            return result;
        }
    }

    public static Class getProxyFactory(final Class persistentClass, Class[] interfaces)
    {


        Enhancer en = new Enhancer();
        en.setUseCache(false);
        en.setCallbackTypes(CALLBACK_TYPES);
        en.setCallbackFilter(FINALIZE_FILTER);
        en.setSuperclass(interfaces.length == 1 ? persistentClass : null);
        en.setInterfaces(interfaces);
        en.setNamingPolicy(
                new NamingPolicy()
                {
                    public String getClassName(String arg0, String arg1, Object arg2, Predicate arg3)
                    {
                        return persistentClass.getName() + "$ServerProxy";
                    }
                }
        );

        return en.createClass();

    }

    public static HibernateProxy getProxy(final Class factory, final String entityName,
                                          final Class persistentClass, final Class[] interfaces,
                                          final Method getIdentifierMethod, final Method setIdentifierMethod,
                                          final AbstractComponentType componentIdType, final Serializable id,
                                          final SessionImplementor session) throws HibernateException
    {

        final LazyInitializerImpl instance = new LazyInitializerImpl(
                entityName,
                persistentClass,
                interfaces,
                id,
                getIdentifierMethod,
                setIdentifierMethod,
                componentIdType,
                session
        );

        final HibernateProxy proxy;
        try
        {
            proxy = (HibernateProxy) factory.newInstance();
        }
        catch (Exception e)
        {
            throw new HibernateException("CGLIB Enhancement failed: " + persistentClass.getName(), e);
        }
        ((Factory) proxy).setCallback(0, instance);
        return proxy;
    }

}
