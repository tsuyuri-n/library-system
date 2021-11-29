package library.service;

import java.sql.Date;

import library.bean.MemberBean;
import library.dao.DAOException;
import library.dao.MemberDAO;

public class MemberService {
	// 会員IDによる会員情報の検索
	public MemberBean searchMemberInfo(int memberId) throws ServiceException {
		try {
			MemberDAO dao = new MemberDAO();
			return dao.searchMember(memberId);
		}catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	// 新規会員登録
	public MemberBean signUp(String name, String address, String tel, String email, Date birth, Date joined) throws ServiceException {
		try {
			MemberDAO dao = new MemberDAO();
			MemberBean bean = dao.addMember(name, address, tel, email, birth, joined);
			return dao.searchMember(bean.getMemberId());
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	// 会員情報変更
	public MemberBean changeMemberInfo(int memberId, String name, String address, String tel, String email) throws ServiceException{
		try {
			MemberDAO dao = new MemberDAO();
			dao.updateMember(memberId, name, address, tel, email, null);
			return dao.searchMember(memberId);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}

	}

	// 退会処理
	public void bye(int memberId, Date out) throws ServiceException{
		try {
			MemberDAO dao = new MemberDAO();
			dao.updateMember(memberId, null, null, null, null, out);
//			return dao.searchMember(memberId);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}

	}

	// 電話番号・メールアドレス検索
	public MemberBean searchByPhone(String number) throws ServiceException{
		try {
			MemberDAO dao = new MemberDAO();
			int result = number.indexOf(".");
			MemberBean bean;
			if (result != -1) {
				// "."を含む(= メールアドレスのとき)
				bean = dao.searchMemberIdByPhone(null, number);
			} else {
				bean = dao.searchMemberIdByPhone(number, null);
			}

			return bean;
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

//	public static void main(String[] args) {
//		try {
//			MemberService mgr = new MemberService();
//			MemberBean bean = null;
//
//			System.out.println();
//			System.out.println("---------- searchMemberInfo ----------");
//			System.out.println("memberId : 1");
//			bean = mgr.searchMemberInfo(1);
//			System.out.println(bean);
//
//			System.out.println();
//			System.out.println("---------- signUp ----------");
//			bean = mgr.signUp("テスト 八郎", "東京都新宿区テストビル8-88", "090-888-8888", "test.888.mail", LibraryUtility.createSQLDate("2000-01-01"), LibraryUtility.createSQLDate("2021-08-08"));
//			System.out.println(bean);
//
//			System.out.println();
//			System.out.println("---------- 	changeMemberInfo ----------");
//			bean = mgr.changeMemberInfo(6, null, "北海道", null, null);
//			System.out.println(bean);
//
//			System.out.println();
//			System.out.println("---------- bye ----------");
//			mgr.bye(7, LibraryUtility.createSQLDate("2021-08-24"));
//
//			System.out.println();
//			System.out.println("---------- searchByPhone (tel) ----------");
//			bean = mgr.searchByPhone("090-111-1111");
//			System.out.println("tel:090-111-1111");
//			System.out.println(bean);
//
//			System.out.println();
//			System.out.println("---------- searchByPhone (email) ----------");
//			bean = mgr.searchByPhone("test.111.mail");
//			System.out.println("email:test.111.mail");
//			System.out.println(bean);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

}
