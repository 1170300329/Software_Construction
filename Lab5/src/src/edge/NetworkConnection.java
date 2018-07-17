package src.edge;

import java.util.List;
import src.exception.InvalidEdgeException;
import src.log.MyLog;
import src.vertex.Computer;
import src.vertex.Server;
import src.vertex.Vertex;

public class NetworkConnection extends UndirectedEdge {
  private static final long serialVersionUID = 1L;

  /**
   * new a networkconnection with a label and its weight
   * 
   * @param label
   * @param weight
   */
  public NetworkConnection(String label, double weight) {
    super(label, weight);
  }

  /**
   * override the addVertices() to add vertices into the edge
   * 
   * @throws Exception
   */
  @Override
  public boolean addVertices(List<Vertex> vertices) throws Exception {
    boolean flag = true;
    if (vertices.size() == 1 || vertices.get(0).equals(vertices.get(1))) {
      flag = false;
      MyLog.logger.error("InvalidEdgeException:在拓扑图中不能存在环");
      throw new InvalidEdgeException("在拓扑图中不能存在环");

    } else if (vertices.get(0) instanceof Computer && vertices.get(1) instanceof Computer) {
      flag = false;
      MyLog.logger.error("InvalidEdgeException:边的端点类型不能同为Computer");
      throw new InvalidEdgeException("边的端点类型不能同为Computer");
    } else if (vertices.get(0) instanceof Server && vertices.get(1) instanceof Server) {
      flag = false;
      MyLog.logger.error("InvalidEdgeException:边的端点类型不能同为Server");
      throw new InvalidEdgeException("边的端点类型不能同为Server");
    } else {
      if (vertices.size() == 2) {
        listAdd(vertices.get(0));
        listAdd(vertices.get(1));
        return true;
      }
    }
    return flag;
  }
}
