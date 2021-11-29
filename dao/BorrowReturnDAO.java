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
import library.bean.BorrowReturnBean;



public class BorrowReturnDAO {
	private Connection con;
	public BorrowReturnDAO() throws DAOException{
		getConnection();
	}
	public void borrowBook(Integer memberId, Integer bookId, Date borrowed, Date deadLine) throws DAOException{
		if(con==null)
			getConnection();
		PreparedStatement st=null;
		try {
			String sql="INSERT INTO borrowlog(memberid,bookid,borrowed,deadline) VALUES(?, ?, ?, ?)";

			st=con.prepareStatement(sql);
			st.setInt(1, memberId);
			st.setInt(2, bookId);
			st.setDate(3, borrowed);
			st.setDate(4, deadLine);
			st.executeUpdate();
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

	public void updateBorrowLog(Integer borrowId, Integer memberId, Integer bookId, Date borrowed,Date deadLine, Date returned)
			throws DAOException{
		if(con==null)
			getConnection();
		PreparedStatement st=null;
		try {
			String sql="UPDATE borrowlog SET memberid = COALESCE(?::INTEGER, memberid), bookid =COALESCE(?::INTEGER, bookid), borrowed = COALESCE(?, borrowed), deadline =COALESCE(?, deadline), returned = COALESCE(?, returned) WHERE borrowid = ?";

			st=con.prepareStatement(sql);
			st.setString(1, memberId==null ? null : "" +memberId);
			st.setString(2, bookId==null ? null : "" +bookId);
			st.setDate(3, borrowed);
			st.setDate(4, deadLine);
			st.setDate(5, returned);
			st.setInt(6, borrowId);
			st.executeUpdate();
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

	//図書ボックス返却
	public void returnByBookId(int bookId, Date returned)
			throws DAOException{
		if(con==null)
			getConnection();
		PreparedStatement st=null;
		try {
			String sql="UPDATE borrowlog SET returned= ? WHERE bookId=? AND returned ISNULL";

			st=con.prepareStatement(sql);
			st.setDate(1,returned);
			st.setInt(2, bookId);

			st.executeUpdate();
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


// 間違えてBookBean形式でとってくるのを作っていました、使うことあれば復活させてください
	public List<BookBean> searchBorrowBB(Integer memberId, Integer bookId, Date borrowed, Date deadLine) throws DAOException{
		if(con==null)
			getConnection();
		PreparedStatement st=null;
		ResultSet rs=null;
		try {
			String sql="SELECT br.bookid,title, deadline,bl.isbn,title,publisher,publishDate,arrival,Discarded,reason,cc.categorycode,categoryname FROM borrowlog br JOIN bookledger bl ON br.bookid =bl.bookid JOIN bookcatalog bc ON bl.isbn=bc.isbn JOIN categorycodelist cc ON bc.categorycode=cc.categorycode WHERE (memberId = COALESCE(?::INTEGER,memberid) AND br.bookid = COALESCE(?::INTEGER,br.bookid) AND borrowed = COALESCE(?,borrowed) AND deadline = COALESCE(?,deadline)) IS NOT FALSE";

			st=con.prepareStatement(sql);
			st.setString(1, memberId==null ? null : "" +memberId);
			st.setString(2, bookId==null ? null : "" +bookId);
			st.setDate(3, borrowed);
			st.setDate(4, deadLine);
			rs=st.executeQuery();
			List<BookBean>list=new ArrayList<BookBean>();
			while(rs.next()) {
				String isbn=rs.getString("isbn");
				String title =rs.getString("title");
				String author =rs.getString("author");
				String publisher=rs.getString("publisher");
				Date publishDate =rs.getDate("publishDate");
				int bookId2 = rs.getInt("bookId");
				Date arrival = rs.getDate("arrival");
				Date discarded = rs.getDate("Discarded");
				String reason = rs.getString("reason");
				int categoryCode = rs.getInt("categorycode");
				String categoryName = rs.getString("categoryName");
				BookBean bean=new BookBean(isbn,title,author,publisher,publishDate,bookId2,arrival,discarded,reason,categoryCode,categoryName);
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

	public List<BookBean> searchNotReturnedBorrowBB(Integer memberId, Integer bookId, Date borrowed, Date deadLine) throws DAOException{
		if(con==null)
			getConnection();
		PreparedStatement st=null;
		ResultSet rs=null;
		try {
			String sql="SELECT br.bookid,title, deadline,bl.isbn,title,publisher,publishDate,arrival,Discarded,reason,cc.categorycode,categoryname FROM borrowlog br JOIN bookledger bl ON br.bookid =bl.bookid JOIN bookcatalog bc ON bl.isbn=bc.isbn JOIN categorycodelist cc ON bc.categorycode=cc.categorycode WHERE (memberId = COALESCE(?::INTEGER,memberid) AND br.bookid = COALESCE(?::INTEGER,br.bookid) AND borrowed = COALESCE(?,borrowed) AND deadline = COALESCE(?,deadline) AND returned ISNULL) IS NOT FALSE";

			st=con.prepareStatement(sql);
			st.setString(1, memberId==null ? null : "" +memberId);
			st.setString(2, bookId==null ? null : "" +bookId);
			st.setDate(3, borrowed);
			st.setDate(4, deadLine);
			rs=st.executeQuery();
			List<BookBean>list=new ArrayList<BookBean>();
			while(rs.next()) {
				String isbn=rs.getString("isbn");
				String title =rs.getString("title");
				String author =rs.getString("author");
				String publisher=rs.getString("publisher");
				Date publishDate =rs.getDate("publishDate");
				int bookId2 = rs.getInt("bookId");
				Date arrival = rs.getDate("arrival");
				Date discarded = rs.getDate("Discarded");
				String reason = rs.getString("reason");
				int categoryCode = rs.getInt("categorycode");
				String categoryName = rs.getString("categoryName");
				BookBean bean=new BookBean(isbn,title,author,publisher,publishDate,bookId2,arrival,discarded,reason,categoryCode,categoryName);
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

	public List<BorrowReturnBean> searchBorrow(Integer memberId, Integer bookId, Date borrowed, Date deadLine) throws DAOException{
		if(con==null)
			getConnection();
		PreparedStatement st=null;
		ResultSet rs=null;
		try {
			String sql="SELECT title,borrowid,memberid,bl.bookid,borrowed,deadline,returned FROM borrowlog br JOIN bookledger bl ON br.bookid=bl.bookid JOIN bookcatalog bc ON bl.isbn=bc.isbn WHERE (memberId = COALESCE(?::INTEGER,memberid) AND bl.bookid = COALESCE(?::INTEGER,bl.bookid) AND borrowed = COALESCE(?,borrowed) AND deadline = COALESCE(?,deadline) AND returned ISNULL) IS NOT FALSE";
			st=con.prepareStatement(sql);
			st.setString(1, memberId==null ? null : "" +memberId);
			st.setString(2, bookId==null ? null : "" +bookId);
			st.setDate(3, borrowed);
			st.setDate(4, deadLine);
			rs=st.executeQuery();
			List<BorrowReturnBean>list=new ArrayList<BorrowReturnBean>();
			while(rs.next()) {
				String title=rs.getString("title");
				int borrowId=rs.getInt("borrowId");
				int memberId2=rs.getInt("memberId");
				int bookId2 = rs.getInt("bookId");
				Date borrowed2 = rs.getDate("borrowed");
				Date deadLine2 = rs.getDate("deadline");
				Date returned = rs.getDate("returned");
				BorrowReturnBean bean=new BorrowReturnBean(title,borrowId,memberId2,bookId2,borrowed2,deadLine2,returned);
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

	public List<BorrowReturnBean> searchRecord(Integer memberId, Integer bookId, Date borrowed, Date deadLine) throws DAOException{
		if(con==null)
			getConnection();
		PreparedStatement st=null;
		ResultSet rs=null;
		try {
			String sql="SELECT title,borrowid,memberid,bl.bookid,borrowed,deadline,returned FROM borrowlog br JOIN bookledger bl ON br.bookid=bl.bookid JOIN bookcatalog bc ON bl.isbn=bc.isbn WHERE (memberId = COALESCE(?::INTEGER,memberid) AND bl.bookid = COALESCE(?::INTEGER,bl.bookid) AND borrowed = COALESCE(?,borrowed) AND deadline = COALESCE(?,deadline) AND returned IS NOT NULL) IS NOT FALSE ORDER BY returned DESC";
			st=con.prepareStatement(sql);
			st.setString(1, memberId==null ? null : "" +memberId);
			st.setString(2, bookId==null ? null : "" +bookId);
			st.setDate(3, borrowed);
			st.setDate(4, deadLine);
			rs=st.executeQuery();
			List<BorrowReturnBean>list=new ArrayList<BorrowReturnBean>();
			while(rs.next()) {
				String title=rs.getString("title");
				int borrowId=rs.getInt("borrowId");
				int memberId2=rs.getInt("memberId");
				int bookId2 = rs.getInt("bookId");
				Date borrowed2 = rs.getDate("borrowed");
				Date deadLine2 = rs.getDate("deadline");
				Date returned = rs.getDate("returned");
				BorrowReturnBean bean=new BorrowReturnBean(title,borrowId,memberId2,bookId2,borrowed2,deadLine2,returned);
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
	public static void main(String[] args) {
		try {
			BorrowReturnDAO dao = new BorrowReturnDAO();
			Date now=new Date(System.currentTimeMillis());
		    dao.returnByBookId(1,now);
		} catch (DAOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
					}
				}
*/

	}
