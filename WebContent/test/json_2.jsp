<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- jQuery library -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.0/jquery.min.js"></script>
<script type="text/javascript">

	$(function() {
		
		$('#btn').click(function() {
			$.get("http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=430156241533f1d058c603178cc3ca0e&targetDt=20190521", function(data) {
				
				data.boxOfficeResult.dailyBoxOfficeList.forEach(function(d) {
					var name = d.movieNm;
					var result = '<div>'+name+'</div>'
					$('#result').append(result);
				});
				
				
			});
		});
		
	});


</script>
</head>
<body>
<button id="btn">Click</button>
<div id="result"></div>

</body>
</html>