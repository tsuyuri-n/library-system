<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>資料変更</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

<style>
	* {
		margin: 10px;
	}
	.btn {
		float: right;
	}
	.serch {
		margin-left: 10px;
        background-color: green;
        color: white;
        padding: 10px 20px;
        border-radius: 10px;
		position: relative;
		top: -10px;
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
        margin-top: 0px;
        font-size: 20px;
		padding: 10px 20px;
      }

	.text {
		margin-left: 50px;
        margin-top: 10px;
        font-size: 20px;
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
<h1>資料情報の変更
<a href="top.jsp"><button type="button" class="btn">TOPへ</button></a></h1>
</header>

<form action="/library/BookServlet" method="post">
     <p class="text">ISBN番号 :
        <input class="inputtext" type="text" name="isbn" size="20" placeholder="${nowBookInfo.isbn}">
        <input class="serch" type="submit"  value="検索">
        <input type="hidden" name="action" value="search">
    </p>
</form>
<h2>${errMessage }</h2>

<form action="/library/BookServlet?action=alter" method="post">
    <p class="text">タイトル: <input class="inputtext" type="text" name="title" placeholder="${nowBookInfo.title}" size="40"></p>
    <p class="text">出版社: <input class="inputtext" type="text" name="publisher" placeholder="${nowBookInfo.publisher}"></p>
    <p class="text">出版日: <input class="inputtext" type="text" name="publishDate" placeholder="${nowBookInfo.publishDate}"></p>
    <p class="text">著者: <input class="inputtext" type="text" name="author" placeholder="${nowBookInfo.author}" size="40"></p>
    <p class="text" id=categorycode>分類：
    <select name="categoryCode">
		<c:forEach items="${CCL}" var="category">
			<option value="${category.categoryCode}" ${nowBookInfo.categoryCode eq category.categoryCode ? "selected" : "" }>${category.categoryName}</option>
		</c:forEach>
	</select><br>
	</p>

    <input id="cfm" type="submit" name="altBook" value="変更">
	<input id="return" type=button value="戻る" onclick="history.back()">
</form>

</body>
</html>