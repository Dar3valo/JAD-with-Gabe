package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateCartItemsDAO {

    public boolean updateCartItem(int cart_item_id, Date booking_date, String special_request, String main_address,
                                  int postal_code, int user_id, int schedule_id, int service_id, 
                                  String serviceName, double servicePrice) {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean isUpdated = false;

        try {
            Class.forName("org.postgresql.Driver");
            String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
            conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
            String sql = "UPDATE cart_item SET booking_date = ?, special_request = ?, main_address = ?, "
                    + "postal_code = ?, user_id = ?, schedule_id = ?, service_id = ?, service_name = ?, "
                    + "service_price = ? WHERE cart_item_id = ?;";

            stmt = conn.prepareStatement(sql);
            stmt.setDate(1, booking_date);
            stmt.setString(2, special_request);
            stmt.setString(3, main_address);
            stmt.setInt(4, postal_code);
            stmt.setInt(5, user_id);
            stmt.setInt(6, schedule_id);
            stmt.setInt(7, service_id);
            stmt.setString(8, serviceName);
            stmt.setDouble(9, servicePrice);
            stmt.setInt(10, cart_item_id);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                isUpdated = true;
            } else {
                System.out.println("Update failed, no rows affected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
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

        return isUpdated;
    }
}
