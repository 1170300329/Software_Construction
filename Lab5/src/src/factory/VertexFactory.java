package src.factory;

import src.vertex.Vertex;

public abstract class VertexFactory {
  public abstract Vertex createVertex(String label, String[] args) throws Exception;
}
