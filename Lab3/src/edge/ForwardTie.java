package edge;

import java.util.List;

import vertex.Vertex;

public class ForwardTie extends DirectedEdge {
	private static final long serialVersionUID = 1L;
	/**
	 * new a ForwardTie with a label and a weight
	 * @param label
	 * @param weight
	 */
	public ForwardTie(String label, double weight) {
		super(label, weight);
	}
	/**
	 * override the addVertices() to add a list of vertexes into an edge
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