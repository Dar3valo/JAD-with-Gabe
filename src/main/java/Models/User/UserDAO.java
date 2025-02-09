package Models.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Utils.PasswordBcrypt;

public class UserDAO {

	public User getUserDetails(String email, String password) {
		User user = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			String sql = "SELECT * FROM Users WHERE email = ?";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);

			rs = stmt.executeQuery();

			if(rs.next()) {
				String hashedPassword = rs.getString("password");

				if(PasswordBcrypt.verifyPassword(password, hashedPassword)) {
					int user_id = rs.getInt("user_id");
					String user_email = rs.getString("email");
					char gender = rs.getString("gender").charAt(0);
					String name = rs.getString("name");
					String profile_photo_url = rs.getString("profile_photo_url");
					int role_id = rs.getInt("role_id");

					user = new User(user_id, hashedPassword, user_email, gender, name, profile_photo_url, role_id);
				} else {
					System.out.println("Invalid Password");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

	public static List<User> getUserByAll() {
		List<User> users = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			String sql = "SELECT * FROM Users";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);

			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					int user_id = rs.getInt("user_id");
					String user_password = rs.getString("password");
					String user_email = rs.getString("email");
					char gender = rs.getString("gender").charAt(0);
					String name = rs.getString("name");
					String profile_photo_url = rs.getString("profile_photo_url");
					int role_id = rs.getInt("role_id");

					users.add(new User(user_id, user_password, user_email, gender, name, profile_photo_url, role_id));
				}

			} else {
				throw new Exception("No users found.");
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return users;
	}

	public static int updateUserPasswordById(int user_id, String password) {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			// config
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			// query
			String ps = "UPDATE Users SET password = ? WHERE user_id = ?";

			// password is hashed

			statement = connection.prepareStatement(ps);
			statement.setString(1, password);
			statement.setInt(2, user_id);
			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				return rowsAffected;

			} else {
				throw new SQLException("(probably) wrong input or no user found with ID: " + user_id);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return 0;
	}

	public static int updateUserById(int user_id, String email, char gender, String name, String profile_photo_url,
			int role_id) {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			// config
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			// query
			String ps = "UPDATE Users SET email = ?, gender = ?, name = ?, role_id = ? WHERE user_id = ?";

			statement = connection.prepareStatement(ps);
			statement.setString(1, email);
			statement.setString(2, String.valueOf(gender));
			statement.setString(3, name);
			// statement.setString(4, profile_photo_url);
			statement.setInt(4, role_id);
			statement.setInt(5, user_id);
			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				return rowsAffected;

			} else {
				throw new SQLException("(probably) wrong input or no user found with ID: " + user_id);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return 0;
	}

