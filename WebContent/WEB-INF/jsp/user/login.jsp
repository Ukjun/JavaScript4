<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
* { margin: 0px; padding: 0px;}
#container { width: 100%; height: 100vh; margin: 0 auto; text-align: center; background: #333333}
#container h1{padding: 70px; color: #ECAFB5}
#container form {text-align: center; margin-top: 100px;}
#container form input { width: 500px; height: 50px; margin-bottom: 20px; border-radius : 20px; padding-left : 10px;}
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="container">
	<h1>로그인</h1>
	<form id="frm" action="/LoginSer" method="post">
		<div><input type="text" name="user_id" id="user_id" placeholder=" 아이디" required value = "${data.user_id }"></div>
		<div><input type="text" name="user_pw" id="user_pw" placeholder=" 비밀번호" required></div>
		<div><input type="submit" id="button" value=" 로그인" onclick="check()"></div>
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
	</script>
</body>
</html>