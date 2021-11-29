<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会員登録確認画面</title>

<style>
	* {
		margin: 10px;
	}
	form p {
		font-size: 20px;
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
<h1>会員登録の確認
<a href="top.jsp"><button type="button" class="btn">TOPへ</button></a></h1>
</header>



<form action="/library/MemberServlet?action=signUp" method="post">
    <p class="text">氏名: ${candidate.name}</p>
    <p class="text">住所: ${candidate.address}</p>
    <p class="text">TEL: ${candidate.tel}</p>
    <p class="text">Email: ${candidate.email}</p>
    <p class="text">生年月日: ${candidate.birth}</p>
   	<input id="cfm" type="submit" value="登録">
    <input id="return" type="button" value="戻る" onclick="history.back()"></p>
</form>

</body>
</html>