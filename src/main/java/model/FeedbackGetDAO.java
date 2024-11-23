package model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class FeedbackGetDAO {
	public List<Feedback> getFeedbackReviews(){
		List<Feedback> feedbackList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			String sql =  """
	                SELECT user_id, rating, sources, other_sources, comments, improvements
	                FROM feedback
	                WHERE rating = 5
	                ORDER BY created_at DESC
	                LIMIT 3
	            """;
			
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				int userId = rs.getInt("user_id");
				int rating = rs.getInt("rating");
				String sources = rs.getString("sources");
				String other_sources = rs.getString("other_sources");
				String comments = rs.getString("comments");
				String improvements = rs.getString("improvements");
				
				Feedback feedback = new Feedback(userId, rating, sources, other_sources, comments, improvements);
				feedbackList.add(feedback);
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
		return feedbackList;
	}
}
