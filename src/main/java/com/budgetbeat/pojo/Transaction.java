package com.budgetbeat.pojo;

import java.sql.Date;

public class Transaction {
	private Integer transaction_id;
	private Integer fk_user_id;
	private Integer ft_account_id;
	private Integer fk_tag_id;
	private String description;
	private Double amount;
	private Date date;
	private String file;
	private Boolean status;
	private Long step;
	private Boolean repeat;

	public Transaction(Integer transaction_id, Integer fk_user_id, Integer ft_account_id, Integer fk_tag_id,
			String description, Double amount, Date date, String file, Boolean status, Long step, Boolean repeat) {

		this.transaction_id = transaction_id;
		this.fk_user_id = fk_user_id;
		this.ft_account_id = ft_account_id;
		this.fk_tag_id = fk_tag_id;
		this.description = description;
		this.amount = amount;
		this.date = date;
		this.file = file;
		this.status = status;
		this.step = step;
		this.repeat = repeat;
	}

	public Transaction() {
	}

	public Integer getTransaction_id() {
		return transaction_id;
	}

	public void setTransaction_id(Integer transaction_id) {
		this.transaction_id = transaction_id;
	}

	public Integer getFk_user_id() {
		return fk_user_id;
	}

	public void setFk_user_id(Integer fk_user_id) {
		this.fk_user_id = fk_user_id;
	}

	public Integer getFt_account_id() {
		return ft_account_id;
	}

	public void setFt_account_id(Integer ft_account_id) {
		this.ft_account_id = ft_account_id;
	}

	public Integer getFk_tag_id() {
		return fk_tag_id;
	}

	public void setFk_tag_id(Integer fk_tag_id) {
		this.fk_tag_id = fk_tag_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Long getStep() {
		return step;
	}

	public void setStep(Long step) {
		this.step = step;
	}

	public Boolean getRepeat() {
		return repeat;
	}

	public void setRepeat(Boolean repeat) {
		this.repeat = repeat;
	}

}