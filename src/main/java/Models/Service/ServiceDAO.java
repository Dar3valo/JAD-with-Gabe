package Models.Service;
import java.util.List;

import Models.ServiceCategory.ServiceCategory;
import Models.ServiceReport.ServiceReport;

import java.util.ArrayList;
import java.sql.*;

public class ServiceDAO {
	// get all service (name, id)
	public static List<Service> getAllServices(){
		List<Service> services = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			String sql = "SELECT service_id, name FROM Service";
			
			stmt = conn.prepareStatement(sql);
			
			rs = stmt.executeQuery();

			while(rs.next()) {
				int serviceId = rs.getInt("service_id");
				String serviceName = rs.getString("name");
				
				services.add(new Service(serviceId, serviceName));
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
		return services;
	}
	
	// get all service (all information)
	public static List<Service> getServiceInformationByCategory(int serviceCategoryId) {
		List<Service> services = new ArrayList<>();
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet rs = null;
	    
		try {
			// config
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			// query
			String ps = "SELECT * FROM select_service_by_category(?)";
			
			statement = connection.prepareStatement(ps);
			statement.setInt(1, serviceCategoryId);
			rs = statement.executeQuery();
			
			while(rs.next()) {
				int serviceId = rs.getInt("service_id");
				String serviceName = rs.getString("name");
				String serviceDescription = rs.getString("description");
				double servicePrice = rs.getDouble("price");
				String serviceImage = rs.getString("service_photo_url");
				
				services.add(new Service(serviceId, serviceName, serviceDescription, servicePrice, serviceImage));
			}
			
			connection.close();
			
		} catch (Exception e) {
			 e.printStackTrace(); 
		} finally {
	        try {
	            if (rs != null) rs.close();
	            if (statement != null) statement.close();
	            if (connection != null) connection.close();
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		
		return services;
	}
	
	
	 // Get all services
    public static List<Service> getServiceInformationByAll() {
        List<Service> services = new ArrayList<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            // Database configuration
            Class.forName("org.postgresql.Driver");
            String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
            connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

            // SQL query
            String sql = "SELECT * FROM Service";

            statement = connection.createStatement();
            rs = statement.executeQuery(sql);

            // Iterate through results
            while (rs.next()) {
                int serviceId = rs.getInt("service_id");
                String serviceName = rs.getString("name");
                String serviceDescription = rs.getString("description");
                double servicePrice = rs.getDouble("price");
                String serviceImage = rs.getString("service_photo_url");

                services.add(new Service(serviceId, serviceName, serviceDescription, servicePrice, serviceImage));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return services;
    }
	
    // name description price url
	public static int createService(String input_name, String input_description, double input_price, String input_service_photo_url) {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet generatedKeys = null;
	    
		try {
			// config
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			// query
			String ps = "INSERT INTO Service (name, description, price, service_photo_url) VALUES (?, ?, ?, ?)";
			
			statement = connection.prepareStatement(ps, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, input_name);
			statement.setString(2, input_description);
			statement.setDouble(3, input_price);
			statement.setString(4, input_service_photo_url);
			int rowsAffected = statement.executeUpdate();
			
			if (rowsAffected > 0) {
				generatedKeys = statement.getGeneratedKeys();
				
		        if (generatedKeys.next()) {
		            int serviceId = generatedKeys.getInt(1);
		            System.out.println("New Service ID: " + serviceId);
		            
		            return serviceId;
		            
		        } else {
		            throw new SQLException("Insertion succeeded but no ID was returned.");
		            
		        }
                
            } else {
                throw new SQLException("for some reason, nothing was inserted LOL\nHere is the information received:" +
               "\nName: " + input_name +
               "\nDescription: " + input_description +
               "\nPrice: " + input_price+
               "\nService_photo_url: " + input_service_photo_url);
            }
			
		} catch (Exception e) {
			 e.printStackTrace(); 
		} finally {
	        try {
	            if (statement != null) statement.close();
	            if (connection != null) connection.close();
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		
		return 0;
	}
	
	public static int updateServiceById(int input_service_id, String input_name, String input_description, double input_price, String input_service_photo_url) {
		ServiceCategory category = null;
	    Connection connection = null;
	    PreparedStatement statement = null;
	    
		try {			
			// config
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			// query
			String ps = "UPDATE Service SET name = ?, description = ?, price = ?, service_photo_url = ? WHERE service_id = ?";
			
			statement = connection.prepareStatement(ps);
			statement.setString(1, input_name);
			statement.setString(2, input_description);
			statement.setDouble(3, input_price);
			statement.setString(4, input_service_photo_url);
			statement.setInt(5, input_service_id);
			int rowsAffected = statement.executeUpdate();
			
			if (rowsAffected > 0) {
				return rowsAffected;
                
            } else {
                throw new SQLException("(probably) No category found with ID: " + input_service_id);
            }
			
		} catch (Exception e) {
			 e.printStackTrace(); 
		} finally {
	        try {
	            if (statement != null) statement.close();
	            if (connection != null) connection.close();
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		
		return 0;
	}
	
	public static int deleteServiceById(int input_service_id) {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    
		try {			
			// config
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			// query
			String ps = "DELETE FROM Service WHERE service_id = ?";
			
			statement = connection.prepareStatement(ps);
			statement.setInt(1, input_service_id);
			int rowsAffected = statement.executeUpdate();
			
			if (rowsAffected > 0) {
				return rowsAffected;
                
            } else {
                throw new SQLException("(probably) No category found with ID: " + input_service_id);
            }
			
		} catch (Exception e) {
			 e.printStackTrace(); 
		} finally {
	        try {
	            if (statement != null) statement.close();
	            if (connection != null) connection.close();
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		
		return 0;
	};
	
	public List<ServiceReport> getServiceReport() {
		List<ServiceReport> serviceReports = new ArrayList<>();
		Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        
        try {
        	Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			
			String sql = "SELECT " + "s.service_id, " + "s.name, " + "SUM(s.price) AS totalRevenue, "
					+ "COUNT(b.booking_id) AS totalBookings " + "FROM Service s "
					+ "JOIN Booking b ON s.service_id = b.service_id " + "GROUP BY s.service_id, s.name "
					+ "ORDER BY s.service_id ASC;";

			statement = connection.prepareStatement(sql);
			rs = statement.executeQuery();
			
			while (rs.next()) {
                int serviceId = rs.getInt("service_id");
                String serviceName = rs.getString("name");
                double totalRevenue = rs.getDouble("totalRevenue");
                int totalBookings = rs.getInt("totalBookings");

                serviceReports.add(new ServiceReport(serviceId, serviceName, totalRevenue, totalBookings));
			}
        }catch(Exception e) {
        	e.printStackTrace();
        }finally {
	        try {
	            if (statement != null) statement.close();
	            if (connection != null) connection.close();
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
        return serviceReports;
	}
}
