package model;

public class Service {
	private int service_id;
	private String name;
	private String description;
	private double price;
	private String service_photo_url;
	
	// constructor where all values are passed
	public Service(int service_id, String name, String description, double price, String service_photo_url) {
		super();
		this.service_id = service_id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.service_photo_url = service_photo_url;
	}
	
	// provide default values if not specified (only id and name needs to be specified)
	public Service(int service_id, String name) {
		super();
		this.service_id = service_id;
		this.name = name;
		this.description = "";
		this.price = 0.0;
		this.service_photo_url = "../Image/defaultpic.png";
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
