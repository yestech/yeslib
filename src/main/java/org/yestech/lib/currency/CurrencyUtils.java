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
    public static IMoney add(IMoney leftSide, IMoney rightSide) {
        IMoney addition = null;
        validationCheck(leftSide, rightSide);
        addition = new Money(leftSide.getAmount().add(rightSide.getAmount()), leftSide.getLocale());
        return addition;
    }

    public static IMoney subtract(IMoney leftSide, IMoney rightSide) {
        IMoney addition = null;
        validationCheck(leftSide, rightSide);
        addition = new Money(leftSide.getAmount().subtract(rightSide.getAmount()), leftSide.getLocale());
        return addition;
    }

    public static IMoney divide(IMoney leftSide, IMoney rightSide) {
        IMoney addition = null;
        validationCheck(leftSide, rightSide);
        divideByZeroCheck(rightSide);
        addition = new Money(leftSide.getAmount().divide(rightSide.getAmount()), leftSide.getLocale());
        return addition;
    }

    public static IMoney divide(IMoney leftSide, IMoney rightSide, int roundMode) {
        IMoney addition = null;
        validationCheck(leftSide, rightSide);
        divideByZeroCheck(rightSide);
        addition = new Money(leftSide.getAmount().divide(rightSide.getAmount(), roundMode), leftSide.getLocale());
        return addition;
    }

    private static void divideByZeroCheck(IMoney rightSide) {
        if (rightSide.getAmount().equals(BigDecimal.ZERO)) {
            throw new CurrencyException("cant divide by zero");
        }
    }

    public static IMoney multiple(IMoney leftSide, IMoney rightSide) {
        IMoney addition = null;
        validationCheck(leftSide, rightSide);
        addition = new Money(leftSide.getAmount().multiply(rightSide.getAmount()), leftSide.getLocale());
        return addition;
    }

    private static void validationCheck(IMoney leftSide, IMoney rightSide) {
        if (leftSide == null || rightSide == null) {
            throw new CurrencyException("cant add null value");
        }
        if (!leftSide.getCurreny().equals(rightSide.getCurreny())) {
            throw new CurrencyException("cant add two values that dont have the same currency");
        }
    }
}
