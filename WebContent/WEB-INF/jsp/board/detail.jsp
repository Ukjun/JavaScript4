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
	<c:if test="${LoginUser.i_user == data.i_user }">
		<a href ="BoardRegmodSer?i_board=${data.i_board }">수정</a>
		<form id="frm" action="BoardDelete" method="post">
			<input type="hidden" name="i_board" value="${data.i_board }">
			<a href="#" onclick="submitDel()">삭제</a>
		</form>
		
	</c:if>
	<div>글 번호 : ${data.i_board}  작성시간 : ${data.r_dt}</div>
	<div>제 목 : ${data.title}</div>
	<div>작성자 : ${data.nm}</div>
	<div>내 용 : ${data.ctnt}</div>
	<div>조회수 : ${data.hits}</div>
	<input type = "submit" value="수정">
	<script>
		function submitDel(){
			if(confirm('삭제하시겠습니까?')){
				frm.submit();	
			}
		}
		function submitMod(){
			frm
		}
	</script>
</body>
</html>