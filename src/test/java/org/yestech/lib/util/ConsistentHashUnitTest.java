/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.util;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import static com.google.common.collect.Lists.newArrayList;

import java.util.ArrayList;

/**
 *
 *
 */
public class ConsistentHashUnitTest {
    ConsistentHash<String> nodes;

    @Before
    public void setUp() {
        nodes = new ConsistentHash<String>(HashAlgorithm.KETAMA_HASH, 2);
    }

    @Test
    public void testAdd() {
        String node1 = "node-A";
        nodes.add(node1);
        int totalNodes = nodes.getTotalNodes();
        assertEquals(2, totalNodes);
    }

    @Test
    public void testRemove() {
        String node1 = "node-A";
        nodes.add(node1);
        String node2 = "node-B";
        nodes.add(node2);
        int totalNodes = nodes.getTotalNodes();
        assertEquals(4, totalNodes);
        nodes.remove(node1);
        totalNodes = nodes.getTotalNodes();
        assertEquals(2, totalNodes);
    }

    @Test
    public void testGet() {
        String node1 = "node-C";
        nodes.add(node1);
        String node2 = "node-D";
        nodes.add(node2);
        int totalNodes = nodes.getTotalNodes();
        assertEquals(4, totalNodes);
        String node = nodes.get("abcd");
        assertEquals(node1, node);
        node = nodes.get(node2);
        assertEquals(node2, node);
    }
}
