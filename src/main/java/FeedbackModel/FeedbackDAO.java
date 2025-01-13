package FeedbackModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<Feedback> getFeedbackReviews() {
		List<Feedback> feedbackList = new ArrayList<>();

		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";

			// Debug database connection
			System.out.println("Attempting to connect to database...");

			Connection conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			System.out.println("Database connection successful!");

			String sql = """
					SELECT user_id, rating, sources, other_sources, comments, improvements
					FROM feedback
					WHERE rating = 5
					ORDER BY created_at DESC
					LIMIT 3
					""";

			System.out.println("Executing SQL: " + sql);

			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			// Debug result set
			int count = 0;
			while (rs.next()) {
				count++;
				int userId = rs.getInt("user_id");
				int rating = rs.getInt("rating");
				String sources = rs.getString("sources");
				String other_sources = rs.getString("other_sources");
				String comments = rs.getString("comments");
				String improvements = rs.getString("improvements");

				System.out.println("Found feedback for user: " + userId);

				Feedback feedback = new Feedback(userId, rating, sources, other_sources, comments, improvements);
				feedbackList.add(feedback);
			}
			System.out.println("Total feedback records found: " + count);

			rs.close();
			stmt.close();
			conn.close();

		} catch (Exception e) {
			System.out.println("Error in DAO: " + e.getMessage());
			e.printStackTrace();
		}

		return feedbackList;
	}
}
