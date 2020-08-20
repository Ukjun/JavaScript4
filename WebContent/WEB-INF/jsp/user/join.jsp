<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link href="https://fonts.googleapis.com/css2?family=Gamja+Flower&family=Nanum+Pen+Script&display=swap" rel="stylesheet">
<style>
	* { margin: 0px; padding: 0px;font-family: 'Gamja Flower', cursive; font-size: 25px}
	#container { width: 100%; height: 100vh; margin: 0 auto; text-align: center; background: #333333} 
	#container h1{padding: 70px; color: #dedede}
	#container form {text-align: center; margin-top: 100px;}
	#container form input { width: 500px; height: 50px; margin-bottom: 20px; border-radius : 20px;}
	#button { background : #8e8e8e}
	#button:hover{ background : #cecece; color : 2e2e2e; font-weight: bold;}
</style>
<body>
	
	<div id="container">
		<h1>회원 가입</h1>
		
		<hr>
		<form id="frm" action="/JoinSer" method="post" onsubmit="return chk()">
			<div><input type="text" name="user_id" id="user_id"placeholder=" 아이디" required value = "${data.user_id }"></div>
			<div><input type="text" name="user_pw" placeholder=" 비밀번호" required></div>
			<div><input type="text" name="user_pwre" placeholder=" 비밀번호 확인" required></div>
			<div><input type ="text" name ="nm" id="nm"placeholder=" 이름" required value = "${data.nm }"></div>
			<div><input type ="email" name ="email" id="email"placeholder=" 이메일" required value = "${data.email }"></div>
			<div><input type="submit" id="button" value=" 회원가입"></div>
		</form>
	</div>
	<script>
		function chk(){
			if(frm.user_id.value.length <5){
				alert('Id must be at least 5 characters!!!')
				frm.user_id.focus();
				return false
			}else if ( frm.user_pw.value.length <5){
				alert('Password must be at least 5 characters!!!')
				frm.user_pw.focus();
				return false
			}else if (frm.user_pw.value != frm.user_pwre.value){
				alert('Please check your password')
				frm.user_pwre.focus();
				return false
			}else if (frm.nm.value.length >0){
				const korean = /[^가-힣]/;
				const result = korean.test(frm.nm.value);
				if(result){
					alert('Please check your Name')
					frm.nm.focus()
					return false
				}
				//console.log('result : ' + result)
			}
			if(frm.email.value.length>0){
				const email_t = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
				if(!email_t.test(frm.email.value)){
					alert('Please check your email !!') 
					frm.email.focus()	
					return false
				}
				console.log('email result: ' + email_t.test(frm.email.value))
			}
			
		}
	</script>
</body>
</html>