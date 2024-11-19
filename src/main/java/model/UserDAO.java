package model;
import java.sql.*;

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
			String sql = "SELECT email, password FROM users WHERE user_id = ?";
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, password);
			
			rs = stmt.executeQuery();
			if(rs.next()) {
				user = new User(rs.getString("email"), rs.getString("password"));
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
		return user;
	}
}
