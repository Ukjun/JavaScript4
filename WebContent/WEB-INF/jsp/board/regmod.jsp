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
	body {background: #2B3032;  margin-top : 150px; font-family: 'Gamja Flower', cursive; font-size: 25px;}
	#frm{ width:830px; height: 600px;; margin: 0 auto;}

	.h33 {color: white;}
	#container { padding-top: 70px; text-align: center; width: 1000px; height: 650px; margin: 0 auto; background: #232729;} 
	.ipt{ font-family: 'Gamja Flower', cursive; font-size: 25px; margin-top: 50px; boarder: 3px solid #9e9e9e; width: 800px; height: 45px; padding-left:20px; background:#2B3032; color: dedede;}
	.tpt{ font-family: 'Gamja Flower', cursive; font-size: 25px; margin-top: 20px; boarder: 3px solid #9e9e9e; width: 800px; height: 300px; padding: 10px; background:#2B3032;}
	#button { font-family: 'Gamja Flower', cursive; font-size: 25px; margin-top: 80px; margin-bottom:30px; width: 100px; height: 40px; color: #dedede; background: #4e4e4e; border: none;}
	#button:hover { font-weight: bold; color: white;}
	.ipt::placeholder { font-family: 'Gamja Flower', cursive; font-size: 25px; color: #ffffff;}
	.tpt::placeholder { font-family: 'Gamja Flower', cursive; font-size: 25px; color: #ffffff;}
</style>
</head>
<body>
	<div id="container">
		<h2 class="h33">게시글작성</h2>
		<form id="frm"action="BoardRegmodSer" method="post">
			<div><input class="ipt" type="text" class="input"name="title" placeholder="제목을 작성해주세요" value="${data.title}" style="color:#ffffff; font-weight:bold;"></div>
			<div><textarea class="tpt" name="cont" placeholder="내용을 작성해주세요" style="color:#ffffff;">${data.ctnt}</textarea></div>
			<input type="hidden" name="i_board" value="${data.i_board}">
			<div><input id="button" type="submit" value="등록"></div>
		</form>
	</div>
</body>
</html>