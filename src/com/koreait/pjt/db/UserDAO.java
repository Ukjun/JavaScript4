package com.koreait.pjt.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.koreait.pjt.vo.BoardVO;
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
	public static int login(UserVO param) {
		String sql = "select i_user, user_pw, nm from t_user where user_id=?";
		
		return JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

			@Override
			public ResultSet prepared(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setNString(1, param.getUser_id());
				return ps.executeQuery();
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
	public static List<?> selBoardList(){
		String sql = "select i_board, title, ctnt, hits, i_user from t_board4 order by i_board";
		return JdbcTemplate.executeQueryList(sql, new JdbcSelectInterface() {

			@Override
			public ResultSet prepared(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				return ps.executeQuery();
			}

			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<BoardVO> selBoard(ResultSet rs) {
				// TODO Auto-generated method stub
				List<BoardVO> list = new ArrayList();
				try {
					while(rs.next()) {
						int i_board = rs.getInt("i_board");
						String title = rs.getNString("title");
						String ctnt = rs.getNString("ctnt");
						int hits = rs.getInt("hits");
						int i_user = rs.getInt("i_user");
						
						BoardVO vo = new BoardVO();
						
						vo.setI_board(i_board);
						vo.setTitle(title);
						vo.setCtnt(ctnt);
						vo.setHit(hits);
						vo.setI_user(i_user);
						list.add(vo);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
				return list;
			}
			
		});
	}
	
}
