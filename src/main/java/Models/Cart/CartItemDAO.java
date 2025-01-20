package Models.Cart;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
	
	public static List<CartItem> getCartItems(int user_id){
		List<CartItem> cartItems = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			String sql = "SELECT c.cart_item_id, c.booking_date, c.special_request, " +
                    "c.main_address, c.postal_code, c.user_id, c.schedule_id, " +
                    "c.service_id, s.name AS service_name, s.price AS service_price " +
                    "FROM cart_item c " +
                    "JOIN service s ON c.service_id = s.service_id WHERE c.user_id = ? " +
                    "ORDER BY c.booking_date DESC";
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user_id);
			
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				int cartItemId = rs.getInt("cart_item_id");
                Date bookingDate = rs.getDate("booking_date");
                String specialRequest = rs.getString("special_request");
                String mainAddress = rs.getString("main_address");
                int postalCode = rs.getInt("postal_code");
                int userId = rs.getInt("user_id");
                int scheduleId = rs.getInt("schedule_id");
                int serviceId = rs.getInt("service_id");
                String serviceName = rs.getString("service_name");
                double servicePrice = rs.getDouble("service_price");
                
                CartItem cartItem = new CartItem(cartItemId, bookingDate, specialRequest, mainAddress, postalCode, userId,
                		scheduleId, serviceId, serviceName, servicePrice);
                cartItems.add(cartItem);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return cartItems;
	}
	
	public boolean updateCartItem(int cart_item_id, Date booking_date, String special_request, String main_address,
			int postal_code, int user_id, int schedule_id, int service_id, String serviceName, double servicePrice) {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean isUpdated = false;

		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			String sql = "UPDATE cart_item SET booking_date = ?, special_request = ?, main_address = ?, "
					+ "postal_code = ?, user_id = ?, schedule_id = ?, service_id = ?, service_name = ?, "
					+ "service_price = ? WHERE cart_item_id = ?;";

			stmt = conn.prepareStatement(sql);
			stmt.setDate(1, booking_date);
			stmt.setString(2, special_request);
			stmt.setString(3, main_address);
			stmt.setInt(4, postal_code);
			stmt.setInt(5, user_id);
			stmt.setInt(6, schedule_id);
			stmt.setInt(7, service_id);
			stmt.setString(8, serviceName);
			stmt.setDouble(9, servicePrice);
			stmt.setInt(10, cart_item_id);

			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected > 0) {
				isUpdated = true;
			} else {
				System.out.println("Update failed, no rows affected.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return isUpdated;
	}
	
	public static void deleteCartItem(int cart_item_id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			String sql = "DELETE FROM Cart_Item WHERE cart_item_id = ?";
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cart_item_id);
			
			stmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	
	//Update schedule in the cart
}
