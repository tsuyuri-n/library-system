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

import library.bean.CategoryCodeBean;
import library.dao.BookDAO;
import library.dao.DAOException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static final String USER = "staff";
	private static final String PASS = "library";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// action リクエストパラメータの読み込み
		String action = request.getParameter("action");
		if(action.contentEquals("login")) { //ログイン時
			// ログイン時はユーザ名とパスワードを取得する
			// パラメータのエラーチェックは省略
			String user = request.getParameter("user");
			String pass = request.getParameter("pass");

			if(user.equals(USER) && pass.equals(PASS)) {
				// ユーザ名とパスワード名が一致したらログイン処理を行う
				// セッション管理を行う
				HttpSession session = request.getSession();
				// ログイン済みの属性を設定する
				session.setAttribute("isLogin", "true");
				gotoPage(request, response, "/top.jsp");
				return;
			}else {
				gotoPage(request, response, "/login.jsp");
			}
		}else if(action.equals("logout")){  // ログアウト時
			// すでに作成されているセッション領域を取得する。新しく作成はしない
			HttpSession session = request.getSession(false);
			if(session != null) {
				// セッション領域を無効にする
				session.invalidate();
				// ログアウト処理
				gotoPage(request, response, "/login.jsp");
			}
		}

	}

	public void init() throws ServletException{
		try {
			BookDAO dao=new BookDAO();
			List<CategoryCodeBean>list=dao.getCategoryCodeList();
			getServletContext().setAttribute("CCL", list);
		}catch(DAOException e) {
			e.printStackTrace();
			throw new ServletException();
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


}
