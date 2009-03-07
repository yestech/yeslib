package org.yestech.lib.hessian.joda;

import com.caucho.hessian.io.*;
import org.joda.time.DateTime;

/**
 *
 *
 */
public class JodaSerializerFactory extends AbstractSerializerFactory
{
    @Override
    public Serializer getSerializer(Class cl) throws HessianProtocolException
    {
        if (DateTime.class.isAssignableFrom(cl)) {
            return new JodaDateTimeSerializer();
        }
        return null;
    }

    @Override
    public Deserializer getDeserializer(Class cl) throws HessianProtocolException
    {
        if (DateTime.class.isAssignableFrom(cl)) {
            return new JodaDateTimeDeserializer();
        }
        return null;
    }
}
