package com.budgetbeat.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.budgetbeat.SpringWebConfig;
import com.budgetbeat.dao.IAccountDAO;
import com.budgetbeat.pojo.Account;
import com.budgetbeat.pojo.AccountMapper;
import com.budgetbeat.pojo.Transaction;
import com.budgetbeat.pojo.User;

public class AccountManager implements IAccountDAO {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public void setInitialAccount(Integer fk_user_id) {
		String SQL = "INSERT INTO `finance_tracker`.`accounts` (`fk_user_id`, `name`, `balance`, `institution`, `status`) VALUES (?,?,?,?,?);";
		jdbcTemplateObject.update(SQL, fk_user_id, "No Account", 0.00, "Empty", true);
		return;
	}

	@Override
	@Transactional
	public void delete(User user, Integer accountId, Integer defaultaccountId) throws DataAccessException {

	
			TransactionManager tranManager = (TransactionManager) SpringWebConfig.context.getBean("TransactionManager");

			String SQL = "UPDATE transactions  SET ft_account_id = ?  WHERE ft_account_id = ?;";
			jdbcTemplateObject.update(SQL, defaultaccountId, accountId);

			SQL = "DELETE FROM accounts_log WHERE fk_account_id=?;";
			jdbcTemplateObject.update(SQL, accountId);

			SQL = "DELETE FROM accounts WHERE account_id=?; ";
			jdbcTemplateObject.update(SQL, accountId);

			tranManager.moveToDefaultAccountCollection(user, accountId, user.getAccounts().lastKey());
			user.getAccounts().remove(accountId);

			System.out.println("Deleted Account with ID = " + accountId);

	}

	@Override
	public Account create(Account account) {
		String SQL = "INSERT INTO `finance_tracker`.`accounts` (`fk_user_id`, `name`, `balance`, `institution`, `status`) VALUES (?,?,?,?,?);";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplateObject.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQL, new String[] { "account_id" });
				ps.setInt(1, account.getFk_userId());
				ps.setString(2, account.getName());
				ps.setDouble(3, account.getBalance());
				ps.setString(4, account.getInstitution());
				ps.setBoolean(5, account.getStatus());
				return ps;
			}
		}, keyHolder);
		account.setAccountId(keyHolder.getKey().intValue());
		return account;

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

	public String accountValidation(Integer fk_user_id, String name, Double balance, String institution, Boolean status,
			List<Account> accountList) {

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
