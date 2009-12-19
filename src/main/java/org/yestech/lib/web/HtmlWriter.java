/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.web;

import org.jboss.resteasy.spi.ResteasyProviderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.yestech.lib.web.Location;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * A Custom Provider that handles forwarding requests using {@link RequestDispatcher}.  {@link org.yestech.lib.web.Location}
 * is used to determine where to forward the request and where to set the model is there is a return from the method.
 * <br/>
 * Note:
 * depending how you have resteasy configured. unless your method has a return type that isn't on this list:
 * 	 <ul>
 *      <li>java.lang.String</li>
 *      <li>java.io.InputStream</li>
 *      <li>javax.activation.DataSource</li>
 *      <li>java.io.File</li>
 *      <li>byte[]</li>
 *   </ul>
 *   Resteasy wont execute this Producer. 
 */
@SuppressWarnings("unchecked")
@Provider
@Produces(MediaType.TEXT_HTML)
@Component
public class HtmlWriter implements MessageBodyWriter {
    public HtmlWriter() {
        super();
        System.out.println("here...");
    }

    @SuppressWarnings("unused")
    final private static Logger logger = LoggerFactory.getLogger(HtmlWriter.class);

    @Override
    public long getSize(Object obj, Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        // No chance of figuring this out ahead of time
        return -1;
    }

    @Override
    public boolean isWriteable(Class type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return MediaType.TEXT_HTML_TYPE.equals(mediaType);
    }

    @Override
    public void writeTo(Object obj, Class type, Type genericType, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        HttpServletRequest request = ResteasyProviderFactory.getContextData(HttpServletRequest.class);
        HttpServletResponse response = ResteasyProviderFactory.getContextData(HttpServletResponse.class);
        try {
            if (annotations != null) {
                int totalAnnotations = annotations.length;
                for (int i = 0; i < totalAnnotations; i++) {
                    Annotation annotation = annotations[i];
                    if (Location.class.equals(annotation.annotationType())) {
                        Location location = (Location) annotation;
                        if (obj != null) {
                            request.setAttribute(location.modelKey(), obj);
                        }
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(location.value());

                        requestDispatcher.forward(request, response);
                    }
                }
            }
        }
        catch (ServletException ex) {
            throw new WebApplicationException(ex);
        }
    }
}
