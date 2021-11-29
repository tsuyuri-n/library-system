package library.bean;
import java.sql.Date;


public class BorrowReturnBean {


	private String title;

	//貸出ID(通し番号)
	private int borrowId;

	//会員IDmemberlistと紐づく
	private int memberId;

	//資料ID
	//bookledgerと紐づく
	//これと返却年月日(returned)で資料を特定、返却
	private int bookId;

	//貸出年月日
	private Date borrowed;

	//返却期日(年月日)
	private Date deadLine;

	//返却年月日nullなら未返却
	private Date returned;

	//コンストラクタ全フィールドを初期化するコンストラクタを作成する
	public BorrowReturnBean(String title,int borrowId, int memberId, int bookId, Date borrowed, Date deadLine, Date returned) {
		super();
		this.title=title;
		this.borrowId = borrowId;
		this.memberId = memberId;
		this.bookId = bookId;
		this.borrowed = borrowed;
		this.deadLine = deadLine;
		this.returned = returned;
	}

	//全フィールドのゲッターとセッター
	public int getBorrowId() {
		return borrowId;
	}
	public void setBorrowId(int borrowId) {
		this.borrowId = borrowId;
	}
	public int getMemberId() {
		return memberId;
	}
	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public Date getBorrowed() {
		return borrowed;
	}
	public void setBorrowed(Date borrowed) {
		this.borrowed = borrowed;
	}
	public Date getDeadLine() {
		return deadLine;
	}
	public void setDeadLine(Date deadLine) {
		this.deadLine = deadLine;
	}
	public Date getReturned() {
		return returned;
	}
	public void setReturned(Date returned) {
		this.returned = returned;
	}

	//toString生成
	@Override
	public String toString() {
		return "BorrowReturnBean [title="+title+",borrowId=" + borrowId + ", memberId=" + memberId + ", bookId=" + bookId
				+ ", borrowed=" + borrowed + ", deadLine=" + deadLine + ", returned=" + returned + "]";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
