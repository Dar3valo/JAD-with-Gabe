package model;
import java.util.List;
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
	public static List<Service> getAllServiceInformation(int serviceCategoryId) {
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

			int i = 0;
			
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
}
