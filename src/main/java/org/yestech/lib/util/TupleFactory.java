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

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
final public class TupleFactory {
    private TupleFactory() {

    }

    public static <T1, T2> Pair<T1, T2> createPair(T1 first, T2 second) {
        return new Pair<T1, T2>(first, second);
    }
    public static <T1, T2, T3> Triple<T1, T2, T3> createTriple(T1 first, T2 second, T3 third) {
        return new Triple<T1, T2, T3>(first, second, third);
    }
    public static <T1, T2, T3, T4> Quadruple<T1, T2, T3, T4> createQuadruple(T1 first, T2 second, T3 third, T4 fourth) {
        return new Quadruple<T1, T2, T3, T4>(first, second, third, fourth);
    }
}
