package com.budgetbeat.pojo;

public class User {

	private Integer userID;
	private String firstName;
	private String lastName;
	private String email;
	private String password;

	public User(Integer userID, String firstName, String lastName, String email, String password) {
		this.userID = userID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public User() {
	}

	// Трябват ми публични за тестове
	public int getUserID() {
		return userID;
	}

	public void setUserID(Integer id) {
		this.userID = id;
	}

	public String getFirstName() {
		return firstName;
	}

	void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	void setEmail(String email) {
		this.email = email;
	}

	String getPassword() {
		return password;
	}

	void setPassword(String password) {
		this.password = password;
	}

}
