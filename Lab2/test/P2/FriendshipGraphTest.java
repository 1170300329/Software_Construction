package P2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;


public class FriendshipGraphTest {
    // Testing strategy
	//test the class with a graph.
	//there are connected person and not connected person
	/**
     * Tests that assertions are enabled.
     */
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
    FriendshipGraph friendshipGraph = new FriendshipGraph();
   	Person a = new Person("A");
   	Person b = new Person("B");
   	Person c = new Person("C");
   	Person d = new Person("D");
   	Person e = new Person("E");
   	Person f = new Person("F");
   	Person g = new Person("G");
   	/**
     * Test addVertex
     */
    @Test
    public void addVertexTest() {
    	friendshipGraph.addVertex(a);
    	friendshipGraph.addVertex(b);
    	friendshipGraph.addVertex(c);
    	friendshipGraph.addVertex(d);
    	friendshipGraph.addVertex(e);
    	friendshipGraph.addVertex(f);
    	friendshipGraph.addVertex(g);
    	assertTrue(friendshipGraph.graph.vertices().contains(a));
    	assertTrue(friendshipGraph.graph.vertices().contains(b));
    	assertTrue(friendshipGraph.graph.vertices().contains(c));
    	assertTrue(friendshipGraph.graph.vertices().contains(d));
    	assertTrue(friendshipGraph.graph.vertices().contains(e));
    	assertTrue(friendshipGraph.graph.vertices().contains(f));
    	assertTrue(friendshipGraph.graph.vertices().contains(g));
    	
    }
    
    /**
     * Test addEdge
     */
    
    @Test
    public void addEdgeTest() {
    	assertTrue(friendshipGraph.addEdge(a, b).contains(b));
    	assertTrue(friendshipGraph.addEdge(b, c).contains(c));
    }
    /**
     * Test getDistance
     */
    
    @Test
    public void getDistanceTest() {
    	friendshipGraph.addVertex(a);
    	friendshipGraph.addVertex(b);
    	friendshipGraph.addVertex(c);
    	friendshipGraph.addVertex(d);
    	friendshipGraph.addVertex(e);
    	friendshipGraph.addVertex(f);
    	friendshipGraph.addVertex(g);
    	friendshipGraph.addEdge(a, b);
		friendshipGraph.addEdge(b, a);
		friendshipGraph.addEdge(a, c);
		friendshipGraph.addEdge(c, a);
		friendshipGraph.addEdge(a, d);
		friendshipGraph.addEdge(d, a);
		friendshipGraph.addEdge(b, f);
		friendshipGraph.addEdge(f, b);
		friendshipGraph.addEdge(c, e);
		friendshipGraph.addEdge(e, c);
		friendshipGraph.addEdge(f, e);
		friendshipGraph.addEdge(e, f);
		friendshipGraph.addEdge(c, d);
		friendshipGraph.addEdge(d, c);
    	assertEquals(1, friendshipGraph.getDistance(a, b));
    	assertEquals(2, friendshipGraph.getDistance(a, f));
    	assertEquals(0, friendshipGraph.getDistance(a, a));
    	assertEquals(-1, friendshipGraph.getDistance(a, g));
    	assertEquals(1, friendshipGraph.getDistance(e, f));
    }
}
