<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div>
		<form id="form" action="BoardRegmodSer" method="post">
			<div><input type = "hidden" name="i_board" value="${data.i_board }"></div>
			<div>제목 : <input type ="text" name= "title"></div>
			<div>내용 : <textarea name="ctnt"></textarea></div>
			<div>작성자 : <input type ="text" name= "i_user"></div>
			<div><input type ="submit" value = "등록"></div>
		</form>
	</div>
</body>
</html>