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
import static org.junit.Assert.assertNull;
import org.junit.Test;

import java.io.*;

/**
 * @author $Author: $
 * @version $Revision: $
 */
public class MessageDigestUtilsUnitTest {

    @Test
    public void testMD5HashFile() throws IOException {
        File tempTxt = null;

        try {
            final String tempDir = System.getProperty("java.io.tmpdir");
            tempTxt = new File(tempDir + File.separator + "md5Test.test");
            FileWriter out = new FileWriter(tempTxt);
            out.write("testing");
            out.flush();
            out.close();
            final String md5Sum = MessageDigestUtils.md5Hash(tempTxt);
            assertEquals("ae2b1fca515949e5d54fb22b8ed95575", md5Sum);
            assertEquals(32, md5Sum.length());
        } finally {
            if (tempTxt != null) {
                tempTxt.delete();
            }
        }
    }

    @Test
    public void testMD5HashNullFile() throws IOException {
        assertNull(MessageDigestUtils.md5Hash((File) null));
    }

    @Test
    public void testMD5HashStream() throws IOException {
        String testString = "This is a test string";
        ByteArrayInputStream stream = new ByteArrayInputStream(testString.getBytes());
        final String hash = MessageDigestUtils.md5Hash(stream);
        assertEquals("c639efc1e98762233743a75e7798dd9c", hash);
        assertEquals(32, hash.length());
    }

    @Test
    public void testMD5HashNullStream() throws IOException {
        assertNull(MessageDigestUtils.md5Hash((InputStream) null));
    }

    @Test
    public void testMD5HashString() {
        String hash = MessageDigestUtils.md5Hash("this is a test md5");
        assertNotNull(hash);
        assertEquals("8ad9c65947107a6bca12f19eb2145348", hash);
        assertEquals(32, hash.length());
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
        assertEquals(32, hash.length());
    }

    @Test
    public void testSHA1HashString() {
        String hash = MessageDigestUtils.sha1Hash("this is a test sha1");
        assertNotNull(hash);
        assertEquals("632e1dee7f892a531ad68e3db62440f24075d87a", hash);
        assertEquals(40, hash.length());
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
        assertEquals(40, hash.length());
    }

    @Test
    public void testSHA1HashFile() throws IOException {
        File tempTxt = null;

        try {
            final String tempDir = System.getProperty("java.io.tmpdir");
            tempTxt = new File(tempDir + File.separator + "sha1Test.test");
            FileWriter out = new FileWriter(tempTxt);
            out.write("testingSha1");
            out.flush();
            out.close();
            final String sha1Sum = MessageDigestUtils.sha1Hash(tempTxt);
            assertEquals("8063f7b893b16b873a5735a4f1fd71231d3f3093", sha1Sum);
            assertEquals(40, sha1Sum.length());
        } finally {
            if (tempTxt != null) {
                tempTxt.delete();
            }
        }
    }

    @Test
    public void testSHA1HashNullFile() throws IOException {
        assertNull(MessageDigestUtils.sha1Hash((File) null));
    }

    @Test
    public void testSHA1HashStream() throws IOException {
        String testString = "This is a test string";
        ByteArrayInputStream stream = new ByteArrayInputStream(testString.getBytes());
        final String sha1Sum = MessageDigestUtils.sha1Hash(stream);
        assertEquals("e2f67c772368acdeee6a2242c535c6cc28d8e0ed", sha1Sum);
        assertEquals(40, sha1Sum.length());
    }

    @Test
    public void testSHA1HashNullStream() throws IOException {
        assertNull(MessageDigestUtils.sha1Hash((InputStream) null));
    }

}