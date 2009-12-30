/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.util;

/**
 * Interface to provide a consistent hash algorithm following this
 * <a href="http://www8.org/w8-papers/2a-webserver/caching/paper2.html">Algorithm</a>
 *
 * @param <T> Type of the Node on the circle
 */
public interface IConsistentHash<T> {

    /**
     * Add a node to the Ring and replicate it.
     *
     * @param node Node to add
     */
    void add(T node);

    /**
     * Remove a node from the Ring.
     *
     * @param node Node to remove
     */
    void remove(T node);

    /**
     * Return the number of nodes on the Ring.
     *
     * @return the total node count.
     */
    int getTotalNodes();

    /**
     * Return the node associated with a Key.
     *
     * @param key The check to has to find the nearest node
     * @return The nearest Node
     */
    T get(Object key);
}
