package RoleModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import UserModel.User;

public class RoleDAO {
	public static List<Role> getRoleByAll() {
		List<Role> roles = new ArrayList<Role>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			
			String sql = "SELECT * FROM Role";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					int role_id = rs.getInt("role_id");
					String name = rs.getString("name");
					String description = rs.getString("description");
					
					roles.add(new Role(role_id, name, description));
				}
				
			} else {
				throw new Exception("No roles found.");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return roles;
	}
	
	public static Role getRoleById(int input_role_id) {
		Role role = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			
			String sql = "SELECT * FROM Role WHERE role_id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, input_role_id);
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				int role_id = rs.getInt("role_id");
				String name = rs.getString("name");
				String description = rs.getString("description");
				
				role = new Role(role_id, name, description);
			
			} else {
				throw new Exception("No role found with role id: " + input_role_id);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			try {
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return role;
	}
	
	public static boolean userRoleAccess(User user, int pageAccessLevel) {
		/* pageAccessLevel legend:
			1: admin
			2: client
			3: public
		*/
		
		boolean hasAccess = false;
		
		try {
			if (pageAccessLevel == 3) {
				hasAccess = true;
				
			} else if (user.getRole_id() == pageAccessLevel || user.getRole_id() < pageAccessLevel) { // is user is admin
				hasAccess = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return hasAccess;
	}
}
