/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */
package org.yestech.lib.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * A simple Consistent Hash Algorithmn derived from:
 * <a href="http://weblogs.java.net/blog/tomwhite/archive/2007/11/consistent_hash.html">http://weblogs.java.net/blog/tomwhite/archive/2007/11/consistent_hash.html</a>
 *
 * @param <T> Type of the Node on the circle
 */
public class ConsistentHash<T> implements IConsistentHash<T> {

    private final HashAlgorithm hashFunction;
    private final int numberOfReplicas;
    private final SortedMap<Long, T> circle = new TreeMap<Long, T>();

    /**
     * Creates a Ring and adds an initial collection of node to the Ring.
     * 
     * @param hashFunction The Hashing Functions to use.
     * @param numberOfReplicas Number of times to replicate a node on the Ring
     */
    public ConsistentHash(HashAlgorithm hashFunction, int numberOfReplicas) {
        this(hashFunction, numberOfReplicas, new ArrayList<T>());
    }

    /**
     * Creates a Ring and adds an initial collection of node to the Ring.
     *
     * @param hashFunction The Hashing Functions to use.
     * @param numberOfReplicas Number of times to replicate a node on the Ring
     * @param nodes Nodes to add
     */
    public ConsistentHash(HashAlgorithm hashFunction, int numberOfReplicas,
                          Collection<T> nodes) {
        this.hashFunction = hashFunction;
        this.numberOfReplicas = numberOfReplicas;

        for (T node : nodes) {
            add(node);
        }
    }

    /**
     * Add a node to the Ring and replicate it.
     *
     * @param node Node to add
     */
    public void add(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.put(hashFunction.hash(node.toString() + i), node);
        }
    }

    /**
     * Remove a node from the Ring.
     *
     * @param node Node to remove
     */
    public void remove(T node) {
        for (int i = 0; i < numberOfReplicas; i++) {
            circle.remove(hashFunction.hash(node.toString() + i));
        }
    }

    /**
     * Return the number of nodes on the Ring.
     *
     * @return the total node count.
     */
    public int getTotalNodes() {
        return circle.size();
    }
    
    /**
     * Return the nearest node associated with a resource.
     *
     * @param resource The check to has to find the nearest node
     * @return The nearest Node
     */
    public T get(Object resource) {
        if (circle.isEmpty()) {
            return null;
        }
        long hash = hashFunction.hash(resource.toString());
        if (!circle.containsKey(hash)) {
            SortedMap<Long, T> tailMap = circle.tailMap(hash);
            hash = tailMap.isEmpty() ? circle.firstKey() : tailMap.firstKey();
        }
        return circle.get(hash);
    }
}