package com.iu.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iu.action.ActionForward;
import com.iu.upload.UploadDAO;
import com.iu.upload.UploadService;

/**
 * Servlet implementation class FileController
 */
@WebServlet("/FileController")
public class UploadController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UploadService uploadService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UploadController() {
        super();
        uploadService = new UploadService();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/* /file/fileDelete */
		
		String command = request.getPathInfo();
		ActionForward actionForward = null;
		
		if(command.equals("/fileDelete")) {
			
			actionForward = uploadService.delete(request, response);
			
		} else {
			
			
		}
		
		if(actionForward.isCheck()) {
			
			RequestDispatcher view = request.getRequestDispatcher(actionForward.getPath());
			view.forward(request, response);
			
		} else {
			
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
