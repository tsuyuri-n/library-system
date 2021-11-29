package library.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import library.bean.MemberBean;

public class MemberDAO {
	private Connection con;

	public MemberDAO() throws DAOException{
		// データベースへのコネクションが確立
		getConnection();
	}

	public MemberBean addMember(String name, String address, String tel, String email, Date birth, Date joined) throws DAOException{
		if(con == null)
			getConnection();
		PreparedStatement st=null;
		try {
			MemberBean bean = null;
			String sql="INSERT INTO memberlist(memberid, name, address, tel, email, birth, joined) VALUES(?, ?, ?, ?, ?, ?, ?)";
			// PreparedStatementオブジェクトの取得
			st=con.prepareStatement(sql);
			int id = DAOUtility.getNextVal(con, "memberlist_memberid_seq");
			st.setInt(1, id);
			st.setString(2, name);
			st.setString(3, address);
			st.setString(4, tel);
			st.setString(5, email);
			st.setDate(6, birth);
			st.setDate(7, joined);
			// SQLの実行
			int cRow = st.executeUpdate();
			if (cRow > 0) {
				bean = searchMember(id);
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

	public MemberBean updateMember(int memberId, String name, String address, String tel, String email, Date out) throws DAOException{
		if(con == null)
			getConnection();
		PreparedStatement st=null;
		try {
			String sql="UPDATE memberlist SET name = COALESCE(?, name), address = COALESCE(?, address), tel= COALESCE(?, tel), email = COALESCE(?, email), out = COALESCE(?, out) WHERE memberid= ?";

			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setString(1,name);
			st.setString(2, address);
			st.setString(3, tel);
			st.setString(4, email);
			st.setDate(5, out);
			st.setInt(6, memberId);
			// SQLの実行
//			st.executeUpdate();
			int cRow = st.executeUpdate();
			MemberBean bean = null;
			if (cRow > 0) {
				bean = searchMember(memberId);
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

	public MemberBean searchMemberIdByPhone(String tel,String email) throws DAOException {
		if(con==null)
			getConnection();
		PreparedStatement st=null;
		ResultSet rs = null;
		try {
			String sql="SELECT memberid FROM memberlist WHERE (tel = COALESCE(?,tel) AND email =COALESCE(?,email)) IS NOT FALSE";

			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setString(1,tel);
			st.setString(2, email);
			// SQLの実行
			rs = st.executeQuery();

			// 結果の取得および表示
			MemberBean bean = null;
			if (rs.next()) {
				bean = searchMember(rs.getInt("memberId"));
			}
			// カテゴリ一覧をListとして返す
			// falseならばnullを返す
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


	public MemberBean searchMember(int memberId) throws DAOException {
		if (con == null)
			getConnection();

		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			// SQL文の作成
			String sql = "SELECT * FROM memberlist WHERE memberId = ?";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setInt(1, memberId);
			// SQLの実行
			rs = st.executeQuery();
			// 結果の取得および表示
			MemberBean bean = null;
			if (rs.next()) {
				String name = rs.getString("name");
				String address = rs.getString("address");
				String tel = rs.getString("tel");
				String email = rs.getString("email");
				Date birth = rs.getDate("birth");
				Date joined = rs.getDate("joined");
				Date out = rs.getDate("out");
				bean = new MemberBean(memberId, name, address, tel, email, birth, joined, out);
			}
			// カテゴリ一覧をListとして返す
			return bean;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		} finally {
			try {
				// リソースの開放
				if(rs != null) rs.close();
				if(st != null) st.close();
				close();
			} catch (Exception e) {
				throw new DAOException("リソースの開放に失敗しました。");
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

	// mainメソッド作成
	// インスタンス作成
	// 戻り値の出力
	// なければSQLで出力

	// テスト用
//	public static void main(String[] args) {
//		try {
//			MemberDAO dao = new MemberDAO();
//			MemberBean bean = null;

//			System.out.println();
//			System.out.println("---------- addMember ----------");
//			bean = dao.addMember("テスト 六郎","東京都新宿区テストビル6-66","090-666-6666","test.666@mail", LibraryUtility.createSQLDate("2000-01-01"), LibraryUtility.createSQLDate("2021-01-01"));
//			System.out.println(bean);
//
//			System.out.println();
//			System.out.println("---------- updateMember ----------");
//			bean = dao.updateMember(25, null, null, "03-1111-0000", null, null);
//			System.out.println(bean);
//
//			System.out.println();
//			System.out.println("---------- updateMember (delete) ----------");
//			bean = dao.updateMember(25, null, null, null, null, LibraryUtility.createSQLDate("2021-08-24"));
//			System.out.println(bean);
//
//			System.out.println();
//			System.out.println("---------- searchMemberIdByPhone (tel) ----------");
//			System.out.println("tel:090-111-1111");
//			bean = dao.searchMemberIdByPhone("090-111-1111", null);
//			System.out.println(bean);
//
//			System.out.println();
//			System.out.println("---------- searchMemberIdByPhone (email) ----------");
//			System.out.println("mail:test.111.mail");
//			bean = dao.searchMemberIdByPhone(null, "test.111.mail");
//			System.out.println(bean);
//
//			System.out.println();
//			System.out.println("---------- searchMember ----------");
//			System.out.println("memberId:1");
//			bean = dao.searchMember(1);
//			System.out.println(bean);
//		} catch (Exception e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//	}

}
