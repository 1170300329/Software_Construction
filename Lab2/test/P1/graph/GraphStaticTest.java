/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Collections;

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
    
    // TODO test other vertex label types in Problem 3.2
    @Test
    public void testAdd() {
    	Graph<Integer> graph =Graph.empty();
    	Integer a=1;
    	Integer a1=2;
    	assertTrue("expected true here", graph.add(a));
    	assertTrue(graph.vertices().contains(a));
    	assertFalse(graph.add(a));
    	assertTrue("expected flase here", graph.add(a1));
    	assertTrue(graph.vertices().contains(a1));
    }
    @Test
    public void testSet() {
    	Graph<Integer> graph = Graph.empty();
    	Integer a=1;
    	Integer a1=2;
    	graph.add(a);
    	graph.add(a1);
    	assertEquals(0, graph.set(a, a1, 5));
    	assertEquals(5, graph.set(a, a1, 6));
    	assertEquals(6, graph.set(a, a1, 0));
    	assertFalse(graph.targets(a).keySet().contains(a1));
    
    	
    }
    @Test
    public void testRemove() {
    	Graph<Integer> graph = Graph.empty();
    	Integer a1=1;
    	Integer a2=2;
    	Integer a3=3;
    	Integer a4=4;
		graph.set(a1, a2, 3);
		graph.set(a2, a3, 3);
		graph.set(a4, a2, 4);
		assertTrue(graph.remove(a2));
		assertFalse(graph.targets(a1).keySet().contains(a2));
		assertFalse(graph.sources(a3).keySet().contains(a2));
		assertFalse(graph.targets(a4).keySet().contains(a2));
		assertFalse(graph.vertices().contains(a2));
	}
    @Test
    public void testVertices() {
    	Graph<Integer> graph = Graph.empty();
    	Integer a1=1;
    	Integer a2=2;
    	Integer a3=3;
    	Integer a4=4;
    	graph.add(a1);
    	graph.add(a2);
    	graph.add(a3);
    	graph.add(a4);
    	assertTrue(graph.vertices().size()==4);
    	assertTrue(graph.vertices().contains(a1));
    	assertTrue(graph.vertices().contains(a2));
    	assertTrue(graph.vertices().contains(a3));
    	assertTrue(graph.vertices().contains(a4));
    	
    }
    @Test
    public void testSources() {
    	Graph<Integer> graph = Graph.empty();
    	Integer a1=1;
    	Integer a2=2;
    	Integer a3=3;
    	Integer a4=4;
    	assertTrue(graph.add(a1));
    	assertTrue(graph.add(a2));
    	assertTrue(graph.add(a3));
    	assertTrue(graph.add(a4));
    	assertEquals(0, graph.set(a1, a2, 3));
    	assertEquals(0, graph.set(a1, a4, 5));
    	assertEquals(0, graph.set(a3, a4, 4));
    	assertEquals(0, graph.set(a2, a4, 6));
    	assertTrue(graph.sources(a4).size()==3);
    	assertTrue(graph.sources(a4).get(a1)==5);
    	assertTrue(graph.sources(a4).get(a2)==6);
    	assertTrue(graph.sources(a4).get(a3)==4);  	
    }
    @Test
    public void testTargets() {
    	Graph<Integer> graph = Graph.empty();
    	Integer a1=1;
    	Integer a2=2;
    	Integer a3=3;
    	Integer a4=4;
    	assertTrue(graph.add(a1));
    	assertTrue(graph.add(a2));
    	assertTrue(graph.add(a3));
    	assertTrue(graph.add(a4));
    	assertEquals(0, graph.set(a1, a2, 3));
    	assertEquals(0, graph.set(a1, a4, 5));
    	assertEquals(0, graph.set(a3, a4, 4));
    	assertEquals(0, graph.set(a2, a4, 6));
    	assertTrue(graph.targets(a1).size()==2);
    	assertTrue(graph.targets(a1).get(a2)==3);
    	assertTrue(graph.targets(a1).get(a4)==5);
    	assertTrue(graph.targets(a2).get(a4)==6);
    }    
}
