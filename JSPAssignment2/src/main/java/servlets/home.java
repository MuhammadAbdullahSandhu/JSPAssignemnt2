package servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Services.MySQLdb;
import models.Topic;


/**
 * Servlet implementation class home
 */
@WebServlet("/home")
public class home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 HttpSession session = request.getSession();
	        MySQLdb db = MySQLdb.getInstance();
	        if (session != null) {
	            if (session.getAttribute("user") != null) {
	                try {
	                    List<Topic> list = db.getTopic();
	                    request.setAttribute("list_of_topic", list);
	                    RequestDispatcher requestDispatcher = request.getRequestDispatcher("home.jsp");
	                    requestDispatcher.forward(request, response);

	                } catch (Exception e) {
	                    e.printStackTrace();
	                }
	            } else {
	                RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
	                request.setAttribute("error", "Please login to continue..!!!");
	                requestDispatcher.forward(request, response);
	            }
	        } else {
	            RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
	            request.setAttribute("error", "Please login to continue..!!!");
	            requestDispatcher.forward(request, response);
	        }
	    }

}
