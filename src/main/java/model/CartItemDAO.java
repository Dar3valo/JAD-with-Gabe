package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CartItemDAO {
	public CartItem insertBooking (int cart_item_id, Date booking_date, String special_request, String main_address, int postal_code,
			int user_id, int schedule_id, int service_id) {
		CartItem bookingInfo = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			String sql = "INSERT INTO cart_item (booking_date, special_request, main_address, postal_code, user_id, schedule_id, service_id) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
			
			stmt = conn.prepareStatement(sql);
			stmt.setDate(1, booking_date);
			stmt.setString(2, special_request);
			stmt.setString(3, main_address);
			stmt.setInt(4, postal_code);
			stmt.setInt(5, user_id);
			stmt.setInt(6, schedule_id);
			stmt.setInt(7, service_id);
			
			int rowsAffected = stmt.executeUpdate();
			
			if(rowsAffected > 0) {
				bookingInfo = new CartItem(cart_item_id, booking_date, special_request, main_address, postal_code, user_id, schedule_id, service_id);
			}else {
				System.out.println("Booking failed, no rows affected");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return bookingInfo;
	}
}
