package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FeedbackDAO {
	public Feedback insertUserFeedback(int user_id, int rating, String sources, String other_sources, String comments, String improvements) {
		Feedback feedbackInfo = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			String sql = "INSERT INTO feedback (user_id, rating, sources, other_sources, comments, improvements) VALUES (?, ?, ?, ?, ?, ?);";
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user_id);
			stmt.setInt(2, rating);
			stmt.setString(3, sources);
			stmt.setString(4, other_sources);
			stmt.setString(5, comments);
			stmt.setString(6, improvements);
			
			int rowsAffected = stmt.executeUpdate();
			
			if(rowsAffected > 0) {
				feedbackInfo = new Feedback(user_id, rating, sources, other_sources, comments, improvements);
			}else {
				System.out.println("Feedback failed, no rows affected");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
			conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return feedbackInfo;
	}
}
