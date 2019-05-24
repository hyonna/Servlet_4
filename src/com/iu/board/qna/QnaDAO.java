package com.iu.board.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.iu.board.BoardDAO;
import com.iu.board.BoardDTO;
import com.iu.page.SearchRow;
import com.iu.util.DBConnector;

public class QnaDAO implements BoardDAO{

	@Override
	public int getNum() throws Exception {
		
		int result = 0;
		
		Connection con = DBConnector.getConnect();
			
		String sql = "select qna_seq.nextval from dual"; //notice_seq.nextval 이라는 컬럼명이 없으니까 가상의 테이블명
			
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
		
		String sql = "select * from "
				+ "(select rownum R, Q.* from "
				+ "(select * from qna where " + searchRow.getSearch().getKind() + " like ? order by ref desc, step asc) Q) "
				+ "where R between ? and ?";
		
		PreparedStatement st = con.prepareStatement(sql);
		
		st.setString(1, "%"+searchRow.getSearch().getSearch()+"%");
		st.setInt(2, searchRow.getStartRow());
		st.setInt(3, searchRow.getLastRow());
		
		ResultSet rs = st.executeQuery();
		
		ArrayList<BoardDTO> ar = new ArrayList<BoardDTO>();
		
		while(rs.next()) {
			
			QnaDTO qnaDTO = new QnaDTO();
			
			qnaDTO.setNum(rs.getInt("num"));
			qnaDTO.setTitle(rs.getString("title"));
			qnaDTO.setContents(rs.getString("contents"));
			qnaDTO.setName(rs.getString("name"));
			qnaDTO.setReg_date(rs.getDate("reg_date"));
			qnaDTO.setHit(rs.getInt("hit"));
			qnaDTO.setRef(rs.getInt("ref"));
			qnaDTO.setStep(rs.getInt("step"));
			qnaDTO.setDepth(rs.getInt("depth"));
			
			ar.add(qnaDTO);
			
		}
		
		DBConnector.disConnect(con, st, rs);
		
		return ar;
	}

	@Override
	public BoardDTO selectOne(int num, Connection con) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(BoardDTO boardDTO, Connection con) throws Exception {
		
		int result=0;
		String sql ="insert into qna values(?,?,?,?,sysdate,0,?,0,0)";
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, boardDTO.getNum());
		st.setString(2, boardDTO.getTitle());
		st.setString(3, boardDTO.getContents());
		st.setString(4, boardDTO.getName());
		st.setInt(5, boardDTO.getNum());
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
	
	
	//답글 메소드 추가
	
	public int reply(QnaDTO qnaDTO) throws Exception {
		
		return 0;
	}
	

}
