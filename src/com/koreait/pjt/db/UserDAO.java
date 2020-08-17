package com.koreait.pjt.db;

import java.sql.*;

import com.koreait.pjt.vo.UserVO;

public class UserDAO {
	public static int insUser(UserVO param) {
		String sql ="Insert into t_user "
				+"(i_user, user_id, user_pw, nm,email) "
				+"values "
				+"(seq_user.nextval,?,?,?,?) ";
		return JdbcTemplate.excuteupdate(sql, new JdbcUpdateInterface() {
			@Override
			public int update(Connection conn, PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setNString(1, param.getUser_id());
				ps.setNString(2, param.getUser_pw());
				ps.setNString(3, param.getNm());
				ps.setNString(4, param.getEmail());
				return ps.executeUpdate();
			}
		});
	}
}
