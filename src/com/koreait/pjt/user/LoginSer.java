package com.koreait.pjt.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.koreait.pjt.MyUtils;
import com.koreait.pjt.ViewResolver;
import com.koreait.pjt.db.UserDAO;
import com.koreait.pjt.vo.UserVO;




@WebServlet("/LoginSer")
public class LoginSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ViewResolver.forward("user/login", request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user_id = request.getParameter("user_id");
		String user_pw = request.getParameter("user_pw");
		String encrypt_pw = MyUtils.encryptString(user_pw);
		UserVO user = new UserVO();
		user.setUser_id(user_id);
		user.setUser_pw(encrypt_pw);
		String msg = null;
		
		int result = UserDAO.login(user);
		
		
		if(result!=1) {
			switch(result) {
			case 2:
				msg = "Password Error!!!!";
				System.out.println("Password Error!!!!");
				break;
			case 3:
				msg = "Id Error!!!!";
				System.out.println("Id Error!!!!");
				break;
			}
			request.setAttribute("data", user);
			request.setAttribute("msg", msg);
			doGet(request,response);

		}else if(result==1) {
			response.sendRedirect("BoardListSer");
			HttpSession hs = request.getSession();
			System.out.println();
			hs.setAttribute(Const.LOGIN_USER, user);
			System.out.println("로그인 성공");
			System.out.println("result : " + result);
			return;
		}
		
//		if(result !=1) {
//			request.setAttribute("data", user);
//			doGet(request,response);
//			return;
//		}
		
		
	}

}
