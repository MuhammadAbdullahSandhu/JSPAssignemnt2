package Services;



import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import models.Book;
import models.Reservations;
import models.Topic;
import models.UserModel;

public class MySQLdb {
    String url = "jdbc:mysql://localhost:3306/library_catalog";
    String username = "root";
    String password = "pass123";
    Connection connection = null;
    static MySQLdb instance = null;


    public MySQLdb() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    public static synchronized MySQLdb getInstance() {
        if(instance == null) {
            instance = new MySQLdb();
        }
        return instance;
    }

    public UserModel doLogin(String username, String password) throws SQLException {
        UserModel userModel = null;

        // PreparedStatement

//        String qLogin = "SELECT name FROM users WHERE email = ? AND password = ?";
//        PreparedStatement preparedStatement = connection.prepareStatement(qLogin);
//        preparedStatement.setString(1, email);
//        preparedStatement.setString(2, password);
//        ResultSet resultSet = preparedStatement.executeQuery();


        // Statement
        String qLogin = "SELECT username FROM users WHERE username = '"+ username +"' AND password = '"+ password +"'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(qLogin);


        if(resultSet.next()) {
            String name = resultSet.getString("username");
            userModel = new UserModel(name,password, name, name);
        }
        resultSet.close();
        statement.close();
        return userModel;

    }
    public UserModel doRegister(String firstname, String lastname, String username, String password) throws SQLException {
        UserModel userModel = null;

        try {
            // Check if user already exists
            String checkUserSql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement checkUserStatement = connection.prepareStatement(checkUserSql);
            checkUserStatement.setString(1, username);
            ResultSet resultSet = checkUserStatement.executeQuery();

            if (resultSet.next()) {
                // User already exists
                return null;
            } else {
                // User does not exist, proceed with registration
                String insertUserSql = "INSERT INTO users (fname, lname, username, password) VALUES (?, ?, ?, ?)";
                PreparedStatement insertUserStatement = connection.prepareStatement(insertUserSql);
                insertUserStatement.setString(1, firstname);
                insertUserStatement.setString(2, lastname);
                insertUserStatement.setString(3, username);
                insertUserStatement.setString(4, password);

                // Execute the query
                int rowsInserted = insertUserStatement.executeUpdate();
                if (rowsInserted > 0) {
                    // Registration successful
                    userModel = new UserModel(firstname, lastname, password, username);
                } else {
                    // Registration failed
                    return null;
                }
            }

            // Close the connection
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }

        return userModel;
    }

