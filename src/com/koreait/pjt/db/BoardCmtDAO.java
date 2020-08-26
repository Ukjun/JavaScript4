package com.koreait.pjt.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreait.pjt.vo.BoardCmtVO;
import com.koreait.pjt.vo.BoardVO;

public class BoardCmtDAO {
	public static List<?> selCmt(int i_board){
//		String sql = "select i_cmt, i_user, i_board, cmt, r_dt from t_board4_cmt where i_board=?";
		String sql = "select A.i_board,B.nm, C.cmt, C.r_dt, C.m_dt "
				+ "from t_board4 A "
				+ "inner join t_user B "
				+ "on A.i_user = B.i_user "
				+ "right join t_board4_cmt C "
				+ "on A.i_board = c.i_board "
				+ "where c.i_board=?";
		final List<BoardCmtVO> list = new ArrayList();
		return JdbcTemplate.executeQueryList(sql, new JdbcSelectInterface() {
			
			@Override
			public List<BoardCmtVO> selBoard(ResultSet rs) {
				// TODO Auto-generated method stub
				try {
					while(rs.next()) {
						BoardCmtVO vo = new BoardCmtVO();
						vo.setI_board(rs.getInt("i_board"));
						vo.setNm(rs.getNString("nm"));
						vo.setCmt(rs.getNString("cmt"));
						vo.setR_dt(rs.getNString("r_dt"));
						vo.setM_dt(rs.getNString("m_dt"));
						list.add(vo);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
				return list;
			}
			
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, i_board);
			}
			
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
		});
	}
	public static int insertCmt(BoardCmtVO para) {
		String sql =  "insert into t_board4_cmt (i_cmt, i_board, i_user, cmt) values (seq_board4_cmt.nextval,?,?,?)";
		return JdbcTemplate.excuteupdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(Connection conn, PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, para.getI_board());
				ps.setInt(2, para.getI_user());
				ps.setNString(3, para.getCmt());
			}
		});
	}
	public static int updateCmt() {
		return 0;
	}
	public static int deleteCmt(BoardCmtVO para) {
		String sql ="update t_board4_cmt set cmt=?";
		return JdbcTemplate.excuteupdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(Connection conn, PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, para.getI_cmt());
			}
		});
	}
}
