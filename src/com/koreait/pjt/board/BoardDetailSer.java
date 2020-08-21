package com.koreait.pjt.board;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
		BoardVO para = new BoardVO();
		UserVO loginUser = MyUtils.getLoginUser(request);
		if(loginUser ==null) {
			response.sendRedirect("LoginSer");
			return;
		}
		// 글작성시의 i_user와 로그인했을때의 i_user가 같지않을때 true가 반환되서 조회수올리기를 실행 
//		ServletContext application = getServletContext();
//		Integer readI_user = (Integer)application.getAttribute("read_" + strI_board);
//		if(readI_user != loginUser.getI_user()) {
//			
//			para.setI_board(i_board);
//			BoardDAO.addHits(i_board);
//			application.setAttribute("read_"+strI_board,loginUser.getI_user());
//		}
		
//		para.setI_board(i_board);
//		BoardDAO.addHits(i_board);
//		request.setAttribute("data", BoardDAO.detailBoardList(para));
//		ViewResolver.forwardLoginCheck("board/detail", request, response);
		
				
		System.out.println("i_board = " + i_board);
		if(i_board==0) {
			response.sendRedirect("/BoardListSer");
			return;
		}else {
			para.setI_board(i_board);
			BoardDAO.updateCount(para);
			System.out.println("count: " + para.getHits());
			request.setAttribute("data", BoardDAO.detailBoardList(para));
			ViewResolver.forwardLoginCheck("board/detail", request, response);
		}	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
