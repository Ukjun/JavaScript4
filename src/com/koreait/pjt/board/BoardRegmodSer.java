package com.koreait.pjt.board;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.pjt.MyUtils;
import com.koreait.pjt.ViewResolver;
import com.koreait.pjt.db.BoardDAO;
import com.koreait.pjt.user.Const;
import com.koreait.pjt.vo.BoardVO;
import com.koreait.pjt.vo.UserVO;

/**
 * Servlet implementation class BoardRegmodSer
 */
@WebServlet("/BoardRegmodSer")
public class BoardRegmodSer extends HttpServlet {
	BoardVO vo = new BoardVO();
	private static final long serialVersionUID = 1L;
    
	//화면띄우는 용도(등록/수정)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		if(hs.getAttribute(Const.LOGIN_USER)==null) {
			
			response.sendRedirect("LoginSer");
			return;
		}
		String strI_board = request.getParameter("i_board");
		
		int i_board = MyUtils.parseStringToInt(strI_board, 0);
		
		System.out.println(i_board);
		
		vo.setI_board(i_board);
		
		ViewResolver.forward("board/regmod", request, response);
	}

	//처리용도(DB에 등록/수정)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		if(hs.getAttribute(Const.LOGIN_USER)==null) {
			
			response.sendRedirect("LoginSer");
			return;
		}
		String strI_board = request.getParameter("i_board");
		int i_board = MyUtils.parseStringToInt(strI_board, 0);
		String title = request.getParameter("title");
		String ctnt = request.getParameter("ctnt");
		String strI_user = request.getParameter("i_user");
		int i_user = MyUtils.parseStringToInt(strI_user, 0);
		
		UserVO util = (UserVO)hs.getAttribute(Const.LOGIN_USER);
		int result = -1;
		
		vo.setI_board(i_board);
		vo.setTitle(title);
		vo.setCtnt(ctnt);
		vo.setI_user(util.getI_user());
		
		result = BoardDAO.insertList(vo);
		System.out.println("result : " + result);
		System.out.println(vo.getI_user());
		if(result==1) {
			response.sendRedirect("/BoardListSer");
		}
	}

}
