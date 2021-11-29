package library.service;

import java.sql.Date;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;

import library.bean.BorrowReturnBean;
import library.dao.BookDAO;
import library.dao.BorrowReturnDAO;
import library.dao.DAOException;
import library.util.LibraryUtility;;


public class BorrowReturnService {

	//同時貸出可能な最大冊数
	public static final int MAX_BORROW = 5;

	//資料一冊の貸出処理
	public void borrowBooks(int memberId, int bookId, Date borrowed)
			throws ServiceException, DAOException, ParseException {
		//返却期日を計算
		Date deadLine = calcReturnDate(bookId, borrowed);
		//BorrowReturnDAOのborrowBookで会員ID、資料ID、貸出年月日、返却期日を追加
		try {
		BorrowReturnDAO dao = new BorrowReturnDAO();
		dao.borrowBook(memberId, bookId, borrowed, deadLine);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	//複数資料の貸出処理
	public List<BorrowReturnBean> borrowBooks(int memberId, List<Integer> bookIds, Date borrowed)
			throws ServiceException, DAOException, ParseException {
		try {
		BorrowReturnDAO dao = new BorrowReturnDAO();
		for (Integer bookId : bookIds) {
			borrowBooks(memberId, bookId, borrowed);
		}
		//DAOからリストをもらってリスト
		return  dao.searchBorrow(memberId, null, null, null);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public boolean isNew(int bookId) throws ServiceException {
		try {
			BookDAO dao = new BookDAO();
			Date published=dao.getPublishDate(bookId);
			long miliseconds = System.currentTimeMillis();
			Date today=new Date(miliseconds);
			Calendar calPub = Calendar.getInstance();
			calPub.setTime(published);
			Calendar calToday = Calendar.getInstance();
			calToday.setTime(today);
		     calPub.add(Calendar.MONTH, 3);
		     return calToday.before(calPub);
		}catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	//資料の返却期日を計算
	public Date calcReturnDate(int bookId, Date borrowed)
			throws ServiceException, ParseException {


			//BookServiceのisNew()で新刊判定
		Date deadLine = LibraryUtility.createSQLDate(borrowed);


		if (isNew(bookId)) {
			//　返却期日を貸出年月日から10日後に設定
			calcDate(deadLine,10);
		}else {
			//返却期日を貸出年月日から15日後に設定
			calcDate(deadLine,15);
			}
			// 返却期日を返す
			return deadLine;
		//}
	}


	//資料の返却処理
	public void returnBooks(int borrowId, Date returned)
			throws ServiceException, DAOException{
		//BorrowReturnDAOのupdateBorrowLog()で、貸出IDの返却年月日にreturnedを設定
		try {
			BorrowReturnDAO dao = new BorrowReturnDAO();
			dao.updateBorrowLog(borrowId, null, null, null, null, returned);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}


	//複数資料の返却処理
	public void returnBooks(List<Integer> borrowIds, Date returned)
			throws ServiceException, DAOException {
		//BorrowReturnDAOのupdateBorrowLog()で、貸出IDの返却年月日にreturnedを設定
		try {
			BorrowReturnDAO dao = new BorrowReturnDAO();
			for (int borrowId : borrowIds) {
			dao.updateBorrowLog(borrowId, null, null, null, null, returned);
			}
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	//会員に貸出中の資料リストを取得
	public List<BorrowReturnBean> getBorrowingBooks(int memberId)
			throws ServiceException, DAOException {
		//会員に貸出中の資料リストをBorrowReturnDAOのsearchBorrow()で選択し返す
		try {
			BorrowReturnDAO dao = new BorrowReturnDAO();
			return dao.searchBorrow(memberId, null, null, null);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	//任意の日数を日付に足す
	public static void calcDate(Date d, Integer date) {
		d.setTime(d.getTime() + 1000L * 60 * 60 * 24 * date);
	}

	// 貸出履歴の資料リスト作成
	public List<BorrowReturnBean> getBorrowedRecord(int memberId)
			throws ServiceException, DAOException {
		//会員に貸出中の資料リストをBorrowReturnDAOのsearchBorrow()で選択し返す
		try {
			BorrowReturnDAO dao = new BorrowReturnDAO();
			return dao.searchRecord(memberId, null, null, null);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}
}
// *************  動作テスト  ***********************
/*
		public static void main(String[] args) {
		try {
			BorrowReturnService brs = new BorrowReturnService();
			System.out.println();
*/
			//borrowBooks(int, int, Date) 一冊の本の貸出処理
/*			System.out.println("borrowBooks(一冊の本の貸出処理)***************");
			brs.borrowBooks(3, 1, LibraryUtility.createSQLDate("2021-08-20"));
			System.out.println("貸出台帳borrowlogに会員ID３、資料ID１、貸出日2021-08-20を登録しました。");
*/

/*			//borrowBooks(int, List<Integer>, Date) 複数の本の貸出処理
			System.out.println();
			System.out.println("borrowBooks(複数の本の貸出処理)***************");
			//引数(int memberId, List<Integer> bookIds, Date borrowed)
			List<Integer> bookIds = new ArrayList<Integer>();
			bookIds.add(10);
			bookIds.add(11);
			bookIds.add(12);
			brs.borrowBooks(5, bookIds, LibraryUtility.createSQLDate("2021-08-20"));
			System.out.println("貸出台帳borrowlogに会員ID5、資料ID10,11,12、 貸出日2021-08-20を登録しました。");
*/
			//calcDate(int, Date) 任意の日数を日付に足す計算
			//System.out.println("calcDate(任意の日数を日付に足す計算)***************");
			//brs.calcDate(LibraryUtility.createSQLDate("2021-08-20"), 10);
			//System.out.println(LibraryUtility.createSQLDate("2021-08-20") + "の" + 10 + "日後の日付は" + brs.calcDate(LibraryUtility.createSQLDate("2021-08-20"), 10) + "です");
			//System.out.println(brs.calcDate(LibraryUtility.createSQLDate("2021-08-20"), 10));


			//calcReturnDate(int, Date) 資料の返却期日を計算
			//System.out.println("calcReturnDate(資料の返却期日を計算)***************");
			//brs.calcReturnDate(10, LibraryUtility.createSQLDate("2021-08-20"));
			//System.out.println(brs.calcReturnDate(10, LibraryUtility.createSQLDate("2021-08-20")));
/*
			//getBorrowingBooks(int) 会員に貸出中の資料リストを取得
			System.out.println();
			System.out.println("getBorrowingBooks(会員に貸出中の資料リストを取得)***************");
			System.out.println(brs.getBorrowingBooks(1));
			System.out.println("会員ID1の貸出中リストを取得しました。");
			//SQL上で下記のコードを実行し、チェック
			//SELECT * FROM borrowlog WHERE memberid = 1 AND returned IS NULL;
*/
/*
			//returnBooks(int borrowId, Date returned) 一冊の本返却処理・返却日時を記入
			System.out.println();
			System.out.println("returnBooks (一冊の本返却処理・返却日時を記入)*******************");
			brs.returnBooks(15, LibraryUtility.createSQLDate("2021-08-20"));
			System.out.println("貸出ID15の資料を返却しました。");
			//SQL上で下記のコードを実行し、borrowid=15のレコードがないかチェック
			//SELECT * FROM borrowlog WHERE memberid = 1 AND returned IS NULL;
*/
/*
			//returnBooks(List<Integer> borrowIds, Date returned) 複数の本の返却処理・返却日時を記入
			System.out.println();
			System.out.println("returnBooks (複数の本の返却処理・返却日時を記入)*******************");
			//(int memberId, List<Integer> borrowIds, Date borrowed)
			List<Integer> borrowIds = new ArrayList<Integer>();
			borrowIds.add(13);
			brs.returnBooks(borrowIds, LibraryUtility.createSQLDate());
			System.out.println("貸出ID13の資料を返却しました。");
			//SQL上で下記のコードを実行し、borrowid=11,12のレコードがないかチェック
			//SELECT * FROM borrowlog WHERE memberid = 5;
*/
/*
			//複数資料の返却処理
			public void returnBooks(List<Integer> borrowIds, Date returned)
					throws ServiceException, DAOException {
				//BorrowReturnDAOのupdateBorrowLog()で、貸出IDの返却年月日にreturnedを設定
				try {
					BorrowReturnDAO dao = new BorrowReturnDAO();
					dao.updateBorrowLog(null, null, null, null, null, returned);
				} catch (DAOException e) {
					throw new ServiceException(e.getMessage());
				}
			}
*/
/*
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
*/