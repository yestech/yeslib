/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Utility Class for Dealing with {@link Class} and Reflection.
 *
 */
final public class Clazz {
    final private static Logger logger = LoggerFactory.getLogger(Clazz.class);

    private Clazz() {
        super();
    }


    /**
     * Looks up and retrieves an Initialized class.  This means that the
     * class with only load it's static block!
     *
     * @param classFqn FQN of the class to initialize
     * @return The initialized Class
     * @throws RuntimeException If error happens Loading or Initializing
     */
    public static Class getClass(String classFqn) {
        Class clazz = InMemoryReflectionCache.getClass(classFqn);
        return clazz;
    }

    /**
     * Instantiates a Class from a Fully Qualified Name.  It assumes that there
     * is a default Constructor.
     *
     * @param classFQN FQN of the class to Instantiate
     * @return The Instantiated Class
     * @throws RuntimeException If error happens Instantiating
     */
    @SuppressWarnings("unchecked")
    public static <I> I instantiateClass(String classFQN) {
        return (I)instantiateClass(getClass(classFQN));
    }

    /**
     * Instantiates a Class from a Class.  It assumes that there
     * is a default Constructor.
     *
     * @param clazz Class to Instantiate
     * @return The Instantiated Class
     * @throws RuntimeException If error happens Instantiating
     */
    @SuppressWarnings("unchecked")
    public static <I> I instantiateClass(Class clazz) {
        try {
            return (I)clazz.newInstance();
        } catch (Exception e) {
            logger.error("Error Instantiating Class: " + clazz, e);
            throw new RuntimeException("Error Instantiating Class: " + clazz, e);
        }
    }

    /**
     * Returns the Method that is a associated with the FQN class.
     *
     * @param classFQN The FQN of the class method is a member of
     * @param methodName The method name
     * @param params Array of Class the are in the method parameter list
     * @return the {@link Method} found
     * @throws RuntimeException If error happens Loading Method
     */
    public static Method getMethod(String classFQN, String methodName, Class[] params) {
        return InMemoryReflectionCache.getMethod(classFQN, methodName, params);
    }

    /**
     * Dynamically invokes a method using the supplied params as the parameters for the call.
     * If the method being invoked is static, the instance should be null.
     *
     * @param method Method to invoke
     * @param methodArgs Parameters that will be passed to the method
     * @param instance Instance of the Onject to invoke method on.  (null if method is static)
     * @return The result of the method call
     * @throws RuntimeException If error happens Invoking Method
     */
    public static Object invoke(Method method, Object[] methodArgs, Object instance) {
        try {
            return method.invoke(instance, methodArgs);
        } catch (Exception e) {
            logger.error("Error Invoking Method: " + method.getName(), e);
            throw new RuntimeException("Error Invoking Method: " + method.getName(), e);
        }
    }

    /**
     * Dynamically invokes a method using the supplied params as the parameters for the call.
     * If the method being invoked is static, the instance should be null.
     *
     * @param classFQN The FQN of the class method is a member of
     * @param methodName The method name
     * @param params Array of Class the are in the method parameter list
     * @param methodArgs Parameters that will be passed to the method
     * @param instance Instance of the Onject to invoke method on.  (null if method is static)
     * @return The result of the method call
     * @throws RuntimeException If error happens Invoking Method
     */
    public static Object invoke(String classFQN, String methodName, Class[] params,
                                Object[] methodArgs, Object instance) {
        return invoke(getMethod(classFQN, methodName, params), methodArgs, instance);
    }

    /**
     * Returns a Cached Method associated by the methodName.  If the method
     * isn't in the cache a new one is created and added to the cache.  There
     * will <b>ALWAYS</b> be only one method instance in the cache.
     *
     * @param clazz      Class method associated with
     * @param methodName Name of the method
     * @param argTypes   Class Array of argument types in method signature
     * @return The Method
     */
    public static Method getMethod(Class clazz, String methodName, Class[] argTypes) {
        return InMemoryReflectionCache.getMethod(clazz, methodName, argTypes);
    }

