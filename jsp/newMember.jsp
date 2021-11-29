<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>会員登録画面</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<script src="https://ajaxzip3.github.io/ajaxzip3.js" charset="UTF-8"></script>
<script>
	function set_birthday(num) {
		for(var i = 1; i <= num; i++) {
			document.write("<option value=" + ('00' + i).slice(-2) +  ">" + i + "</option>")
		}
	}
	function set_year() {
		for(var i = 1900; i <= 2021; i++) {
			document.write("<option value=" + i + ">" + i + "</option>");
		}
	}
</script>

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
</style>

</head>
<body>
<header>
<h1>新規会員登録
<a href="top.jsp"><button type="button" class="btn">TOPへ</button></a></h1>
</header>
<br>

<form action="/library/MemberServlet?action=new" method="post">
<p class="text">氏名：<input type="text" name="name" size="10"></p>
<!-- ▼郵便番号入力フィールド(7桁) -->
<p class="text">〒：<input type="text" name="zip11" size="10" maxlength="8" onKeyUp="AjaxZip3.zip2addr(this,'','address','address');"></p>
<!-- ▼住所入力フィールド(都道府県+以降の住所) -->
<p class="text">住所：<input  type="text" name="address" size="60"></p>
<p class="text">TEL：<input  type="text" name="tel1" size="3"> - <input type="text" name="tel2" size="3"> - <input type="text" name="tel3" size="3"></p>
<p class="text">Email：<input  type="text" name="email" size="60"></p>
<p class="text">生年月日：
	<select name="year"><script>set_year()</script></select>年
	<select name="month"><script>set_birthday(12)</script></select>月
	<select name="date"><script>set_birthday(31)</script></select>日

<p><input type="hidden" name="joined"></p>
<input id="cfm" type="submit" name="confirm" value="確認">
</form>

</body>
</html>