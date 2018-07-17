package src.vertex;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import src.exception.InvalidCmdException;

public abstract class Vertex implements Serializable{
	private static final long serialVersionUID = 1L;
	private String label=null;
	// Abstraction function:
	// the label represents the name of a vertex.
	// Representation invariant:
	// the label shouldn't be null and shouldn't be repeated.
	// Safety from rep exposure:
	// label is private and it has no method which can change it.
	public void checkRep() {
		assert label!=null:"顶点标签为空";
	}
	/**
	 * make a new Vertex with a label.
	 * @param label
	 */
	public Vertex(String label){
		assert label.matches("\\w+"):"顶点label不符合正则表达式要求";
		this.label=label;
		checkRep();
	}
	/**
	 * get the label of a vertex
	 * @return the label of a vertex
	 */
	public String getLabel() {
		checkRep();
		return label;
	}
	/**
	 * use the args to fill the information of vertex
	 * @param args
	 * @throws InvalidCmdException 
	 * @throws Exception 
	 */
	public abstract void fillVertexInfo(String[] args) throws Exception;
	/**
	 * override a hashCode() which includes label only.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}
	/**
	 * override a equals() which includes label only.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vertex other = (Vertex) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	/**
	 * override toString()
	 */
	@Override
	public String toString() {
		return "Vertex label="+label;
	}
	/**
	 * Deep clone a vertex instance to finish defensive copy.
	 * @return a new Object which has the same attributes.
	 * @throws Exception
	 */
	 public Object deepClone() throws Exception
	    {
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        ObjectOutputStream oos = new ObjectOutputStream(bos);
	        oos.writeObject(this);
	 
	        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
	        ObjectInputStream ois = new ObjectInputStream(bis);
	        return ois.readObject();
	    }
	 /**
	  * change the attrs in vertex
	  * @param group
	  * @param group2
	 * @throws InvalidCmdException 
	  */
	public abstract void changeAttr(String group, String group2) throws InvalidCmdException;
}
