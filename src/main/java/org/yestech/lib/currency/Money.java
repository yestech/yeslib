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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Locale;

/**
 * Represents an immutable monetary value. By default it assumes {@link Locale#US} and
 * the corresponding Currency.
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
public class Money implements Serializable, Comparable<Money> {
    private BigDecimal amount = BigDecimal.ZERO;
    private Locale locale;
    private Currency curreny;

    public Money(double amount) {
        this(amount, Locale.US);
    }

    public Money(double amount, Locale locale) {
        if (amount < 0) {
            throw new CurrencyException("money amount must be a positive number");
        }
        if (locale == null) {
            throw new CurrencyException("can't have a null locale");
        }
        this.amount = new BigDecimal(amount);
        this.locale = locale;
        this.curreny = Currency.getInstance(locale);
    }

    public Money(String amount) {
        this(amount, Locale.US);
    }

    public Money(String amount, Locale locale) {
        BigDecimal tmpAmount;
        if (StringUtils.isBlank(amount) || !NumberUtils.isNumber(amount)) {
            throw new CurrencyException("money amount must be a positive number");
        } else {
            tmpAmount = new BigDecimal(amount);
            validateAmount(tmpAmount);
        }
        if (locale == null) {
            throw new CurrencyException("can't have a null locale");
        }
        this.amount = tmpAmount;
        this.locale = locale;
        this.curreny = Currency.getInstance(locale);
    }

    public Money(BigDecimal amount) {
        this(amount, Locale.US);
    }

    public Money(BigDecimal amount, Locale locale) {
        validateAmount(amount);
        if (locale == null) {
            throw new CurrencyException("can't have a null locale");
        }
        this.amount = amount;
        this.locale = locale;
        this.curreny = Currency.getInstance(locale);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.doubleValue() < BigDecimal.ZERO.doubleValue()) {
            throw new CurrencyException("can't have a null of negative money amount");
        }
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
    public int compareTo(Money comparable) {
        return getAmount().compareTo(comparable.getAmount());
    }
}
