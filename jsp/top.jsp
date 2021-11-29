<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>トップ</title>
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Kaisei+HarunoUmi:wght@700&display=swap" rel="stylesheet">

<style>
	header {
	 margin: 10px;
	}
	header h1 {
		text-align: center;
	}
	.serch {
		font-size: 30px;
		text-align: center;
	}
	#memberId {
		height: 50px;
		width: 400px;
		font-size: 30px;
	}
	#go {
		background-color: green;
		border-radius: 10px;
		margin-left: 20px;
		padding: 5px 20px;
	}
	#goImg {
		position: relative;
		top:5px;
		height: 30px;
		width: 35px;
	}
	.button {
		text-align: center;
	}
	.content {
		float: center;
		margin: 10px 20px;
		padding: 10px 20px;
		font-size: 30px;
		border-radius: 10px;
	}
	.contentimg {
		margin: 5px 10px;
		position: relative;
		top: 10px;
		height: 40px;
		width: 40px;
	}
	#newmember {
		background-color: lightgreen;
	}
	#boxreturn {
		background-color: orange;
	}
	#alterbook, #discard, #newbook {
		background-color: lightblue;
	}
	.logout {
		float: right;
		height: 40px;
		width: 150px;
		border-radius: 10px;
		font-size: 20px;
		border-color: lightblue;
		background-color: rgba(255, 255, 255, 0.5);
	}
</style>

</head>
<body>

<header >
<h1 style="font-family: 'Kaisei HarunoUmi', serif;color:#ffffff;background:#008000;">新宿図書館　情報管理システム</h1>
</header>


	<br><br>
	<form action="/library/MemberServlet?action=go" method="post">
	<div class="serch">
	    <input id="memberId" class="member" type="text" name="memberId" placeholder="ID/TEL">
	    <button id="go" class="member" type="submit" value="go"><img id="goImg" src="go.png"></button>
    </div>
	<br>
	<div class="button">
	      <p>
	      <a href="/library/newMember.jsp"><button class="content" id="newmember" type="button" name="newMember">会員登録<img class="contentimg" src="newmember.png"></button></a>
	      <a href="/library/boxReturn.jsp"><button class="content" id="boxreturn" type="button" name="boxReturn">一括返却<img class="contentimg" src="box.png"></button></a>
	      </p>
	      <p>
	      <a href="/library/newBook.jsp"><button class="content" id="newbook" type="button" name="newBook">資料登録<img class="contentimg" src="newbook.png"></button></a>
	      <a href="/library/discard.jsp"><button class="content" id="discard" type="button" name="discard">資料廃棄<img class="contentimg" src="discard.png"></button></a>
	      <a href="/library/alterBook.jsp"><button class="content" id="alterbook" type="button" name="alterBook">資料変更<img class="contentimg" src="alter.png"></button></a>
	      </p>
	</div>
	<br>
		<p><a href="login.jsp"><button class="logout" type="button" name="logout">ログアウト</button></a></p>
	</form>
</body>
</html>
