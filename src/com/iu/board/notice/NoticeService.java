package com.iu.board.notice;

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
import com.iu.action.ActionForward;
import com.iu.board.BoardDTO;
import com.iu.page.SearchMakePage;
import com.iu.page.SearchPager;
import com.iu.page.SearchRow;
import com.iu.upload.UploadDAO;
import com.iu.upload.UploadDTO;
import com.iu.util.DBConnector;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

public class NoticeService implements Action {
	
	private NoticeDAO noticeDAO;
	private UploadDAO uploadDAO;
	
	public NoticeService() {
		
		noticeDAO = new NoticeDAO();
		uploadDAO = new UploadDAO();
	}

	@Override
	public ActionForward list(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionFoward = new ActionForward();
		
		int curPage = 1; //현재페이지
		
		try {
			curPage = Integer.parseInt(request.getParameter("curPage"));
		} catch (Exception e) {
			
		}
		
		String kind = request.getParameter("kind"); //검색하기
		String search = request.getParameter("search");
		//c,w,t
		
		SearchMakePage s = new SearchMakePage(curPage, kind, search);
		
		//1.row
		SearchRow searchRow = s.makeRow();
		List<BoardDTO> ar = null;
		Connection con = null;
		
		try {

			con = DBConnector.getConnect();
			ar = noticeDAO.selectList(searchRow, con);
			
						
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		//2.page
		
		try {
			int totalCount = noticeDAO.getTotalCount(searchRow, con);
			SearchPager searchPager = s.makePage(totalCount);
			
			request.setAttribute("pager", searchPager);
			request.setAttribute("list", ar);
			request.setAttribute("board", "notice");
			
			actionFoward.setCheck(true); //포워드로 보내겠다
			actionFoward.setPath("../WEB-INF/views/board/boardList.jsp");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			request.setAttribute("message", "Server Error");
			request.setAttribute("path", "../index.do");
			actionFoward.setCheck(true);
			actionFoward.setPath("../WEB-INF/views/common/result.jsp");
		} finally {
			
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				
		return actionFoward;
	}

	@Override
	public ActionForward select(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		String path = "";
		
		BoardDTO boardDTO = null;
		List<UploadDTO> ar = null;
		Connection con = null;
		
		try {
			
			con = DBConnector.getConnect();
			int num = Integer.parseInt(request.getParameter("num"));
			boardDTO = noticeDAO.selectOne(num, con);
			ar = uploadDAO.selectList(num, con);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		if(boardDTO != null) {
			
			request.setAttribute("boardDTO", boardDTO);
			request.setAttribute("upload", ar);
			path ="../WEB-INF/views/board/boardSelect.jsp";
			
		} else {
			
			request.setAttribute("message", "No Data");
			request.setAttribute("path", "./noticeList");
			path="../WEB-INF/views/common/result.jsp";
			
		}
		
		actionForward.setCheck(true);
		actionForward.setPath(path);
		
		return actionForward;
	}
	

	@Override
	public ActionForward insert(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/board/boardWrite.jsp");
		String method = request.getMethod();
		if(method.equals("POST")) {
			String saveDirectory = request.getServletContext().getRealPath("upload");
			File f = new File(saveDirectory);
			if(!f.exists()) {
				f.mkdirs();
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
				NoticeDTO noticeDTO = new NoticeDTO();
				noticeDTO.setTitle(multipartRequest.getParameter("title"));
				noticeDTO.setName(multipartRequest.getParameter("name"));
				noticeDTO.setContents(multipartRequest.getParameter("contents"));

				con = DBConnector.getConnect();

				//1.시퀀스번호
				int num = noticeDAO.getNum();
				noticeDTO.setNum(num);
				con.setAutoCommit(false);
				//2. qna insert
				num = noticeDAO.insert(noticeDTO, con);

				//3. upload insert
				for(UploadDTO uploadDTO:ar) {
					uploadDTO.setNum(noticeDTO.getNum());
					num = uploadDAO.insert(uploadDTO, con);
					if(num<1) {
						throw new Exception();
					}
				}

				con.commit();

			} catch (Exception e) {
				e.printStackTrace();
				// TODO Auto-generated catch block
				try {
					con.rollback();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
			actionForward.setPath("./noticeList");

		}//post끝
		return actionForward;
	}



	@Override
	public ActionForward update(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/board/boardUpdate.jsp");
		
		String method = request.getMethod();
		
		if(method.equals("POST")) {
			
			
			//MultipartRequest multi = new MultipartRequest(request, saveDirectory, maxPostSize, encoding, new DefaultFileRenamePolicy());
			
			
		} else {
			
			int num = Integer.parseInt(request.getParameter("num"));
			Connection con = null;
			BoardDTO boardDTO = null;
			List<UploadDTO> ar = null;
			
			try {
				
				con = DBConnector.getConnect();
				boardDTO = noticeDAO.selectOne(num, con);
				ar = uploadDAO.selectList(num, con);
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}//finally끝
			
			request.setAttribute("boardDTO", boardDTO);
			request.setAttribute("upload", ar);
			
		}
		
		return actionForward;
	}
	

	@Override
	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

}
