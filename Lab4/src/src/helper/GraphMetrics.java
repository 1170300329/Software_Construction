package src.helper;

import src.graph.ConcreteGraph;
import src.vertex.Vertex;

public class GraphMetrics{
	public static double degreeCentrality(ConcreteGraph g, Vertex v) throws Exception {
		Context context = new Context((Strategy)new ComputeDegreeCentrality());
		return context.executeStrategy(g, v);
	}
	public static double degreeCentrality(ConcreteGraph g) throws Exception{
		Context context = new Context((Strategy)new ComputeDegreeCentrality());
		return context.executeStrategy(g);
	}
	public static double closenessCentrality(ConcreteGraph g, Vertex v) throws Exception {
		Context context = new Context((Strategy)new ComputeClosenessCentrality());
		return context.executeStrategy(g, v);
	}
	public static double betweennessCentrality(ConcreteGraph g, Vertex v) throws Exception {
		Context context = new Context((Strategy)new ComputeBetweennessCentrality());
		return context.executeStrategy(g, v);
	}
	public static double inDegreeCentrality(ConcreteGraph g, Vertex v)  throws Exception{
		Context context = new Context((Strategy)new ComputeInDegreeCentrality());
		return context.executeStrategy(g, v);
	}
	public static double outDegreeCentrality(ConcreteGraph g, Vertex v)  throws Exception{
		Context context = new Context((Strategy)new ComputeOutDegreeCentrality());
		return context.executeStrategy(g, v);
	}
	public static double distance(ConcreteGraph g, Vertex start, Vertex end)  throws Exception{
		Context context = new Context((Strategy)new ComputeDistance());
		return context.executeStrategy(g, start,end);
	}
	public static double eccentricity(ConcreteGraph g, Vertex v)  throws Exception{
		Context context = new Context((Strategy)new ComptuteEccentricity());
		return context.executeStrategy(g, v);
	}
	public static double radius(ConcreteGraph g)  throws Exception{
		Context context = new Context((Strategy)new ComputeRadius());
		return context.executeStrategy(g);
	}
	public static double diameter(ConcreteGraph g)  throws Exception{
		Context context = new Context((Strategy)new ComputeDiameter());
		return context.executeStrategy(g);
	}
}