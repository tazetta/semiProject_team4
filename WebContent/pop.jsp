<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>팝업 목록 페이지</title>
<link rel="stylesheet" type="text/css" href="basic.css">
<style>
#pop_main {
	width: 900px;
    margin-top: 5%;
    margin-left: 35%;
}

#popup {
	border-collapse: collapse;
	margin: 45px 0;
	width: 700px;
}

.pop_form {
	text-align: left;
}

th, td {
	border: 1px solid lightgray;
	text-align: center;
	padding: 8px;
	font-size: 14px;
}

td {
	background-color: white;
}

#title {
	background-color: gray;
	color: white;
}

.regist {
	font-size: 13px;
	padding: 5px 8px;
}

.p_button {
	float: right;
	margin-right: 30%;
}

#page{
     margin : auto;
     text-align:center;           
}

#page span{
	font-size : 16px;
	border:1px solid lightgray;
	padding: 2px 10px;
	margin:2px;
	background-color: whitesmoke;
}

a {
	text-decoration: none;
	font-size: 90%;
	color: black;
}

a:hover {
	font-weight: 600;
}

</style>
</head>
<body>
	<!--상단페이지-->
	<jsp:include page="top.jsp" />

	<!--상단네비-->
	<jsp:include page="navi_manager.jsp" />

	<div id="pop_main">

		<div class="p_button">
			<button class="regist" onclick="location.href='popWrite.jsp'">신규 팝업등록</button>
		</div>

			<div class="pop_form">
				<h3>팝업 목록</h3>
			</div>
		<table id="popup">
			<tr id="title">
				<th>등록일</th>
				<th>등록 관리자</th>
				<th>제목</th>
				<th>노출여부</th>
				<th></th>
			</tr>
			<c:forEach items="${popupList}" var="popup">
				<tr>
					<td>${popup.reg_date}</td>
					<td>${popup.managerid}</td>
					<td><a href="popupDetail?infoidx=${popup.infoidx}">${popup.subject}</a></td>
					<td>${popup.popupalert}</td>
					<td><a href="popupDel?infoidx=${popup.infoidx}">삭제</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	
			<div id="page">
			<span>
				<c:if test="${currPage==1}">이전</c:if>
				<c:if test="${currPage>1}">
					<a href='./popupList?page=${currPage-1}'>이전</a>
				</c:if>
			</span>
			<span>${currPage}</span>
			<span>
				<c:if test="${currPage == maxPage}">다음</c:if>
         		<c:if test="${currPage < maxPage}">
         			<a href="./popupList?page=${currPage+1}">다음</a></c:if>
			</span>		
		</div>
</body>
<script>
	var msg = "${msg}";
	if(msg!=""){
		alert(msg);
	}	
</script>
</html>