package src.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import src.edge.DirectedEdge;
import src.edge.Edge;
import src.edge.HyperEdge;
import src.edge.UndirectedEdge;
import src.exception.ExistedEdgeException;
import src.exception.ExistedVertexException;
import src.exception.InvalidCmdException;
import src.exception.NonExistentVertexException;
import src.log.MyLog;
import src.vertex.Vertex;

public class ConcreteGraph implements Graph<Vertex, Edge> {
  private List<Vertex> vertices = new ArrayList<>();
  private List<Edge> edges = new ArrayList<>();
  private Set<Vertex> verSet = new HashSet<>();
  private Set<Edge> edgeSet = new HashSet<>();
  private Map<String, Vertex> map = new HashMap<>();
  private String GraphName;

  // Abstraction function:
  // vertices represents the vertexes included in the graph
  // edges represents the edges included in the graph
  // GraphName represents the name of the graph
  // Representation invariant:
  // all the vertexes in the edges should be in the vertices,GraphName!=null
  // Safety from rep exposure:
  // all the fields are private,GraphName is immutable,and other fields are copied
  // when got.
  public void checkRep() throws Exception {
    for (Edge e : edges) {
      for (int i = 0; i < e.getList().size(); i++) {
        assert vertices.contains(e.getList().get(i));
      }
    }
  }

  public Map<String, Vertex> getMap() {
    return map;
  }

  public Set<Vertex> getVerSet() {
    return verSet;
  }

  public Set<Edge> getEdgeSet() {
    return edgeSet;
  }

  /**
   * new a ConcreteGraph with a name
   * 
   * @param name
   * @throws Exception
   */
  public ConcreteGraph(String name) throws Exception {
    this.setGraphName(name);
  }

  /**
   * new a ConcreteGraph with vertices,edges and GraphName
   * 
   * @param vertices
   * @param edges
   * @param GraphName
   */
  public ConcreteGraph(List<Vertex> vertices, List<Edge> edges, String GraphName) {
    this.vertices = new ArrayList<>(vertices);
    this.edges = new ArrayList<>(edges);
    this.GraphName = new String(GraphName);
  }

  /**
   * get all the edges in the graph
   * 
   * @return Edge
   * @throws Exception
   */
  public List<Edge> getEdge() throws Exception {
    // List<Edge> list = new ArrayList<>(edges);
    // return list;
    return edges;
  }

  /**
   * get all the vertexes in the graph
   * 
   * @return list
   * @throws Exception
   */
  public List<Vertex> getVertex() throws Exception {
    // List<Vertex> list = new ArrayList<>(vertices);
    return vertices;
  }

  /**
   * change the weight of the edge by the index and weight
   * 
   * @param index
   * @param weight
   * @throws Exception
   */
  public void reWeight(int index, double weight) throws Exception {
    edges.get(index).setWeight(weight);
    // checkRep();
  }

  /**
   * remove the v from the vertices
   * 
   * @param v
   * @return
   * @throws Exception
   */
  public boolean verRemove(Vertex v) throws Exception {
    if (vertices.contains(v)) {
      vertices.remove(v);
      verSet.remove(v);
      return true;
    }
    return false;
  }

  /**
   * remove the e from the edges
   * 
   * @param e
   * @return
   * @throws Exception
   */
  public boolean edgeRemove(Edge e) throws Exception {
    if (edges.contains(e)) {
      edges.remove(e);
      edgeSet.remove(e);
      return true;
    }
    checkRep();
    return false;
  }

  /**
   * add v into the vertices,v shouldn't have existed in the vertices
   * 
   * @param v is the vertex to add.
   * @return If add a vertex successfully,return true. If the vertex added has existed,return false
   * @throws Exception
   */
  @Override
  public boolean addVertex(Vertex v) throws Exception {
    boolean flag = true;
    if (verSet.contains(v)) {
      flag = false;
      MyLog.logger.error("ConcreteGraph#addVertex#ExistedVertexException:顶点存在");
      throw new ExistedVertexException("顶点" + v.getLabel() + "已经存在");
    }
    vertices.add(v);
    verSet.add(v);
    map.put(v.getLabel(), v);
    // MyLog.logger.info("加入顶点" + v.getLabel());
    return flag;
  }

