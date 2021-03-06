<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String loginId = (String)request.getSession().getAttribute("loginId"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>


<style>
	table{
		width:800px;
		position:relative;
		left:20%;	
		top:20px;
	}
	table, th, td {
		border: 1px solid gray;
		border-collapse: collapse;
		text-align: center;
		padding : 5px 10px;
		font-size: 18px;
	}
	input[type='text']{
		width:100%;
		font-size: 16px;
	}
	textarea{
		width:100%;
		height:300px;
		resize:none;
	}
	#submit{
		position:absolute;
		top:95%;
		left:71%;
	}
	textarea,input[type='file'],input[type='button'],button{
		font-size: 16px;
	}
	
</style>
</head>
<body>
	<jsp:include page="top.jsp" />
	<jsp:include page="navi.jsp" />
    
	<form action="boardWrite" enctype="multipart/form-data" method="post">
		<table>
			<tr>
				<th>작성자</th>
				<td><input type="text" name="userId" id="userId"value="${loginId}" readonly/></td>
			</tr>
			<tr>
				<th>제목</th>
				<td><input type="text" id="subject" name="subject" maxlength="100" placeholder="제목을 100자 내로 입력하세요."/></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea id="content" name="content" maxlength="1000" placeholder="내용을 1000자 내로 입력하세요"></textarea></td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="file" name="photo" id="photo"/>
				</td>
			</tr>
			<tr>
				<td colspan="2"><input type="button" id="regist" value="등록"/>
				<input type="button" id="cancel" onclick="location.href='./boardList'" value="취소"/>
				</td>
			</tr>
		</table>
	</form>
</body>
<script>
var $subject = $("#subject");
var $content = $("#content");
$("#regist").click(function(){
	if($subject.val()==""||$content.val()==""){
		console.log($subject.val()+"/"+$content.val())
		alert("제목과 내용을 모두 작성해주세요");
	}else{
		$("form").submit();
	}
});

var msg="${msg}";
if(msg!=""){
	alert(msg);
}
</script>
</html>