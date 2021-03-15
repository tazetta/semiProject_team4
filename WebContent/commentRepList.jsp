<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
		<style>
			table,td,th{
				border: 1px solid black;
				border-collapse: collapse;
				padding: 5px 10px;
			}
			#body{
				width: 800px;
			}
			#page{
				text-align: center;
			}
			div{
			
			}
		</style>
	</head>
	<body>
		<div>
			<ol style="float: left; margin-right: 50px;">
				<ul><a href="./reportBBS">게시글 신고 내역</a></ul>
				<ul><a href="./reportComment">댓글 신고 내역</a></ul>
			</ol>
		</div>
		<div id="body">
		<a href="./reportComment">미처리 내역 </a> / <a href="./reportComment?deactivate=TRUE">처리 내역 </a>
		<table>
			<tr>
				<th>신고댓글 no.</th>
				<th>신고 당한 ID</th>
				<th>신고 사유</th>
				<th>블라인드 여부</th>
				<th>처리자</th>
			</tr>
			<c:forEach items="${list }" var="rep">
			<tr>
				<td><a href="./repDetailCom?reIdx=${rep.reIdx }&commentRepIdx=${rep.commentRepIdx}">${rep.reIdx }</a></td>
				<td>${rep.id }</td>
				<td>${rep.reason }</td>
				<c:if test="${rep.deactivate eq 'FALSE' }">
					<td>N</td>
				</c:if>
				<c:if test="${rep.deactivate eq 'TRUE' }">
					<td>Y</td>
				</c:if>
				<td>${rep.managerId }</td>
			</tr>			
			</c:forEach>
		</table>
		<div id="page">
		<span>
			<c:if test="${currPage==1}">이전</c:if>
			<c:if test="${currPage>1}">
				<a href='./reportComment?page=${currPage-1}'>이전</a>
			</c:if>
		</span>
		<span>${currPage}</span>
		<span>
			<c:if test="${currPage == maxPage}">다음</c:if>
         	<c:if test="${currPage < maxPage}">
         		<a href="./reportComment?page=${currPage+1}">다음</a></c:if>
		</span>		
		</div>
		</div>
	</body>
	<script>
	</script>
</html>