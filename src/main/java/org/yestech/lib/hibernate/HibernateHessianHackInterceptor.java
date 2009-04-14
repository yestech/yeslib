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
package org.yestech.lib.hibernate;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * For some really odd reason, the hibernate objects don't serialize correctly from hessian unless some
 * method has been called on the object, wtf? So I made this method interceptor to make sure something gets called.
 *
 * @author A.J. Wright
 */
public class HibernateHessianHackInterceptor implements MethodInterceptor
{
    private static final Logger logger = LoggerFactory.getLogger(HibernateHessianHackInterceptor.class);


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable
    {
        try
        {
            Object o = invocation.proceed();


            if (o != null)
            {
                logger.debug("output: " + o.toString());
            }
            return o;
        }
        catch (Throwable throwable)
        {
            logger.error(throwable.getMessage(), throwable);
            throw throwable;
        }
    }

}
