<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/smarteditor2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<!-- head 안에 추가 -->
<script>

function save(){
	oEditors.getById["txtContent"].exec("UPDATE_CONTENTS_FIELD", []);  
    		//스마트 에디터 값을 텍스트컨텐츠로 전달
	var content = document.getElementById("smartEditor").value;
	alert(document.getElementById("txtContent").value); 
    		// 값을 불러올 땐 document.get으로 받아오기
	return; 
}

</script>
</head>
<body>
	<form method="post" action="/writeresult">
		<c:if test="${board eq null}">
			<input type="text" name="boardTitle">
			<textarea id="txtContent" name="boardContent" rows="10" cols="100" style="width: 100%;"></textarea>
			<input type="hidden" name="userId" value="${member}">
			<input type="hidden" name="boardId" value="0">
		</c:if>
		<c:if test="${board ne null}">
			<input type="text" name="boardTitle" value="${board.boardTitle}">
			<textarea id="txtContent" name="boardContent" rows="10" cols="100" style="width: 100%;">${board.boardContent}</textarea>
			<input type="hidden" name="userId" value="${member}">
			<input type="hidden" name="boardId" value="${board.boardId}">
			<input type="hidden" name="boardBlock" value="${board.boardBlock}">
			<input type="hidden" name="boardFilePath" value="${board.boardFilePath}">
		</c:if>
		
		<div id="se2_sample" style="margin:10px 0;">
			<input type="submit" onclick="save();" value="제출">
		</div>
	</form>

    <script id="smartEditor" type="text/javascript"> 
	var oEditors = [];
	nhn.husky.EZCreator.createInIFrame({
	    oAppRef: oEditors,
	    elPlaceHolder: "txtContent",  //textarea ID 입력
	    sSkinURI: "/smarteditor2/SmartEditor2Skin.html",  //martEditor2Skin.html 경로 입력
	    fCreator: "createSEditor2",
	    htParams : { 
	    	// 툴바 사용 여부 (true:사용/ false:사용하지 않음) 
	        bUseToolbar : true, 
		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음) 
		bUseVerticalResizer : false, 
		// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음) 
		bUseModeChanger : false 
	    }
	});
</script>

</body>
</html>