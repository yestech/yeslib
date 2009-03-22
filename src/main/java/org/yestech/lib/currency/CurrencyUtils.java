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

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class CurrencyUtils {
    public static Money add(Money leftSide, Money rightSide) {
        Money addition = null;
        validationCheck(leftSide, rightSide);
        addition = new Money(leftSide.getAmount().add(rightSide.getAmount()), leftSide.getLocale());
        return addition;
    }

    public static Money subtract(Money leftSide, Money rightSide) {
        Money addition = null;
        validationCheck(leftSide, rightSide);
        addition = new Money(leftSide.getAmount().subtract(rightSide.getAmount()), leftSide.getLocale());
        return addition;
    }

    public static Money divide(Money leftSide, Money rightSide) {
        Money addition = null;
        validationCheck(leftSide, rightSide);
        divideByZeroCheck(rightSide);
        addition = new Money(leftSide.getAmount().divide(rightSide.getAmount()), leftSide.getLocale());
        return addition;
    }

    public static Money divide(Money leftSide, Money rightSide, int roundMode) {
        Money addition = null;
        validationCheck(leftSide, rightSide);
        divideByZeroCheck(rightSide);
        addition = new Money(leftSide.getAmount().divide(rightSide.getAmount(), roundMode), leftSide.getLocale());
        return addition;
    }

    private static void divideByZeroCheck(Money rightSide) {
        if (rightSide.getAmount().equals(BigDecimal.ZERO)) {
            throw new CurrencyException("cant divide by zero");
        }
    }

    public static Money multiple(Money leftSide, Money rightSide) {
        Money addition = null;
        validationCheck(leftSide, rightSide);
        addition = new Money(leftSide.getAmount().multiply(rightSide.getAmount()), leftSide.getLocale());
        return addition;
    }

    private static void validationCheck(Money leftSide, Money rightSide) {
        if (leftSide == null || rightSide == null) {
            throw new CurrencyException("cant add null value");
        }
        if (!leftSide.getCurreny().equals(rightSide.getCurreny())) {
            throw new CurrencyException("cant add two values that dont have the same currency");
        }
    }
}
