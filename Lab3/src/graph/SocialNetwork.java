package graph;

import edge.Edge;
import vertex.Vertex;

public class SocialNetwork extends ConcreteGraph{
	/**
	 * new a socialnetwork with a name
	 * @param name
	 */
	public SocialNetwork(String name) {
		super(name);
	}
	/**
	 * add an edge into the graph
	 * @param edge
	 */
	@Override
	public boolean addEdge(Edge edge) throws Exception {
		boolean flag= super.addEdge(edge);
		if(edges().size()==1) {
			reWeight(0, 1);
		}else {
			for(int i=0;i<getEdge().size()-1;i++) {
				reWeight(i, getEdge().get(i).getWeight()*(1-edge.getWeight()));
			}
		}
		return flag;
	}
	/**
	 * 
	 */
	@Override
	public boolean removeEdge(Edge edge) throws Exception {
		boolean flag= super.removeEdge(edge);
		if(edges().size()==1) {
			reWeight(0, 1);
		}else {
			for(int i=0;i<getEdge().size();i++) {
				reWeight(i, getEdge().get(i).getWeight()/(1-edge.getWeight()));
			}
		}
		return flag;
	}
	/**
	 * remove an edge from the graph
	 * @param edge
	 */
	@Override
	public boolean removeVertex(Vertex v) throws Exception {
		if(vertices().contains(v)) {
			verRemove(v);
			for(int i=0;i<getEdge().size();i++) {
					if(getEdge().get(i).getList().get(0).equals(v)||getEdge().get(i).getList().get(1).equals(v)) {
						edgeRemove(getEdge().get(i));
						//i--;
					}
			}
			return true;
		}else {
			return false;
		}
	}
}
