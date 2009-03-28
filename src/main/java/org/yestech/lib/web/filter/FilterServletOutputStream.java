/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */
package org.yestech.lib.web.filter;

import javax.servlet.ServletOutputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * A custom {@link javax.servlet.ServletOutputStream} for use by our filters
 *
 * @author Artie Copeland
 * @version $Revision: $
 */
public class FilterServletOutputStream extends ServletOutputStream {

    private OutputStream stream;

    /**
     * Creates a FilterServletOutputStream.
     */
    public FilterServletOutputStream(final OutputStream stream) {
        this.stream = stream;
    }

    /**
     * Writes to the stream.
     */
    public void write(final int b) throws IOException {
        stream.write(b);
    }

    /**
     * Writes to the stream.
     */
    public void write(final byte[] b) throws IOException {
        stream.write(b);
    }

    /**
     * Writes to the stream.
     */
    public void write(final byte[] b, final int off, final int len) throws IOException {
        stream.write(b, off, len);
    }
}
