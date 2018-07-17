/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;
import static org.junit.Assert.*;

import org.junit.Test;

import P1.graph.ConcreteEdgesGraph;
import P1.graph.Graph;


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
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    // test for empty graph 
    // test the graph without edges
    // test formal graph
    @Test
    public void toStringofEmptyGraphTest() {
    	Graph<String> graph = emptyInstance();
    	assertEquals("empty graph!", graph.toString());
    }
    
    @Test 
    public void toStringofOnlyVertexGraphTest() {
    	Graph<String> graph = emptyInstance();
    	graph.add("A");
    	graph.add("B");
    	assertEquals("no edges!", graph.toString());
    }
    
    @Test 
    public void toStringofNormalGraphTest() {
    	Graph<String> graph = emptyInstance();
    	graph.add("a");
    	graph.add("b");
    	graph.add("c");
    	graph.add("d");
    	graph.set("a", "b", 3);
    	graph.set("c", "d", 4);
    	graph.set("b", "c", 5);
    	//System.out.println(graph.toString());
    	assertTrue(graph.toString().indexOf("a->b 3") > -1);
    	assertTrue(graph.toString().indexOf("c->d 4") > -1);
    	assertTrue(graph.toString().indexOf("b->c 5") > -1);
    }
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   new an edge to test the class
    
    // TODO tests for operations of Edge
    @Test
    public void getSourceTest() {
    	//Graph<String> graph = emptyInstance();
    	Edge<String> e = new Edge<>("source", "target", 6);
    	assertEquals("source", e.getSource());
	}
    @Test
    public void getTargetTest() {
    	Edge<String> edge = new Edge<>("source", "target", 6);
    	assertEquals("target", edge.getTarget());
    }
    @Test
    public void getWeightTest() {
    	Edge<String> edge = new Edge<>("source", "target", 6);
    	assertEquals(6, edge.getWeight());
    }
    @Test
    public void toStringTest() {
    	Edge<String> edge = new Edge<>("source", "target", 6);
    	assertEquals("source->target 6", edge.toString());
    }
    @Test
    public void myequalTest() {
    	Edge<String> edge = new Edge<>("source", "target", 6);
    	Edge<String> edge1 = new Edge<>("source", "target", 3);
    	assertTrue(edge1.myequal(edge));

    }
    
    
}
