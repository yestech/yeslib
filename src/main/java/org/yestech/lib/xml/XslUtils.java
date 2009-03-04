/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.xml;

import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
//import org.apache.xalan.processor.TransformerFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

import java.io.StringWriter;
import java.io.File;

/**
 * Utility Methods for XSL Templating.
 *
 */
final public class XslUtils {
    public static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactoryImpl.newInstance();

    final private static Logger logger = LoggerFactory.getLogger(XslUtils.class);

    private XslUtils() {
        super();
    }

    /**
     * Transforms an xml source using an xsl source and returns the result.
     *
     * @param xmlSource XML Source
     * @param xslSource XSL Source
     * @return The result
     * @throws RuntimeException if an error happens
     */
    public static String transformMessage(Source xmlSource, Source xslSource) {
        try {
            StringWriter writer = new StringWriter();
            //TODO: cache the transformers!!!!!
            Transformer transform = XslUtils.TRANSFORMER_FACTORY.newTransformer(xslSource);
            transform.transform(xmlSource, new StreamResult(writer));
            return writer.toString();
        } catch (Exception e) {
            logger.error("trouble with xsl transformation ", e);
            throw new RuntimeException("trouble with xsl transformation : " + e + " --> " + e.getMessage());
        }

    }

    public static File locateFileInApp(String filePath)
    {
        throw new UnsupportedOperationException("Not Yet implemented");
    }
}
