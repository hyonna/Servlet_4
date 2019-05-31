<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<c:import url="../temp/bootstrap.jsp"/>
<script type="text/javascript" src="/Servlet_4/se2/js/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript">
var oEditors = [];

$(function() {
	
	// editor 
	
	nhn.husky.EZCreator.createInIFrame({
        oAppRef: oEditors,
        elPlaceHolder: "contents",
        //SmartEditor2Skin.html 파일이 존재하는 경로
        sSkinURI: "/Servlet_4/se2/SmartEditor2Skin.html",  
        htParams : {
            // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
            bUseToolbar : true,             
            // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
            bUseVerticalResizer : true,     
            // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
            bUseModeChanger : true,         
            fOnBeforeUnload : function(){
                 
            }
        }, 
        fOnAppLoad : function(){
            //기존 저장된 내용의 text 내용을 에디터상에 뿌려주고자 할때 사용
            oEditors.getById["contents"].exec("PASTE_HTML", [""]);
        },
        fCreator: "createSEditor2"
    });
    
    //저장버튼 클릭시 form 전송
    $("#save").click(function(){
        oEditors.getById["contents"].exec("UPDATE_CONTENTS_FIELD", []);
        $("#frm").submit();
    });    

	
	
	////////////////////////////////////////////
	var maxAppend = 0;
	var d1=	0;
	
	$('#add').click(function() {
		d1++;
		if(maxAppend < 5) {
			
			$('#addFile').append('<input type="file" class="form-control" id="" name="f'+ d1 +'"><span class="deL">X</span>');
			/* div 에 아이디를 주고 span title에도 동일한 값을 주면 부모 자식 관계 */
		maxAppend++;
		} else {
			
			alert('파일은 5개 미만');
			
		}
	});
	
			$('#addFile').on('click', '.deL', function(e) {
				$(this).prev().remove();
				$(this).remove();
				maxAppend--;
			});
	
	
});


</script>
<style type="text/css">

.deL {

	color : red;
	cursor: pointer;


}

#contents {

	width: 99.7%;

}

</style>
</head>
<body>
<c:import url="../temp/header.jsp"/>

<div class="container">
	
	<h1>${board}&nbsp;Write Page</h1>

 <form action="./${board}Write" id="frm" method="post" enctype="multipart/form-data">
<!--  데이터가 크기 때문에 메소드는 포스트로, 파일 업로드시  enctype=multipart/form-data 필수 -->
 
    <div class="form-group">
      <label for="title">title :</label>
      <input type="text" class="form-control" id="title" name="title">
    </div>
    
    <div class="form-group">
      <label for="name">작성자 :</label>
      <input type="text" class="form-control" id="name" name="name">
    </div>
    
    <div class="form-group">
      <label for="contents">Contents :</label>
      <textarea class="form-control" rows="10" id="contents" name="contents"></textarea>
    </div>
    
    <div class="form-group" id="addFile">
      <label for="file">File :</label>
      
    </div>
    
     <div class="form-group">
    <input type="button" value="Add" class="btn btn-primary" id="add">
    </div>
    
    <input type="button" id="save" value="Write" class="btn btn-danger">
  </form>

</div>

</body>
</html>