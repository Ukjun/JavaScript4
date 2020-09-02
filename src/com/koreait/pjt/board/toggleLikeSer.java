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
		
		System.out.println("Toggle Test!!!!!!!!!!!!!");
		//테스트
		String searchText = request.getParameter("search");
		String page = request.getParameter("page");
		String record_cnt = request.getParameter("record_cnt"); 
		String searchType = request.getParameter("searchType");
		
		System.out.println("page= " + page);
		System.out.println("searchText= " + searchText);
		System.out.println("recordCnt= " + record_cnt);
		//
		
		UserVO loginUser = MyUtils.getLoginUser(request);
		System.out.println("yn_like = " + yn_like);
		
		
		BoardDomain para = new BoardDomain();
		para.setI_board(i_board);
		para.setI_user(loginUser.getI_user());
		
		
		System.out.println("user: " + loginUser.getI_user());
		if(yn_like==1) {
			BoardDAO.toggleDelete(para);
		}else {
			BoardDAO.toggleInsert(para);
		}
		String target = String.format("/board/detail?&page=%s&record_cnt=%s&search=%s&searchType=%s&i_board=%d"
				, page, record_cnt, searchText,searchType,i_board);
		response.sendRedirect(target);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
