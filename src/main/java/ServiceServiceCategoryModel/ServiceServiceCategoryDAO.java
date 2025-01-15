package ServiceServiceCategoryModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceServiceCategoryDAO {
	public static void deleteServiceServiceCategory(int service_id, int service_category_id) {
		Connection connection = null;
		
		try {
			// config
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			String ps = "DELETE FROM Service_Service_Category WHERE service_id = ? AND service_category_id = ?";
			PreparedStatement statement = connection.prepareStatement(ps);
			statement.setInt(1, service_id);
			statement.setInt(2, service_category_id);
			
			int rowsAffected = statement.executeUpdate();
			
			if (rowsAffected > 0) {
				System.out.println("relationship established. Yipee!");
				statement.close();
				
            } else {
				statement.close();
                throw new SQLException("for some reason, nothing was deleted LOL\nHere is the information received:" +
               "\nserviceId: " + service_id +
               "\nserviceServiceCategories: " + service_category_id);
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
	
	public static void createServiceServiceCategorySingle(int serviceId, int serviceCategoryId) {
		Connection connection = null;
		
		try {
			// config
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			String ps = "INSERT INTO Service_Service_Category (service_id, service_category_id) VALUES (?, ?)";
			PreparedStatement statement = connection.prepareStatement(ps);
			statement.setInt(1, serviceId);
			statement.setInt(2, serviceCategoryId);
			
			int rowsAffected = statement.executeUpdate();
			
			if (rowsAffected > 0) {
				System.out.println("relationship established. Yipee!");
				statement.close();
				
            } else {
				statement.close();
                throw new SQLException("for some reason, nothing was inserted LOL\nHere is the information received:" +
               "\nserviceId: " + serviceId +
               "\nserviceServiceCategories: " + serviceCategoryId);
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
					statement.close();
					
	            } else {
					statement.close();
	                throw new SQLException("for some reason, nothing was inserted LOL\nHere is the information received:" +
	               "\nserviceId: " + serviceId +
	               "\nserviceServiceCategories: " + serviceServiceCategories);
	            }
				
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
	
	public static boolean checkServiceServiceCategoryRelationship(int serviceId, int categoryId) {
        try {
        	// config
			Class.forName("org.postgresql.Driver");
			String query = "SELECT COUNT(*) FROM Service_Service_Category WHERE service_id = ? AND service_category_id = ?";
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			
        	try (
        			Connection connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
        			PreparedStatement statement = connection.prepareStatement(query);
    			) {
                statement.setInt(1, serviceId);
                statement.setInt(2, categoryId);
                
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1) > 0;
                    }
                }
        	}
        	
        } catch (Exception e) {
        	e.printStackTrace();
        }
        
    	return false; // if true was not returned in the previous code, something has gone wrong and it is false
    }
	
	public static List<ServiceServiceCategory> getServiceServiceCategoryByAll() {
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			Connection connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			String sql = "SELECT * FROM Service_Service_Category";
			Statement statement = connection.createStatement();
			
            ResultSet rs = statement.executeQuery(sql);
			
            List<ServiceServiceCategory> categories = new ArrayList<ServiceServiceCategory>();
            
            if (rs == null) {
            	throw new Exception("getServiceServiceCategoryByAll ERROR ERROR READING NOTHING ERROR");
            }
            
            while (rs.next()) {
            	int service_id = rs.getInt("service_id");
            	int service_category_id = rs.getInt("service_category_id");
            	
            	categories.add(new ServiceServiceCategory(service_id, service_category_id));
            }
            
            return categories;
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
