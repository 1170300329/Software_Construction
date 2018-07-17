package src.vertex;

import src.exception.AttrErrorException;
import src.exception.InvalidCmdException;
import src.log.MyLog;
import src.mementomanager.PsonMemManager;
import src.statememento.PersonMemento;

public class Person extends Vertex {
  private static final long serialVersionUID = 1L;
  private String sex;
  private int age;
  private String state = "deactive";
  private int indegree = 0;

  // Abstraction function:
  // the sex represents the sex of the person,the age represents the age of the
  // person
  // the state represents the state of a user,and the indegree represents how many
  // persons are connected to the person.
  // Representation invariant:
  // the sex should be M/F,the age should be positive integer,the state should
  // only
  // be "deactive","active" and "locked"
  // Safety from rep exposure:
  // the fields are private and immutable

  /**
   * new a person with a label
   * 
   * @param label
   */
  public Person(String label) {
    super(label);
  }

  /**
   * override the fillVertexInfo() to fill the information of a vertex
   * 
   * @throws Exception
   */
  @Override
  public void fillVertexInfo(String[] args) throws Exception {
    // this.sex=args[0];
    // this.age=Integer.valueOf(args[1]);
    assert args.length == 2 : "参数数目错误";
    try {
      this.age = Integer.valueOf(args[1]);
    } catch (Exception e) {
      MyLog.logger.error("AttrErrorException:属性不合法");
      throw new AttrErrorException("属性不合法");
    }

    if (args[0].equals("M") || args[0].equals("F")) {
      this.sex = args[0];
    } else {
      MyLog.logger.error("AttrErrorException:属性不合法");
      throw new AttrErrorException("属性不合法");
    }
  }

  /**
   * get the sex of a person
   * 
   * @return sex
   */
  public String getSex() {
    return sex;
  }

  /**
   * get the age of a person
   * 
   * @return age
   */
  public int getAge() {
    return age;
  }

  /**
   * override the toString() to show the information of the person
   */
  @Override
  public String toString() {
    return "Person [label=" + this.getLabel() + " sex=" + sex + ", age=" + age + " Indegree"
        + this.getIndegree() + "]";
  }

  /**
   * change the status to locked
   */
  public void lock() {
    this.state = "locked";
  }

  /**
   * change the status to active
   */
  public void unlock() {
    this.state = "active";
  }

  /**
   * change the status to active
   */
  public void active() {
    this.state = "active";
  }

  /**
   * change the status ti deactive
   */
  public void deactive() {
    this.state = "deactive";
  }

  /**
   * save the status into a memento and new a memento manager for it
   */
  public PsonMemManager save() {
    PersonMemento memento = this.createMemento();
    PsonMemManager pm = new PsonMemManager();
    pm.setMemento(memento);
    return pm;
  }

  /**
   * restore the previous status of the vertex with the manager sm
   * 
   * @param sm
   */
  public void restore(PsonMemManager pm) {
    this.state = pm.getMemento().getState();
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
  public PersonMemento createMemento() {
    return new PersonMemento(state);
  }

  /**
   * get the indegree of the person
   * 
   * @return indegree
   */
  public int getIndegree() {
    return indegree;
  }

  /**
   * set the indegree with the para indegree
   * 
   * @param indegree
   */
  public void setIndegree(int indegree) {
    this.indegree = indegree;
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

  public static void main(String[] args) {
    Person person = new Person("testperson");

    System.out.println("初始化状态" + person.getState());
    person.active();
    System.out.println("active后状态" + person.getState());
    PsonMemManager pm = person.save();
    person.lock();
    System.out.println("保存之后调用lock状态" + person.getState());
    person.restore(pm);
    System.out.println("恢复保存之前的状态" + person.getState());
  }
}
