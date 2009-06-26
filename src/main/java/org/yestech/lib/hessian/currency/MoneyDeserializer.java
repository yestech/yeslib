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

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Locale;

/**
 *
 *
 */
public class MoneyDeserializer extends AbstractDeserializer {

    @Override
    public Object readObject(AbstractHessianInput in) throws IOException {
        BigDecimal amount = (BigDecimal) in.readObject();
        Locale locale = (Locale) in.readObject();
        return new Money(amount, locale);
    }
}