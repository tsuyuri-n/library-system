<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>

<style>
	* {
		margin-top: 20px;
	}
	h1 {
		color: lightgreen;
		text-align: center;
	}
	table{
		margin: auto;
		background-color: lightgreen;
		border-radius: 10px;
		padding: 1em 5em;
		font-size: 30px;
	}
	table td {
		padding: 10px 20px;
	}
	#login {
		font-size: 30px;
		float: right;
	}

</style>

<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Kaisei+HarunoUmi:wght@700&display=swap" rel="stylesheet">

</head>
<body>



<h1 style="font-family: 'Kaisei HarunoUmi', serif;color:#ffffff;background:#008000;">新宿図書館　情報管理システム</h1>
<form action="/library/LoginServlet" method="post">
<table>
	<tr>
		<td>ユーザID<br><input type="text" style="font-size:30px" name="user"></td>

	</tr>
	<tr>
		<td>Password<br><input type="password"style="font-size:30px" name="pass"></td>
	</tr>
	<tr>
		<td><input id="login" type="submit" value="ログイン"></td>
	</tr>
</table>
<input type="hidden" name="action" value="login">
</form>

</body>
</html>