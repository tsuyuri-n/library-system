package library.service;

import java.sql.Date;
import java.text.ParseException;
import java.util.List;

import library.bean.BorrowReturnBean;
import library.dao.BorrowReturnDAO;
import library.dao.DAOException;;


public class BoxReturnService {



	public List<BorrowReturnBean> checkInfo(int bookId)
			throws ServiceException, DAOException, ParseException {
		//BorrowReturnDAOのborrowBookで会員ID、資料ID、貸出年月日、返却期日を追加
		try {
		BorrowReturnDAO dao = new BorrowReturnDAO();
		return dao.searchBorrow(null, bookId, null, null);

		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	public void returnById(int bookId,Date date)
			throws ServiceException, DAOException, ParseException {
		//BorrowReturnDAOのborrowBookで会員ID、資料ID、貸出年月日、返却期日を追加
		try {
		BorrowReturnDAO dao = new BorrowReturnDAO();
		dao.returnByBookId(bookId,date);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage());
		}
	}


}
