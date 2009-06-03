package org.yestech.lib.hessian.hibernate;

import com.caucho.hessian.io.AbstractSerializerFactory;
import com.caucho.hessian.io.Deserializer;
import com.caucho.hessian.io.HessianProtocolException;
import com.caucho.hessian.io.Serializer;
import org.hibernate.collection.AbstractPersistentCollection;

/**
 *
 * @author andrew
 */
public class HibernateSerializerFactory extends AbstractSerializerFactory {

    private HibernateListSerializer listSerializer = new HibernateListSerializer();

    @Override
    public Serializer getSerializer(Class cl) throws HessianProtocolException {

        if (AbstractPersistentCollection.class.isAssignableFrom(cl)) {
            return listSerializer;
        }
        return null;

    }

    @Override
    public Deserializer getDeserializer(Class cl) throws HessianProtocolException {
        return null;
    }

}
