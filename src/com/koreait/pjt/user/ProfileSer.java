package com.koreait.pjt.user;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.koreait.pjt.MyUtils;
import com.koreait.pjt.ViewResolver;
import com.koreait.pjt.db.UserDAO;
import com.koreait.pjt.vo.UserVO;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;


@WebServlet("/profile")
public class ProfileSer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    //프로필 화면 ( 나의 프로필 이미지, 이미지 변경가능한 화면)
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVO LoginUser = MyUtils.getLoginUser(request);	
		if(LoginUser ==null) {
			response.sendRedirect("/login");
			return;
		}
		request.setAttribute("data", UserDAO.selUser(LoginUser.getI_user()));
		
		ViewResolver.forward("user/profile", request, response);
	}

	//이미지 변경 처리
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserVO LoginUser =MyUtils.getLoginUser(request); 
		String savePath = getServletContext().getRealPath("img") + "/user/" + LoginUser.getI_user();
		System.out.println("savePath : " + savePath);
		
		File dir = new File(savePath);
		
		if(!dir.exists()) {
			//dir.mkdir() 은 폴더를 한개만 만들게 된다.
			// mkdirs()는 폴더를 계속 여러개 생성할수 있다.
			dir.mkdirs();
		}
		
		
		int maxFileSize = 10_485_760; //1024 * 1024 * 10(10mb) // 최대 파일 사이즈 크기
		String fileNm = "";
		String saveFileNm = null;
		
		try {
			//MultipartRequest 웹페이지에서 파일 업로드할때 쓰는 객체 
			//DefaultFileRenamePolicy 파일이름 생성시 중복방지
			MultipartRequest multi = new MultipartRequest(request, savePath, maxFileSize, "UTF-8", new DefaultFileRenamePolicy());
			Enumeration files = multi.getFileNames();
			
			while(files.hasMoreElements()) { // Element가 더 있는지 없는지 확인 
				String key = (String)files.nextElement(); //키값이 넘어온다
				fileNm = multi.getFilesystemName(key);
				//saveFileNm = multi.getOriginalFileName(key);
				String fileExt = fileNm.substring(fileNm.lastIndexOf("."));
				
				//파일 확장자명 가져오기
				//문자열의 인덱스값(왼쪽기준) : ~.indexOf();
				//오른쪽 기준 : ~.lastIndexOf();
				System.out.println("fileExt: " + fileExt);
				
				saveFileNm = UUID.randomUUID() +fileExt;
				
				
				System.out.println("str: " + key);
				System.out.println("fileNm: " + fileNm);
				System.out.println("saveFileNm: " + saveFileNm);
				
				
				
				//파일을 저장시키고 올릴때 oldFile이름이 newFile이름으로 덮어지게된다.
				//UUID.randomUUID() : 파일이름을 랜덤으로 정하게 된다 (중복 방지 + 매번 다르게 이름이 지어진다.)
				File oldFile = new File(savePath + "/" +fileNm);
				//파일을 저장할때 이미지이름 -> 확장자까지 DB에 저장
				File newFIle = new File(savePath + "/" + saveFileNm);
				
				oldFile.renameTo(newFIle);
				
			}
			
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		if(saveFileNm != null) {
			UserVO param = new UserVO();
			param.setProfile_img(saveFileNm);
			param.setI_user(LoginUser.getI_user());
			UserDAO.updUser(param);
		}
		response.sendRedirect("/profile");
	}

}
