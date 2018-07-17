package factory;

import java.util.List;

import edge.Edge;
import edge.SameMovieHyperEdge;
import vertex.Vertex;

public class SameMovieHyperEdgeFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, double weight, List<Vertex> vertices) {
		SameMovieHyperEdge sameMovieHyperEdge=new SameMovieHyperEdge(label, weight);
		sameMovieHyperEdge.addVertices(vertices);
		return sameMovieHyperEdge;
	}

}
