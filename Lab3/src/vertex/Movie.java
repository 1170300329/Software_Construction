package vertex;

public class Movie extends Vertex{
	private static final long serialVersionUID = 1L;
	private int year;
	private String position;
	private double IMDb;
	// Abstraction function:
	// the year represents the year when the movie was showed firstly
	// the position is a String representing the position where the movie was made
	// the IMDb represents the score of IMDb
	// Representation invariant:
	// the year shoud ranges from 1900 to 2018([1900,2018)),the position should be a string which
	// is not null,the IMDb shoud ranges form 0 to 10 and with at most two decimal places.
	// Safety from rep exposure:
	// all the fields are private and immutable.
	/**
	 * new a movie with label
	 * @param label
	 */
	public Movie(String label) {
		super(label);
	}
	/**
	 * fill the information of a vertex with args
	 */
	@Override
	public void fillVertexInfo(String[] args) {
		this.year=Integer.valueOf(args[0]);
		this.position=args[1];
		this.IMDb=Double.valueOf(args[2]);
		
	}
	/**
	 * get the year
	 * @return year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * get the position
	 * @return position
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * get the IMDb
	 * @return IMDb
	 */
	public double getIMDb() {
		return IMDb;
	}
	/**
	 * override the toString() to show the detail information of the movie
	 */
	@Override
	public String toString() {
		return "Movie [label="+this.getLabel()+" year=" + year + ", position=" + position + ", IMDb=" + IMDb + "]";
	}
	/**
	  * change the attrs in vertex
	  * @param group
	  * @param group2
	  */
	@Override
	public void changeAttr(String group, String group2) {
		if(group.equals("year")) {
			this.year=Integer.valueOf(group2);
		}else if(group.equals("position")) {
			this.position=group2;
		}else if(group.equals("IMDb")) {
			this.IMDb=Double.valueOf(group2);
		}else {
			System.out.println("格式有误");
		}
		
	}

}
