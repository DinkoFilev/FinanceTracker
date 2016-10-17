package com.budgetbeat.dao;

import java.util.List;

import javax.sql.DataSource;

import com.budgetbeat.pojo.Account;
import com.budgetbeat.pojo.User;


public interface IAccountDAO {

	public void setDataSource(DataSource ds);

	public Account create(Account account);

	public Account getAccount(Integer accountId);

	public List<Account> listAccounts(Integer userId);

	public void update(Integer accountId, String name, Double balance, String institution, Boolean status);

	public void delete(User user, Integer accountId, Integer defaultaccountId) throws Exception;
}
