/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.concurrency;

import com.hazelcast.core.Hazelcast;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Represents a concurrency lock manager
 */
@SuppressWarnings("unchecked")
public class LockManager {
    final private static LockManager instance = new LockManager();
    final private static ConcurrentMap<String, Object> locks = new ConcurrentHashMap<String, Object>();
//    final private ReentrantLock objectLock = new ReentrantLock();
//    final private ReentrantLock readWriteLock = new ReentrantLock();

    /**
     * Creates or retrieve a previous lock associated with the key.
     *
     * @param key  key associated with the lock.
     * @param type Type of lock to return
     * @return The Lock
     */
    public static <L> L getLock(String key, LockType type) {
        L lock;
        if (LockType.DISTRIBUTED.equals(type)) {
            lock = (L) Hazelcast.getLock(key);
        } else {
            lock = (L) locks.get(key);
            if (lock == null) {
                lock = instance.retrieveLock(key, type);
            }
        }
        return lock;
    }

    private <L> L retrieveLock(String key, LockType type) {
        L lock;
        if (LockType.OBJECT.equals(type)) {
            lock = createObjectLock(key);
        } else if (LockType.READ_WRITE.equals(type)) {
            lock = createReadWriteLock(key);
        } else {
            throw new IllegalArgumentException("Not a valid type: " + type);
        }
        return lock;
    }

    private <L> L createReadWriteLock(String key) {
        L lock;
        synchronized (key) {
            lock = (L) locks.get(key);
            if (lock == null) {
                lock = (L) new ReentrantReadWriteLock();
                locks.put(key, lock);
            }
        }
        return lock;
    }

    private <L> L createObjectLock(String key) {
        L lock;
        synchronized (key) {
            lock = (L) locks.get(key);
            if (lock == null) {
                lock = (L) new ReentrantLock();
                locks.put(key, lock);
            }
        }
        return lock;
    }
}
