package com.koreait.pjt.board;

import java.io.IOException;
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
		vo.setI_cmt(i_cmt);
		vo.setI_user(loginUser.getI_user());
		BoardCmtDAO.deleteCmt(vo);
		
		response.sendRedirect("/board/detail?i_board="+i_board);
	}

	//댓글 (등록/수정)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		String strI_cmt = request.getParameter("i_cmt");
		int i_cmt = MyUtils.parseStringToInt(strI_cmt, 0);
		String cmt = request.getParameter("cmt");
		
		String strI_board = request.getParameter("i_board");
		int i_board = MyUtils.parseStringToInt(strI_board, 0);
		System.out.println("before cmt: " + cmt);
		System.out.println("before i_cmt: " + i_cmt);
		UserVO util = (UserVO)hs.getAttribute(Const.LOGIN_USER);
		
		System.out.println("information : " + i_board + util.getI_user());
		
		vo.setI_cmt(i_cmt);
		vo.setCmt(cmt);
		
		vo.setI_user(util.getI_user());
		vo.setI_board(i_board);
		
		switch(strI_cmt) {
		//등록
		case "0":
			System.out.println("errorTest");
			BoardCmtDAO.insertCmt(vo);
			break;
	
		//수정
		case "2":
			BoardCmtDAO.updateCmt(vo);
			System.out.println("after cmt: " + cmt);
			System.out.println("after i_cmt: " + i_cmt);
			break;
		}
		response.sendRedirect("/board/detail?i_board="+i_board);
	}

}
