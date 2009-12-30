/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.util;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

/**
 *
 *
 */
public class HashAlgorithmUnitTest {
    @Before
    public void setUp() {
        // Add your code here
    }

    @Test
    public void testCrcStringKey() {
        long hash = HashAlgorithm.CRC32_HASH.hash("this is my key");
        assertEquals(25351l, hash);
    }

    @Test
    public void testNativeStringKey() {
        long hash = HashAlgorithm.NATIVE_HASH.hash("this is my key");
        assertEquals(2839207583l, hash);
    }

    @Test
    public void testKetamaStringKey() {
        long hash = HashAlgorithm.KETAMA_HASH.hash("this is my key");
        assertEquals(104957886l, hash);
    }

    @Test
    public void testFnv1StringKey() {
        long hash = HashAlgorithm.FNV1_32_HASH.hash("this is my key");
        assertEquals(4141078290l, hash);
    }

    @Test
    public void testFnv1_64StringKey() {
        long hash = HashAlgorithm.FNV1_64_HASH.hash("this is my key");
        assertEquals(2873423154l, hash);
    }

    @Test
    public void testFnv1a_32StringKey() {
        long hash = HashAlgorithm.FNV1A_32_HASH.hash("this is my key");
        assertEquals(326024864l, hash);
    }

    @Test
    public void testFnv1a_64StringKey() {
        long hash = HashAlgorithm.FNV1A_64_HASH.hash("this is my key");
        assertEquals(797163744l, hash);
    }
}