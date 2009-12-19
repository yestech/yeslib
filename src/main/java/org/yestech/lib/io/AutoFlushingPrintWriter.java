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

    /**
     * Writes a single character.
     *
     * @param c int specifying a character to be written.
     */
    @Override
    public void write(int c) {
        super.write(c);
        super.flush();
    }

    /**
     * Writes an array of characters.  This method cannot be inherited from the
     * Writer class because it must suppress I/O exceptions.
     *
     * @param buf Array of characters to be written
     */
    @Override
    public void write(char[] buf) {
        super.write(buf);
        super.flush();
    }

    /**
     * Writes a portion of a string.
     *
     * @param s   A String
     * @param off Offset from which to start writing characters
     * @param len Number of characters to write
     */
    @Override
    public void write(String s, int off, int len) {
        super.write(s, off, len);
        super.flush();
    }

    /**
     * Writes a string.  This method cannot be inherited from the Writer class
     * because it must suppress I/O exceptions.
     *
     * @param s String to be written
     */
    @Override
    public void write(String s) {
        super.write(s);
        super.flush();
    }

    /**
     * Prints a boolean value.  The string produced by <code>{@link
     * String#valueOf(boolean)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link
     * #write(int)}</code> method.
     *
     * @param b The <code>boolean</code> to be printed
     */
    @Override
    public void print(boolean b) {
        super.print(b);
        super.flush();
    }

    /**
     * Prints a character.  The character is translated into one or more bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link
     * #write(int)}</code> method.
     *
     * @param c The <code>char</code> to be printed
     */
    @Override
    public void print(char c) {
        super.print(c);
        super.flush();
    }

    /**
     * Prints an integer.  The string produced by <code>{@link
     * String#valueOf(int)}</code> is translated into bytes according
     * to the platform's default character encoding, and these bytes are
     * written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * @param i The <code>int</code> to be printed
     * @see Integer#toString(int)
     */
    @Override
    public void print(int i) {
        super.print(i);
        super.flush();
    }

    /**
     * Prints a long integer.  The string produced by <code>{@link
     * String#valueOf(long)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * @param l The <code>long</code> to be printed
     * @see Long#toString(long)
     */
    @Override
    public void print(long l) {
        super.print(l);
        super.flush();
    }

    /**
     * Prints a floating-point number.  The string produced by <code>{@link
     * String#valueOf(float)}</code> is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * @param f The <code>float</code> to be printed
     * @see Float#toString(float)
     */
    @Override
    public void print(float f) {
        super.print(f);
        super.flush();
    }

    /**
     * Prints a double-precision floating-point number.  The string produced by
     * <code>{@link String#valueOf(double)}</code> is translated into
     * bytes according to the platform's default character encoding, and these
     * bytes are written in exactly the manner of the <code>{@link
     * #write(int)}</code> method.
     *
     * @param d The <code>double</code> to be printed
     * @see Double#toString(double)
     */
    @Override
    public void print(double d) {
        super.print(d);
        super.flush();
    }

    /**
     * Prints an array of characters.  The characters are converted into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * @param s The array of chars to be printed
     * @throws NullPointerException If <code>s</code> is <code>null</code>
     */
    @Override
    public void print(char[] s) {
        super.print(s);
        super.flush();
    }

    /**
     * Prints a string.  If the argument is <code>null</code> then the string
     * <code>"null"</code> is printed.  Otherwise, the string's characters are
     * converted into bytes according to the platform's default character
     * encoding, and these bytes are written in exactly the manner of the
     * <code>{@link #write(int)}</code> method.
     *
     * @param s The <code>String</code> to be printed
     */
    @Override
    public void print(String s) {
        super.print(s);
        super.flush();
    }

    /**
     * Prints an object.  The string produced by the <code>{@link
     * String#valueOf(Object)}</code> method is translated into bytes
     * according to the platform's default character encoding, and these bytes
     * are written in exactly the manner of the <code>{@link #write(int)}</code>
     * method.
     *
     * @param obj The <code>Object</code> to be printed
     * @see Object#toString()
     */
    @Override
    public void print(Object obj) {
        super.print(obj);
        super.flush();
    }

    /**
     * Terminates the current line by writing the line separator string.  The
     * line separator string is defined by the system property
     * <code>line.separator</code>, and is not necessarily a single newline
     * character (<code>'\n'</code>).
     */
    @Override
    public void println() {
        super.println();
        super.flush();
    }

    /**
     * Prints a boolean value and then terminates the line.  This method behaves
     * as though it invokes <code>{@link #print(boolean)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x the <code>boolean</code> value to be printed
     */
    @Override
    public void println(boolean x) {
        super.println(x);
        super.flush();
    }

    /**
     * Prints a character and then terminates the line.  This method behaves as
     * though it invokes <code>{@link #print(char)}</code> and then <code>{@link
     * #println()}</code>.
     *
     * @param x the <code>char</code> value to be printed
     */
    @Override
    public void println(char x) {
        super.println(x);
        super.flush();
    }

    /**
     * Prints an integer and then terminates the line.  This method behaves as
     * though it invokes <code>{@link #print(int)}</code> and then <code>{@link
     * #println()}</code>.
     *
     * @param x the <code>int</code> value to be printed
     */
    @Override
    public void println(int x) {
        super.println(x);
        super.flush();
    }

    /**
     * Prints a long integer and then terminates the line.  This method behaves
     * as though it invokes <code>{@link #print(long)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x the <code>long</code> value to be printed
     */
    @Override
    public void println(long x) {
        super.println(x);
        super.flush();
    }

    /**
     * Prints a floating-point number and then terminates the line.  This method
     * behaves as though it invokes <code>{@link #print(float)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x the <code>float</code> value to be printed
     */
    @Override
    public void println(float x) {
        super.println(x);
        super.flush();
    }

    /**
     * Prints a double-precision floating-point number and then terminates the
     * line.  This method behaves as though it invokes <code>{@link
     * #print(double)}</code> and then <code>{@link #println()}</code>.
     *
     * @param x the <code>double</code> value to be printed
     */
    @Override
    public void println(double x) {
        super.println(x);
        super.flush();
    }

    /**
     * Prints an array of characters and then terminates the line.  This method
     * behaves as though it invokes <code>{@link #print(char[])}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x the array of <code>char</code> values to be printed
     */
    @Override
    public void println(char[] x) {
        super.println(x);
        super.flush();
    }

    /**
     * Prints a String and then terminates the line.  This method behaves as
     * though it invokes <code>{@link #print(String)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x the <code>String</code> value to be printed
     */
    @Override
    public void println(String x) {
        super.println(x);
        super.flush();
    }

    /**
     * Prints an Object and then terminates the line.  This method calls
     * at first String.valueOf(x) to get the printed object's string value,
     * then behaves as
     * though it invokes <code>{@link #print(String)}</code> and then
     * <code>{@link #println()}</code>.
     *
     * @param x The <code>Object</code> to be printed.
     */
    @Override
    public void println(Object x) {
        super.println(x);
        super.flush();
    }
}
