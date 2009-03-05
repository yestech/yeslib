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
import org.yestech.lib.cache.InMemoryReflectionCache;

import java.lang.reflect.Method;
import java.util.HashMap;

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
}
