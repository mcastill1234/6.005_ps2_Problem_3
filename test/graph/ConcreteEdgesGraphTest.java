/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 * Test cases based in Dmytro Shaban.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {

    /*
     * Testing strategy for Edge class
     *
     * Edge()
     *   Constructor of edge object given source, target and weight. Cases:
     *      1. Source equal target: yes, no
     *      2. Test with different strings
     *      3. weight = 1, n.
     * getSource()
     *   returns source of edge, test with different strings.
     * getTarget()
     *   returns target of edge, test with different strings.
     * getWeight()
     *   returns weight of edge, test with different values.
     * isSame()
     *   returns true if edge is equal to other edge. Cases:
     *      1. targets are equal
     *      2. sources are equalds
     * toString()
     *   returns a string composed of source, target and weight of edge. Cases:
     *      1. source equal target
     *      2. weight =  1, n
     */

    /*
     * Testing strategy for ConcreteEdgesGraph class
     *
     * toString()
     *   vertices : 0, 1, n
     *   edges : 0, 1, n
     */


    private final String vertex1 = "V1";
    private final String vertex2 = "V";
    private final String vertex3 = "";

    private final int weight1 = 1;
    private final int weight2 = 2;
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }

    /*
     * Testing Edge...
     */

    // Covers construct edge with equal vertices, weight = 1.
    @Test
    public void testEdgeEqualVertices() {
        Edge edge = new Edge(vertex1, vertex1, weight1);
        assertEquals(vertex1, edge.getSource());
        assertEquals(vertex1, edge.getTarget());
        assertEquals(weight1, edge.getWeight());
    }

    // Covers construct edge with different vertices, weight = n.
    @Test
    public void testEdgeDifferentVertices() {
        Edge edge = new Edge(vertex1, vertex2, weight2);
        assertEquals(vertex1, edge.getSource());
        assertEquals(vertex2, edge.getTarget());
        assertEquals(weight2, edge.getWeight());
    }

    // Covers construct edge with different vertices, empty target.
    @Test
    public void testEdgeEmptyTarget() {
        Edge edge = new Edge(vertex2, vertex3, weight1);
        assertEquals(vertex2, edge.getSource());
        assertEquals(vertex3, edge.getTarget());
        assertEquals(weight1, edge.getWeight());
    }

    // Covers construct edge with different vertices, empty source.
    @Test
    public void testEdgeEmptySource() {
        Edge edge = new Edge(vertex3, vertex2, weight2);
        assertEquals(vertex3, edge.getSource());
        assertEquals(vertex2, edge.getTarget());
        assertEquals(weight2, edge.getWeight());
    }

    // Covers equal edges for isSame method.
    @Test
    public void testEdgeSourcesAndTargetsEqual() {
        Edge edgeThis = new Edge(vertex1, vertex2, weight1);
        Edge edgeOther = new Edge(vertex1, vertex2, weight1);
        assertTrue(edgeThis.isSame(edgeOther));
    }

    // Covers different edges for isSame method.
    @Test
    public void testEdgeSourcesAndTargetDifferent() {
        Edge edgeThis = new Edge(vertex1, vertex2, weight1);
        Edge edgeOther = new Edge(vertex2, vertex1, weight1);
        assertFalse(edgeThis.isSame(edgeOther));
    }

    // Covers equal source and target for toString method.
    @Test
    public void testToStringSourceEqualTarget() {
        Edge edge = new Edge(vertex1, vertex1, weight1);
        String modelString = "Source = V1 Target = V1 Weight = 1";
        assertEquals(modelString, edge.toString());
    }

    // Covers different source and target for toString method.
    @Test
    public void testToStringSourceAndTargetDifferent() {
        Edge edge = new Edge(vertex1, vertex2, weight2);
        String modelString = "Source = V1 Target = V Weight = 2";
        assertEquals(modelString, edge.toString());
    }

    /*
     * Testing ConcreteEdgesGraph...
     */

    // Covers number of vertices = 0, number of edges = 0.
    @Test
    public void testToStringEmptyGraph() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        String modelString = "Graph contains 0 vertices and 0 edges";
        assertEquals(modelString, graph.toString());
    }

    // Covers number of vertices = 1, number of edges = 1.
    @Test
    public void testToStringOneVertexOneEdge() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.set(vertex1, vertex1, weight1);
        String modelString = "Graph contains 1 vertices and 1 edges";
        assertEquals(modelString, graph.toString());
    }

    // Covers number of vertices = 3, number of edges = 2.
    @Test
    public void testToStringThreeVertexTwoEdge() {
        ConcreteEdgesGraph graph = new ConcreteEdgesGraph();
        graph.set(vertex1, vertex2, weight1);
        graph.set(vertex2, vertex3, weight2);
        String modelString = "Graph contains 3 vertices and 2 edges";
        assertEquals(modelString, graph.toString());
    }


}
