package src.vertex;

import src.exception.AttrErrorException;
import src.exception.InvalidCmdException;
import src.log.MyLog;

public class Actor extends Vertex {
  private static final long serialVersionUID = 1L;
  private int age;
  private String sex;

  // Abstraction function:
  // the age represents the age of the actor,the sex represents the sex
  // of the actor
  // Representation invariant:
  // the age should be positive integer,the sex should only be M/F
  // Safety from rep exposure:
  // all the fileds are private and immutable

  /**
   * new an actor with a label
   * 
   * @param label
   */
  public Actor(String label) {
    super(label);
  }

  /**
   * fill the information of a vertex with args
   * 
   * @throws Exception
   */
  @Override
  public void fillVertexInfo(String[] args) throws Exception {
    // this.age=Integer.valueOf(args[0]);
    // this.sex=args[1];
    try {
      this.age = Integer.valueOf(args[1]);
    } catch (Exception e) {
      MyLog.logger.error("AttrErrorException:参数不合法");
      throw new AttrErrorException("参数不合法");
    }

    if (args[0].equals("M") || args[0].equals("F")) {
      this.sex = args[0];
    } else {
      MyLog.logger.error("AttrErrorException:参数不合法");
      throw new AttrErrorException("参数不合法");
    }
  }

  /**
   * get the age of an actor
   * 
   * @return age
   */
  public int getAge() {
    return age;
  }

  /**
   * get the sex of an actor
   * 
   * @return
   */
  public String getSex() {
    return sex;
  }

  /**
   * override the toString() to show the detail information of the vertex
   */
  @Override
  public String toString() {
    return "Actor [label=" + this.getLabel() + " age=" + age + ", sex=" + sex + "]";
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
    if (group.equals("age")) {
      try {
        this.age = Integer.valueOf(group2);
      } catch (Exception e) {
        MyLog.logger.error("InvalidCmdException:修改后的参数不合法");
        throw new InvalidCmdException("修改后的参数不合法");
      }
    } else if (group.equals("sex")) {
      if (group2.equals("M") || group2.equals("F")) {
        this.sex = group2;
      } else {
        MyLog.logger.error("InvalidCmdException:修改后的参数不合法");
        throw new InvalidCmdException("修改后的参数不合法");
      }
    } else {
      MyLog.logger.error("InvalidCmdException:要修改的属性不存在");
      throw new InvalidCmdException("要修改的属性不存在");
    }
  }

}
