/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.jpa;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaOperations;

/**
 *
 *
 */
public interface YesJpaOperations extends JpaOperations
{
    int bulkUpdateNamedQuery(String queryName, Object... values) throws DataAccessException;
}