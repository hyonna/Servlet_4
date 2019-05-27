<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript">

window.onload = function() {

	var btn = document.getElementById('btn');
	var num = document.getElementById('num');
	
	/* 선택자.addEventListener('이벤트명', 실행할 익명함수선언 or 실행할 함수 호출) */
	
	/* btn.onclick = function() {
		alert(num);
	}; */
	
	btn.addEventListener('click', function() {
		
		//../notice/noticeSelect
		
		var xhttp;
		
		if(window.XMLHttpRequest) { //최신 브라우저라면,
			
			xhttp = new XMLHttpRequest(); //객체 생성
			
		} else { //오래된 브라우저라면
			
			xhttp = new ActiveXObject("Microsoft.XMLHTTP"); //이 코드 작성
		}
		
		xhttp.onreadystatechange = function() {

			if(this.readyState == 4 && this.status == 200) { //readyState = 갖고왔는지 / status = 에러인지 아닌지
				
				alert(this.responseText);
				
			}
			
		};
		
		xhttp.open("GET", "../notice/noticeSelect?num="+num.value, true); //open(method, url, async)
		xhttp.send(); //GET방식의 send              ▲ 파라미터를 넘겨준다.
		
		
		
	});
	
	
	
}



</script>
</head>
<body>

<input type="text" id="num">
<button id="btn">Click</button>

</body>
</html>