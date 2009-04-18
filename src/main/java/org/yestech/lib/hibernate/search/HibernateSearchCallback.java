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
package org.yestech.lib.hibernate.search;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public interface HibernateSearchCallback {

    Object doInHibernate(Session session) throws HibernateException;
}
