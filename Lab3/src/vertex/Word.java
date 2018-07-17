package vertex;

public class Word extends Vertex{
	
	private static final long serialVersionUID = 1L;
	/**
	 * new a word with a label.
	 * @param label
	 */
	public Word(String label) {
		super(label);
	}
	/**
	 * override fillVertexinfo()
	 */
	@Override
	public void fillVertexInfo(String[] args) {
		
	}
	/**
	 * override toString to express the information of the word
	 */
	@Override
	public String toString() {
		return "Word [label="+this.getLabel()+"]";
	}
	/**
	  * change the attrs in vertex
	  * @param group
	  * @param group2
	  */
	@Override
	public void changeAttr(String group, String group2) {
		System.out.println("没有可改的属性");
	}

	
}
