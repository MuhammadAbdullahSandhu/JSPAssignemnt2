package servlets;
import models.Book;
import models.Reservations;
import models.UserModel;


import javax.servlet.*;
import javax.servlet.http.*;

import Services.MySQLdb;

import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ReserveServlet", value = "/ReserveServlet")
public class ReserveServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int book_id = Integer.parseInt(request.getParameter("id"));

        HttpSession session = request.getSession();
        MySQLdb db = MySQLdb.getInstance();
        if(session != null) {
            if(session.getAttribute("user") != null) {
                try {
                    UserModel user = (UserModel) session.getAttribute("user");
                    boolean result = db.doReserve(user.getUsername(), book_id);
                    String username = user.getUsername();
                    

                    if(result) {
                    	List<Book> list_res = db.getReservedBook(username);
                        request.setAttribute("list_of_reserve_books", list_res);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("reserve.jsp");
                        requestDispatcher.forward(request, response);
                    } else {
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("home.jsp");
                        request.setAttribute("message", "Something went wrong! Server error.!");
                        requestDispatcher.forward(request, response);
                    }
                } catch (SQLException e) {
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
