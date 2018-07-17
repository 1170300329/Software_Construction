package factory;

import vertex.Computer;
import vertex.Vertex;

public class ComputerVertexFactory extends VertexFactory{

	@Override
	public Vertex createVertex(String label, String[] args) {
		Computer computer=new Computer(label);
		computer.fillVertexInfo(args);
		return computer;
	}

}
