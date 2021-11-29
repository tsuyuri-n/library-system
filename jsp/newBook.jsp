<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>新規資料登録</title>
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
<h1>新規資料登録
<a href="top.jsp"><button type="button" class="btn">TOPへ</button></a></h1>
</header>

     <p>
      <label>ISBNコード
        <input id="isbncode" type="text" size="20" style="ime-mode: disabled;">
      </label>
      <button id="btn">検索</button>
    </p>
    <label id="errorMessage" style="color:red"></label>
<hr>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script>
  $(function(){
	  $('#btn').on('click', function() {
	    var isbn = $('#isbncode').val().trim().replace("-", "");

	    // ISBNコード入力チェック
	    $('#errorMessage').html("");
	    if (isbn=="") {
	      $('#errorMessage').html("ISBNコードが入力されていません。");
	    } else {

	      // 入力されたISBNでWebAPIに書籍情報をリクエスト
	      $.ajax({
	        url: 'https://api.openbd.jp/v1/get?isbn=' + isbn,
	        dataType : 'json',
	      }).done(function(data) {
	        if (data != null) {
	          setData(data);
	        } else {
	          $('#errorMessage').html("該当するデータが見つかりませんでした。");
	        }
	      }).fail(function(data) {
	        $('#errorMessage').html("通信に失敗しました。");
	      });
	    }
	  });

	  // データ取得が成功したときの処理
	  function setData(data) {
		 $('#isbn').html("ISBN：<input type='text' name='isbn' size='20' value='"+$('#isbncode').val()+"'><br> ");          // 書籍名
	    //取得したデータを各HTML要素に代入
	    if (data[0] != null) {
	      $('#title').html("タイトル：<input type='text' name='title' size='70' value='"+data[0].summary.title+"'><br> ");          // 書籍名
	      $('#publisher').html("出版社：<input type='text' name='publisher' size='20' value='"+data[0].summary.publisher+"'><br>");  // 発売元出版社
	      $('#pubdate').html("出版日：<input type='date' name='publishdate' size='20' value='"+data[0].summary.pubdate.substring(0,4)+"-"+data[0].summary.pubdate.substring(4,6)+"-"+data[0].summary.pubdate.substring(6,8)+"'><br>");      // 発売日
	      $('#author').html("著者：<input type='text' name='author' size='40' value='"+data[0].summary.author.replace(/／著/g,"")+"'><br>");        // 著者
	    } else {
	      $('#errorMessage').html("該当するデータが見つかりませんでした。");
	    }
	  }
	});
  </script>

<%
        int num[] = new int[20];//一気に同じ本を何冊まで作れるか
        // 配列に代入
        for(int i = 1; i <= 20; i++){
            num[i - 1] = i;
        }
        request.setAttribute("list", num);
  %>


<form action="/library/BookServlet?action=newBook" method="post">
<p id=isbn>ISBN：<input type="text" name="isbn" size="20"><br></p>
<p id=title>タイトル：<input type="text" name="title" size="70"><br></p>
<p id=author>著者：<input type="text" name="author" size="40"><br></p>
<p id=publisher>出版社：<input type="text" name="publisher" size="20"><br></p>
<p id=pubdate>出版日：<input type="text" name="publishdate" size="20"><br></p>
<p id=categorycode>分類：<select name="categorycode">
<option hidden>選択してください</option>
<c:forEach items="${CCL}" var="category">
<option value="${category.categoryCode}">${category.categoryName}</option>
</c:forEach></select><br></p>
<p id=amount>冊数：<select name="amount">
<c:forEach items="${list}" var="list">
<option value="${list}">${list}</option>
</c:forEach></select><br></p>


<input id="cfm" type="submit" name="confirm" size="10" value="確認"><br>
</form>

</body>
</html>