package factory;

import java.util.List;

import edge.Edge;
import vertex.Vertex;

public abstract class EdgeFactory {
	public abstract Edge createEdge(String label,double weight,List<Vertex> vertices) ;
}
