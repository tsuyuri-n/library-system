<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会員情報変更確認画面</title>

<style>
      * {
      	margin: 10px;
      }

	header {
		padding: 5px 10px;
		background-color: green;
	}

	h1 {
		color: white;
	}

      #cfm {
        float: right;
        margin-left: 20px;
        font-size: 30px;
        background-color: lightgreen;
        padding: 10px 20px;
        border-radius: 10px;
      }

      #return {
        float: right;
        margin-left: 20px;
        font-size: 30px;
        background-color: light;
		padding: 10px 20px;
        border-radius: 10px;

      }

      .btn {
        float: right;
        background-color: white;
        border-color: black;
        border-radius: 10px;
        margin-left: 20px;
        margin-top: -5px;
        font-size: 20px;
		padding: 10px 20px;
      }

	.text {
		margin-left: 50px;
        margin-top: 10px;
        font-size: 50px;
	}
	.inputtext {
		margin-left: 30px;
        margin-top: 10px;
        font-size: 30px;

	}

</style>

</head>
<body>
<header>
<h1>会員情報変更の確認
<a href="top.jsp"><button type="button" class="btn">TOPへ</button></a></h1>
</header>
<br><br>
<form action="/library/MemberServlet?action=altConf" method="post">
    <p class="text">氏名:
    	<c:choose>
    	<c:when test="${empty candidate.name}">${nowInfo.name}</c:when>
    	<c:otherwise>${candidate.name}</c:otherwise>
    	</c:choose>
    </p>
    <p class="text">住所:
    	<c:choose>
    	<c:when test="${empty candidate.address}">${nowInfo.address}</c:when>
    	<c:otherwise>${candidate.address}</c:otherwise>
    	</c:choose>
    </p>
    <p class="text">TEL :
    	<c:choose>
    	<c:when test="${empty candidate.tel}">${nowInfo.tel}</c:when>
    	<c:otherwise>${candidate.tel}</c:otherwise>
    	</c:choose>
    </p>
    <p class="text">Email :
    	<c:choose>
    	<c:when test="${empty candidate.email}">${nowInfo.email}</c:when>
    	<c:otherwise>${candidate.email}</c:otherwise>
    	</c:choose>
    </p>
    <p><button id="cfm" type="submit" name="confirm">確認</button>
    <input id="return" type="button" value="戻る" onclick="history.back()"></p>

</form>

</body>
</html>