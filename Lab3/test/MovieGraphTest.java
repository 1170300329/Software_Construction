import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.Test;

import edge.MovieDirectorRelation;
import factory.GraphFactory;
import graph.ConcreteGraph;
import vertex.Director;
import vertex.Movie;
import vertex.Vertex;

public class MovieGraphTest {

	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
	@Test
	public void testgetEdge() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test.txt");
		assertEquals(6,g.getEdge().size());
	}
	@Test
	public void testgetVertex() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test.txt");
		assertEquals(6,g.getVertex().size());
	}
	@Test
	public void testreWeight() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test.txt");
		g.reWeight(1, 3);
		assertEquals(3,(int)g.getEdge().get(1).getWeight());
	}
	@Test
	public void testverRemove() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test.txt");
		assertEquals(6,g.getVertex().size());
		Vertex vertex=g.getVertex().get(1);
		g.removeVertex(vertex);
		assertEquals(5,g.getVertex().size());
		assertFalse(g.getVertex().contains(vertex));
	}
	@Test
	public void testedgeRemove() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test.txt");
		assertEquals(6,g.getEdge().size());
		edge.Edge edge=g.getEdge().get(1);
		g.edgeRemove(edge);
		assertEquals(5,g.getEdge().size());
		assertFalse(g.getEdge().contains(edge));
	}
	@Test
	public void testaddVertex() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test.txt");
		Vertex vertex=new Movie("sdf");
		assertEquals(6, g.getVertex().size());
		g.addVertex(vertex);
		assertEquals(7, g.getVertex().size());
	}
	@Test
	public void testremoveVertex() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test.txt");
		Vertex vertex=g.getVertex().get(1);
		assertEquals(6,g.getVertex().size() );
		g.removeVertex(vertex);
		assertEquals(5,g.getVertex().size());
		assertFalse(g.getVertex().contains(vertex));
		
	}
	@Test
	public void testvertices() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test.txt");
		assertEquals(6,g.vertices().size());
	}
	@Test
	public void testtargets() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test.txt");
		Vertex vertex=new Movie("TheShawshankRedemption");
		assertEquals(3,g.targets(vertex).size());
	}
	@Test
	public void testaddEdge() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test.txt");
		MovieDirectorRelation e=new MovieDirectorRelation("sdf", -1);
		Movie movie=new Movie("asd");
		Director director=new Director("asddf");
		g.addVertex(movie);
		g.addVertex(director);
		ArrayList<Vertex> list=new ArrayList<>();
		list.add(movie);
		list.add(director);
		e.addVertices(list);
		g.addEdge(e);
		assertEquals(7,g.getEdge().size());
	}
	@Test
	public void testremoveEdge() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test.txt");
		edge.Edge edge=g.getEdge().get(1);
		assertEquals(6,g.getEdge().size());
		g.removeEdge(edge);
		assertEquals(5,g.getEdge().size());
		assertTrue(!g.getEdge().contains(edge));
	}
	@Test
	public void testsources() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test.txt");
		Vertex vertex=new Movie("TheShawshankRedemption");
		assertEquals(3,g.sources(vertex).size());
	}
	@Test
	public void testsetGraphName() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test.txt");
		g.setGraphName("ssss");
		assertEquals("ssss", g.getGraphName());
	}
	@Test
	public void testgetGraphName() throws Exception {
		ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test.txt");
		assertEquals("MyFavoriteMovies", g.getGraphName());
	}

}
