package Models.Feedback;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FeedbackDAO {
	public Feedback insertUserFeedback(int user_id, int rating, String sources, String other_sources, String comments, String improvements, int service_id) {
		Feedback feedbackInfo = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			String sql = "INSERT INTO feedback (user_id, rating, sources, other_sources, comments, improvements, service_id) VALUES (?, ?, ?, ?, ?, ?, ?);";
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user_id);
			stmt.setInt(2, rating);
			stmt.setString(3, sources);
			stmt.setString(4, other_sources);
			stmt.setString(5, comments);
			stmt.setString(6, improvements);
			stmt.setInt(7, service_id);
			
			int rowsAffected = stmt.executeUpdate();
			
			if(rowsAffected > 0) {
				feedbackInfo = new Feedback(user_id, rating, sources, other_sources, comments, improvements, service_id);
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
	};
	
	public List<Feedback> getAllFeedback() {
		List<Feedback> feedbackList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			String sql = """
					    SELECT f.*, u.name as username, s.name as service_name
					    FROM feedback f
					    LEFT JOIN users u ON f.user_id = u.user_id
					    LEFT JOIN service s ON f.service_id = s.service_id
					    ORDER BY f.created_at DESC
					""";
		
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Feedback feedback = new Feedback();
				feedback.setFeedback_id(rs.getInt("feedback_id"));
			    feedback.setUser_id(rs.getInt("user_id"));
			    feedback.setService_id(rs.getInt("service_id"));
			    feedback.setRating(rs.getInt("rating"));
			    feedback.setComments(rs.getString("comments"));
			    feedback.setCreated_at(rs.getTimestamp("created_at"));
			    feedback.setUsername(rs.getString("username"));
			    feedback.setService_name(rs.getString("service_name"));
			    feedbackList.add(feedback);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return feedbackList;
	};
	
	public List<Feedback> getFeedbackByRating(int rating) {
		List<Feedback> feedbackRatingList = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			
			String sql = """
		            SELECT f.*, u.name as username, s.name as service_name
		            FROM feedback f 
		            LEFT JOIN users u ON f.user_id = u.user_id 
		            LEFT JOIN service s ON f.service_id = s.service_id
		            WHERE f.rating = ? 
		            ORDER BY f.created_at DESC
		        """;
			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, rating);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Feedback feedback = new Feedback();
			    feedback.setFeedback_id(rs.getInt("feedback_id"));
			    feedback.setUser_id(rs.getInt("user_id"));
			    feedback.setService_id(rs.getInt("service_id"));
			    feedback.setRating(rs.getInt("rating"));
			    feedback.setComments(rs.getString("comments"));
			    feedback.setCreated_at(rs.getTimestamp("created_at"));
			    feedback.setUsername(rs.getString("username"));
			    feedback.setService_name(rs.getString("service_name"));
			    feedbackRatingList.add(feedback);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return feedbackRatingList;
	}
	
	public boolean deleteFeedback(int feedbackId) {
	    Connection conn = null;
	    PreparedStatement stmt = null;

	    try {
	        // Load the PostgreSQL JDBC Driver
	        Class.forName("org.postgresql.Driver");

	        // Database connection details
	        String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
	        conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

	        String sql = "DELETE FROM feedback WHERE feedback_id = ?";

	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, feedbackId);
	        int rowsAffected = stmt.executeUpdate();
	        return rowsAffected > 0;

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    return false;
	};

	
}