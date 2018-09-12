/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;
import java.util.zip.CheckedInputStream;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   Represents a mutable weighted directed graph with labeled vertices
    // Representation invariant:
    //   Vertices are not duplicate
    // Safety from rep exposure:
    //   Fields are private final and observers return either immutable types or copies of mutable types.

    // Check rep invariant
    private void checkRep() {
        assert(this.isVerticesNotDuplicate()) : "Vertices are duplicate";
    }

    /**
     * Verify that there are no duplicated vertices in this list.
      * @return true if there are no duplicates, false otherwise.
     */
    private boolean isVerticesNotDuplicate() {
        if (vertices.size() > 1) {
            for (int i = 0; i < vertices.size(); i++) {
                for (int j=0; j < vertices.size(); j++) {
                    if (i != j && vertices.get(i).getName().equals(vertices.get(j).getName())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    @Override
    public boolean add(L vertex) {
        boolean result = false;
        if (!hasVertex(vertex)) {
            vertices.add(new Vertex(vertex));
            result = true;
        }
        checkRep();
        return result;
    }
    
    @Override
    public int set(L source, L target, int weight) {
        int result = 0;
        if (weight > 0) {
            if (!hasVertex(source)) vertices.add(new Vertex(source));
            if (!hasVertex(target)) vertices.add(new Vertex(target));
            for (Vertex<L> testVertex : vertices) {
                if (testVertex.getName().equals(source)) {
                    if (testVertex.isVertexInTargets(target)) {
                        result = testVertex.getWeight(target);
                    }
                    testVertex.setTarget(target, weight);
                    checkRep();
                    return result;
                }
            }
        } else if (weight == 0) {
            if(hasVertex(source)) {
                for (Vertex<L> testVertex : vertices) {
                    if (testVertex.getName().equals(source) && testVertex.getTargets().contains(target)) {
                        result = testVertex.getWeight(target);
                        testVertex.setTarget(target, weight);
                        checkRep();
                        return result;
                    }
                }
            }
        }
        return 0;
    }
    
    @Override
    public boolean remove(L vertex) {
        boolean result = false;
        int remIndex = 0;
        if (hasVertex(vertex)) {
            for (Vertex<L> testVertex : vertices) {
                if (testVertex.getName().equals(vertex)) {
                    remIndex = vertices.indexOf(testVertex);
                }
                if (testVertex.getTargets().contains(vertex)) {
                    testVertex.setTarget(vertex, 0);
                }
            }
            vertices.remove(remIndex);
            result = true;
        }
        checkRep();
        return result;
    }
    
    @Override
    public Set<L> vertices() {
        Set<L> graphVertices = new HashSet<>();
        for (Vertex<L> testVertex : vertices) {
            graphVertices.add(testVertex.getName());
        }
        checkRep();
        return graphVertices;
    }
    
    @Override
    public Map<L, Integer> sources(L target) {
        Map<L, Integer> result = new HashMap<>();
        for (Vertex<L> testVertex : vertices) {
            if (testVertex.getTargets().contains(target)) {
                result.put(testVertex.getName(), testVertex.getWeight(target));
            }
        }
        checkRep();
        return result;
    }
    
    @Override
    public Map<L, Integer> targets(L source) {
        Map<L, Integer> result = new HashMap<>();
        for (Vertex<L> vertex : vertices) {
            if (vertex.getName().equals(source)) {
                result = vertex.getTargetsMap();
            }
        }
        checkRep();
        return result;
    }

    /**
     * Helper function to verify if vertices has a given vertex in its list
     * @param vertex a label
     * @return true if this lists contains a vertex with the given label name, false otherwise.
     */
    private boolean hasVertex(L vertex) {
        for (Vertex<L> testVertex : vertices) {
            if (testVertex.getName().equals(vertex)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        int numEdges = 0;
        for (Vertex<L> vertex : vertices) {
            numEdges += vertex.getTargets().size();
        }
        return "Graph contains " + vertices.size() + " vertices and " + numEdges + " edges";
    }
    
}

/**
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * Each Vertex is an object in the adjacency list representation of graph. The Vertex has a name and a map of targeted
 * vertices as keys and the weight of each outgoing edge as values.
 * Targets are not duplicate by default. Keys in map form a set.
 */
class Vertex<L> {
    
    private L nodeName;
    private Map<L, Integer> targets;
    
    // Abstraction function:
    //   Represents a vertex in the adjacency list of Graph with its outgoing edges and their respective weights.
    // Representation invariant:
    //   Targets are not duplicate by default and weights are positive.
    // Safety from rep exposure:
    //   Fields are private and observers return copies of the mutable fields


    /**
     * Create a new Vertex object with given name and initializes Vertex targets map.
     * @param nodeName label for new Vertex
     */
    public Vertex(L nodeName) {
        this.nodeName = nodeName;
        this.targets = new HashMap<>();
    }

    // Check rep invariant
    private void checkRep() {
        assert (this.areWeightsPositive()) : "Invalid weight found";
    }

    /**
     * Verify that all weights are positive
     * @return true if all weights are positive, false otherwise.
     */
    private boolean areWeightsPositive() {
        for (L testString : targets.keySet()) {
            if (targets.get(testString) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * If weight is positive adds or changes the edge from Vertex to target. Otherwise, it deletes the target.
     * @param target target name.
     * @param weight weight of edge from Vertex to target
     */
    public void setTarget(L target, int weight) {
        if (isVertexInTargets(target)) {
            if (weight == 0) {
                targets.remove(target);
                checkRep();
            }
            else {
                targets.replace(target, weight);
                checkRep();
            }
        }
        else {
            if (weight == 0) {
                return;
            }
            else {
                targets.put(target, weight);
                checkRep();
            }
        }
    }

    /**
     * Get name of Vertex
     * @return label of Vertex
     */
    public L getName() {
        return nodeName;
    }

    /**
     * Get all targeted vertices from Vertex.
     * @return the set of targets out from Vertex.
     */
    public Set<L> getTargets() {
        return new HashSet<>(targets.keySet());
    }

    /**
     * Get a all targeted vertices from Vertex and the weight of each edge.
     * @return a map where the keys are the set of labels of vertices targeted by Vertex and the value for each key
     * is the (nonzero) weight of each edge from Vertex to targets.
     */
    public Map<L, Integer> getTargetsMap() {
        return new HashMap<>(targets);
    }

    /**
     * Get the weight of the edge from Vertex to target.
     * @param target a label
     * @return the weight of the edge from Vertex to target.
     */
    public int getWeight(L target) {
        return targets.get(target);
    }

    /**
     * Helper functions to verify if a given target is part of the set of targets that Vertex is pointing to.
     * @param target a label
     * @return true if target is in the set of Vertex targets. False otherwise.
     */
    public boolean isVertexInTargets(L target) {
        if (targets.keySet().contains(target)){
            return true;
        }
        else {
            return false;
        }

    }

    @Override
    public String toString() {
        return "Vertex = " + nodeName + " has " + targets.size() + " targets";
    }

}
