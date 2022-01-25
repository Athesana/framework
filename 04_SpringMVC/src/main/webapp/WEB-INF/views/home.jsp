<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
<c:set var="path" value="${ pageContext.request.contextPath }"/>

<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is ${serverTime}. </P>

<p>
	<a href="${ path }/board/list">
		게시글 조회
	</a>
</p>

<c:if test="${ empty loginMember }">
	<form action="${ path }/login"  method="post">
		<label>아이디 : <input type="text" name="id" required /></label>
		<label>비밀번호 : <input type="password" name="password" /></label>
		<input type="button" onclick="location.href='${ path }/member/enroll'" value="회원가입">
		<input type="submit" value="로그인">
	</form>
</c:if>

<c:if test="${ !empty loginMember }">
	<a href="${ path }/member/myPage">
		${ loginMember.name }
	</a>님, 안녕하세요.
	
	<form action="${ path }/logout" method="get">
		<button type="submit">로그아웃</button>
	</form>
</c:if>

</body>
</html>
