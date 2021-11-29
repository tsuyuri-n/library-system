<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>資料変更確認</title>

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
	.inputtext {
		margin-left: 30px;
        margin-top: 10px;
        font-size: 30px;
	}

</style>

</head>
<body>
<header>
<h1>資料情報の変更確認
<a href="top.jsp"><button type="button" class="btn">TOPへ</button></a></h1>
</header>


<form action="/library/BookServlet?action=altConf" method="post">
<p class="text">ISBN番号 : ${nowBookInfo.isbn }</p>
<p class="text">タイトル:
    	<c:choose>
    	<c:when test="${empty candidate.title}">${nowBookInfo.title}</c:when>
    	<c:otherwise>${candidate.title}</c:otherwise>
    	</c:choose>
    </p>
    <p class="text">出版社:
    	<c:choose>
    	<c:when test="${empty candidate.publisher}">${nowBookInfo.publisher}</c:when>
    	<c:otherwise>${candidate.publisher}</c:otherwise>
    	</c:choose>
    </p>
    <p class="text">出版日:
    	<c:choose>
    	<c:when test="${empty candidate.publishDate}">${nowBookInfo.publishDate}</c:when>
    	<c:otherwise>${candidate.publishDate}</c:otherwise>
    	</c:choose>
    </p>
    <p class="text">著者:
    	<c:choose>
    	<c:when test="${empty candidate.author}">${nowBookInfo.author}</c:when>
    	<c:otherwise>${candidate.author}</c:otherwise>
    	</c:choose>
    </p>
    <p class="text">分類:
    	<c:choose>
    	<c:when test="${empty candidate.categoryCode}">${CCL[nowBookInfo.categoryCode].categoryName}</c:when>
    	<c:otherwise>${CCL[candidate.categoryCode].categoryName}</c:otherwise>
    	</c:choose>
    </p>

<input id="cfm" type="submit" name="altBookConf" value="確認">
<button id="return" type="button" name="return" onclick="history.back()">戻る</button>
<input type="hidden" name="go" value="alter">
</form>

</body>
</html>