package com.koreait.pjt.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.pjt.ViewResolver;
import com.koreait.pjt.db.UserDAO;
import com.koreait.pjt.user.Const;
import com.koreait.pjt.vo.BoardVO;


@WebServlet("/BoardListSer")
public class BoardListSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		System.out.println(hs.getAttribute(Const.LOGIN_USER));
		if(hs.getAttribute(Const.LOGIN_USER)==null) {
			
			response.sendRedirect("LoginSer");
			return;
		}
		List<BoardVO> list = UserDAO.selBoardList();
		for(BoardVO vo : list) {
			System.out.println(vo.getI_board());
		}
		System.out.println(hs.getAttribute(Const.LOGIN_USER));
		
		request.setAttribute("data", list);
		ViewResolver.forward("board/list", request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
