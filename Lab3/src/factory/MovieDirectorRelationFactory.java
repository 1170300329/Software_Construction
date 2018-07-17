package factory;

import java.util.List;

import edge.Edge;
import edge.MovieDirectorRelation;
import vertex.Vertex;

public class MovieDirectorRelationFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, double weight, List<Vertex> vertices) {
		MovieDirectorRelation movieDirectorRelation=new MovieDirectorRelation(label, weight);
		movieDirectorRelation.addVertices(vertices);
		return movieDirectorRelation;
	}

}
