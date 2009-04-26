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
package org.yestech.lib.commons;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A simple adapter for bean utils the generifies and wraps the exceptions in runtime exceptions.
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings("unchecked")
final public class BeanUtilsAdapter {
    final private static Logger logger = LoggerFactory.getLogger(BeanUtilsAdapter.class);

    public static <B> B cloneBean(Object bean) {
        try {
            return (B)BeanUtils.cloneBean(bean);
        } catch (Exception e) {
            logger.error("error cloning: " + bean, e);
            throw new RuntimeException(e);
        }
    }
}
