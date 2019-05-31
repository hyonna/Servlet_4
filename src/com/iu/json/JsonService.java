package com.iu.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.iu.board.BoardDTO;
import com.iu.board.comment.CommentsDAO;
import com.iu.board.comment.CommentsDTO;
import com.iu.board.notice.NoticeDAO;
import com.iu.board.qna.QnaDAO;
import com.iu.page.SearchMakePage;
import com.iu.page.SearchRow;
import com.iu.util.DBConnector;
import com.sun.corba.se.spi.activation.Repository;

public class JsonService {
	
	public void test2(HttpServletRequest request, HttpServletResponse response) {
		
		CommentsDAO commentsDAO = new CommentsDAO();
		int num = Integer.parseInt(request.getParameter("num"));
		SearchMakePage s = new SearchMakePage(1, "", "");
		SearchRow searchRow = s.makeRow();
		
		Connection con=null;
		List<CommentsDTO> ar = null;
		try {
			con = DBConnector.getConnect();
			ar = commentsDAO.selectList(searchRow, num, con);
			
			JSONArray jsonAr = new JSONArray(); //[]
			
			for (CommentsDTO commentsDTO : ar) {
				
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("name", commentsDTO.getName());
				jsonObject.put("contents", commentsDTO.getContents());

				jsonAr.add(jsonObject);
				
			}
			
			JSONObject js = new JSONObject();
			js.put("ar", jsonAr);
			
			PrintWriter out = response.getWriter();
			out.println(js.toJSONString());
			out.close();
			
			
//			String result = "[";
//			
//			for (CommentsDTO commentsDTO : ar) {
//				
//				result = result+"{\"name\":\""+commentsDTO.getName()+"\",\"comments\":\""+commentsDTO.getContents()+"\"}";
//			}
//			
//			result = result+"]";
//			
//			PrintWriter out = response.getWriter();
//			out.println(result);
//			out.close();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public void test1(HttpServletRequest request, HttpServletResponse response) {
		
		//this.test2(request, response);
		
		int num = Integer.parseInt(request.getParameter("num"));
		QnaDAO qnaDAO = new QnaDAO();
		Connection con=null;
		try {
			con = DBConnector.getConnect();
			BoardDTO boardDTO = qnaDAO.selectOne(num, con);
			
			JSONObject jsonObject = new JSONObject(); // {} 를 만들었다는 뜻, json 라이브러리
			jsonObject.put("name", boardDTO.getName()); //{"name":"test"}
			jsonObject.put("contents", boardDTO.getContents());
			jsonObject.put("title", boardDTO.getTitle());
			
			PrintWriter out = response.getWriter();
			out.println(jsonObject.toJSONString());
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
