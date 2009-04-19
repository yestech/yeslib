package org.yestech.lib.hibernate.client;

import org.hibernate.tuple.entity.PojoEntityTuplizer;
import org.hibernate.tuple.entity.EntityMetamodel;
import org.hibernate.proxy.ProxyFactory;
import org.hibernate.mapping.PersistentClass;

/**
 *
 *
 */
public class TuplizerImpl extends PojoEntityTuplizer
{
    public TuplizerImpl(EntityMetamodel entityMetamodel, PersistentClass mappedEntity)
    {
        super(entityMetamodel, mappedEntity);
    }

    protected ProxyFactory createProxyFactory()
    {
        return new ProxyFactoryImpl();
    }

}
