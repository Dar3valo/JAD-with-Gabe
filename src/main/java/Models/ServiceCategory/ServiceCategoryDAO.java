package Models.ServiceCategory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceCategoryDAO {
	public static List<ServiceCategory> getServiceCategoryByAll() {
		List<ServiceCategory> categories = new ArrayList<>();
	    Connection connection = null;
	    Statement statement = null;
	    ResultSet rs = null;
	    
		try {
			// config
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			// query
			String sql = "SELECT * FROM Service_Category";
			
			statement = connection.createStatement();
			rs = statement.executeQuery(sql);
			
			while(rs.next()) {
				int serviceCategoryId = rs.getInt("service_category_id");
				String serviceCategoryName = rs.getString("name");
				String serviceCategoryDescription = rs.getString("description");
				
				categories.add(new ServiceCategory(serviceCategoryId, serviceCategoryName, serviceCategoryDescription));
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
		
		return categories;
	}
	
	public static ServiceCategory getServiceCategoryById(int input_service_category_id) {
		ServiceCategory category = null;
	    Connection connection = null;
	    PreparedStatement statement = null;
	    ResultSet rs = null;
	    
		try {
			// config
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			// query
			String ps = "SELECT * FROM Service_Category WHERE service_category_id = ?";
			
			statement = connection.prepareStatement(ps);
			statement.setInt(1, input_service_category_id);
			rs = statement.executeQuery();
			
			if (rs.next()) {
				int service_category_id = rs.getInt("service_category_id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				
                category = new ServiceCategory(service_category_id, name, description);
                
                return category;
                
            } else {
                throw new SQLException("No category found with ID: " + input_service_category_id);
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
		
		return category;
	}
	
	public static int updateServiceCategoryById(int input_service_category_id, String input_service_category_name, String input_service_category_description) {
		ServiceCategory category = null;
	    Connection connection = null;
	    PreparedStatement statement = null;
	    
		try {			
			// config
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			// query
			String ps = "UPDATE Service_Category SET name = ?, description = ? WHERE service_category_id = ?";
			
			statement = connection.prepareStatement(ps);
			statement.setString(1, input_service_category_name);
			statement.setString(2, input_service_category_description);
			statement.setInt(3, input_service_category_id);
			int rowsAffected = statement.executeUpdate();
			
			if (rowsAffected > 0) {
				return rowsAffected;
                
            } else {
                throw new SQLException("No category found with ID: " + input_service_category_id);
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
	
	public static int deleteServiceCategoryById(int input_service_category_id) {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    
		try {
			// config
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			// query
			String ps = "DELETE FROM Service_Category WHERE service_category_id = ?";
			
			statement = connection.prepareStatement(ps);
			statement.setInt(1, input_service_category_id);
			int rowsAffected = statement.executeUpdate();
			
			if (rowsAffected > 0) {
				return rowsAffected;
                
            } else {
                throw new SQLException("No category found with ID: " + input_service_category_id);
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
	
	public static int createServiceCategory(String input_name, String input_description) {
	    Connection connection = null;
	    PreparedStatement statement = null;
	    
		try {
			// config
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			// query
			String ps = "INSERT INTO Service_Category (name, description) VALUES (?, ?)";
			
			statement = connection.prepareStatement(ps);
			statement.setString(1, input_name);
			statement.setString(2, input_description);
			int rowsAffected = statement.executeUpdate();
			
			System.out.println(input_name);
			System.out.println("\n");
			System.out.println(input_description);
			System.out.println("\n");
			System.out.println(rowsAffected);
			
			if (rowsAffected > 0) {
				return rowsAffected;
                
            } else {
                throw new SQLException("for some reason, nothing was inserted LOL\nHere are the information received:" +
               "\nName: " + input_name +
               "\nDescription: " + input_description);
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
}
