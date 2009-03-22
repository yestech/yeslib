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
import static org.junit.Assert.assertEquals;
import org.jmock.Mockery;

import java.math.BigDecimal;
import java.util.Locale;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class CurrencyUtilsUnitTest {
    private Mockery context = new Mockery();

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
        IMoney money = new Money(new BigDecimal("2"));
        IMoney resultMoney = CurrencyUtils.add(new Money(BigDecimal.ONE), new Money(BigDecimal.ONE));
        assertEquals(money, resultMoney);
    }
    @Test
    public void testSubtrction() {
        IMoney resultMoney = CurrencyUtils.subtract(new Money(BigDecimal.ONE), new Money(BigDecimal.ONE));
        assertEquals(new Money(BigDecimal.ZERO), resultMoney);
    }
    @Test
    public void testMultiply() {
        IMoney resultMoney = CurrencyUtils.multiple(new Money(BigDecimal.TEN), new Money(BigDecimal.ONE));
        assertEquals(new Money(BigDecimal.TEN), resultMoney);
    }

    @Test
    public void testDivide() {
        IMoney money = new Money(new BigDecimal("1"));
        IMoney resultMoney = CurrencyUtils.divide(new Money(BigDecimal.ONE), new Money(BigDecimal.ONE));
        assertEquals(money, resultMoney);
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
        IMoney money = new Money(new BigDecimal("0.85"));
        IMoney resultMoney = CurrencyUtils.divide(new Money(new BigDecimal("1.70")), new Money(new BigDecimal("2.00")), BigDecimal.ROUND_UP);
        assertEquals(money, resultMoney);
    }

}
