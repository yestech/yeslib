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
package org.yestech.lib.crypto;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class PasswordGeneratorUnitTest {
    /*
      * Class under test for String getNext()
      */
    @Test
    public void testCreatePassword() {
        String s = PasswordGenerator.createPassword();
        System.out.println(s);
        assertNotNull(s);
        assertEquals(ICryptoConstants.MIN_LENGTH, s.length());
    }

    /*
      * Class under test for String getNext(int)
      */
    @Test
    public void testCreatePasswordWithLength() {
        final int[] lengths = {1, 5, 20, 200};
        for (int i = 0; i < lengths.length; i++) {
            int j = lengths[i];
            String s = PasswordGenerator.createPassword(j);
            System.out.println(s);
            assertNotNull(s);
            assertEquals(j, s.length());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreatePasswordZero() {
        PasswordGenerator.createPassword(0);
    }
}
