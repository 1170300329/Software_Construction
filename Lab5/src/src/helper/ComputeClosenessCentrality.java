package src.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import src.edge.CommentTie;
import src.edge.Edge;
import src.edge.ForwardTie;
import src.edge.FriendTie;
import src.edge.HyperEdge;
import src.graph.ConcreteGraph;
import src.graph.GraphPoet;
import src.graph.MovieGraph;
import src.graph.NetworkTopology;
import src.graph.SocialNetwork;
import src.vertex.Person;
import src.vertex.Vertex;

public class ComputeClosenessCentrality implements Strategy {

  @Override
  public double compute(ConcreteGraph g) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public double compute(ConcreteGraph g, Vertex v) throws Exception {
    // 调用第三方库的算法
    // SparseMultigraph<Vertex, Edge> graph = new SparseMultigraph<>();
    // for (Vertex vm : g.vertices()) {
    // graph.addVertex(vm);
    // }
    // for (Edge em : g.edges()) {
    // if (em instanceof DirectedEdge) {
    // graph.addEdge(em, em.getList().get(0), em.getList().get(1),
    // EdgeType.DIRECTED);
    // } else if (em instanceof UndirectedEdge) {
    // graph.addEdge(em, em.getList().get(0), em.getList().get(1),
    // EdgeType.UNDIRECTED);
    // }
    // }
    // ClosenessCentrality<Vertex, Edge> c = new ClosenessCentrality<>(graph);
    // return c.getVertexScore(v);
    double ans = 0;
    double temp = 0;
    for (Vertex vm : g.vertices()) {
      if (!vm.getLabel().equals(v.getLabel())) {
        temp = minPath(g, vm, v);
        if (temp == -1) {
          ans = ans + 0;
        } else {
          ans = ans + 1 / temp;
        }
      }
    }
    return ans / (g.vertices().size() - 1);
  }

  @Override
  public double compute(ConcreteGraph g, Vertex v1, Vertex v2) {
    // TODO Auto-generated method stub
    return 0;
  }

  private static Map<Integer, Vertex> map;// 由编号得到名字
  private static Map<Vertex, Integer> map1;// 由名字得到编号

