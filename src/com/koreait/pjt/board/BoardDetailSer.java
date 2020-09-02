package com.koreait.pjt.board;

import java.io.IOException;
import java.util.List;

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
import com.koreait.pjt.db.BoardCmtDAO;
import com.koreait.pjt.db.BoardDAO;
import com.koreait.pjt.db.UserDAO;
import com.koreait.pjt.user.Const;
import com.koreait.pjt.vo.BoardDomain;
import com.koreait.pjt.vo.BoardVO;
import com.koreait.pjt.vo.UserVO;

/**
 * Servlet implementation class BoardDetailSer
 */
@WebServlet("/board/detail")
public class BoardDetailSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String strI_board = request.getParameter("i_board");

		int i_board = MyUtils.parseStringToInt(strI_board, 0);
		BoardVO para = new BoardVO();
		BoardDomain domain = new BoardDomain();
		UserVO loginUser = MyUtils.getLoginUser(request);
		if(loginUser ==null) {
			response.sendRedirect("/login");
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
		
		
//		int page = MyUtils.getIntParameter(request, "page");
//		page = (page == 0 ? 1 : page);
//		
//		int recordCnt = MyUtils.getIntParameter(request, "record_cnt");
//		System.out.println("recordCnt : " + recordCnt);
//		recordCnt = (recordCnt == 0 ? 3 : recordCnt);
//		request.setAttribute("page", page);
		String searchType = request.getParameter("searchType");
		searchType = (searchType == null ) ? "a" : searchType;
		request.setAttribute("searchType", searchType);
		
		
		String searchText = request.getParameter("search");
		searchText = (searchText == null ? "":searchText);
		
		System.out.println("DetailSer i_board = " + i_board);
		List<?> list = BoardCmtDAO.selCmt(i_board);
		request.setAttribute("allList", list);
		System.out.println("cmt list size: " + list.size());
		if(i_board==0) {
			
			response.sendRedirect("/board/list");
			return;
		}else {
			para.setI_board(i_board);
			para.setI_user(loginUser.getI_user());
			BoardDAO.updateCount(para);
			
			
			//int check = BoardDAO.likeCheck(para);
			domain = BoardDAO.detailBoardList(para);
			System.out.println("detail title: " + domain.getTitle());
			if(!"".equals(searchText)) {
				String title = domain.getTitle();
				title = title.replace(searchText, "<span class=\"highlight\">" + searchText +"</span>");
				domain.setTitle(title);
			}
			
			
			request.setAttribute("data", domain);
			ViewResolver.forwardLoginCheck("board/detail", request, response);
		}	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
