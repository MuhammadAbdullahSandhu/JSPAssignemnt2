package com.JSPAssignment.registration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Registration
 */
@WebServlet("/registration")
public class Registration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Registration() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String firstname = request.getParameter("fname");
		String lastname = request.getParameter("lname");
		String password = request.getParameter("password");
		String username = request.getParameter("username");
		RequestDispatcher dispacher = null;
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3360/library_catalog", "root", "pass123");
			PreparedStatement pst = con.prepareStatement("inser into users(fname,lname,username,password) vlaues(?,?,?,?)");
			pst.setString(1, firstname);
			pst.setString(2, lastname);
			pst.setString(3, password);
			pst.setString(4, username);
			dispacher = request.getRequestDispatcher("registration.jsp"); 
			int rorwCount = pst.executeUpdate();
			if(rorwCount > 0) {
				request.setAttribute("Status", "Success");
			}
			else {
				request.setAttribute("Status", "Failed");
			}
			dispacher.forward(request, response);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

}
