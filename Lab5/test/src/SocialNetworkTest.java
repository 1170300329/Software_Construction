package src;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import src.edge.Edge;
import src.edge.FriendTie;
import src.factory.GraphFactory;
import src.graph.ConcreteGraph;
import src.vertex.Person;
import src.vertex.Vertex;

public class SocialNetworkTest {

  @Test(expected = AssertionError.class)
  public void testAssertionsEnabled() {
    assert false;
  }

  @Test
  public void testgetEdge() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test2.txt", 2);
    assertEquals(5, g.getEdge().size());
  }

  @Test
  public void testgetVertex() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test2.txt", 2);
    assertEquals(4, g.getVertex().size());
  }

  @Test
  public void testreWeight() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test2.txt", 2);
    g.reWeight(1, 3);
    assertEquals(3, (int) g.getEdge().get(1).getWeight());
  }

  @Test
  public void testverRemove() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test2.txt", 2);
    assertEquals(4, g.getVertex().size());
    Vertex vertex = g.getVertex().get(1);
    g.verRemove(vertex);
    assertEquals(3, g.getVertex().size());
    assertFalse(g.getVertex().contains(vertex));
  }

  @Test
  public void testedgeRemove() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test2.txt", 2);
    assertEquals(5, g.getEdge().size());
    Edge edge = g.getEdge().get(1);
    g.edgeRemove(edge);
    assertEquals(4, g.getEdge().size());
    assertFalse(g.getEdge().contains(edge));
  }

  @Test
  public void testaddVertex() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test2.txt", 2);
    Person vertex = new Person("some");
    assertEquals(4, g.getVertex().size());
    g.addVertex(vertex);
    assertEquals(5, g.getVertex().size());
  }

  @Test
  public void testremoveVertex() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test2.txt", 2);
    Vertex vertex = g.getVertex().get(1);
    assertEquals(4, g.getVertex().size());
    g.removeVertex(vertex);
    assertEquals(3, g.getVertex().size());
    assertFalse(g.getVertex().contains(vertex));

  }

  @Test
  public void testvertices() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test2.txt", 2);
    assertEquals(4, g.vertices().size());
  }

  @Test
  public void testtargets() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test2.txt", 2);
    Person vertex = new Person("Emma");
    assertEquals(1, g.targets(vertex).size());
  }

  @Test
  public void testaddEdge() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test2.txt", 2);
    FriendTie friendTie = new FriendTie("asfa", 0.1);
    List<Vertex> list = new ArrayList<>();
    list.add(g.getVertex().get(3));
    list.add(g.getVertex().get(2));
    friendTie.addVertices(list);
    g.addEdge(friendTie);
    assertEquals(6, g.getEdge().size());
  }

  @Test
  public void testremoveEdge() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test2.txt", 2);
    Edge edge = g.getEdge().get(1);
    assertEquals(5, g.getEdge().size());
    g.removeEdge(edge);
    assertEquals(4, g.getEdge().size());
    assertTrue(!g.getEdge().contains(edge));
  }

  @Test
  public void testsources() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test2.txt", 2);
    Person vertex = new Person("Emma");
    assertEquals(1, g.sources(vertex).size());

  }

  @Test
  public void testsetGraphName() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test2.txt", 2);
    g.setGraphName("ssss");
    assertEquals("ssss", g.getGraphName());
  }

  @Test
  public void testgetGraphName() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test2.txt", 2);
    assertEquals("LabSocial", g.getGraphName());
  }
}
