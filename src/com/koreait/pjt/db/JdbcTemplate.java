package com.koreait.pjt.db;

import java.sql.*;

public class JdbcTemplate {
	public static int excuteupdate(String sql,JdbcUpdateInterface jdbc) {
		int result = 0;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DBCon.getCon();
			ps = conn.prepareStatement(sql);
			result = jdbc.update(conn, ps);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			DBCon.close(conn, ps);
		}
		return result;
	}
}
