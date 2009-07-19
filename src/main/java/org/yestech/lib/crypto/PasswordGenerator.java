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

import java.security.SecureRandom;
import java.security.MessageDigest;
import java.util.Date;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class PasswordGenerator {
    /**
     * Creates and return a randomly generated password to {@link ICryptoConstants#MIN_LENGTH}
     * length.
     *
     * @return The password
     */
    public static String createPassword() {
        return createPassword(ICryptoConstants.MIN_LENGTH);
    }

    /**
     * Creates and return a randomly generated password to a supplied
     * length. Null if an error happens.
     *
     * @param len Length of the password
     * @return The password as bytes
     */
    private static byte[] generatePassword(int len) {
        StringBuilder buf = new StringBuilder();
        SecureRandom random = new SecureRandom();
        SecureRandom randomType = new SecureRandom();
        for (int i = 0; i < len; i++) {
            int type = randomType.nextInt(3);
            switch (type) {
                case 0:
                    //Get uppercase letter
                    buf.append(ICryptoConstants.UPPER_ALPHABET[random.nextInt(26)]);
                    break;
                case 1:
                    //Get number between 1 - 9
                    int num = random.nextInt(9);
                    buf.append(++num);
                    break;

                case 2:
                    //Get lower letter
                    buf.append(ICryptoConstants.LOWER_ALPHABET[random.nextInt(26)]);
                    break;
            }
        }
        return buf.toString().getBytes();
    }

    /**
     * Creates and return a randomly generated password to a supplied
     * length.
     *
     * @param len Length of the password
     * @return The password
     */
    public static String createPassword(int len) {
        if (len < 1) {
            throw new IllegalArgumentException("min length must be atleast 1");
        }
        return new String(generatePassword(len));
    }
}
