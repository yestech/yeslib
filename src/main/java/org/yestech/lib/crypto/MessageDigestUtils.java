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
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


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
     * Take a file  and return its md5 MD5hash as a 32 hex digit string. If the file is null then a null hash is returned.
     *
     * @param file
     * @return the MD5
     */
    public static String md5Hash(File file) {
        if (file == null) {
            return null;
        }
        try {
            return md5Hash(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Take an InputStream and return its md5 MD5hash as a 32 hex digit string. If the stream is null then a null hash is returned.
     *
     * @param stream
     * @return the MD5
     */
    public static String md5Hash(InputStream stream) {
        if (stream == null) {
            return null;
        }
        BufferedInputStream bis = new BufferedInputStream(stream);
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[16384];
            int bytesRead = -1;
            while ((bytesRead = bis.read(buffer, 0, buffer.length)) != -1) {
                messageDigest.update(buffer, 0, bytesRead);
            }
            return new String(Hex.encodeHex(messageDigest.digest()));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(bis);
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