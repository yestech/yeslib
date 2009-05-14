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
package org.yestech.lib.util;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * A utility to decode from multiple formats.
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
final public class DecoderUtil {
    final private static Logger logger = LoggerFactory.getLogger(DecoderUtil.class);
    final private static String DEFAULT_STRING_ENCODING = "UTF-8";

    private DecoderUtil() {
    }

    /**
     * URL Decodes the string
     *
     * @param value
     * @return
     */
    public static String urlDecode(String value) {
        return urlDecode(value, DEFAULT_STRING_ENCODING);
    }

    /**
     * URL Decodes string using a charset
     *
     * @param value
     * @param encoding
     * @return
     */
    public static String urlDecode(String value, String encoding) {
        try {
            if (value == null) {
                return "";
            }
            if (StringUtils.isBlank(value)) {
                return value;
            }
            return URLDecoder.decode(value, encoding);
        }
        catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Decodes 's' by converting each character to its equivalent HTML entity,
     * where one exists.
     *
     * @param s the string to convert.
     * @return a string with entities decoded.
     */
    public static String htmlDecode(String s) {
        return StringEscapeUtils.unescapeHtml(s);
    }

    /**
     *
     * @param encodedStr
     * @return
     */
    public static String uriDecode(final String encodedStr) {
        if (encodedStr == null) {
            return null;
        }
        if (encodedStr.indexOf('%') < 0) {
            return encodedStr;
        }
        final StringBuffer buffer = new StringBuffer(encodedStr);
        uriDecode(buffer, 0, buffer.length());
        return buffer.toString();
    }

    private static void uriDecode(final StringBuffer buffer, final int offset,
                                  final int length) {
        int index = offset;
        int count = length;
        for (; count > 0; count--, index++) {
            final char ch = buffer.charAt(index);
            if (ch != '%') {
                continue;
            }
            if (count < 3) {
                throw new RuntimeException(
                        "invalid-escape-sequence.error: " + buffer
                                .substring(index, index + count));
            }

            // Decode
            int dig1 = Character.digit(buffer.charAt(index + 1), 16);
            int dig2 = Character.digit(buffer.charAt(index + 2), 16);
            if (dig1 == -1 || dig2 == -1) {
                throw new RuntimeException(
                        "invalid-escape-sequence.error " + buffer
                                .substring(index, index + 3));
            }
            char value = (char) (dig1 << 4 | dig2);

            // Replace
            buffer.setCharAt(index, value);
            buffer.delete(index + 1, index + 3);
            count -= 2;
        }
    }

    /**
     * Decodes 's' by converting each character to its equivalent XML entity,
     * where one exists.
     *
     * @param s the string to convert.
     * @return a string with entities decoded.
     */
    public static String xmlDecode(String s) {
        return StringEscapeUtils.unescapeXml(s);
    }
}
