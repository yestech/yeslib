package org.yestech.lib.hessian.spring;

import com.caucho.hessian.io.AbstractSerializerFactory;
import com.caucho.hessian.io.SerializerFactory;

import java.util.List;

/**
 * A friendly SerializerFactory for spring.
 *
 *
 * @author A.J. Wright
 */
public class SpringExtensibleSerializerFactory extends SerializerFactory
{

    public void setSerializerFactories(List<AbstractSerializerFactory> factories)
    {
        for (AbstractSerializerFactory factory : factories)
        {
            addFactory(factory);
        }
    }

}
