<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*" %>
<%@ page import="library.bean.BorrowReturnBean" %>
<%@ page import="library.util.LibraryUtility" %>

<%
	ArrayList<BorrowReturnBean> list = new ArrayList<BorrowReturnBean>();
	// BorrowReturnBean(title, borrowId, memberId, bookId, borrowed, deadLine, returned);
	list.add(new BorrowReturnBean("javaベーシック教本", 1001, 101, 11, LibraryUtility.createSQLDate("2000-01-01"), LibraryUtility.createSQLDate("2000-01-10"), LibraryUtility.createSQLDate("2000-01-02")));
	list.add(new BorrowReturnBean("javaマスター教本", 1002, 101, 12, LibraryUtility.createSQLDate("2000-01-01"), LibraryUtility.createSQLDate("2000-01-10"), LibraryUtility.createSQLDate("2000-01-10")));
	list.add(new BorrowReturnBean("SQL教本", 1003, 101, 13, LibraryUtility.createSQLDate("2000-01-01"), LibraryUtility.createSQLDate("2000-01-10"), null));
	list.add(new BorrowReturnBean("歴史教科書", 1004, 102, 14, LibraryUtility.createSQLDate("2000-01-02"), LibraryUtility.createSQLDate("2000-01-11"), LibraryUtility.createSQLDate("2000-01-05")));
	list.add(new BorrowReturnBean("小説１", 1005, 103, 15, LibraryUtility.createSQLDate("2000-01-02"), LibraryUtility.createSQLDate("2000-01-11"), LibraryUtility.createSQLDate("2000-01-04")));
	list.add(new BorrowReturnBean("小説2", 1006, 103, 16, LibraryUtility.createSQLDate("2000-01-02"), LibraryUtility.createSQLDate("2000-01-11"), null));
	list.add(new BorrowReturnBean("javaベーシック教本", 1007, 104, 11, LibraryUtility.createSQLDate("2000-01-03"), LibraryUtility.createSQLDate("2000-01-013"), null));
	list.add(new BorrowReturnBean("小説3", 1008, 105, 17, LibraryUtility.createSQLDate("2000-01-03"), LibraryUtility.createSQLDate("2000-01-13"), LibraryUtility.createSQLDate("2000-01-09")));
	list.add(new BorrowReturnBean("料理本", 1009, 106, 18, LibraryUtility.createSQLDate("2000-01-04"), LibraryUtility.createSQLDate("2000-01-14"), null));

	request.setAttribute("borrowBooks", list);
%>

<jsp:include page="bookList.jsp"></jsp:include>