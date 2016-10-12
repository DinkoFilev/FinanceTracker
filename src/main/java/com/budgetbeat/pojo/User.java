package com.budgetbeat.pojo;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;


public class User {

	private Integer userID;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private TreeMap<Integer,Account> accounts = new TreeMap<Integer,Account>(Collections.reverseOrder()); // ID --> Account
	private TreeMap<Integer,Tag> tags = new TreeMap<Integer,Tag>(Collections.reverseOrder()); // ID --> Tag
	private TreeMap<Integer,Transaction> transactions = new TreeMap<Integer,Transaction>(Collections.reverseOrder()); // ID --> Transaction

	
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

	@Override
	public String toString() {
		return "User [userID=" + userID + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", password=" + password + ", accounts=" + accounts + ", tags=" + tags + ", transactions="
				+ transactions + "]";
	}

	String getPassword() {
		return password;
	}

	void setPassword(String password) {
		this.password = password;
	}
	
	//COLLECTIONS
	
	public TreeMap<Integer, Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(TreeMap<Integer, Account> accounts) {
		this.accounts = accounts;
	}

	public TreeMap<Integer, Tag> getTags() {
		return tags;
	}

	public void setTags(TreeMap<Integer, Tag> tags) {
		this.tags = tags;
	}

	public TreeMap<Integer, Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(TreeMap<Integer, Transaction> transactions) {
		this.transactions = transactions;
	}
	public void addAccount(Account account){
		accounts.put(account.getAccountId(),account);
			
			
		}
	public void addTag(Tag tag){
		tags.put(tag.getTagId(),tag);
		
	}
	
	public void addTransaction(Transaction transaction){
		transactions.put(transaction.getTransaction_id(),transaction);
	
		
	}
	public Account getAccount(Integer id){
		
		
		return accounts.get(id);
	}
	public Tag getTag(Integer id){
		
		return tags.get(id);
	}
	
	public Transaction getTransaction(Integer id){
		
	
		return transactions.get(id);
	}
	
	
	

}
