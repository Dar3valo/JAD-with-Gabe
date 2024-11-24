package model;
import java.sql.*;

public class UserRegisterDAO {
	public UserRegister insertUserInfo (String name, String email, String password, String gender) {
		UserRegister userInfo = null;
		Connection conn = null;
		PreparedStatement stmt = null;
//		int rowsAffected = 0;
		ResultSet rs = null;
		
		
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			
			String hashedPassword = PasswordBcrypt.hashPassword(password);
			
			String sql = "INSERT INTO users (name, email, password, gender) VALUES (?, ?, ?, ?);";
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, hashedPassword);
			stmt.setString(4, gender);
			
			int rowsAffected = stmt.executeUpdate();
			
			if(rowsAffected > 0) {
				userInfo = new UserRegister(name, email, hashedPassword, gender);
			}else {
				System.out.println("User registered unsuccessfully, no rows affected");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return userInfo;
	}

	public boolean isEmailExist(String email) {
		// TODO Auto-generated method stub
		return false;
	}
}
