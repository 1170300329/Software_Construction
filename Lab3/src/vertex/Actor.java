package vertex;

public class Actor extends Vertex{
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
	 * @param label
	 */
	public Actor(String label) {
		super(label);
	}
	/**
	 * fill the information of a vertex with args
	 */
	@Override
	public void fillVertexInfo(String[] args) {
		this.age=Integer.valueOf(args[0]);
		this.sex=args[1];
	}
	/**
	 * get the age of an actor
	 * @return age
	 */
	public int getAge() {
		return age;
	}
	/**
	 * get the sex of an actor
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
		return "Actor [label="+this.getLabel()+" age=" + age + ", sex=" + sex + "]";
	}
	/**
	  * change the attrs in vertex
	  * @param group
	  * @param group2
	  */
	@Override
	public void changeAttr(String group, String group2) {
		if(group.equals("age")) {
			this.age=Integer.valueOf(group2);
		}else if(group.equals("sex")) {
			this.sex=group2;
		}else {
			System.out.println("格式有误");
		}
		
	}
	
}
