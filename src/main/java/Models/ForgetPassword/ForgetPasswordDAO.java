package Models.ForgetPassword;

import java.security.SecureRandom;
import java.sql.*;
import java.util.Base64;

import Models.User.User;

public class ForgetPasswordDAO {
	public User checkExistingEmail(String email) {
		User existEmail = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			
			String sql = "SELECT email, name FROM Users WHERE email = ?";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);

			rs = stmt.executeQuery();
			
			if(rs.next()) {
				existEmail = new User();
				existEmail.setEmail(rs.getString("email"));
				existEmail.setName(rs.getString("name"));
			}
			
		}catch(Exception e) {
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
		return existEmail;
	}
	
	public User generateResetToken(String email) {
	    User generateToken = null;
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    try {
	        SecureRandom random = new SecureRandom();
	        byte[] tokenBytes = new byte[32];
	        random.nextBytes(tokenBytes);
	        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);

	        // Set expiration time (e.g., 5 minutes from now)
	        long expiryDuration = 5 * 60 * 1000; // 15 minutes in milliseconds
	        Timestamp tokenExpiryTime = new Timestamp(System.currentTimeMillis() + expiryDuration);
	        
	        Class.forName("org.postgresql.Driver");
	        String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
	        conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
	        
	        String sql = "UPDATE Users SET reset_token = ?, token_expiry = ? WHERE email = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, token);
	        stmt.setTimestamp(2, tokenExpiryTime);
	        stmt.setString(3, email);
	        
	        int rowsUpdated = stmt.executeUpdate();
	        if (rowsUpdated > 0) {
	        	
	        	String selectSql = "SELECT name, reset_token, token_expiry FROM Users WHERE email = ?";
	            stmt = conn.prepareStatement(selectSql);
	            stmt.setString(1, email);
	            rs = stmt.executeQuery();
	            
	            if(rs.next()) {
	            	generateToken = new User();
	                generateToken.setName(rs.getString("name")); // Retrieve and set name
	                generateToken.setReset_token(rs.getString("reset_token"));
	                generateToken.setTokenExpiryTime(rs.getTimestamp("token_expiry"));
	            }
	            
	            
//	            generateToken = new User(); // Assuming User class has an appropriate constructor
//	            generateToken.setReset_token(token);
//	            generateToken.setTokenExpiryTime(tokenExpiryTime);
//	            System.out.println("Token generated and saved for email: " + email);
	        }
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // Close resources in reverse order
	        try {
	            if (rs != null)
	                rs.close();
	            if (stmt != null)
	                stmt.close();
	            if (conn != null)
	                conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    return generateToken;
	}
	
	public User checkTokenValidity(String token) {
	    User userWithToken = null;
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    try {
	        Class.forName("org.postgresql.Driver");
	        String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
	        conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

	        String sql = "SELECT email, name, reset_token, token_expiry FROM Users WHERE reset_token = ? AND token_expiry > NOW();";
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, token);

	        rs = stmt.executeQuery();
	        
	        if (rs.next()) {
	            userWithToken = new User();
	            userWithToken.setEmail(rs.getString("email"));
	            userWithToken.setName(rs.getString("name"));
	            userWithToken.setReset_token(rs.getString("reset_token"));
	            userWithToken.setTokenExpiryTime(rs.getTimestamp("token_expiry"));
	            
	         // Clear expired token
	            if (userWithToken.getTokenExpiryTime().getTime() < System.currentTimeMillis()) {
	                clearExpiredToken(conn, userWithToken.getEmail());
	                return null;
	            }
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // Close resources
	        try {
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return userWithToken;
	}
	
	private void clearExpiredToken(Connection conn, String email) {
	    PreparedStatement stmt = null;
	    try {
	        String sql = "UPDATE Users SET reset_token = NULL, token_expiry = NULL WHERE email = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1, email);
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmt != null) stmt.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	}
}
