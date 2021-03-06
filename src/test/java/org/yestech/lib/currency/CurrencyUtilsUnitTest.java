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

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class CurrencyUtilsUnitTest {

    @Test(expected = CurrencyException.class)
    public void testAddNullLeftSide() {
        CurrencyUtils.add(null, new Money(BigDecimal.ONE));
    }

    @Test(expected = CurrencyException.class)
    public void testAddNullRightSide() {
        CurrencyUtils.add(new Money(BigDecimal.ONE), null);
    }

    @Test(expected = CurrencyException.class)
    public void testAddMismatchCurrency() {
        CurrencyUtils.add(new Money(BigDecimal.ONE), new Money(BigDecimal.ONE, Locale.UK));
    }

    @Test
    public void testAddition() {
        Money money = new Money(new BigDecimal("2"));
        Money resultMoney = CurrencyUtils.add(new Money(BigDecimal.ONE), new Money(BigDecimal.ONE));
        assertEquals(money, resultMoney);
        assertEquals("$", resultMoney.getCurreny().getSymbol());
    }
    @Test
    public void testSubtrction() {
        Money resultMoney = CurrencyUtils.subtract(new Money(BigDecimal.ONE), new Money(BigDecimal.ONE));
        assertEquals(new Money(BigDecimal.ZERO), resultMoney);
        assertEquals("$", resultMoney.getCurreny().getSymbol());
    }
    @Test
    public void testMultiply() {
        Money resultMoney = CurrencyUtils.multiple(new Money(BigDecimal.TEN), new Money(BigDecimal.ONE));
        assertEquals(new Money(BigDecimal.TEN), resultMoney);
        assertEquals("$", resultMoney.getCurreny().getSymbol());
    }

    @Test
    public void testDivide() {
        Money money = new Money(new BigDecimal("1"));
        Money resultMoney = CurrencyUtils.divide(new Money(BigDecimal.ONE), new Money(BigDecimal.ONE));
        assertEquals(money, resultMoney);
        assertEquals("$", resultMoney.getCurreny().getSymbol());
    }

    @Test(expected = CurrencyException.class)
    public void testDivideByZero() {
        CurrencyUtils.divide(new Money(BigDecimal.ONE), new Money(BigDecimal.ZERO));
    }

    @Test(expected = CurrencyException.class)
    public void testDivideByZeroWithRound() {
        CurrencyUtils.divide(new Money(BigDecimal.ONE), new Money(BigDecimal.ZERO), BigDecimal.ROUND_UP);
    }

    @Test
    public void testDivideWithRound() {
        Money money = new Money(new BigDecimal("0.85"));
        Money resultMoney = CurrencyUtils.divide(new Money(new BigDecimal("1.70")), new Money(new BigDecimal("2.00")), BigDecimal.ROUND_UP);
        assertEquals(money, resultMoney);
        assertEquals("$", resultMoney.getCurreny().getSymbol());
    }

}
