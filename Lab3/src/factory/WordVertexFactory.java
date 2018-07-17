package factory;

import vertex.*;
public class WordVertexFactory extends VertexFactory{
	@Override
	public Vertex createVertex(String label, String[] args) {
		Word word=new Word(label);
		word.fillVertexInfo(args);
		return word;
	}
	
}