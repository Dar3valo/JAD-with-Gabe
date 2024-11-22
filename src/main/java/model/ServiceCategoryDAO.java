package model;

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
}
