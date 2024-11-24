package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;

public class BookingDAO {
	public Booking transferCartData(int user_id) {

		Booking transferInfo = null;
		Connection conn = null;
		PreparedStatement stmt = null;

		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			String sql = """
					     INSERT INTO Booking (
					        booking_date, special_request, main_address,
					        postal_code, user_id, schedule_id, service_id
					    )
					    SELECT
					        booking_date, special_request, main_address,
					        postal_code, user_id, schedule_id, service_id
					    FROM Cart_Item
					    WHERE user_id = ?
					    RETURNING booking_id, booking_date, special_request, main_address,
					              postal_code, user_id, schedule_id, service_id
					""";

			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, user_id);

			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				transferInfo = new Booking(
						rs.getInt("booking_id"),
	                    rs.getDate("booking_date"),
	                    rs.getString("special_request"),
	                    rs.getString("main_address"),
	                    rs.getInt("postal_code"),
	                    rs.getInt("user_id"),
	                    rs.getInt("schedule_id"),
	                    rs.getInt("service_id")
				);
				deleteFromCart(user_id);
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return transferInfo;
	}
	
	private void deleteFromCart(int user_id) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			String deleteSQL = "DELETE FROM Cart_Item WHERE user_id = ?";
			
			stmt = conn.prepareStatement(deleteSQL);
			stmt.setInt(1, user_id);
			stmt.executeUpdate();
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			// Close resources
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
