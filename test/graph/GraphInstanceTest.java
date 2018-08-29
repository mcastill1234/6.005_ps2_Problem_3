/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 * Test cases based in Dmytro Shaban... thanks.
 *
 */

package graph;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;


/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {

    /*
     * Testing strategy for each operation of Graph:
     *
     * add():
     *   returns true/false if added new/existing vertex
     *   modifies Graph by adding vertex: test on different Graph sizes, 0, n.
     * set():
     *   returns previous edge weight: 0, n > 0, n < 0?
     *   modifies Graph by changing the value of edge between source and target to weight. Cases:
     *      1. Source is in Graph?
     *      2. Target is in Graph?
     *      3. Source equal target?
     *      4. Weight: 0, 1, n, negative?
     *      5. Source and Target was connected?
     *      6. Test on different Graph sizes
     * remove():
     *   returns true/false if removed vertex
     *   modifies Graph by removing a vertex, there are two cases:
     *       1. removing a vertex with incoming edges
     *       2. removing a vertex with outgoing edges
     * sources():
     *   returns a map with vertices as keys and weights as values. Cases:
     *      1. Target is in graph?
     *      2. Test multiple sources: 1, n
     *      3. Test different Graph sizes: 0, 1, n
     * targets():
     *   returns a map with vertices as keys and weights as values. Cases:
     *      1. Source is in graph?
     *      2. Test multiple targets: 0, 1, n
     *      3. Test different Graph sizes : 0, 1, n
     * vertices():
     *   returns a set of all vertices in Graph
     *   Test different graph sizes: 0,1, n
     */

    private static final String vertex1 = "V1";
    private static final String vertex2 = "V2";
    private static final String vertex3 = "V3";
    private static final String vertex4 = "V4";

    private static final int weight0 = 0;
    private static final int weight1 = 1;
    private static final int weight2 = 2;


    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    



    /**
     * Tests for add() method
     */

    // Covers:  add new vertex in Graph size = 0.
    @Test
    public void testAddNewVertexEmptyGraph() {
        Graph<String> testGraph = emptyInstance();
        assertTrue(testGraph.add(vertex1));
        assertTrue(testGraph.vertices().contains(vertex1));
    }

    // Covers:  add new vertex in Graph size = n.
    @Test
    public void testAddVertexesToEmptyGraph() {
        Graph<String> testGraph = emptyInstance();
        assertTrue(testGraph.add(vertex1));
        assertTrue(testGraph.add(vertex2));
        assertTrue(testGraph.add(vertex3));
        assertTrue(testGraph.vertices().contains(vertex1));
        assertTrue(testGraph.vertices().contains(vertex2));
        assertTrue(testGraph.vertices().contains(vertex3));
    }

    // Covers:  add existing vertex in Graph size = 1.
    @Test
    public void testAddExistingVertexToGraph() {
        Graph<String> testGraph = emptyInstance();
        assertTrue(testGraph.add(vertex1));
        assertTrue(testGraph.add(vertex2));
        assertFalse(testGraph.add(vertex2));
    }

    /**
     * Tests for set() method
     */

    // Covers:  source not equal target, source and target not in graph,
    //          Graph size = 0, weight =1, source and target were not connected.
    @Test
    public void testSetEdgeEmptyGraph() {
        Graph<String> testGraph = emptyInstance();
        int previousWeight = testGraph.set(vertex1, vertex2, weight1);
        assertEquals(0, previousWeight);
        assertTrue(testGraph.targets(vertex1).get(vertex2) == weight1);
        assertEquals(2, testGraph.vertices().size());
    }

    // Covers:  source not in Graph, target is in Graph, source not equal target,
    //          Graph size = 1, weight = 2, source and target were not connected.
    @Test
    public void testSetEdgeWithNewSource() {
        Graph<String> testGraph = emptyInstance();
        testGraph.add(vertex2);
        int previousWeight = testGraph.set(vertex1, vertex2, weight2);
        assertEquals(0, previousWeight);
        assertTrue(testGraph.targets(vertex1).get(vertex2) == weight2);
    }

    // Covers:  source is in Graph, target not in Graph, source not equal target,
    //          Graph size = 1, weight = 0, source and target were not connected.
    @Test
    public void testSetEdgeNewTargetWeight0() {
        Graph<String> testGraph = emptyInstance();
        testGraph.add(vertex1);
        int previousWeight = testGraph.set(vertex1, vertex2, weight0);
        assertEquals(0, previousWeight);
        assertFalse(testGraph.vertices().contains(vertex2));
        assertEquals(1, testGraph.vertices().size());
    }

    // Covers:  source and target are in Graph, source equals targets,
    //          Graph size = 1, weight = 0, source and target were not connected.
    @Test
    public void testSetEdgeSourceEqualTarget() {
        Graph<String> testGraph = emptyInstance();
        testGraph.add(vertex1);
        int previousWeight = testGraph.set(vertex1, vertex1, weight1);
        assertEquals(0, previousWeight);
        assertTrue(testGraph.targets(vertex1).get(vertex1) == weight1);
        assertEquals(1, testGraph.vertices().size());
    }

    // Covers:  source and target are in Graph, source not equal target,
    //          Graph size = 2, weight = 2, source and target were connected.
    @Test
    public void testSetEdgeModifier() {
        Graph<String> testGraph = emptyInstance();
        testGraph.add(vertex1);
        testGraph.add(vertex2);
        testGraph.set(vertex1, vertex2, weight1);
        int previousWeight = testGraph.set(vertex1, vertex2, weight2);
        assertEquals(weight1, previousWeight);
        assertTrue(testGraph.targets(vertex1).get(vertex2) == weight2);
        assertEquals(2, testGraph.vertices().size());
    }

    // Covers:  source and target are in Graph, source not equal target,
    //          Graph size = 2, weight = 0, source and target were connected.
    @Test
    public void testSetEdgeDelete() {
        Graph<String> testGraph = emptyInstance();
        testGraph.add(vertex1);
        testGraph.add(vertex2);
        testGraph.set(vertex1, vertex2, weight1);
        int previousWeight = testGraph.set(vertex1, vertex2, weight0);
        assertEquals(weight1, previousWeight);
        assertFalse(testGraph.targets(vertex1).containsKey(vertex2));
        assertEquals(2, testGraph.vertices().size());
    }

    /**
     * Tests for remove() method
     */

    // Covers:  remove existing vertex in Graph with incoming edges
    @Test
    public void testRemoveVertexWithIncEdge() {
        Graph<String> testGraph = emptyInstance();
        testGraph.set(vertex2, vertex1, weight1);
        Map<String, Integer> edges = testGraph.sources(vertex1);
        boolean isRemoved = testGraph.remove(vertex1);
        for (String vertex : edges.keySet()) {
            assertFalse(testGraph.targets(vertex).containsKey(vertex1));
        }
        assertFalse(testGraph.vertices().contains(vertex1));
        assertTrue(isRemoved);
    }

    // Covers:  remove existing vertex in Graph with outgoing edges
    @Test
    public void testRemoveVertexWithOutEdge() {
        Graph<String> testGraph = emptyInstance();
        testGraph.set(vertex1, vertex2, weight1);
        Map<String, Integer> edges = testGraph.targets(vertex1);
        boolean isRemoved = testGraph.remove(vertex1);
        for (String vertex : edges.keySet()) {
            assertFalse(testGraph.sources(vertex).containsKey(vertex1));
        }
        assertFalse(testGraph.vertices().contains(vertex1));
        assertTrue(isRemoved);
    }

    // Covers:  remove vertex not in Graph
    @Test
    public void testRemoveVertexEmptyGraph() {
        Graph<String> testGraph = emptyInstance();
        Set<String> verticesBefore = testGraph.vertices();
        boolean isRemoved = testGraph.remove(vertex1);
        assertTrue(verticesBefore.containsAll(testGraph.vertices()));
        assertEquals(verticesBefore.size(), testGraph.vertices().size());
        assertFalse(isRemoved);
    }

    /**
     * Tests for sources() method
     */

    // Covers:  vertex not in Graph size = 0. Source vertices = 0.
    @Test
    public void testSourcesEmptyGraph() {
        Graph<String> testGraph = emptyInstance();
        Map<String, Integer> sources = testGraph.sources(vertex1);
        assertEquals(Collections.emptyMap(), sources);
    }

    // Covers:  vertex is in Graph size = 1. Source vertices = 1.
    @Test
    public void testSourcesOneSource() {
        Graph<String> testGraph = emptyInstance();
        testGraph.add(vertex1);
        testGraph.set(vertex1, vertex1, weight1); // self loops allowed...
        Map<String, Integer> sources = testGraph.sources(vertex1);
        assertEquals(1, sources.size());
        assertTrue(sources.get(vertex1) == weight1);
    }

    // Covers:  vertex is in Graph size = n. Source vertices = 3.
    @Test
    public void testSourcesManySoources() {
        Graph<String> testGraph = emptyInstance();
        testGraph.add(vertex1);
        testGraph.add(vertex2);
        testGraph.add(vertex3);
        testGraph.add(vertex4);
        testGraph.set(vertex2, vertex1, weight1);
        testGraph.set(vertex3, vertex1, weight2);
        testGraph.set(vertex4, vertex1, weight1);
        Map<String, Integer> sources = testGraph.sources(vertex1);
        assertEquals(3, sources.size());
        assertTrue(sources.get(vertex2) == weight1);
        assertTrue(sources.get(vertex3) == weight2);
        assertTrue(sources.get(vertex4) == weight1);
    }

    /**
     * Tests for targets() method
     */

    // Covers:  vertex not in Graph size = 0. Target vertices = 0.
    @Test
    public void testTargetsEmptyGraph() {
        Graph<String> testGraph = emptyInstance();
        Map<String, Integer> targets = testGraph.targets(vertex1);
        assertEquals(Collections.emptyMap(), targets);
    }

    // Covers:  vertex is in Graph size = 1. Target vertices = 1.
    @Test
    public void testTargetsOneTarget() {
        Graph<String> testGraph = emptyInstance();
        testGraph.add(vertex1);
        testGraph.set(vertex1, vertex1, weight1);
        Map<String, Integer> targets = testGraph.targets(vertex1);
        assertEquals(1, targets.size());
        assertTrue(targets.get(vertex1) == weight1);
    }

    // Covers:  vertex is in Graph size = n. Target vertices = 3.
    @Test
    public void testTargetsManyTargets() {
        Graph<String> testGraph = emptyInstance();
        testGraph.add(vertex1);
        testGraph.add(vertex2);
        testGraph.add(vertex3);
        testGraph.add(vertex4);
        testGraph.set(vertex1, vertex2, weight1);
        testGraph.set(vertex1, vertex3, weight2);
        testGraph.set(vertex1, vertex4, weight1);
        Map<String, Integer> targets = testGraph.targets(vertex1);
        assertEquals(3, targets.size());
        assertTrue(targets.get(vertex2) == weight1);
        assertTrue(targets.get(vertex3) == weight2);
        assertTrue(targets.get(vertex4) == weight1);
    }

    /**
     * Tests for vertices() method
     */

    // Covers:  Graph size = 0.
    @Test
    public void testInitialVerticesEmpty() {
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }

    // Covers:  Graph size = 1.
    @Test
    public void testVerticesOneVertex() {
        Graph<String> testGraph = emptyInstance();
        testGraph.add(vertex1);
        Set<String> vertices = testGraph.vertices();
        assertEquals(1, vertices.size());
        assertTrue(vertices.contains(vertex1));
    }

    // Covers:  Graph size = n.
    @Test
    public void testVerticesManyVertices() {
        Graph<String> testGraph = emptyInstance();
        testGraph.add(vertex1);
        testGraph.add(vertex2);
        testGraph.add(vertex3);
        Set<String> vertices = testGraph.vertices();
        Set<String> modelVertises = new HashSet<>(Arrays.asList(vertex1, vertex2, vertex3));
        assertTrue(vertices.containsAll(modelVertises));
        assertEquals(3, vertices.size());
    }

}
