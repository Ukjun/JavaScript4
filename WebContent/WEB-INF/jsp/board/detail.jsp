<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="com.koreait.pjt.vo.BoardVO" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
   <link href="https://fonts.googleapis.com/css2?family=Gamja+Flower&family=Nanum+Pen+Script&display=swap" rel="stylesheet">
   <link href="https://fonts.googleapis.com/css2?family=Raleway:ital@1&display=swap" rel="stylesheet">
   <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet">
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<style>
	body{background: #F0FFF0; font-family: 'Gamja Flower', cursive; font-size: 25px}
	#right {display: flex; justify-content:flex-end; margin-top:50px; margin-bottom: 50px;}
	#container{ width:1000px; height: 100%; margin: 0 auto;}
	#cont_txt { padding: 10px; height: 500px; vertical-align: top;}
	table { border: 1px solid white; border-collapse : collapse;font-size: 12px;  width: 100%; margin: 0 auto; border: none; background: #C8EE9D; font-size : 20px;}
	table tr { border: 1px solid #4e4e4e; background: gray; border: none;}
	table th { border: 1px solid #4e4e4e; background: #9e9e9e;  height: 40px;  font-weight: bold; background: #A9E2C5; color: #000000}
	table td { border: 1px solid #4e4e4e; height: 40px; width: 150px; ; color: #000000; background: #C8EE9D;}
	form { display: inline; }
	a {  text-decoration: none;  color: #000000; font-weight: bold;}
	.mar_l {margin: 0px 10px;}
	.s_td { width: 100px;}
	.l_td { padding-left: 10px; width: 600px;}
	.m_td { padding-left: 10px;}
	.material-icons{color: #000000; cursor: pointer;}
</style>
</head>
<body>
	<div id="container">
		<div id="right">
			<a href="/board/list" class="mar_l">뒤로가기</a>
			<c:if test="${LoginUser.i_user == data.i_user}">
				<a href="/board/regmod?i_board=${data.i_board}" class="mar_l">수정</a>
				<form id="frm" action="/board/delete" method="post">
					<input type="hidden" name="i_board" value="${data.i_board}">
					<a href="#" class="mar_l" onclick="submitDel()">삭제</a>
					
				</form>
			</c:if>
		</div>
		<table>
			<tr>
				<th class="s_td">
					제목
				</th>
				<td class="l_td">
					${data.title}
				</td>
				<th class="s_td">
					작성자
				</th>
				<td class="m_td">
					${data.nm}
				</td>
				<th class="s_td">
					날짜
				</th>
				<td class="m_td">
					${data.r_dt}
				</td>
				<th class="s_td">
					조회수
				</th>
				<td class="m_td">
					${data.hits}
				</td>
				<th class="s_td">
					좋아요
				</th>
				<td class="m_td">
					<c:if test="${data.yn_like ==0}"> <span class="material-icons" onclick="toggleChange(${data.yn_like})">favorite_border</span></c:if>
					<c:if test="${data.yn_like ==1}"> <span class="material-icons" onclick="toggleChange(${data.yn_like})">favorite</span></c:if>
				</td>	
			</tr>
			<tr>
				<th colspan="10">내용</td>
			</tr>
			<tr>
				<td id="cont_txt" colspan="10">${data.ctnt}</td>
			</tr>
			<tr>
				<td colspan ="2">좋아요를 누른사람 </td>
				<td colspan ="8"></td>
			</tr>			
		</table>
	</div>
	<script>
		function submitDel()
		{
			confirm('삭제하시겠습니까?')
			{
				frm.submit();
			}
		}
		
		function likeCount(){
			var check = `{data.like}`;
			check++;
			console.log(check)
		}
		
		function toggleChange(yn_like){
			if(yn_like==0){
				alert("좋아요을 누르셨습니다.")
				//쿼리스트링 = 키값 + value 값
				location.href= "/board/toggle?i_board=${data.i_board}&nm=${data.nm}&yn_like="+yn_like;
			}else if(yn_like==1){
				alert("좋아요를 취소하셨습니다.")
				location.href= "/board/toggle?i_board=${data.i_board}&nm=${data.nm}&yn_like="+yn_like;
			}
			
		}
	</script>
</body>
</html>