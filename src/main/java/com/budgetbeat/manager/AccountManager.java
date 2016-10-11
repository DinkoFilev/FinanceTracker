package com.budgetbeat.manager;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.budgetbeat.dao.IAccountDAO;
import com.budgetbeat.pojo.Account;
import com.budgetbeat.pojo.AccountMapper;

public class AccountManager implements IAccountDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void delete(Integer accountId) {
		String SQL = "DELETE FROM transactions WHERE ft_account_id=?;";
		jdbcTemplateObject.update(SQL, accountId);
		SQL = "DELETE FROM accounts_log WHERE fk_account_id=?;";
		jdbcTemplateObject.update(SQL, accountId);
		SQL = "DELETE FROM accounts WHERE account_id=?; ";
		jdbcTemplateObject.update(SQL, accountId);
		System.out.println("Deleted Account with ID = " + accountId);
		return;
	}

	@Override
	public void create(Integer fk_user_id, String name, Double balance, String institution, Boolean status) {
		String SQL = "INSERT INTO `finance_tracker`.`accounts` (`fk_user_id`, `name`, `balance`, `institution`, `status`) VALUES (?,?,?,?,?);";
		jdbcTemplateObject.update(SQL, fk_user_id, name, balance, institution, status);
		return;

	}

	@Override
	public Account getAccount(Integer accountId) {
		String SQL = "select * from accounts where account_id = ?";
		Account account = jdbcTemplateObject.queryForObject(SQL, new Object[] { accountId }, new AccountMapper());
		return account;
	}

	@Override
	public List<Account> listAccounts(Integer userId) {
		String SQL = "select * from accounts where fk_user_id = ?";
		List<Account> accounts = jdbcTemplateObject.query(SQL, new Object[] { userId }, new AccountMapper());
		return accounts;
	}

	@Override
	public void update(Integer accountId, String name, Double balance, String institution, Boolean status) {
		String SQL = "UPDATE accounts SET name=?, balance=?, institution=?, status=? WHERE account_id=?";
		jdbcTemplateObject.update(SQL, name, balance, institution, status, accountId);
	}
	
	public String accountValidation(Integer fk_user_id, String name, Double balance, String institution, Boolean status, List<Account> accountList) {

		if (!name.matches("^[a-zA-Z0-9]{2,45}$")) {
			System.out.println("NE MATCHVA faccountName");
			return "name";
		}
		
		if (!institution.matches("^[a-zA-Z0-9]{2,45}$")) {
			System.out.println("NE MATCHVA institution");
			return "institution";
		}
			
		if (accountList.contains(name)) {
			return "Account name is already used";
		}

		System.out.println(this);

		return "register";

	}
}
