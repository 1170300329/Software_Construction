package src.graph;

import src.edge.CommentTie;
import src.edge.Edge;
import src.edge.ForwardTie;
import src.edge.FriendTie;
import src.exception.InvalidCmdException;
import src.log.MyLog;
import src.vertex.Vertex;

public class SocialNetwork extends ConcreteGraph {

  /**
   * new a socialnetwork with a name
   * 
   * @param name
   * @throws Exception
   */
  public SocialNetwork(String name) throws Exception {
    super(name);
  }

  /**
   * add an edge into the graph
   * 
   * @param edge
   */
  @Override
  public boolean addEdge(Edge edge) throws Exception {
    boolean flag = super.addEdge(edge);
    if (edges().size() == 1) {
      reWeight(0, 1);
    } else {
      for (int i = 0; i < getEdge().size() - 1; i++) {
        reWeight(i, getEdge().get(i).getWeight() * (1 - edge.getWeight()));
      }
    }
    // MyLog.logger.info("加入边" + edge.getLabel());
    return flag;
  }

  /**
   * remove an edge from the graph
   */
  @Override
  public boolean removeEdge(Edge edge) throws Exception {
    boolean flag = super.removeEdge(edge);
    if (edges().size() == 1) {
      reWeight(0, 1);
    } else {
      for (int i = 0; i < getEdge().size(); i++) {
        reWeight(i, getEdge().get(i).getWeight() / (1 - edge.getWeight()));
      }
    }
    // MyLog.logger.info("删除边" + edge.getLabel());
    return flag;
  }

  /**
   * remove an edge from the graph
   * 
   * @param edge
   */
  @Override
  public boolean removeVertex(Vertex v) throws Exception {
    if (vertices().contains(v)) {
      verRemove(v);
      for (int i = 0; i < getEdge().size(); i++) {
        if (getEdge().get(i).getList().get(0).equals(v)
            || getEdge().get(i).getList().get(1).equals(v)) {
          edgeRemove(getEdge().get(i));
          // i--;
        }
      }
      // MyLog.logger.info("删除顶点" + v.getLabel());
      return true;
    } else {
      return false;
    }
  }

  public void reWeightTie(int index, double weight) throws Exception {
    if (weight > 1 || weight < 0) {
      MyLog.logger.error("SocialNetwork#reWeightTie#InvalidCmdException:指令中边权范围错误");
      throw new InvalidCmdException("指令中边权范围错误");
    }
    System.out.println(weight);
    if (this instanceof SocialNetwork) {
      if (this.getEdge().get(index) instanceof FriendTie) {
        FriendTie nFriendTie = new FriendTie(this.getEdge().get(index).getLabel(), weight);
        nFriendTie.addVertices(this.getEdge().get(index).getList());
        this.removeEdge(this.getEdge().get(index));
        this.addEdge(nFriendTie);
      } else if (this.getEdge().get(index) instanceof ForwardTie) {
        ForwardTie nFriendTie = new ForwardTie(this.getEdge().get(index).getLabel(), weight);
        nFriendTie.addVertices(this.getEdge().get(index).getList());
        this.removeEdge(this.getEdge().get(index));
        this.addEdge(nFriendTie);
      } else if (this.getEdge().get(index) instanceof CommentTie) {
        CommentTie nFriendTie = new CommentTie(this.getEdge().get(index).getLabel(), weight);
        nFriendTie.addVertices(this.getEdge().get(index).getList());
        this.removeEdge(this.getEdge().get(index));
        this.addEdge(nFriendTie);
      }
    }
  }

  public void addEdgeNOC(Edge edge) throws Exception {
    super.addEdge(edge);
  }
}
