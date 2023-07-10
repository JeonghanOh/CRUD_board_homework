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
	<div>
		<form method="post" action="/searchresult">
			<input type="text">
			<input type="submit" value="검색">
		</form>
	</div>
	
	<div>
		<form method="post" action="/write">
			<input type="submit" value="게시글 작성">
		</form>
	</div>
	
	<table>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
		</tr>
	<c:forEach var="board" items="${boardList}" varStatus="status">
		<tr>
			<td>${status.count}</td>
			<td>${board.boardTitle}</td>
			<td>${board.userId}</td>
			<td>${board.boardDate}</td>
		</tr>
	</c:forEach>
	</table>
</body>
</html>