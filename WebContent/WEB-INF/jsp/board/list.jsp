<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import = "com.koreait.pjt.vo.BoardVO" %>
    <%@ page import = "java.util.*" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<% 
	List<BoardVO> list = (ArrayList)request.getAttribute("data");
	final int ROWSIZE = 10;
	final int BLOCK = 5;
	
	
	
%>
<html>
<link href="https://fonts.googleapis.com/css2?family=Gamja+Flower&family=Nanum+Pen+Script&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
      rel="stylesheet">
<head>
<style>
*{margin:0px auto; padding:0px; font-family: 'Gamja Flower', sans-serif; font-size: 25px}
#container{width: 1000px; }
#tableCon{background-color: #F0FFF0;}
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
   .writemov{
   text-align: center;}
   .fontCenter { text-align: center; }
	.pageSelected { color:red; font-weight: bold; }
	a {
		text-decoration: none;
		color:black;
	}
	.pagingFont {
		font-size: 1.3em;
	}
	
	.pagingFont:not(:first-child) {
		margin-left: 13px;
	}
	.pagecntnot{font-weight: bold;}
	.searchtxt{text-align: center;}
	.pImg{width:48px; height: 48px; border-radius: 50%; }
	.containerpImg {display:inline-block; overflow : hidden;}
</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="container">
	<!-- 로그인하면서 정보를 Const에 저장시켜놨음 -->
	<div class="first">${LoginUser.nm }님 환영합니다! <a href="/logout">로그아웃</a></div>
	<a href = "/profile">프로필</a>
	<div>
	<form id="selFrm" action ="/board/list" method="get">
		<input type="hidden" name ="page" value="1">
		<input type="hidden" name ="search" value="${param.search }">
		레코드 수 : 
		<select name="record_cnt" onchange="changeRecordCnt()">
		<c:forEach begin="3" end="15" step="3" var="item">
			<c:choose>
				<c:when test="${param.record_cnt ==item}">
					<option value="${item}" selected>${item}개</option>
				</c:when>
			<c:otherwise>
				<option value="${item}">${item }개</option>
			</c:otherwise>
		</c:choose>
			
		</c:forEach>
		</select>
	</form>
		
	</div>
	<h1>List</h1>
	<table id="tableCon">
	<tr>
		<th>No</th>
		<th>제 목</th>
		<th>내 용</th>
		<th>조 회 수</th>
		<th colspan="2">작 성 자</th>
		<th>작 성 일 자</th>
		<th>좋아요수</th>
		<th>댓글수</th>
		<th>Y/N</th>
	</tr>
	
	<c:choose>
		<c:when test="${empty data}">
		<tr>
			<td colspan="6" align="center">현재 등록된 글이 없습니다.</td>
		</tr>
		</c:when>
		<c:otherwise>
		<!-- jstl에서 보낼때는 setAttribute에서 보낸 이름으로 명령해야된다   -->
		<div class="fontCenter">
			<c:forEach begin="1" end="${pageCnt}" var="item">
				<c:choose>
					<c:when test="${page == item }">
						<span class="pagingFont pageSelected">${item}</span>
					</c:when>
					<c:otherwise>
						<span class="pagingFont">
							<a href="/board/list?page=${item}&record_cnt=${param.record_cnt==null ? 3 : param.record_cnt}
							&search=${param.search}">${item}</a>
						</span>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</div>
		
		<div>
		<a href="/board/regmod" class='writemov'>글쓰기</a>                    
		</div>
		
		<form id="serFrm" action="/board/list">
			<div class = "searchtxt">
				<input type="search" name="search">
				<input type="submit" name="serTransmit" value="검색">
			</div>
		</form>
		
		<c:forEach items="${data}" var="item">
			<tr class="itemRow" onclick="moveToDetail(${item.i_board })" >
				<td>${item.i_board }</td>
				<td>${item.title }</td>
				<td>${item.ctnt }</td>
		 		<td>${item.hits }</td>
				<td>
					<div class="containerpImg">
						<c:choose>
							<c:when test="${item.profile_img != null}" >
								<img class="pImg" onclick="window.open(this.src)" src="/img/user/${item.i_user }/${item.profile_img}" >
							</c:when>
							<c:otherwise>
								<img class="pImg" onclick="window.open(this.src)" src="/img/default_profile.jpg">
							</c:otherwise>
						</c:choose>
					</div>
					</td>
				<td>${item.nm }</td>
				<td>${item.r_dt }</td>
				<td>${item.like_count }</td>
				<td>${item.cnt_count}</td>
				<td class="m_td">
					<c:if test="${item.i_like ==0}"> <span class="material-icons">favorite_border</span></c:if>
					<c:if test="${item.i_like ==1}"> <span class="material-icons">favorite</span></c:if>
				</td>
		<!-- <td><input type="button" value="좋아요"></td> -->
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
	<!-- 주소 이동에서 앞에 /를 붙이지않으면 뒤에 주소로만 가고 붙이면 앞주소기준으로 간다 -->
	
</div>
</body>
<script>

function moveToDetail(i_board){
	console.log('moveToDetailSer - i_board:'+i_board)
	location.href ="/board/detail?page=${page}&record_cnt=${param.record_cnt}&search=${param.search}&i_board="+i_board;
}

function changeRecordCnt(){
	selFrm.submit();
}
</script>
</html>