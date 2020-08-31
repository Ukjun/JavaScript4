package com.koreait.pjt.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.koreait.pjt.vo.BoardVO;
import com.koreait.pjt.vo.UserLoginHistoryVO;
import com.koreait.pjt.vo.UserVO;

public class UserDAO {
	public static int insUserLoginHistory(UserLoginHistoryVO param) {
		String sql ="insert into t_user_loginhistory "
				+ "(i_history, i_user, ip_addr, os, browser) "
				+ "values "
				+ "(seq_userloginhistory.nextval,?,?,?,?) ";
		return JdbcTemplate.excuteupdate(sql, new JdbcUpdateInterface() {

			@Override
			public void update(Connection conn, PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, param.getI_user());
				ps.setNString(2, param.getIp_addr());
				ps.setNString(3, param.getOs());
				ps.setNString(4, param.getBrowser());
			}
			
		});
	}
	public static int insUser(UserVO param) {
		String sql ="Insert into t_user "
				+"(i_user, user_id, user_pw, nm,email) "
				+"values "
				+"(seq_user4.nextval,?,?,?,?) ";
		//interface를 객체화 한것이 아니라 implements한 것
		return JdbcTemplate.excuteupdate(sql, new JdbcUpdateInterface() {
			@Override
			public void update(Connection conn, PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setNString(1, param.getUser_id());
				ps.setNString(2, param.getUser_pw());
				ps.setNString(3, param.getNm());
				ps.setNString(4, param.getEmail());
			}
		});
	}
	
	public static int detailUser(UserVO param) {
		String sql = "select nm from t_user where i_user=?";
		return JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, param.getI_user());
			}

			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				if(rs.next()) {
					String nm = rs.getNString("nm");
					param.setNm(nm);
					return 1;
				}else
					return 2;
			}

			@Override
			public List<?> selBoard(ResultSet rs) {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
	}
	public static int login(UserVO param) {
		String sql = "select i_user, user_pw, nm from t_user where user_id=?";
		
		return JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setNString(1, param.getUser_id());
			}

			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				if(rs.next()) { // 1. 로그인 성공 2. 비밀번호 틀림 3. 아이디 없음
					String db_pw = rs.getNString("user_pw");
					if(db_pw.equals(param.getUser_pw())) {
						int i_user = rs.getInt("i_user");
						String nm = rs.getNString("nm");
						
						param.setUser_pw(null);
						param.setI_user(i_user);
						param.setNm(nm);
						return 1;
					}else
						return 2;
				}else
				return 3;
			}

			@Override
			public List<?> selBoard(ResultSet rs) {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
	}
	public static UserVO selUser(int i_user) {
		String sql = "select user_id, nm, profile_img, r_dt, email "
				+ "from t_user "
				+ "where i_user=? ";
		
		UserVO result = new UserVO();
		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			
			@Override
			public List<?> selBoard(ResultSet rs) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1,i_user);
			}
			
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				if(rs.next()) {
					result.setUser_id(rs.getNString("user_id"));
					result.setNm(rs.getNString("nm"));
					result.setProfile_img(rs.getNString("profile_img"));
					result.setR_dt(rs.getNString("r_dt"));
					result.setEmail(rs.getNString("email"));
				}
				return 1;
			}
		});
		return result;
	}
	
	public static int updUser(UserVO param) {
		// 문자열 관련 메소드 ~.append = 그 문자열 뒤에 문자/문자열을 추가 
		StringBuilder sb = new StringBuilder("update t_user set m_dt = sysdate");
		if(param.getUser_pw() != null) {
			sb.append(" , user_pw = '");
			sb.append(param.getUser_pw());
			sb.append("' ");
		}
		if(param.getNm() != null) {
			sb.append(" , nm = '");
			sb.append(param.getNm());
			sb.append("' ");
		}
		if(param.getEmail() != null) {
			sb.append(" , email = '");
			sb.append(param.getEmail());
			sb.append("' ");
		}
		if(param.getProfile_img() != null) {
			sb.append(" , profile_img = '");
			sb.append(param.getProfile_img());
			sb.append("' ");
		}
		sb.append(" where i_user= " );
		sb.append(param.getI_user());
		
		System.out.println("sb : " + sb.toString());
		
		return JdbcTemplate.excuteupdate(sb.toString(), new JdbcUpdateInterface() {
			
			@Override
			public void update(Connection conn, PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
