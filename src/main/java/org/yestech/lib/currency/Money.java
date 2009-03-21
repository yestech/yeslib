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

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class Money implements IMoney {
    private BigDecimal value = BigDecimal.ZERO;
    private Locale currency = Locale.US;

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        if (value != null && value.doubleValue() > -1 )
        this.value = value;
    }

    public Locale getCurrency() {
        return currency;
    }

    public void setCurrency(Locale currency) {
        this.currency = currency;
    }

}
