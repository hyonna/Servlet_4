package com.iu.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iu.action.ActionForward;
import com.iu.board.qna.QnaService;


/**
 * Servlet implementation class QnaController
 */
@WebServlet("/QnaController")
public class QnaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private QnaService qnaService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QnaController() {
        super();
        qnaService = new QnaService() {
		};
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String command = request.getPathInfo();
		ActionForward actionForward = null;
		
		if(command.equals("/qnaList")) {
			
			actionForward = qnaService.list(request, response);
			
		} else if(command.equals("/qnaWrite")){
			
			actionForward = qnaService.insert(request, response);
			
		} else if(command.equals("/qnaSelect")){
			
			actionForward = qnaService.select(request, response);
			
		} else if(command.equals("/qnaUpdate")){
			
			actionForward = qnaService.update(request, response);
			
		}
		 
		request.setAttribute("board", "qna"); //서비스에서 보드값 안먹히면 여기서 주기
		
		if(actionForward.isCheck()) {
			
			//포워드
			RequestDispatcher view = request.getRequestDispatcher(actionForward.getPath());
			view.forward(request, response);
			
		} else {
			
			//리다이렉트
			response.sendRedirect(actionForward.getPath());
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
