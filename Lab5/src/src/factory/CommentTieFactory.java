package src.factory;

import java.util.List;
import src.edge.CommentTie;
import src.edge.Edge;
import src.vertex.Vertex;

public class CommentTieFactory extends EdgeFactory {

  @Override
  public Edge createEdge(String label, double weight, List<Vertex> vertices) throws Exception {
    CommentTie commentTie = new CommentTie(label, weight);
    commentTie.addVertices(vertices);
    return commentTie;
  }

}
