<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>退会確認</title>

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
	#bye {
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

	.text {
		margin-left: 50px;
        margin-top: 30px;
        font-size: 50px;

</style>

</head>
<body>
<header>
<h1>退会確認</h1>
</header>

	<h2 class="text">${member.name} さんの退会処理を行いますか？</h2>
	<form action="/library/MemberServlet" method="post">
	<input id="return" type="button" name="return" value="戻る" onclick="history.back()">
	<input id="bye" type="button" name="delete" value="退会" onclick="if(confirm('よろしいですか？')) {submit();}">
	<input type="hidden" name="action" value="out">
	</form>

</body>
</html>