package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Services.MySQLdb;
import models.UserModel;

/**
 * Servlet implementation class LoginSevelet
 */
@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");



        MySQLdb db = MySQLdb.getInstance();
        UserModel userModel = null;
        try {
            userModel = db.doLogin(username, password);
        } catch(SQLException e) {
            e.printStackTrace();
        }




        if (userModel != null) {

        	  HttpSession session = request.getSession();
              session.setAttribute("user", userModel);

              RequestDispatcher requestDispatcher = request.getRequestDispatcher("/FetchTopicServlet");
              requestDispatcher.forward(request, response);
          } else {
              RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
              request.setAttribute("error", "Incorrect email or password..!!!");
              requestDispatcher.forward(request, response);
          }
    }
}
