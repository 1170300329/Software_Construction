package factory;

import vertex.Director;
import vertex.Vertex;

public class DirectorVertexFactory extends VertexFactory{

	@Override
	public Vertex createVertex(String label, String[] args) {
		Director director=new Director(label);
		director.fillVertexInfo(args);
		return director;
	}
}
