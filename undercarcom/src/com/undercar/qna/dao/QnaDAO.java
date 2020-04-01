package com.undercar.qna.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.undercar.qna.dto.QnaDTO;
import com.undercar.util.db.DBUtil;
import com.undercar.util.page.PageObject;
import com.undercar.util.page.SearchCondition;

public class QnaDAO {

public List<QnaDTO>adminlist(PageObject pageObject) throws Exception{
		
		List<QnaDTO> list =null;
		
		Connection con = DBUtil.getConnection();
		
		String sql = " select q.no, q.title, q.id, m.name, "
				+ " to_char(q.writeDate, 'yyyy-mm-dd') writeDate , "
				+ " q.levNo "
				+ " from qna q, member m "
				+ " where (q.id = m.id) "; // join 조건
		sql += "";
		if(pageObject.getWord() != null && !pageObject.getWord().equals("")) {
			
		sql +=" and  (1=2 ";
		if(pageObject.getKey().indexOf("t") >= 0)
			sql += " or q.title like ?  ";
		if(pageObject.getKey().indexOf("c") >= 0)
			sql += " or q.content like ? ";
		if(pageObject.getKey().indexOf("w") >= 0)
			sql += " or q.id like ? ";
		sql += " )";
		}
		sql += "order by refNo desc, ordNo asc";
		  
		  sql ="select rownum rnum, no,title, id,name,writeDate,levNo "
					+" from( " +sql +") ";
			sql="select * "
					+" from( " +sql +")"
					+" where rnum between ? and ?";

		System.out.println("QnaDAO.list().sql" + sql); 
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		int idx = 1;
		idx = SearchCondition.setPreparedStatement(pstmt, pageObject, idx);
		pstmt.setInt(idx++, pageObject.getStartRow());
		pstmt.setInt(idx++, pageObject.getEndRow());

		ResultSet rs = pstmt.executeQuery();
		 
		if(rs != null) {
			while(rs.next()) {
				if(list == null)
					list = new ArrayList<QnaDTO>();
				
				QnaDTO dto = new QnaDTO();
				dto.setNo(rs.getInt("no"));
				dto.setTitle(rs.getString("title"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setWriteDate(rs.getString("writeDate"));
				dto.setLevNo(rs.getInt("levNo"));
				
				list.add(dto);
			}
		}//if end
		DBUtil.close(con, pstmt, rs);
		
		System.out.println("qna리스트 "+list);
		
		
		return list;
		
		
	}//리스트 끝
	
	
	public List<QnaDTO>list(PageObject pageObject, String id) throws Exception{
		
		List<QnaDTO> list =null;
		String sql="";
		Connection con = DBUtil.getConnection();
		
		sql = " select q.no, q.title, q.id, m.name, "
				+ " to_char(q.writeDate, 'yyyy-mm-dd') writeDate , "
				+ " q.levNo,q.id1 "
				+ " from qna q, member m "
				+ " where (q.id = m.id)and (q.id = ? or q.id='admin')and q.id1=? "; // join 조건 

		sql += "";
		if(pageObject.getWord() != null && !pageObject.getWord().equals("")) {
			
		sql +=" and  (1=2 ";
		if(pageObject.getKey().indexOf("t") >= 0)
			sql += " or title like ?  ";
		if(pageObject.getKey().indexOf("c") >= 0)
			sql += " or q.content like ? ";
		if(pageObject.getKey().indexOf("w") >= 0)
			sql += " or m.name like ? ";
		sql +=")";
		}
		  sql += " order by refNo desc, ordNo asc";
		  

		  sql ="select rownum rnum, no,title, id,name,writeDate,levNo,id1 "

					+" from( " +sql +") ";
			sql="select * "
					+" from( " +sql +")"
					+" where rnum between ? and ?";

		System.out.println("QnaDAO.list().sql" + sql);
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		int idx = 1;
		pstmt.setString(idx++, id);
		pstmt.setString(idx++, id);
		idx = SearchCondition.setPreparedStatement(pstmt, pageObject, idx);
		pstmt.setInt(idx++, pageObject.getStartRow());
		pstmt.setInt(idx++, pageObject.getEndRow());

		ResultSet rs = pstmt.executeQuery();
		
		if(rs != null) {
			while(rs.next()) {
				if(list == null)
					list = new ArrayList<QnaDTO>();
				
				QnaDTO dto = new QnaDTO();
				dto.setNo(rs.getInt("no"));
				dto.setTitle(rs.getString("title"));
				dto.setId(rs.getString("id"));
				dto.setName(rs.getString("name"));
				dto.setWriteDate(rs.getString("writeDate"));
				dto.setLevNo(rs.getInt("levNo"));
				dto.setId1(rs.getString("id1"));

				
				list.add(dto);
				
			}
		}//if end
		DBUtil.close(con, pstmt, rs);
		
		System.out.println("qna리스트 "+list);
		
		
		return list;
		
		
	}//리스트 끝
	
public int getTotalRow(PageObject pageObject) throws Exception{
		
		System.out.println("QnaDAO.getTotalRow");
		
		int totalRow = 0;
		
		Connection con = DBUtil.getConnection();
		String sql = "select count(*) cnt from qna";
		
		System.out.println("QnaDAO.getTotal().sql"+sql);
		sql += SearchCondition.getSearchSQLWithWhereqna(pageObject);

		PreparedStatement pstmt = con.prepareStatement(sql);
		
		int idx = 1;
		idx = SearchCondition.setPreparedStatement(pstmt, pageObject, idx);
		ResultSet rs = pstmt.executeQuery();

		if(rs != null && rs.next()) {
			totalRow = rs.getInt("cnt");
		} // end of if(rs != null && rs.next())
		
		DBUtil.close(con, pstmt, rs);
		
		System.out.println("QnaDAO.getTotalRow(0.totalRow"+totalRow);
		
		return totalRow;
	} // end of view()
public int getTotalRow1(PageObject pageObject,String id) throws Exception{
	
	System.out.println("QnaDAO.getTotalRow");
	
	int totalRow = 0;
	
	Connection con = DBUtil.getConnection();
	String sql = "select count(*) cnt from qna"
			   +" where id = ? or id='admin' and id1=?"
			;
	
	System.out.println("QnaDAO.getTotal().sql"+sql);
	sql += SearchCondition.getSearchSQLWithWhere(pageObject);
	
	PreparedStatement pstmt = con.prepareStatement(sql);
	pstmt.setString(1, id);
	pstmt.setString(2, id);
	int idx = 1;
	idx = SearchCondition.setPreparedStatement(pstmt, pageObject, idx);
	ResultSet rs = pstmt.executeQuery();
	
	if(rs != null && rs.next()) {
		totalRow = rs.getInt("cnt");
	} // end of if(rs != null && rs.next())
	
	DBUtil.close(con, pstmt, rs);
	
	System.out.println("QnaDAO.getTotalRow(0.totalRow"+totalRow);
	
	return totalRow;
} // end of view()
	
	
	public QnaDTO view (int no) throws Exception{
		
		System.out.println("qna"+no);
		QnaDTO dto = null;
		
		Connection con = DBUtil.getConnection();
		
		String sql = " select q.no, q.title, q.content, q.id,m.name, "
				+ " to_char(q.writeDate, 'yyyy-mm-dd') writeDate , id1, "
				+ " q.refNo,q.ordNo,q.levNo,q.parentNo "
				+ " from qna q, member m where q.no = ?"//일반조건
				+ " and q.id = m.id "; //조인조건
	
	System.out.println("QnaDAO view sql "+sql);
	
	PreparedStatement pstmt = con.prepareStatement(sql);
	
	pstmt.setInt(1, no);
	
	ResultSet rs = pstmt.executeQuery();
	
	if(rs != null && rs.next()) {
		dto = new QnaDTO();
		
		dto.setNo(rs.getInt("no"));
		dto.setTitle(rs.getString("title"));
		dto.setContent(rs.getString("content"));
		dto.setId(rs.getString("id"));
		dto.setName(rs.getString("name"));
		dto.setWriteDate(rs.getString("writeDate"));
		dto.setRefNo(rs.getInt("refNO"));
		dto.setOrdNo(rs.getInt("ordNO"));
		dto.setLevNo(rs.getInt("levNO"));
		dto.setParentNo(rs.getInt("parentNO"));
		dto.setId1(rs.getString("id1"));
	}
	DBUtil.close(con, pstmt, rs);
	
	System.out.println("qnaDAOlist dto" +dto);
	
	return dto;
	
	}//뷰끝
	
  public int write(QnaDTO dto) throws Exception{
	  
	  System.out.println("qnaDAO.write.dto"+dto);
	  
	  Connection con = DBUtil.getConnection();
	  
	  String sql = " insert into qna(no, title, content,id,refNo,ordNo,levNo,id1) "
				+ " values(qna_seq.nextval, ?, ?,?,qna_seq.nextval,1,0,?) ";
		System.out.println("QnaDAO.write().sql:"+sql);
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setString(1, dto.getTitle());
		pstmt.setString(2, dto.getContent());
		pstmt.setString(3, dto.getId());
		pstmt.setString(4, dto.getId1());
		
		int result = pstmt.executeUpdate();
		
		System.out.println("질문하기 성공");
		
		DBUtil.close(con, pstmt);
		return result;
	  
  }//글쓰기 끝
  
  
  public void answer(QnaDTO dto) throws Exception{
	  
	  System.out.println("QnaDAO.answer.dto" + dto);
	  
	  Connection con =DBUtil.getConnection();
	  
		String sql = " insert into qna(no, title, content,id,refNo,ordNo,levNo,parentNo,id1) "
				+ " values(qna_seq.nextval,?,?,?,?,?,?,?,?) ";
		System.out.println("QnaDAO.write().sql:"+sql);
		
		PreparedStatement pstmt =con.prepareStatement(sql);
		
		pstmt.setString(1, dto.getTitle());
		pstmt.setString(2, dto.getContent());
		pstmt.setString(3, dto.getId());
		pstmt.setInt(4, dto.getRefNo());
		pstmt.setInt(5, dto.getOrdNo()+1);
		pstmt.setInt(6, dto.getLevNo()+1);
		pstmt.setInt(7, dto.getParentNo());
		pstmt.setString(8, dto.getId1());
		
		pstmt.executeUpdate();
		
		System.out.println("답변하기 성공"+dto);
		
		DBUtil.close(con, pstmt);
  }
  
  public void increaseOrdNo(QnaDTO dto) throws Exception{
	  
	  System.out.println("QnaDAO.increaseOrdNo update dto"+dto);
	  
	  Connection con = DBUtil.getConnection();
	  String sql = " update qna set ordNo = ordNo +1 "					
				+ " where refNo  = ? and ordNo > ? ";
		System.out.println("QnaDAO.update().sql:"+sql);
		
		PreparedStatement pstmt = con.prepareStatement(sql);
		
		pstmt.setInt(1, dto.getRefNo());
		pstmt.setInt(2, dto.getOrdNo());
		int result = pstmt.executeUpdate();
		
		if(result>0)
			System.out.println("순서번호 1증가 성공");
		else System.out.println("순서번호 1증가 실패");
		
	  DBUtil.close(con, pstmt);
  }
   
  public int update(QnaDTO dto) throws Exception{
		
		System.out.println("QnaDAO.update().dto:" + dto);
		Connection  con = DBUtil.getConnection();
		String sql = " update qna set title = ?, content = ? "					
				+ " where no = ? ";
		System.out.println("QnaDAO.update().sql:"+sql);
		PreparedStatement pstmt = con.prepareStatement(sql);
		pstmt.setString(1, dto.getTitle());
		pstmt.setString(2, dto.getContent());
		pstmt.setInt(3, dto.getNo());
		int result = pstmt.executeUpdate();
		if(result > 0 )
		System.out.println("글수정 성공");
		else System.out.println("글수정이 되지 않았습니다. -글번호를 확인하세요");
		DBUtil.close(con, pstmt);
		return result;
	} // end of update()	
public int delete(int no) throws Exception{
	
	System.out.println("QnaDAO.delete().dto:" + no);
	
Connection con = DBUtil.getConnection();
	String sql = " delete from qna  "
			  + " where no = ? ";
	System.out.println("QnaDAO.delete().sql:"+sql);
	PreparedStatement pstmt = con.prepareStatement(sql);

	pstmt.setInt(1, no);
	int result = pstmt.executeUpdate();
	if(result > 0 )
	System.out.println("글삭제 성공");
	else
		System.out.println("글삭제가 되지 않았습니다. -글번호를 확인해주세요");
	DBUtil.close(con, pstmt);
	return result;
} // end of delete()
}// dao끝