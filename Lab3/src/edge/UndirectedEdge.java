package edge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import vertex.Vertex;

public class UndirectedEdge extends Edge{
	private static final long serialVersionUID = 1L;
	/**
	 * new a undirected edge with a label and weight
	 * @param label
	 * @param weight
	 */
	public UndirectedEdge(String label, double weight) {
		super(label, weight);
	}
	/**
	 * override addVertices() to add a list of vertexes into an edge
	 */
	@Override
	public boolean addVertices(List<Vertex> vertices) {
		if(vertices.size()==2) {
			listAdd(vertices.get(0));
			listAdd(vertices.get(1));
			return true;
		}
		return false;
	}
	/**
	 * override sourceVertices() to get the source vertexes of the edge
	 * @return set
	 */
	@Override
	public Set<Vertex> sourceVertices() throws Exception {
		Set<Vertex> set = null;
		try {
			set = new HashSet<>(vertices());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return set;
	}
	/**
	 * override targetVertices() to get the target vertexes of the edge
	 * @return set
	 */
	@Override
	public Set<Vertex> targetVertices() throws Exception {
		Set<Vertex> set=new HashSet<>(vertices());
		return set;
	}
	/**
	 * override toString() to show detail information of the edge
	 */
	@Override
	public String toString() {
		try {
			return getLabel()+": ["+getList().get(0)+"-"+getList().get(1)+"]";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
