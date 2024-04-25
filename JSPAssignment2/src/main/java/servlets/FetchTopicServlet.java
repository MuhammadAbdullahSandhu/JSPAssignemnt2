package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import models.Topic;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import Services.MySQLdb;
import models.Book;



/**
 * Servlet implementation class FetchAuthorsServlet
 */
@WebServlet("/FetchTopicServlet")
public class FetchTopicServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetchTopicServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int topic_id = 999;
		 HttpSession session = request.getSession();
	        MySQLdb db = MySQLdb.getInstance();
	        if(session != null) {
	            if(session.getAttribute("user") != null) {


	                if(request.getParameter("topic") != null) {
	                	topic_id = Integer.parseInt(request.getParameter("topic"));
	                }
	                try {	
	                    List<Book> list = db.getBooks(topic_id);
	                    request.setAttribute("list_of_books", list);

	                    List<Topic> topicModelList = db.getTopic();
	                    request.setAttribute("list_of_topic", topicModelList);
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }
	            RequestDispatcher requestDispatcher = request.getRequestDispatcher("home.jsp");
                requestDispatcher.forward(request, response);



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
