package com.budgetbeat.dao;

import java.util.List;

import javax.sql.DataSource;

import com.budgetbeat.pojo.Account;


public interface IAccountDAO {

	public void setDataSource(DataSource ds);

	public void create(Integer fk_user_id, String name, Double balance, String institution, Boolean status);

	public Account getAccount(Integer accountId);

	public List<Account> listAccounts(Integer userId);

	public void delete(Integer accountId);

	public void update(Integer accountId, String name, Double balance, String institution, Boolean status);
}
