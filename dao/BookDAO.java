package library.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import library.bean.BookBean;
import library.bean.CategoryCodeBean;



public class BookDAO {
	private Connection con;
	public BookDAO() throws DAOException{
		getConnection();
	}

	//分類コード表の取得
	public List<CategoryCodeBean> getCategoryCodeList() throws DAOException{
		if(con==null)
			getConnection();
		PreparedStatement st=null;
		ResultSet rs=null;
		try {
			String sql="SELECT *FROM categorycodelist";

			st=con.prepareStatement(sql);
			rs=st.executeQuery();
			ArrayList<CategoryCodeBean>list=new ArrayList<CategoryCodeBean>();
			while(rs.next()) {
				int categoryCode=rs.getInt("categorycode");
				String categoryName =rs.getString("categoryname");
				CategoryCodeBean bean=new CategoryCodeBean(categoryCode,categoryName);
				list.add(bean);
				}
			return list;
			}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗してしまいました……。");
		}finally {
			try {
				if(rs!=null)rs.close();
				if(st!=null)st.close();
				close();
			}catch (Exception e) {
				throw new DAOException("リソースの解放に失敗してしまいました……。");
			}
		}
	}

	//分類名の取得
		public CategoryCodeBean getCategoryName(int categoryCode) throws DAOException{
			if (con == null)
				getConnection();
			PreparedStatement st=null;
			ResultSet rs=null;
			try {
				String sql="SELECT categoryname FROM categorycodelist WHERE categorycode = ?";

				st=con.prepareStatement(sql);
				st.setInt(1, categoryCode);
				rs=st.executeQuery();
				CategoryCodeBean bean = null;
				if (rs.next()) {
					String name = rs.getString("categoryname");
					bean = new CategoryCodeBean(categoryCode, name);
				}
				return bean;

			}catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗してしまいました……。");
			}finally {
				try {
					if(rs!=null)rs.close();
					if(st!=null)st.close();
					close();
				}catch (Exception e) {
					throw new DAOException("リソースの解放に失敗してしまいました……。");
				}
			}
		}


	//新規資料登録入力された情報を資料目録に追加(addBookLedgerメソッドとセットで呼び出す)
	public BookBean addBookCatalog(String isbn, String title,
			int categoryCode, String author, String publisher,Date publishDate) throws DAOException	{
		if(con == null)
			getConnection();

		PreparedStatement st=null;
		BookBean bean = null;
		try {
			String sql="INSERT INTO bookcatalog(isbn, title, categorycode, author, publisher, publishdate)VALUES(?, ?, ?, ?, ?, ?)";
			// PreparedStatementオブジェクトの取得
			st=con.prepareStatement(sql);
			st.setString(1, isbn);
			st.setString(2, title);
			st.setInt(3, categoryCode);
			st.setString(4, author);
			st.setString(5, publisher);
			st.setDate(6, publishDate);

			int cRow = st.executeUpdate();
			if (cRow > 0) {
				bean = getBookAllByIsbn(isbn);
			}
			return bean;
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗してしまいました……。");
		}finally {
			try {
				// リソースの開放
				if(st!=null)st.close();
				close();
			}catch (Exception e) {
				throw new DAOException("リソースの解放に失敗してしまいました……。");
			}
		}
	}

	//新規資料登録入力された情報を資料台帳に追加(addBookCatalogメソッドとセットで呼び出す)
	public BookBean addBookLedger(String isbn, Date arrival) throws DAOException {
		if(con == null)
			getConnection();
		PreparedStatement st=null;
		try {
			BookBean bean = null;
			String sql="INSERT INTO bookledger(bookid, isbn, arrival) VALUES(?, ?, ?)";
			// PreparedStatementオブジェクトの取得
			st=con.prepareStatement(sql);
			int id = DAOUtility.getNextVal(con, "bookledger_bookid_seq");
			st.setInt(1, id);
			st.setString(2, isbn);
			st.setDate(3, arrival);
			int cRow = st.executeUpdate();
			if (cRow > 0) {
				bean = getBookAllByBookId(id);
			}
			return bean;
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗してしまいました……。");
		}finally {
			try {
				// リソースの開放
				if(st!=null)st.close();
				close();
			}catch (Exception e) {
				throw new DAOException("リソースの解放に失敗してしまいました……。");
			}
		}
	}


	//資料情報変更資料目録(bookcatalog)の資料情報を変更する(ISBN番号で特定、変更しない項目はNULL)
	public BookBean updateBookCatalog(String isbn, String title,
			Integer categoryCode, String author, String publisher, Date publishDate)
					throws DAOException {
		if(con == null)
			getConnection();

		PreparedStatement st=null;
		try {
			BookBean bean = null;
			String sql="UPDATE bookcatalog SET title = COALESCE(?, title), categorycode = COALESCE(?::Integer,categorycode), author = COALESCE(?, author), publisher = COALESCE(?, publisher), publishdate = COALESCE(?, publishdate) WHERE isbn = ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setString(1, title);
			st.setString(2, categoryCode == null ? null : "" + categoryCode);
			st.setString(3, author);
			st.setString(4, publisher);
			st.setDate(5, publishDate);
			st.setString(6,isbn);

			// SQLの実行
			int cRow = st.executeUpdate();
			System.out.println("cRow : " + cRow);
			if (cRow >0) {
			bean = getBookAllByIsbn(isbn);
			}
			return bean;
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗してしまいました……。");
		}finally {
			try {
				if(st!=null)st.close();
				close();
			}catch (Exception e) {
				throw new DAOException("リソースの解放に失敗してしまいました……。");
			}
		}
	}

	//資料の廃棄処理　資料台帳(bookledger)の資料廃棄情報を追加する(資料IDで特定、変更しない項目はNULL)
	public BookBean updateBookLedger(int bookId, Date arrival, Date discarded, String reason) throws DAOException {
		if(con == null)
			getConnection();

		PreparedStatement st=null;
		try {
			BookBean bean = null;
			String sql="UPDATE bookledger SET arrival = COALESCE(?, arrival), discarded = COALESCE(?, discarded), reason = COALESCE(?, reason) WHERE bookid = ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setDate(1, arrival);
			st.setDate(2, discarded);
			st.setString(3, reason);
			st.setInt(4, bookId);
			// SQLの実行
			int cRow = st.executeUpdate();
			if (cRow > 0) {
				bean = getBookAllByBookId(bookId);
			}
			return bean;
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗してしまいました……。");
		}finally {
			try {
				if(st!=null)st.close();
				close();
			}catch (Exception e) {
				throw new DAOException("リソースの解放に失敗してしまいました……。");
			}
		}
	}

	//ISBN番号で検索し該当資料の資料目録、資料台帳情報を取得する
	public BookBean getBookAllByIsbn(String isbn) throws DAOException {
		if(con==null)
			getConnection();

		PreparedStatement st=null;
		ResultSet rs = null;
		try {
			String sql="SELECT * FROM bookcatalog bc JOIN bookledger bl ON bc.isbn = bl.isbn WHERE bc.isbn = ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setString(1,isbn);
			// SQLの実行
			rs = st.executeQuery();
			BookBean bean = null;
			if (rs.next()) {
				String title = rs.getString("title");
				String author = rs.getString("author");
				String publisher = rs.getString("publisher");
				Date publishDate = rs.getDate("publishDate");
				int bookId = rs.getInt("bookId");
				Date arrival = rs.getDate("arrival");
				Date discarded = rs.getDate("discarded");
				String reason = rs.getString("reason");
				int categoryCode = rs.getInt("categoryCode");

				bean = new BookBean(isbn, title, author, publisher,
						publishDate, bookId, arrival, discarded, reason, categoryCode);
			}
			return bean;
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗してしまいました……。");
		}finally {
			try {
				if(st!=null)st.close();
				close();
			}catch (Exception e) {
				throw new DAOException("リソースの解放に失敗してしまいました……。");
			}
		}
	}

	//資料IDで検索し該当資料の資料目録、資料台帳情報を取得する
	public BookBean getBookAllByBookId(int bookId) throws DAOException {
		if(con==null)
			getConnection();

		PreparedStatement st=null;
		ResultSet rs = null;
		try {
			String sql="SELECT * FROM bookledger bl JOIN bookcatalog bc ON bl.isbn = bc.isbn WHERE bl.bookid = ?;";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setInt(1,bookId);
			// SQLの実行
			rs = st.executeQuery();
			BookBean bean = null;
			if (rs.next()) {
				String isbn = rs.getString("isbn");
				String title = rs.getString("title");
				String author = rs.getString("author");
				String publisher = rs.getString("publisher");
				Date publishDate = rs.getDate("publishDate");
				Date arrival = rs.getDate("arrival");
				Date discarded = rs.getDate("discarded");
				String reason = rs.getString("reason");
				int categoryCode = rs.getInt("categoryCode");

				bean = new BookBean(isbn, title, author, publisher,
						publishDate, bookId, arrival, discarded, reason, categoryCode);
			}
			return bean;
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗してしまいました……。");
		}finally {
			try {
				if(st!=null)st.close();
				close();
			}catch (Exception e) {
				throw new DAOException("リソースの解放に失敗してしまいました……。");
			}
		}
	}


	//資料IDで検索し該当資料の資料台帳情報を取得する
	public BookBean getBookLedgerByBookId(int bookId) throws DAOException {
		if(con==null)
			getConnection();

		PreparedStatement st=null;
		ResultSet rs = null;
		try {
			String sql="SELECT * FROM bookledger WHERE bookid = ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setInt(1, bookId);
			// SQLの実行
			rs = st.executeQuery();
			BookBean bean = null;
			if (rs.next()) {
				String isbn = rs.getString("isbn");
				Date arrival = rs.getDate("arrival");
				Date discarded = rs.getDate("discarded");
				String reason = rs.getString("reason");

				bean = new BookBean(isbn, arrival, discarded, reason);
			}
			return bean;
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗してしまいました……。");
		}finally {
			try {
				if(st!=null)st.close();
				close();
			}catch (Exception e) {
				throw new DAOException("リソースの解放に失敗してしまいました……。");
			}
		}
	}

	//ISBNで検索し該当資料の資料目録、資料台帳情報を取得する
	public BookBean getBookCatalogByIsbn(String isbn) throws DAOException {
		if(con==null)
			getConnection();

		PreparedStatement st=null;
		ResultSet rs = null;
		try {
			String sql="SELECT * FROM bookcatalog WHERE isbn = ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setString(1,isbn);
			// SQLの実行
			rs = st.executeQuery();
			BookBean bean = null;
			if (rs.next()) {
				String title = rs.getString("title");
				String author = rs.getString("author");
				String publisher = rs.getString("publisher");
				Date publishDate = rs.getDate("publishDate");
				int categoryCode = rs.getInt("categoryCode");

				bean = new BookBean(isbn, title, author, publisher,
						publishDate, categoryCode);
			}
			return bean;
		}catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗してしまいました……。");
		}finally {
			try {
				if(st!=null)st.close();
				close();
			}catch (Exception e) {
				throw new DAOException("リソースの解放に失敗してしまいました……。");
			}
		}
	}


	//出版日の取得
	public Date getPublishDate(int bookId) throws DAOException {
			if(con==null)
				getConnection();
			PreparedStatement st=null;
			ResultSet rs=null;
			try {
				String sql="SELECT publishdate FROM bookcatalog bc JOIN bookledger bl ON bc.isbn=bl.isbn WHERE bookId=?";

				st=con.prepareStatement(sql);
				st.setInt(1, bookId);
				rs=st.executeQuery();
				Date publishDate=new Date(0);
				while(rs.next()) {
				publishDate=rs.getDate("publishdate");
					}
				return publishDate;

				}catch (Exception e) {
				e.printStackTrace();
				throw new DAOException("レコードの取得に失敗してしまいました……。");
			}finally {
				try {
					if(rs!=null)rs.close();
					if(st!=null)st.close();
					close();
				}catch (Exception e) {
					throw new DAOException("リソースの解放に失敗してしまいました……。");
				}
			}
		}




	private void getConnection() throws DAOException{
		try {
			Class.forName("org.postgresql.Driver");
			String url="jdbc:postgresql:library";
			String user="postgres";
			String pass="himitu";
			con=DriverManager.getConnection(url,user,pass);
		}catch(Exception e) {
			throw new DAOException("接続に失敗しました");
		}
	}
	private void close()throws SQLException{
		if(con!=null) {
			con.close();
			con=null;
		}
	}

