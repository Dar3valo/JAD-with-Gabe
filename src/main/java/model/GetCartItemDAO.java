package model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class GetCartItemDAO {
	public List<CartItem> getCartItems(){
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
                    "JOIN service s ON c.service_id = s.service_id " +
                    "ORDER BY c.booking_date DESC";
			
			stmt = conn.prepareStatement(sql);
			
			
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
}
