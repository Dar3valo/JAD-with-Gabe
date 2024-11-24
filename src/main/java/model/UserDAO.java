package model;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

	public User getUserDetails(String email, String password) {
		User user = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			String sql = "SELECT * FROM Users WHERE email = ? AND password = ?";
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, password);
			
			rs = stmt.executeQuery();
			if(rs.next()) {
				int user_id = rs.getInt("user_id");
				String user_password = rs.getString("password");
				String user_email = rs.getString("email");
				char gender = (char) rs.getString("gender").charAt(0);
				String name = rs.getString("name");
				String profile_photo_url = rs.getString("profile_photo_url");
				int role_id = rs.getInt("role_id");
				
				
				user = new User(user_id, user_password, user_email, gender, name, profile_photo_url, role_id);
			}
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}
	
	public static List<User> getUserByAll() {
		List<User> users = new ArrayList<User>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			
			String sql = "SELECT * FROM Users";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					int user_id = rs.getInt("user_id");
					String user_password = rs.getString("password");
					String user_email = rs.getString("email");
					char gender = (char) rs.getString("gender").charAt(0);
					String name = rs.getString("name");
					String profile_photo_url = rs.getString("profile_photo_url");
					int role_id = rs.getInt("role_id");
					
					users.add(new User(user_id, user_password, user_email, gender, name, profile_photo_url, role_id));
				}
				
			} else {
				throw new Exception("No users found.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return users;
	}
}
