package factory;

import java.util.List;

import edge.Edge;
import edge.WordNeighborhood;
import vertex.Vertex;

public class WordNeighborhoodFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label,double weight,List<Vertex> vertices) {
		WordNeighborhood word=new WordNeighborhood(label, weight);
		word.addVertices(vertices);
		return word;
	}

}
