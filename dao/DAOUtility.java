package library.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;



public class DAOUtility {
	public static int getNextVal(Connection con, String seq) throws DAOException {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			int bookID = 0;
			// SQL文の作成
			String sql = "SELECT nextval(?)";
			// PreparedStatementオブジェクトの取得
			st = con.prepareStatement(sql);
			st.setString(1, seq);
			// SQLの実行
			rs = st.executeQuery();
			if (rs.next()) {
				bookID = rs.getInt(1);
			}
			return bookID;
		} catch (Exception e) {
			e.printStackTrace();
			throw new DAOException("レコードの取得に失敗しました。");
		} finally {
			try {
				// リソースの開放
				if(rs != null) rs.close();
				if(st != null) st.close();
			} catch (Exception e) {
				throw new DAOException("リソースの開放に失敗しました。");
			}
		}
	}
}
