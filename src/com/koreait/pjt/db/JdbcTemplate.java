package com.koreait.pjt.db;

import java.sql.*;
import java.util.*;

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
	public static int executeQuery(String sql, JdbcSelectInterface jdbc) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int result =0;
		try {
			conn = DBCon.getCon();
			ps = conn.prepareStatement(sql);
			rs = jdbc.prepared(ps);
			result = jdbc.executeQuery(rs);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBCon.close(conn, ps,rs);
		}
		return result;
		
	}
	public static List<?> executeQueryList(String sql, JdbcSelectInterface jdbc){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List <?> list = new ArrayList();
		try {
			conn = DBCon.getCon();
			ps = conn.prepareStatement(sql);
			rs = jdbc.prepared(ps);
			list = jdbc.selBoard(rs);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBCon.close(conn, ps,rs);
		}
		return list;
	}
//	public static int excuteQuery(String sql,JdbcUpdateInterface jdbc) {
//		int result = 0;
//		Connection conn= null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		try {
//			conn = DBCon.getCon();
//			ps = conn.prepareStatement(sql);
//		}
//		return 0;
//	}
}

