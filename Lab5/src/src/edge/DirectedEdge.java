package src.edge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import src.vertex.Person;
import src.vertex.Vertex;

public class DirectedEdge extends Edge {
  private static final long serialVersionUID = 1L;
  private Vertex source;
  private Vertex target;

  // Abstraction function:
  // source represents the source vertex of the directed edge
  // target represents the target vertex of the directed edge
  // Representation invariant:
  // the vertex shouldn'd be null
  // Safety from rep exposure:
  // all the fields are private and copied when geted.
  public DirectedEdge(String label, double weight) {
    super(label, weight);
  }

  /**
   * override addVertices() to add vertices into an edge
   * 
   * @throws Exception
   */
  @Override
  public boolean addVertices(List<Vertex> vertices) throws Exception {
    if (vertices.size() == 1) {
      source = vertices.get(0);
      target = vertices.get(0);
      listAdd(vertices.get(0));
      if (vertices.get(0) instanceof Person) {
        ((Person) vertices.get(0)).setIndegree(((Person) vertices.get(0)).getIndegree() + 1);
      }
      return true;
    } else if (vertices.size() == 2) {
      source = vertices.get(0);
      target = vertices.get(1);
      listAdd(source);
      listAdd(target);
      if (vertices.get(1) instanceof Person) {
        ((Person) vertices.get(1)).setIndegree(((Person) vertices.get(1)).getIndegree() + 1);
      }
      return true;
    }
    return false;
  }

  /**
   * return the source vertex in the edge return set
   * 
   * @throws Exception
   */
  @Override
  public Set<Vertex> sourceVertices() throws Exception {
    Set<Vertex> set = new HashSet<>();
    set.add((Vertex) source.deepClone());
    return set;
  }

  /**
   * return the target vertex in the edge return set
   * 
   * @throws Exception
   */
  @Override
  public Set<Vertex> targetVertices() throws Exception {
    Set<Vertex> set = new HashSet<>();
    set.add((Vertex) target.deepClone());
    return set;
  }

  /**
   * override toString() to show the detail information of the edge
   */
  @Override
  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(getLabel() + ": [source=" + source.toString() + ", target=" + target.toString()
        + ", weight=" + this.getWeight() + "]");
    return sb.toString();
  }

}
