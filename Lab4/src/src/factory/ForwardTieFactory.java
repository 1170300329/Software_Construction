package src.factory;

import java.util.List;

import src.edge.Edge;
import src.edge.ForwardTie;
import src.vertex.Vertex;

public class ForwardTieFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, double weight, List<Vertex> vertices) throws Exception {
		ForwardTie f=new ForwardTie(label, weight);
		f.addVertices(vertices);
		return f;
	}

}
