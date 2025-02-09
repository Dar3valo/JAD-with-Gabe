package Models.Booking;

import java.sql.Date;
import java.sql.Timestamp;

public class Booking {
	private int booking_id;
	private Date booking_date;
	private String special_request;
	private String main_address;
	private int postal_code;
	private int user_id;
	private int schedule_id;
	private int service_id;
	private String serviceName;  // New field for service name
    private double servicePrice;
    private Timestamp creation_date;
    private int status_id;
    private String username;
    private String userEmail;
    private String bookingPeriod;
    private String status_name;
    private String status_description;

	public Booking(int booking_id, Date booking_date, String special_request, String main_address, int postal_code,
			int user_id, int schedule_id, int service_id, int status_id) {
		super();
		this.booking_id = booking_id;
		this.booking_date = booking_date;
		this.special_request = special_request;
		this.main_address = main_address;
		this.postal_code = postal_code;
		this.user_id = user_id;
		this.schedule_id = schedule_id;
		this.service_id = service_id;
		this.status_id = status_id;
	}
	
	public Booking(int booking_id, Date booking_date, String special_request, String main_address, int postal_code,
			int user_id, int schedule_id, int service_id, String serviceName, double servicePrice, Timestamp creation_date, 
			int status_id) {
		super();
		this.booking_id = booking_id;
		this.booking_date = booking_date;
		this.special_request = special_request;
		this.main_address = main_address;
		this.postal_code = postal_code;
		this.user_id = user_id;
		this.schedule_id = schedule_id;
		this.service_id = service_id;
		this.serviceName = serviceName;
		this.servicePrice = servicePrice;
		this.creation_date = creation_date;
		this.status_id = status_id;
	}
	
	public Booking(int booking_id, Date booking_date, String special_request, String main_address, int postal_code,
			int user_id, int schedule_id, int service_id, String serviceName, double servicePrice) {
		super();
		this.booking_id = booking_id;
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
	
	public Booking() {
		
	}

	public Timestamp getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Timestamp creation_date) {
		this.creation_date = creation_date;
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

	public int getBooking_id() {
		return booking_id;
	}

	public void setBooking_id(int booking_id) {
		this.booking_id = booking_id;
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
	
	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	public String getBookingPeriod() {
		return bookingPeriod;
	}

	public void setBookingPeriod(String bookingPeriod) {
		this.bookingPeriod = bookingPeriod;
	}
	
	public String getStatusDescription() {
		return status_description;
	}

	public void setStatusDescription(String status_description) {
		this.status_description = status_description;
	}
	
	public String getStatusName() {
		return status_name;
	}

	public void setStatusName(String status_name) {
		this.status_name = status_name;
	}
}