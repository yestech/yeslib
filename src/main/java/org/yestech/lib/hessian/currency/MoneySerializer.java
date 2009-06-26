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

/**
 * Serializes Money objects correctly.
 *
 * @author A.J. Wright
 */
public class MoneySerializer extends AbstractSerializer
{

    @Override
    public void writeObject(Object obj, AbstractHessianOutput out) throws IOException
    {

        if (obj == null) {
            out.writeNull();
        }
        else {
            Money money = (Money) obj;
            out.writeObject(money.getAmount());
            out.writeObject(money.getLocale());
        }

    }
}