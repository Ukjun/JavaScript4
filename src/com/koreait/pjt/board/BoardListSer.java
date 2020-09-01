package com.koreait.pjt.board;

import java.io.IOException;
import java.util.List;

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
import com.koreait.pjt.vo.BoardDomain;
import com.koreait.pjt.vo.BoardVO;
import com.koreait.pjt.vo.UserVO;


@WebServlet("/board/list")
public class BoardListSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession hs = (HttpSession)request.getSession();
		
		if(MyUtils.isLogout(request)) {
			response.sendRedirect("/login");
			return;
		}
		
		String searchText = request.getParameter("search");
		searchText = (searchText == null ? "":searchText);
		
		BoardDomain para = new BoardDomain();
		
		
		int page = MyUtils.getIntParameter(request, "page");
		page = (page == 0 ? 1 : page);
		
		int recordCnt = MyUtils.getIntParameter(request, "record_cnt");
		System.out.println("recordCnt : " + recordCnt);
		recordCnt = (recordCnt == 0 ? 3 : recordCnt);
		
				
		
		para.setRecord_cnt(recordCnt);
		para.setSearchText("%"+searchText+"%");
		int pageCnt = BoardDAO.selPagingCnt(para);
		System.out.println("pageCnt : " + pageCnt);
		if(page>pageCnt) {
			page = pageCnt;
		}
		
//		Integer beforeRecordCnt = (Integer)hs.getAttribute("recordCnt");
//		
//		if(beforeRecordCnt !=null && beforeRecordCnt < recordCnt) {
//			page = pageCnt;
//		}
		request.setAttribute("page", page);
		
		System.out.println("page : " + page);
		
//		request.setAttribute("checkpage", page);
		int eIdx = page * recordCnt;
		int sIdx = eIdx - recordCnt;
		
		
		para.seteIdx(eIdx);
		para.setsIdx(sIdx);
		
		
		System.out.println("eIdx = " + eIdx);
		System.out.println("sIdx = " + sIdx);
		
		System.out.println(BoardDAO.selPagingCnt(para));
		
		request.setAttribute("pageCnt", BoardDAO.selPagingCnt(para));
		request.setAttribute("data", BoardDAO.selBoardList(para));
		
		
		//request.setAttribute("data", list);
		ViewResolver.forwardLoginCheck("board/list", request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
