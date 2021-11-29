package library.bean;

import java.sql.Date;

public class BookBean {

	private String isbn;
	//ISBN番号
	//資料を一意に特定する

	private String title;
	//資料名

	private String author;

	private String publisher;
	//出版社

	private Date publishDate;
	//出版日

	private int bookId;
	//資料ID

	private Date arrival;
	//入荷年月日

	private Date discarded;
	//廃棄年月日
	//nullなら未廃棄

	private String reason;
	//備考(廃棄理由)

	private int categoryCode;
	//分類コード

	private String categoryName;
	//類目名


	//コンストラクタ
	//全フィールドを初期化するコンストラクタを作成する
	public BookBean(String isbn, String title, String author, String publisher, Date publishDate, int bookId, Date arrival,
			Date discarded, String reason, int categoryCode, String categoryName) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.publishDate = publishDate;
		this.bookId = bookId;
		this.arrival = arrival;
		this.discarded = discarded;
		this.reason = reason;
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
	}

	public BookBean(String isbn, String title, String author, String publisher, Date publishDate, int bookId, Date arrival,
			Date discarded, String reason, int categoryCode) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.publishDate = publishDate;
		this.bookId = bookId;
		this.arrival = arrival;
		this.discarded = discarded;
		this.reason = reason;
		this.categoryCode = categoryCode;
	}


	//bookId以外のフィールドに関するコンストラクタ
		public BookBean(String isbn, String title, String author, String publisher, Date publishDate, Date arrival,
			Date discarded, String reason, int categoryCode, String categoryName) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.publishDate = publishDate;
		this.arrival = arrival;
		this.discarded = discarded;
		this.reason = reason;
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
	}

	//資料目録に関するコンストラクタ（分類名なし）
	public BookBean(String isbn, String title, String author, String publisher, Date publishDate,
				int categoryCode) {
			super();
			this.isbn = isbn;
			this.title = title;
			this.author = author;
			this.publisher = publisher;
			this.publishDate = publishDate;
			this.categoryCode = categoryCode;
		}

	//資料目録に関する（分類名あり）
	public BookBean(String isbn, String title, String author, String publisher, Date publishDate, int categoryCode,
			String categoryName) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.publishDate = publishDate;
		this.categoryCode = categoryCode;
		this.categoryName = categoryName;
	}

	//資料台帳に関するコンストラクタ
	public BookBean(String isbn, Date publishDate) {
		super();
		this.isbn = isbn;
		this.publishDate = publishDate;
	}


	//資料目録に関するコンストラクタ分類コード、分類名なし)
	public BookBean(String isbn, String title, String author, String publisher,
			Date publishDate) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.publishDate = publishDate;
	}

	//資料目録に関するコンストラクタ
	public BookBean(String isbn, Date arrival, Date discarded, String reason) {
		this.isbn = isbn;
		this.arrival = arrival;
		this.discarded = discarded;
		this.reason = reason;
	}

	//ISBNをもとに該当する資料の全ての情報をもってくるgetAllBookByISBNメソッドで使用するコンストラクタ
	public BookBean(String title, int categoryCode, String author, String publisher, Date publishDate, int bookId, Date arrival,
			Date discarded, String reason) {
		super();
		this.title = title;
		this.categoryCode = categoryCode;
		this.author = author;
		this.publisher = publisher;
		this.publishDate = publishDate;
		this.bookId = bookId;
		this.arrival = arrival;
		this.discarded = discarded;
		this.reason = reason;
	}

	//getAllBookByBookIdで使用するコンストラクタ
	public BookBean(String isbn, String title, int categoryCode, String author, String publisher, Date publishDate, Date arrival,
			Date discarded, String reason) {
		super();
		this.isbn = isbn;
		this.title = title;
		this.categoryCode = categoryCode;
		this.author = author;
		this.publisher = publisher;
		this.publishDate = publishDate;
		this.arrival = arrival;
		this.discarded = discarded;
		this.reason = reason;
	}


	//全フィールドのゲッターとセッターを作成する
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}


	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public Date getArrival() {
		return arrival;
	}

	public void setArrival(Date arrival) {
		this.arrival = arrival;
	}

	public Date getDiscarded() {
		return discarded;
	}

	public void setDiscarded(Date discarded) {
		this.discarded = discarded;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public int getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(int categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	@Override
	public String toString() {
		return "BookBean [isbn=" + isbn + ", title=" + title + ", author=" + author + ", publisher=" + publisher
				+ ", publishDate=" + publishDate + ", bookId=" + bookId + ", arrival=" + arrival + ", discarded="
				+ discarded + ", reason=" + reason + ", categoryCode=" + categoryCode + ", categoryName=" + categoryName
				+ "]";
	}
}
