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
	<div>
		<img src  ="${data.profile_img==null ? '/img/default_profile.jpg': '' }" width="400" height="400">
	</div>
	<div>
		<form action="/profile" method="post" enctype="multipart/form-data">
			<div>
				<label>프로필 이미지 : <input type="file" name="profile_img" accept="image/*"></label>
				<input type="submit" value="upload">
			</div>
		</form>
	</div>
	<div>id : ${data.user_id }</div>
	<div>이름 : ${data.nm }</div>
	<div>이메일 : ${data.email }</div>
	<div>가입일시 : ${data.r_dt }</div>
	
</body>
</html>
