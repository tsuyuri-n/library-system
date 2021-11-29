package library.util;

import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;



public class LibraryUtility {
	public static Date createSQLDate(String date) throws ParseException {
		return date != null ? Date.valueOf(date) : Date.valueOf(LocalDate.now());
	}
	public static Date createSQLDate() throws ParseException {
		return Date.valueOf(LocalDate.now());
	}
	public static Date createSQLDate(String date, Integer interval) throws ParseException {
		return new Date(createSQLDate(date).getTime() + 1000L * 60 * 60 * 24 * interval);
	}
	public static Date createSQLDate(java.util.Date date) throws ParseException {
		return new Date(date.getTime());
	}
	public static void main(String[] args) {
		try {
			System.out.println(LibraryUtility.createSQLDate("2001-01-01"));
			System.out.println(LibraryUtility.createSQLDate("2001-01-01", 100));
			System.out.println(LibraryUtility.createSQLDate());
			System.out.println(LibraryUtility.createSQLDate(null, 100));
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}
