/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.OutputStream;

/**
 * A PrintWriter that auto flush all calls after delegating to parent class.
 *
 */
public class AutoFlushingPrintWriter extends PrintWriter {
    final private static Logger logger = LoggerFactory.getLogger(AutoFlushingPrintWriter.class);

    /**
     * Creates a new PrintWriter, without automatic line flushing, from an
     * existing OutputStream.  This convenience constructor creates the
     * necessary intermediate OutputStreamWriter, which will convert characters
     * into bytes using the default character encoding.
     *
     * @param out An output stream
     * @see java.io.OutputStreamWriter#OutputStreamWriter(java.io.OutputStream)
     */
    public AutoFlushingPrintWriter(OutputStream out) {
        super(out);
    }


    /**
     * Writes A Portion of an array of characters.
     *
     * @param buf Array of characters
     * @param off Offset from which to start writing characters
     * @param len Number of characters to write
     */
    @Override
    public void write(char[] buf, int off, int len) {
        super.write(buf, off, len);
        super.flush();
    }
}
