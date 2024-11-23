package model;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class FeedbackGetDAO {
	public List<Feedback> getFeedbackReviews(){
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
            while(rs.next()) {
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
            
        } catch(Exception e) {
            System.out.println("Error in DAO: " + e.getMessage());
            e.printStackTrace();
        }
        
        return feedbackList;
    }
}
