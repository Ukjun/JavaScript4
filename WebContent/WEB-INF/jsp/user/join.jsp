<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
body{
	
}
.container{
	width: 600px;
	text-align: left;
}
h1{
	
	text-align: center;
}
</style>
<body>
	<h1>회원 가입</h1>
	<div class="container">
		<form id="frm" action="/JoinSer" method="post" onsubmit="return chk()">
			<div><input type="text" name="user_id" placeholder="아이디" required></div>
			<div><input type="text" name="user_pw" placeholder="비밀번호" required></div>
			<div><input type="text" name="user_pwre" placeholder="비밀번호 확인" required></div>
			<div><input type ="text" name ="nm" placeholder="이름" required></div>
			<div><input type ="email" name ="email" placeholder="이메일" required></div>
			<div><input type="submit" value="회원가입"></div>
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
				const email_t = (/^[0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/);
				if(!email_t.test(frm.email.value)){
					alert('Please check your email !!') 
					frm.email.focus()	
					return false
				}
				console.log('email result: ' + email_t.test(frm.email.value))
			}
			return false
		}
	</script>
</body>
</html>