package edge;

import java.util.List;

import vertex.Computer;
import vertex.Server;
import vertex.Vertex;

public class NetworkConnection extends UndirectedEdge{
	private static final long serialVersionUID = 1L;
	/**
	 * new a networkconnection with a label and its weight
	 * @param label
	 * @param weight
	 */
	public NetworkConnection(String label, double weight) {
		super(label, weight);
	}
	/**
	 * override the addVertices() to add vertices into the edge
	 */
	@Override
	public boolean addVertices(List<Vertex> vertices) {
		if(vertices.size()==1||vertices.get(0).equals(vertices.get(1))) {
			System.out.println("error");
			return false;
		}
		else if(vertices.get(0) instanceof Computer&&vertices.get(1) instanceof Computer) {
			System.out.println("error");
			return false;
		}else if(vertices.get(0) instanceof Server&&vertices.get(1) instanceof Server) {
			System.out.println("error");
			return false;
		}else {
			if(vertices.size()==2) {
				listAdd(vertices.get(0));
				listAdd(vertices.get(1));
				return true;
			}
		}
		return false;
	}
}
