package src.factory;

import src.vertex.Router;
import src.vertex.Vertex;

public class RouterVertexFactory extends VertexFactory {

  @Override
  public Vertex createVertex(String label, String[] args) throws Exception {
    Router router = new Router(label);
    try {
      router.fillVertexInfo(args);
    } catch (Exception e) {
      throw e;
    }
    return router;
  }

}
