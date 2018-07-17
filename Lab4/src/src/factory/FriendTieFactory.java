package src.factory;

import java.util.List;

import src.edge.Edge;
import src.edge.FriendTie;
import src.vertex.Vertex;

public class FriendTieFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, double weight, List<Vertex> vertices) throws Exception {
		FriendTie f=new FriendTie(label, weight);
		f.addVertices(vertices);
		return f;
	}

}
