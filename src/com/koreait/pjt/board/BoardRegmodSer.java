package com.koreait.pjt.board;

import java.io.IOException;
import java.net.URLEncoder;

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
@WebServlet("/board/regmod")
public class BoardRegmodSer extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	//화면띄우는 용도(등록/수정)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String strI_board = request.getParameter("i_board");
		
		int i_board = MyUtils.parseStringToInt(strI_board, 0);
		
		
		BoardVO vo = new BoardVO();
		
		vo.setI_board(i_board);
		
		request.setAttribute("data", BoardDAO.detailBoardList(vo));
		System.out.println("regmod i_board= " + i_board);
		ViewResolver.forwardLoginCheck("board/regmod", request, response);
		
	}

	//처리용도(DB에 등록/수정)
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession hs = request.getSession();
		
		String strI_board = request.getParameter("i_board");
		int i_board = MyUtils.parseStringToInt(strI_board, 0);
		String title = request.getParameter("title");
		String ctnt = request.getParameter("ctnt");
		String strI_user = request.getParameter("i_user");
		int i_user = MyUtils.parseStringToInt(strI_user, 0);
		
		
		String searchText = request.getParameter("search");
		
		
		//한글로 파라미터 보낼떄 깨지는 현상 수정하는 코드
		searchText = URLEncoder.encode(searchText,"UTF-8");
		
		
		
		String page = request.getParameter("page");
		String record_cnt = request.getParameter("record_cnt");
		String searchType = request.getParameter("searchType");

		
		BoardVO vo = new BoardVO();
		UserVO util = (UserVO)hs.getAttribute(Const.LOGIN_USER);
		int result = -1;
		
		String filterCtnt1 = scriptFilter(ctnt);
		String filterCtnt2 = swearWordFilter(filterCtnt1);
		
		vo.setI_board(i_board);
		vo.setTitle(title);
		vo.setCtnt(filterCtnt1);
		vo.setI_user(util.getI_user());
		System.out.println("check i_board = " + i_board);
		
		
		if(i_board==0) {
			result = BoardDAO.insertList(vo);
		}else {
			result = BoardDAO.updateList(vo);
		}
		
		System.out.println("result : " + result);
		System.out.println(vo.getI_user());
		if(result==1) {
			String target = String.format("/board/detail?&page=%s&record_cnt=%s&search=%s&searchType=%s&i_board=%d"
					, page, record_cnt, searchText,searchType,i_board);
			response.sendRedirect(target);
			System.out.println("reg search:" + searchText);
			System.out.println("reg page:" + page);
			System.out.println("reg searchType:" + searchType);
			
		}
	}
	private String swearWordFilter(final String ctnt) {
		String[] filter = {"미친년"};
		String result = ctnt;
		for(int i=0; i<filter.length; i++) {
			result = result.replace(filter[i], "");
			
		}
		return result;
	}
	
	public String scriptFilter(String ctnt) {
		String[] filter = {"<script>"};
		String[] filterResult = {"&lt;script&gt;","&lt;/script&gt;"};
		
		String result = ctnt;
		for(int i=0; i<filter.length; i++) {
			result = result.replace(filter[i],filterResult[i]);
		}
		return result;
	}
}
