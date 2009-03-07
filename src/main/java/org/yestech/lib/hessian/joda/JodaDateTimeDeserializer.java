package org.yestech.lib.hessian.joda;

import com.caucho.hessian.io.AbstractDeserializer;
import com.caucho.hessian.io.AbstractHessianInput;

import java.io.IOException;

import org.joda.time.DateTime;

/**
 *
 *
 */
public class JodaDateTimeDeserializer extends AbstractDeserializer
{

    @Override
    public Object readObject(AbstractHessianInput in) throws IOException
    {
        long millis = in.readUTCDate();
        return new DateTime(millis);
    }
}
