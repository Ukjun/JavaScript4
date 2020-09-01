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
import com.koreait.pjt.vo.UserLoginHistoryVO;
import com.koreait.pjt.vo.UserVO;




@WebServlet("/login")
public class LoginSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVO LoginUser = MyUtils.getLoginUser(request);
		if(LoginUser != null) {
			response.sendRedirect("/board/list");
			return;
		}
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
		System.out.println("result : " + result);
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
			request.setAttribute("user_id", user_id);
			request.setAttribute("msg", msg);
			doGet(request,response);
			return;
		}
		
		
		String agent =request.getHeader("User-Agent");
		System.out.println("agent: " + agent);

		String browser = getBrowser(agent);
		String ip_addr = request.getRemoteAddr();
		String os = getOS(agent);
		System.out.println("os: " + os);
		System.out.println("browser: " + browser);
		System.out.println("ip_addr: " + ip_addr);
		
		
		UserLoginHistoryVO ulhVO = new UserLoginHistoryVO();
		ulhVO.setI_user(user.getI_user());
		ulhVO.setOs(os);
		ulhVO.setIp_addr(ip_addr);
		ulhVO.setBrowser(browser);
		
		UserDAO.insUserLoginHistory(ulhVO);
		//로그인 히스토리 기록
		
		
		
		
		HttpSession hs = request.getSession();
		hs.setAttribute(Const.LOGIN_USER, user);
		
		System.out.println("로그인 성공");
		response.sendRedirect("/board/list");
			
//		if(result !=1) {
//			request.setAttribute("data", user);
//			doGet(request,response);
//			return;
//		}
		
		
	}
	private String getBrowser(String agent) {
		if(agent.toLowerCase().contains("msie")) {
			return "ie";
		}else if (agent.toLowerCase().contains("chrome")) {
			return "chrome";
		}else if (agent.toLowerCase().contains("safari")) {
			return "safari";
		}
		return "";
	}
	private String getOS(String agent) {
		if(agent.toLowerCase().contains("mac")) {
			return "mac";
		}else if (agent.toLowerCase().contains("windows")) {
			return "windows";
		}else if (agent.toLowerCase().contains("x11")) {
			return "unix";
		}else if (agent.toLowerCase().contains("android")) {
			return "android";
		}else if (agent.toLowerCase().contains("iphone")) {
			return "iOS";
		}else if (agent.toLowerCase().contains("linux")) {
			return "linux";
		}
		
		return "";
	}

}
