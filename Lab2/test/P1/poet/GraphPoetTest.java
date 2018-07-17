/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    //   to test the class with the test.txt.
	// text in test.txt include edges with different weights.
	//e.g. weight=0,weight=1,weight=2
    
	File corpus = new File("src/P1/poet/test.txt");
	
	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // TODO tests
    @Test
    public void poemTest() throws IOException {
    	GraphPoet gp = new GraphPoet(corpus);
    	assertEquals("I just think this flower is good." + 
    			"", gp.poem("I think this is good."));
    }
    @Test
    public void toStringTest() throws IOException {
    	GraphPoet gp = new GraphPoet(corpus);
    	assertTrue(gp.toString().contains("this->flower 2"));
    	assertTrue(gp.toString().contains("this->child 1"));
    	assertTrue(gp.toString().contains("i->think 1"));
    }
}
