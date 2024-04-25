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

<form action="FetchTopicServlet">
    <h2>Search Book</h2>
   <select name="topic">
    <option value="999">All</option>
    <c:forEach var="each_topic" items="${list_of_topic}">
        <option value="${each_topic.getTopic_id()}">${each_topic.getTopic_name()}</option>
        
    </c:forEach>
    
</select>
<input type="submit" value="Search" />
</form>

  <table>
            <tr>
                <th>Book Name</th>
                <th>Topic</th>
                <th>Author</th>
                <th>Action</th>
            </tr>
            <c:forEach var="each_book" items="${list_of_books}">
                <tr>
                    <td>${each_book.getBook_name()}</td>
                    <td>${each_book.getTopic_name()}</td>
                    <td>${each_book.getAuthor_name()}</td>
                    <td><a href="ReserveServlet?id=${each_book.getBook_id()}">Reserve</a></td>
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