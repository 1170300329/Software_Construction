package factory;

import java.util.List;

import edge.Edge;
import edge.ForwardTie;
import vertex.Vertex;

public class ForwardTieFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, double weight, List<Vertex> vertices) {
		ForwardTie f=new ForwardTie(label, weight);
		f.addVertices(vertices);
		return f;
	}

}
