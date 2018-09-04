/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import java.io.*;
import java.util.Scanner;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph.
 * Vertices in the graph are words. Words are defined as non-empty
 * case-insensitive strings of non-space non-newline characters. They are
 * delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>For example, given this corpus:
 * <pre>    Hello, HELLO, hello, goodbye!    </pre>
 * <p>the graph would contain two edges:
 * <ul><li> ("hello,") -> ("hello,")   with weight 2
 *     <li> ("hello,") -> ("goodbye!") with weight 1 </ul>
 * <p>where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>Given an input string, GraphPoet generates a poem by attempting to
 * insert a bridge word between every adjacent pair of words in the input.
 * The bridge word between input words "w1" and "w2" will be some "b" such that
 * w1 -> b -> w2 is a two-edge-long path with maximum-weight weight among all
 * the two-edge-long paths from w1 to w2 in the affinity graph.
 * If there are no such paths, no bridge word is inserted.
 * In the output poem, input words retain their original case, while bridge
 * words are lower case. The whitespace between every word in the poem is a
 * single space.
 * 
 * <p>For example, given this corpus:
 * <pre>    This is a test of the Mugar Omni Theater sound system.    </pre>
 * <p>on this input:
 * <pre>    Test the system.    </pre>
 * <p>the output poem would be:
 * <pre>    Test of the system.    </pre>
 * 
 * <p>PS2 instructions: this is a required ADT class, and you MUST NOT weaken
 * the required specifications. However, you MAY strengthen the specifications
 * and you MAY add additional methods.
 * You MUST use Graph in your rep, but otherwise the implementation of this
 * class is up to you.
 */
public class GraphPoet {
    
    private final Graph<String> graph = Graph.empty();
    
    // Abstraction function:
    //  GraphPoet consists of a Graph<String> such that vertices are case-insensitive words and edge weights are
    //  in order adjacency counts of at least 1
    // Representation invariant:
    //   - vertices must be lowercase
    //   - all edge weights are >= 1
    //   - all vertices in graph must belong to at least one edge
    //   - the input corpus file can't be blank
    //   - the input corpus file must contain at least two different words
    // Safety from rep exposure:
    //   - word affinity graph is private and final
    //   - vertices are of type String, which is immutable
    
    /**
     * Create a new poet with the graph from corpus (as described above).
     * 
     * @param corpus text file from which to derive the poet's affinity graph
     * @throws IOException if the corpus file cannot be found or read
     */
    public GraphPoet(File corpus) throws IOException {

        Scanner corpusReader = null;
        boolean startOfFile = true;
        String previousWord = "";
        int previousWeight = 0;

        try {
            corpusReader = new Scanner(new BufferedReader(new FileReader(corpus)));

            while (corpusReader.hasNext()) {
                String newWord = corpusReader.next();
                newWord = newWord.replaceAll("[^-A-Za-z0-9_]", "").toLowerCase();
                if (startOfFile && newWord != "") {
                    graph.add(newWord);
                    previousWord = newWord;
                }
                else if (newWord != "") {
                    graph.add(newWord);
                    if (graph.sources(newWord).containsKey(previousWord)) {
                        previousWeight = graph.sources(newWord).get(previousWord);
                        graph.set(previousWord, newWord, previousWeight ++);
                        previousWord = newWord;
                    }
                    else {
                        graph.set(previousWord, newWord, 1);
                        previousWord = newWord;
                    }
                }
                startOfFile = false;
            }
        } finally {
            if (corpusReader != null) {
                corpusReader.close();
            }
        }


    }
    
    // TODO checkRep
    
    /**
     * Generate a poem.
     * 
     * @param input string from which to create the poem
     * @return poem (as described above)
     */
    public String poem(String input) {
        throw new RuntimeException("not implemented");
    }
    
    // TODO toString()
    
}
