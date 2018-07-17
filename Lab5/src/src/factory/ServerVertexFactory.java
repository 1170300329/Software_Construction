package src.factory;

import src.vertex.Server;
import src.vertex.Vertex;

public class ServerVertexFactory extends VertexFactory {

  @Override
  public Vertex createVertex(String label, String[] args) throws Exception {
    Server server = new Server(label);
    try {
      server.fillVertexInfo(args);
    } catch (Exception e) {
      throw e;
    }
    return server;
  }

}