	public List<Topic> getTopic() throws SQLException {
		List<Topic> list = new ArrayList<>();
        String qGetTopic = "SELECT * from topics";
        PreparedStatement preparedStatement = connection.prepareStatement(qGetTopic);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            int topic_id = resultSet.getInt("topic_id");
            String topic_name = resultSet.getString("topic_name");
            Topic topics = new Topic(topic_id, topic_name);
            list.add(topics);
        }
        resultSet.close();
        preparedStatement.close();
        return list;
	}
	
	public List<Book> getBooks(int topic_id) throws SQLException {
	    String qGetBooks = null;
	    List<Book> list = new ArrayList<>();
	    if (topic_id == 999) {
	        qGetBooks = "SELECT b.*, t.topic_name, a.author_name " +
	                    "FROM books AS b " +
	                    "JOIN topics AS t ON b.topic_id = t.topic_id " +
	                    "JOIN authors AS a ON b.author_id = a.author_id";
	    } else {
	        qGetBooks = "SELECT b.*, t.topic_name, a.author_name " +
	                    "FROM books AS b " +
	                    "JOIN topics AS t ON b.topic_id = t.topic_id " +
	                    "JOIN authors AS a ON b.author_id = a.author_id " +
	                    "WHERE b.topic_id = ?";
	    }

	    PreparedStatement preparedStatement = connection.prepareStatement(qGetBooks);
	    if (topic_id != 999) {
	        preparedStatement.setInt(1, topic_id);
	    }

	    ResultSet resultSet = preparedStatement.executeQuery();
	    while (resultSet.next()) {
	        int book_id = resultSet.getInt("book_id");
	        int topicid = resultSet.getInt("topic_id");
	        String book_name = resultSet.getString("book_name");
	        int author_id = resultSet.getInt("author_id");
	        String topic_name = resultSet.getString("topic_name");
	        String author_name = resultSet.getString("author_name");
	        Book book = new Book(book_id, topicid, book_name, author_id, topic_name, author_name);
	        list.add(book);
	    }

	    resultSet.close();
	    preparedStatement.close();
	    return list;
	}
	public List<Reservations> getReserveBooks() throws SQLException {
	    String qGetBooks = "SELECT * from reservations";
	    List<Reservations> list = new ArrayList<>();

	    PreparedStatement preparedStatement = connection.prepareStatement(qGetBooks);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    while(resultSet.next()) {
	        String username = resultSet.getString("username");
	        int book_id = resultSet.getInt("book_id");
	        Reservations reservation = new Reservations(username, book_id);
	        list.add(reservation);
	        System.out.println(username);
	        System.out.println(book_id);
	    }
	    
	    // Close resources
	    resultSet.close();
	    preparedStatement.close();
	    
	    return list;
	}

	 public boolean doReserve(String username, int book_id) throws SQLException {
	        boolean result = false;
	        String qDoReserve = "INSERT INTO reservations VALUES(?, ?)";
	        PreparedStatement preparedStatement = connection.prepareStatement(qDoReserve);
	        preparedStatement.setString(1, username);
	        preparedStatement.setInt(2, book_id);
	        int rows_update = preparedStatement.executeUpdate();
	        if(rows_update > 0) {
	            result = true;
	        }
	        preparedStatement.close();
	        return result;
	    }

	public List<Book> getReservedBook(String username2) throws SQLException {
		List<Book> list = new ArrayList<>();
        String qGetReservation = "SELECT b.*, t.topic_name, a.author_name, r.username FROM books AS b JOIN topics AS t ON b.topic_id = t.topic_id JOIN authors AS a ON b.author_id = a.author_id JOIN reservations AS r ON r.book_id = b.book_id And r.username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(qGetReservation);
        preparedStatement.setString(1, username2);
        ResultSet resultSet = preparedStatement.executeQuery();
	    while(resultSet.next()) {
	    	int book_idd = resultSet.getInt("book_id");
	        int topicid = resultSet.getInt("topic_id");
	        String book_name = resultSet.getString("book_name");
	        int author_id = resultSet.getInt("author_id");
	        String topic_name = resultSet.getString("topic_name");
	        String author_name = resultSet.getString("author_name");
	        Book book = new Book(book_idd, topicid, book_name, author_id, topic_name, author_name);
	        list.add(book);
	    }
	    
	    resultSet.close();
	    preparedStatement.close();
	    return list;
	}

	public List<Book> getBookById(int book_id) throws SQLException {
		String qGetBooks = null;
	    List<Book> list = new ArrayList<>();
	        qGetBooks = "SELECT b.*, t.topic_name, a.author_name FROM books AS b JOIN topics AS t ON b.topic_id = t.topic_id JOIN authors AS a ON b.author_id = a.author_id JOIN reservations AS r ON r.book_id = b.book_id And book_id = ?";
	    PreparedStatement preparedStatement = connection.prepareStatement(qGetBooks);
	        preparedStatement.setInt(1, book_id);
	    ResultSet resultSet = preparedStatement.executeQuery();
	    while (resultSet.next()) {
	        int book_idd = resultSet.getInt("book_id");
	        int topicid = resultSet.getInt("topic_id");
	        String book_name = resultSet.getString("book_name");
	        int author_id = resultSet.getInt("author_id");
	        String topic_name = resultSet.getString("topic_name");
	        String author_name = resultSet.getString("author_name");
	        Book book = new Book(book_idd, topicid, book_name, author_id, topic_name, author_name);
	        list.add(book);
	    }

	    resultSet.close();
	    preparedStatement.close();
	    return list;
	}
	 
	    
    
}


