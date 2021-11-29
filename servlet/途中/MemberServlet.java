package library.servlet;

import java.io.IOException;
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
 * Servlet implementation class MemberServlet
 */
@WebServlet("/MemberServlet")
public class MemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			// requestパラメータの取得
			String action = request.getParameter("action");
			if (action.equals("go")) {
				HttpSession session = request.getSession(true);
				int memberId = Integer.parseInt(request.getParameter("memberId"));
//				MemberBean member = (MemberBean) session.getAttribute("member");
				BorrowReturnService borrowReturnService = new BorrowReturnService();
//				MemberService memberService = new MemberService();

				MemberBean member = new MemberBean(memberId, "ななし", "Shinjyuku", "090-888-9999", "abc@aaa.com", LibraryUtility.createSQLDate("2000-01-01"), LibraryUtility.createSQLDate("2020-02-01"), null);
				session.setAttribute("member", member);
				// 貸出中の資料リスト取得
				List<BorrowReturnBean> list = borrowReturnService.getBorrowingBooks(member.getMemberId());
				// スコープにリストを登録
				session.setAttribute("borrowingBooks", list);
				gotoPage(request, response, "/member.jsp");
				return;
			}else {
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
