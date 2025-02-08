package Models.Cart;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import Models.Booking.Booking;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	public String getCartItemName() {
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    String timing = "";

	    try {
	        Class.forName("org.postgresql.Driver");
	        String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
	        conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

	        String sql = "SELECT start_time::time, end_time::time FROM schedule WHERE schedule_id = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, this.getSchedule_id());  // Using the current object's schedule_id

	        rs = stmt.executeQuery();

	        if (rs.next()) {
	            LocalTime startTime = rs.getObject("start_time", LocalTime.class);
	            LocalTime endTime = rs.getObject("end_time", LocalTime.class);
	            
	            timing = String.format("%s - %s", 
	                startTime.format(DateTimeFormatter.ofPattern("HH:mm")),
	                endTime.format(DateTimeFormatter.ofPattern("HH:mm"))
	            );
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		
		return String.format("%s | %s | %s", this.getServiceName(), this.getBooking_date(), timing);
	}
}
