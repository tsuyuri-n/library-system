<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date"%>
<%@ page import="java.text.SimpleDateFormat"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会員トップ</title>

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
	table {
		border: 1px solid;
		border-collapse: collapse;
		text-align: center;
	}
	th, td {
		padding: 2px 20px;
		font-size: 20px;
	}
	#borrowbookid {
		border: none;
	}
	#return, #borrow {
		float: right;
		font-size: 20px;
		padding: 10px 20px;
		border-radius: 10px;
		background-color: lightgreen;
	}
	.button {
		margin: 10px 10px;
		padding: 10px 30px;
		background-color: lightblue;
		border-color: lightblue;
		border-radius: 10px;
		font-size: 20px;
	}
	#history {
		font-size: 20px;
	}
</style>

</head>
<body>

	<header>
	<h1>会員情報
    <a href="top.jsp"><button type="button" class="btn btn-success">TOPへ</button></a>
	</h1>
	</header>
	<br><br>
<%
	request.setAttribute("isDead", Boolean.valueOf(false));
 	Date now = new Date();
 	request.setAttribute("now", now);
 %>
	<form action="/library/BorrowReturnServlet?action=return" method="post">
    <h2>ID: ${member.memberId}</h2>
    <h2>${member.name}  さん</h2><br>
    <h3>貸出中</h3>

	<table border="1">	<tr><th></th><th>資料ID</th><th>資料名</th><th>貸出日</th><th>返却期限</th></tr>
<c:forEach items="${borrowingBooks}" var="borrowing">
    	<c:choose>
    	<c:when test="${now > borrowing.deadLine}">
    		<p class="borrowing" >
    		<tr><td><input id="borrowid" type="checkbox" name="borrowing" value="${borrowing.borrowId }"></td>
    			<td><font color="red">${borrowing.bookId}</font></td><td><font color="red">${borrowing.title}</font></td>
    			<td><font color="red">${borrowing.borrowed}</font></td><td><font color="red">${borrowing.deadLine}</font></td></tr>
    		</p>
    		<%
			request.setAttribute("isDead", Boolean.valueOf(true));
 			%>
		</c:when>
    	<c:otherwise>
    	<p class="borrowing" >
    		<tr><td><input id="borrowid" type="checkbox" name="borrowing" value="${borrowing.borrowId }"></td>
    			<td>${borrowing.bookId}</td><td>${borrowing.title}</td>
    			<td>${borrowing.borrowed}</td><td>${borrowing.deadLine}</td></tr>
    		</p>
    	</c:otherwise>
    	</c:choose>
	</c:forEach></table>

    <p><input id="return" type="submit" value="返却"></p>
    </form>

    <br><br>
	<h3>貸出</h3>

	<c:choose>
		<c:when test="${isDead eq false}">
    		<form action="/library/BorrowReturnServlet?action=borrow" method="post">
    		<c:forEach begin="1" end="${MAX_BORROW - borrowingBooks.size()}">
				<p class="borrow"> 資料ID: <input type="text" name="bookId"></p>
			</c:forEach>
    		<p><input type="submit" id="borrow" value="貸出"></p>
    		</form>
		</c:when>
		<c:otherwise>
			<h3>返却期限を過ぎた資料があるため貸し出しはできません</h3>
		</c:otherwise>
	</c:choose>
    </form>


    <br><br>

    <form action="/library/MemberServlet">
    <button class="button" type="submit" name="action" value="change">情報変更</button>
    <c:if test="${borrowingBooks.size()<1}">
    <button class="button" type="submit" name="action" value="delete">退会</button>
    </c:if>
    <button class="button" type="submit" name="action" value="history">履歴</button>
    <c:forEach items="${borrowedRecord}" var="item">
    <p id="history">${item.title}, ${item.returned}</p>
    </c:forEach>
	</form>

</body>
</html>