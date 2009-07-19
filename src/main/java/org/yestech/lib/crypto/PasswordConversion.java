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

import java.util.Map;
import java.util.HashMap;

/**
 * Converts a Password from plain text to a valid {@link org.yestech.lib.crypto.PasswordTypeEnum}.
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
public class PasswordConversion {

    final private static Map<PasswordTypeEnum, IPasswordConversionFactory> convertors = new HashMap<PasswordTypeEnum, IPasswordConversionFactory>();

    static {
        convertors.put(PasswordTypeEnum.UNIX_CRYPT, new UnixCryptConversionFactory());
        convertors.put(PasswordTypeEnum.UNIX_MD5, new UnixMd5ConversionFactory());
        convertors.put(PasswordTypeEnum.APACHE, new ApacheConversionFactory());
        convertors.put(PasswordTypeEnum.UPORTAL, new UPortalConversionFactory());
    }

    public static String convert(PasswordTypeEnum type, String password, Object... params) {
        String convertedPassword = null;
        IPasswordConversionFactory convertor = convertors.get(type);
        if (convertor != null) {
            convertedPassword = convertor.transform(password, params);
        }
        return convertedPassword;
    }
}
