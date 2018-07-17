package src.helper;

import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.algorithms.scoring.ClosenessCentrality;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import src.edge.DirectedEdge;
import src.edge.Edge;
import src.edge.HyperEdge;
import src.edge.UndirectedEdge;
import src.graph.ConcreteGraph;
import src.vertex.Vertex;

public class GraphMetrics1 {
  public static double degreeCentrality(ConcreteGraph g, Vertex v) throws Exception {
    double ans = 0;
    for (Edge e : g.edges()) {
      if (!(e instanceof HyperEdge)) {
        if (e.getList().get(0).getLabel().equals(v.getLabel())) {
          ans++;
        } else if (e.getList().get(1).getLabel().equals(v.getLabel())) {
          ans++;
        }
      }
    }
    return ans;
  }

  public static double degreeCentrality(ConcreteGraph g) throws Exception {
    double ans = 0;
    int VertexNum = g.vertices().size();
    double bottom = VertexNum * VertexNum - 3 * VertexNum + 2;
    double top = 0;
    double p = -1;
    for (Vertex v : g.vertices()) {
      if (degreeCentrality(g, v) > p) {
        p = degreeCentrality(g, v);
      }
    }
    for (Vertex v : g.vertices()) {
      top = top + (p - degreeCentrality(g, v));
    }
    ans = top / bottom;
    return ans;
  }

  public static double betweennessCentrality(ConcreteGraph g, Vertex v) throws Exception {

    SparseMultigraph<Vertex, Edge> graph = new SparseMultigraph<>();
    for (Vertex vm : g.vertices()) {
      graph.addVertex(vm);
    }
    for (Edge em : g.edges()) {
      if (em instanceof DirectedEdge) {
        graph.addEdge(em, em.getList().get(0), em.getList().get(1), EdgeType.DIRECTED);
      } else if (em instanceof UndirectedEdge) {
        graph.addEdge(em, em.getList().get(0), em.getList().get(1), EdgeType.UNDIRECTED);
      }
    }
    BetweennessCentrality<Vertex, Edge> b = new BetweennessCentrality<>(graph);
    return b.getVertexScore(v);
  }

  public static double closenessCentrality(ConcreteGraph g, Vertex v) throws Exception {
    SparseMultigraph<Vertex, Edge> graph = new SparseMultigraph<>();
    for (Vertex vm : g.vertices()) {
      graph.addVertex(vm);
    }
    for (Edge em : g.edges()) {
      if (em instanceof DirectedEdge) {
        graph.addEdge(em, em.getList().get(0), em.getList().get(1), EdgeType.DIRECTED);
      } else if (em instanceof UndirectedEdge) {
        graph.addEdge(em, em.getList().get(0), em.getList().get(1), EdgeType.UNDIRECTED);
      }
    }
    ClosenessCentrality<Vertex, Edge> c = new ClosenessCentrality<>(graph);
    return c.getVertexScore(v);// (g.vertices().size()-1);
  }

  public static double distance(ConcreteGraph g, Vertex start, Vertex end) throws Exception {
    SparseMultigraph<Vertex, Edge> graph = new SparseMultigraph<>();
    for (Vertex vm : g.vertices()) {
      graph.addVertex(vm);
    }
    for (Edge em : g.edges()) {
      if (em instanceof DirectedEdge) {
        graph.addEdge(em, em.getList().get(0), em.getList().get(1), EdgeType.DIRECTED);
      } else if (em instanceof UndirectedEdge) {
        graph.addEdge(em, em.getList().get(0), em.getList().get(1), EdgeType.UNDIRECTED);
      }
    }
    DijkstraDistance<Vertex, Edge> d = new DijkstraDistance<>(graph);
    return (double) d.getDistance(start, end);
  }

  public static double inDegreeCentrality(ConcreteGraph g, Vertex v) throws Exception {
    int ans = 0;
    for (Edge e : g.edges()) {
      if (!(e instanceof HyperEdge)) {
        if (e.getList().get(0).getLabel().equals(v.getLabel())) {
          ans++;
        }
      }
    }
    return ans;
  }

  public static double outDegreeCentrality(ConcreteGraph g, Vertex v) throws Exception {
    int ans = 0;
    for (Edge e : g.edges()) {
      if (!(e instanceof HyperEdge)) {
        if (e.getList().get(1).getLabel().equals(v.getLabel())) {
          ans++;
        }
      }
    }
    return ans;
  }
}
