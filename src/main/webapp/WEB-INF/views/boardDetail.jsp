<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div><label>${board.boardTitle}</label></div>
	<div><label>${board.boardContent}</label></div>
	
	<c:if test="${member eq board.userId}">
		<form method="post" action="delete">
			<input type="hidden" name="boardId" value="${board.boardId}">
			<input type="submit" value="게시물 삭제">
		</form>
		
		<form method="post" action="update">
			<input type="hidden" name="boardId" value="${board.boardId}">
			<input type="submit" value="게시물 수정">
		</form>
	</c:if>
</body>
</html>