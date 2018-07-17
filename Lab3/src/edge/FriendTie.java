package edge;

import java.util.List;

import vertex.Vertex;

public class FriendTie extends DirectedEdge {
	private static final long serialVersionUID = 1L;
	/**
	 * new a FriendTie with a label and a weight
	 * @param label
	 * @param weight
	 */
	public FriendTie(String label, double weight) {
		super(label, weight);
	}
	/**
	 * override the addVertices() to add a list of vertexes into the edge
	 */
	@Override
	public boolean addVertices(List<Vertex> vertices) {
		if(vertices.get(0).equals(vertices.get(1))) {
			System.out.println("error");
			return false;
		}
		boolean flag= super.addVertices(vertices);
		return flag;
	}
}
