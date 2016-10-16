package com.budgetbeat.manager;

import java.util.Calendar;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.budgetbeat.dao.ITransactionDAO;
import com.budgetbeat.pojo.Transaction;
import com.budgetbeat.pojo.TransactionMapper;
import com.budgetbeat.pojo.User;
import com.budgetbeat.pojo.UserMapper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionManager implements ITransactionDAO {
	private ConcurrentHashMap<Integer, Transaction> userTransactions = new ConcurrentHashMap<>();
	private JdbcTemplate jdbcTemplateObject;
	private DataSource dataSource;

	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);

	}

	@Override
	public int create(Integer fk_user_id, Integer ft_account_id, Integer fk_tag_id, String description, Double amount,
			Date date, String file, Boolean status, Long step, Boolean repeat, Boolean income) {
		String SQL = "INSERT INTO transactions (fk_user_id,ft_account_id,fk_tag_id,description,amount,date,file,status,step,transaction_repeat,income) values (?,?,?,?,?,?,?,?,?,?,?)";

		// jdbcTemplateObject.update(SQL,fk_user_id,ft_account_id,fk_tag_id,description,amount,date,file,status,step,repeat);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplateObject.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQL, new String[] { "id" });
				ps.setInt(1, fk_user_id);
				ps.setInt(2, ft_account_id);
				ps.setInt(3, fk_tag_id);
				ps.setString(4, description);
				ps.setDouble(5, amount);
				ps.setDate(6, date);
				ps.setString(7, file);
				ps.setBoolean(8, status);
				ps.setLong(9, step);
				ps.setBoolean(10, repeat);
				ps.setBoolean(11, income);
				return ps;
			}
		}, keyHolder);

		System.out.println("Created Record Transacton : FK USER = " + fk_user_id + " FT ACCOUNT = " + ft_account_id
				+ " FK TAG = " + fk_tag_id + "  Description, = " + description + " Amount : " + amount + " Date " + date
				+ " file " + file + " status " + status + " step " + step + " repeat " + repeat + " income " + income);

		return keyHolder.getKey().intValue();

	}

	@Override
	public Transaction getTransactionbyID(Integer id) {
		String SQL = "SELECT * from transactions where transaction_id = ?";
		Transaction transaction = jdbcTemplateObject.queryForObject(SQL, new Object[] { id }, new TransactionMapper());
		return transaction;
	}

	@Override
	public List<Transaction> getListOfTransactionByUserID(Integer id) {
		String SQL = "SELECT * from transactions where fk_user_id = ?";
		List<Transaction> transaction = jdbcTemplateObject.query(SQL, new Object[] { id }, new TransactionMapper());
		return transaction;

	}

	@Override
	public List<Transaction> getListOfTransactionbyAccountID(Integer id) {
		String SQL = "SELECT * from transactions where ft_account_id = ?";
		List<Transaction> transaction = jdbcTemplateObject.query(SQL, new TransactionMapper());
		return transaction;
	}

	@Override
	public List<Transaction> getListOfTransactionbyTagID(Integer id) {
		String SQL = "SELECT * from transactions where fk_tag_id = ?";
		List<Transaction> transaction = jdbcTemplateObject.query(SQL, new TransactionMapper());
		return transaction;
	}

	/**
	 * @author CaveMan List of all transactions made by all users
	 */
	@Override
	public List<Transaction> listOfAllTransaction() {
		String SQL = "SELECT * from transactions";
		List<Transaction> transaction = jdbcTemplateObject.query(SQL, new TransactionMapper());
		return transaction;
	}

	@Override
	public void delete(Integer id) {
		String SQL = "delete from transactions where transaction_id = ?";
		jdbcTemplateObject.update(SQL, id);
		System.out.println("Delete record transaction with ID " + id);

	}

	@Override
	public void update(Integer transaction_id, Integer fk_user_id, Integer ft_account_id, Integer fk_tag_id,
			String description, Double amount, Date date, String file, Boolean status, Long step, Boolean repeat,
			Boolean income) {

		String SQL = "UPDATE transactions SET fk_user_id=?,ft_account_id=?,fk_tag_id=?,description=?,amount=?,date=?,file=?,status=?,step=?,transaction_repeat=?,income=? WHERE transaction_id = ?";
		jdbcTemplateObject.update(SQL, fk_user_id, ft_account_id, fk_tag_id, description, amount, date, file, status,
				step, repeat, income, transaction_id);
		System.out.println("Updated Record transaction with ID = " + transaction_id);
		return;

	}

	// Move transactions to default Account
	public void moveToDefaultAccount(User user, Integer accountId, Integer defaultAccountId) {

		String SQL = "UPDATE transactions  SET ft_account_id = ?  WHERE ft_account_id = ?;";
		jdbcTemplateObject.update(SQL, defaultAccountId, accountId);
		
		for (Integer key : user.getTransactions().keySet()) {
			Transaction transaction = user.getTransaction(key);
			if (transaction.getFt_account_id() == accountId) {
				transaction.setFt_account_id(defaultAccountId);
			}
		}
	}

	// Move transactions to default tag
	public void moveToDefaultTag(User user, Integer tagId, Integer defaultTagId) {

		String SQL = "UPDATE transactions  SET fk_tag_id = ?  WHERE fk_tag_id = ?;";
		jdbcTemplateObject.update(SQL, defaultTagId, tagId);

		for (Integer key : user.getTransactions().keySet()) {
			Transaction transaction = user.getTransaction(key);
			if (transaction.getFk_tag_id() == tagId) {
				transaction.setFk_tag_id(defaultTagId);
			}
		}
	}

}
