package com.budgetbeat.pojo;

public class Account {
	private Integer account_id;
	private Integer fk_user_id;
	private String name;
	private Double balance;
	private String institution;
	private Boolean status;
	
	
	
	public Account(Integer account_id, Integer fk_user_id, String name, Double balance, String institution,
			Boolean status) {

		this.account_id = account_id;
		this.fk_user_id = fk_user_id;
		this.name = name;
		this.balance = balance;
		this.institution = institution;
		this.status = status;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance =+ balance;
	}
	
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public Integer getAccount_id() {
		return account_id;
	}
	public Integer getFk_user_id() {
		return fk_user_id;
	}
	public String getInstitution() {
		return institution;
	}
	
	

}
