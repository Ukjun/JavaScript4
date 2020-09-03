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
 * Servlet implementation class ChangePwSer
 */
@WebServlet("/change")
public class ChangePwSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ViewResolver.forwardLoginCheck("user/changePw", request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVO LoginUser= MyUtils.getLoginUser(request);
		
		String type = request.getParameter("type");
		String pw = request.getParameter("pw");
		String user_pw = MyUtils.encryptString(pw);
		System.out.println(pw);
		System.out.println(user_pw);
		int i_user = 0;
		UserVO param = new UserVO();
		switch(type) {
		case "1": // 현재 비밀번호 확인
			
			param.setUser_pw(user_pw);
			param.setUser_id(LoginUser.getUser_id());
			
			int result = UserDAO.login(param);
			i_user = param.getI_user();
			if(result == 1) {
				request.setAttribute("isAuth", true);
			}else {
				request.setAttribute("msg", "비밀번호를 확인해주세요");
			}
			doGet(request,response);
			break;
		case "2": // 비밀번호 변경
			System.out.println("changePw i_user: " + i_user);
			param.setUser_id(LoginUser.getUser_id());
			param.setUser_pw(user_pw);
			param.setI_user(LoginUser.getI_user());
			UserDAO.updUser(param);		
			response.sendRedirect("/profile?proc=1");
			break;
		}
	}

}
