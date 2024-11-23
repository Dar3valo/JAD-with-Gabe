package model;

public class ServiceServiceCategory {
	private int service_id;
	private int service_category_id;
	
	public ServiceServiceCategory(int service_id, int service_category_id) {
		super();
		this.service_id = service_id;
		this.service_category_id = service_category_id;
	}
	
	public int getService_id() {
		return service_id;
	}
	
	public void setService_id(int service_id) {
		this.service_id = service_id;
	}
	
	public int getService_category_id() {
		return service_category_id;
	}
	
	public void setService_category_id(int service_category_id) {
		this.service_category_id = service_category_id;
	}
}
