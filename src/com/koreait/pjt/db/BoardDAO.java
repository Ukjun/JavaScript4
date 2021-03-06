package com.koreait.pjt.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.koreait.pjt.user.Const;
import com.koreait.pjt.vo.BoardDomain;
import com.koreait.pjt.vo.BoardVO;

public class BoardDAO {
	public List<BoardDomain> searchList(){
		List<BoardDomain> list = new ArrayList();
		String sql ="select * from t_board4 where ctnt like=?";
		JdbcTemplate.executeQueryList(sql, new JdbcSelectInterface() {
			
			@Override
			public List<?> selBoard(ResultSet rs) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				//ps.setNString(1, value);
			}
			
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
		});
		return list;
	}
	
	
	private void executeQueryList(String sql, JdbcSelectInterface jdbcSelectInterface) {
		// TODO Auto-generated method stub
		
	}


	//페이징 숫자 가져오기 
	public static int selPagingCnt(final BoardDomain para ) {
		String sql ="select ceil(count(i_board)/?) from t_board4 where ";
		switch(para.getSearchType()) {
		case "a":
			sql += "title like ?";
			break;
		case "b":
			sql += "ctnt like ?";
			break;
		case "c":
			sql += "(title like ? or ctnt like ? )";
			break;
		}
		
		return JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			
			@Override
			public List<?> selBoard(ResultSet rs) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				ps.setInt(1, para.getRecord_cnt());
				ps.setNString(2, para.getSearchText());
				if(para.getSearchType().equals("c")) {
					ps.setNString(3, para.getSearchText());
				}
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
	public static List<?> selBoardList_page(int page, int recordCnt){
		String sql = "select A.* from( " + 
				"    select rownum as rnum, A.* from( " + 
				"    select A.i_board, A.title, A.ctnt, A.hits, A.i_user, A.r_dt, B.nm " + 
				"    from t_board4 A " + 
				"    inner join t_user B " + 
				"    on A.i_user = B.i_user " +
				"	 where A.title like ? "	+
				"    order by i_board desc " + 
				"    )A " + 
				"    where rownum<=? " + 
				"	)A " + 
				"	where A.rnum >=? ";
		final List<BoardVO> list = new ArrayList();
		return JdbcTemplate.executeQueryList(sql, new JdbcSelectInterface() {
			
			@Override
			public List<?> selBoard(ResultSet rs) {
				// TODO Auto-generated method stub
				try {
					while(rs.next()) {
						BoardDomain vo = new BoardDomain();
						
						vo.setI_board(rs.getInt("i_board"));
						vo.setTitle(rs.getNString("title"));
						vo.setCtnt(rs.getNString("ctnt"));
						vo.setHits(rs.getInt("hits"));
						vo.setI_user(rs.getInt("i_user"));
						vo.setR_dt(rs.getNString("r_dt"));
						vo.setNm(rs.getNString("nm"));
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
				int eIdx = page*recordCnt;
				int sIdx = eIdx-recordCnt;
				ps.setInt(1, eIdx+1);
				ps.setInt(2, sIdx);
			}
			
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				return 0;
			}
		});
		
	}
	public static List<BoardDomain> selBoardList(BoardDomain param) {
		List<BoardDomain> list = new ArrayList();
		/*
		String sql = " SELECT A.i_board, A.title, A.hits, A.i_user, A.r_dt, B.nm "
				+ " FROM t_board4 A INNER JOIN t_user B ON A.i_user = B.i_user "
				+ " ORDER BY i_board DESC ";
		*/
		String sql = " SELECT A.* FROM ( "
				+ " SELECT ROWNUM as RNUM, A.* FROM ( "
				+ " 		SELECT A.i_board, A.title, A.ctnt, A.hits, A.i_user, A.r_dt, B.nm, B.profile_img "
				+ "         , nvl(C.cnt, 0) as like_count "
				+ "			, nvl(D.cnt, 0) as cnt_count "
				+ "			, DECODE(E.i_board, null, 0, 1) as i_like "
				+ " 		FROM t_board4 A "
				+ " 		INNER JOIN t_user B "
				+ " 		ON A.i_user = B.i_user"
				+ "			LEFT JOIN ( "  
				+ "    			SELECT i_board, count(i_board) as cnt FROM t_board4_like GROUP BY i_board " 
				+ "			) C "  
				+ "			ON A.i_board = C.i_board "
				+ "			LEFT JOIN ( "
				+ "				SELECT i_board, count(i_board) as cnt FROM t_board4_cmt GROUP BY i_board "
				+ "			) D "
				+ "			ON A.i_board = D.i_board "
				+ "			LEFT JOIN ( "
				+ "				 SELECT i_board FROM t_board4_like WHERE i_user = ? "
				+ "			) E "
				+ "			ON A.i_board = E.i_board "
				+ " 		WHERE  ";
				switch(param.getSearchType()) {
				case "a":
					sql += "A.title like ?";
					break;
				case "b":
					sql += "A.ctnt like ?";
					break;
				case "c":
					sql += "(A.title like ? or A.ctnt like ? )";
					break;
				}
				sql+= " 		ORDER BY i_board DESC "
				+ " 	) A WHERE ROWNUM <= ? "
				+ " ) A WHERE A.RNUM > ? ";
		
		int result = JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				int seq = 1;
				ps.setInt(seq, param.getI_user()); //로그인한 사람의 i_user
				ps.setNString(++seq, param.getSearchText());
				if(param.getSearchType().equals("c")) {
					ps.setNString(++seq, param.getSearchText());
				}
				
				ps.setInt(++seq, param.geteIdx());
				ps.setInt(++seq, param.getsIdx());
			}
			
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				while(rs.next()) {
					int i_board = rs.getInt("i_board");	
					String title = rs.getNString("title");
					String ctnt = rs.getNString("ctnt");
					int hits = rs.getInt("hits");
					int i_user = rs.getInt("i_user");
					String r_dt = rs.getNString("r_dt");
					String nm = rs.getNString("nm");
					String profile_img = rs.getNString("profile_img");
					int like_count = rs.getInt("like_count");
					int cnt_count = rs.getInt("cnt_count");
					int i_like = rs.getInt("i_like");
					
					BoardDomain vo = new BoardDomain();
					vo.setI_board(i_board);
					vo.setTitle(title);
					vo.setCtnt(ctnt);
					vo.setHits(hits);
					vo.setI_user(i_user);
					vo.setR_dt(r_dt);
					vo.setNm(nm);
					vo.setProfile_img(profile_img);
					vo.setLike_count(like_count);
					vo.setCnt_count(cnt_count);
					vo.setI_like(i_like);
					
					list.add(vo);
				}
				return 1;
			}

			@Override
			public List<?> selBoard(ResultSet rs) {
				// TODO Auto-generated method stub
				return null;
			}			
		});
		
		return list;
	}
	public static List<?> selBoardList(){
		String sql = "  select A.i_board, A.title, A.ctnt, A.hits, A.i_user, A.r_dt, B.nm " + 
				"    from t_board4 A " + 
				"    inner join t_user B " + 
				"    on A.i_user = B.i_user "; 
//		String sql = "select i_board, title, ctnt, hits, i_user, r_dt "
//				+ "from t_board4 order by i_board desc";
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
						BoardDomain vo = new BoardDomain();
						
						vo.setI_board(rs.getInt("i_board"));
						vo.setTitle(rs.getNString("title"));
						vo.setCtnt(rs.getNString("ctnt"));
						vo.setHits(rs.getInt("hits"));
						vo.setI_user(rs.getInt("i_user"));
						vo.setR_dt(rs.getNString("r_dt"));
						vo.setNm(rs.getNString("nm"));
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
				+ "A.r_dt, B.nm, B.profile_img, decode(C.i_user,null,0,1) as yn_like"
				+ ", nvl(D.cnt,0)as like_count "
				+ "from t_board4 A "
				+ "inner join t_user B "
				+ "on A.i_user = B.i_user "
				+ "left join t_board4_like C "
				+ "on A.i_board = C.i_board "
				+ "and C.i_user=? "
				+ "left join ( "
				+ "		select i_board, count(i_board)as cnt from t_board4_like "
				+ "		where i_board = ? "
				+ "		group by i_board "
				+ ") D "
				+ "on A.i_board = D.i_board "
				+ "where A.i_board=? ";
		
		int resultInt = JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {

			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, para.getI_user());
				ps.setInt(2, para.getI_board());
				ps.setInt(3, para.getI_board());
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
					vo.setProfile_img(rs.getNString("profile_img"));
					vo.setLike_count(rs.getInt("like_count"));
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
	
	public static List<BoardDomain> selBoardLikeList(int i_board){
		List<BoardDomain> list = new ArrayList();
		String sql = "select B.i_user, B.nm, B.profile_img "
				+ "from t_board4_like A "
				+ "inner join t_user B "
				+ "on A.i_user = B.i_user "
				+ "where A.i_board = ? "
				+ "order by A.r_dt asc ";
		JdbcTemplate.executeQuery(sql, new JdbcSelectInterface() {
			
			@Override
			public List<?> selBoard(ResultSet rs) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void prepared(PreparedStatement ps) throws SQLException {
				// TODO Auto-generated method stub
				ps.setInt(1, i_board);
			}
			
			@Override
			public int executeQuery(ResultSet rs) throws SQLException {
				// TODO Auto-generated method stub
				while(rs.next()) {
					BoardDomain vo = new BoardDomain();
					vo.setI_user(rs.getInt("i_user"));
					vo.setNm(rs.getNString("nm"));
					vo.setProfile_img(rs.getNString("profile_img"));
					list.add(vo);
				}
				return 0;
			}
		});
		return list;
	}
}
