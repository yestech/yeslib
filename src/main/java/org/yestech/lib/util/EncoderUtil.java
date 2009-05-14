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
import java.net.URLEncoder;

/**
 * A utility to encode to multiple formats.
 * 
 * @author Artie Copeland
 * @version $Revision: $
 */
final public class EncoderUtil {
    final private static Logger logger = LoggerFactory.getLogger(EncoderUtil.class);
    final private static String DEFAULT_STRING_ENCODING = "UTF-8";

    private EncoderUtil() {
    }

    /**
     * URL Encodes the string
     *
     * @param value
     * @return
     */
    public static String urlEncode(String value) {
        return urlEncode(value, DEFAULT_STRING_ENCODING);
    }

    /**
     * URL Encodes the string using a charset.
     *
     * @param value
     * @param encoding
     * @return
     */
    public static String urlEncode(String value, String encoding) {
        try {
            if (value == null) {
                return "";
            }
            if (StringUtils.isBlank(value)) {
                return value;
            }
            return URLEncoder.encode(value, encoding);
        }
        catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Encodes 's' by converting each character to its equivalent HTML entity,
     * where one exists.
     *
     * @param s the string to convert.
     * @return a string with entities encoded.
     */
    public static String htmlEncode(String s) {
        return StringEscapeUtils.escapeHtml(s);
    }

    /**
     * Escape HTTP URI fragment per RFC2396
     *
     * @param uri
     * @return
     */
    public static String uriEncode(String uri) {
        int length = uri.length();

        if (length == 0)
            return uri;

        StringBuffer results = new StringBuffer();

        for (int i = 0;
             i < length;
             i++) {
            char c = uri.charAt(i);
            switch (c) {
                case ';':
                case '/':
                case '?':
                case ':':
                case '@':
                case '&':
                case '=':
                case '+':
                case '$':
                case ',':
                case '%':
                    // Note:  Space and double quote are not specified by
                    //        RFC2396..., but we need 'em anyway...
                case ' ':
                case '"':
                    // TODO:  getBytes() should specify encoding???
                    try {
                        byte[] octets;
                        octets = uri.substring(i, i + 1).getBytes();
                        for (int j = 0;
                             j < octets.length;
                             j++) {
                            String hexVal = Integer.toHexString(octets[j]).toUpperCase();
                            if (hexVal.length() == 2) {
                                results.append("%").append(hexVal);
                            } else {
                                results.append("%0").append(hexVal);
                            }
                        }
                    } catch (Exception e) {
                    }

                    break;
                default:
                    results.append(c);
                    break;
            }
        }

        return results.toString();
    }

    /**
     * Encodes 's' by converting each character to its equivalent XML entity,
     * where one exists.
     *
     * @param s the string to convert.
     * @return a string with entities encoded.
     */
    public static String xmlEncode(String s) {
        return StringEscapeUtils.escapeXml(s);
    }

}