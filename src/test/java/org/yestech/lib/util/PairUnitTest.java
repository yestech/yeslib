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

import java.util.ArrayList;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class PairUnitTest {

    @Test
    public void testCreate() {
        String first = "first";
        ArrayList<Integer> second = new ArrayList<Integer>();
        Pair<String, ArrayList<Integer>> arrayListPair = Pair.create(first, second);
        assertNotNull(arrayListPair);
        assertEquals(first, arrayListPair.getFirst());
        assertEquals(second, arrayListPair.getSecond());
    }
}