  public static double minPath(ConcreteGraph g, Vertex v1, Vertex v2) throws Exception {
    final int INF = Integer.MAX_VALUE;
    double ans = 0;
    if (g instanceof GraphPoet) {
      map = new HashMap<>();// 由编号得到名字
      map1 = new HashMap<>();// 由名字得到编号
      int size = g.vertices().size();
      double[][] array = new double[size][size];
      int cnt = 0;
      for (Vertex v : g.vertices()) {
        map.put(cnt, v);
        map1.put(v, cnt);
        cnt++;
      }
      for (int i = 0; i < array.length; i++) {
        for (int j = 0; j < array.length; j++) {
          array[i][j] = INF;
        }
      }
      for (Edge e : g.edges()) {
        array[map1.get(e.getList().get(0))][map1.get(e.getList().get(1))] = 1;
      }
      ans = FloydInGraph.findAllPath(array, map1.get(v1), map1.get(v2));
      if (array[map1.get(v1)][map1.get(v2)] == INF && FloydInGraph.getPath().get(0).size() == 2) {
        return -1;
      }
    } else if (g instanceof NetworkTopology) {
      map = new HashMap<>();// 由编号得到名字
      map1 = new HashMap<>();// 由名字得到编号
      int size = g.vertices().size();
      double[][] array = new double[size][size];
      int cnt = 0;
      for (Vertex v : g.vertices()) {
        map.put(cnt, v);
        map1.put(v, cnt);
        cnt++;
      }
      for (int i = 0; i < array.length; i++) {
        for (int j = 0; j < array.length; j++) {
          array[i][j] = INF;
        }
      }
      for (Edge e : g.edges()) {
        array[map1.get(e.getList().get(0))][map1.get(e.getList().get(1))] = 1;
        array[map1.get(e.getList().get(1))][map1.get(e.getList().get(0))] = 1;
      }
      ans = FloydInGraph.findAllPath(array, map1.get(v1), map1.get(v2));
      if (array[map1.get(v1)][map1.get(v2)] == INF && FloydInGraph.getPath().get(0).size() == 2) {
        return -1;
      }
    } else if (g instanceof SocialNetwork) {
      List<Vertex> vertexs = new ArrayList<>(g.getVertex());
      List<Vertex> vertexsused = new ArrayList<>(g.getVertex());
      List<Edge> edges = new ArrayList<>(g.getEdge());
      List<Edge> edgesused = new ArrayList<>(g.getEdge());
      for (int i = 0; i < vertexs.size(); i++) {
        for (int j = 0; j < vertexs.size(); j++) {
          List<Edge> list = new ArrayList<>();
          for (int k = 0; k < edges.size(); k++) {
            if (edges.get(k).getList().get(0).getLabel().equals(vertexs.get(i).getLabel())
                && edges.get(k).getList().get(1).getLabel().equals(vertexs.get(j).getLabel())) {
              list.add(edges.get(k));
            }
          }
          // System.out.println(list.size());
          if (list.size() == 2) {
            if (list.get(0).getWeight() != list.get(1).getWeight()) {
              // System.out.println("dsfgdsg");
              list.remove(1);
            }
          } else if (list.size() == 3 && list.get(0).getWeight() == list.get(1).getWeight()
              && list.get(0).getWeight() != list.get(2).getWeight()) {
            list.remove(2);
          } else if (list.size() == 3 && list.get(0).getWeight() == list.get(2).getWeight()
              && list.get(0).getWeight() != list.get(1).getWeight()) {
            list.remove(1);
          } else if (list.size() == 3 && list.get(1).getWeight() == list.get(2).getWeight()
              && list.get(0).getWeight() != list.get(1).getWeight()) {
            list.remove(0);
          } else if (list.size() == 3 && list.get(0).getWeight() != list.get(1).getWeight()
              && list.get(2).getWeight() != list.get(1).getWeight()
              && list.get(2).getWeight() != list.get(0).getWeight()) {
            list.remove(2);
            list.remove(1);
          }
          // System.out.println(list.size());
          if (list.size() == 2) {
            Vertex temp = new Person(vertexs.get(i).getLabel() + " " + vertexs.get(j).getLabel());
            Edge edge1 = new FriendTie("", -1);
            Edge edge2 = new FriendTie("", -1);
            if (list.get(0) instanceof FriendTie) {
              edge1 = new FriendTie(list.get(0) + "1", 0.5 * list.get(0).getWeight());
              edge2 = new FriendTie(list.get(0) + "2", 0.5 * list.get(0).getWeight());
            } else if (list.get(0) instanceof ForwardTie) {
              edge1 = new ForwardTie(list.get(0) + "1", 0.5 * list.get(0).getWeight());
              edge2 = new ForwardTie(list.get(0) + "2", 0.5 * list.get(0).getWeight());
            } else if (list.get(0) instanceof CommentTie) {
              edge1 = new CommentTie(list.get(0) + "1", 0.5 * list.get(0).getWeight());
              edge2 = new CommentTie(list.get(0) + "2", 0.5 * list.get(0).getWeight());
            }
            ArrayList<Vertex> list1 = new ArrayList<>();
            list1.add(list.get(0).getList().get(0));
            list1.add(temp);
            edge1.addVertices(list1);

            ArrayList<Vertex> list2 = new ArrayList<>();
            list2.add(temp);
            list2.add(list.get(0).getList().get(1));
            edge2.addVertices(list2);

            vertexsused.add(temp);
            edgesused.remove(list.get(0));
            edgesused.add(edge1);
            edgesused.add(edge2);
          } else if (list.size() == 3) {
            Vertex temp = new Person(vertexs.get(i).getLabel() + " " + vertexs.get(j).getLabel());
            Vertex temp1 =
                new Person(vertexs.get(i).getLabel() + " " + vertexs.get(j).getLabel() + "1");
            Edge edge1 = new FriendTie("", -1);
            Edge edge2 = new FriendTie("", -1);
            if (list.get(0) instanceof FriendTie) {
              edge1 = new FriendTie(list.get(0) + "1", 0.5 * list.get(0).getWeight());
              edge2 = new FriendTie(list.get(0) + "2", 0.5 * list.get(0).getWeight());
            } else if (list.get(0) instanceof ForwardTie) {
              edge1 = new ForwardTie(list.get(0) + "1", 0.5 * list.get(0).getWeight());
              edge2 = new ForwardTie(list.get(0) + "2", 0.5 * list.get(0).getWeight());
            } else if (list.get(0) instanceof CommentTie) {
              edge1 = new CommentTie(list.get(0) + "1", 0.5 * list.get(0).getWeight());
              edge2 = new CommentTie(list.get(0) + "2", 0.5 * list.get(0).getWeight());
            }
            ArrayList<Vertex> list1 = new ArrayList<>();
            list1.add(list.get(0).getList().get(0));
            list1.add(temp);
            edge1.addVertices(list1);

            ArrayList<Vertex> list2 = new ArrayList<>();
            list2.add(temp);
            list2.add(list.get(0).getList().get(1));
            edge2.addVertices(list2);
            vertexsused.add(temp);
            edgesused.remove(list.get(0));
            edgesused.add(edge1);
            edgesused.add(edge2);

            Edge edge3 = new FriendTie("", -1);
            Edge edge4 = new FriendTie("", -1);
            if (list.get(1) instanceof FriendTie) {
              edge3 = new FriendTie(list.get(1) + "1", 0.5 * list.get(1).getWeight());
              edge4 = new FriendTie(list.get(1) + "2", 0.5 * list.get(1).getWeight());
            } else if (list.get(0) instanceof ForwardTie) {
              edge3 = new ForwardTie(list.get(1) + "1", 0.5 * list.get(1).getWeight());
              edge4 = new ForwardTie(list.get(1) + "2", 0.5 * list.get(1).getWeight());
            } else if (list.get(0) instanceof CommentTie) {
              edge3 = new CommentTie(list.get(1) + "1", 0.5 * list.get(1).getWeight());
              edge4 = new CommentTie(list.get(1) + "2", 0.5 * list.get(1).getWeight());
            }
            ArrayList<Vertex> list3 = new ArrayList<>();
            list3.add(list.get(1).getList().get(0));
            list3.add(temp);
            edge3.addVertices(list3);

            ArrayList<Vertex> list4 = new ArrayList<>();
            list4.add(temp);
            list4.add(list.get(1).getList().get(1));
            edge4.addVertices(list4);

            vertexsused.add(temp1);
            edgesused.remove(list.get(1));
            edgesused.add(edge3);
            edgesused.add(edge4);
          }
        }
      }
      ConcreteGraph graph = new ConcreteGraph(vertexsused, edgesused, g.getGraphName());

      map = new HashMap<>();// 由编号得到名字
      map1 = new HashMap<>();// 由名字得到编号
      int size = graph.vertices().size();
      double[][] array = new double[size][size];
      int cnt = 0;
      for (Vertex v : graph.vertices()) {
        map.put(cnt, v);
        map1.put(v, cnt);
        cnt++;
      }
      for (int i = 0; i < array.length; i++) {
        for (int j = 0; j < array.length; j++) {
          array[i][j] = INF;
        }
      }
      for (Vertex vr1 : graph.vertices()) {
        for (Vertex vr2 : graph.vertices()) {
          for (Edge e : graph.edges()) {
            if (e.getList().get(0).getLabel().equals(vr1.getLabel())
                && e.getList().get(1).getLabel().equals(vr2.getLabel())) {
              if (array[map1.get(vr1)][map1.get(vr2)] == INF
                  || array[map1.get(vr1)][map1.get(vr2)] > e.getWeight()) {
                array[map1.get(vr1)][map1.get(vr2)] = 1;
              }
            }
          }
        }
      }
      ans = FloydInGraph.findAllPath(array, map1.get(v1), map1.get(v2));
      if (array[map1.get(v1)][map1.get(v2)] == INF && FloydInGraph.getPath().get(0).size() == 2) {
        return -1;
      }
    } else if (g instanceof MovieGraph) {
      map = new HashMap<>();// 由编号得到名字
      map1 = new HashMap<>();// 由名字得到编号
      int size = g.vertices().size();
      double[][] array = new double[size][size];
      int cnt = 0;
      for (Vertex v : g.vertices()) {
        map.put(cnt, v);
        map1.put(v, cnt);
        cnt++;
      }
      for (int i = 0; i < array.length; i++) {
        for (int j = 0; j < array.length; j++) {
          array[i][j] = INF;
        }
      }
      for (Edge e : g.edges()) {
        if (!(e instanceof HyperEdge)) {
          array[map1.get(e.getList().get(0))][map1.get(e.getList().get(1))] = 1;
          array[map1.get(e.getList().get(1))][map1.get(e.getList().get(0))] = 1;
        }
      }
      ans = FloydInGraph.findAllPath(array, map1.get(v1), map1.get(v2));
      if (array[map1.get(v1)][map1.get(v2)] == INF && FloydInGraph.getPath().get(0).size() == 2) {
        return -1;
      }
    }
    return ans;
  }

}
