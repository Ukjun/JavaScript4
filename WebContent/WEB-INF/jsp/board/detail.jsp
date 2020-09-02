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
	#container{ width:700px; height: 100%; margin: 0 auto;}
	#cont_txt { padding: 10px; height: 300px; vertical-align: top;}
	table { border: 1px solid white; border-collapse : collapse;font-size: 12px;  width: 100%; margin: 0 auto; border: none; background: #C8EE9D; font-size : 20px;}
	table tr { border: 1px solid #4e4e4e; background: gray; border: none;}
	table th { border: 1px solid #4e4e4e; background: #9e9e9e;  height: 40px;  font-weight: bold; background: #A9E2C5; color: #000000}
	table td { border: 1px solid #4e4e4e; height: 40px; width: 100px; ; color: #000000; background: #C8EE9D;}
	form { display: inline; }
	a {  text-decoration: none;  color: #000000; font-weight: bold;}
	.mar_l {margin: 0px 10px;}
	.s_td { width: 100px;}
	.l_td { padding-left: 10px; width: 100px;}
	.m_td { padding-left: 10px; width: 150px; }
	.m2_td{ border-right: hidden; text-align: center; width: 100px;}
	.d_td { width: 200px;}
	.n_td { width: 100px;}
	.t_td { width: 180px;}
	.material-icons{color: #000000; cursor: pointer;}
	#reg_cmt { visibility: hidden;}
	.pImg{width:48px; height: 48px; border-radius: 50%; }
</style> 
</head>
<body>
	<div id="container">
		<div id="right">
			<a href="/board/list?page=${param.page }&record_cnt=${param.record_cnt}&search=${param.search}&${param.searchType}" class="mar_l">뒤로가기</a>
			<c:if test="${LoginUser.i_user == data.i_user}">
				<a href="/board/regmod?page=${param.page }&record_cnt=${param.record_cnt}&search=${param.search}&i_board=${data.i_board}" class="mar_l">수정</a>
				<form id="frm" action="/board/delete?page=${param.page }&record_cnt=${param.record_cnt}&search=${param.search}" method="post">
					<input type="hidden" name="i_board" value="${data.i_board}">
					<a href="#" class="mar_l" onclick="submitDel()">삭제</a>
				</form>
			</c:if>
		</div>
		<table>
			<tr>
				<th class="s_td">제목</th>
				<td class="l_td">${data.title}</td>
				<th class="s_td">작성자</th>
				<td class="m2_td" colspan="2">
					<c:choose>
							<c:when test="${data.profile_img != null}" >
								<img class="pImg" onclick="window.open(this.src)" src="/img/user/${data.i_user }/${data.profile_img}" >
							</c:when>
							<c:otherwise>
								<img class="pImg" onclick="window.open(this.src)" src="/img/default_profile.jpg">
							</c:otherwise>
					</c:choose>
				</td>
				<td class="m_td">
					${data.nm}
				</td>
				<th class="s_td">날짜</th>
				<td class="d_td">${data.r_dt}</td>
			</tr>
			<tr>
				<th colspan="10">내용</td>
			</tr>
			<tr>
				<td id="cont_txt" colspan="10">${data.ctnt}</td>
			</tr>
			<tr>
				<th class="s_td">
					조회수
				</th>
				<td class="m_td">
					${data.hits}
				</td>
				<th class="s_td">
					좋아요
				</th>
				<td class="m_td" colspan="6">
					<c:if test="${data.yn_like ==0}"> <span class="material-icons" onclick="toggleChange(${data.yn_like})">favorite_border</span></c:if>
					<c:if test="${data.yn_like ==1}"> <span class="material-icons" onclick="toggleChange(${data.yn_like})">favorite</span></c:if>
				</td>
			</tr>			
		</table>
		<div>
			<form  id="cmtFrm" action="/board/cmt?page=${param.page }&record_cnt=${param.record_cnt}&search=${param.search}" method="post">
				<input type ="hidden" name="i_cmt" value="0">
				<input type ="hidden" name="i_board" value="${data.i_board }">
				
				<div>
					<input type="text" id="cmt" name="cmt" placeholder="댓글을 입력하세요">
					<input type = "submit" id="cmtSubmit" value="입력">
					<input type = "button" value = "취소" onclick="clkCmtCancel()">
				</div>
			</form>
		</div>
		<div>
			댓글 리스트
			<table>
			<tr>
				<th colspan="2">작성자</th>
				<th>작성 내용</th>
				<th>작성 일자</th>
				<th>비 고 </th>
			</tr>
		 	<c:forEach items="${allList}" var="item">
				<tr class="itemRow">
					<td class="m2_td">
					<c:choose>
							<c:when test="${item.profile_img != null}" >
								<img class="pImg" onclick="window.open(this.src)" src="/img/user/${item.i_user }/${item.profile_img}" >
							</c:when>
							<c:otherwise>
								<img class="pImg" onclick="window.open(this.src)" src="/img/default_profile.jpg">
							</c:otherwise>
					</c:choose>
					</td>
					<td class="s_td">
					${item.nm }
					</td>
					<td class="n_td"><c:out value="${item.cmt }"></c:out></td>
					<td class="t_td"><c:out value="${item.r_dt }"></c:out></td>
					
					<td>
					<c:if test="${LoginUser.i_user == item.i_user}">
						<%-- <a href="/board/regmod?i_board=${allList.i_board}" class="mar_l">수정</a> --%>
							<a href="#" onclick="clkCmtMod(${item.i_cmt},'${item.cmt }')">수정</a>
							<a href="#" onclick="clkCmtDel(${item.i_cmt})"class="mar_l">삭제</a>
					</c:if>
					</td>
				</tr>
				 <%-- <tr>
					<td id="reg_cmt">
						<form  id="cmtFrm2" action="/board/cmt" method="post">
							<input type="text"name = "cmt" value=${item.cmt }>
							<input type ="hidden" name="i_cmt" value="${item.i_cmt }">
							<input type ="hidden" name="i_board" value="${item.i_board }">
							<input type = "submit" value="수정">
						</form>
					</td>
				</tr>   --%>
			</c:forEach>
			</table>
		</div>
	</div>
	<script>
		console.log(`${data.i_board}`)
		
		function clkCmtCancel(){
			cmtFrm.i_cmt.value = 0;
			cmtFrm.cmt.value='';
			cmtSubmit.value ='전송';
		}
		
		
		function submitDel()
		{
			confirm('삭제하시겠습니까?')
			{
				frm.submit();
			}
		}
		function clkCmtDel(i_cmt){
			if(confirm("Do you want to delete??")){
				location.href ='/board/cmt?page=${param.page }&record_cnt=${param.record_cnt}&search=${param.search}&i_board=${data.i_board}&i_cmt='+i_cmt;
			}
		}
		function clkCmtMod(i_cmt,cmt){
			console.log(i_cmt)	
			cmtFrm.i_cmt.value = i_cmt;
			cmtFrm.cmt.value = cmt;
			
			cmtSubmit.value ='수정';
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
				location.href= "/board/toggle?page=${param.page}&record_cnt=${param.record_cnt}&search=${param.search}&i_board=${data.i_board}&yn_like=" + yn_like;
			}else if(yn_like==1){
				alert("좋아요를 취소하셨습니다.")
				location.href= "/board/toggle?page=${param.page}&record_cnt=${param.record_cnt}&search=${param.search}&i_board=${data.i_board}&yn_like=" + yn_like;
			}
			
		}
	</script>
</body>
</html>