    /**
     * Returns a Cached Constructor associated with the Class.  If the
     * Constructor isn't in the cache a new one is created and added to the
     * cache.  There will <b>ALWAYS</b> be only one Constructor instance in the
     * cache.
     *
     * @param clazz     Class Constructor associated with
     * @param ctorTypes Class Array of argument types in Constructors signature
     * @return The Constructor
     */
    public static Constructor getConstructor(Class clazz, Class[] ctorTypes) {
        return InMemoryReflectionCache.getCtor(clazz, ctorTypes);
    }

    /**
     * This class represents the manager for all the different cache policies.
     *
     * @author Arthur Copeland
     */
    private static class InMemoryReflectionCache implements Serializable {

        //--------------------------------------------------------------------------
        // C L A S S   V A R I A B L E S
        //--------------------------------------------------------------------------
        final private static Logger logger = LoggerFactory.getLogger(InMemoryReflectionCache.class);

        final private static HashMap<String, Class> classCache = new HashMap<String, Class>();

        final private static HashMap<String, ArrayList> methodCache = new HashMap<String, ArrayList>();

        final private static HashMap<String, ArrayList> ctorCache = new HashMap<String, ArrayList>();


        //--------------------------------------------------------------------------
        // S T A T I C   I N I T I A L I Z E R
        //--------------------------------------------------------------------------

        //--------------------------------------------------------------------------
        // C O N S T R U C T O R S
        //--------------------------------------------------------------------------

        /**
         * default ctor
         */
        private InMemoryReflectionCache() {
            super();
        }

