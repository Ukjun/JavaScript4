<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import = "com.koreait.pjt.vo.BoardVO" %>
    <%@ page import = "java.util.*" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<% List<BoardVO> list = (ArrayList)request.getAttribute("data");%>
<html>
<link href="https://fonts.googleapis.com/css2?family=Gamja+Flower&family=Nanum+Pen+Script&display=swap" rel="stylesheet">
<head>
<style>
*{margin:0px auto; padding:0px; font-family: 'Gamja Flower', cursive; font-size: 25px}
body{background-color: #EBE5E4;}
#container{width: 1000px; background-color: #EBE5E4;}
#container h1{text-align: center;}
.first{font-size: 20px;}
h1{line-height: 100px; font-size: 50px;}
table{margin: 0px auto; border-collapse: collapse;}
td tr{border:1px dotted black;}
td{
	width:130px;
	text-align: center;
	border-bottom: 1px solid black;
}
th:not(:last-child) {
	margin-right: 20px;	
}
th:last-child{
	width: 150px;
}
.itemRow:hover {
	cursor: pointer;
	animation-duration : 2s;
	animation-name : ani_hover;
}
@keyframes ani_hover{
        from{
            background-color: #EBE5E4;
        }to{
            background-color: #BBD1E8;
        }
   }
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="container">
	<!-- 로그인하면서 정보를 Const에 저장시켜놨음 -->
	<div class="first">${LoginUser.nm }님 환영합니다! <a href="/user/logout">로그아웃</a></div>
	<h1>List</h1>
	<table>
	<tr>
		<th>No</th>
		<th>제 목</th>
		<th>내 용</th>
		<th>조 회 수</th>
		<th>작 성 자</th>
		<th>작 성 일 자</th>
	</tr>
	<tr>
	<!-- 주소 이동에서 앞에 /를 붙이지않으면 뒤에 주소로만 가고 붙이면 앞주소기준으로 간다 -->
		<a href="/BoardRegmodSer">글쓰기</a>
	</tr>
	<c:choose>
		<c:when test="${empty data}">
		<tr>
			<td colspan="6" align="center">현재 등록된 글이 없습니다.</td>
		</tr>
		</c:when>
		<c:otherwise>
		<!-- jstl에서 보낼때는 setAttribute에서 보낸 이름으로 명령해야된다   -->
			<c:forEach items="${data}" var="item">
			<tr class="itemRow" onclick="moveToDetail(${item.i_board })">
				<td>${item.i_board }</td>
				<td>${item.title }</td>
				<td>${item.ctnt }</td>
				<td>${item.hits }</td>
				<td>${item.i_user }</td>
				<td>${item.r_dt }</td>
			</tr>
		</c:forEach>
	</c:otherwise>
</c:choose>
	
	<%-- <% for(BoardVO vo : list){ %>
	<tr class="itemRow" onclick="moveToDetail(<%=vo.getI_board()%>)">
		<td><%=vo.getI_board()%></td>
		<td><%=vo.getTitle()%></td>
		<td><%=vo.getCtnt()%></td>
		<td><%=vo.getHits()%></td>
		<td><%=vo.getI_user()%></td>
		<td><%=vo.getR_dt()%></td>
	</tr>
	<%} %> --%>
	</table>
</div>
</body>
<script>
function moveToDetail(i_board){
	console.log('moveToDetailSer - i_board:'+i_board)
	location.href ="BoardDetailSer?i_board="+i_board;
}
</script>
</html>