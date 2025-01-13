package ScheduleModel;

import java.sql.*;
import java.util.List;

import ScheduleModel.Schedule;

import java.util.ArrayList;

public class ScheduleDAO {
	public static List<Schedule> getAllSchedule(){
		List<Schedule> schedules = new ArrayList<>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.postgresql.Driver");
			String dbUrl = "jdbc:postgresql://ep-shiny-queen-a5kntisz.us-east-2.aws.neon.tech/neondb?sslmode=require";
			conn = DriverManager.getConnection(dbUrl, "neondb_owner", "mMGl0ndLNXD6");
			String sql = "SELECT * FROM Schedule";
			
			stmt = conn.prepareStatement(sql);
			
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				int scheduleId = rs.getInt("schedule_id");
				String startTime = rs.getString("start_time");
				String endTime = rs.getString("end_time");
				
				schedules.add(new Schedule(scheduleId, startTime, endTime));
			}
			
		}catch(Exception e) {
			
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
		return schedules;
	}
}
