<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import ="java.util.ArrayList,java.util.List,library.bean.BorrowReturnBean" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>一括返却確認</title>

<link rel="stylesheet" href="loading.css">

<style>
 	* {
		margin: 10px;
	}
	.btn {
		float: right;
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
        font-size: 20px;
	}

</style>

</head>
<header>
<h1>資料の一括返却
<a href="top.jsp"><button type="button" class="btn">TOPへ</button></a></h1>
</header>


<div id="loader-bg" class="is-hide">
    <div id="loader" class="is-hide">
        <p>
            <img src="loading.gif"><br>
            NOW LOADING
        </p>
    </div>
</div>
<!--コンテンツ-->
<div id="wrap">

<c:forEach var="list" items="${returnList}">
<c:out value="${list.bookId}"/>:<c:out value="${list.title}"/><br>
</c:forEach>
    <a href="/library/BoxReturnServlet?action=return"><button id="cfm" type="button" class="btn btn-success">返却</button></a>
	<a href="boxReturn.jsp"><button id="return" type="button" class="btn btn-success">戻る</button></a>

</div>



<script type="text/javascript" src="loading.js"></script>

</body>
</html>