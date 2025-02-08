package Models.Schedule;

public class Schedule {
	private int schedule_id;
	private String start_time;
	private String end_time;
	
	
	public Schedule(int schedule_id, String start_time, String end_time) {
		super();
		this.schedule_id = schedule_id;
		this.start_time = start_time;
		this.end_time = end_time;
	}


	public int getSchedule_id() {
		return schedule_id;
	}


	public void setSchedule_id(int schedule_id) {
		this.schedule_id = schedule_id;
	}


	public String getStart_time() {
		return start_time;
	}


	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}


	public String getEnd_time() {
		return end_time;
	}


	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
}
