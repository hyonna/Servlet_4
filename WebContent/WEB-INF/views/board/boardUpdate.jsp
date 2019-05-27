<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/bootstrap.jsp"/>
<script type="text/javascript">

$(function() {
	
	var maxAppend = 0;
	var d1=	0;
	
	//x버튼을 클릭하면 alert(pnum.val)
	$('.fileDelete').click(function() {
		
		//1.XMLHttpRequest 생성
		var xhttp;
		var id = $(this).attr("id");
		if(window.XMLHttpRequest) { //최신 브라우저라면,
				xhttp = new XMLHttpRequest(); //객체 생성
			} else { //오래된 브라우저라면
				xhttp = new ActiveXObject("Microsoft.XMLHTTP"); //이 코드 작성
			}

		//2.연결정보 작성
		xhttp.open("GET", "../file/fileDelete?pnum="+id, true);
		
		//3.전송
		xhttp.send();
		
		//4.결과처리
		xhttp.onreadystatechange = function() {
			
				if(this.readyState == 4 && this.status == 200) {
					
					//alert(this.responseText.trim()=='1'); //중복된 아이디 0, 사용가능한건 1
					
					if(this.responseText.trim() == '1') {
						$("#"+id).prev().remove();
						$("#"+id).remove();
						
					} else {
						
						alert('삭제 실패');
					}
				
				}
				
			}
		
	});
	
	$("#add").click(function() {
		d1++;
		if(maxAppend<5){
			$("#addFile").append('<div id="'+d1+'"><input type="file" class="form-control" id="" name="f'+d1+'"><span title="'+d1+'" class="del">X</span></div>')
			maxAppend++;
			
		}else {
			alert('5개만 가능');
		}
	});
	
	///////////////////
	$("#addFile").on("click", ".del", function() {
		//$(this).prev().remove();
		//$(this).remove();
		var v = $(this).attr('title');
		$('#'+v).remove();
		maxAppend--;
		
	});
});
</script>
<style type="text/css">

.deL {

	color : red;
	cursor: pointer;


}

</style>
</head>
<body>
<c:import url="../temp/header.jsp"/>

<div class="container">
	
	<h1>${board}&nbsp;Update Page</h1>

 <form action="./${board}Update" method="post" enctype="multipart/form-data">
<!--  데이터가 크기 때문에 메소드는 포스트로, 파일 업로드시  enctype=multipart/form-data 필수 -->
 
 	<input type="hidden" name="num" value="${boardDTO.num}"> 
 
    <div class="form-group">
      <label for="title">title :</label>
      <input type="text" class="form-control" id="title" name="title" value="${boardDTO.title}">
    </div>
    
    <div class="form-group">
      <label for="name">작성자 :</label>
      <input type="text" class="form-control" id="name" name="name" value="${boardDTO.name}" readonly>
    </div>
    
    <div class="form-group">
	<label for="contents">Contents:</label>
	<textarea class="form-control" rows="20" id="contents" name="contents">${boardDTO.contents}</textarea>
	</div>
			
			<div class="form-group" id="addFile">
		      <label for="file">File:</label>
		      
		      <c:forEach items="${upload}" var="up">
		      <span>${up.oname}</span><span id="${up.pnum}" class="fileDelete">X</span>
		     
		      </c:forEach>
		    </div>
		    
		    <div class="form-group">
		    	<input type="button" value="Add" id="add" class="btn btn-primary">
		    </div>
		    
		    <button class="btn btn-danger">Update</button>
		  </form>
	
	
	</div>


</body>
</html>