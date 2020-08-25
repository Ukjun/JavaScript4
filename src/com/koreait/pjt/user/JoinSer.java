package com.koreait.pjt.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.pjt.MyUtils;
import com.koreait.pjt.ViewResolver;
import com.koreait.pjt.db.UserDAO;
import com.koreait.pjt.vo.UserVO;

/**
 * Servlet implementation class JoinSer
 */
@WebServlet("/join")
public class JoinSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// jsp파일을 열때 주로 포워딩 용도로 쓴다.
		// web-inf는 jsp를 접근하지 못하도록 하기위해서 설정하는 것
		
//		String jsp = "WEB-INF/jsp/join.jsp";
//		request.getRequestDispatcher(jsp).forward(request, response);
		
		ViewResolver.forward("user/join", request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		String encrypt_pw = MyUtils.encryptString(user_pw);
		String nm = request.getParameter("nm");
		String email = request.getParameter("email");
		
		UserVO user = new UserVO();
		user.setUser_id(user_id);
		user.setUser_pw(encrypt_pw);
		
		user.setNm(nm);
		user.setEmail(email);
		int result = UserDAO.insUser(user);
		
		if(result!=1) {
			request.setAttribute("msg", "Error!!!!");
			request.setAttribute("data", user);
			
			doGet(request,response);
			return;
		}else if(result==1) {
			response.sendRedirect("/login");
		}
	}

}

