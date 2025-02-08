package Models.Status;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StatusDAO {
	public Status getStatus(int statusId) {
	    Status status = null;
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    ResultSet rs = null;
	    
	    try {
	        // Load the PostgreSQL driver
	        Class.forName("org.postgresql.Driver");

	        // Create a connection using the PostgreSQL database URL
	        String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
	        conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
	        
	        // Define the SQL query
	        String sql = "SELECT * FROM Status WHERE status_id = ?";

	        // Prepare the statement with the SQL query
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, statusId);

	        // Execute the query
	        rs = stmt.executeQuery();

	        // Process the result set
	        if (rs.next()) {
	            status = new Status();
	            status.setStatus_id(rs.getInt("status_id"));
	            status.setName(rs.getString("name"));
	            status.setDescription(rs.getString("description"));
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    } finally {
	        // Close resources explicitly
	        try {
	            if (rs != null) rs.close();
	            if (stmt != null) stmt.close();
	            if (conn != null) conn.close();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	    return status;
	}
	
	public List<Status> getAllStatuses() {
        List<Status> statuses = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Class.forName("org.postgresql.Driver");
            String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
            conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

            String sql = "SELECT * FROM Status ORDER BY status_id";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Status status = new Status();
                status.setStatus_id(rs.getInt("status_id"));
                status.setName(rs.getString("name"));
                status.setDescription(rs.getString("description"));
                statuses.add(status);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statuses;
    }

}
