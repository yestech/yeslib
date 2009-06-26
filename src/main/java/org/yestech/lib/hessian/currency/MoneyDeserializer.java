/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.hessian.currency;

import com.caucho.hessian.io.AbstractDeserializer;
import com.caucho.hessian.io.AbstractHessianInput;
import org.yestech.lib.currency.Money;
import org.joda.time.DateTime;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;

/**
 *
 *
 */
public class MoneyDeserializer extends AbstractDeserializer {

    @Override
    public Object readObject(AbstractHessianInput in) throws IOException
    {
        BigDecimal amount = (BigDecimal) in.readObject();
        Locale locale = (Locale) in.readObject();
        return new Money(amount, locale);
    }

//    @Override
//    public Class getType() {
//        return BigDecimal.class;
//    }
//
//    @Override
//    public Object readMap(AbstractHessianInput in)
//            throws IOException {
//        int ref = in.addRef(null);
//
//        String initValue = null;
//
//        while (!in.isEnd()) {
//            String key = in.readString();
//
//            if (key.equals("value"))
//                initValue = in.readString();
//            else
//                in.readString();
//        }
//
//        in.readMapEnd();
//
//        Object value = new BigDecimal(initValue);
//
//        in.setRef(ref, value);
//
//        return value;
//    }
//
//    @Override
//    public Object readObject(AbstractHessianInput in, String[] fieldNames)
//            throws IOException {
//        int ref = in.addRef(null);
//
//        String initValue = null;
//
//        for (String key : fieldNames) {
//            if (key.equals("value"))
//                initValue = in.readString();
//            else
//                in.readObject();
//        }
//
//        Object value = new BigDecimal(initValue);
//
//        in.setRef(ref, value);
//
//        return value;
//    }
//
//    @Override
//    public Object readObject(AbstractHessianInput in) throws IOException {
//        BigDecimal amount = (BigDecimal) in.readObject();
//        Locale locale = (Locale) in.readObject();
//        return new Money(amount, locale);
//    }
}