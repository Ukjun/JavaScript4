package com.koreait.pjt.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.pjt.MyUtils;
import com.koreait.pjt.ViewResolver;
import com.koreait.pjt.db.BoardDAO;
import com.koreait.pjt.db.UserDAO;
import com.koreait.pjt.user.Const;
import com.koreait.pjt.vo.BoardVO;
import com.koreait.pjt.vo.UserVO;

/**
 * Servlet implementation class BoardDetailSer
 */
@WebServlet("/BoardDetailSer")
public class BoardDetailSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strI_board = request.getParameter("i_board");

		int i_board = MyUtils.parseStringToInt(strI_board, 0);
		System.out.println("i_board = " + i_board);
		if(i_board==0) {
			response.sendRedirect("/BoardListSer");
			return;
		}else {
			BoardVO para = new BoardVO();
			para.setI_board(i_board);
//			para.setName(name);
			BoardVO vo = BoardDAO.detailBoardList(para);
			request.setAttribute("data", vo);
			//request.setAttribute("data", util);
			System.out.println(vo.getTitle());
			ViewResolver.forwardLoginCheck("board/detail", request, response);
		}	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
