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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Utility methods to log.
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
final public class LoggingUtils {
    final private static Logger logger = LoggerFactory.getLogger(LoggingUtils.class);

    private LoggingUtils() {
    }

    /**
     * Logs the request headers, if debug is enabled.
     *
     * @param request - Request to Check
     */
    public static void logRequestHeaders(final HttpServletRequest request) {
        logRequestHeaders(logger, request);
    }

    /**
     * Logs the request headers, if debug is enabled.
     *
     * @param request - Request to Check
     * @param logger - logger to use when logging
     */
    public static void logRequestHeaders(final Logger logger, final HttpServletRequest request) {
        if (logger.isDebugEnabled()) {
            Enumeration enumeration = request.getHeaderNames();
            StringBuffer logLine = new StringBuffer();
            logLine.append("Request Headers");
            while (enumeration.hasMoreElements()) {
                String name = (String) enumeration.nextElement();
                String headerValue = request.getHeader(name);
                logLine.append(": ").append(name).append(" -> ").append(headerValue);
            }
            logger.debug(logLine.toString());
        }
    }
}
