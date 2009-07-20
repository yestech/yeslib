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

import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.apache.commons.lang.StringUtils;

/**
 * @author $Author: $
 * @version $Revision: $
 */
public class AesCryptoUtilsUnitTest {
    @Test
    public void testGenerateKey()
    {
        String key = AesCryptoUtils.generateKey();
        assertNotNull(key);
        assertTrue("Make sure we dont have an empty key", StringUtils.isNotBlank(key));
    }

    @Test
    public void testEncryptWithManualKey() throws Exception
    {
        String encrypt = AesCryptoUtils.encrypt("NzwxkjkRhMGmbJjN0Bqz2Q==",
                "This is a secret");
        assertNotNull(encrypt);
        assertTrue(StringUtils.isNotBlank(encrypt));
        assertEquals("338e67055b17204a993494a16f7c1d1f916b619c14f915d2c82e2aa2bc7d45db", encrypt);
        assertEquals(64, encrypt.length());
    }

    @Test
    public void testDecryptWithManualKey() throws Exception
    {
        String decrypt = AesCryptoUtils.decrypt("NzwxkjkRhMGmbJjN0Bqz2Q==",
        "338e67055b17204a993494a16f7c1d1f916b619c14f915d2c82e2aa2bc7d45db");
        assertNotNull(decrypt);
        assertTrue(StringUtils.isNotBlank(decrypt));
        assertEquals("This is a secret", decrypt);
    }

    @Test
    public void testEncryptBase64WithManualKey() throws Exception
    {
        String encrypt = AesCryptoUtils.encryptBase64("NzwxkjkRhMGmbJjN0Bqz2Q==",
        "This is top secret");
        assertNotNull(encrypt);
        assertTrue(StringUtils.isNotBlank(encrypt));
        assertEquals("kiJvCxdi2BWPpY+uxTlzAiGYpFx+kc4i2pXT15l6oS8=", encrypt);
        assertEquals(44, encrypt.length());
    }

    @Test
    public void testDecryptBase64WithManualKey() throws Exception
    {
        String decrypt = AesCryptoUtils.decryptBase64("NzwxkjkRhMGmbJjN0Bqz2Q==",
        "kiJvCxdi2BWPpY+uxTlzAiGYpFx+kc4i2pXT15l6oS8=");
        assertNotNull(decrypt);
        assertTrue(StringUtils.isNotBlank(decrypt));
        assertEquals("This is top secret", decrypt);
    }}
