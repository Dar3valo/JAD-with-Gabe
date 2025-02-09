package Models.Booking;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Models.User.User;

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

            String sql = "SELECT u.name, u.email, b.booking_date, CONCAT(s.start_time, ' - ', s.end_time) AS booking_period, "
                       + "srv.name AS service_name, srv.price AS service_price "
                       + "FROM booking b "
                       + "JOIN service srv ON b.service_id = srv.service_id "
                       + "JOIN users u ON b.user_id = u.user_id "
                       + "JOIN schedule s ON b.schedule_id = s.schedule_id ";

            // Add filtering logic to SQL
            if ("date".equals(filterType)) {
                sql += "WHERE b.booking_date = ? ";
            } else if ("month".equals(filterType)) {
                sql += "WHERE EXTRACT(MONTH FROM b.booking_date) = ? AND EXTRACT(YEAR FROM b.booking_date) = ? ";
            }

            // Add pagination and sorting
            sql += "ORDER BY b.booking_date LIMIT ? OFFSET ?";

            stmt = conn.prepareStatement(sql);

            // Set parameters based on filter type
            if ("date".equals(filterType)) {
                stmt.setDate(1, Date.valueOf(filterValue)); // Convert String to Date
                stmt.setInt(2, pageSize);
                stmt.setInt(3, (pageNumber - 1) * pageSize);
            } else if ("month".equals(filterType)) {
                int year = LocalDate.now().getYear();  // Current year
                stmt.setInt(1, Integer.parseInt(filterValue));  // Convert month to Integer
                stmt.setInt(2, year);  // Set current year
                stmt.setInt(3, pageSize);
                stmt.setInt(4, (pageNumber - 1) * pageSize);
            }

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
        return bookings;
    };
    
    public int getTotalFilteredBookings(String filterType, String filterValue) {
        int totalFilteredRecords = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("org.postgresql.Driver");
            String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
            conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

            String sql = "SELECT COUNT(*) FROM booking b " +
                         "JOIN service srv ON b.service_id = srv.service_id " +
                         "JOIN users u ON b.user_id = u.user_id " +
                         "JOIN schedule s ON b.schedule_id = s.schedule_id ";
            
            // Add WHERE clause based on filter type
            if ("date".equals(filterType)) {
                sql += "WHERE b.booking_date = ?";
            } else if ("month".equals(filterType)) {
                sql += "WHERE EXTRACT(MONTH FROM b.booking_date) = ?";
            } else {
                throw new IllegalArgumentException("Invalid filter type: " + filterType);
            }

            stmt = conn.prepareStatement(sql);

            if ("date".equals(filterType)) {
                // For date filter, ensure filterValue is a valid date string (e.g., "2025-02-06")
                stmt.setDate(1, Date.valueOf(filterValue));  // Convert string to Date
            } else if ("month".equals(filterType)) {
                // For month filter, filterValue should be an integer (e.g., "5" for May)
                stmt.setInt(1, Integer.parseInt(filterValue));  // Convert string to integer
            }
            
            rs = stmt.executeQuery();
            
            if (rs.next()) {
            	totalFilteredRecords = rs.getInt(1);
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
        return totalFilteredRecords;
    }

    public List<Booking> getTopCustomersByServicePrice(int pageNumber, int pageSize) {
        List<Booking> topCustomers = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("org.postgresql.Driver");
            String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
            conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
            
            String sql = "SELECT u.user_id, u.name, SUM(s.price) AS total_service_price " +
                         "FROM booking b " +
                         "JOIN users u ON b.user_id = u.user_id " +
                         "JOIN service s ON b.service_id = s.service_id " +
                         "GROUP BY u.user_id, u.name " +
                         "ORDER BY total_service_price DESC " +
                         "LIMIT ? OFFSET ?";
            
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pageSize);  // Limit to 'pageSize' number of records per page
            stmt.setInt(2, (pageNumber - 1) * pageSize);  // Offset to skip records for pagination
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Booking booking = new Booking();
                booking.setUser_id(rs.getInt("user_id"));
                booking.setUsername(rs.getString("name"));
                booking.setServicePrice(rs.getDouble("total_service_price")); // Use servicePrice for aggregated total
                topCustomers.add(booking);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return topCustomers;
    }

    
    public int getTotalTopCustomersByServicePrice() {
        int totalRecords = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
        	Class.forName("org.postgresql.Driver");
            String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
            conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
            
            String query = "SELECT COUNT(DISTINCT user_id) FROM booking";
            
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                totalRecords = rs.getInt(1);
            }
        	
        }catch (Exception e) {
            e.printStackTrace();
        }
        return totalRecords;
    };
    
 // Method to filter bookings by service
    public List<Booking> getFilteredBookingsByService(String serviceName, int pageNumber, int pageSize) {
        List<Booking> bookings = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.postgresql.Driver");
            String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
            conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

            String sql = "SELECT u.name, u.email, b.booking_date, CONCAT(s.start_time, ' - ', s.end_time) AS booking_period, "
                       + "srv.name AS service_name, srv.price AS service_price "
                       + "FROM booking b "
                       + "JOIN service srv ON b.service_id = srv.service_id "
                       + "JOIN users u ON b.user_id = u.user_id "
                       + "JOIN schedule s ON b.schedule_id = s.schedule_id "
                       + "WHERE srv.name = ? "
                       + "ORDER BY b.booking_date LIMIT ? OFFSET ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, serviceName);  // Set the service name filter
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
        return bookings;
    }

    // Method to get total records for service filter
    public int getTotalFilteredBookingsByService(String serviceName) {
        int totalFilteredRecords = 0;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.postgresql.Driver");
            String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
            conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

            String sql = "SELECT COUNT(*) FROM booking b "
                       + "JOIN service srv ON b.service_id = srv.service_id "
                       + "JOIN users u ON b.user_id = u.user_id "
                       + "JOIN schedule s ON b.schedule_id = s.schedule_id "
                       + "WHERE srv.name = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, serviceName);  // Set the service name filter
            rs = stmt.executeQuery();

            if (rs.next()) {
                totalFilteredRecords = rs.getInt(1);
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
        return totalFilteredRecords;
    }
    
    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookingsByUser = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Load the PostgreSQL driver
            Class.forName("org.postgresql.Driver");
            String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
            conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

            // SQL query to include 'start_time' and 'end_time' from the 'schedule' table
            String sql = "SELECT b.*, s.name as service_name, " +
                    "CONCAT(TO_CHAR(sch.start_time, 'HH24:MI'), ' - ', " +
                    "TO_CHAR(sch.end_time, 'HH24:MI')) as booking_period " +
                    "FROM Booking b " +
                    "LEFT JOIN Service s ON b.service_id = s.service_id " +
                    "LEFT JOIN Schedule sch ON b.schedule_id = sch.schedule_id " +
                    "WHERE b.user_id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);  // Set the user_id filter
            rs = stmt.executeQuery();

            while (rs.next()) {
                Booking booking = new Booking();

                // Set existing properties
                booking.setBooking_id(rs.getInt("booking_id"));
                booking.setBooking_date(rs.getDate("booking_date"));
                booking.setSpecial_request(rs.getString("special_request"));
                booking.setMain_address(rs.getString("main_address"));
                booking.setPostal_code(rs.getInt("postal_code"));
                booking.setUser_id(rs.getInt("user_id"));
                booking.setSchedule_id(rs.getInt("schedule_id"));
                booking.setService_id(rs.getInt("service_id"));
                booking.setCreation_date(rs.getTimestamp("creation_date"));
                booking.setStatus_id(rs.getInt("status_id"));
                booking.setServiceName(rs.getString("service_name"));
                booking.setBookingPeriod(rs.getString("booking_period"));

                bookingsByUser.add(booking);
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

        return bookingsByUser;
    }

    
    public boolean updateBookingStatus(int bookingId, int statusId) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            // Load the JDBC driver
            Class.forName("org.postgresql.Driver");
            
            // Establish the connection
            String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
            conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
            
            // SQL query for updating the booking status
            String sql = "UPDATE Booking SET status_id = ? WHERE booking_id = ?";
            
            // Prepare the statement
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, statusId);
            stmt.setInt(2, bookingId);
            
            // Execute the update and return true if successful
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
            
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            return false;
        } finally {
            // Close resources in the finally block
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Booking getBookingDetailsByBookingId(int bookingId) {
    	Booking bookingDetail = null;
    	Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
    	try {
    		Class.forName("org.postgresql.Driver");
            String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
            conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
    		String sql = "SELECT " +
                    "b.booking_id, " +
                    "b.booking_date, " +
                    "b.main_address, " +
                    "s.name AS service_name, " +
                    "st.name AS status_name, " +
                    "CONCAT(TO_CHAR(sch.start_time, 'HH24:MI'), ' - ', TO_CHAR(sch.end_time, 'HH24:MI')) AS booking_period " +
                    "FROM booking b " +
                    "LEFT JOIN service s ON b.service_id = s.service_id " +
                    "LEFT JOIN schedule sch ON b.schedule_id = sch.schedule_id " +
                    "LEFT JOIN status st ON b.status_id = st.status_id " +
                    "WHERE b.booking_id = ?";
    		
    		stmt = conn.prepareStatement(sql);
            stmt.setInt(1, bookingId);  // Set the user_id filter
            rs = stmt.executeQuery();
            
            if (rs.next()) { // Use `if` since we're fetching a single booking
            	bookingDetail = new Booking();

                // Populate the Booking object with data from the result set
                bookingDetail.setBooking_id(rs.getInt("booking_id"));
                bookingDetail.setBooking_date(rs.getDate("booking_date"));
                bookingDetail.setMain_address(rs.getString("main_address"));

                // Set service name
                bookingDetail.setServiceName(rs.getString("service_name"));

                // Set booking period (already concatenated in SQL)
                bookingDetail.setBookingPeriod(rs.getString("booking_period"));

                // Set status name
                bookingDetail.setStatusName(rs.getString("status_name"));
            }
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	return bookingDetail;
    }
    
}