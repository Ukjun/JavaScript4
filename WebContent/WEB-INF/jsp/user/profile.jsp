<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <link href="https://fonts.googleapis.com/css2?family=Gamja+Flower&family=Nanum+Pen+Script&display=swap" rel="stylesheet">
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
*{font-family: 'Gamja Flower', cursive; font-size: 25px}
</style>
</head>
<body>
	test
	<div>
		<img src  ="${data.profile_img==null ? '/img/default_profile.jpg': " " }">
	</div>
	<div>id : ${data.user_id }</div>
	<div>이름 : ${data.nm }</div>
	<div>이메일 : ${data.email }</div>
	<div>가입일시 : ${data.r_dt }</div>
</body>
</html>
