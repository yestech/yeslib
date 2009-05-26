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
package org.yestech.lib.jpa;

import static org.yestech.lib.util.CastUtil.cast;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * A adapter for a JPA {@link javax.persistence.EntityManager}.
 * 
 * @author Artie Copeland
 * @version $Revision: $
 */
public class EntityManagerAdapter {
    
    @PersistenceContext
    private EntityManager entityManager;

    public EntityManagerAdapter() {
    }

    /**
     * Defines ASC and DESC sort orders for queries.
     */
    public enum SortOrder {
      ASC, DESC
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public <T> T findById(final Class<T> entityClass, final Object id) {
      if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");
      if (null == id) throw new IllegalArgumentException("id can't be null");

      return entityManager.find(entityClass, id);
    }

    public boolean delete(final Object entity) {
      if (null == entity) throw new IllegalArgumentException("entity can't be null");

      entityManager.remove(entity);
      return true;
    }

    public <T> boolean deleteById(final Class<T> entityClass, final Object id) {
      if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");
      if (null == id) throw new IllegalArgumentException("id can't be null");

      return delete(findById(entityClass, id));
    }

    public <T> List<T> getAllEntities(final Class<T> entityClass) {
      if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");

      return cast(entityManager.createQuery("select e from " + entityClass.getSimpleName() + " e")
          .getResultList());
    }

    public <T> List<T> getAllEntities(final Class<T> entityClass, final String orderByAttributeName,
        final SortOrder sortOrder) {
      if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");
      if (null == orderByAttributeName) throw new IllegalArgumentException("orderByAttributeName can't be null");

      return cast(entityManager.createQuery(
          "select e from " + entityClass.getSimpleName() + " e order by e." + orderByAttributeName + " "
              + sortOrder.name()).getResultList());
    }

    /**
     * Checks to see if the entity exists.  if it foes then merge, else persist.
     *
     * @param entityClass
     * @param entity
     * @param <T>
     * @return
     */
    public <T> T persistOrMerge(final Class<T> entityClass, final T entity) {
      if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");
      if (null == entity) throw new IllegalArgumentException("entity can't be null");
        if (!entityManager.contains(entity)) {
            persist(entityClass, entity);
        } else {
            merge(entityClass, entity);
        }
      return entity;
    }

    public <T> T persist(final Class<T> entityClass, final T entity) {
      if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");
      if (null == entity) throw new IllegalArgumentException("entity can't be null");

      entityManager.persist(entity);
      return entity;
    }

    public <T> T merge(final Class<T> entityClass, final T entity) {
      if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");
      if (null == entity) throw new IllegalArgumentException("entity can't be null");

      return entityManager.merge(entity);
    }

    public long countEntities(final Class<?> entityClass) {
      if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");

      return (Long) entityManager.createQuery("select count(entity) from " + entityClass.getSimpleName() + " entity")
          .getSingleResult();
    }

    public long countEntitiesByAttribute(final Class<?> entityClass, final String attributeName,
        final Object attributeValue) {
      if (null == entityClass) throw new IllegalArgumentException("entityClass can't be null");
      if (null == attributeName) throw new IllegalArgumentException("attributeName can't be null");
      if (null == attributeValue) throw new IllegalArgumentException("attributeValue can't be null");

      return (Long) entityManager.createQuery(
          "select count(e) from " + entityClass.getSimpleName() + " e where e." + attributeName + " = ?1").setParameter(
          1, attributeValue).getSingleResult();
    }
}
