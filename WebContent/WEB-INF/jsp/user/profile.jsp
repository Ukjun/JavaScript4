<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="https://fonts.googleapis.com/css2?family=Gamja+Flower&family=Nanum+Pen+Script&display=swap" rel="stylesheet">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
*{font-family: 'Gamja Flower', cursive; font-size: 25px}
img{width:96px; height: 96px; border-radius: 50%;}
</style>
	
</head>
<body>
	<div>
		<c:choose>
				<c:when test="${data.profile_img != null}" >

					<!-- 로그인했을때 정보는 Const에 저장한 정보를 가져와야된다. -->
					<img src="/img/user/${LoginUser.i_user }/${data.profile_img}" >
				</c:when>
				<c:otherwise>
					<img src="/img/default_profile.jpg">
				</c:otherwise>
		</c:choose>
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
