<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/bootstrap.jsp"/>
<script type="text/javascript">

	$(function() {
		
		getList(); //함수호출
		
		
		
		$('#btn').click(function() {
			
			var num = '${boardDTO.num}';
			var name = $('#name').val();
			var contents = $('#contents').val();
			var xhttp;
			
			//POST방식으로
			if(window.XMLHttpRequest) {
				
				xhttp = new XMLHttpRequest();
				
			} else {
				
				xhttp = new ActiveXObject("Microsoft.XMLHTTP");
				
			}
			
			xhttp.open("POST", "../comments/commentsWrite", true);
			xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			
			xhttp.send("num="+num+"&name="+name+"&contents="+contents);
			
			xhttp.onreadystatechange = function() {
				if(this.readyState == 4 && this.status == 200) {
					
					//alert(this.responseText.trim()=='1'); //중복된 아이디 0, 사용가능한건 1
					
					if(this.responseText.trim()=='1') {
						
						alert('등록완료');
						location.reload();
						
						
					} else {
						
						alert('등록살패');	
					}
					
				}
				
			}
			
		});
		
		//리스트 가져오기
		function getList() {
			
			var xhttp;
			
			if(window.XMLHttpRequest) {
				
				xhttp = new XMLHttpRequest();
				
			} else {
				
				xhttp = new ActiveXObject("Microsoft.XMLHTTP");
				
			}
			
			xhttp.open("GET", "../comments/commentsList", true);
			xhttp.send();
			
			xhttp.onreadystatechange = function() {
				if(this.readyState == 4 && this.status == 200) {
					
					$('#commentslist').append(this.responseText.trim());
					
					
				}
				
			}
			
			
		}
		
		$('#commentslist').on('click', '.delete', function() {
			var cnum = $(this).attr('id');
			var check = confirm("삭제하시겠습니까?");
				if(check==true){
					$.get("../comments/commentsDelete?cnum="+cnum, function(data) {
						if(data.trim()=='1') {
							alert('삭제완료');
							location.reload();
						} else {
							alert('삭제실패');	
						}

					});
				} 
		});
		
		
	});

</script>
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
	
	<c:if test="${board != 'notice'}">
		<div class="container">
			<!-- 댓글 입력폼 --><!-- 글번호, 작성자, 내용 -->
			<div class="row">
				 <div class="form-group">
			      <label for="name">작성자 :</label>
			      <input type="text" class="form-control" id="name" name="name">
		    </div>
		    
		    <div class="form-group">
		      <label for="contents">댓글 :</label>
		      <textarea class="form-control" rows="5" id="contents" name="contents"></textarea>
		      <button class="btn btn-primary" id="btn">댓글 등록</button>
		    </div>
			</div>
			
			<!-- 댓글리스트 -->
			<div class="row">
			
				<table class="table table-bordered" id="commentslist">
					
				
				
				</table>
			</div>
		
		
		</div>
	</c:if>
	
<a href="./${board}Update?num=${boardDTO.num}" class="btn btn-danger">Update</a>
<a href="./${board}Delete?num=${boardDTO.num}" class="btn btn-danger">Delete</a>

</div>

</body>
</html>