/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing Strategy for Vertex
     *
     * Vertex()
     *   Constructor of Vertex object. Test with different strings.
     *
     * setTarget()
     *   Constructor of target and edge weight. Cases:
     *   1. target and vertex are equal: yes, no.
     *   2. Test with different strings for target name.
     *   3. Test for different weights w < 0, w = 0, w > 0.
     *
     * getName()
     *   returns the name of this Vertex. Try different strings.
     *
     * getTargets()
     *   returns the set of targets that vertex points to. Cases:
     *   1. Try different set sizes or targets.
     *   2. Try different Strings for target names.
     *
     * getWeight()
     *   returns the weights of edge from Vertex to target. Cases:
     *   1. Target is in Vertex targets? Yes, no.
     *   2. Test for different weights w < 0, w = 0, w > 0.
     *
     * isVertexInTargets()
     *   returns true if target is in vertex targets. Cases
     *   1. Target is in Vertex targets? Yes, no.
     *   2. Try different String sizes for targets.
     *
     * toString()
     *   Test Vertex with no targets, 1 target, n targets.
     *
     */

    /*
     * Testing Strategy for ConcreteVerticesGraph
     *
     * toString()
     *   Test with empty graph, 1 Vertex in Graph, N Vertices in Graph
     *   Test with no edges, 1 edge, N, edges
     */


    private final String vertex1 = "V1";
    private final String vertex2 = "V2";
    private final String vertex3 = "V3";

    private final String target1 = "T1";
    private final String target2 = "T2";
    private final String target3 = "T3";

    private final int weight0 = 0;
    private final int weight1 = 1;
    private final int weight2 = 2;
    private final int weight3 = 3;


    /*
     * Testing Vertex...
     */


    // Covers construct Vertex with different string sizes
    @Test
    public void testVertexConstruct() {
        Vertex testVertex1 = new Vertex(vertex1);
        Vertex testVertex2 = new Vertex(vertex2);
        Vertex testVertex3 = new Vertex(vertex3);
        assertEquals(vertex1, testVertex1.getName());
        assertEquals(vertex2, testVertex2.getName());
        assertEquals(vertex3, testVertex3.getName());
    }

    // Covers construct edges to different targets. Includes self loop. Includes remove target.
    //
    @Test
    public void testSetTargetVariousTargets() {
        Vertex testVertex1 = new Vertex(vertex1);
        testVertex1.setTarget(target1, weight1);
        testVertex1.setTarget(target2, weight2);
        testVertex1.setTarget(target3, weight3);
        testVertex1.setTarget(vertex1, weight3);
        assertEquals(weight1, testVertex1.getWeight(target1));
        assertEquals(weight2, testVertex1.getWeight(target2));
        assertEquals(weight3, testVertex1.getWeight(target3));
        assertEquals(weight3, testVertex1.getWeight(vertex1));
        testVertex1.setTarget(target1, weight0);
        assertFalse(testVertex1.getTargets().contains(target1));
    }

    // Convers both true and false returns with different string sizes.
    @Test
    public void testIsVertexInTargets() {
        Vertex testVertex1 = new Vertex(vertex1);
        testVertex1.setTarget(target1, weight1);
        testVertex1.setTarget(target2, weight2);
        assertFalse(testVertex1.isVertexInTargets(target3));
        assertTrue(testVertex1.isVertexInTargets(target2));
    }

    // Covers toString with several targets and a self loop.
    @Test
    public void testToString() {
        Vertex testVertex1 = new Vertex(vertex1);
        testVertex1.setTarget(target1, weight1);
        testVertex1.setTarget(target2, weight2);
        testVertex1.setTarget(vertex1, weight1); // try a self loop.
        String testString = "Vertex = V1 has 3 targets";
        assertEquals(testString, testVertex1.toString());
    }

    // Covers toString with no targets.
    @Test
    public void testToStringNoTargets() {
        Vertex testVertex1 = new Vertex(vertex1);
        String testString = "Vertex = V1 has 0 targets";
        assertEquals(testString, testVertex1.toString());
    }

    /*
     * Testing ConcreteVerticesGraph
     */

    // Covers number of vertices = 0, number of edges = 0.
    @Test
    public void testToStringEmptyGraph() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        String testString = "Graph contains 0 vertices and 0 edges";
        assertEquals(testString, graph.toString());
    }

    // Covers number of vertices = 1, number of edges = 1.
    @Test
    public void testToStringOneVertexOneEdge() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.set(vertex1, vertex1, weight1);
        String testString = "Graph contains 1 vertices and 1 edges";
        assertEquals(testString, graph.toString());
    }

    // Covers number of vertices = 3, number of edges = 2.
    @Test
    public void testToStringThreeVertexTwoEdge() {
        ConcreteVerticesGraph graph = new ConcreteVerticesGraph();
        graph.set(vertex1, vertex2, weight1);
        graph.set(vertex2, vertex3, weight2);
        String testString = "Graph contains 3 vertices and 2 edges";
        assertEquals(testString, graph.toString());
    }
    
}
