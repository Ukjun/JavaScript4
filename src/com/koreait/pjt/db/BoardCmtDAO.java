package com.koreait.pjt.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreait.pjt.vo.BoardCmtVO;
import com.koreait.pjt.vo.BoardCmtVODomain;
import com.koreait.pjt.vo.BoardVO;

public class BoardCmtDAO {
	public static List<?> selCmt(int i_board){
//		String sql = "select i_cmt, i_user, i_board, cmt, r_dt from t_board4_cmt where i_board=?";
		String sql = "select A.i_cmt, B.i_user, A.cmt, A.r_dt, B.nm "
				+ "from t_board4_cmt A "
				+ "inner join t_user B "
				+ "on A.i_user=B.i_user "
				+ "where A.i_board=? "
				+ "order by A.i_cmt";
		final List<BoardCmtVODomain> list = new ArrayList();
		return JdbcTemplate.executeQueryList(sql, new JdbcSelectInterface() {
			
			@Override
			public List<BoardCmtVODomain> selBoard(ResultSet rs) {
				// TODO Auto-generated method stub
				try {
					while(rs.next()) {
						BoardCmtVODomain vo = new BoardCmtVODomain();
						vo.setI_cmt(rs.getInt("i_cmt"));
						vo.setI_user(rs.getInt("i_user"));
						vo.setNm(rs.getNString("nm"));
						vo.setCmt(rs.getNString("cmt"));
						vo.setR_dt(rs.getNString("r_dt"));
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
	public static void insertCmt(BoardCmtVO para) {
		String sql =  "insert into t_board4_cmt (i_cmt, i_board, i_user, cmt) values (seq_board4_cmt.nextval,?,?,?) ";
		JdbcTemplate.excuteupdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(Connection conn, PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, para.getI_board());
				ps.setInt(2, para.getI_user());
				ps.setNString(3, para.getCmt());
			}
		});
	}
	public static void updateCmt(BoardCmtVO para) {
		String sql ="update t_board4_cmt set cmt=? where i_board=? and i_cmt=? and i_user=?";
		JdbcTemplate.excuteupdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(Connection conn, PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setString(1, para.getCmt());
				ps.setInt(2, para.getI_board());
				ps.setInt(3, para.getI_cmt());
				ps.setInt(4, para.getI_user());
			}
		});
	}
	public static int deleteCmt(BoardCmtVO para) {
		String sql = "delete from t_board4_cmt where i_cmt=? and i_user=?";
		return JdbcTemplate.excuteupdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(Connection conn, PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, para.getI_cmt());
				ps.setInt(2, para.getI_user());
			}
		});
	}
}
