/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 * This implementation of Graph is based on  Dmytro Shaban solution with some changes. I'm trying out an easier
 * program for checkRep which is much more complex than using a sorted list.
 * I also propose set() without exceptions for notFound edge and some variations in the code.
 */
package graph;

import java.util.*;



/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   Represents a mutable weighted directed graph with labeled vertices
    // Representation invariant:
    //   Edges are not duplicate
    // Safety from rep exposure:
    //   Fields are declared private final and observers return copies of the mutable Graph
    
    // Check rep invariant
    private void checkRep() {
        assert(this.isEdgeNotDuplicate()) : "Edges are duplicate";
        assert(this.isVerticesNotNull()) : "Some vertex is null";
    }

    /**
     * Check that edges are not duplicate
     * Using nested loops - O(n^2).
     * @return true if edges are not duplicate
     */
     private boolean isEdgeNotDuplicate() {
        if (edges.size() > 1) {
            for (int i=0; i<edges.size(); i++) {
                for (int j=0; j<edges.size(); j++) {
                    if (i != j && edges.get(i).getSource() == edges.get(j).getSource() &&
                            edges.get(i).getTarget() == edges.get(j).getTarget()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Check that vertices are not null
     * @return true if all vertices are not null
     */
    private boolean isVerticesNotNull() {
        for (L vertex : vertices) {
            if (vertex == null) { return false;}
        }
        return true;
    }
    
    @Override
    public boolean add(L vertex) {
        boolean result = false;
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            result = true;
        }
        checkRep();
        return result;
    }
    
    @Override
    public int set(L source, L target, int weight) {
        if (weight == 0) {
            if (isEdgeInGraph(source, target)) {
                Edge<L> edgeToSet = findEdge(source, target);
                int result = edgeToSet.getWeight();
                edges.remove(edgeToSet);
                checkRep();
                return result;
            }
            else {
                checkRep();
                return 0;
            }
        }
        else {
            if (isEdgeInGraph(source, target)) {
                Edge<L> edgeToSet = findEdge(source, target);
                int result = edgeToSet.getWeight();
                edges.remove(edgeToSet);
                edges.add(new Edge(source, target, weight));
                checkRep();
                return result;
            }
            else {
                add(source);
                add(target);
                edges.add(new Edge(source, target, weight));
                checkRep();
                return 0;
            }
        }
    }

    /**
     * Check if edge is in list of edges
     * @param source source vertex
     * @param target target vertex
     * @return true if edge is in list of edges, false otherwise
     */
    private boolean isEdgeInGraph(L source, L target) {
        for (Edge<L> edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)){
                return true;
            }
        }
        return false;
    }

    /**
     * Find an edge in list of edges given source and target vertices
     * @param source source of edge
     * @param target target of edge
     * @return edge if it's in list of edges, empty edge if it isn't
     */
    private Edge<L> findEdge(L source, L target) {
        for (Edge<L> edge : edges) {
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                return edge;
            }
        }
        return new Edge("", "", 0);
    }

    @Override
    public boolean remove(L vertex) {
        boolean result = false;
        if (vertices.contains(vertex)) {
            vertices.remove(vertex);
            List<Edge<L>> edgesForRemove = findEdgesBySource(vertex);
            edgesForRemove.addAll(findEdgesByTarget(vertex));
            edges.removeAll(edgesForRemove);
            result = true;
        }
        checkRep();
        return result;
    }


    /**
     * Search edges with given source in list of edges
     * @param source source of edges
     * @return list of found edges
     */
    private List<Edge<L>> findEdgesBySource(L source) {
        List<Edge<L>> result = new ArrayList<>();
        for (Edge<L> edge : edges) {
            if (edge.getSource().equals(source)) {
                result.add(edge);
            }
        }
        return result;
    }

    /**
     * Search edges with give target in list of edges
     * @param target target of edges
     * @return list of found edges
     */
    private List<Edge<L>> findEdgesByTarget(L target) {
        List<Edge<L>> result = new ArrayList<>();
        for (Edge<L> edge : edges) {
            if (edge.getTarget().equals(target)) {
                result.add(edge);
            }
        }
        return result;
    }
    
    @Override
    public Set<L> vertices() {
        checkRep();
        return new HashSet<>(vertices);
    }
    
    @Override
    public Map<L, Integer> sources(L target) {
        Map<L, Integer> result = new HashMap<>();
        for (Edge<L> edge : edges) {
            if (edge.getTarget().equals(target)) {
                result.put(edge.getSource(), edge.getWeight());
            }
        }
        checkRep();
        return result;
    }
    
    @Override
    public Map<L, Integer> targets(L source) {
        Map<L, Integer> result = new HashMap<>();
        for (Edge<L> edge : edges) {
            if (edge.getSource().equals(source)) {
                result.put(edge.getTarget(), edge.getWeight());
            }
        }
        checkRep();
        return result;
    }
    
    @Override
    public String toString() {
        return "Graph contains " + vertices.size() + " vertices and " + edges.size() + " edges";
    }
    
}

/**
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * Each edge has a source vertex and a target vertex.
 * Both vertices must have the same immutable type.
 * Edges are directed, they point from source to target.
 * Each edge has a positive weight of type int.
 * The vertices of an edge don't necessarily have to exist in the graph.
 */
class Edge<L> {
    
    private L source;
    private L target;
    private int weight;
    
    // Abstraction function:
    //   Represents the weighted directed edge from source to target
    // Representation invariant:
    //   Weight is positive
    // Safety from rep exposure:
    //   All fields are private and all types in the rep are immutable



    /**
     * Create a new edge object
     * @param source vertex
     * @param target vertex
     * @param weight
     */
    public Edge(L source, L target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }

    // Check rep invariant
    private void checkRep() {
        assert (weight > 0) : "Invalid value of weight";
        assert (source != null) : "sourse not exists";
        assert (target != null) : "target not exists";
    }


    /**
     * Get source of the edge
     * @return source vertex of edge
     */
    public L getSource() {
        checkRep();
        return source;
    }

    /**
     * Get target of the edge
     * @return target vertex of edge
     */
    public L getTarget() {
        checkRep();
        return target;
    }

    /**
     * Get weight of the edge
     * @return weight of edge
     */
    public int getWeight() {
        checkRep();
        return weight;
    }

    /**
     * Compare two edges
     * @param other edge
     * @return true if edges are equal
     */
    public boolean isSame(Edge other) {
        boolean result = false;
        if (source.equals(other.source) && target.equals(other.target)) {
            result = true;
        }
        checkRep();
        return result;
    }

    @Override
    public String toString() {
        return "Source = " + source + " Target = " + target + " Weight = " + weight;
    }
    
}
