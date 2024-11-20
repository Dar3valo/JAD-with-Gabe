package model;
import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class ServiceDAO {
	public static List<Service> getAllServices(){
		List<Service> services = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			String sql = "SELECT service_id, name FROM Service";
			
			stmt = conn.prepareStatement(sql);
			
			rs = stmt.executeQuery();

			while(rs.next()) {
				int serviceId = rs.getInt("service_id");
				String serviceName = rs.getString("name");
				
				services.add(new Service(serviceId, serviceName));
			}
		}catch(Exception e) {
			 e.printStackTrace(); 
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return services;
	}
}
