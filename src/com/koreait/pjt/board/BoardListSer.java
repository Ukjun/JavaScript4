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
		// 홈페이지 켜졌을 때 세션 작동
		HttpSession hs = (HttpSession)request.getSession();
		
		// 로그인한 유저 확인 + 로그인하지않고 들어왔을 때 로그인창으로 돌려보냄
		UserVO LoginUser = MyUtils.getLoginUser(request);
		if(MyUtils.isLogout(request)) {
			response.sendRedirect("/login");
			return;
		}
		
		// 제목/내용/제목+내용 어떤것을 검색할것인지 정함
		String searchType = request.getParameter("searchType");
		searchType = (searchType == null ) ? "a" : searchType;
		request.setAttribute("searchType", searchType);
		
		// 검색내용
		String searchText = request.getParameter("search");
		searchText = (searchText == null ? "":searchText);
		
		BoardDomain para = new BoardDomain();
		
		// 페이징
		int page = MyUtils.getIntParameter(request, "page");
		page = (page == 0 ? 1 : page);
		// 페이징마다 나타내는 게시판 갯수
		int recordCnt = MyUtils.getIntParameter(request, "record_cnt");
		System.out.println("recordCnt : " + recordCnt);
		recordCnt = (recordCnt == 0 ? 3 : recordCnt);
		
		
		para.setSearchType(searchType);
		para.setI_user(LoginUser.getI_user());
		para.setRecord_cnt(recordCnt);
		para.setSearchText("%"+searchText+"%");
		int pageCnt = BoardDAO.selPagingCnt(para);
		System.out.println("pageCnt : " + pageCnt);
		
		// 글삭제 등등 여러이유로 현재 페이지가 최대 페이지를 넘었을때 
		// 현재 페이지를 최대 페이지로 보내버림
		if(page>pageCnt) {
			page = pageCnt;
		}
		
		request.setAttribute("page", page);
		
		System.out.println("page : " + page);
		
		// 페이징 기준 변수 설정 
		int eIdx = page * recordCnt;
		int sIdx = eIdx - recordCnt;
		
		
		para.seteIdx(eIdx);
		para.setsIdx(sIdx);
		
		
		System.out.println("eIdx = " + eIdx);
		System.out.println("sIdx = " + sIdx);
		
		System.out.println(BoardDAO.selPagingCnt(para));
		
		request.setAttribute("pageCnt", BoardDAO.selPagingCnt(para));
		List<BoardDomain> list = BoardDAO.selBoardList(para);
		
		//하이라이트 처리
		if(!"".equals(searchText)&& ("a".equals(searchType) || "c".equals(searchType))) {
			for(BoardDomain item : list) {
				String title = item.getTitle();
				title = title.replace(searchText, "<span class=\"highlight\">" + searchText +"</span>");
				item.setTitle(title);
			}
		}
		// 처리를 해서 나온 결과를 보내야한다 !!!!!!!!!!
		request.setAttribute("data", list);
		
		
		//request.setAttribute("list", list);
		ViewResolver.forwardLoginCheck("board/list", request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
