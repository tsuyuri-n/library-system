<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>資料廃棄確認</title>

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
<body>
<header>
<h1>資料情報の変更確認
<a href="top.jsp"><button type="button" class="btn">TOPへ</button></a></h1>
</header>

    <h1>廃棄しますか？</h1>

    <c:forEach items="${beanList}" var="list">
    	<p class="text">${list.bookId}, ${list.title}</p>
    </c:forEach>
    <p class="text">廃棄理由：${cause }</p>
    <br>

	<form action="/library/BookServlet">
    <button id="cfm" type="submit" name="action" value="discardConf">廃棄</button>
    <button id="return"  type="button" name="return" onclick="history.back()">戻る</button>
    <input type="hidden" name="go" value="discard">
    </form>

</body>
</html>