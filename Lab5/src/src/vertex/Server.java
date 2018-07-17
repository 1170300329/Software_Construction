package src.vertex;

import src.exception.AttrErrorException;
import src.exception.InvalidCmdException;
import src.log.MyLog;
import src.mementomanager.SvrMemManager;
import src.statememento.ServerMemento;

public class Server extends Vertex {
  private static final long serialVersionUID = 1L;
  private String IP;
  private String state = "close";

  // Abstraction function:
  // the IP represents the IP address of the server,while the state represents the
  // current
  // status of the vertex
  // Representation invariant:
  // the IP should be divided by "." into four parts,and each part ranges from 0
  // to 255([0,255))
  // and the state can be only "close" or "open"
  // Safety from rep exposure:
  // all the fields are private and immutable.

  /**
   * new a server with a label
   * 
   * @param label
   */
  public Server(String label) {
    super(label);
  }

  /**
   * override the fillvertexinfo() to fill the IP address with args
   * 
   * @param args
   * @throws Exception
   */
  @Override
  public void fillVertexInfo(String[] args) throws Exception {
    String[] temp = args[0].split("\\.");
    int flag = 0;
    for (int i = 0; i < temp.length; i++) {
      try {
        if (Integer.valueOf(temp[i]) > 255 || Integer.valueOf(temp[i]) < 0) {
          flag = 1;
        }
      } catch (Exception e) {
        MyLog.logger.error("AttrErrorException:属性不合法");
        throw new AttrErrorException("属性不合法");
      }
    }
    if (flag == 0)
      this.IP = args[0];
    if (flag == 1) {
      MyLog.logger.error("AttrErrorException:属性不合法");
      throw new AttrErrorException("属性不合法");
    }
  }

  /**
   * get the IP address of the vertex
   * 
   * @return IP
   */
  public String getIP() {
    return IP;
  }

  /**
   * override the toString() to show the detail information of the vertex
   */
  @Override
  public String toString() {
    return "Server [label=" + this.getLabel() + "IP=" + IP + "]";
  }

  /**
   * change the status to "open"
   */
  public void open() {
    this.state = "open";
  }

  /**
   * change the status to "close"
   */
  public void close() {
    this.state = "close";
  }

  /**
   * save the status into a memento and new a memento manager for it
   */
  public SvrMemManager save() {
    ServerMemento memento = this.createMemento();
    SvrMemManager sm = new SvrMemManager();
    sm.setMemento(memento);
    return sm;
  }

  /**
   * restore the previous status of the vertex with the manager sm
   * 
   * @param sm
   */
  public void restore(SvrMemManager sm) {
    this.state = sm.getMemento().getState();
  }

  /**
   * get the status of the vertex
   * 
   * @return state
   */
  public String getState() {
    return state;
  }

  /**
   * create a memento for the state.
   * 
   * @return memento
   */
  public ServerMemento createMemento() {
    return new ServerMemento(state);
  }

  /**
   * change the attrs in vertex
   * 
   * @param group
   * @param group2
   * @throws InvalidCmdException
   */
  @Override
  public void changeAttr(String group, String group2) throws InvalidCmdException {
    if (group.equals("IP")) {
      String[] temp = group2.split("\\.");
      int flag = 0;
      for (int i = 0; i < temp.length; i++) {
        try {
          if (Integer.valueOf(temp[i]) > 255 || Integer.valueOf(temp[i]) < 0) {
            flag = 1;
          }
        } catch (Exception e) {
          MyLog.logger.error("InvalidCmdException:修改后的属性不合法");
          throw new InvalidCmdException("修改后的指令不合法");
        }
      }
      if (flag == 0)
        this.IP = group2;
      if (flag == 1) {
        MyLog.logger.error("InvalidCmdException:修改后的属性不合法");
        throw new InvalidCmdException("修改后的指令不合法");
      }
    } else {
      MyLog.logger.error("InvalidCmdException:修改后的属性不合法");
      throw new InvalidCmdException("要修改的属性不存在");
    }
  }
}
