/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package poet;

import static org.junit.Assert.*;


import graph.Graph;
import org.junit.Test;

import java.io.IOException;
import java.io.File;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    /*
     * Partitions for contructor poem() method:
     *      - all spaces between words are one whitespace-char/newline char long
     *      - spaces between words have different whitespace/newline lengths
     *      - words contains letters only
     *      - words contains letters and numbers
     *      - words contains symbols, letters and numbers
     *      - corpus makes grammatical sense
     *      - corpus doesn't make grammatical sense
     *      - corpus contains repeated case-variant words
     *      - corpus contains 2 different words (minimum)
     *      - cospus contains +2 words
     *      - adjacency graph has multiple words equally weighted between
     *          two words in poem -- code must choose which on to use
     *      - for all words in poem, adjacency graph has no words weighted between them
     *
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // tests

    // test for Graph creation
    @Test
    public void GraphBuildingTest() throws IOException {
        File givenCorpus = new File("test/poet/testText.txt");
        GraphPoet graph = new GraphPoet(givenCorpus);
    }

    // corpus doesn't exist
    @Test (expected = IOException.class)
    public void testNonexistentCorpus() throws IOException {
        File givenCorpus = new File("test/poet/fooled-you.txt");
        GraphPoet graph = new GraphPoet(givenCorpus);
    }

    // corpus is empty
    @Test
    public void testPoemEmptyCorpus() throws IOException {
        File givenCorpus = new File("test/poet/empty.txt");
        GraphPoet graph = new GraphPoet(givenCorpus);
        String input = "test the empty one";
        String outputPoem = graph.poem(input);
        assertEquals(input, outputPoem);
    }

    // input is empty
    @Test
    public void testPoemEmptyInput() throws IOException {
        File givenCorpus = new File("test/poet/mugar-omni-theater.txt");
        GraphPoet graph = new GraphPoet(givenCorpus);
        String input = "";
        String outputPoem = graph.poem(input);
        assertEquals(input, outputPoem);
    }

    // all spaces are one whitespace/newline char long, words contains letters only
    @Test
    public void testPoemLettersOnlySingleSpaces() throws IOException {
        File givenCorpus = new File("test.poem/mugar-omni-theater.txt");
        GraphPoet graph = new GraphPoet(givenCorpus);
        String input = "Test the Theater system";
        String outputPoem = graph.poem(input);
        String correctPoem = "Test of the Theater sound system";
        assertEquals(correctPoem, outputPoem);
    }

    // spaces have different space lenghts, words contains letters and numbers
    @Test
    public void testPoemLettersNumbersDifferentSpaces() throws IOException {
        File givenCorpus = new File("test.poem/mugar-omni-theater.txt");
        GraphPoet graph = new GraphPoet(givenCorpus);
        String input = "Stick a line down and quick and you're done";
        String outputPoem = graph.poem(input);
        String correctPoem = "Stick a straight line down and very quick and you're done";
        assertEquals(correctPoem, outputPoem);
    }

    // all spaces are newline chars, words contains symbols letters and numbers
    @Test
    public void testPoemAllWordChars() throws IOException {
        File giveCorpus = new File("test/poet/newLineAndAllChars.txt");
        GraphPoet graph = new GraphPoet(giveCorpus);
        String input = "Watch them & over bones and you will see 6's and 7's";
        String outputPoem = graph.poem(input);
        String correctPoem = "Watch them wrangling & fighting over raw bones and you will see 6's and 7's";
        assertEquals(correctPoem, outputPoem);
    }

    // corpus doesn't make sense, contains 2 words
    @Test
    public void testPoemTwoWords() throws IOException {
        File giveCorpus = new File("test/poet/TwoWordsNoSense.txt");
        GraphPoet graph = new GraphPoet(giveCorpus);
        String input = "This poem should not change SingtomeOMuse ofthemanoftwistsandturns";
        String outputPoem = graph.poem(input);
        String correctPoem = "This poem should not change SingtomeOMuse ofthemanoftwistsandturns";
        assertEquals(correctPoem, outputPoem);
    }

    // corpus contains repeated case-variant words
    @Test
    public void testPoemRepeatedCaseVariant() throws IOException {
        File giveCorpus = new File("test/poet/repeatedCorpusCaseVariantWords.txt");
        GraphPoet graph = new GraphPoet(giveCorpus);
        String input = "Will his bereaved on this grievous day, his day";
        String outputPoem = graph.poem(input);
        String correctPoem = "Will to his bereaved on this grievous day, his dying day";
        assertEquals(correctPoem, outputPoem);
    }

    // adjacency graph has multiple words equally weighted between two words in poem
    @Test
    public void testPoemMultipleBridgeWords() throws IOException {
        File giveCorpus = new File("test/poet/multipleBridgesPossible.txt");
        GraphPoet graph = new GraphPoet(giveCorpus);
        String input = "Aren't they the of all the days of our lives?";
        String outputPoem = graph.poem(input);
        String oneCorrectPoem = "Aren't they the best of all the days of our lives?";
        String otherCorrectPoem = "Aren't they the worst of all the days of our lives";
        assertTrue(outputPoem.equals(oneCorrectPoem) || outputPoem.equals(otherCorrectPoem));
    }

    // for all poem words, adjacency graph has no words weighted between them
    @Test
    public void testPoemNoBridgeWords() throws IOException {
        File giveCorpus = new File("test/poet/mugar-omni-theater.txt");
        GraphPoet graph = new GraphPoet(giveCorpus);
        String input = "his poem should not change";
        String outputPoem = graph.poem(input);
        String correctPoem = input;
        assertEquals(correctPoem, outputPoem);
    }

}
