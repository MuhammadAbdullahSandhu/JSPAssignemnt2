<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="models.UserModel" %>
<%@ page import="models.Topic" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Library</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>
<div class="logo">
<a href="home.jsp">
        <h2>Library</h2>
    </a>
</div>
<div class="nav">
<%
// Check if session exists and user is logged in
if (request.getSession() != null) {
    if(session.getAttribute("user") != null) {
        // Retrieve user object from session
        UserModel user = (UserModel) session.getAttribute("user");
%>
        <p>Hello, <%= user.getUsername() %></p>
        <!-- Add a logout link -->
        <p><a href="LogoutServlet">Logout</a></p>

</div>
<div class="main">
<div class="box">

<h2>Reservations</h2>
  <table>
            <tr>
            	<th>No.</th>
                <th>Book Name</th>
                <th>Author</th>
            </tr>
             <c:forEach var="each_reserve" items="${list_of_reserve_books}" varStatus="i">
        <tr>
            <td>${i.index + 1}</td>
            <td>${each_reserve.getBook_name()}</td>
            <td>${each_reserve.getAuthor_name()}</td>
        </tr>
    </c:forEach>
        </table>
<p>${message}</p>
</div>
</div>

        <%
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
%>
</body>
</html>