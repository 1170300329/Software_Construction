package src.factory;

import java.util.List;
import src.edge.Edge;
import src.edge.WordNeighborhood;
import src.vertex.Vertex;

public class WordNeighborhoodFactory extends EdgeFactory {

  @Override
  public Edge createEdge(String label, double weight, List<Vertex> vertices) throws Exception {
    WordNeighborhood word = new WordNeighborhood(label, weight);
    word.addVertices(vertices);
    return word;
  }

}
