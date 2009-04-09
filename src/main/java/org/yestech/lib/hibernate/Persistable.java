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
