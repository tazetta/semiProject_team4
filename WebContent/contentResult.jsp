<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>테마별</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
/* div.contentList {
	position: absolute;
	top: 20%;
}

div.content {
	padding: 0px 15px;
	border: 1px solid black;
	width: 120px;
	height: 30px;
	text-align: center;
}

div.clear {
	clear: left;
	border: 1px solid black;
}

div.areaList>div {
	float: left;
	border: 1px solid black;
	padding: 5px 5px;
	width: 140px;
}

div.areaList {
	position: absolute;
	left: 25%;
	top: 15%;
}

 a:link {
	color: black;
	text-decoration: none;
}

a:visited {
	color: black;
	text-decoration: none;
}

table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
	padding: 10px 20px;
	text-align: center;
	margin-left: 25%;
	margin-top: 13%;
}

.title {
	width: 50%;
}



a {
	text-decoration: none;
}



div.chkBtn {
	position: absolute;
	top: 30%;
	right: 47%;
}

.btn {
    border:#BDBDBD ;
    background-color:#D8D8D8;
    font-weight: 600;
	padding: 20px 40px;
} */

body{
	min-width:1400px;
}
div.contentList {
	float: left;
	margin-left: 3%;
	border: 1px solid lightgray;
}

div.content {
	padding: 3px 15px;
	/* border: 1px solid black; */
	width: 150px;
	height: 30px;
	text-align: center;	
}

div.clear {
	clear: left;
	/* border: 1px solid black; */
}

div.areaList>div {
	float: left;
	/* border: 1px solid black; */
	padding: 5px 5px;
	width: 140px;
}

div.areaList {
}

 #page {
	font-weight: 600;
	color: red;
}
.pageArea {
	width: 100%;
	text-align: center;
	margin: 10px;
}

.pageArea span {
	font-size: 16px;
	border: 1px solid lightgray;
	padding: 2px 10px;
	color: gray;
}
div.chkBtn{
	text-align: center;
	margin-top: 15px;

}
.btn{
    border:#BDBDBD ;
    background-color:#D8D8D8;
    font-weight: 600;
	padding:10px 10px;
	margin:3px;
}
.mid{
	/* min-width: 600px; */
	width: 800px;
	margin: 0 auto;
	border : 1px solid #E6E6E6;
}
.areabody{
	margin-top: 25px;
	/* width: 100%; */
/* 	min-width: 1000px; */
/* 	margin-left: 5%; */
}

.result, .result th, .result td{
/* 	border: 1px solid lightgray; */
	border-collapse: collapse;
/* 	padding: 10px 20px; */
	text-align: center;
	margin-left: 20%;
	margin-top: 20px;
	border-bottom:1px solid lightgray;
}
.result{
	width: 1100px;
	maring: 0 auto;
}

.result th{
	padding:5px;
	background-color: #0B0B3B;
	color: white;
}
.title{
width:50%;
}

</style>
</head>
<body>
		<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />
	
	<div class="areabody">
	<div class="contentList">
		<c:forEach items="${contentList}" var="content">
			<div class="content" id="${content.contentCode}">
				<a href="./themeContentList?nav=${content.contentCode}">${content.name}</a>
			</div>
		</c:forEach>
	</div>


<!-- 	<form action="resultList" method="get" onsubmit="return false"> -->
	<form action="resultList" method="get">
		<table class="mid">
			<tr>
				<td>
					<div class="areaList">
						<c:forEach items="${areaList}" var="area" varStatus="status">
							<c:if test="${status.index % 5 == 0}">
								<div class="clear">
									<input type="checkbox" name="local" value="${area.areaCode}">${area.name}
								</div>
							</c:if>
							<c:if test="${status.index % 5 != 0}">
								<div>
									<input type="checkbox" name="local" value="${area.areaCode}">${area.name}
								</div>
							</c:if>
						</c:forEach>
					</div>
				</td>
			</tr>
			</table>
			
					<div class = "chkBtn">
						<input type="button" id="allBtn" class="btn" onclick="allChk()" value="전체 선택">
						<input type="button" class="btn" onclick="minChkBox()" value="검색" />
					</div>
			
		
		
		<input type="hidden" name="nav" value="${nav}" />
		<input type="hidden" name="type" value="theme" />
		
	</form>
	
	<table class="result">
		<tr>
			<th>사진</th>
			<th>제목</th>
			<th>등록일</th>
			<th>즐겨찾기 수</th>
		</tr>
		<c:forEach items="${list}" var="result" varStatus="status">
			<tr>
				<td><img src="${result.firstImage}" width="150px"
					height="100px" /></td>
				<td class="title"><a href="#"
					onclick='window.open("./tripDetail?contentId=${result.contentId}","",
			"width=880px, height=950px, left=400, top=10")'>${result.title}</a></td>
				<td>${result.reg_date}</td>
				<td>${result.bookmarkCnt}</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="4" style="border-color: white;">
				<div class="pageArea">
					<span> <c:if test="${currPage == 1}">이전</c:if> <c:if
							test="${currPage > 1}">
							<a href="./resultList?${url}&page=${currPage-1}">이전</a>
						</c:if>
					</span> <span id="page"> ${currPage} </span> <span> <c:if
							test="${currPage == maxPage}">다음</c:if> <c:if
							test="${currPage < maxPage}">
							<a href="./resultList?${url}&page=${currPage+1}">다음</a>
						</c:if>
					</span> <span>${currPage}/${maxPage}</span>
				</div>			
			</td>
		</tr>
	</table>
</div>	
</body>
<script>
	$(document).ready(function() {
		$("div#"+${nav}).css({"background-color" : "lightgray","font-weight":"600"});
	});
	
	
	function maxChkBox(){
		var cnt = 0;
		$('input[type="checkbox"]').each(function(idx, item){
			
			if($(this)[0].checked){
					cnt++;
				}
		});
		if(cnt == 0){
			alert('하나 이상을 선택해 주세요.');
		} else{
			$('form').submit();
		}
	}
	
	var isChecked = false;
	function allChk(){
		if(isChecked == false) {
			$("input[name=local]").prop("checked",true);		
			$("#allBtn").val("전체해제");
			isChecked = true;
		} else{
			$("input[name=local]").prop("checked",false);		
			$("#allBtn").val("전체선택");
			isChecked = false;
		}
	}
</script>
</html>