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
		// 리스트 목록 받아오기 
		
		
		List<?> list = BoardDAO.selBoardList();
		// 리스트 크기 출력 
		System.out.println(list.size());
		
		// 리스트 내용 jsp로 전달해서 출력하기 위함
		
		BoardDomain para = new BoardDomain();
		para.setRecord_cnt(Const.RECORD_CNT);
		
		
		int page = MyUtils.getIntParameter(request, "page");
		page = page==0?1:page;
		
		
		System.out.println("recordCnt = " + para.getRecord_cnt());
		System.out.println(BoardDAO.selPagingCnt(para));
		request.setAttribute("pageCnt", BoardDAO.selPagingCnt(para));
		
		
		request.setAttribute("data", list);
		ViewResolver.forwardLoginCheck("board/list", request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
