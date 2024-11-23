package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class ServiceServiceCategoryDAO {
	public static void createServiceServiceCategory(int serviceId, int[] serviceServiceCategories) {
	    Connection connection = null;
	    
		try {
			// config
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			// create a row for each service_service_category relationship
			for (int serviceServiceCategory : serviceServiceCategories) {
				// query
				String ps = "INSERT INTO Service_Service_Category (service_id, service_category_id) VALUES (?, ?)";
				PreparedStatement statement = connection.prepareStatement(ps);
				
				statement.setInt(1, serviceId);
				statement.setInt(2, serviceServiceCategory);

				int rowsAffected = statement.executeUpdate();
				
				if (rowsAffected > 0) {
					System.out.println("relationship established. Yipee!");
					
	            } else {
	                throw new SQLException("for some reason, nothing was inserted LOL\nHere is the information received:" +
	               "\nserviceId: " + serviceId +
	               "\nserviceServiceCategories: " + serviceServiceCategories);
	            }
				
				statement.close();
			}
			
		} catch (Exception e) {
			 e.printStackTrace(); 
		} finally {
	        try {
	            if (connection != null) connection.close();
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
