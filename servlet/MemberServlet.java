package library.servlet;

import java.io.IOException;
import java.sql.Date;
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
import library.service.MemberService;
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
			request.setCharacterEncoding("UTF-8");
			// requestパラメータの取得
			String action = request.getParameter("action");

			if (action.equals("go")) {
				// top.jsp から member.jsp へフォワード
				HttpSession session = request.getSession(true);
				String number = request.getParameter("memberId");
				MemberBean member = (MemberBean) session.getAttribute("member");
				BorrowReturnService borrowReturnService = new BorrowReturnService();
				MemberService memberService = new MemberService();

				if (number.indexOf(".") != -1|| number.indexOf("-") != -1) {
					// メールアドレスまたは電話番号で検索する場合
					member = memberService.searchByPhone(number);
				} else {
					int memberId = Integer.parseInt(number);
					member = memberService.searchMemberInfo(memberId);
				}

				if (member == null) {
					request.setAttribute("message", "該当データがありません。");
					gotoPage(request, response, "/errInternal.jsp");
					return;
				}

//				MemberBean member = new MemberBean(memberId, "ななし", "Shinjyuku", "090-888-9999", "abc@aaa.com", LibraryUtility.createSQLDate("2000-01-01"), LibraryUtility.createSQLDate("2020-02-01"), null);
				session.setAttribute("member", member);
				// 貸出中の資料リスト取得
				List<BorrowReturnBean> list = borrowReturnService.getBorrowingBooks(member.getMemberId());
				// スコープにリストを登録
				session.setAttribute("borrowingBooks", list);
				gotoPage(request, response, "/member.jsp");
				return;

			} else if (action.equals("new")) {
				// newMember.jsp から newmemberConfirm.jsp へフォワード
				HttpSession session = request.getSession(true);
				String name = request.getParameter("name");
				String address = request.getParameter("address");
				String tel1 = request.getParameter("tel1");
				String tel2 = request.getParameter("tel2");
				String tel3 = request.getParameter("tel3");
				String tel = tel1 + "-" +  tel2 + "-" + tel3;
				String email = request.getParameter("email");
				String stringYear = request.getParameter("year");
				String stringMonth = request.getParameter("month");
				String stringDay = request.getParameter("date");
				System.out.println(stringYear);
				String stringBirth = stringYear + "-" + stringMonth + "-" + stringDay;
				System.out.println("birthday:" + stringBirth);
				Date birth = LibraryUtility.createSQLDate(stringBirth);
				Date joined = LibraryUtility.createSQLDate();

				MemberBean member = new MemberBean(0, name, address, tel, email, birth, joined, null);
				// スコープにMemberBeanを登録
				session.setAttribute("candidate", member);
				gotoPage(request, response, "/newMemberConfirm.jsp");
				return;
			} else if (action.equals("signUp")) {
				// newMemberConfirm.jsp から member.jsp へフォワード
				HttpSession session = request.getSession(false);
				MemberBean candidate = (MemberBean) session.getAttribute("candidate");

				MemberService memberService = new MemberService();
				MemberBean member = memberService.signUp(candidate.getName(), candidate.getAddress(), candidate.getTel(), candidate.getEmail(), candidate.getBirth(), candidate.getJoined());
				session.removeAttribute("borrowingBooks");
				// スコープにMemberBeanを登録
				session.setAttribute("member", member);
				gotoPage(request, response, "/member.jsp");
				return;

			} else if (action.equals("change")) {
				// member.jsp から alterInfo.jsp へフォワード
				HttpSession session = request.getSession(false);
				MemberBean nowInfo = (MemberBean) session.getAttribute("member");
//				System.out.println("candidate : " + candidate);

				// スコープにMemberBeanを登録
				session.setAttribute("nowInfo", nowInfo);
				gotoPage(request, response, "/alterInfo.jsp");
				return;
			} else if (action.equals("alt")) {
				// alterInfo.jsp から alterInfoConfirm.jsp へフォワード
				HttpSession session = request.getSession(false);
				MemberBean nowInfo = (MemberBean) session.getAttribute("member");
//				System.out.println("candidate : " + candidate);
				String name = request.getParameter("name");
				String address = request.getParameter("address");
				String tel = request.getParameter("tel");
				String email = request.getParameter("email");

				// スコープにMemberBeanを登録
				MemberBean candidate = new MemberBean(nowInfo.getMemberId(), name, address, tel, email, nowInfo.getBirth(), nowInfo.getJoined(), nowInfo.getOut());
				session.setAttribute("nowInfo", nowInfo);
				session.setAttribute("candidate", candidate);
				gotoPage(request, response, "/alterInfoConfirm.jsp");
				return;

			} else if (action.equals("altConf")) {
				// alterInfoConfirm.jsp から completed.jsp へフォワード
				HttpSession session = request.getSession(false);
				MemberBean candidate = (MemberBean) session.getAttribute("candidate");

				MemberService memberService = new MemberService();
				String name = isNull(candidate.getName());
				String address = isNull(candidate.getAddress());
				String tel = isNull(candidate.getTel());
				String email = isNull(candidate.getEmail());
				MemberBean member = memberService.changeMemberInfo(candidate.getMemberId(), name, address, tel, email);
				// スコープにMemberBeanを登録
				session.setAttribute("member", member);
				request.setAttribute("completing", "会員情報を変更しました。");
				gotoPage(request, response, "/completed.jsp");
				return;
			} else if (action.equals("altComp")) {
				// alterInfoConfirm.jsp から completed.jsp へフォワード
				HttpSession session = request.getSession(false);
				MemberBean member = (MemberBean) session.getAttribute("member");

				// スコープにMemberBeanを登録
				request.setAttribute("member", member);
				gotoPage(request, response, "/member.jsp");
				return;

			} else if (action.equals("delete")) {
				// member.jsp から bye.jsp へフォワード
				HttpSession session = request.getSession(false);
				MemberBean candidate = (MemberBean) session.getAttribute("member");
//				System.out.println("candidate : " + candidate);

				// スコープにMemberBeanを登録
				session.setAttribute("candidate", candidate);
				gotoPage(request, response, "/bye.jsp");
				return;
			} else if (action.equals("out")) {
				// bye.jsp から top.jsp へフォワード
				HttpSession session = request.getSession(false);
				MemberBean member = (MemberBean) session.getAttribute("member");
				Date out = LibraryUtility.createSQLDate();

				MemberService memberService = new MemberService();
				memberService.bye(member.getMemberId(), out);
				gotoPage(request, response, "/top.jsp");
				return;

			} else if (action.equals("history")) {
				// member.jsp から member.jsp へフォワード
				HttpSession session = request.getSession(false);
				MemberBean member = (MemberBean) session.getAttribute("member");

				BorrowReturnService borrowReturnService = new BorrowReturnService();
				// 貸出履歴の資料リスト取得
				List<BorrowReturnBean> list = borrowReturnService.getBorrowedRecord(member.getMemberId());
				// スコープにリストを登録
				request.setAttribute("borrowedRecord", list);
				System.out.println(list);
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

	public String isNull(String arg) throws ServletException {
		if (arg.equals(null) || arg.equals("")) {
			arg = null;
		}
		return arg;
	}

}
