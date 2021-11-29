<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>資料廃棄画面</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

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


      .bookID {
        margin-left: 5px;
        margin-bottom: 3px;
      }
      body {
        margin: 10px;
      }
</style>

</head>
<body>
<header>
<h1>資料廃棄
<a href="top.jsp"><button type="button" class="btn">TOPへ</button></a></h1>
</header>

    <form action="/library/BookServlet?action=discard" method="post">
    <br><br>
    <h3>資料ID:</h3>

	<c:forEach var="i" begin="1" end="20" step="1">
		<c:choose>
			<c:when test="${i % 5 eq 0}"><input class="bookID" size="5" type="text" name="list" value=""><br></c:when>
			<c:otherwise><input class="bookID" size="5" type="text" name="list" value=""></c:otherwise>
		</c:choose>
	</c:forEach>
    <br>

    <div class="text">
    備考：<input class="cause" type="radio" name="cause" value="劣化" checked>劣化
          <input  class="cause" type="radio" name="cause" value="紛失">紛失
          <input  class="cause" type="radio" name="cause" value="その他">その他
    <br>
    その他理由：<input type="text" name="causeOthers" rows="1" cols="50">
    </div>

    <input id="cfm" type="submit" name="discard" value="廃棄">
    </form>

</body>
</html>