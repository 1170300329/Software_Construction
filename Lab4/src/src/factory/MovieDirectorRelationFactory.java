package src.factory;

import java.util.List;

import src.edge.Edge;
import src.edge.MovieDirectorRelation;
import src.vertex.Vertex;

public class MovieDirectorRelationFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, double weight, List<Vertex> vertices) throws Exception {
		MovieDirectorRelation movieDirectorRelation=new MovieDirectorRelation(label, weight);
		movieDirectorRelation.addVertices(vertices);
		return movieDirectorRelation;
	}

}
