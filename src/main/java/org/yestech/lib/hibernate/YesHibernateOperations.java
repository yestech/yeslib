package org.yestech.lib.hibernate;

import org.springframework.orm.hibernate3.HibernateOperations;
import org.springframework.dao.DataAccessException;

/**
 *
 *
 */
public interface YesHibernateOperations extends HibernateOperations
{
    int bulkUpdateNamedQuery(String queryName, Object... values) throws DataAccessException;
}
