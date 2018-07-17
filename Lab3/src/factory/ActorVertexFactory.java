package factory;

import vertex.Actor;
import vertex.Vertex;

public class ActorVertexFactory extends VertexFactory{

	@Override
	public Vertex createVertex(String label, String[] args) {
		Actor actor=new Actor(label);
		actor.fillVertexInfo(args);
		return actor;
	}

}
