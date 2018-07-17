package src.factory;

import java.util.List;

import src.edge.Edge;
import src.vertex.Vertex;

public abstract class EdgeFactory {
	public abstract Edge createEdge(String label,double weight,List<Vertex> vertices) throws Exception ;
}