/*
	//テスト用//
	public static void main(String[] args) throws Exception {
		try {
			BookDAO dao = new BookDAO();
*/
/*		    System.out.println("***********getCategoryCodeList*************");
			System.out.print(dao.getCategoryCodeList());
*/

/*		    System.out.println("************addBookCatalog(新規資料登録入力情報を資料目録に追加(addBookLedgerとセットで呼び出す))************");
		    dao.addBookCatalog("28", "テストBook", 0, "new", "LA", LibraryUtility.createSQLDate("2021-08-26"));
		    System.out.println("資料を資料目録に追加しました。");
*/
/*		    System.out.println("************addBookLedger(新規資料登録入力情報を資料台帳に追加(addBookCatalogとセットで呼び出す))************");
		    dao.addBookLedger("1234567891", LibraryUtility.createSQLDate("2021-08-26"));
		    System.out.println("資料を資料台帳に追加しました。");
*/
/*
			System.out.println("************getBookLedger(資料IDをもとにした資料情報の検索)************");
			System.out.println("資料ID＝２の資料情報");
			System.out.println(dao.getBookLedger(2));
*/
/*
			System.out.println("************upDateBookCatalog(資料情報変更資料目録の資料情報を変更(ISBN番号で特定、変更しない項目はNULL))************");
			dao.updateBookCatalog("1234567891", "Javaマスター", 1, null, null, null);
			System.out.println("ISBN番号＝1234567891の資料のタイトルをJavaマスターに変更しました");
*/
/*
			System.out.println("************upDateBookLedger(資料IDで検索し該当の資料台帳情報を変更(資料ID番号で特定、変更しない項目はNULL))************");
			dao.updateBookLedger(17, null, LibraryUtility.createSQLDate("2021-08-26"), "紛失");
			System.out.println("資料ID＝17の資料を廃棄しました");
*/
/*
			System.out.println("************getBookAllByIsbn(ISBNをもとにした資料情報の検索)************");
			System.out.println("ISBN＝1234567891の資料情報");
			System.out.println(dao.getBookAllByIsbn("1"));
*/
/*
			System.out.println("************getBookCatalogByIsbn(ISBNをもとにした資料目録情報の検索)************");
			System.out.println("ISBN＝1の資料情報");
			System.out.println(dao.getBookCatalogByIsbn("1"));
*/

/*
			System.out.println("************getBookAllByBookId(ISBNをもとにした資料情報の検索)************");
			System.out.println("資料ID＝18の資料情報");
			System.out.println(dao.getBookAllByBookId(18));
*/
/*
			System.out.println("************getBookLedgerByBookId(資料IDをもとにした資料台帳情報の検索)************");
			System.out.println("資料ID＝２の資料台帳情報");
			System.out.println(dao.getBookLedgerByBookId(2));
*/

/*
			System.out.println("************getPublishDate(bookIdをもとにした出版日の検索)************");
			System.out.println("資料ID＝２の資料の出版日");
			System.out.println(dao.getPublishDate(2));
*/
/*
		} catch (DAOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
					}
				}
*/
}