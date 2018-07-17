package src.factory;

import src.vertex.Director;
import src.vertex.Vertex;

public class DirectorVertexFactory extends VertexFactory {

  @Override
  public Vertex createVertex(String label, String[] args) throws Exception {
    Director director = new Director(label);
    try {
      director.fillVertexInfo(args);
    } catch (Exception e) {
      throw e;
    }
    return director;
  }
}
