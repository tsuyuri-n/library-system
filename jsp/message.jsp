<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>完了メッセージ</title>

	<style>
		* {
		margin: 10px;
	}
	header {
		background-color: green;
		padding: 10px 5px;
	}
	h1 {
		color: white;
	}
	.btn {
		float: right;
		position: relative;
		bottom:10px;
		color: black;
		font-size: 20px;
		background-color: white;
		border-radius: 10px;
		padding: 7px 30px;
	}
  	#cfm {
    	float: right;
    	padding: 10px 30px;
    	font-size: 30px;
    	background-color: lightblue;
    	border-radius: 10px;
  	}
</style>

</head>
<body>

<header>
	<h1>手続き完了
    <a href="top.jsp"><button type="button" class="btn btn-success">TOPへ</button></a></h1>
</header>
    <br><br>
    <h2>${message}</h2>

<!--
	<c:forEach items="${list}" var="list">
    ${list.bookId} , ${list.title }<br>
	</c:forEach>
 -->
	<br><br>
 	<form action="/library/BookServlet">
		<button id="cfm" type="submit" name="action" value="goto">OK</button>
	</form>

</body>
</html>