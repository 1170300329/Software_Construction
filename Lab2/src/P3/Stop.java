package P3;

public class Stop {
	private String route;
 	private String name;
	private double latitude;
	private double longitude;
	private int time;
	private int visited;
	private boolean isDivided=false;
	private int id=0;
	private Stop son1=null;
	private Stop son2=null;
	private Stop parent=null;
	private int dist=Integer.MAX_VALUE;
	public Stop(String route,String name,double latitude,double longitude,int time) {
		this.route =route ;
		this.name=name;
		this.latitude=latitude;
		this.longitude=longitude;
		this.time=time;
	}
	public Stop(Stop stop) {
		this.route =stop.getRoute();
		this.name=stop.getName();
		this.latitude=stop.getLatitude();
		this.longitude=stop.getLongitude();
		this.time=stop.getTime();
	}
	public String getName() {
		return name;
	}
	public String getRoute() {
		return route;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time=time;
	}
	public boolean similarto(Stop stop) {
		if(stop.getName().equals(name)&&stop.getLatitude()==latitude&&stop.getLongitude()==longitude) {
			return true;
		}
		return false;
	}
	
	public int getVisited() {
		return visited;
	}
	public void setVisited(int visited) {
		this.visited = visited;
	}
	public boolean getIsDivided() {
		return isDivided;
	}
	public void setIsDivided(boolean isDivided) {
		this.isDivided = isDivided;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + time;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stop other = (Stop) obj;
		if (id != other.id)
			return false;
		if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (time != other.time)
			return false;
		return true;
	}
	public Stop getSon1() {
		return son1;
	}
	public void setSon1(Stop son1) {
		this.son1 = son1;
	}
	public Stop getSon2() {
		return son2;
	}
	public void setSon2(Stop son2) {
		this.son2 = son2;
	}
	public int getDist() {
		return dist;
	}
	public void setDist(int dist) {
		this.dist = dist;
	}
	public Stop getParent() {
		return parent;
	}
	public void setParent(Stop parent) {
		this.parent = parent;
	}
	@Override
	public String toString() {
		return "[route=" + route + ", name=" + name + ", latitude=" + latitude + ", longitude=" + longitude
				+ ", time=" + time + ", isDivided=" + isDivided + ", id=" + id + "]";
	}
	
}
