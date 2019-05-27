package com.iu.board.comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.iu.page.SearchRow;
import com.iu.util.DBConnector;

public class CommentsDAO {
	
	
	
	public List<CommentsDTO> selectList(SearchRow searchRow, Connection con) throws Exception {
		
		ArrayList<CommentsDTO> ar = new ArrayList<CommentsDTO>();
		
		String sql = "select * from"
				+ "(select rownum R, C.* from "
				+ "(select * from comments order by desc) C) "
				+ "where R between ? and ?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setInt(1, searchRow.getStartRow());
		st.setInt(2, searchRow.getLastRow());
		
		ResultSet rs = st.executeQuery();
		
		while(rs.next()) {
			
			CommentsDTO commentsDTO = new CommentsDTO();
			
			commentsDTO.setCnum(rs.getInt("cnum"));
			commentsDTO.setNum(rs.getInt("num"));
			commentsDTO.setName(rs.getString("name"));
			commentsDTO.setContents(rs.getString("contents"));
			commentsDTO.setReg_date(rs.getDate("reg_date"));
			
			ar.add(commentsDTO);
			
		}

		rs.close();
		st.close();
		
		return ar;
	}
	
	public int update(CommentsDTO commentsDTO, Connection con) throws Exception {
		
		String sql = "update comments set contents=? where cnum=?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, commentsDTO.getContents());
		st.setInt(2, commentsDTO.getCnum());
		
		int result = st.executeUpdate();
		
		st.close();
		
		return result;
		
		
	}
	
	
	public int delete(int cnum, Connection con) throws Exception {
		
		String sql = "delete comments where cnum=?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setInt(1, cnum);
		
		int result = st.executeUpdate();
		
		st.close();
		
		return result;
		
	}
	
	public int insert(CommentsDTO commentsDTO, Connection con) throws Exception {
		
		int result = 0;
		
		String sql = "insert into comments values(qna_seq.nextval, ?, ?, ?, sysdate)";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setInt(1, commentsDTO.getNum());
		st.setString(2, commentsDTO.getName());
		st.setString(3, commentsDTO.getContents());
		
		result = st.executeUpdate();
		
		st.close();
		
		return result;
		
	}

}
