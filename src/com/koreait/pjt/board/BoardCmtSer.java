package com.koreait.pjt.board;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.pjt.MyUtils;
import com.koreait.pjt.db.BoardCmtDAO;
import com.koreait.pjt.user.Const;
import com.koreait.pjt.vo.BoardCmtVO;
import com.koreait.pjt.vo.UserVO;

/**
 * Servlet implementation class BoardCmtSer
 */
@WebServlet("/board/cmt")
public class BoardCmtSer extends HttpServlet {
	BoardCmtVO vo = new BoardCmtVO();
	private static final long serialVersionUID = 1L;
    
	
	//댓글 삭제
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strI_cmt = request.getParameter("i_cmt");
		int i_cmt = MyUtils.parseStringToInt(strI_cmt, 0);
		
		String strI_board = request.getParameter("i_board");
		int i_board = MyUtils.parseStringToInt(strI_board, 0);
		UserVO loginUser = MyUtils.getLoginUser(request);
		System.out.println("i_cmt: " + i_cmt);
		
		String searchText = request.getParameter("search");
		String page = request.getParameter("page");
		String record_cnt = request.getParameter("record_cnt");
		
		vo.setI_cmt(i_cmt);
		vo.setI_user(loginUser.getI_user());
		BoardCmtDAO.deleteCmt(vo);
		
		String target = String.format("/board/detail?&page=%s&record_cnt=%s&search=%s&i_board=%d"
				, page, record_cnt, searchText,i_board);
		response.sendRedirect(target);
	}

	//댓글 (등록/수정)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		UserVO util = (UserVO)hs.getAttribute(Const.LOGIN_USER);
		
		int i_cmt = MyUtils.getIntParameter(request, "i_cmt");
		String cmt = request.getParameter("cmt");
		
		int i_board = MyUtils.getIntParameter(request, "i_board");
		
		String searchText = request.getParameter("search");
		String page = request.getParameter("page");
		String record_cnt = request.getParameter("record_cnt");
		
		System.out.println("page: " + page);
		System.out.println("search: " + searchText);
		System.out.println("record_cnt: " + record_cnt);
		
		System.out.println("before cmt: " + cmt);
		System.out.println("before i_cmt: " + i_cmt);
		
		Date now = new Date();
		System.out.println("i_board : " + i_board);
		
		vo.setI_cmt(i_cmt);
		vo.setCmt(cmt);
		
		vo.setI_user(util.getI_user());
		vo.setI_board(i_board);
		
		switch(i_cmt) {
		//등록
		case 0:
			BoardCmtDAO.insertCmt(vo);
			break;
	
		//수정
		default:
			System.out.println("Update Start");
			//vo.setM_dt(now.toString());
			BoardCmtDAO.updateCmt(vo);
			//쿼리문 실행했을때 무한로딩이 걸린다면 DB에서 commit을 실행할것!!!!!!!!!!!!!!!!!
			
			System.out.println("after cmt: " + cmt);
			System.out.println("after i_cmt: " + i_cmt);
			break;
		}
		String target = String.format("/board/detail?&page=%s&record_cnt=%s&search=%s&i_board=%d"
				, page, record_cnt, searchText,i_board);
		response.sendRedirect(target);
	}

}
