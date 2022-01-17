<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>

<form action="login"  method="post">
	<label>아이디 : <input type="text" name="id" required /></label>
	<label>비밀번호 : <input type="password" name="password" /></label>
	
	<input type="submit" value="로그인">
</form>


</body>
</html>
