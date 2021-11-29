<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>確認メッセージ</title>
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
</style>

</head>
<body>
    <header>
<h1>返却完了
<a href="top.jsp"><button type="button" class="btn">TOPへ</button></a></h1>
</header>
    <br><br>
    <h2>${message}</h2>
    <br><br>
    <a href="boxReturn.jsp"><button type="button" class="cfm" id="cfm">続ける</button></a>
  </body>
</html>