package library.servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import library.bean.BookBean;
import library.bean.CategoryCodeBean;
import library.service.BookService;
import library.util.LibraryUtility;

/**
 * Servlet implementation class BookServlet
 */
@WebServlet("/BookServlet")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			request.setCharacterEncoding("UTF-8");
			// requestパラメータの取得
			String action = request.getParameter("action");

			if (action.equals("newBook")) {
				// newBook.jsp から newBookConfirm.jsp にフォワード
				HttpSession session = request.getSession(true);
				String isbn = request.getParameter("isbn");
				Integer amount = Integer.parseInt(request.getParameter("amount"));
				BookService service = new BookService();
				BookBean candidate = null;


				// 資料目録に新規登録する場合（蔵書内に同一ISBN番号が存在しない）
				String title = request.getParameter("title");
				String author = request.getParameter("author");
				String publisher = request.getParameter("publisher");
				String stringPublishDate = request.getParameter("publishdate");
				Date publishDate = LibraryUtility.createSQLDate(stringPublishDate);
				int categoryCode = Integer.parseInt(request.getParameter("categorycode"));
//				CategoryCodeBean category = service.getCategoryCodeBean(categoryCode);
//				String categoryName = category.getCategoryName();
				CategoryCodeBean category = service.getCategoryCodeBean(categoryCode);
System.out.println("isbn : " + isbn);
System.out.println("title : " + title);
				candidate = new BookBean(isbn, title, author, publisher, publishDate, categoryCode, category.getCategoryName());
				 System.out.println("candidate : " + candidate);

				// スコープにBookBeanISBN番号と冊数を登録
				session.setAttribute("candidate", candidate);
				session.setAttribute("isbn", isbn);
				session.setAttribute("amount", amount);
				gotoPage(request, response, "newBookConfirm.jsp");
				return;
			} else if (action.equals("newBookConf")) {
				// newBookConfirm.jsp から message.jsp にフォワード
				HttpSession session = request.getSession(false);
				BookBean candidate = (BookBean) session.getAttribute("candidate");
				String isbn = (String) session.getAttribute("isbn");
				int amount = (Integer) session.getAttribute("amount");
				BookService service = new BookService();
				Date arrival = LibraryUtility.createSQLDate();

				if (service.searchBookCatalog(isbn) == null) {
					// 資料目録に新規登録する場合（蔵書内に同一ISBN番号が存在しない）
					System.out.println("candidate : " + candidate);
					BookBean book = service.addBookCatalog(isbn, candidate.getTitle(), candidate.getCategoryCode(), candidate.getAuthor(), candidate.getPublisher(), candidate.getPublishDate());

					// スコープにBookBeanを登録
					session.setAttribute("book", book);
				}

				List<BookBean> list = service.addBookLedger(isbn, amount, arrival);
				// スコープに資料台帳に追加した資料リストを登録
				session.setAttribute("list", list);
				String go = request.getParameter("go");
				session.setAttribute("go", go);
				session.setAttribute("message", "新規資料を追加しました。");
				gotoPage(request, response, "message.jsp");
				return;

//			} else if (action.equals("addComp")) {
//				// message.jsp から newBook.jsp にフォワード
//				List<BookBean> books = (List<BookBean>) request.getAttribute("list");
//				request.setAttribute("message", "新規資料を登録しました。");
//				gotoPage(request, response, "/newBook.jsp");
//				return;

			} else if (action.equals("discard")) {
				// discard.jsp から discardConfirm.jsp にフォワード
				HttpSession session = request.getSession(true);
				String[] stringIdList = request.getParameterValues("list");
//				List<Integer> idList = new ArrayList<Integer>();
				String cause = request.getParameter("cause");

				List<BookBean> beanList = new ArrayList<BookBean>();
				BookService service = new BookService();

				for (String discardId : stringIdList) {
					if (!(discardId.equals("")) && discardId != null) {
						// IDが入力されている場合
						beanList.add(service.searchBookById(Integer.parseInt(discardId)));
					} else {
						continue;
					}
				}

				if (cause.equals("その他")) {
					cause += " : " + request.getParameter("causeOthers");
				}

				// スコープにリストを登録
				session.setAttribute("beanList", beanList);
				session.setAttribute("cause", cause);
				gotoPage(request, response, "/discardConfirm.jsp");
				return;
			} else if (action.equals("discardConf")) {
				// discardConfirm.jsp から message.jsp
				HttpSession session = request.getSession(false);
				List<BookBean> discardBookList = (List<BookBean>) session.getAttribute("beanList");
				List<Integer> discardIdList = new ArrayList<Integer>();
				for (BookBean book : discardBookList) {
					discardIdList.add(book.getBookId());
				}

				String cause = (String) session.getAttribute("cause");
//				List<BookBean> discardBookList = new ArrayList<BookBean>();
				BookService service = new BookService();
//System.out.println("discardIdList : " + discardIdList);
				discardBookList = service.discardBooks(discardIdList, cause);
				session.setAttribute("list", discardBookList);
				session.setAttribute("message", "資料を廃棄しました。");
				String go = request.getParameter("go");
				session.setAttribute("go", go);
				gotoPage(request, response, "/message.jsp");
				return;

			} else if (action.equals("search")) {
				// alterBook.jsp から alterBook.jsp にフォワード
				HttpSession session = request.getSession(true);
				BookService service = new BookService();
				String isbn = request.getParameter("isbn");
				BookBean nowBookInfo = service.searchBookCatalog(isbn);
//System.out.println("candidate : " + candidate);
				if (nowBookInfo == null) {
					request.setAttribute("errMessage", "該当データが見つかりません。");
				}

				// スコープにBookBeanを登録
				session.setAttribute("nowBookInfo", nowBookInfo);
				gotoPage(request, response, "/alterBook.jsp");
				return;
			} else if (action.equals("alter")) {
				// alterBook.jsp から alterBookConfirm.jsp にフォワード
				HttpSession session = request.getSession(false);
				BookBean nowBookInfo = (BookBean) session.getAttribute("nowBookInfo");
//				BookBean candidate = service.searchBookCatalog(nowInfo.getIsbn());
				String title = request.getParameter("title");
				Integer categoryCode = Integer.parseInt(request.getParameter("categoryCode"));
				String author = request.getParameter("author");
				String publisher = request.getParameter("publisher");
				String stringPublishDate = request.getParameter("publishDate");
				Date publishDate = (stringPublishDate == null || stringPublishDate.length() == 0) ? null : LibraryUtility.createSQLDate(stringPublishDate);

				// スコープにBookBeanを登録
				BookBean candidate = new BookBean(nowBookInfo.getIsbn(), title, author, publisher, publishDate, categoryCode);
				session.setAttribute("nowBookInfo", nowBookInfo);
				session.setAttribute("candidate", candidate);
				System.out.println("candidate : " + candidate);
				gotoPage(request, response, "/alterBookConfirm.jsp");
				return;
			} else if (action.equals("altConf")) {
				// alterBookConfirm.jsp から message.jsp にフォワード
				HttpSession session = request.getSession(false);
				BookBean candidate = (BookBean) session.getAttribute("candidate");

				BookService service = new BookService();
				String title = isNull(candidate.getTitle());
				String author = isNull(candidate.getAuthor());
				String publisher = isNull(candidate.getPublisher());
				Date publishDate = candidate.getPublishDate();
				Integer categoryCode = candidate.getCategoryCode();
				BookBean book = service.changeBookInfo(candidate.getIsbn(), title, author, publisher, publishDate, categoryCode);

				// スコープにBookBeanを登録
//				session.setAttribute("book", book);
				String go = request.getParameter("go");
				session.setAttribute("go", go);
				session.setAttribute("message", "資料情報を変更しました。");
				gotoPage(request, response, "/message.jsp");
				return;
//			} else if (action.equals("altComp")) {
//				// message.jsp から alterBookInfo.jsp にフォワード
//				BookBean book = (BookBean) request.getAttribute("book");
//
//				// スコープにBookBeanを登録
//				request.setAttribute("book", book);
//				gotoPage(request, response, "/alterBookInfo.jsp");
//				return;
			}else if (action.equals("goto")) {
				// message.jsp から それぞれの処理画面にフォワード
				HttpSession session = request.getSession(false);
				String go = (String) session.getAttribute("go");

				if (go.equals("add")) {
					session.removeAttribute("candidate");
					session.removeAttribute("isbn");
					session.removeAttribute("amount");
					session.removeAttribute("list");
					session.removeAttribute("go");
					session.removeAttribute("message");
					gotoPage(request, response, "newBook.jsp");
					return;
				} else if (go.equals("discard")) {
					session.removeAttribute("beanList");
					session.removeAttribute("cause");
					session.removeAttribute("list");
					session.removeAttribute("go");
					session.removeAttribute("message");
					gotoPage(request, response, "discard.jsp");
					return;
				} else {
					session.removeAttribute("nowBookInfo");
					session.removeAttribute("candidate");
					session.removeAttribute("go");
					session.removeAttribute("message");
					gotoPage(request, response, "alterBook.jsp");
					return;
				}

			} else {
				request.setAttribute("message", "正しく操作してください。");
				gotoPage(request, response, "/errInternal.jsp");
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "内部エラーが発生しました。");
			gotoPage(request, response, "/errInternal.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void gotoPage(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher(page);
		rd.forward(request, response);
	}

	public String isNull(String arg) throws ServletException {
		if (arg.equals(null) || arg.equals("")) {
			arg = null;
		}
		return arg;
	}

}
