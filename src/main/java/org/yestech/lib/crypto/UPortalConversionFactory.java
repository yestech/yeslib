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

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Date;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class UPortalConversionFactory implements IPasswordConversionFactory {

    /**
     * Encodes a password that adheres to UPortal.
     *
     * @param password
     * @return The password
     */
    @Override
    public String transform(String password, Object... params) {
        byte[] rnd = new byte[8];
        byte[] fin = new byte[24];
        SecureRandom r;
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            Long date = new Long((new Date()).getTime());
            r = new SecureRandom((date.toString()).getBytes());
        } catch (Exception e) {
            throw new RuntimeException("Error getting instance...", e);
        }
        r.nextBytes(rnd);
        md.update(rnd);
        byte[] hash = md.digest(password.getBytes());
        System.arraycopy(rnd, 0, fin, 0, 8);
        System.arraycopy(hash, 0, fin, 8, 16);
        return "(MD5)" + encode(fin);
    }

    //
    //  This was originally Jonathan B. Knudsen's Example from his book
    //  Java Cryptography published by O'Reilly Associates (1st Edition 1998)
    //
    private static String encode(byte[] raw) {
        StringBuffer encoded = new StringBuffer();
        for (int i = 0; i < raw.length; i += 3) {
            encoded.append(encodeBlock(raw, i));
        }
        return encoded.toString();
    }

    private static char[] encodeBlock(byte[] raw, int offset) {
        int block = 0;
        int slack = raw.length - offset - 1;
        int end = (slack >= 2) ? 2 : slack;
        for (int i = 0; i <= end; i++) {
            byte b = raw[offset + i];
            int neuter = (b < 0) ? b + 256 : b;
            block += neuter << (8 * (2 - i));
        }
        char[] base64 = new char[4];
        for (int i = 0; i < 4; i++) {
            int sixbit = (block >>> (6 * (3 - i))) & 0x3f;
            base64[i] = getChar(sixbit);
        }
        if (slack < 1) base64[2] = '=';
        if (slack < 2) base64[3] = '=';
        return base64;
    }

    private static char getChar(int sixBit) {
        if (sixBit >= 0 && sixBit <= 25)
            return (char) ('A' + sixBit);
        if (sixBit >= 26 && sixBit <= 51)
            return (char) ('a' + (sixBit - 26));
        if (sixBit >= 52 && sixBit <= 61)
            return (char) ('0' + (sixBit - 52));
        if (sixBit == 62) return '+';
        if (sixBit == 63) return '/';
        return '?';
    }
}