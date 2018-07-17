/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        return new ConcreteVerticesGraph<String>();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //  test empty graph
    //  test a graph only contains vertices
    //  test a normal graph
    
    // TODO tests for ConcreteVerticesGraph.toString()
    @Test 
    public void toStringofEmptyGraphTest() {
    	Graph<String> graph = emptyInstance();
    	assertEquals("The graph is empty!", graph.toString());
    }
//    
    @Test 
    public void toStringofOnlyVertexGraphTest() {
    	Graph<String> graph = emptyInstance();
    	graph.add("A");
    	graph.add("B");
    	graph.add("C");
    	assertEquals("A\nB\nC\n", graph.toString());
    }
    
    @Test 
    public void toStringofNormalGraphTest() {
    	Graph<String> graph = emptyInstance();
    	graph.add("A");
    	graph.add("B");
    	graph.add("C");
    	graph.add("D");
    	graph.add("E");
    	graph.set("A", "B", 3);
    	graph.set("A", "C", 4);
    	graph.set("B", "D", 5);
    	graph.set("D", "E", 6);
    	String str = graph.toString();
    	assertTrue(str.contains("A->B 3"));
    	assertTrue(str.contains("A->C 4"));
    	assertTrue(str.contains("B->D 5"));
    	assertTrue(str.contains("D->E 6"));
    	
    }
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   test each method with a vertex<String>
    
    // TODO tests for operations of Vertex
    @Test
    public void getVertexnameTest() {
    	Vertex<String> temp=new Vertex<String>("A");
    	assertEquals("A", temp.getVertexname());
    }
    
    @Test
    public void toStringTest() {
    	Vertex<String> temp=new Vertex<String>("A");
    	assertEquals("A\n", temp.toString());
    	String temp1="B";
    	String temp2="C";
    	Integer a = new Integer(3);
    	Integer b = new Integer(4);
    	temp.put(temp1, a);
    	temp.put(temp2, b);
    	assertTrue(temp.toString().contains("A->B 3"));
    	assertTrue(temp.toString().contains("A->C 4"));
    }
    @Test
    public void putTest() {
    	Vertex<String> temp=new Vertex<String>("A");
    	String temp1="B";
    	String temp2="C";
    	Integer a = new Integer(3);
    	Integer b = new Integer(4);
    	temp.put(temp1, 3);
    	temp.put(temp2, 4);
    	assertEquals(a, temp.getMap().get(temp1));
    	assertEquals(b, temp.getMap().get(temp2));

    }
    @Test
    public void getMapTest() {
    	Vertex<String> temp=new Vertex<String>("A");
    	String temp1="B";
    	String temp2="C";
    	Integer a = new Integer(3);
    	Integer b = new Integer(4);
    	temp.put(temp1, 3);
    	temp.put(temp2, 4);
    	assertEquals(2, temp.getMap().size());
    	assertEquals(a,temp.getMap().get(temp1));
    	assertEquals(b,temp.getMap().get(temp2));

    }
    @Test
    public void removeTest() {
    	Vertex<String> temp=new Vertex<String>("A");
    	String temp1="B";
    	String temp2="C";
    	Integer a = new Integer(3);
    	Integer b = new Integer(4);
    	temp.put(temp1, a);
    	temp.put(temp2, b);
    	temp.remove(temp2);
    	assertEquals(1, temp.getMap().size());
    	
    }
}
