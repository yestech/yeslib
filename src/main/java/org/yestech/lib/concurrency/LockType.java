/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.concurrency;

/**
 * Represent to types of Locks the {@link org.yestech.lib.concurrency.LockManager} can manager.
 *
 */
public enum LockType {
    READ_WRITE, DISTRIBUTED, OBJECT 
}
