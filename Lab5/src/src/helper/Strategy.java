package src.helper;

import src.graph.ConcreteGraph;
import src.vertex.Vertex;

public interface Strategy {
  public double compute(ConcreteGraph g) throws Exception;

  public double compute(ConcreteGraph g, Vertex v) throws Exception;

  public double compute(ConcreteGraph g, Vertex v1, Vertex v2) throws Exception;
}
