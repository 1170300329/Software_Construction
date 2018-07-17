package helper;

import graph.ConcreteGraph;
import vertex.Vertex;

public interface Strategy {
	public double compute(ConcreteGraph g)throws Exception;
	public double compute(ConcreteGraph g,Vertex v) throws Exception;
	public double compute(ConcreteGraph g,Vertex v1,Vertex v2)throws Exception;
}
