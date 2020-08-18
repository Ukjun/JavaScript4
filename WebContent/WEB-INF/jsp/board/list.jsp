<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import = "com.koreait.pjt.vo.BoardVO" %>
    <%@ page import = "java.util.*" %>
<!DOCTYPE html>
<% List<BoardVO> list = (List<BoardVO>)request.getAttribute("data");%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>List</h1>
	<table>
	<tr>
	<td>11</td>
	<% for(BoardVO vo : list){ %>
		<td><%=vo.getI_board()%></td>
	</tr>
	<%} %>
	</table>
</body>
</html>