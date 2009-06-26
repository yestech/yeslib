/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.hessian.currency;

import com.caucho.hessian.io.*;
import org.joda.time.DateTime;
import org.yestech.lib.currency.Money;

/**
 *
 *
 */
public class MoneySerializerFactory extends AbstractSerializerFactory
{
    private MoneySerializer moneySerializer = new MoneySerializer();
    private MoneyDeserializer moneyDeserializer = new MoneyDeserializer();

    @Override
    public Serializer getSerializer(Class cl) throws HessianProtocolException
    {
        if (Money.class.isAssignableFrom(cl)) {
            return moneySerializer;
        }
        return null;
    }

    @Override
    public Deserializer getDeserializer(Class cl) throws HessianProtocolException
    {
        if (Money.class.isAssignableFrom(cl)) {
            return moneyDeserializer;
        }
        return null;
    }
}