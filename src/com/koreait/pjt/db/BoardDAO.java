package com.koreait.pjt.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreait.pjt.vo.BoardDomain;
import com.koreait.pjt.vo.BoardVO;

public class BoardDAO {
	//페이징 숫자 가져오기 
	public static int selPagingCnt(final BoardDomain para) {
		String sql ="select ceil(count(i_board)/?) from t_board4";
		
		return JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			
			@Override
			public List<?> selBoard(ResultSet rs) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, para.getRecord_cnt());
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				if(rs.next()) {
					return rs.getInt(1);
				}
				return 0;
			}
		});
	}
	public static List<?> selBoardList(){
		String sql = "select i_board, title, ctnt, hits, i_user, r_dt "
				+ "from t_board4 order by i_board desc";
//		String sql = "select A.i_board, A.title, A.ctnt, A.hits, B.user_id, A.r_dt "
//				+ "from t_board4 A inner join t_user B"
//				+ "on A.i_user=B.i_user"
//				+ "order by i_board desc";
		final List<BoardVO> list = new ArrayList();
		return JdbcTemplate.executeQueryList(sql, new JdbcSelectInterface() {

			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public List<BoardVO> selBoard(ResultSet rs) {
				// TODO Auto-generated method stub
				
				try {
					while(rs.next()) {
						BoardVO vo = new BoardVO();
						
						vo.setI_board(rs.getInt("i_board"));
						vo.setTitle(rs.getNString("title"));
						vo.setCtnt(rs.getNString("ctnt"));
						vo.setHits(rs.getInt("hits"));
						vo.setI_user(rs.getInt("i_user"));
						
						vo.setR_dt(rs.getNString("r_dt"));
						list.add(vo);
					}
				}catch(Exception e) {
					e.printStackTrace();
				}
				return list;
			}
		});
	}

	public static BoardDomain detailBoardList(final BoardVO para) {
		// TODO Auto-generated method stub
		final BoardDomain vo = new BoardDomain();
		vo.setI_board(para.getI_board());
//		String sql = "select A.i_board, A.title, A.ctnt, A.hits, B.nm, A.r_dt,A.i_user "
//				+ "from t_board4 A inner join t_user B "
//				+ "on A.i_user = B.i_user "
//				+ "where i_board=?";
		String sql = "select A.i_board, A.title, A.ctnt, A.hits, A.i_user, "
				+ "A.r_dt, B.nm, decode(C.i_user,null,0,1) as yn_like "
				+ "from t_board4 A inner join t_user B "
				+ "on A.i_user = B.i_user "
				+ "left join t_board4_like C "
				+ "on A.i_board = C.i_board and C.i_user=? "
				+ "where A.i_board=? ";
		
		int resultInt = JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, para.getI_user());
				ps.setInt(2, para.getI_board());
			}

			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				if(rs.next()) {
					vo.setI_board(rs.getInt("i_board"));
					vo.setTitle(rs.getNString("title"));
					vo.setCtnt(rs.getNString("ctnt"));
					vo.setHits(rs.getInt("hits"));
					vo.setI_user(rs.getInt("i_user"));
					vo.setR_dt(rs.getNString("r_dt"));
					vo.setNm(rs.getNString("nm"));
					vo.setYn_like(rs.getInt("yn_like"));
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
		return vo;
	}
	
	public static int insertList(BoardVO vo) {
		String sql = "insert into t_board4(i_board, title, ctnt, i_user) "+
	    		"values (seq_user4.nextval,?,?,?)";
		return JdbcTemplate.excuteupdate(sql, new JdbcUpdateInterface() {

			@Override
			public void update(Connection conn, PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setNString(1, vo.getTitle());
				ps.setNString(2, vo.getCtnt());
				ps.setInt(3, vo.getI_user());
			}
			
		});
	}
	
	public static int deleteList(BoardVO vo) {
		String sql = "delete from t_board4 where i_board=? and i_user=? ";
		
		return JdbcTemplate.excuteupdate(sql, new JdbcUpdateInterface() {

			@Override
			public void update(Connection conn, PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, vo.getI_board());
				ps.setInt(2, vo.getI_user());
			}
			
		});
	}
	public static int updateList(BoardVO vo) {
		//int i_board = vo.getI_board();
		String sql = "update t_board4 set title=?,ctnt=? where i_board=?";
		return JdbcTemplate.excuteupdate(sql, new JdbcUpdateInterface() {

			@Override
			public void update(Connection conn, PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setNString(1, vo.getTitle());
				ps.setNString(2, vo.getCtnt());
				ps.setInt(3, vo.getI_board());
			}
			
		});
	}
	public static void updateCount(BoardVO vo) {
		String sql = "update t_board4 set hits=hits+1 where i_board=?";
		JdbcTemplate.excuteupdate(sql, new JdbcUpdateInterface() {

			@Override
			public void update(Connection conn, PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, vo.getI_board());
			}
			
		});
	}
	public static void addHits(final int i_board) {
		String sql = " UPDATE t_board4 "
				+ " SET hits = hits + 1 "
				+ " WHERE i_board = ? ";
		JdbcTemplate.excuteupdate(sql, new JdbcUpdateInterface() {

			@Override
			public void update(Connection conn, PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, i_board);
			}
		
		});
	}
	
	public static void toggleInsert(BoardVO vo) {
		String sql ="insert into t_board4_like(i_board,i_user) values(?,?)";
		JdbcTemplate.excuteupdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(Connection conn, PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, vo.getI_board());
				ps.setInt(2, vo.getI_user());
			}
		});
	}
	
	public static void toggleDelete(BoardVO vo) {
		String sql = "delete from t_board4_like where i_board=? and i_user=? ";
		JdbcTemplate.excuteupdate(sql, new JdbcUpdateInterface() {
			
			@Override
			public void update(Connection conn, PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, vo.getI_board());
				ps.setInt(2, vo.getI_user());
			}
		});
	}
	
//	public static int likeCheck(BoardVO vo) {
//		String sql = "select A.i_board, A.title, A.ctnt, A.hits, A.i_user, "
//				+ "A.r_dt, B.nm, decode(C.i_user,null,0,1)as yn_like "
//				+ "from t_board4 A inner join t_user B "
//				+ "on A.i_user = B.i_user "
//				+ "left join t_board4_like C "
//				+ "on A.i_board = C.i_board and C.i_user=? "
//				+ "where A.i_board=? ";
//		return JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
//
//			@Override
//			public void prepared(PreparedStatement ps) throws SQLException {
//				// TODO Auto-generated method stub
//				ps.setInt(1, vo.getI_user());
//				ps.setInt(2, vo.getI_board());
//			}
//
//			@Override
//			public int executeQuery(ResultSet rs) throws SQLException {
//				// TODO Auto-generated method stub
//				if(rs.next()) {
//					vo.setI_board(rs.getInt("i_board"));
//					vo.setTitle(rs.getNString("title"));
//					vo.setCtnt(rs.getNString("ctnt"));
//					vo.setHits(rs.getInt("hits"));
//					vo.setI_user(rs.getInt("i_user"));
//					vo.setR_dt(rs.getNString("r_dt"));
////					vo.setNm(rs.getNString("nm"));
//					vo.setLike(rs.getInt("yn_like"));
//					return 1;
//				}else
//					return 2;
//				
//			}
//
//			@Override
//			public List<?> selBoard(ResultSet rs) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//			
//		});
//	}
}
