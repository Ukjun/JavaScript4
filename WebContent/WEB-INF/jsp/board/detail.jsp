<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.koreait.pjt.vo.BoardVO" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>글 번호 : ${data.i_board}</div>
	<div>제 목 : ${data.title}</div>
	<div>내 용 : ${data.ctnt}</div>
	<div>조회수 : ${data.hits}</div>
	<div>작성자 : ${data.i_user}</div>
	<div>작성시간 : ${data.r_dt}</div>
</body>
</html>