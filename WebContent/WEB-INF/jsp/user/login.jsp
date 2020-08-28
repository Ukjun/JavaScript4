<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<link href="https://fonts.googleapis.com/css2?family=Gamja+Flower&family=Nanum+Pen+Script&display=swap" rel="stylesheet">
<style>
* { margin: 0 px; padding: 0px;font-family: 'Gamja Flower', cursive; font-size: 25px}
#container { width: 500px; height: 600px; margin: 0 auto; text-align: center; background: #F0FFF0}
#container h1{padding: 70px; color: #E0980C; font-size:50px;}
#container form {text-align: center; margin-top: 70px;}
#container form input { width: 300px; height: 50px; margin-bottom: 20px; border-radius : 20px; padding-left : 10px;}
#container form #button{width : 150px}
#container form input:focus{border : 5px solid #FFADC5; background-color: #FFCCCC}
#container form input::placeholder{color: black}
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="container">
	<h1>로그인</h1>
	<div>${msg }</div>
	<form id="frm" action="/login" method="post">
		<%-- <div><input type="text" name="user_id" id="user_id" placeholder="아이디" required value = "${data.user_id }"></div> --%>
		<div><input type="text" name="user_id" id="user_id" placeholder="아이디" required value = "Ukjun"></div>
		<div><input type="text" name="user_pw" id="user_pw" placeholder="비밀번호" required value = "2222222"></div>
		<div><input type="submit" id="button" value="로그인" onclick="check()"></div>
		<div><input type="submit" id="button" value="회원가입" onclick="move()"></div>
	</form>
	</div>
	<script>
		function check(){
			if(frm.user_id.value.length <5){
				alert('Id must be at least 5 characters!!!')
				frm.user_id.focus();
				return false
			}
		}
		function move(){
			alert('회원가입 창으로 이동합니다');
			location.href="/join"
		}
	</script>
</body>
</html>