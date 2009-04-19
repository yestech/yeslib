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

import org.springframework.dao.DataAccessException;
import org.apache.lucene.analysis.Analyzer;

import java.util.List;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public interface YesHibernateSearchOperations {
    Object doExecute(HibernateSearchCallback action, boolean enforceNewSession, boolean enforceNativeSession)
            throws DataAccessException;

    <T> List<T> search(org.apache.lucene.search.Query query, Class searchClass);

    <T> List<T> search(String searchText, Class searchClass, String... fields);

    <T> List<T> search(String searchText, Class searchClass, Analyzer analyzer, String... fields);
}
