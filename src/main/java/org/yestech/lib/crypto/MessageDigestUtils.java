/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.crypto;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


/**
 * Class that implements Message Digest Algorithmns
 */
public class MessageDigestUtils {
    final private static Logger logger = LoggerFactory.getLogger(MessageDigestUtils.class);

    final private static Hex hexCodec = new Hex();

    private MessageDigestUtils() {
    }

    /**
     * Take a string and return its md5 MD5hash as a 32 hex digit string
     *
     * @param arg
     * @return the MD5
     */
    public static String md5Hash(String arg) {
        if (arg != null) {
            return DigestUtils.md5Hex(arg);
        } else {
            return "";
        }
    }

    /**
     * Take a byte array and return its md5 MD5hash as a 32 hex digit string
     *
     * @param barray
     * @return the MD5
     */
    public static String md5Hash(byte barray[]) {
        return DigestUtils.md5Hex(barray);
    }

    public static String sha1Hash(String arg) {
        if (arg != null) {
            return DigestUtils.shaHex(arg);
        } else {
            return "";
        }
    }

    public static String sha1Hash(byte barray[]) {
        return DigestUtils.shaHex(barray);
    }
}