	public static User getUserById(int user_id) {
		User user = null;
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			// config
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			// query
			String ps = "SELECT * FROM Users WHERE user_id = ?";

			statement = connection.prepareStatement(ps);
			statement.setInt(1, user_id);

			ResultSet rs = statement.executeQuery();

			if (rs.next()) {
				int output_user_id = rs.getInt("user_id");
				String user_password = rs.getString("password");
				String user_email = rs.getString("email");
				char gender = rs.getString("gender").charAt(0);
				String name = rs.getString("name");
				String profile_photo_url = rs.getString("profile_photo_url");
				int role_id = rs.getInt("role_id");

				user = new User(output_user_id, user_password, user_email, gender, name, profile_photo_url, role_id);

			} else {
				throw new SQLException("(probably) wrong input or no user found with user id: " + user_id);

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return user;
	}

	public static void deleteUserById(int user_id) {
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			// config
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			// query
			String ps = "DELETE FROM Users WHERE user_id = ?";

			statement = connection.prepareStatement(ps);
			statement.setInt(1, user_id);

			int rowsAffected = statement.executeUpdate();

			if (rowsAffected > 0) {
				System.out.println("User successfully deleted");

			} else {
				throw new SQLException("(probably) wrong input or no user found with user id: " + user_id);

			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static List<User> getUserByRoleId(int role_id) {
		List<User> users = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			// config
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			connection = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			// query
			String ps = "SELECT * FROM Users WHERE role_id = ?";

			statement = connection.prepareStatement(ps);
			statement.setInt(1, role_id);

			ResultSet rs = statement.executeQuery();

			if (rs.isBeforeFirst()) {
				while (rs.next()) {
					int user_id = rs.getInt("user_id");
					String user_password = rs.getString("password");
					String user_email = rs.getString("email");
					char gender = rs.getString("gender").charAt(0);
					String name = rs.getString("name");
					String profile_photo_url = rs.getString("profile_photo_url");
					int user_role_id = rs.getInt("role_id");

					users.add(new User(user_id, user_password, user_email, gender, name, profile_photo_url, user_role_id));
				}
			} else {
				throw new SQLException("(probably) wrong input or no user found with role id: " + role_id);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return users;
	}

	public User insertUserInfo (String name, String email, String password, char gender) {
		User userInfo = null;
		Connection conn = null;
		PreparedStatement stmt = null;
//		int rowsAffected = 0;
		ResultSet rs = null;


		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			String hashedPassword = PasswordBcrypt.hashPassword(password);

			String sql = "INSERT INTO users (name, email, password, gender) VALUES (?, ?, ?, ?);";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, hashedPassword);
			stmt.setString(4, String.valueOf(gender));

			int rowsAffected = stmt.executeUpdate();

			if(rowsAffected > 0) {
				userInfo = new User(name, email, hashedPassword, gender);
			}else {
				System.out.println("User registered unsuccessfully, no rows affected");
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return userInfo;
	}

	 public boolean isEmailExist(String email) {
	        boolean exists = false;
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;

	        try {
	            Class.forName("org.postgresql.Driver");
	            String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
	            conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

	            String sql = "SELECT 1 FROM users WHERE email = ?";

	            stmt = conn.prepareStatement(sql);
	            stmt.setString(1, email);

	            rs = stmt.executeQuery();
	            exists = rs.next();

	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            // Close resources
	            try {
	                if (rs != null) {
						rs.close();
					}
	                if (stmt != null) {
						stmt.close();
					}
	                if (conn != null) {
						conn.close();
					}
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        return exists;
	    }

	public User updateUserPfp(int user_id, String profile_photo_url) {
		User userPfp = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

			String sql = "UPDATE users SET profile_photo_url = ? WHERE user_id = ?;";

			stmt = conn.prepareStatement(sql);
			stmt.setString(1, profile_photo_url);
			stmt.setInt(2, user_id);

			int rowsAffected = stmt.executeUpdate();

			if (rowsAffected > 0) {
				userPfp = new User();
				userPfp.setUser_id(user_id);
				userPfp.setProfile_photo_url(profile_photo_url);
			} else {
				System.out.println("Update Unsuccessful, no rows affected");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Close resources
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userPfp;
	}

	public boolean updatePassword(String email, String hashedPassword) {
        boolean isUpdated = false;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("org.postgresql.Driver");
            String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
            conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");

            String sql = "UPDATE users SET password = ? WHERE email = ?;";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, hashedPassword);
            stmt.setString(2, email);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                isUpdated = true;
            } else {
                System.out.println("Update Unsuccessful, no rows affected");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (stmt != null) {
					stmt.close();
				}
                if (conn != null) {
					conn.close();
				}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isUpdated;
    }

	public static boolean verifyPassword(int user_id, String input_password) {
	    Connection conn = null;
	    PreparedStatement stmt = null;
	    
	    try {
	        Class.forName("org.postgresql.Driver");
	        String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
	        conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
	        
	        String sql = "SELECT password FROM users WHERE user_id = ?";
	        
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1, user_id);
	        
	        try (ResultSet rs = stmt.executeQuery()) {
	            if (rs.next()) {
	                String hashedPassword = rs.getString("password");
	                return PasswordBcrypt.verifyPassword(input_password, hashedPassword);
	            } else {
	                System.out.println("User not found");
	            }
	        }
	        
	    } catch (ClassNotFoundException e) {
	        System.err.println("PostgreSQL driver not found: " + e.getMessage());
	    } catch (SQLException e) {
	        System.err.println("Database error occurred: " + e.getMessage());
	    } finally {
	        try {
	            if (stmt != null) {
	                stmt.close();
	            }
	            if (conn != null) {
	                conn.close();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    return false;
	}
}