  /**
   * Remove v from the vertices and remove the edges connected to the vertex. If the vertex existed
   * in a hyperedge,it will check the hyperedge's number of vertexes. If the hyperedge's number of
   * vertexes is less than 1,the edge will be removed as well.
   * 
   * @param v is the vertex to remove.
   * @return If remove successfully,return true,or return false.
   */
  @Override
  public boolean removeVertex(Vertex v) throws Exception {
    boolean flag = false;
    if (vertices.contains(v)) {
      vertices.remove(v);
      for (int i = 0; i < edges.size(); i++) {
        if (edges.get(i) instanceof HyperEdge) {
          edges.get(i).removeVer(v);
          if (edges.get(i).getList().size() <= 1) {
            edges.remove(i);
            i--;
            MyLog.logger.error("ConcreteGraph#removeVertex#InvalidCmdException:超边中不合法的删除导致超边被删除");
            throw new InvalidCmdException("超边中不合法的删除导致超边被删除");
          }
        } else {
          if (edges.get(i).getList().get(0).getLabel().equals(v.getLabel())
              || edges.get(i).getList().get(1).getLabel().equals(v.getLabel())) {
            edges.remove(i);
            i--;
          }
        }
      }
      flag = true;
      // MyLog.logger.info("删除顶点" + v.getLabel());
      verSet.remove(v);
      return flag;
    } else {
      return flag;
    }
  }

  /**
   * return all the vertexes in the graph,which has defensively copied.
   * 
   * @return set contains all the vertex in the graph
   * @throws Exception
   */
  @Override
  public Set<Vertex> vertices() throws Exception {
    Set<Vertex> set = new HashSet<>(getVertex());
    for (int i = 0; i < getVertex().size(); i++) {
      set.add((Vertex) getVertex().get(i).deepClone());
    }
    checkRep();
    return set;
  }

  /**
   * Get all the source vertexes for the target,and the weight between them.
   * 
   * @param target vertex
   * @return A map whose key is the source vertex of the target,and it's value is a list of weight
   *         of the all the edges between the source vertex and target vertex.
   */
  @Override
  public Map<Vertex, List<Double>> sources(Vertex target) throws Exception {
    Map<Vertex, List<Double>> map = new HashMap<>();
    for (int i = 0; i < edges.size(); i++) {
      if (edges.get(i) instanceof UndirectedEdge) {
        if (edges.get(i).getList().get(0).getLabel().equals(target.getLabel())) {
          if (!map.keySet().contains(edges.get(i).getList().get(1))) {
            List<Double> list = new ArrayList<>();
            list.add(edges.get(i).getWeight());
            map.put(edges.get(i).getList().get(1), list);
          } else {
            map.get(edges.get(i).getList().get(1)).add(edges.get(i).getWeight());
          }
        }
        if (edges.get(i).getList().get(1).getLabel().equals(target.getLabel())) {
          if (!map.keySet().contains(edges.get(i).getList().get(0))) {
            List<Double> list = new ArrayList<>();
            list.add(edges.get(i).getWeight());
            map.put(edges.get(i).getList().get(0), list);
          } else {
            map.get(edges.get(i).getList().get(0)).add(edges.get(i).getWeight());
          }
        }
      }
      if (edges.get(i) instanceof DirectedEdge) {
        if (edges.get(i).getList().get(1).getLabel().equals(target.getLabel())) {
          if (!map.keySet().contains(edges.get(i).getList().get(0))) {
            List<Double> list = new ArrayList<>();
            list.add(edges.get(i).getWeight());
            map.put(edges.get(i).getList().get(0), list);
          } else {
            map.get(edges.get(i).getList().get(0)).add(edges.get(i).getWeight());
          }
        }
      }
    }
    checkRep();
    return map;
  }

