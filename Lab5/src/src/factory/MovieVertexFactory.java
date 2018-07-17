package src.factory;

import src.vertex.Movie;
import src.vertex.Vertex;

public class MovieVertexFactory extends VertexFactory {

  @Override
  public Vertex createVertex(String label, String[] args) throws Exception {
    Movie movie = new Movie(label);
    try {
      movie.fillVertexInfo(args);
    } catch (Exception e) {
      throw e;
    }
    return movie;
  }

}
