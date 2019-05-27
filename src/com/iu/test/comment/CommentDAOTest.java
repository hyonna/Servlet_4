package com.iu.test.comment;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.iu.board.comment.CommentsDAO;
import com.iu.board.comment.CommentsDTO;
import com.iu.util.DBConnector;

public class CommentDAOTest {
	private CommentsDAO commentDAO;
	private Connection con;
	
	public CommentDAOTest() {
		commentDAO = new CommentsDAO();
	}
	
	@BeforeClass
	public static void start() {
		//제일 처음에 한번 하는 테스트
	}
	
	@Before
	public void s() {
		try {
			con = DBConnector.getConnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

	@Test
	public void insertTest() throws Exception{
		
		CommentsDTO commentDTO = new CommentsDTO();
		commentDTO.setCnum(21);
		commentDTO.setNum(2);
		commentDTO.setName("iu");
		commentDTO.setContents("contents");
		
		int result = commentDAO.insert(commentDTO, con);
		
		assertEquals(1, result);
		
		
	}
	
	
	
	@After
	public void a() {
		
		try {
			con.rollback();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@AfterClass
	public static void after() {
		//테스트가 다 끝난 후 하는 테스트
		
	}

}
