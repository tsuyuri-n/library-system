package library.service;

import java.sql.Date;
import java.util.List;

import library.bean.BorrowReturnBean;

public class BorrowReturnService {

	//同時貸出可能な最大冊数
	public static final int MAX_BORROW = 5;

	//資料一冊の貸出処理
	public List<BorrowReturnBean> borrowBooks(int memberId, int bookId, Date borrowed)
			throws ServiceException {
		//返却期日を計算
		//calcReturnDate();

		//BorrowReturnDAOのborrowBookで会員ID、資料ID、貸出年月日、返却期日を追加
		//borrowBook();
	}

	//複数資料の貸出処理
	public List<BorrowReturnBean> borrowBooks(int memberId, List<Integer> bookIds, Date borrowed)
			throws ServiceException {
		//for (資料IDリスト) {
			//貸出処理を行う
			// borrowBooks();

		}

	//資料の返却期日を計算
	public Date calcReturnDate(int bookId, Date borrowed)
			throws ServiceException {
		//BookServiceのisNew()で新刊判定
		//if () {
			//　返却期日を貸出年月日から10日後に設定
		//else {返却期日を貸出年月日から15日後に設定}
			// 返却期日を返す
	}

	//資料の返却処理
	public void returnBooks(int borrowId, Date returned)
			throws ServiceException {
		//BorrowReturnDAOのupdateBorrowLog()で、貸出IDの返却年月日にreturnedを設定
		//updateBorrowLog();
	}

	//複数資料の返却処理
	public void returnBooks(List<Integer> borrowIds, Date returned)
			throws ServiceException {
		//BorrowReturnDAOのupdateBorrowLog()で、貸出IDの返却年月日にreturnedを設定
		//updateBorrowLog();
	}


	//会員に貸出中の資料リストを取得
	public List<BorrowReturnBean> getBorrowingBooks(int memberId)
			throws ServiceException {
		//会員に貸出中の資料リストをBorrowReturnDAOのsearchBorrow()で選択し返す
		//searchBorrow();
	}
}
