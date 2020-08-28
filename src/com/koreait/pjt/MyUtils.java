package com.koreait.pjt;

import java.io.IOException;
import java.security.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.koreait.pjt.user.Const;
import com.koreait.pjt.vo.UserVO;

public class MyUtils {
	public static int getIntParameter(HttpServletRequest request, String keyNm) {
		return parseStringToInt(request.getParameter(keyNm));
	}
	
	public static String encryptString(String str) {
		String sha = "";

	       try{
	          MessageDigest sh = MessageDigest.getInstance("SHA-256");
	          sh.update(str.getBytes());
	          byte byteData[] = sh.digest();
	          StringBuffer sb = new StringBuffer();
	          for(int i = 0 ; i < byteData.length ; i++){
	              sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
	          }

	          sha = sb.toString();

	      }catch(NoSuchAlgorithmException e){
	          //e.printStackTrace();
	          System.out.println("Encrypt Error - NoSuchAlgorithmException");
	          sha = null;
	      }

	      return sha;
	}
	
	public static int parseStringToInt(String str) {
		return parseStringToInt(str, 0);
	}
	public static int parseStringToInt(String str, int n) {
		try {
			Double.parseDouble(str);
			return Integer.parseInt(str);
		}catch(Exception e) {
			return n;
		}
	}
	public static UserVO getLoginUser(HttpServletRequest request) {
		HttpSession hs = request.getSession();
		return (UserVO)hs.getAttribute(Const.LOGIN_USER);
	}
	
	//return true : 로그인이 안됨 , false: 로그인이 된 상태
	public static boolean isLogout(HttpServletRequest request)throws IOException{
		HttpSession hs = request.getSession();
		if(getLoginUser(request)==null) {
			return true;
		}else
			return false;
	}
}
