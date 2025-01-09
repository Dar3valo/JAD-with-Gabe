package model;

import java.sql.*;

public class UserPfpDAO {
	public User updateUserPfp(int user_id, String profile_photo_url) {
		User userPfp = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			String sql = "UPDATE users SET profile_photo_url = ? WHERE user_id = ?;";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, profile_photo_url);
			stmt.setInt(2, user_id);

			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected > 0) {
				userPfp = new User();
				userPfp.setUser_id(user_id);
				userPfp.setProfile_photo_url(profile_photo_url);
			} else {
				System.out.println("Update Unsuccessful, no rows affected");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close resources
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userPfp;
	}
}
