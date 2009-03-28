/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */
package org.yestech.lib.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yestech.lib.web.GenericResponseWrapper;
import org.yestech.lib.web.ResponseUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPOutputStream;

/**
 * Provides compression of responses. Currently only GZip is supported. In the future other compression
 * algorithms will be integrated.
 * <p/>
 * See the filter-mappings.xml entry for the gzip filter for the URL patterns
 * which will be gzipped. At present this includes .jsp, .js and .css.
 * <p/>
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
public class CompressionFilter implements Filter {
    final private static Logger logger = LoggerFactory.getLogger(CompressionFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void doFilter(ServletRequest rawRequest, ServletResponse rawResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) rawRequest;
        HttpServletResponse response = (HttpServletResponse) rawResponse;
        if (!isIncluded(request) && acceptsEncoding(request, "gzip")) {
            // Client accepts zipped content
            if (logger.isDebugEnabled()) {
                logger.debug(request.getRequestURL() + ". Writing with gzip compression");
            }

            // Create a gzip stream
            final ByteArrayOutputStream compressed = new ByteArrayOutputStream();
            final GZIPOutputStream gzout = new GZIPOutputStream(compressed);

            // Handle the request
            final GenericResponseWrapper wrapper = new GenericResponseWrapper(response, gzout);
            chain.doFilter(request, wrapper);
            wrapper.flush();

            gzout.close();

            //return on error or redirect code, because response is already committed
            int statusCode = wrapper.getStatus();
            if (statusCode != HttpServletResponse.SC_OK) {
                return;
            }

            //Saneness checks
            byte[] compressedBytes = compressed.toByteArray();
            boolean shouldGzippedBodyBeZero = ResponseUtil.shouldGzippedBodyBeZero(compressedBytes, request);
            boolean shouldBodyBeZero = ResponseUtil.shouldBodyBeZero(request, wrapper.getStatus());
            if (shouldGzippedBodyBeZero || shouldBodyBeZero) {
                compressedBytes = new byte[0];
            }

            // Write the zipped body
            ResponseUtil.addGzipHeader(response);
            response.setContentLength(compressedBytes.length);


            response.getOutputStream().write(compressedBytes);
        } else {
            // Client does not accept zipped content - don't bother zipping
            if (logger.isDebugEnabled()) {
                logger.debug(request.getRequestURL()
                        + ". Writing without gzip compression because the request does not accept gzip.");
            }
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Checks if the request uri is an include.
     * These cannot be gzipped.
     */
    private boolean isIncluded(final HttpServletRequest request) {
        final String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
        final boolean includeRequest = !(uri == null);

        if (includeRequest && logger.isDebugEnabled()) {
            logger.debug(request.getRequestURL() + " resulted in an include request. This is unusable, because" +
                    "the response will be assembled into the overrall response. Not gzipping.");
        }
        return includeRequest;
    }

    /**
     * Determine whether the user agent accepts GZIP encoding. This feature is part of HTTP1.1.
     * If a browser accepts GZIP encoding it will advertise this by including in its HTTP header:
     * <p/>
     * <code>
     * Accept-Encoding: gzip
     * </code>
     * <p/>
     * Requests which do not accept GZIP encoding fall into the following categories:
     * <ul>
     * <li>Old browsers, notably IE 5 on Macintosh.
     * <li>Internet Explorer through a proxy. By default HTTP1.1 is enabled but disabled when going
     * through a proxy. 90% of non gzip requests seen on the Internet are caused by this.
     * </ul>
     * As of September 2004, about 34% of Internet requests do not accept GZIP encoding.
     *
     * @param request
     * @return true, if the User Agent request accepts GZIP encoding
     */
    protected boolean acceptsGzipEncoding(HttpServletRequest request) {
        return acceptsEncoding(request, "gzip");
    }

    /**
     * Checks if request accepts the named encoding.
     */
    protected boolean acceptsEncoding(final HttpServletRequest request, final String name) {
        final boolean accepts = headerContains(request, "Accept-Encoding", name);
        return accepts;
    }

    /**
     * Checks if request contains the header value.
     */
    private boolean headerContains(final HttpServletRequest request, final String header, final String value) {

        logRequestHeaders(request);

        final Enumeration accepted = request.getHeaders(header);
        while (accepted.hasMoreElements()) {
            final String headerValue = (String) accepted.nextElement();
            if (headerValue.indexOf(value) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Logs the request headers, if debug is enabled.
     *
     * @param request
     */
    protected void logRequestHeaders(final HttpServletRequest request) {
        if (logger.isDebugEnabled()) {
            Map headers = new HashMap();
            Enumeration enumeration = request.getHeaderNames();
            StringBuffer logLine = new StringBuffer();
            logLine.append("Request Headers");
            while (enumeration.hasMoreElements()) {
                String name = (String) enumeration.nextElement();
                String headerValue = request.getHeader(name);
                headers.put(name, headerValue);
                logLine.append(": ").append(name).append(" -> ").append(headerValue);
            }
            logger.debug(logLine.toString());
        }
    }
}
