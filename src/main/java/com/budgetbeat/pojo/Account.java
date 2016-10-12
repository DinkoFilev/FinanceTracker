package com.budgetbeat.pojo;

public class Account {
	private Integer accountId;
	private Integer fk_userId;
	private String name;
	private Double balance;
	private String institution;
	private Boolean status;

	public Account(Integer account_id, Integer fk_user_id, String name, Double balance, String institution,
			Boolean status) {

		this.accountId = account_id;
		this.fk_userId = fk_user_id;
		this.name = name;
		this.balance = balance;
		this.institution = institution;
		this.status = status;
	}

	public Account() {
	}


	public void setInstitution(String institution) {
		this.institution = institution;
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
		this.balance = +balance;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}




	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getFk_userId() {
		return fk_userId;
	}

	public void setFk_userId(Integer fk_userId) {
		this.fk_userId = fk_userId;
	}

	public String getInstitution() {
		return institution;
	}

}
