import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edge.WordNeighborhood;
import factory.GraphFactory;
import graph.ConcreteGraph;
import vertex.Vertex;
import vertex.Word;

public class GraphPoetTest {


		@Test(expected=AssertionError.class)
	    public void testAssertionsEnabled() {
	        assert false;
	    }
		@Test
		public void testgetEdge() throws Exception {
			ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test3.txt");
			assertEquals(5,g.getEdge().size());
		}
		@Test
		public void testgetVertex() throws Exception {
			ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test3.txt");
			assertEquals(4,g.getVertex().size());
		}
		@Test
		public void testreWeight() throws Exception {
			ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test3.txt");
			g.reWeight(1, 3);
			assertEquals(3,(int)g.getEdge().get(1).getWeight());
		}
		@Test
		public void testverRemove() throws Exception {
			ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test3.txt");
			assertEquals(4,g.getVertex().size());
			Vertex vertex=g.getVertex().get(1);
			g.removeVertex(vertex);
			assertEquals(3,g.getVertex().size());
			assertFalse(g.getVertex().contains(vertex));
		}
		@Test
		public void testedgeRemove() throws Exception {
			ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test3.txt");
			assertEquals(5,g.getEdge().size());
			edge.Edge edge=g.getEdge().get(1);
			g.edgeRemove(edge);
			assertEquals(4,g.getEdge().size());
			assertFalse(g.getEdge().contains(edge));
		}
		@Test
		public void testaddVertex() throws Exception {
			ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test3.txt");
			Vertex vertex=new Word("some");
			assertEquals(4, g.getVertex().size());
			g.addVertex(vertex);
			assertEquals(5, g.getVertex().size());
		}
		@Test
		public void testremoveVertex() throws Exception {
			ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test3.txt");
			Vertex vertex=g.getVertex().get(1);
			assertEquals(4,g.getVertex().size() );
			g.removeVertex(vertex);
			assertEquals(3,g.getVertex().size());
			assertFalse(g.getVertex().contains(vertex));
			
		}
		@Test
		public void testvertices() throws Exception {
			ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test3.txt");
			assertEquals(4,g.vertices().size());
		}
		@Test
		public void testtargets() throws Exception {
			ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test3.txt");
			Vertex vertex=new Word("to");
			assertEquals(3,g.targets(vertex).size());
		}
		@Test
		public void testaddEdge() throws Exception {
			ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test3.txt");
			WordNeighborhood e=new WordNeighborhood("scsd", 3);
			List<Vertex>list=new ArrayList<>();
			Word word=new Word("seek");
			Word word1=new Word("ask");
			list.add(word);
			list.add(word1);
			e.addVertices(list);
			g.addEdge(e);
			assertEquals(6,g.getEdge().size());
		}
		@Test
		public void testremoveEdge() throws Exception {
			ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test3.txt");
			edge.Edge edge=g.getEdge().get(1);
			assertEquals(5,g.getEdge().size());
			g.removeEdge(edge);
			assertEquals(4,g.getEdge().size());
			assertTrue(!g.getEdge().contains(edge));
		}
		@Test
		public void testsources() throws Exception {
			ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test3.txt");
			Vertex vertex=new Word("seek");
			assertEquals(3,g.sources(vertex).size());
		}
		@Test
		public void testsetGraphName() throws Exception {
			ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test3.txt");
			g.setGraphName("ssss");
			assertEquals("ssss", g.getGraphName());
		}
		@Test
		public void testgetGraphName() throws Exception {
			ConcreteGraph g=(ConcreteGraph) GraphFactory.createGraph("src/test3.txt");
			assertEquals("MyGraphPoet", g.getGraphName());
		}
	}
