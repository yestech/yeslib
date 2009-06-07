/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.jpa;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.JpaTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;


/**
 *
 *
 */
public class YesJpaTemplate extends JpaTemplate implements YesJpaOperations
{

    public int bulkUpdateNamedQuery(final String queryName, final Object... values) throws DataAccessException
    {

        return (Integer) execute(new JpaCallback()
        {
            @Override
            public Object doInJpa(EntityManager em) throws PersistenceException {
                Query queryObject = em.createNamedQuery(queryName);
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

    }

}