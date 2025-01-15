package ServiceCategoryModel;

public class ServiceCategory {
	private int service_category_id;
	private String name;
	private String description;
	
	public ServiceCategory(int service_category_id, String name, String description) {
		super();
		this.service_category_id = service_category_id;
		this.name = name;
		this.description = description;
	}
	
	public int getService_category_id() {
		return service_category_id;
	}
	
	public void setService_category_id(int service_category_id) {
		this.service_category_id = service_category_id;
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
