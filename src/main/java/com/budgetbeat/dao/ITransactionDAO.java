package com.budgetbeat.dao;

import java.util.Calendar;
import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import com.budgetbeat.pojo.Transaction;
import com.budgetbeat.pojo.User;

public interface ITransactionDAO {

	public void setDataSource(DataSource dataSource);

	public int create(User user,Transaction transaction);

	public Transaction getTransactionbyID(Integer id);

	public List<Transaction> getListOfTransactionByUserID(Integer id);

	public List<Transaction> getListOfTransactionbyAccountID(Integer id);

	public List<Transaction> getListOfTransactionbyTagID(Integer id);

	public List<Transaction> listOfAllTransaction();

	public void delete(User user,Integer id);

	public void update(User user ,Transaction transaction);
}
