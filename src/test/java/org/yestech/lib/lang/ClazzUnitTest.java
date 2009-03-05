/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

/*
 *
 * Original Author:  Artie Copeland
 * Last Modified Date: $DateTime: $
 */
package org.yestech.lib.lang;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author $Author: $
 * @version $Revision: $
 */
public class ClazzUnitTest {
    @Test
    public void testInstantiateClassFromClass() {
        List list = Clazz.instantiateClass(ArrayList.class);
        assertNotNull(list);
    }

    @Test
    public void testInstantiateClassFromString() {
        Map map = Clazz.instantiateClass("java.util.HashMap");
        assertNotNull(map);
    }
}
