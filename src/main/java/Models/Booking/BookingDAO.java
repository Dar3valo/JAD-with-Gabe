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
					        postal_code, user_id, schedule_id, service_id, status_id
					    )
					    SELECT
					        booking_date, special_request, main_address,
					        postal_code, user_id, schedule_id, service_id, ? 
					    FROM Cart_Item
					    WHERE user_id = ?
					    RETURNING booking_id, booking_date, special_request, main_address,
					              postal_code, user_id, schedule_id, service_id, status_id
					""";

			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 1);
			stmt.setInt(2, user_id);

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
	                    rs.getInt("service_id"),
	                    rs.getInt("status_id") 
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
	                + "b.user_id, b.schedule_id, b.service_id, s.name AS service_name, s.price AS service_price, b.creation_date, b.status_id "
	                + "FROM booking b "
	                + "JOIN Service s ON b.service_id = s.service_id "
	                + "WHERE b.user_id = ? AND b.creation_date = (SELECT MAX(creation_date) FROM booking WHERE user_id = ?) "
	                + "ORDER BY b.creation_date DESC, b.booking_id ASC";

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
	            Timestamp creationDate = rs.getTimestamp("creation_date");
	            int statusId = rs.getInt("status_id");

	            Booking latestItem = new Booking(bookingId, bookingDate, specialRequest, mainAddress, postalCode, userId,
	                    scheduleId, serviceId, serviceName, servicePrice, creationDate, statusId);
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
	
	public List<Booking> getBookingDetailsAdmin(int pageNumber, int pageSize){
		List<Booking> bookingList = new ArrayList<>();
		Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    try {
	    	Class.forName("org.postgresql.Driver");
	        String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
	        conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
	        
	        String sql = """
	        		SELECT 
						u.name,
						u.email,
					    b.booking_date,
					    CONCAT(s.start_time, ' - ', s.end_time) AS booking_period, 
					    srv.name AS service_name, 
					    srv.price AS service_price
					FROM 
					    booking b
					JOIN 
					    service srv 
					ON 
					    b.service_id = srv.service_id
					JOIN 
					    users u 
					ON 
					    b.user_id = u.user_id
					JOIN 
					    schedule s 
					ON 
					    b.schedule_id = s.schedule_id
					ORDER BY 
					    b.booking_date LIMIT ? OFFSET ?;
	        		""";
	        //Remove LIMIT ? and OFFSET ? if no work
	        stmt = conn.prepareStatement(sql);
	        //remove these 2 if no work
	        stmt.setInt(1, pageSize);
	        stmt.setInt(2, (pageNumber - 1) * pageSize);
	        rs = stmt.executeQuery();
	        
	        while(rs.next()) {
	        	Booking booking = new Booking();
	        	booking.setUsername(rs.getString("name")); // User name
	            booking.setUserEmail(rs.getString("email")); // User email
	            booking.setBooking_date(rs.getDate("booking_date")); // Booking date
	            booking.setBookingPeriod(rs.getString("booking_period")); // Booking period (start_time - end_time)
	            booking.setServiceName(rs.getString("service_name")); // Service name
	            booking.setServicePrice(rs.getDouble("service_price")); // Service price
	            
	            bookingList.add(booking);
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
	    return bookingList;
	};
	
	public int getTotalBookings() {
        int totalRecords = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("org.postgresql.Driver");
            String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
            conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
            
            String sql = "SELECT COUNT(*) AS total FROM booking";
            stmt = conn.prepareStatement(sql);
            
            rs = stmt.executeQuery();
            
            if(rs.next()) {
                totalRecords = rs.getInt("total");
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
        return totalRecords;
    };
    
    public List<Booking> getFilteredBookings(String filterType, String filterValue, int pageNumber, int pageSize) {
    	List<Booking> bookings = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
        	Class.forName("org.postgresql.Driver");
            String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
            conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
            
            String sql = "SELECT u.name, u.email, b.booking_date, CONCAT(s.start_time, ' - ', s.end_time) AS booking_period, srv.name AS service_name, srv.price AS service_price "
                    + "FROM booking b "
                    + "JOIN service srv ON b.service_id = srv.service_id "
                    + "JOIN users u ON b.user_id = u.user_id "
                    + "JOIN schedule s ON b.schedule_id = s.schedule_id ";

            sql += "ORDER BY b.booking_date LIMIT ? OFFSET ?";

            stmt = conn.prepareStatement(sql);
            if ("date".equals(filterType)) {
                stmt.setDate(1, Date.valueOf(filterValue));  // Convert String to Date
            } else if ("month".equals(filterType)) {
                stmt.setInt(1, Integer.parseInt(filterValue));  // Convert String to Integer
            }
            stmt.setInt(2, pageSize);
            stmt.setInt(3, (pageNumber - 1) * pageSize);

            rs = stmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();
                booking.setUsername(rs.getString("name"));
                booking.setUserEmail(rs.getString("email"));
                booking.setBooking_date(rs.getDate("booking_date"));
                booking.setBookingPeriod(rs.getString("booking_period"));
                booking.setServiceName(rs.getString("service_name"));
                booking.setServicePrice(rs.getDouble("service_price"));

                bookings.add(booking);
        }
        }catch(Exception e) {
        	e.printStackTrace();
        }finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return bookings;
    }
	
}