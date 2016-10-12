package com.budgetbeat.dao;

import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import com.budgetbeat.pojo.Transaction;

public interface ITransactionDAO {

	public void setDataSource(DataSource dataSource);

	public void create(Integer fk_user_id, Integer ft_account_id, Integer fk_tag_id, String description, Double amount,
			Date date, String file, Boolean status, Long step, Boolean repeat);

	public Transaction getTransactionbyID(Integer id);

	public List<Transaction> getListOfTransactionByUserID(Integer id);

	public List<Transaction> getListOfTransactionbyAccountID(Integer id);

	public List<Transaction> getListOfTransactionbyTagID(Integer id);

	public List<Transaction> listOfAllTransaction();

	public void delete(Integer id);

	public void update(Integer transaction_id, Integer fk_user_id, Integer ft_account_id, Integer fk_tag_id,
			String description, Double amount, Calendar date, String file, Boolean status, Long step, Boolean repeat);
}
