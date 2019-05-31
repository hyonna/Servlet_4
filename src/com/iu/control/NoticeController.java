package com.iu.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iu.action.ActionForward;
import com.iu.board.notice.NoticeService;


/**
 * Servlet implementation class NoticeController
 */
@WebServlet("/NoticeController")
public class NoticeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NoticeService noticeService;
	private String board;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeController() {
        super();
        noticeService = new NoticeService() {
        	
		};
        
    }

    
    @Override
    public void init(ServletConfig config) throws ServletException {
    	
    	board = config.getInitParameter("board");
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//getServletContext():전체정보
		String v = request.getServletContext().getInitParameter("view");
		System.out.println(v);
		String command = request.getPathInfo();
		ActionForward actionFoward = null;
		
		if(command.equals("/noticeList")) {
			
			actionFoward = noticeService.list(request, response);
			
		} else if(command.equals("/noticeSelect")) {
			
			actionFoward = noticeService.select(request, response);
			
		} else if(command.equals("/noticeUpdate")) {
			
			actionFoward = noticeService.update(request, response);
			
		} else if(command.equals("/noticeWrite")) {
			
			actionFoward = noticeService.insert(request, response);
			
		} else if(command.equals("/noticeDelete")) {
			
			actionFoward = noticeService.delete(request, response);
			
		} else { //이상한 주소가 들어오면 다른 곳으로 보내버릴때
			
			actionFoward = new ActionForward();
			
			
		}
		
		request.setAttribute("board", board);
		
		
		
		if(actionFoward.isCheck()) {
			
			//포워드
			RequestDispatcher view = request.getRequestDispatcher(actionFoward.getPath());
			view.forward(request, response);
			
		} else {
			
			//리다이렉트
			response.sendRedirect(actionFoward.getPath());
		}
		
		System.out.println("Notice");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
