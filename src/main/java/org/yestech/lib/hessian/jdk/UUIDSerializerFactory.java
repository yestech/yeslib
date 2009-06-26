/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.hessian.jdk;

import com.caucho.hessian.io.*;

import java.util.UUID;

/**
 *
 *
 */
public class UUIDSerializerFactory extends AbstractSerializerFactory
{
    private UUIDSerializer uuidSerializer = new UUIDSerializer();
    private UUIDDeserializer uuidDeserializer = new UUIDDeserializer();

    @Override
    public Serializer getSerializer(Class cl) throws HessianProtocolException
    {
        if (UUID.class.isAssignableFrom(cl)) {
            return uuidSerializer;
        }
        return null;
    }

    @Override
    public Deserializer getDeserializer(Class cl) throws HessianProtocolException
    {
        if (UUID.class.isAssignableFrom(cl)) {
            return uuidDeserializer;
        }
        return null;
    }
}