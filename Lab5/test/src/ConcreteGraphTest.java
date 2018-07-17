package src;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import src.edge.Edge;
import src.edge.HyperEdge;
import src.edge.WordNeighborhood;
import src.exception.EdgeTypeException;
import src.exception.ExistedEdgeException;
import src.exception.ExistedVertexException;
import src.exception.InvalidInstructionException;
import src.exception.InvalidLabelException;
import src.exception.MismatchEdgeException;
import src.exception.NonExistentVertexException;
import src.exception.VertexTypeException;
import src.exception.WeightException;
import src.factory.GraphFactory;
import src.graph.ConcreteGraph;
import src.vertex.Vertex;
import src.vertex.Word;

public class ConcreteGraphTest {
  /**
   * Test Strategy:use different input including as more as possible cases and exception to test the
   * project work well or not.Different cases are divided into several equal cases.
   */
  @Test(expected = AssertionError.class)
  public void testAssertionsEnabled() {
    assert false;
  }

  @Test
  public void testgetEdge() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test3.txt", 2);
    assertEquals(5, g.getEdge().size());
  }

  @Test
  public void testgetVertex() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test3.txt", 2);
    assertEquals(4, g.getVertex().size());
  }

  @Test
  public void testreWeight() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test3.txt", 2);
    g.reWeight(1, 3);
    assertEquals(3, (int) g.getEdge().get(1).getWeight());
  }

  @Test
  public void testverRemove() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test3.txt", 2);
    assertEquals(4, g.getVertex().size());
    Vertex vertex = g.getVertex().get(1);
    g.removeVertex(vertex);
    assertEquals(3, g.getVertex().size());
    assertFalse(g.getVertex().contains(vertex));
  }

  @Test
  public void testedgeRemove() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test3.txt", 2);
    assertEquals(5, g.getEdge().size());
    Edge edge = g.getEdge().get(1);
    g.edgeRemove(edge);
    assertEquals(4, g.getEdge().size());
    assertFalse(g.getEdge().contains(edge));
  }

  @Test
  public void testaddVertex() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test3.txt", 2);
    Vertex vertex = new Word("some");
    assertEquals(4, g.getVertex().size());
    g.addVertex(vertex);
    assertEquals(5, g.getVertex().size());
  }

  @Test
  public void testremoveVertex() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test3.txt", 2);
    Vertex vertex = g.getVertex().get(1);
    assertEquals(4, g.getVertex().size());
    g.removeVertex(vertex);
    assertEquals(3, g.getVertex().size());
    assertFalse(g.getVertex().contains(vertex));

  }

  @Test
  public void testvertices() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test3.txt", 2);
    assertEquals(4, g.vertices().size());
  }

  @Test
  public void testtargets() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test3.txt", 2);
    Vertex vertex = new Word("to");
    assertEquals(3, g.targets(vertex).size());
  }

  @Test
  public void testaddEdge() throws Exception {
    WordNeighborhood e = new WordNeighborhood("scsd", 3);
    List<Vertex> list = new ArrayList<>();
    Word word = new Word("seek");
    Word word1 = new Word("ask");
    list.add(word);
    list.add(word1);
    e.addVertices(list);
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test3.txt", 2);
    g.addEdge(e);
    assertEquals(6, g.getEdge().size());
  }

  @Test
  public void testremoveEdge() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test3.txt", 2);
    Edge edge = g.getEdge().get(1);
    assertEquals(5, g.getEdge().size());
    g.removeEdge(edge);
    assertEquals(4, g.getEdge().size());
    assertTrue(!g.getEdge().contains(edge));
  }

  @Test
  public void testsources() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test3.txt", 2);
    Vertex vertex = new Word("seek");
    assertEquals(3, g.sources(vertex).size());
  }

  @Test
  public void testsetGraphName() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test3.txt", 2);
    g.setGraphName("ssss");
    assertEquals("ssss", g.getGraphName());
  }

  @Test
  public void testgetGraphName() throws Exception {
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/test3.txt", 2);
    assertEquals("MyGraphPoet", g.getGraphName());
  }

  @Test
  public void testInvalidLabelException() {
    try {

      @SuppressWarnings("unused")
      ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testGraphPoet1.txt", 2);

    } catch (Exception e) {
      assertTrue(e instanceof InvalidLabelException);
      assertEquals("不合法的Label：图的Label含有不合法字符", e.getMessage());
    }
    try {
      @SuppressWarnings("unused")
      ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testGraphPoet3.txt", 2);

    } catch (Exception e) {
      System.out.println(e.getMessage());
      assertTrue(e instanceof InvalidLabelException);
      assertEquals("不合法的Label：顶点Label含有不合法字符", e.getMessage());
    }
    try {
      ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testGraphPoet4.txt", 2);
      g.hashCode();
    } catch (Exception e) {
      assertTrue(e instanceof InvalidLabelException);
      assertEquals("不合法的Label：边Label含有不合法字符", e.getMessage());
    }
  }

  @Test
  public void testVertexTypeException() {
    try {
      ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testGraphPoet2.txt", 2);
      g.hashCode();
    } catch (Exception e) {
      assertTrue(e instanceof VertexTypeException);
      assertEquals("不匹配的节点类型：Person", e.getMessage());
    }

    try {
      ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testGraphPoet6.txt", 2);
      g.hashCode();
    } catch (Exception e) {
      assertTrue(e instanceof VertexTypeException);
      assertEquals("不匹配的节点类型：Person", e.getMessage());
    }
  }

  @Test
  public void testExistedVertexException() {
    try {
      ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testGraphPoet5.txt", 2);
      g.hashCode();
    } catch (Exception e) {
      assertTrue(e instanceof ExistedVertexException);
    }
  }

  @Test
  public void testEdgeTypeException() {
    try {
      ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testGraphPoet7.txt", 2);
      g.hashCode();
    } catch (Exception e) {
      assertTrue(e instanceof EdgeTypeException);
      assertEquals("不匹配的边类型：FriendTie", e.getMessage());
    }

    try {
      ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testGraphPoet8.txt", 2);
      g.hashCode();
    } catch (Exception e) {
      assertTrue(e instanceof EdgeTypeException);
      assertEquals("不匹配的边类型：FriendTie", e.getMessage());
    }
  }

  @Test
  public void testExistedEdgeException() {
    try {
      ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testGraphPoet9.txt", 2);
      g.hashCode();
    } catch (Exception e) {
      assertTrue(e instanceof ExistedEdgeException);
    }
  }

  @Test
  public void testHEdgeVerNumException() throws Exception {

    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testGraphPoet10.txt", 2);
    for (Edge e : g.edges()) {
      assertFalse(e instanceof HyperEdge);
    }
  }

  @Test
  public void testInvalidInstructionException() throws Exception {

    try {
      ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testGraphPoet11.txt", 2);
      g.hashCode();
    } catch (Exception e) {
      assertTrue(e instanceof InvalidInstructionException);
      assertEquals("无效的指令", e.getMessage());
    }
  }

  @Test
  public void testNonExistentVertexException() throws Exception {

    try {
      ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testGraphPoet12.txt", 2);
      g.hashCode();
    } catch (Exception e) {
      assertTrue(e instanceof NonExistentVertexException);
      assertEquals("未定义的节点：see", e.getMessage());
    }
  }

  @Test
  public void testMismatchEdgeException() {

    try {
      ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testGraphPoet13.txt", 2);
      g.hashCode();
    } catch (Exception e) {
      assertTrue(e instanceof MismatchEdgeException);
      assertEquals("有向图引入了无向边！", e.getMessage());
    }
  }

  @Test
  public void testWeightException() {

    try {
      ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testGraphPoet14.txt", 2);
      g.hashCode();
    } catch (Exception e) {
      assertTrue(e instanceof WeightException);
      assertEquals("带权边TR未给出权值", e.getMessage());
    }

    try {
      ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testGraphPoet15.txt", 2);
      g.hashCode();
    } catch (Exception e) {
      assertTrue(e instanceof WeightException);
      assertEquals("带权边TR权值不是正整数", e.getMessage());
    }

    try {
      ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testGraphPoet16.txt", 2);
      g.hashCode();
    } catch (Exception e) {
      assertTrue(e instanceof WeightException);
      assertEquals("带权边TR权值不是正整数", e.getMessage());
    }
  }

  @Test
  public void testMultiEdgeException() throws Exception {

    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testGraphPoet17.txt", 2);
    assertEquals(4, g.getEdge().size());
  }

  @Test
  public void test() throws Exception {
    @SuppressWarnings("unused")
    ConcreteGraph g = (ConcreteGraph) GraphFactory.createGraph("src/src/testGraphPoet18.txt", 2);
  }
}
