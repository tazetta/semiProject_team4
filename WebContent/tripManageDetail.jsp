<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>여행지 상세보기</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<style>
/* table, th, td {
	border: 1px solid black;
	border-collapse: collapse;
	padding: 10px 20px;
	text-align: center;
	margin-left: 25%;
	margin-top: 20px;
}

table {
	width: 50%;
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

#page {
	font-weight: 600;
	color: red;
}

#tripSearchBar {
	text-align: center;
}

.button {
	text-align: center;
}

button {
	padding: 10px 10px;
}

#overview {
	width: 80%;
}

div.tripManageList {
	position: absolute;
	top: 20%;
	left: 3%;
}

div.tripManageName {
	padding: 5px 15px;
	border: 1px solid black;
	width: 120px;
	height: 30px;
	text-align: center;
} */
</style>
</head>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi_manager.jsp" />
	<jsp:include page="side_tripList.jsp"/>
	
	<div  class="tripBody">
		<form name="form">
			<table class="midBody">
				<tr>
					<th>관리자 아이디</th>
					<td>${tripDTO.managerId}</td>
				</tr>
				<tr>
					<th>contentId</th>
					<td>${tripDTO.contentId}</td>
				</tr>
				<tr>
					<th>이미지 경로</th>
					<td>${tripDTO.firstImage}</td>
				</tr>
				<tr>
					<th>이미지</th>
					<td><img src="${tripDTO.firstImage}" width="200px"
						height="200px"></td>
				</tr>
				<tr>
					<th>위도</th>
					<td>${tripDTO.latitude}</td>
				</tr>
				<tr>
					<th>경도</th>
					<td>${tripDTO.longitude}</td>
				</tr>
				<tr>
					<th>주소</th>
					<td>${tripDTO.address}</td>
				</tr>
				<tr>
					<th>제목</th>
					<td>${tripDTO.title}</td>
				</tr>
				<tr>
					<th>콘텐츠 타입</th>
					<td>${tripDTO.contentName }</td>
				</tr>
				<tr>
					<th>대분류</th>
					<td>${tripDTO.largeName}</td>
				</tr>
				<tr>
					<th>중분류</th>
					<td>${tripDTO.mediumName}</td>
				</tr>
				<tr>
					<th>소분류</th>
					<td>${tripDTO.smallName}</td>
				</tr>
				<tr>
					<th>지역</th>
					<td>${tripDTO.areaName }</td>
				</tr>
				<tr>
					<th>시군구</th>
					<td>${tripDTO.cityName}</td>
				</tr>
				<tr>
					<th>상세설명</th>
					<td id="overview">${tripDTO.overview}</td>
				</tr>
				<tr>
					<th>비활성화 여부</th>
					<td>
					<c:choose>
						<c:when test="${tripDTO.deactivate eq 'TRUE'}">
							비활성화
						</c:when>
						<c:otherwise>
							활성화
						</c:otherwise>
					</c:choose>
					</td>
				</tr>
				<tr>
					<td colspan="2"class="button">
						<div >
							<input type="button" onclick="location.href='./tripManageUpdateForm?contentId=${tripDTO.contentId}&page=${currPage}'" value="수정"/>
							<c:choose>
								<c:when test="${sessionScope.type eq 'manageList'}">
									<input type="button" onclick="location.href='./tripManageList?page=${currPage}'"value="닫기"/>
								</c:when>
								<c:when test="${sessionScope.type eq 'filter'}">
									<input type="button" onclick="location.href='./tripDeactivateFilter?${sessionScope.url}&page=${currPage}'" value="닫기"/>
								</c:when>
								<c:when test="${sessionScope.type eq 'search'}">
									<input type="button" onclick="location.href='./tripSearch?${sessionScope.url}&page=${currPage}'"value="닫기"/>
								</c:when>
							</c:choose>
						</div>					
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
<script>
	$('a').hover(function() {
		$(this).css({
			'font-weight' : '600'
		});
	}, function() {
		$(this).css({
			'font-weight' : '1'
		});
	});

	var msg = "${msg}";
	if (msg != "") {
		alert(msg);
	}
</script>
</html>