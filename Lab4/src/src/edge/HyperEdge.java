package src.edge;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import src.vertex.Vertex;

public class HyperEdge extends Edge{
	private static final long serialVersionUID = 1L;
	
	/**
	 * new a hyperedge with a label and a weight 
	 * @param label
	 * @param weight
	 */
	public HyperEdge(String label, double weight) {
		super(label, weight);
	}
	/**
	 * override addVertices() to add a list of vertexes into an edge
	 */
	@Override
	public boolean addVertices(List<Vertex> vertices) {
		if(vertices.size()>0) {
			for(int i=0;i<vertices.size();i++) {
				listAdd(vertices.get(i));
			}
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
		Set<Vertex> set=new HashSet<>(this.vertices());
		return set;
	}
	/**
	 * override targetVertices() to get the target vertexes of the edge
	 * @return set
	 */
	@Override
	public Set<Vertex> targetVertices() throws Exception {
		Set<Vertex> set=new HashSet<>(this.vertices());
		return set;
	}
	/**
	 * override toString() to show detail information of the edge
	 */
	@Override
	public String toString() {
		String string="";
		try {
			for(int i=0;i<getList().size();i++) {
				string=string+getList().get(i).getLabel()+" ";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getLabel()+"["+string+"]";
	}
}
