package src.factory;

import java.util.List;
import src.edge.Edge;
import src.edge.SameMovieHyperEdge;
import src.vertex.Vertex;

public class SameMovieHyperEdgeFactory extends EdgeFactory {

  @Override
  public Edge createEdge(String label, double weight, List<Vertex> vertices) {
    SameMovieHyperEdge sameMovieHyperEdge = new SameMovieHyperEdge(label, weight);
    sameMovieHyperEdge.addVertices(vertices);
    return sameMovieHyperEdge;
  }

}
