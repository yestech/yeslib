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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Locale;
import java.util.Currency;

/**
 * Represents an immutable monetary value.
 * 
 * @author Artie Copeland
 * @version $Revision: $
 */
public interface IMoney extends Serializable, Comparable<IMoney> {
    BigDecimal getAmount();

    Currency getCurreny();

    Locale getLocale();
}
