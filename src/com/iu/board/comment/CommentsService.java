package com.iu.board.comment;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iu.action.Action;
import com.iu.action.ActionForward;
import com.iu.page.SearchMakePage;
import com.iu.page.SearchRow;
import com.iu.util.DBConnector;

public class CommentsService implements Action{
	
	private CommentsDAO commentsDAO;
	
	public CommentsService() {
		commentsDAO = new CommentsDAO();
	}

	@Override
	public ActionForward list(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		int curPage = 1;
		int num = 0;
		
		try {
			curPage = Integer.parseInt(request.getParameter("curPage"));
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		num = Integer.parseInt(request.getParameter("num"));
		SearchMakePage searchMakePage = new SearchMakePage(curPage, "", ""); //댓글은 검색기능X
		SearchRow searchRow = searchMakePage.makeRow();
		
		Connection con = null;
		List<CommentsDTO> ar = null;
		
		try {
			con = DBConnector.getConnect();
			ar = commentsDAO.selectList(searchRow, num, con);
	
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
		}
		
		request.setAttribute("commentsList", ar);
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/common/list.jsp");
		
		return actionForward;
	}

	@Override
	public ActionForward select(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		return actionForward;
	}

	@Override
	public ActionForward insert(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		CommentsDTO commentsDTO = new CommentsDTO();
		commentsDTO.setNum(Integer.parseInt(request.getParameter("num")));
		commentsDTO.setName(request.getParameter("name"));
		commentsDTO.setContents(request.getParameter("contents"));
		
		int result = 0;
		Connection con = null;
		
		try {
			con = DBConnector.getConnect();
			result = commentsDAO.insert(commentsDTO, con);
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
		
		request.setAttribute("result", result);
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/common/result2.jsp");
		
		return actionForward;
	}

	@Override
	public ActionForward update(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		int cnum = Integer.parseInt(request.getParameter("cnum"));
		CommentsDTO commentsDTO = new CommentsDTO();
		commentsDTO.setCnum(cnum);
		commentsDTO.setContents(request.getParameter("contents"));
		int result = 0;
		
		Connection con = null;
		
		try {
			con = DBConnector.getConnect();
			result = commentsDAO.update(commentsDTO, con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	finally {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		request.setAttribute("result", result);
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/common/result2.jsp");
		
		return actionForward;
	}

	@Override
	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		Connection con = null;
		int result = 0;
		
		try {
			int cnum = Integer.parseInt(request.getParameter("cnum"));
			con = DBConnector.getConnect();
			result = commentsDAO.delete(cnum, con);
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
		
		request.setAttribute("result", result);
		actionForward.setCheck(true);
		actionForward.setPath("../WEB-INF/views/common/result2.jsp");
		
		return actionForward;
	}
	
	
	
	

}
