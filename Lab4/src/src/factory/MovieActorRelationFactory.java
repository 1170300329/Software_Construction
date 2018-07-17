package src.factory;

import java.util.List;

import src.edge.Edge;
import src.edge.MovieActorRelation;
import src.vertex.Vertex;

public class MovieActorRelationFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, double weight, List<Vertex> vertices) throws Exception {
		MovieActorRelation movieActorRelation=new MovieActorRelation(label, weight);
		movieActorRelation.addVertices(vertices);
		return movieActorRelation;
	}

}
