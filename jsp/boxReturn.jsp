<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>一括返却</title>
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

      body {
        margin: 10px;
      }
</style>

</head>
<body>
    <header>
<h1>一括返却
<a href="top.jsp"><button type="button" class="btn">TOPへ</button></a></h1>
</header>
返却処理を行いたい資料の、資料IDを入力してください
<form action="/library/BoxReturnServlet?action=confirm" method="post">
	<c:forEach var="i" begin="1" end="20" step="1">
		<c:choose>
			<c:when test="${i % 5 eq 0}"><input name="book${i}" size="5" type="text"  value=""><br></c:when>
			<c:otherwise><input name="book${i}" size="5" type="text"  value=""></c:otherwise>
		</c:choose>
	</c:forEach>


<input id="cfm" type="submit" name="confirm" size="10" value="確認"><br>
</form>

</body>
</html>