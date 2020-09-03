<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="https://fonts.googleapis.com/css2?family=Gamja+Flower&family=Nanum+Pen+Script&display=swap" rel="stylesheet">
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	test
	<div>${msg }</div>
	<c:if test="${isAuth == false || isAuth == null }"> <!-- 현재 비밀번호 확인 -->
		<div>이전 비밀번호 확인</div>
		<form action="/change" method="post">
			<input type = "hidden" name ="type" value ="1">
			<div>
				<label><input type = "password" name="pw" placeholder="현재 비밀번호를 입력해주세요"></label>
			</div>
			<div>
				<label><input type = "submit" value="확인"></label>
			</div>
		</form>
		<a href="/profile"><button>돌아가기</button></a>
	</c:if>
	
	<c:if test="${isAuth == true }"> <!-- 비밀번호 변경 -->
		<div>비밀번호 변경</div>
		<form id="frm" action="/change" method="post" onsubmit="return chkChangePw()">
			<input type = "hidden" name ="type" value ="2">
			<div>
				<label><input type = "password" name="pw" placeholder="바꾸실 비밀번호를 입력해주세요"></label>
			</div>
			<div>
				<label><input type = "password" name="repw" placeholder="비밀번호 확인 "></label>
			</div>
			<div>
				<label><input type = "submit" value="확인"></label>
			</div>
		</form>
		<a href="/profile"><button>돌아가기</button></a>
	</c:if>
</body>
<script>
	function chkChangePw(){
		if(frm.pw.value.length<5){
			alert('Password must be at least 5 characters!!!')
			frm.pw.focus();
			return false
		}
		else if(frm.pw.value != frm.repw.value){
			alert("Incorrect Password!")
			frm.pw.focus();
			return false
		}
	}
</script>
</html>