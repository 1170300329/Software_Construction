package src.edge;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import src.vertex.Vertex;

public abstract class Edge implements Serializable {
  private static final long serialVersionUID = 1L;
  private List<Vertex> vertices = new ArrayList<>();
  private String label;
  private double weight;

  // Abstraction function:
  // vertices represents the vertexs included in the edge,the label represents the
  // label of the edge
  // the weight represents the weight of the edge
  // Representation invariant:
  // vertices shouldn't be null and the weight should be -1 or positive float
  // number.
  // Safety from rep exposure:
  // all the fields are private,label and weight are immutable,the vertices was
  // defensively copied when geted.
  public void checkRep() {
    assert label != null : "标签为空";
    assert weight == -1 || weight > 0 : "不合法的边权重";
  }

  /**
   * new an edge with label and weight
   * 
   * @param label
   * @param weight
   */
  public Edge(String label, double weight) {
    assert label.matches("\\w+") : "边的label不符合正则表达式要求";
    this.label = label;
    this.weight = weight;
    checkRep();
  }

  /**
   * determine whether the vertex was included in the edge
   * 
   * @param v
   * @return if included ,return true,else return false
   */
  public boolean containVertex(Vertex v) {
    for (int i = 0; i < vertices.size(); i++) {
      if (v.getLabel().equals(vertices.get(i).getLabel())) {
        return true;
      }
    }
    return false;
  }

  /**
   * remove a vertex from the edge
   * 
   * @param v
   * @return if removed,return true,else return false
   */
  public boolean removeVer(Vertex v) {

    if (vertices.contains(v)) {
      vertices.remove(v);
      return true;
    }
    return false;
  }

  /**
   * add a vertex into the vertices of the edge
   * 
   * @param v
   */
  public void listAdd(Vertex v) {
    this.vertices.add(v);
  }

  /**
   * clear the vertices
   */
  public void listRemove() {
    this.vertices.clear();
  }

  /**
   * get the vertices and do defensive copy
   * 
   * @return vertices
   * @throws Exception
   */
  public List<Vertex> getList() throws Exception {
    // ArrayList<Vertex> list = new ArrayList<>();
    // for (int i = 0; i < vertices.size(); i++) {
    // Vertex v = (Vertex) vertices.get(i).deepClone();
    // list.add(v);
    // }
    // return list;
    return vertices;
  }

  /**
   * get the set of the vertexs included in the edge
   * 
   * @return set
   * @throws Exception
   */
  public Set<Vertex> vertices() throws Exception {
    Set<Vertex> set = new HashSet<>();
    for (int i = 0; i < vertices.size(); i++) {
      Vertex v = (Vertex) vertices.get(i).deepClone();
      set.add(v);
    }
    return set;
  }

  /**
   * get the label of the edge
   * 
   * @return label
   */
  public String getLabel() {
    checkRep();
    return label;
  }

  /**
   * get the weight of the edge
   * 
   * @return weight
   */
  public double getWeight() {
    checkRep();
    return weight;
  }

  /**
   * set the weight of an edge
   * 
   * @param weight
   */
  public void setWeight(double weight) {
    this.weight = weight;
    checkRep();
  }

  /**
   * override the hashCode() with label
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((label == null) ? 0 : label.hashCode());
    return result;
  }

  /**
   * override the equals() with label
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Edge other = (Edge) obj;
    if (label == null) {
      if (other.label != null)
        return false;
    } else if (!label.equals(other.label))
      return false;
    return true;
  }

  /**
   * add a list of vertex into the edge
   * 
   * @param vertices
   * @return if add successfully,return true
   * @throws Exception
   */
  public abstract boolean addVertices(List<Vertex> vertices) throws Exception;

  /**
   * return the source vertexs of the edge
   * 
   * @return set
   */
  public abstract Set<Vertex> sourceVertices() throws Exception;

  /**
   * return the target vertexs of the edge
   * 
   * @return set
   */
  public abstract Set<Vertex> targetVertices() throws Exception;

  /**
   * Deep clone an edge instance.
   * 
   * @return a new Object which has the same attributes.
   * @throws Exception
   */
  public Object deepClone() throws Exception {
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    ObjectOutputStream oos = new ObjectOutputStream(bos);
    oos.writeObject(this);

    ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
    ObjectInputStream ois = new ObjectInputStream(bis);
    return ois.readObject();
  }
}
