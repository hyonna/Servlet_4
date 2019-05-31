<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
<title>Insert title here</title>
<script type="text/javascript">

	$(function() {
		$('#id').blur(function() {
			
			//1. xhttpRequest 생성
			var xhttp;
			
			if(window.XMLHttpRequest) { //최신 브라우저라면,
				
				xhttp = new XMLHttpRequest(); //객체 생성
				
			} else { //오래된 브라우저라면
				
				xhttp = new ActiveXObject("Microsoft.XMLHTTP"); //이 코드 작성
			}
			
			//2. 요청 정보 기록
			xhttp.open("POST", "../member/idCheck", true); //true 안넣으면 디폴트로 true
			xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			
			//3. 전송
			xhttp.send("id="+$('#id').val()); //POST방식일땐 파라미터를 여기에 넣기
			
			//4. response 전송
			xhttp.onreadystatechange = function() {
				if(this.readyState == 4 && this.status == 200) {
					
					//alert(this.responseText.trim()=='1'); //중복된 아이디 0, 사용가능한건 1
					
					if(this.responseText.trim()=='1') {
						$('#result').html('사용가능한 아이디 입니다');
						$('#result').css("color", "blue");
					} else {
						$('#result').html('사용 불가능한 아이디 입니다');
						$('#result').css("color", "red");
						
						$('#id').val("").focus();
						
					}
				}
				
			}
			
			
		});
		
	});


</script>
</head>
<body>

	<input type="text" id="id">
	
	<div id="result"></div> <!-- 사용가능 : 파랑색, 사용불가능 : 빨강색 -->

</body>
</html>