  /**
   * Get all the target vertexes for the source,and the weight between them.
   * 
   * @param source vertex
   * @return A map whose key is the target vertex of the source,and it's value is a list of weight
   *         of the all the edges between the source vertex and target vertex.
   */
  @Override
  public Map<Vertex, List<Double>> targets(Vertex source) throws Exception {
    Map<Vertex, List<Double>> map = new HashMap<>();
    for (int i = 0; i < edges.size(); i++) {
      if (edges.get(i) instanceof UndirectedEdge) {
        if (edges.get(i).getList().get(0).getLabel().equals(source.getLabel())) {
          if (!map.keySet().contains(edges.get(i).getList().get(1))) {
            List<Double> list = new ArrayList<>();
            list.add(edges.get(i).getWeight());
            map.put(edges.get(i).getList().get(1), list);
          } else {
            map.get(edges.get(i).getList().get(1)).add(edges.get(i).getWeight());
          }
        }
        if (edges.get(i).getList().get(1).getLabel().equals(source.getLabel())) {
          if (!map.keySet().contains(edges.get(i).getList().get(0))) {
            List<Double> list = new ArrayList<>();
            list.add(edges.get(i).getWeight());
            map.put(edges.get(i).getList().get(0), list);
          } else {
            map.get(edges.get(i).getList().get(0)).add(edges.get(i).getWeight());
          }
        }
      }
      if (edges.get(i) instanceof DirectedEdge) {
        if (edges.get(i).getList().get(0).getLabel().equals(source.getLabel())) {
          if (!map.keySet().contains(edges.get(i).getList().get(1))) {
            List<Double> list = new ArrayList<>();
            list.add(edges.get(i).getWeight());
            map.put(edges.get(i).getList().get(1), list);
          } else {
            map.get(edges.get(i).getList().get(1)).add(edges.get(i).getWeight());
          }
        }
      }
    }
    checkRep();
    return map;
  }

  /**
   * add an edge into the graph,the edge shouldn'd have existed
   * 
   * @param edge which needs to be added,it is not in the graph before added.
   * @return If add the edge successfully,return true,or return false.
   */
  @Override
  public boolean addEdge(Edge edge) throws Exception {
    boolean flag = true;
    if (edgeSet.contains(edge)) {
      flag = false;
      MyLog.logger.error("ConcreteGraph#addEdge#ExistedEdgeException:边已经存在");
      throw new ExistedEdgeException("边" + edge.getLabel() + "已经存在");
    }

    for (int k = 0; k < edge.getList().size(); k++) {
      flag = false;
      if (verSet.contains(edge.getList().get(k))) {
        flag = true;
        continue;
      }
      if (flag == false) {
        MyLog.logger.error("ConcreteGraph#addEdge#NonExistentVertexException:未定义的节点");
        throw new NonExistentVertexException("未定义的节点：" + edge.getList().get(k).getLabel());
      }
    }
    edges.add(edge);
    edgeSet.add(edge);
    // MyLog.logger.info("加入边" + edge.getLabel());
    return true;
  }

  /**
   * Remove an edge from the graph.
   * 
   * @param edge needs to be removed.
   * @return If remove successfully ,return true,or return false.
   */
  @Override
  public boolean removeEdge(Edge edge) throws Exception {
    for (int i = 0; i < edges.size(); i++) {
      if (edges.get(i).getLabel().equals(edge.getLabel())) {
        edges.remove(i);
        edgeSet.remove(edge);
        return true;
      }
    }
    // MyLog.logger.info("删除边" + edge.getLabel());
    return false;
  }

  /**
   * return a set containing all the edges in the graph.All the edges are copied defensively.
   * 
   * @throws Exception
   * @return set which contains all the edges in the graph.
   */
  @Override
  public Set<Edge> edges() throws Exception {
    Set<Edge> set = new HashSet<>();
    for (int i = 0; i < edges.size(); i++) {
      set.add((Edge) edges.get(i).deepClone());
    }
    checkRep();
    return set;
  }

  /**
   * get the GraphName
   * 
   * @return GraphName
   */
  public String getGraphName() {
    return GraphName;
  }

  /**
   * set the GraphName
   * 
   * @param graphName
   * @throws Exception
   */
  public void setGraphName(String graphName) throws Exception {
    GraphName = graphName;
    checkRep();
  }

  /**
   * override toString() to show detail information for the graphF
   */
  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append("Name:" + GraphName + "\n");
    sb.append("Vertices:\n");
    int len = vertices.size();
    for (int i = 0; i < len; i++) {
      sb.append(vertices.get(i).toString() + "\n");
    }
    sb.append("edges:\n");
    int len1 = edges.size();
    for (int i = 0; i < len1; i++) {
      sb.append(edges.get(i).toString() + "\n");
    }
    return sb.toString();
  }

  /**
   * remove edge which weights is below valueof
   * 
   * @param valueOf
   * @throws Exception
   */
  public void removeEdgeBelowN(Integer valueOf) throws Exception {

    for (int i = 0; i < edges.size(); i++) {
      if (edges.get(i).getWeight() < valueOf) {
        edges.remove(i);
        i--;
      }
    }
    checkRep();
  }

}
