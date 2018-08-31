/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import org.junit.Test;

/**
 * Tests for static methods of Graph.
 * 
 * To facilitate testing multiple implementations of Graph, instance methods are
 * tested in GraphInstanceTest.
 */
public class GraphStaticTest {
    
    // Testing strategy
    //   empty()
    //     no inputs, only output is empty graph
    //     observe with vertices()
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testEmptyVerticesEmpty() {
        assertEquals("expected empty() graph to have no vertices",
                Collections.emptySet(), Graph.empty().vertices());
    }
    
    // Test other vertex label types in Problem 3.2

    // Covers type Integer
    @Test
    public void testTypeIntegerAddAndRemove() {
        Graph<Integer> testGraph = Graph.empty();
        assertTrue(testGraph.add(1));
        assertFalse(testGraph.add(1));
        assertTrue(testGraph.add(2));

        assertFalse(testGraph.remove(6));
        assertTrue(testGraph.remove(2));
        assertFalse(testGraph.remove(2));
    }

    @Test
    public void testTypeIntegerSet() {
        Graph<Integer> testGraph = Graph.empty();
        assertEquals(0, testGraph.set(1, 2, 3));
        assertEquals(3, testGraph.set(1, 2, 4));
        assertEquals(4, testGraph.set(1, 2, 0));
        assertEquals(0, testGraph.set(1, 2, 0));
    }

    @Test
    public void testTypeIntegerVertices() {
        Graph<Integer> testGraph = Graph.empty();
        assertTrue(testGraph.vertices().isEmpty());
        testGraph.add(2);
        testGraph.add(-5);
        testGraph.add(66);
        Set<Integer> vertexSet = testGraph.vertices();
        assertTrue(vertexSet.contains(2));
        assertTrue(vertexSet.contains(-5));
        assertTrue(vertexSet.contains(66));
        assertEquals(3, vertexSet.size());
    }

    @Test
    public void testTypeIntegerSources() {
        Graph<Integer> testGraph = Graph.empty();
        assertTrue(testGraph.sources(1).isEmpty());
        testGraph.set(4, 2, 3);
        testGraph.set(2, 1, 4);
        testGraph.set(3, 1, 7);
        testGraph.set(4, 3, 1);
        testGraph.set(4, 1, 3);
        Map<Integer, Integer> sourcesMap = testGraph.sources(1);
        Map<Integer, Integer> correctMap = new HashMap<>();
        correctMap.put(2, 4);
        correctMap.put(3, 7);
        correctMap.put(4, 3);
        assertEquals(correctMap, sourcesMap);
    }
    
}
