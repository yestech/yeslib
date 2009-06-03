package org.yestech.lib.hessian.hibernate;

import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.CollectionSerializer;
import com.caucho.hessian.io.Serializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.hibernate.Hibernate;

/**
 *
 */
public class HibernateListSerializer implements Serializer {

    private CollectionSerializer delegate = new CollectionSerializer();

    @Override
    public void writeObject(Object obj, AbstractHessianOutput out)
            throws IOException {
        if (Hibernate.isInitialized(obj)) {
            delegate.writeObject(new ArrayList((Collection) obj), out);
        } else {
            delegate.writeObject(new ArrayList(), out);
        }
    }
}
