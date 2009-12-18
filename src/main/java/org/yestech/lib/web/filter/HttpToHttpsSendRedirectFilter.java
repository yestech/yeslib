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
package org.yestech.lib.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

/**
 * A simple filter that traps all http sendRedirect calls and rewrites them to https.
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
public class HttpToHttpsSendRedirectFilter implements Filter {
    public void init(FilterConfig filterConfig)
            throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request
            , ServletResponse response
            , FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(request
                , new SendRedirectOverloadedResponse((HttpServletRequest) request, (HttpServletResponse
                        ) response));

    }

    /**
     * A simple HttpServletReponse that overrides {@link #sendRedirect(String)}.
     */
    private static class SendRedirectOverloadedResponse extends HttpServletResponseWrapper {
        final private static Logger logger = LoggerFactory.getLogger(SendRedirectOverloadedResponse.class);

        private String prefix = null;

        private SendRedirectOverloadedResponse(HttpServletRequest inRequest
                , HttpServletResponse response) {
            super(response);
            prefix = getPrefix(inRequest);
        }

        public void sendRedirect(String location) throws IOException {
            if (logger.isInfoEnabled()) {
                logger.info("Going originally to:" + location);
            }
            String finalurl = null;

            if (isUrlAbsolute(location)) {
                if (logger.isInfoEnabled()) {
                    logger.info("This url is absolute. No scheme changes will be attempted");
                }
                finalurl = location;
            } else {
                finalurl = fixForScheme(prefix + location);
                if (logger.isInfoEnabled()) {
                    logger.info("Going to absolute url:" + finalurl);
                }
            }
            super.sendRedirect(finalurl);
        }

        public boolean isUrlAbsolute(String url) {
            String lowercaseurl = url.toLowerCase();
            return lowercaseurl.startsWith("http");
        }

        public String fixForScheme(String url) {
            //alter the url here if you were to change the scheme
            return url;
        }

        public String getPrefix(HttpServletRequest request) {
            StringBuffer str = request.getRequestURL();
            String url = str.toString();
            String uri = request.getRequestURI();
            if (logger.isInfoEnabled()) {
                logger.info("requesturl:" + url);
                logger.info("uri:" + uri);
            }
            int offset = url.indexOf(uri);
            String prefix = url.substring(0, offset);
            if (logger.isInfoEnabled()) {
                logger.info("prefix:" + prefix);
            }
            return prefix;
        }
    }
}

