package vertex;

import mementomanager.RtrMemManager;
import statememento.RouterMemento;

public class Router extends Vertex{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String IP;
	private String state="close";
	// Abstraction function:
	// the IP represents the IP address of the router,while the state represents the current
	// status of the vertex
	// Representation invariant:
	// the IP should be divided by "." into four parts,and each part ranges from 0 to 255([0,255))
	// and the state can be only "close" or "open"
	// Safety from rep exposure:
	// all the fields are private and immutable.
	/**
	 * new a router with a label
	 * @param label
	 */
	public Router(String label) {
		super(label);
		// TODO Auto-generated constructor stub
	}
	/**
	 * override the fillvertexinfo() to fill the IP address with args
	 * @param args
	 */
	@Override
	public void fillVertexInfo(String[] args) {
		this.IP=args[0];
	}
	/**
	 * get the IP address of the vertex
	 * @return　IP
	 */
	public String getIP() {
		return IP;
	}
	/**
	 * override the toString() to show the detail information of the vertex
	 */
	@Override
	public String toString() {
		return "Router [label="+this.getLabel()+"IP=" + IP + "]";
	}
	/**
	 * change the status to "open"
	 */
	public void open() {
		this.state="open";
	}
	/**
	 * change the status to "close"
	 */
	public void close() {
		this.state="close";
	}
	/**
	 * save the status into a memento and new a memento manager for it
	 */
	public RtrMemManager save() {
		RouterMemento memento=this.createMemento();
		RtrMemManager rm=new RtrMemManager();
		rm.setMemento(memento);
		return rm;
	}
	/**
	 * restore the previous status of the vertex with the manager sm
	 * @param sm
	 */
	public void restore(RtrMemManager rm) {
		this.state=rm.getMemento().getState();
	}
	/**
	 * get the status of the vertex
	 * @return state
	 */
	public String getState() {
		return state;
	}
	/**
	 * create a memento for the state.
	 * @return memento
	 */
	public RouterMemento createMemento() {
		return new RouterMemento(state);
	}
	/**
	  * change the attrs in vertex
	  * @param group
	  * @param group2
	  */
	@Override
	public void changeAttr(String group, String group2) {
		if(group.equals("IP")) {
			this.IP=group2;
		}else {
			System.out.println("格式有误");
		}
	}
}
