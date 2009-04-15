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
package org.yestech.lib.util;

import java.util.*;

/**
 * Utility class stolen from CXF that use's a method mentioned
 * by Josh Block on changing collections from one type to another.
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
@SuppressWarnings({"unchecked", "unused"})
public final class CastUtil {

    private CastUtil() {
    }

    public static <T, U> Map<T, U> cast(Map<?, ?> p) {
        return (Map<T, U>) p;
    }

    public static <T, U> Map<T, U> cast(Map<?, ?> p, Class<T> t,
                                        Class<U> u) {
        return (Map<T, U>) p;
    }

    public static <T> Collection<T> cast(Collection<?> p) {
        return (Collection<T>) p;
    }

    public static <T> Collection<T> cast(Collection<?> p, Class<T> cls) {
        return (Collection<T>) p;
    }

    public static <T> List<T> cast(List<?> p) {
        return (List<T>) p;
    }

    public static <T> List<T> cast(List<?> p, Class<T> cls) {
        return (List<T>) p;
    }

    public static <T> Iterator<T> cast(Iterator<?> p) {
        return (Iterator<T>) p;
    }

    public static <T> Iterator<T> cast(Iterator<?> p, Class<T> cls) {
        return (Iterator<T>) p;
    }

    public static <T> Set<T> cast(Set<?> p) {
        return (Set<T>) p;
    }

    public static <T> Set<T> cast(Set<?> p, Class<T> cls) {
        return (Set<T>) p;
    }

    public static <T, U> Map.Entry<T, U> cast(Map.Entry<?, ?> p) {
        return (Map.Entry<T, U>) p;
    }

    public static <T, U> Map.Entry<T, U> cast(Map.Entry<?, ?> p,
                                              Class<T> pc, Class<U> uc) {
        return (Map.Entry<T, U>) p;
    }
}