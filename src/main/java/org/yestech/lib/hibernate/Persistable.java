/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.hibernate;

import org.joda.time.DateTime;

import java.io.Serializable;

/**
 *
 *
 */
public interface Persistable extends Serializable
{

    Long getIdentifier();

    DateTime getCreated();

    DateTime getModified();
    

}
