package org.yestech.lib.hessian.joda;

import com.caucho.hessian.io.AbstractSerializer;
import com.caucho.hessian.io.AbstractHessianOutput;

import java.io.IOException;

import org.joda.time.DateTime;

/**
 * Serializes Joda DateTime objects correctly.
 *
 * @author A.J. Wright
 */
public class JodaDateTimeSerializer extends AbstractSerializer
{

    @Override
    public void writeObject(Object obj, AbstractHessianOutput out) throws IOException
    {

        if (obj == null) {
            out.writeNull();
        }
        else {
            DateTime dt = (DateTime) obj;
            out.writeUTCDate(dt.getMillis());
        }
        
    }
}
