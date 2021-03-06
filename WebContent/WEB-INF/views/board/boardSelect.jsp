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
		
		var curPage = 1;
		
		getList(); //함수호출
		
		$('#updateBtn').click(function() {
			var upContents = $('#updateContents').val();
			var cnumId = $('#cnum').val();
			
			$.post("../comments/commentsUpdate", 
					{
				
					cnum:cnumId,
					contents:upContents
				
					}, 
				
				function(data) {
				
						if(data.trim()=='1') {
							
							//location.reload();
							getList(1);
							//$('#c'+id).html(upContents);
														
						} else {
							
							alert('수정실패');
						}
			});
			
		});
		
		$('#commentslist').on('click', '.upBtn', function() {
			
			var id = $(this).attr('title');
			var con = $('#c'+id).html();
			
			$('#updateContents').val(con);
			$('#cnum').val(id);
		
		});
		
		
		//////
		$('#more').click(function() {
			curPage++;
			getList(curPage);
			
		});
		
		
		/////
			
		
		$('#btn').click(function() {
			
			var num = '${boardDTO.num}';
			var name = $('#name').val();
			var contents = $('#contents').val();
			
			//POST방식으로
			
			$.post("../comments/commentsWrite", 
			
					{
				
				num:num,
				name:name,
				contents:contents
				
				
					}, function(data) {
				if(data.trim()=='1') {
					
					alert('등록성공');
					location.reload();
					getList(1);
					
				} else {
					
					alert('등록실패');
				}
			});
			
			
		});
		
		
		//리스트 가져오기
		function getList(count) {
			
			$.get("../comments/commentsList?num=${boardDTO.num}&curPage="+count, function(data) {
				var data = data.trim();
				if(count==1){
					
					$('#commentslist').html(data);
					
				} else {
					
					$('#commentslist').append(data);
				}
				
			})
			
		}
		
		
		
		
		$('#commentslist').on('click', '.delete', function() {
			var cnum = $(this).attr('id');
			var check = confirm("삭제하시겠습니까?");
				if(check==true){
					$.get("../comments/commentsDelete?cnum="+cnum, function(data) {
						if(data.trim()=='1') {
							alert('삭제완료');
							location.reload();
							getList(1); //append가 아니라 html로 덮어씌우기
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
		<h4>CONTENTS : </h4>
		<div>${boardDTO.contents}</div>
		<h4>NAME : ${boardDTO.name}</h4>
		<h4>DATE : ${boardDTO.reg_date}</h4>
		<h4>HIT : ${boardDTO.hit}</h4>
		<c:forEach items="${upload}" var="up">
		<h4>UPLOAD : <a href="../upload/${up.fname}">${up.oname}</a></h4>
		</c:forEach>
	
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
				<button id="more">+ 더보기</button>
			</div>
		
		
		</div>
	</c:if>
	
	<div class="container">
	
	<!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title">${session.id}</h4>
        </div>
        <div class="modal-body">
          <div class="form-group">
		      <label for="contents">댓글 :</label>
		      <textarea class="form-control" rows="5" id="updateContents" name="contents"></textarea>
		      <input type="hidden" id="cnum">
		    </div>
        </div>
        <div class="modal-footer">
		      <button class="btn btn-primary" id="updateBtn" data-dismiss="modal">댓글 수정</button>
          <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
	
	</div>
	
<a href="./${board}Update?num=${boardDTO.num}" class="btn btn-danger">Update</a>
<a href="./${board}Delete?num=${boardDTO.num}" class="btn btn-danger">Delete</a>

</div>

</body>
</html>