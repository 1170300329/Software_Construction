package factory;

import java.util.List;

import edge.Edge;
import edge.FriendTie;
import vertex.Vertex;

public class FriendTieFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, double weight, List<Vertex> vertices) {
		FriendTie f=new FriendTie(label, weight);
		f.addVertices(vertices);
		return f;
	}

}
