<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<link href="https://fonts.googleapis.com/css2?family=Gamja+Flower&family=Nanum+Pen+Script&display=swap" rel="stylesheet">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
	* { margin: 0px; padding: 0px;}
	body {background: #F0FFF0; margin-top : 50px; font-family: 'Gamja Flower', cursive; font-size: 25px;}
	#frm{ width:600px; height: 600px;; margin: 0 auto;}

	.h33 {color: black;}
	#container { padding-top: 70px; text-align: center; width: 1000px; height: 650px; margin: 0 auto; } 
	.ipt{ font-family: 'Gamja Flower', cursive; font-size: 25px; margin-top: 50px; boarder: 3px solid #000000; width: 600px; height: 45px; padding-left:20px; background:#C8EE9D; color: #000000;}
	.tpt{ font-family: 'Gamja Flower', cursive; font-size: 25px; margin-top: 20px; boarder: 3px solid #000000; width: 600px; height: 300px; padding: 10px; background:#C8EE9D;}
	#button { font-family: 'Gamja Flower', cursive; font-size: 25px; border: 1px solid black; margin-top: 80px; margin-bottom:30px; width: 100px; height: 40px; color: #000000;  border: none;}
	#button:hover { font-weight: bold; color: black;}
	.ipt::placeholder { font-family: 'Gamja Flower', cursive; font-size: 25px; color: #000000;}
	.tpt::placeholder { font-family: 'Gamja Flower', cursive; font-size: 25px; color: #000000;}
</style>
</head>
<body>
	<div id="container">
		<h2 class="h33">게시글작성</h2>
		<form id="frm" action="/board/regmod?page=${param.page }&record_cnt=${param.record_cnt}&search=${param.search}&searchType=${param.searchType}" method="post">
			<div><input class="ipt" type="text" class="input"name="title" placeholder="제목을 작성해주세요" value="${data.title}" style=color:#000000;></div>
			<div><textarea class="tpt" name="ctnt" placeholder="내용을 작성해주세요" style="color:#000000;">${data.ctnt}</textarea></div>
			<input type="hidden" name="i_board" value="${data.i_board}">
			<div><input id="button" type="submit" value="등록"></div>
		</form>
	</div>
</body>
<script>
	
</script>
</html>