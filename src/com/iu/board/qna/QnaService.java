package com.iu.board.qna;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iu.action.Action;
import com.iu.action.ActionFoward;
import com.iu.board.BoardDTO;
import com.iu.page.SearchMakePage;
import com.iu.page.SearchPager;
import com.iu.page.SearchRow;
import com.iu.upload.UploadDAO;
import com.iu.upload.UploadDTO;
import com.iu.util.DBConnector;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class QnaService implements Action {
	
	private QnaDAO qnaDAO;
	private UploadDAO uploadDAO;
	
	public QnaService() {
		
		qnaDAO = new QnaDAO();
		uploadDAO = new UploadDAO();
	}
	
	//답글 메소드 추가
	public ActionFoward reply(HttpServletRequest request, HttpServletResponse response) {
		
		return null;
	}

	@Override
	public ActionFoward list(HttpServletRequest request, HttpServletResponse response) {
		ActionFoward actionFoward = new ActionFoward();
		
		
		
		
		//페이징처리
		int curPage = 1; //현재 페이지
		
		try {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		} catch (Exception e) {
			
		}
		
		String kind = request.getParameter("kind"); //c, t, w
		String search = request.getParameter("search"); //검색하기
		
		SearchMakePage s = new SearchMakePage(curPage, kind, search);
		
		//1.row
		SearchRow searchRow = s.makeRow();
		int totalCount = 0;
		Connection con = null;
		
		try {
			
			con = DBConnector.getConnect();
			List<BoardDTO> ar = qnaDAO.selectList(searchRow, con);
			
			//2.page
			totalCount = qnaDAO.getTotalCount(searchRow, con);
			request.setAttribute("list", ar);
			
			actionFoward.setCheck(true); //포워드로 보내겠다
			actionFoward.setPath("../WEB-INF/views/board/boardList.jsp");
		} catch (Exception e) {
			
			e.printStackTrace();
			request.setAttribute("message", "Server Error");
			request.setAttribute("path", "../index.do");
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/views/common/result.jsp");
			
		}
		
		SearchPager searchPager = s.makePage(totalCount);
		request.setAttribute("pager", searchPager);
		request.setAttribute("board", "qna");
		
		
		return actionFoward;
	}

	@Override
	public ActionFoward select(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionFoward insert(HttpServletRequest request, HttpServletResponse response) {
		
		ActionFoward actionForward = new ActionFoward();
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/board/boardWrite.jsp");
		String method = request.getMethod();
		if(method.equals("POST")) {
			String saveDirectory = request.getServletContext().getRealPath("upload");
			
			File file = new File(saveDirectory);
			if(!file.exists()) {
				file.mkdirs();
			}
			
			
			
			int maxPostSize=1024*1024*100;
			Connection con=null;
			try {
				MultipartRequest multipartRequest = new MultipartRequest(request, saveDirectory, maxPostSize, "UTF-8", new DefaultFileRenamePolicy());
			 	Enumeration<String> e= multipartRequest.getFileNames();//파일의 파라미터 이름들
			 	ArrayList<UploadDTO> ar = new ArrayList<UploadDTO>();
			 	while(e.hasMoreElements()) {
			 		String s = e.nextElement();
			 		String fname = multipartRequest.getFilesystemName(s);
			 		String oname = multipartRequest.getOriginalFileName(s);
			 		UploadDTO uploadDTO = new UploadDTO();
			 		uploadDTO.setFname(fname);
			 		uploadDTO.setOname(oname);
			 		ar.add(uploadDTO);
			 	}
			 	QnaDTO qnaDTO = new QnaDTO();
			 	qnaDTO.setTitle(multipartRequest.getParameter("title"));
			 	qnaDTO.setName(multipartRequest.getParameter("name"));
			 	qnaDTO.setContents(multipartRequest.getParameter("contents"));
			 	
			 	con = DBConnector.getConnect();
			 	
			 	//1.시퀀스번호
			 	int num = qnaDAO.getNum();
			 	qnaDTO.setNum(num);
			 	con.setAutoCommit(false);
			 	//2. qna insert
			 	num = qnaDAO.insert(qnaDTO, con);
			 	
			 	//3. upload insert
			 	for(UploadDTO uploadDTO:ar) {
			 		uploadDTO.setNum(qnaDTO.getNum());
			 		num = uploadDAO.insert(uploadDTO, con);
			 		if(num<1) {
			 			throw new Exception();
			 		}
			 	}
			 	
			 	con.commit();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}finally {
				try {
					con.setAutoCommit(true);
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			actionForward.setCheck(false);
			actionForward.setPath("./qnaList");
			
		}//post끝
		return actionForward;
	}

	@Override
	public ActionFoward update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionFoward delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
