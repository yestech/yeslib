/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

/*
 *
 * Author:  Artie Copeland
 * Last Modified Date: $DateTime: $
 */
package org.yestech.lib.currency;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Currency;

/**
 * Default Implementation for an {@link IMoney}. By default it assumes {@link Locale#US} and
 * the corresponding Currency.
 * 
 * @author Artie Copeland
 * @version $Revision: $
 */
public class Money implements IMoney {
    private BigDecimal amount = BigDecimal.ZERO;
    private Locale locale;
    private Currency curreny;

    public Money(BigDecimal amount) {
        this(amount, Locale.US);
    }

    public Money(BigDecimal amount, Locale locale) {
        if (amount == null || amount.doubleValue() < BigDecimal.ZERO.doubleValue()) {
            throw new CurrencyException("can't have a null of negative money amount");
        }
        if (locale == null) {
            throw new CurrencyException("can't have a null locale");            
        }
        this.amount = amount;
        this.locale = locale;
        this.curreny = Currency.getInstance(locale);
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurreny() {
        return curreny;
    }

    public Locale getLocale() {
        return locale;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;

        Money money = (Money) o;

        if (amount != null ? !amount.equals(money.amount) : money.amount != null) return false;
        if (curreny != null ? !curreny.equals(money.curreny) : money.curreny != null) return false;
        if (locale != null ? !locale.equals(money.locale) : money.locale != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = amount != null ? amount.hashCode() : 0;
        result = 31 * result + (locale != null ? locale.hashCode() : 0);
        result = 31 * result + (curreny != null ? curreny.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Money{" +
                "amount=" + amount +
                ", locale=" + locale +
                ", curreny=" + curreny +
                '}';
    }

    @Override
    public int compareTo(IMoney comparable) {
        return getAmount().compareTo(comparable.getAmount());
    }
}
