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

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import static com.google.common.collect.Lists.newArrayList;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class TupleFactoryUnitTest {
    @Test
    public void testCreatePair() {
        String first = "1";
        int second = -100;
        Pair<String, Integer> pair = TupleFactory.create(first, second);
        assertNotNull(pair);
        assertEquals(first, pair.getFirst());
        assertEquals((int)second, (int)pair.getSecond());
    }

    @Test
    public void testCreateTriple() {
        long third = System.currentTimeMillis();
        BigDecimal second = new BigDecimal("10000");
        double first = 1.0;
        Triple<Double, BigDecimal, Long> triple = TupleFactory.create(first, second, third);
        assertEquals(first, triple.getFirst(), 0);
        assertEquals(second, triple.getSecond());
        assertEquals((long)third, (long)triple.getThird());
    }

    @Test
    public void testCreateQuadruple() {
        String first = "first";
        ArrayList<String> second = newArrayList("second");
        Class<String> third = String.class;
        boolean fourth = false;
        Quadruple<String, ArrayList<String>, Class<String>, Boolean> quadruple = TupleFactory.create(first,
                second, third, fourth);
        assertEquals(first, quadruple.getFirst());
        assertEquals(second, quadruple.getSecond());
        assertEquals(third, quadruple.getThird());
        assertEquals(fourth, quadruple.getFourth());

    }
}
