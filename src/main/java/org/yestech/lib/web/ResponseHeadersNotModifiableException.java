/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */
package org.yestech.lib.web;

/**
 * @author Artie Copeland
 * @version $Revision: $
 */
public class ResponseHeadersNotModifiableException extends RuntimeException {

    public ResponseHeadersNotModifiableException() {
    }

    public ResponseHeadersNotModifiableException(String message) {
        super(message);
    }

    public ResponseHeadersNotModifiableException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseHeadersNotModifiableException(Throwable cause) {
        super(cause);
    }
}
