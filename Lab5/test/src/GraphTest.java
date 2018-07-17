package src;

import static org.junit.Assert.assertEquals;
import java.util.Collections;
import org.junit.Test;
import src.graph.Graph;

public class GraphTest {

  @Test(expected = AssertionError.class)
  public void testAssertionsEnabled() {
    assert false;
  }

  @Test
  public void testEmptyVerticesEmpty() throws Exception {
    assertEquals("expected empty() graph to have no vertices", Collections.emptySet(),
        Graph.empty().vertices());
  }

  @Test
  public void testEmptyEdgesEmpty() throws Exception {
    assertEquals("expected empty() graph to have no edges", Collections.emptySet(),
        Graph.empty().edges());
  }
}