        //--------------------------------------------------------------------------
        // S T A T I C   M E T H O D S
        //--------------------------------------------------------------------------
        /**
         * Returns a Cached Initialized Class associated by the className.  If the
         * class isn't in the cache a new one is created, initialized and added to
         * the cache.  There will <b>ALWAYS</b> be only one class instance in the
         * cache.
         *
         * @param className FQN of Class to find and initialize
         * @return The Class
         */
        public static Class getClass(String className) {
            Class clazz = classCache.get(className);
            if (clazz == null) {
                try {
                    clazz = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    logger.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
                classCache.put(className, clazz);
            }

            return clazz;
        }

        /**
         * Returns a Cached Method associated by the methodName.  If the method
         * isn't in the cache a new one is created and added to the cache.  There
         * will <b>ALWAYS</b> be only one method instance in the cache.
         *
         * @param clazzName  FQN of Class method associated with
         * @param methodName Name of the method
         * @param argTypes   Class Array of argument types in method signature
         * @return The Method
         */
        public static Method getMethod(String clazzName, String methodName,
                                       Class[] argTypes) {
            return getMethod(getClass(clazzName), methodName, argTypes);
        }

        /**
         * Returns a Cached Method associated by the methodName.  If the method
         * isn't in the cache a new one is created and added to the cache.  There
         * will <b>ALWAYS</b> be only one method instance in the cache.
         *
         * @param clazz      Class method associated with
         * @param methodName Name of the method
         * @param argTypes   Class Array of argument types in method signature
         * @return The Method
         */
        public static Method getMethod(Class clazz, String methodName,
                                       Class[] argTypes) {

            Method method = null;
            ArrayList<Method> methods = null;
            methods = methodCache.get(methodName);
            if (methods == null) {
                synchronized (methodCache) {
                    methods = methodCache.get(methodName);
                    if (methods == null) {
                        methods = new ArrayList<Method>();
                        methodCache.put(methodName, methods);
                    }
                }
            }
            int sz = methods.size();
            if (sz == 0) {
                try {
                    method = clazz.getMethod(methodName, argTypes);
                } catch (NoSuchMethodException e) {
                    logger.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
                synchronized (methods) {
                    if (!methods.contains(method)) {
                        methods.add(method);
                    }
                }
            } else {
                for (int i = 0; i < sz; i++) {
                    Method tmethod = methods.get(i);
                    Class targTypes[] = tmethod.getParameterTypes();
                    if ((argTypes == null || argTypes.length == 0) &&
                            (targTypes == null || targTypes.length == 0)) {
                        method = tmethod;
                    } else {
                        int tsz = targTypes.length;
                        if (argTypes.length == tsz) {
                            //find the first method that satisfies the argTypes!!!!!
                            boolean valid = false;
                            for (int t = 0; t < tsz; t++) {
                                boolean good =
                                        argTypes[t].isAssignableFrom(targTypes[t]);
                                if (!good) {
                                    valid = false;
                                    break;
                                } else {
                                    valid = true;
                                }
                            }
                            if (valid) {
                                method = tmethod;
                            }
                        }
                    }
                }
                if (method == null) {
                    try {
                        method = clazz.getMethod(methodName, argTypes);
                    } catch (NoSuchMethodException e) {
                        logger.error(e.getMessage(), e);
                        throw new RuntimeException(e);
                    }
                    synchronized (methods) {
                        if (!methods.contains(method)) {
                            methods.add(method);
                        }
                    }
                }
            }
            return method;
        }

        /**
         * Returns a Cached Constructor associated with the className.  If the
         * Constructor isn't in the cache a new one is created and added to the
         * cache.  There will <b>ALWAYS</b> be only one Constructor instance in the
         * cache.
         *
         * @param clazzName FQN of Class Constructor associated with
         * @param ctorTypes Class Array of argument types in Constructors signature
         * @return The Constructor
         */
        public static Constructor getCtor(String clazzName, Class[] ctorTypes) {
            return getCtor(getClass(clazzName), ctorTypes);
        }

        /**
         * Returns a Cached Constructor associated with the Class.  If the
         * Constructor isn't in the cache a new one is created and added to the
         * cache.  There will <b>ALWAYS</b> be only one Constructor instance in the
         * cache.
         *
         * @param clazz     Class Constructor associated with
         * @param ctorTypes Class Array of argument types in Constructors signature
         * @return The Constructor
         */
        public static Constructor getCtor(Class clazz, Class[] ctorTypes) {
            String ctorName = clazz.getName();

            Constructor ctor = null;
            ArrayList<Constructor> ctors = null;
            ctors = ctorCache.get(ctorName);
            if (ctors == null) {
                synchronized (ctorCache) {
                    ctors = ctorCache.get(ctorName);
                    if (ctors == null) {
                        ctors = new ArrayList<Constructor>();
                        ctorCache.put(ctorName, ctors);
                    }
                }
            }
            int sz = ctors.size();
            if (sz == 0) {
                try {
                    ctor = clazz.getConstructor(ctorTypes);
                } catch (NoSuchMethodException e) {
                    logger.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
                synchronized (ctors) {
                    if (!ctors.contains(ctor)) {
                        ctors.add(ctor);
                    }
                }
            } else {
                for (int i = 0; i < sz; i++) {
                    Constructor tctor = ctors.get(i);
                    Class tctorTypes[] = tctor.getParameterTypes();
                    if ((ctorTypes == null || ctorTypes.length == 0) &&
                            (tctorTypes == null || tctorTypes.length == 0)) {
                        ctor = tctor;
                    } else {
                        int tsz = tctorTypes.length;
                        if (ctorTypes.length == tsz) {
                            //find the first ctor that satisfies the ctorTypes!!!!!
                            boolean valid = false;
                            for (int t = 0; t < tsz; t++) {
                                boolean good =
                                        ctorTypes[t].isAssignableFrom(tctorTypes[t]);
                                if (!good) {
                                    valid = false;
                                    break;
                                } else {
                                    valid = true;
                                }
                            }
                            if (valid) {
                                ctor = tctor;
                            }
                        }
                    }
                }
                if (ctor == null) {
                    try {
                        ctor = clazz.getConstructor(ctorTypes);
                    } catch (NoSuchMethodException e) {
                        logger.error(e.getMessage(), e);
                        throw new RuntimeException(e);
                    }
                    synchronized (ctors) {
                        if (!ctors.contains(ctor)) {
                            ctors.add(ctor);
                        }
                    }
                }
            }
            return ctor;

        }

        //--------------------------------------------------------------------------
        // F A C T O R Y   M E T H O D S
        //--------------------------------------------------------------------------
        /**
         * Returns a CacheManager.
         *
         * @return a CacheManager
         */
        public static InMemoryReflectionCache getManager() {
            return new InMemoryReflectionCache();
        }
    }
}
