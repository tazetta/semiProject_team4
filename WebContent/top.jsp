<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%><html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<head>
    <meta charset="utf-8">
    <title>top</title>
    <link rel="icon" href="./koreaCI.png">
    <style>
   
        a:link {
            color: rgb(71, 71, 71);
            text-decoration: none;
        }
        a:visited {
            color: rgb(71, 71, 71);
            text-decoration: none;
        }
        .ci {
            position: relative;
            top: 15px;
            margin-left: 50px;
            float: left;
        }
        .login li {
            float: left;
            list-style-type: none;
            padding-left:15px;

        }
        .login {
            /* margin-right: 80px;; */
            display: flex;
            position: relative;
            top: 25px;
            right: 20px; 
        }
        /*검색창*/
        div#search{
            position: static;
           
            height:4px;

        }
     
        #search input[type='text']{
            border: 2px solid #E6E6E6;
            background-color:#E6E6E6;
            text-align: center;
            width: 500px;
            height: 30px;

        }
        #search input[type='submit']{
            border:#E6E6E6 ;
            background-color:#E6E6E6;
            width: 50px;
            height: 35px;
            margin-left: 10px;
            font-weight: 600;
        }
        #mtop td{
        	text-align:center;
            border-color: white; 
            height: 30px;
            white-space: nowrap;
            padding: 5px;
        }
        #mtop{
        	width: 100%;
        	position: static;
        	left: 0px;
			top: 0px;        	
        	margin: 0px;
        	bottom: 0px;
        	right: 0px;
        }
        #select{
        	background-color:  white;
        	height:30px;
        	border:0.5px solid lgihtgray;
        }
        body {
	min-width: 1400px;
 font-family: "NanumGothic"; 
}
    </style>

</head>

<body style="margin:0">
    <table id="mtop">
    	<tr>
    		<td>
    <div class="ci">
        <a href="./main" target="_parent"><img alt="CI" src="./koreaCI.png" width="100px" height="50px"></a>
    </div>
    		</td>
    		<td>
	    <div id="search">
	        <form action="search" method="GET">
	        	<select name="searchType" id="select">
	        		<option value="title">제목</option>
	        		<option value="overview">내용</option>
	        	</select>
	            <input type="text" name="keyword" placeholder="검색어를 입력해주세요" />
	            <input type="submit"  value="검색" />
	        </form>
	    </div>
    		</td>
    		<td>
	    <div class="login">
	        <ul>
	        	<c:if test="${sessionScope.loginId eq null}">
	            	<li><a href="./login.jsp">로그인</a></li>
			         <li><a href="joinForm.jsp">회원가입</a></li>
				</c:if>
				<c:if test="${sessionScope.loginId ne null}">
	            	<li><a href="./logout">로그아웃</a></li> 
				</c:if>
	        </ul>
	    </div>
    		</td>
    	</tr>
    </table>
	

</body>

</html>