package src;
import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

import src.edge.Edge;
import src.edge.NetworkConnection;
import src.factory.GraphFactory;
import src.graph.ConcreteGraph;
import src.vertex.Computer;
import src.vertex.Router;
import src.vertex.Vertex;

public class NetworkTopologyTest {

	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
	@Test
	public void testgetEdge() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/src/test1.txt");
		assertEquals(2,g.getEdge().size());
	}
	@Test
	public void testgetVertex() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/src/test1.txt");
		assertEquals(3,g.getVertex().size());
	}
	@Test 
	public void testverRemove() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/src/test1.txt");
		assertEquals(3,g.getVertex().size());
		Vertex vertex=g.getVertex().get(1);
		g.removeVertex(vertex);
		assertEquals(2,g.getVertex().size());
		assertFalse(g.getVertex().contains(vertex));
	}
	@Test
	public void testedgeRemove() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/src/test1.txt");
		assertEquals(2,g.getEdge().size());
		Edge edge=g.getEdge().get(1);
		g.edgeRemove(edge);
		assertEquals(1,g.getEdge().size());
		assertFalse(g.getEdge().contains(edge));
	}
	@Test
	public void testaddVertex() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/src/test1.txt");
		Computer vertex=new Computer("sdf");
		assertEquals(3, g.getVertex().size());
		g.addVertex(vertex);
		assertEquals(4, g.getVertex().size());
	}
	@Test
	public void testremoveVertex() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/src/test1.txt");
		Vertex vertex=g.getVertex().get(1);
		assertEquals(3,g.getVertex().size() );
		g.removeVertex(vertex);
		assertEquals(2,g.getVertex().size());
		assertFalse(g.getVertex().contains(vertex));
		
	}
	@Test
	public void testvertices() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/src/test1.txt");
		assertEquals(3,g.vertices().size());
	}
	@Test
	public void testtargets() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/src/test1.txt");
		Computer computer=new Computer("Computer1");
		assertEquals(1,g.targets(computer).size());
	}
	@Test
	public void testaddEdge() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/src/test1.txt");
		NetworkConnection e=new NetworkConnection("sff", -1);
		Computer computer=new Computer("sfs");
		Router router=new Router("dsffg");
		ArrayList<Vertex> list=new ArrayList<>();
		g.addVertex(computer);
		g.addVertex(router);
		list.add(computer);
		list.add(router);
		e.addVertices(list);
		g.addEdge(e);
		assertEquals(3,g.getEdge().size());
	}
	@Test
	public void testremoveEdge() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/src/test1.txt");
		Edge edge=g.getEdge().get(1);
		assertEquals(2,g.getEdge().size());
		g.removeEdge(edge);
		assertEquals(1,g.getEdge().size());
		assertTrue(!g.getEdge().contains(edge));
	}
	@Test
	public void testsources() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/src/test1.txt");
		Computer computer=new Computer("Computer1");
		assertEquals(1,g.sources(computer).size());
	}
	@Test
	public void testsetGraphName() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/src/test1.txt");
		g.setGraphName("ssss");
		assertEquals("ssss", g.getGraphName());
	}
	@Test
	public void testgetGraphName() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/src/test1.txt");
		assertEquals("LabNetwork", g.getGraphName());
	}

}
