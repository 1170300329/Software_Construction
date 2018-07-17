/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Collections;
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
    
    // Testing strategy
    //   test the class with a String
    
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
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
    	//emptyInstance().vertices = new HashSet<>();
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // TODO other tests for instance methods of Graph
    @Test
    public void testAdd() {
    	Graph<String> graph = emptyInstance();
    	assertTrue("expected true here", graph.add("vertex"));
    	assertTrue(graph.vertices().contains("vertex"));
    	assertFalse(graph.add("vertex"));
    	assertTrue("expected flase here", graph.add("vertex1"));
    	assertTrue(graph.vertices().contains("vertex1"));
    }
    @Test
    public void testSet() {
    	Graph<String> graph = emptyInstance();
    	graph.add("source");
    	graph.add("target");
    	assertEquals(0, graph.set("source", "target", 5));
    	assertEquals(5, graph.set("source", "target", 6));
    	assertEquals(6, graph.set("source", "target", 0));
    	assertFalse(graph.targets("source").keySet().contains("targets"));
    
    	
    }
    @Test
    public void testRemove() {
    	Graph<String> graph = emptyInstance();
		graph.set("v1", "v2", 3);
		graph.set("v2", "v3", 3);
		graph.set("v4", "v2", 4);
		assertTrue(graph.remove("v2"));
		assertFalse(graph.targets("v1").keySet().contains("v2"));
		assertFalse(graph.sources("v3").keySet().contains("v2"));
		assertFalse(graph.targets("v4").keySet().contains("v2"));
		assertFalse(graph.vertices().contains("v2"));
	}
    @Test
    public void testVertices() {
    	Graph<String> graph = emptyInstance();
    	graph.add("v1");
    	graph.add("v2");
    	graph.add("v3");
    	graph.add("v4");
    	assertTrue(graph.vertices().size()==4);
    	assertTrue(graph.vertices().contains("v1"));
    	assertTrue(graph.vertices().contains("v2"));
    	assertTrue(graph.vertices().contains("v3"));
    	assertTrue(graph.vertices().contains("v4"));
    	
    }
    @Test
    public void testSources() {
    	Graph<String> graph = emptyInstance();
    	assertTrue(graph.add("a"));
    	assertTrue(graph.add("b"));
    	assertTrue(graph.add("c"));
    	assertTrue(graph.add("d"));
    	assertEquals(0, graph.set("a", "b", 3));
    	assertEquals(0, graph.set("a", "d", 5));
    	assertEquals(0, graph.set("c", "d", 4));
    	assertEquals(0, graph.set("b", "d", 6));
    	assertTrue(graph.sources("d").size()==3);
    	assertTrue(graph.sources("d").get("a")==5);
    	assertTrue(graph.sources("d").get("b")==6);
    	assertTrue(graph.sources("d").get("c")==4);  	
    }
    @Test
    public void testTargets() {
    	Graph<String> graph = emptyInstance();
    	assertTrue(graph.add("1"));
    	assertTrue(graph.add("2"));
    	assertTrue(graph.add("3"));
    	assertTrue(graph.add("4"));
    	assertEquals(0, graph.set("1", "2", 3));
    	assertEquals(0, graph.set("1", "4", 5));
    	assertEquals(0, graph.set("3", "4", 4));
    	assertEquals(0, graph.set("2", "4", 6));
    	assertTrue(graph.targets("1").size()==2);
    	assertTrue(graph.targets("1").get("2")==3);
    	assertTrue(graph.targets("1").get("4")==5);
    	assertTrue(graph.targets("2").get("4")==6);
    }    
}
