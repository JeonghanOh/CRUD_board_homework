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
			<input type="text" name="query">
			<input type="submit" value="검색">
		</form>
	</div>
	
	<c:if test="${member ne null}">
	<div>
		<form method="post" action="/write">
			<input type="submit" value="게시글 작성">
		</form>
	</div>
	</c:if>
	
	<form method="post" action="/toblock">
	<table>
		<tr>
			<th>체크</th>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
		</tr>
	<c:forEach var="board" items="${boardList}" varStatus="status">
		<tr>
			<td><input type="checkbox" name="block" value="${board.boardId}"></td>
			<td>${status.count}</td>
			<td><a href="/boardDetail/${board.boardId}">${board.boardTitle}</a></td>
			<td>${board.userId}</td>
			<td>${board.boardDate}</td>
		</tr>
	</c:forEach>
	</table>
	<input type="submit" value="차단하기">
	</form>
	
	<div class="pagination">
		<ul class="page">
			<c:forEach begin="${startpage}" end="${endpage}" var="pageNum">
				<c:choose>
					<c:when test="${pageNum != nowpage}">
						<li><a href="/board?page=${pageNum - 1}">${pageNum}</a></li>
					</c:when>
					<c:otherwise>
						<li><a href="/board?page=${pageNum - 1}"><strong style="color:red">${pageNum}</strong></a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</ul>
	</div>
</body>
</html>