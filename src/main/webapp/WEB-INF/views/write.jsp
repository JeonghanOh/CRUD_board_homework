<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="/writeresult">
		<div><input type="text" name="boardTitle"></div>
    	<div><textarea rows="20" cols="90" name="boardContent" id="boardContent"></textarea></div>
    	<input type="hidden" name="userId" value="${member}">
    	<div><input type="submit" value="게시글 등록"></div>
    </form>
</body>
</html>