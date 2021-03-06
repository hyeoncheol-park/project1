package com.undercar.util.page;

import java.sql.PreparedStatement;

public class SearchCondition {

	// 검색을 SQL쿼리에 추가하는 메서드
	public static String getSearchSQLWithWhere(PageObject pageObject) {
		System.out.println("SearchCondition.getSearchSQLWithWhere()");
		String sql = "";
		if(pageObject.getWord() != null && !pageObject.getWord().equals("")) {
			sql += " where 1 = 2 ";
			if(pageObject.getKey().indexOf("t") >= 0)
				sql += " or title like ? ";
			if(pageObject.getKey().indexOf("c") >= 0)
				sql += " or content like ? ";
			if(pageObject.getKey().indexOf("w") >= 0)
				sql += " or writer like ? ";
		}
		return sql;
	}
	public static String getSearchSQLWithWhereqna(PageObject pageObject) {
		System.out.println("SearchCondition.getSearchSQLWithWhereqna()");
		String sql = "";
		if(pageObject.getWord() != null && !pageObject.getWord().equals("")) {
			sql += " where 1 = 2 ";
			if(pageObject.getKey().indexOf("t") >= 0)
				sql += " or title like ? ";
			if(pageObject.getKey().indexOf("c") >= 0)
				sql += " or content like ? ";
			if(pageObject.getKey().indexOf("w") >= 0)
				sql += " or id like ? ";
		}
		return sql;
	}
	
	// 회원검색을 위해  SQL쿼리에 추가하는 메서드
	public static String getMemberSearchSQLWithWhere(PageObject pageObject) {
		System.out.println("SearchCondition.getSearchSQLWithWhere()");
		String sql = "";
		if(pageObject.getWord() != null && !pageObject.getWord().equals("")) {
			sql += " where 1 = 2 ";
			if(pageObject.getKey().indexOf("t") >= 0)
				sql += " or id like ? ";
			if(pageObject.getKey().indexOf("c") >= 0)
				sql += " or name like ? ";
			if(pageObject.getKey().indexOf("w") >= 0)
				sql += " or tel like ? ";
		}
		return sql;
	}

	// 메세지 검색을 위해  SQL쿼리에 추가하는 메서드
	public static String getMessageSearchSQLWithWhere(PageObject pageObject) {
		System.out.println("SearchCondition.getMessageSearchSQLWithWhere()");
		String sql = "";
		if(pageObject.getWord() != null && !pageObject.getWord().equals("")) {
			sql += " where 1 = 2 ";
			if(pageObject.getKey().indexOf("s") >= 0)
				sql += " or sender like ? ";
			if(pageObject.getKey().indexOf("a") >= 0)
				sql += " or accepter like ? ";
			if(pageObject.getKey().indexOf("c") >= 0)
				sql += " or content like ? ";
			if(pageObject.getKey().indexOf("t") >= 0)
				sql += " or title like ? ";
		}
		return sql;
	}// end of getMessageSearchSQLWithWhere()
	
	// 실행객체에 검색 데이터를 셋팅하는 메서드
	public static int setPreparedStatement(PreparedStatement pstmt, 
			PageObject pageObject, int idx) throws Exception {
		if(pageObject.getWord() != null && !pageObject.getWord().equals("")) {
			if(pageObject.getKey().indexOf("t") >= 0)
				pstmt.setString(idx++, "%" + pageObject.getWord() + "%");
			if(pageObject.getKey().indexOf("c") >= 0)
				pstmt.setString(idx++, "%" + pageObject.getWord() + "%");
			if(pageObject.getKey().indexOf("w") >= 0)
				pstmt.setString(idx++, "%" + pageObject.getWord() + "%");
		}
		return idx;
	}// end of setPreparedStatement()
	
	// 실행객체에 검색 데이터를 셋팅하는 메서드 - message Version
	public static int setMessagePreparedStatement(PreparedStatement pstmt, 
			PageObject pageObject, int idx) throws Exception {
		if(pageObject.getWord() != null && !pageObject.getWord().equals("")) {
			if(pageObject.getKey().indexOf("s") >= 0)
				pstmt.setString(idx++, "%" + pageObject.getWord() + "%");
			if(pageObject.getKey().indexOf("a") >= 0)
				pstmt.setString(idx++, "%" + pageObject.getWord() + "%");
			if(pageObject.getKey().indexOf("c") >= 0)
				pstmt.setString(idx++, "%" + pageObject.getWord() + "%");
			if(pageObject.getKey().indexOf("t") >= 0)
				pstmt.setString(idx++, "%" + pageObject.getWord() + "%");
		}
		return idx;
	}// end of setMessagePreparedStatement()
	
	
}
