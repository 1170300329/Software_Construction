package src.helper;

import src.graph.ConcreteGraph;
import src.vertex.Vertex;

public class GraphMetrics {
  public static double degreeCentrality(ConcreteGraph g, Vertex v) throws Exception {
    Context context = new Context(new ComputeDegreeCentrality());
    return context.executeStrategy(g, v);
  }

  public static double degreeCentrality(ConcreteGraph g) throws Exception {
    Context context = new Context(new ComputeDegreeCentrality());
    return context.executeStrategy(g);
  }

  public static double closenessCentrality(ConcreteGraph g, Vertex v) throws Exception {
    Context context = new Context(new ComputeClosenessCentrality());
    return context.executeStrategy(g, v);
  }

  public static double betweennessCentrality(ConcreteGraph g, Vertex v) throws Exception {
    Context context = new Context(new ComputeBetweennessCentrality());
    return context.executeStrategy(g, v);
  }

  public static double inDegreeCentrality(ConcreteGraph g, Vertex v) throws Exception {
    Context context = new Context(new ComputeInDegreeCentrality());
    return context.executeStrategy(g, v);
  }

  public static double outDegreeCentrality(ConcreteGraph g, Vertex v) throws Exception {
    Context context = new Context(new ComputeOutDegreeCentrality());
    return context.executeStrategy(g, v);
  }

  public static double distance(ConcreteGraph g, Vertex start, Vertex end) throws Exception {
    Context context = new Context(new ComputeDistance());
    return context.executeStrategy(g, start, end);
  }

  public static double eccentricity(ConcreteGraph g, Vertex v) throws Exception {
    Context context = new Context(new ComptuteEccentricity());
    return context.executeStrategy(g, v);
  }

  public static double radius(ConcreteGraph g) throws Exception {
    Context context = new Context(new ComputeRadius());
    return context.executeStrategy(g);
  }

  public static double diameter(ConcreteGraph g) throws Exception {
    Context context = new Context(new ComputeDiameter());
    return context.executeStrategy(g);
  }
}
