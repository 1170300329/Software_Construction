package src;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import org.junit.Test;
import src.edge.Edge;
import src.edge.MovieDirectorRelation;
import src.exception.AttrNumException;
import src.exception.HEdgeVerNumException;
import src.exception.InvalidInstructionException;
import src.factory.GraphFactory;
import src.graph.ConcreteGraph;
import src.vertex.Director;
import src.vertex.Movie;
import src.vertex.Vertex;

public class MovieGraphTest {

  @Test(expected = AssertionError.class)
  public void testAssertionsEnabled() {
    assert false;
  }

  @Test
  public void testgetEdge() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test.txt", 2);
    assertEquals(6, g.getEdge().size());
  }

  @Test
  public void testgetVertex() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test.txt", 2);
    assertEquals(6, g.getVertex().size());
  }

  @Test
  public void testreWeight() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test.txt", 2);
    g.reWeight(1, 3);
    assertEquals(3, (int) g.getEdge().get(1).getWeight());
  }

  @Test
  public void testverRemove() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test.txt", 2);
    assertEquals(6, g.getVertex().size());
    Vertex vertex = g.getVertex().get(1);
    g.removeVertex(vertex);
    assertEquals(5, g.getVertex().size());
    assertFalse(g.getVertex().contains(vertex));
  }

  @Test
  public void testedgeRemove() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test.txt", 2);
    assertEquals(6, g.getEdge().size());
    Edge edge = g.getEdge().get(1);
    g.edgeRemove(edge);
    assertEquals(5, g.getEdge().size());
    assertFalse(g.getEdge().contains(edge));
  }

  @Test
  public void testaddVertex() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test.txt", 2);
    Vertex vertex = new Movie("sdf");
    assertEquals(6, g.getVertex().size());
    g.addVertex(vertex);
    assertEquals(7, g.getVertex().size());
  }

  @Test
  public void testremoveVertex() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test.txt", 2);
    Vertex vertex = g.getVertex().get(1);
    assertEquals(6, g.getVertex().size());
    g.removeVertex(vertex);
    assertEquals(5, g.getVertex().size());
    assertFalse(g.getVertex().contains(vertex));

  }

  @Test
  public void testvertices() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test.txt", 2);
    assertEquals(6, g.vertices().size());
  }

  @Test
  public void testtargets() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test.txt", 2);
    Vertex vertex = new Movie("TheShawshankRedemption");
    assertEquals(3, g.targets(vertex).size());
  }

  @Test
  public void testaddEdge() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test.txt", 2);
    Movie movie = new Movie("asd");
    Director director = new Director("asddf");
    g.addVertex(movie);
    g.addVertex(director);
    ArrayList<Vertex> list = new ArrayList<>();
    list.add(movie);
    list.add(director);
    MovieDirectorRelation e = new MovieDirectorRelation("sdf", -1);
    e.addVertices(list);
    g.addEdge(e);
    assertEquals(7, g.getEdge().size());
  }

  @Test
  public void testremoveEdge() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test.txt", 2);
    Edge edge = g.getEdge().get(1);
    assertEquals(6, g.getEdge().size());
    g.removeEdge(edge);
    assertEquals(5, g.getEdge().size());
    assertTrue(!g.getEdge().contains(edge));
  }

  @Test
  public void testsources() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test.txt", 2);
    Vertex vertex = new Movie("TheShawshankRedemption");
    assertEquals(3, g.sources(vertex).size());
  }

  @Test
  public void testsetGraphName() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test.txt", 2);
    g.setGraphName("ssss");
    assertEquals("ssss", g.getGraphName());
  }

  @Test
  public void testgetGraphName() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test.txt", 2);
    assertEquals("MyFavoriteMovies", g.getGraphName());
  }

  @Test
  public void testInvalidInstructionException() {
    try {
      @SuppressWarnings("unused")
      ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testMovieGraph1.txt", 2);
    } catch (Exception e) {
      assertTrue(e instanceof InvalidInstructionException);
    }
  }

  @Test
  public void testAttrNumException() {
    try {
      @SuppressWarnings("unused")
      ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testMovieGraph2.txt", 2);
    } catch (Exception e) {
      assertTrue(e instanceof AttrNumException);
    }
  }

  @Test
  public void testHEdgeVerNumException() {
    try {
      @SuppressWarnings("unused")
      ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testMovieGraph3.txt", 2);
    } catch (Exception e) {
      assertTrue(e instanceof HEdgeVerNumException);
    }
  }
}
