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
import library.service.BorrowReturnService;
import library.service.BoxReturnService;



/**
 * Servlet implementation class BorrowReturnServlet
 */
@WebServlet("/BoxReturnServlet")
public class BoxReturnServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

//	public static final int MAX_BORROW = 5;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action=request.getParameter("action");
		HttpSession session = request.getSession(false);
		List<BorrowReturnBean>list=new ArrayList<BorrowReturnBean>();
		ArrayList<Integer>idList=new ArrayList<Integer>();
		if(action.equals("confirm")) {
			try {
				for (int i=1; i<21;i++) {
					String num="book"+i;
					String bookIdStr=request.getParameter(num);
						if(bookIdStr.length()!=0 && bookIdStr!=null && bookIdStr!="") {
							int bookId=Integer.parseInt(bookIdStr);
							idList.add(bookId);
							BoxReturnService service = new BoxReturnService();
							list.addAll(service.checkInfo(bookId));
						}else{
							continue;
						}session.setAttribute("returnList",list);
						session.setAttribute("idList",idList);
					}
				gotoPage(request, response, "/boxReturnConfirm.jsp");
			}catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("message", "内部エラーが発生しました。");
				gotoPage(request, response, "/errInternal.jsp");
			}
		}else if(action.equals("return")) {
	        long miliseconds = System.currentTimeMillis();
			Date today=new Date(miliseconds);
			@SuppressWarnings("unchecked")
			ArrayList<Integer> IdList=(ArrayList<Integer>)session.getAttribute("idList");
			try {
				for(int i = 0; i < IdList.size(); i++){
				    Integer value = IdList.get(i);
				    BoxReturnService service = new BoxReturnService();
					service.returnById(value,today);
				}
				request.setAttribute("message", "返却しました("+today+")");
				gotoPage(request, response, "/boxReturned.jsp");
			}catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("message", "内部エラーが発生しました。");
				gotoPage(request, response, "/errInternal.jsp");
			}
		}else {
			request.setAttribute("message", "不明な遷移先です。");
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
