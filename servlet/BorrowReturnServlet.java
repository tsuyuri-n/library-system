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

import library.bean.BorrowReturnBean;
import library.bean.MemberBean;
import library.service.BorrowReturnService;
import library.util.LibraryUtility;



/**
 * Servlet implementation class BorrowReturnServlet
 */
@WebServlet("/BorrowReturnServlet")
public class BorrowReturnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

//	public static final int MAX_BORROW = 5;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			// requestパラメータの取得
			String action = request.getParameter("action");
			String[] books;
			Date date = LibraryUtility.createSQLDate();
			HttpSession session = request.getSession(false);
			if (session == null) {
				gotoPage(request, response, "/top.jsp");
				return;
			}
			if (action.equals("borrow")) {
				// 貸し出す資料のパラメータ取得（複数の可能性あり）
				books = request.getParameterValues("bookId");
				List<Integer> bookIds = new ArrayList<Integer>();
				for (String bookId : books) {
					if (bookId.length() != 0) {
						bookIds.add(Integer.parseInt(bookId));
					}
				}
				BorrowReturnService service = new BorrowReturnService();
				MemberBean member = (MemberBean) session.getAttribute("member");
				// 貸出処理  borrowBooks(memberId, bookIds, borrowed)
				int memberId = member.getMemberId();
				service.borrowBooks(memberId, bookIds, date);
				// 貸出中の資料リスト取得
				List<BorrowReturnBean> list = service.getBorrowingBooks(memberId);
				// スコープにリストを登録
				session.setAttribute("borrowingBooks", list);
				gotoPage(request, response, "/member.jsp");
				return;
			} else if (action.equals("return")) {
				// 貸出中資料のパラメータ取得（複数の可能性あり）
				books = request.getParameterValues("borrowing");		//borrowId
				List<Integer> borrowIds = new ArrayList<Integer>();
				for (String borrowId : books) {
					borrowIds.add(Integer.parseInt(borrowId));
				}
				BorrowReturnService service = new BorrowReturnService();
				MemberBean member = (MemberBean) session.getAttribute("member");
				// 返却処理
				service.returnBooks(borrowIds, date);
				// 貸出中の資料リスト取得
				List<BorrowReturnBean> list = service.getBorrowingBooks(member.getMemberId());
				// スコープにリストを登録
				session.setAttribute("borrowingBooks", list);
				gotoPage(request, response, "/member.jsp");
				return;
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

	public void init() throws ServletException {
		try {
			System.out.println("init():BorrowReturnService.MAX_BORROW : " + BorrowReturnService.MAX_BORROW);
			// カテゴリ一覧は最初にアプリケーションスコープに入れる
			getServletContext().setAttribute("MAX_BORROW", BorrowReturnService.MAX_BORROW);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServletException();
		}
	}

}
