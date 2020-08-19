package com.koreait.pjt.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreait.pjt.vo.BoardVO;

public class BoardDAO {
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

	public static BoardVO detailBoardList(BoardVO para) {
		// TODO Auto-generated method stub
		BoardVO vo = new BoardVO();
		String sql = "select i_board, title, ctnt, hits, i_user, r_dt "
				+ "from t_board4 where i_board=?";
		
		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, para.getI_board());
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
	    		"values (seq_board4.nextval,?,?,?)";
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
}