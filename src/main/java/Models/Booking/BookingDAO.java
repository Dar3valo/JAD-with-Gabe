package Models.Booking;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
	public Booking transferCartData(int user_id) {

		Booking transferInfo = null;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			String sql = """
					     INSERT INTO Booking (
					        booking_date, special_request, main_address,
					        postal_code, user_id, schedule_id, service_id
					    )
					    SELECT
					        booking_date, special_request, main_address,
					        postal_code, user_id, schedule_id, service_id
					    FROM Cart_Item
					    WHERE user_id = ?
					    RETURNING booking_id, booking_date, special_request, main_address,
					              postal_code, user_id, schedule_id, service_id
					""";

			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user_id);

			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				transferInfo = new Booking(
						rs.getInt("booking_id"),
	                    rs.getDate("booking_date"),
	                    rs.getString("special_request"),
	                    rs.getString("main_address"),
	                    rs.getInt("postal_code"),
	                    rs.getInt("user_id"),
	                    rs.getInt("schedule_id"),
	                    rs.getInt("service_id")
				);
				deleteFromCart(user_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close resources
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return transferInfo;
	}
	
	private void deleteFromCart(int user_id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			String deleteSQL = "DELETE FROM Cart_Item WHERE user_id = ?";
			
			stmt = conn.prepareStatement(deleteSQL);
			stmt.setInt(1, user_id);
			stmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			// Close resources
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//Get Booking Information
	public List<Booking> getBookingInfo(int user_id){
		List<Booking> bookedInfo = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			String selectSQL = "SELECT b.booking_id, b.booking_date, b.special_request, b.main_address, b.postal_code, "
					+ "b.user_id, b.schedule_id, b.service_id, s.name AS service_name, s.price AS service_price FROM booking b "
					+ "JOIN Service s "
					+ "ON b.service_id = s.service_id WHERE user_id = ? "
					+ "ORDER BY b.booking_date DESC";
			
			stmt = conn.prepareStatement(selectSQL);
			stmt.setInt(1, user_id);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				int bookingId = rs.getInt("booking_id");
                Date bookingDate = rs.getDate("booking_date");
                String specialRequest = rs.getString("special_request");
                String mainAddress = rs.getString("main_address");
                int postalCode = rs.getInt("postal_code");
                int userId = rs.getInt("user_id");
                int scheduleId = rs.getInt("schedule_id");
                int serviceId = rs.getInt("service_id");
                String serviceName = rs.getString("service_name");
                double servicePrice = rs.getDouble("service_price");
                
                Booking bookedItem = new Booking(bookingId, bookingDate, specialRequest, mainAddress, postalCode, userId,
                		scheduleId, serviceId, serviceName, servicePrice);
                bookedInfo.add(bookedItem);
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
		return bookedInfo;
	};
	
	//getTransactionLatestResults
	public List<Booking> getTransactionLatestResults(int user_id) {
	    List<Booking> latestResults = new ArrayList<>();
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;

	    try {
	        Class.forName("org.postgresql.Driver");
	        String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
	        conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

	        // SQL to fetch the latest transaction based on the booking date
	        String latestTransactionSQL = "SELECT b.booking_id, b.booking_date, b.special_request, b.main_address, b.postal_code, "
	                + "b.user_id, b.schedule_id, b.service_id, s.name AS service_name, s.price AS service_price, b.purchase_time "
	                + "FROM booking b "
	                + "JOIN Service s ON b.service_id = s.service_id "
	                + "WHERE b.user_id = ? AND b.purchase_time = (SELECT MAX(purchase_time) FROM booking WHERE user_id = ?) "
	                + "ORDER BY b.purchase_time DESC, b.booking_id ASC";

	        stmt = conn.prepareStatement(latestTransactionSQL);
	        stmt.setInt(1, user_id);
	        stmt.setInt(2, user_id);

	        rs = stmt.executeQuery();

	        while (rs.next()) {
	            int bookingId = rs.getInt("booking_id");
	            Date bookingDate = rs.getDate("booking_date");
	            String specialRequest = rs.getString("special_request");
	            String mainAddress = rs.getString("main_address");
	            int postalCode = rs.getInt("postal_code");
	            int userId = rs.getInt("user_id");
	            int scheduleId = rs.getInt("schedule_id");
	            int serviceId = rs.getInt("service_id");
	            String serviceName = rs.getString("service_name");
	            double servicePrice = rs.getDouble("service_price");
	            Timestamp purchaseTime = rs.getTimestamp("purchase_time");

	            Booking latestItem = new Booking(bookingId, bookingDate, specialRequest, mainAddress, postalCode, userId,
	                    scheduleId, serviceId, serviceName, servicePrice, purchaseTime);
	            latestResults.add(latestItem);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (rs != null) {
	                rs.close();
	            }
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
	    return latestResults;
	};
	
}