package library.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import library.bean.BookBean;
import library.bean.CategoryCodeBean;
import library.dao.BookDAO;
import library.util.LibraryUtility;

public class BookService {
	// ISBN番号による蔵書検索
	public BookBean searchBookCatalog(String isbn) throws ServiceException {
		try {
			BookDAO dao = new BookDAO();
			return dao.getBookAllByIsbn(isbn);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	// 資料目録に資料情報を登録
	public BookBean addBookCatalog(String isbn, String title, int categoryCode, String author, String publisher, Date publishDate) throws ServiceException {
		try {
			BookDAO dao = new BookDAO();
			return dao.addBookCatalog(isbn, title, categoryCode, author, publisher, publishDate);

		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	// 資料台帳に資料登録
	public List<BookBean> addBookLedger(String isbn, int amount, Date arrival) throws ServiceException {
		try {
			List<BookBean> list = new ArrayList<BookBean>();
			BookDAO dao = new BookDAO();

			for (int i = 0; i < amount; i++) {
				list.add(dao.addBookLedger(isbn, arrival));
			}
			return list;

		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	// 資料台帳に廃棄年月日を登録
	public List<BookBean> discardBooks(List<Integer> bookIds, String reason) throws ServiceException {
		try {
			List<BookBean> list = new ArrayList<BookBean>();
			BookDAO dao = new BookDAO();
			Date discarded = LibraryUtility.createSQLDate();

			for (Integer bookId : bookIds) {
				if (bookId != null) {
					list.add(dao.updateBookLedger(bookId, null, discarded, reason));
				}
			}
			return list;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	// ISBN番号を用いた資料情報変更
	public BookBean changeBookInfo(String isbn, String title, String author, String publisher, Date publishDate,  Integer categoryCode) throws ServiceException {
		try {
			BookDAO dao = new BookDAO();
			BookBean bean = dao.updateBookCatalog(isbn, title, categoryCode, author, publisher, publishDate);
//			System.out.println("bean : " + bean);
			return bean;

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e.getMessage());
		}
	}

	// 資料IDによる蔵書検索
	public BookBean searchBookById(int bookId) throws ServiceException {
		try {
			BookDAO dao = new BookDAO();
			return dao.getBookAllByBookId(bookId);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
	}

	// 分類コードによる分類名取得
	public CategoryCodeBean getCategoryCodeBean(int code) throws ServiceException {
		try {
			BookDAO dao = new BookDAO();
			return dao.getCategoryName(code);

		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}

	}



//	public static void main(String[] args) {
//		try {
//			BookService mgr = new BookService();
//			BookBean bean = null;
//			List<BookBean> list = new ArrayList<BookBean>();
//
//			System.out.println();
//			System.out.println("---------- searchBookCatalog ----------");
//			System.out.println("isbn : 1");
//			bean = mgr.searchBookCatalog("1");
//			System.out.println(bean);
//
//			System.out.println();
//			System.out.println("---------- addBookCatalog ----------");
//			bean = mgr.addBookCatalog("500", "titleXXXXXXX", 1, "authorXXXX", "publisherXXXX", LibraryUtility.createSQLDate("2021-08-08"));
//			System.out.println(bean);
//
//			System.out.println();
//			System.out.println("---------- 	addBookLedger ----------");
//			list = mgr.addBookLedger("302", 3, LibraryUtility.createSQLDate("2021-08-08"));
//			System.out.println(list);
//
//			System.out.println();
//			System.out.println("---------- discardBooks ----------");
//			List<Integer> id = new ArrayList<Integer>();
//			id.add(3);
//			id.add(4);
//			list = mgr.discardBooks(id, "劣化");
//			System.out.println(list);
//
//			System.out.println();
//			System.out.println("---------- changeBookInfo ----------");
//			bean = mgr.changeBookInfo("1", null, "XXXXXX", null, null, null);
//			System.out.println(bean);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
