package models;
public class Book {
    private int book_id;
    private int topic_id;
    private String topic_name; // New field for topic_name
    private String book_name;
    private int author_id;
    private String author_name; // New field for author_name
    
    // Getters and setters for all fields
    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public Book(int book_id, int topic_id, String book_name, int author_id, String topic_name, String author_name) {
        super();
        this.book_id = book_id;
        this.topic_id = topic_id;
        this.book_name = book_name;
        this.author_id = author_id;
        this.topic_name = topic_name;
        this.author_name = author_name;
    }
}