package vertex;

import mementomanager.SvrMemManager;
import statememento.ServerMemento;

public class Server extends Vertex{
	private static final long serialVersionUID = 1L;
	private String IP;
	private String state="close";
	// Abstraction function:
	// the IP represents the IP address of the server,while the state represents the current
	// status of the vertex
	// Representation invariant:
	// the IP should be divided by "." into four parts,and each part ranges from 0 to 255([0,255))
	// and the state can be only "close" or "open"
	// Safety from rep exposure:
	// all the fields are private and immutable.
	/**
	 * new a server with a label
	 * @param label
	 */
	public Server(String label) {
		super(label);
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
		return "Server [label="+this.getLabel()+"IP=" + IP + "]";
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
	public SvrMemManager save() {
		ServerMemento memento=this.createMemento();
		SvrMemManager sm=new SvrMemManager();
		sm.setMemento(memento);
		return sm;
	}
	/**
	 * restore the previous status of the vertex with the manager sm
	 * @param sm
	 */
	public void restore(SvrMemManager sm) {
		this.state=sm.getMemento().getState();
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
	public ServerMemento createMemento() {
		return new ServerMemento(state);
	}
	public static void main(String[] args) throws Exception {
		Server server=new Server("arsd");
		String[] args1= {"190.21.20.09"};
		server.fillVertexInfo(args1);
		Server server2=(Server)server.deepClone();
		String[] args2= {"193.21.20.09"};
		server2.fillVertexInfo(args2);
		System.out.println(server.getIP());
		System.out.println(server2.getIP());
		
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
