<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form method="post" action="/loginresult">
		<label>아이디</label>
		<input type="text" name="userId">
		<label>비밀번호</label>
		<input type="password" name="userPw">
		<input type="submit" value="제출">
	</form>
</body>
</html>