/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.hessian.jdk;

import com.caucho.hessian.io.*;

import java.math.BigDecimal;

/**
 *
 *
 */
public class BigDecimalSerializerFactory extends AbstractSerializerFactory
{
    private BigDecimalSerializer bigDecimalSerializer = new BigDecimalSerializer();
    private BigDecimalDeserializer bigDecimalDeserializer = new BigDecimalDeserializer();

    @Override
    public Serializer getSerializer(Class cl) throws HessianProtocolException
    {
        if (BigDecimal.class.isAssignableFrom(cl)) {
            return bigDecimalSerializer;
        }
        return null;
    }

    @Override
    public Deserializer getDeserializer(Class cl) throws HessianProtocolException
    {
        if (BigDecimal.class.isAssignableFrom(cl)) {
            return bigDecimalDeserializer;
        }
        return null;
    }
}