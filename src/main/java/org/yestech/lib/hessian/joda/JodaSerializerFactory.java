package org.yestech.lib.hessian.joda;

import com.caucho.hessian.io.*;
import org.joda.time.DateTime;

/**
 *
 *
 */
public class JodaSerializerFactory extends AbstractSerializerFactory
{
    private JodaDateTimeSerializer jodaDateTimeSerializer = new JodaDateTimeSerializer();
    private JodaDateTimeDeserializer jodaDateTimeDeserializer = new JodaDateTimeDeserializer();

    @Override
    public Serializer getSerializer(Class cl) throws HessianProtocolException
    {
        if (DateTime.class.isAssignableFrom(cl)) {
            return jodaDateTimeSerializer;
        }
        return null;
    }

    @Override
    public Deserializer getDeserializer(Class cl) throws HessianProtocolException
    {
        if (DateTime.class.isAssignableFrom(cl)) {
            return jodaDateTimeDeserializer;
        }
        return null;
    }
}
