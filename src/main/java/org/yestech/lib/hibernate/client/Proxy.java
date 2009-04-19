package org.yestech.lib.hibernate.client;

import java.io.ObjectStreamException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import net.sf.cglib.core.NamingPolicy;
import net.sf.cglib.core.Predicate;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;
import net.sf.cglib.proxy.NoOp;


/**
 *
 *
 */
public class Proxy
{

    private static final Class[] CALLBACK_TYPES = new Class[]{InvocationHandler.class, NoOp.class};

    private static final class CollectionProxy extends AbstractSet
    {

        private final CollectionReference reference;
        private Set remote;

        private CollectionProxy(CollectionReference reference)
        {
            super();
            this.reference = reference;
        }

        Object writeReplare() throws ObjectStreamException
        {

            if (remote != null)
            {
                return remote;
            }
            else
            {
                return reference;
            }
        }

        private Set load()
        {
            try
            {
                if (remote == null)
                {
                    remote = (Set) ClientSession.getLoader().
                            initializeCollection(reference.getRole(), reference.getId());
                }
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }

            return remote;

        }

        public int size()
        {

            return load().size();
        }

        public boolean isEmpty()
        {

            return load().isEmpty();
        }

        public Object[] toArray()
        {

            return load().toArray();
        }

        public boolean contains(Object o)
        {

            return load().contains(o);
        }

        public boolean containsAll(Collection c)
        {

            return load().containsAll(c);
        }

        public Iterator iterator()
        {

            return load().iterator();
        }

        public Object[] toArray(Object[] a)
        {

            return load().toArray(a);
        }

    }

    public interface WriteReplace
    {

        Object writeReplace() throws ObjectStreamException;
    }

    private static final class Interceptor implements InvocationHandler
    {


        private Object entity;
        private LazyReference ref;

        Interceptor(LazyReference ref)
        {
            this.ref = ref;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {

            if (args.length == 0 &&
                    method.getDeclaringClass() == WriteReplace.class &&
                    method.getName().equals("writeReplace"))
            {
                if (entity != null)
                {
                    return entity;
                }
                else
                {
                    return ref;
                }
            }
            if (entity == null)
            {
                entity = ClientSession.getLoader().initializeEntity(ref.getClassName(), ref.getId(), ref.getToken());
            }
            try
            {

                return method.invoke(entity, args);

            }
            catch (InvocationTargetException ite)
            {
                throw ite;
            }
        }
    }


    public static Object create(final LazyReference ref) throws ClassNotFoundException
    {

        Class cls = Class.forName(ref.getClassName(), false, Thread
                .currentThread().getContextClassLoader());
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cls);
        enhancer.setCallbackTypes(CALLBACK_TYPES);
        enhancer.setInterfaces(new Class[]{WriteReplace.class});
        enhancer.setCallbackFilter(new CallbackFilter()
        {
            public int accept(Method m)
            {
                return Modifier.isPublic(m.getModifiers()) ? 0 : 1;
            }
        });
        enhancer.setCallbacks(new Callback[]{new Interceptor(ref), NoOp.INSTANCE});
        enhancer.setNamingPolicy(
                new NamingPolicy()
                {
                    public String getClassName(String arg0, String arg1, Object arg2, Predicate arg3)
                    {
                        return ref.getClassName() + "$ClientProxy";
                    }
                }
        );

        return enhancer.create();
    }

    public static Set create(final CollectionReference reference)
    {

        Set set = new CollectionProxy(reference);

        return Collections.unmodifiableSet(set);
    }

}