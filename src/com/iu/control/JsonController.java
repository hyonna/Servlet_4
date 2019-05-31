package com.iu.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iu.action.ActionForward;
import com.iu.json.JsonService;

/**
 * Servlet implementation class JasonController
 */
@WebServlet("/JasonController")
public class JsonController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JsonService jsonService;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JsonController() {
        super();
        jsonService = new JsonService();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String command = request.getPathInfo();
		
		if(command.equals("/jsonTest1")) {
			
			jsonService.test2(request, response);
		} else {
			
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
