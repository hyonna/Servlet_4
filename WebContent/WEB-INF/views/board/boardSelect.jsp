<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/bootstrap.jsp"/>
</head>
<body>
<c:import url="../temp/header.jsp" />


<div class="container">

<h1>${board} Select Page</h1>
 

		<h4>NUM : ${boardDTO.num}</h4> <!-- 내가 꺼내고자 하는 영역 (영역이름.가져온속성이름.getNum 인 경우 get은 생략 가능-->
		<h4>TITLE : ${boardDTO.title}</h4>
		<h4>CONTENTS : ${boardDTO.contents}</h4>
		<h4>NAME : ${boardDTO.name}</h4>
		<h4>DATE : ${boardDTO.reg_date}</h4>
		<h4>HIT : ${boardDTO.hit}</h4>
		<c:catch>
		<c:forEach items="${upload}" var="up">
		<h4>UPLOAD : <a href="../upload/${up.fname}">${up.oname}</a></h4>
		</c:forEach>
		</c:catch>
	
	
	
<a href="./${board}Update?num=${boardDTO.num}" class="btn btn-danger">Update</a>
<a href="./${board}Delete?num=${boardDTO.num}" class="btn btn-danger">Delete</a>

</div>

</body>
</html>