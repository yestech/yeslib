/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.hessian.currency;

import com.caucho.hessian.io.AbstractHessianOutput;
import com.caucho.hessian.io.AbstractSerializer;
import org.yestech.lib.currency.Money;

import java.io.IOException;
import java.util.Locale;

/**
 * Serializes Money objects correctly.
 *
 * @author A.J. Wright
 */
public class MoneySerializer extends AbstractSerializer {

    @Override
    public void writeObject(Object obj, AbstractHessianOutput out) throws IOException {

        Class cl = obj.getClass();

        if (out.addRef(obj))
            return;

        int ref = out.writeObjectBegin(cl.getName());

        Money money = (Money) obj;

        if (ref < -1) {
            out.writeString("value");
            out.writeString(money.toString());
            out.writeString("locale");
            final Locale locale = money.getLocale();
            out.writeString(locale.getLanguage() + ":" + locale.getCountry());
            out.writeMapEnd();
        } else {
            if (ref == -1) {
                out.writeInt(1);
                out.writeString("value");
                out.writeObjectBegin(cl.getName());
            }

            out.writeString(money.toString());
        }
    }
}