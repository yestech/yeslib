/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.hessian.jdk;

import com.caucho.hessian.io.AbstractDeserializer;
import com.caucho.hessian.io.AbstractHessianInput;

import java.io.IOException;
import java.util.UUID;

/**
 *
 *
 */
public class UUIDDeserializer extends AbstractDeserializer {

    @Override
    public Class getType() {
        return UUID.class;
    }

    @Override
    public Object readMap(AbstractHessianInput in)
            throws IOException {
        int ref = in.addRef(null);

        String uuidValue = null;

        while (!in.isEnd()) {
            String key = in.readString();

            if (key.equals("value"))
                uuidValue = in.readString();
            else
                in.readString();
        }

        in.readMapEnd();

        Object value = UUID.fromString(uuidValue);

        in.setRef(ref, value);

        return value;
    }

    @Override
    public Object readObject(AbstractHessianInput in, String[] fieldNames)
            throws IOException {
        int ref = in.addRef(null);

        String uuidValue = null;

        for (String key : fieldNames) {
            if (key.equals("value"))
                uuidValue = in.readString();
            else
                in.readObject();
        }

        Object value = UUID.fromString(uuidValue);

        in.setRef(ref, value);

        return value;
    }
}