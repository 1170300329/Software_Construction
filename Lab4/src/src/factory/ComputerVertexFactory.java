package src.factory;

import src.vertex.Computer;
import src.vertex.Vertex;

public class ComputerVertexFactory extends VertexFactory{

	@Override
	public Vertex createVertex(String label, String[] args) throws Exception {
		Computer computer=new Computer(label);
		try {
		computer.fillVertexInfo(args);
		}catch (Exception e) {
			throw e;
		}
		return computer;
	}

}
