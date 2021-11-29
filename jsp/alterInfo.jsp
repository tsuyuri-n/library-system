<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会員情報変更</title>

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
      #alt {
        float: right;
        margin-left: 20px;
        font-size: 30px;
        background-color: lightgreen;
        padding: 10px 20px;
        border-radius: 10px;

      }

      #top {
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
        font-size: 50px;
	}
	.inputtext {
		margin-left: 30px;
        margin-top: 10px;
        font-size: 30px;

	}

	.textarea

</style>

<body>
<header>
<h1>会員情報の変更
<a href="top.jsp"><button type="button" class="btn">TOPへ</button></a></h1>
</header>

    <br><br>
    <form action="/library/MemberServlet?action=alt" method="post">
    <p class="text">ID: ${nowInfo.memberId}</p>
    <p class="text">氏名: <input class="inputtext" type="text" name="name" placeholder="${nowInfo.name}"></p>
    <p class="text">住所: <input class="inputtext" type="text" name="address" placeholder="${nowInfo.address}" size="50"></p>
    <p class="text">TEL: <input class="inputtext" type="text" name="tel" placeholder="${nowInfo.tel}"></p>
    <p class="text">Email: <input class="inputtext" type="text" name="email" placeholder="${nowInfo.email}" size="50"></p>

    <input id="alt" type="submit" name="alterInfo" value="確認">
    <a href="member.jsp"><input id="top" type="button" value="戻る"></a>
    </form>

</body>
</html>