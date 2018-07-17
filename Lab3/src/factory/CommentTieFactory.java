package factory;

import java.util.List;

import edge.CommentTie;
import edge.Edge;
import vertex.Vertex;

public class CommentTieFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, double weight, List<Vertex> vertices) {
		CommentTie commentTie=new CommentTie(label, weight);
		commentTie.addVertices(vertices);
		return commentTie;
	}

}
