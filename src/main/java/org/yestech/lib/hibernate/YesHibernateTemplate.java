package org.yestech.lib.hibernate;

import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.dao.DataAccessException;

import org.hibernate.Session;
import org.hibernate.HibernateException;
import org.hibernate.Query;


/**
 *
 *
 */
public class YesHibernateTemplate extends HibernateTemplate implements YesHibernateOperations
{

    public int bulkUpdateNamedQuery(final String queryName, final Object... values) throws DataAccessException
    {

        Integer updateCount = (Integer) execute(new HibernateCallback()
        {
            public Object doInHibernate(Session session) throws HibernateException
            {
                Query queryObject = session.getNamedQuery(queryName);
                prepareQuery(queryObject);
                if (values != null)
                {
                    for (int i = 0; i < values.length; i++)
                    {
                        queryObject.setParameter(i, values[i]);
                    }
                }
                return queryObject.executeUpdate();
            }
        });
        return updateCount;

    }

}
