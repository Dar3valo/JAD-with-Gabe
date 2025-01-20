package Models.Status;

public class Status {
	private int status_id;
	private String name;
	private String description;
	
	public Status(int status_id, String name, String description) {
		super();
		this.status_id = status_id;
		this.name = name;
		this.description = description;
	}
	
	//Default constructor
	public Status() {
		
	}
	
	public int getStatus_id() {
		return status_id;
	}
	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
