package com.budgetbeat.pojo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {

	private Integer userID;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private ArrayList<Account> accounts = new ArrayList<>();
	private ArrayList<Tag> tags = new ArrayList<>();
	private ArrayList<Transaction> transactions = new ArrayList<>();

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

	List<Account> getAccounts() {
		return Collections.unmodifiableList(accounts);
	}

	void setAccounts(ArrayList<Account> accounts) {
		this.accounts = accounts;
	}

	List<Tag> getTags() {
		return Collections.unmodifiableList(tags);
	}

	void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}

	List<Transaction> getTransactions() {
		return Collections.unmodifiableList(transactions);
	}

	void setTransactions(ArrayList<Transaction> transactions) {
		this.transactions = transactions;
	}

}
