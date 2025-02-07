package Models.Service;

public class Service {
	private int service_id;
	private String name;
	private String description;
	private double price;
	private String service_photo_url;
	private int booking_count;
	private double totalRevenue;
    private int totalBookings;
    private double averageRating;
	
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
	
	public Service(int service_id, String name, double totalRevenue, int totalBookings) {
        this.service_id = service_id;
        this.name = name;
        this.totalRevenue = totalRevenue;
        this.totalBookings = totalBookings;
    }
	
	public Service(int service_id, String name, double averageRating) {
		super();
		this.service_id = service_id;
		this.name = name;
		this.averageRating = averageRating;
	}
	
	public Service() {
		
	}
	
	

	public int getBooking_count() {
		return booking_count;
	}

	public void setBooking_count(int booking_count) {
		this.booking_count = booking_count;
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
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getService_photo_url() {
		return service_photo_url;
	}
	
	public void setService_photo_url(String service_photo_url) {
		this.service_photo_url = service_photo_url;
	}
	
	public double getTotalRevenue() {
		return totalRevenue;
	}

	public void setTotalRevenue(double totalRevenue) {
		this.totalRevenue = totalRevenue;
	}

	public int getTotalBookings() {
		return totalBookings;
	}

	public void setTotalBookings(int totalBookings) {
		this.totalBookings = totalBookings;
	}
	
	public double getAverageRating() {
		return averageRating;
	}
	
	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}
}
