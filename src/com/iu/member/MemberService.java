package com.iu.member;

import java.sql.Connection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.iu.action.Action;
import com.iu.action.ActionForward;
import com.iu.util.DBConnector;

public class MemberService implements Action {
	
	private MemberDAO memberDAO;
	
	public MemberService() {
		memberDAO = new MemberDAO();
	}
	
	public ActionForward logout(HttpServletRequest request, HttpServletResponse response) {
		
		ActionForward actionForward = new ActionForward();
		
		HttpSession session = request.getSession();
		
		session.invalidate();
		
		actionForward.setCheck(false);
		actionForward.setPath("../index.do");
		
		return actionForward;
	}
	
	
	public ActionForward login(HttpServletRequest request, HttpServletResponse response) {
		
		ActionForward actionForward = new ActionForward();
		String method = request.getMethod();
		
		boolean check = true;
		String path = "";
		
		MemberDTO memberDTO = new MemberDTO();
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		memberDTO.setId(id);
		memberDTO.setPw(pw);
		
		HttpSession session = request.getSession();
		
		String remember = request.getParameter("check"); //remember 체크여부
		
		if(method.equals("POST")) {
			
			try {
				memberDTO = memberDAO.memberLogin(memberDTO);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(memberDTO != null) {
				
				check = false;
				path = "../index.do";
				session.setAttribute("session", memberDTO);
				
			} else {
				
				request.setAttribute("message", "Login Fail");
				request.setAttribute("path", "./memberLogin");
				check = true;
				path = "../WEB-INF/views/common/result.jsp";
				
			}
			
			if(remember != null) {
				
				Cookie c = new Cookie("c", memberDTO.getId());
				response.addCookie(c);
				
			} else {
				
				Cookie c = new Cookie("c", "");
				response.addCookie(c);
				
			}
			
		} else {
			
			check = true;
			path = "../WEB-INF/views/member/memberLogin.jsp";
			
		}
		
		
		actionForward.setCheck(check);
		actionForward.setPath(path);
		
		return actionForward;
	}
	
	
	
	//id중복체크
	public ActionForward idCheck(HttpServletRequest request, HttpServletResponse response) {
		
		ActionForward actionFoward = new ActionForward();

		String id = request.getParameter("id");
		
		Connection con;
		int check = 0; //사용 불가능
		
		try {
			con = DBConnector.getConnect();
			check = memberDAO.idCheck(id, con);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.setAttribute("result", check);
		actionFoward.setCheck(true);
		actionFoward.setPath("../WEB-INF/views/common/result2.jsp");
		
		
		return actionFoward;
	}
	

	@Override
	public ActionForward list(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		
		
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
		
		
		
		return actionForward;
	}

	@Override
	public ActionForward update(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		
		
		return actionForward;
	}

	@Override
	public ActionForward delete(HttpServletRequest request, HttpServletResponse response) {
		ActionForward actionForward = new ActionForward();
		
		
		
		return actionForward;
	}

}
