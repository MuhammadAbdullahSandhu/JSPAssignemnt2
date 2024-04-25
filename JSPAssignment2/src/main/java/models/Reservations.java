package models;

public class Reservations {
	private String username;
	private int book_id;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getBook_id() {
		return book_id;
	}
	public void setBook_id(int book_id) {
		this.book_id = book_id;
	}
	public Reservations(String username, int book_id) {
		super();
		this.username = username;
		this.book_id = book_id;
	}
	
}
