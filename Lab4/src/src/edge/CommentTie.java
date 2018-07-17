package src.edge;

import java.util.List;

import src.exception.InvalidEdgeException;
import src.log.MyLog;
import src.vertex.Vertex;

public class CommentTie extends DirectedEdge {
	private static final long serialVersionUID = 1L;
	/**
	 * new a CommentTie with a label and a weight
	 * @param label
	 * @param weight
	 */
	public CommentTie(String label, double weight) {
		super(label, weight);
	}
	/**
	 * override the addVertices() to add a list of vertexes into an edge
	 * @throws Exception 
	 */
	@Override
	public boolean addVertices(List<Vertex> vertices) throws Exception {
		boolean flag=true;
		if(vertices.get(0).equals(vertices.get(1))) {
			flag=false;
			MyLog.logger.error("InvalidEdgeException:社交图中不能存在环");
			throw new InvalidEdgeException("社交图中不能存在环");
		}
		flag= super.addVertices(vertices);
		return flag;
	}
	
}
