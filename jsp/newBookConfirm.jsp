<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>資料登録確認</title>
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

</style>

</head>
<body>
<header>
<h1>新規資料登録の確認</h1>
</header>
<form action="/library/BookServlet" method="post">
	<h2>下記の資料を登録します。</h2>
	<br>
<!-- <p>資料ID： ${candidate.bookId}</p>  -->
	<p>ISBN：${candidate.isbn}<br></p>
	<p>タイトル：${candidate.title}</p>
	<p>著者：${candidate.author}</p>
	<p>出版社：${candidate.publisher}</p>
	<p>出版日：${candidate.publishDate}</p>
	<p>分類：${candidate.categoryName}</p>
	<p>冊数：${amount}</p>



  	<button id="cfm" type="submit" name="action" value="newBookConf">登録</button>
    <button id="return" type="button" name="return" onclick="history.back()">戻る</button>

    <input type="hidden" name="go" value="add">
</form>

</body>
</html>