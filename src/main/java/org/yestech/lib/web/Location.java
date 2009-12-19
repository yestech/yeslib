/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.web;

import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation to determine the Location to forward request.
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Location {
    /**
     * Path to the location to forward
     *
     * @return the Location
     */
    String value();

    /**
     * Holds the key to set the model to
     *
     * @return The key
     */
    String modelKey() default "__yestech_location_key__";
}
