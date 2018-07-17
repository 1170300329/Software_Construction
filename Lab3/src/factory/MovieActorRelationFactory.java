package factory;

import java.util.List;

import edge.Edge;
import edge.MovieActorRelation;
import vertex.Vertex;

public class MovieActorRelationFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, double weight, List<Vertex> vertices) {
		MovieActorRelation movieActorRelation=new MovieActorRelation(label, weight);
		movieActorRelation.addVertices(vertices);
		return movieActorRelation;
	}

}
