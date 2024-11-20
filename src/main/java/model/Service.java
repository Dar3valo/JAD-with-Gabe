package model;

public class Service {
	private int service_id;
	private String name;
	
	public Service(int service_id, String name) {
		super();
		this.service_id = service_id;
		this.name = name;
	}

	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
