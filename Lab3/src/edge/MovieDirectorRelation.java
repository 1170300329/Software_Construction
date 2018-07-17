package edge;

import java.util.List;

import vertex.Vertex;

public class MovieDirectorRelation extends UndirectedEdge{
	private static final long serialVersionUID = 1L;
	/**
	 * new a MovieDirectorRelation with a label and a weight
	 * @param label
	 * @param weight
	 */
	public MovieDirectorRelation(String label, double weight) {
		super(label, weight);
	}
	/**
	 * override addVertices() to add a list of vertexes
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
