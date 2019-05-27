package com.iu.upload;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iu.action.Action;
import com.iu.action.ActionForward;
import com.iu.util.DBConnector;

public class UploadService implements Action{
	
	private UploadDAO uploadDAO;
	
	public UploadService() {
		uploadDAO = new UploadDAO();
	}

	@Override
	public ActionForward list(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward select(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward insert(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward update(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) {

		ActionForward actionForward = new ActionForward();
		boolean check = true;
		String path = "";
		
		Connection con;
		int result = 0;
		
		try {
			
			int pnum = Integer.parseInt(request.getParameter("pnum"));
			con = DBConnector.getConnect();
			result = uploadDAO.delete(pnum, con);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		request.setAttribute("result", result);

		
		actionForward.setCheck(check);
		actionForward.setPath("../WEB-INF/views/common/result2.jsp");
		
		
		return actionForward;
	}
	
	

}
