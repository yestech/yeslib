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
package org.yestech.lib.crypto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

/**
 * @author $Author: $
 * @version $Revision: $
 */
public class MessageDigestUtilsUnitTest {
    @Test
    public void testMD5HashString() {
        String hash = MessageDigestUtils.md5Hash("this is a test md5");
        assertNotNull(hash);
        assertEquals("8ad9c65947107a6bca12f19eb2145348", hash);
    }

    @Test
    public void testMD5HashNull() {
        String hash = MessageDigestUtils.md5Hash((String) null);
        assertNotNull(hash);
        assertEquals("", hash);
    }

    @Test
    public void testMD5HashByteArray() {
        String hash = MessageDigestUtils.md5Hash("testing byte array md5".getBytes());
        assertNotNull(hash);
        assertEquals("1fd0368b4be0248d486c3d1bd91cb647", hash);
    }

    @Test
    public void testSHA1HashString() {
        String hash = MessageDigestUtils.sha1Hash("this is a test sha1");
        assertNotNull(hash);
        assertEquals("632e1dee7f892a531ad68e3db62440f24075d87a", hash);
    }

    @Test
    public void testSHA1HashNull() {
        String hash = MessageDigestUtils.sha1Hash((String) null);
        assertNotNull(hash);
        assertEquals("", hash);
    }

    @Test
    public void testSHA1HashByteArray() {
        String hash = MessageDigestUtils.sha1Hash("testing byte array sha1".getBytes());
        assertNotNull(hash);
        assertEquals("e555604264e40a8359f0996384d02de3db8adc38", hash);
    }

}