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
    //TODO:  use a try cache that can persist to disk and expire old locks
    final private static ConcurrentMap<String, Object> locks = new ConcurrentHashMap<String, Object>();
    final private ReentrantLock objectLock = new ReentrantLock();
    final private ReentrantLock readWriteLock = new ReentrantLock();

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
                lock = (L)instance.retrieveLock(key, type);
            }
        }
        return lock;
    }

    private Object retrieveLock(String key, LockType type) {
        Object lock;
        if (LockType.OBJECT.equals(type)) {
            lock = createObjectLock(key);
        } else if (LockType.READ_WRITE.equals(type)) {
            lock = createReadWriteLock(key);
        } else {
            throw new IllegalArgumentException("Not a valid type: " + type);
        }
        return lock;
    }

    private Object createReadWriteLock(String key) {
        Object lock;
        try {
            readWriteLock.lock();
            lock = locks.get(key);
            if (lock == null) {
                lock = new ReentrantReadWriteLock();
                locks.put(key, lock);
            }
        } finally {
            readWriteLock.unlock();
        }
        return lock;
    }

    private Object createObjectLock(String key) {
        Object lock;
        try {
            objectLock.lock();
            lock = locks.get(key);
            if (lock == null) {
                lock = new ReentrantLock();
                locks.put(key, lock);
            }
        } finally {
            objectLock.unlock();
        }
        return lock;
    }
}
