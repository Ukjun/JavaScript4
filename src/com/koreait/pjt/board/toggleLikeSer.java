package com.koreait.pjt.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.pjt.MyUtils;
import com.koreait.pjt.db.BoardDAO;
import com.koreait.pjt.vo.BoardDomain;
import com.koreait.pjt.vo.UserVO;

/**
 * Servlet implementation class toggleLikeSer
 */
@WebServlet("/board/toggle")
public class toggleLikeSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String strI_board = request.getParameter("i_board");
		int i_board = MyUtils.parseStringToInt(strI_board, 0);
		String strYn_like = request.getParameter("yn_like");
		int yn_like = MyUtils.parseStringToInt(strYn_like, 0);
		
		
		UserVO loginUser = MyUtils.getLoginUser(request);
		System.out.println("yn_like = " + yn_like);
		
		
		BoardDomain para = new BoardDomain();
		para.setI_board(i_board);
		para.setI_user(loginUser.getI_user());
		
		
		System.out.println("user: " + loginUser.getI_user());
		if(yn_like==1) {
			BoardDAO.toggleDelete(para);
			response.sendRedirect("/board/detail?i_board="+i_board);
		}else {
			BoardDAO.toggleInsert(para);
			response.sendRedirect("/board/detail?i_board="+i_board);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
