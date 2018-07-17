package src.edge;

import java.util.List;

import src.exception.InvalidEdgeException;
import src.log.MyLog;
import src.vertex.Actor;
import src.vertex.Director;
import src.vertex.Vertex;

public class MovieActorRelation extends UndirectedEdge{
	private static final long serialVersionUID = 1L;
	/**
	 * new a MovieActorRelation with a label and a weight
	 * @param label
	 * @param weight
	 */
	public MovieActorRelation(String label, double weight) {
		super(label, weight);
	}
	/**
	 * override the addVertices() to add a list of vertexes into the edge
	 * @throws Exception 
	 */
	@Override
	public boolean addVertices(List<Vertex> vertices) throws Exception {
		boolean flag=true;
		if(vertices.size()==2&&vertices.get(0).equals(vertices.get(1))) {
			flag=false;
			MyLog.logger.error("InvalidEdgeException:电影图中不能存在环");
			throw new InvalidEdgeException("电影图中不能存在环");
		}
		if(vertices.get(0) instanceof Actor&&vertices.get(1) instanceof Director) {
			flag=false;
			MyLog.logger.error("InvalidEdgeException:电影图中不能存在演员和导演之间的边");
			throw new InvalidEdgeException("电影图中不能存在演员和导演之间的边");
		}
		if(vertices.get(1) instanceof Actor&&vertices.get(0) instanceof Director) {
			flag=false;
			MyLog.logger.error("InvalidEdgeException:电影图中不能存在演员和导演之间的边");
			throw new InvalidEdgeException("电影图中不能存在演员和导演之间的边");
		}
		flag= super.addVertices(vertices);
		return flag;
	}
}
