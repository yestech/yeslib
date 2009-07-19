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
public class UnixMd5ConversionFactory implements IPasswordConversionFactory {

    /**
     * Encodes a password that adheres to UPortal.
     *
     * @param password
     * @return The password
     */
    @Override
    public String transform(String password, Object... params) {
        String convertedPassword = null;
        if (params == null || params.length < 1) {
            convertedPassword = toUnixMD5Crypt(password);
        } else {
            convertedPassword = toUnixMD5Crypt(params[0].toString(), password);
        }
        return convertedPassword;
    }

    /**
     * Convert a String to a Unix Crypt Representation. The original
     * is the clear text password.
     *
     * @param original Unencrypted value
     * @return The encrypted value
     */
    private String toUnixMD5Crypt(String original) {

        return MD5Crypt.crypt(original);
    }

    /**
     * Convert a String to a Unix MD5 Crypt Representation.  Usually salt is a random string
     * and original is the clear text password.
     *
     * @param salt     Salt to use for encryption
     * @param original Unencrypted value
     * @return The encrypted value
     */
    private String toUnixMD5Crypt(String salt, String original) {
        return MD5Crypt.crypt(salt, original);
    }
}