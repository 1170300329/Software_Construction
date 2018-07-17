package src.factory;

import src.vertex.Actor;
import src.vertex.Vertex;

public class ActorVertexFactory extends VertexFactory{

	@Override
	public Vertex createVertex(String label, String[] args) throws Exception {
		Actor actor=new Actor(label);
		try {
		actor.fillVertexInfo(args);
		}catch (Exception e) {
			throw e;
		}
		return actor;
	}

}
