package P3;

public class Itinerary {
	private String instructions="";
	private int startTime;
	private int endTime;
	
	private Stop startLocation;
	private Stop endLocation;
	
		
	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public Stop getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(Stop startLocation) {
		this.startLocation = startLocation;
	}

	

	public Stop getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(Stop endLocation) {
		this.endLocation = endLocation;
	}

	public int getWaitTime() {
		return getEndTime()-getStartTime();
	}

	
}
