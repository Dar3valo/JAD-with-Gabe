package Models.Cart;

import java.sql.Timestamp;
import java.sql.Date;

public class CartItem {
	private int cart_item_id;
	private Date booking_date;
	private String special_request;
	private String main_address;
	private int postal_code;
	private int user_id;
	private int schedule_id;
	private int service_id;
	private String serviceName;  // New field for service name
    private double servicePrice;
	
	
	public CartItem(int cart_item_id, Date booking_date, String special_request, String main_address, int postal_code,
			int user_id, int schedule_id, int service_id) {
		super();
		this.cart_item_id = cart_item_id;
		this.booking_date = booking_date;
		this.special_request = special_request;
		this.main_address = main_address;
		this.postal_code = postal_code;
		this.user_id = user_id;
		this.schedule_id = schedule_id;
		this.service_id = service_id;
	}
	
	public CartItem(int cart_item_id, Date booking_date, String special_request, String main_address, int postal_code,
			int user_id, int schedule_id, int service_id, String serviceName, double servicePrice) {
		this.cart_item_id = cart_item_id;
		this.booking_date = booking_date;
		this.special_request = special_request;
		this.main_address = main_address;
		this.postal_code = postal_code;
		this.user_id = user_id;
		this.schedule_id = schedule_id;
		this.service_id = service_id;
		this.serviceName = serviceName;
		this.servicePrice = servicePrice;
	}


	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public double getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(double servicePrice) {
		this.servicePrice = servicePrice;
	}
	
	public int getCart_item_id() {
		return cart_item_id;
	}


	public void setCart_item_id(int cart_item_id) {
		this.cart_item_id = cart_item_id;
	}


	public Date getBooking_date() {
		return booking_date;
	}


	public void setBooking_date(Date booking_date) {
		this.booking_date = booking_date;
	}


	public String getSpecial_request() {
		return special_request;
	}


	public void setSpecial_request(String special_request) {
		this.special_request = special_request;
	}


	public String getMain_address() {
		return main_address;
	}


	public void setMain_address(String main_address) {
		this.main_address = main_address;
	}


	public int getPostal_code() {
		return postal_code;
	}


	public void setPostal_code(int postal_code) {
		this.postal_code = postal_code;
	}


	public int getUser_id() {
		return user_id;
	}


	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	public int getSchedule_id() {
		return schedule_id;
	}


	public void setSchedule_id(int schedule_id) {
		this.schedule_id = schedule_id;
	}


	public int getService_id() {
		return service_id;
	}


	public void setService_id(int service_id) {
		this.service_id = service_id;
	}
	
	public String getServiceImg() {
		return null;
	}
	
}
