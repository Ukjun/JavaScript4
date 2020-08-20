package com.koreait.pjt.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.pjt.MyUtils;
import com.koreait.pjt.ViewResolver;
import com.koreait.pjt.db.BoardDAO;
import com.koreait.pjt.vo.BoardVO;
import com.koreait.pjt.vo.UserVO;

/**
 * Servlet implementation class BoardDelSer
 */
@WebServlet("/BoardDelete")
public class BoardDelSer extends HttpServlet {
	BoardVO vo = new BoardVO();
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strI_board = request.getParameter("i_board");
		
		int i_board = MyUtils.parseStringToInt(strI_board, 0);
		
		System.out.println("delete: " + i_board);
		
		vo.setI_board(i_board);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVO loginUser = MyUtils.getLoginUser(request);
		String strI_board = request.getParameter("i_board");
		int i_board = MyUtils.parseStringToInt(strI_board, 0);
		if(loginUser==null) {
			response.sendRedirect("LoginSer");
			return;
		}
		vo.setI_board(i_board);
		vo.setI_user(loginUser.getI_user());
		int result = BoardDAO.deleteList(vo);
		System.out.println("delete result: "+ result);
		if(result ==1) {
			response.sendRedirect("/BoardListSer");
		}else {
			response.sendRedirect("/BoardDetailSer");
		}
	}

}
