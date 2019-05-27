package com.iu.board.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.iu.board.BoardDAO;
import com.iu.board.BoardDTO;
import com.iu.page.SearchRow;
import com.iu.util.DBConnector;

public class NoticeDAO implements BoardDAO{

	@Override
	public int getNum() throws Exception {
		int result = 0;
		
		Connection con = DBConnector.getConnect();
		
		String sql = "select notice_seq.nextval from dual"; //notice_seq.nextval 이라는 컬럼명이 없으니까 가상의 테이블명
		
		PreparedStatement st = con.prepareStatement(sql);
		
		ResultSet rs = st.executeQuery();
		
		rs.next();
		
		result = rs.getInt(1);
		
		DBConnector.disConnect(con, st, rs);
		
		return result;
	}

	@Override
	public int getTotalCount(SearchRow searchRow, Connection con) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<BoardDTO> selectList(SearchRow searchRow, Connection con) throws Exception {
		
		ArrayList<BoardDTO> ar = new ArrayList<BoardDTO>();
		
		String sql = "select * from " + 
				"(select rownum R, P.* from " + 
				"(select * from notice where "+ searchRow.getSearch().getKind() +" like ? order by num desc) P) " + 
				"where r between ? and ?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		st.setInt(2, searchRow.getStartRow());
		st.setInt(3, searchRow.getLastRow());
		
		ResultSet rs = st.executeQuery();
		
		
		while(rs.next()) {
			
			NoticeDTO noticeDTO = new NoticeDTO();
			
			noticeDTO.setNum(rs.getInt("num"));
			noticeDTO.setTitle(rs.getString("title"));
			noticeDTO.setName(rs.getString("name"));
			noticeDTO.setReg_date(rs.getDate("reg_date"));
			noticeDTO.setHit(rs.getInt("hit"));
			
			
			ar.add(noticeDTO);
			
			
		}
		
		rs.close();
		st.close();
		
		return ar;
		
	}

	@Override
	public BoardDTO selectOne(int num, Connection con) throws Exception {
		NoticeDTO noticeDTO= null;
		String sql ="select * from notice where num=?";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, num);
		ResultSet rs = st.executeQuery();
		if(rs.next()) {
			noticeDTO = new NoticeDTO();
			noticeDTO.setNum(rs.getInt("num"));
			noticeDTO.setTitle(rs.getString("title"));
			noticeDTO.setContents(rs.getString("contents"));
			noticeDTO.setName(rs.getString("name"));
			noticeDTO.setReg_date(rs.getDate("reg_date"));
			noticeDTO.setHit(rs.getInt("hit"));
		}
		
		rs.close();
		st.close();
		
		return noticeDTO;
	}
	

	@Override
	public int insert(BoardDTO boardDTO, Connection con) throws Exception {
		
		int result=0;
		
		String sql ="insert into notice values(?,?,?,?, sysdate,0)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, boardDTO.getNum());
		st.setString(2, boardDTO.getTitle());
		st.setString(3, boardDTO.getContents());
		st.setString(4, boardDTO.getName());
		result = st.executeUpdate();
		st.close();
		
		return result;
	
	}

	@Override
	public int update(BoardDTO boardDTO, Connection con) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int num, Connection con) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
	

}
