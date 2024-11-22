package model;

import java.sql.Connection;
import java.sql.DriverManager;
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
}
