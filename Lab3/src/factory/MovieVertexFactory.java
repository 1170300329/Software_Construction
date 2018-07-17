package factory;

import vertex.Movie;
import vertex.Vertex;

public class MovieVertexFactory extends VertexFactory{

	@Override
	public Vertex createVertex(String label, String[] args) {
		Movie movie=new Movie(label);
		movie.fillVertexInfo(args);
		return movie;
	}
	
}
