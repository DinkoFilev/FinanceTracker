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
import org.springframework.transaction.annotation.Transactional;

import com.budgetbeat.SpringWebConfig;
import com.budgetbeat.dao.ITransactionDAO;
import com.budgetbeat.pojo.Account;
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
	@Transactional
	@Override
	public int create(User user ,Transaction transaction) {
		String SQL = "INSERT INTO transactions (fk_user_id,ft_account_id,fk_tag_id,description,amount,date,file,status,step,transaction_repeat,income) values (?,?,?,?,?,?,?,?,?,?,?)";

		// jdbcTemplateObject.update(SQL,fk_user_id,ft_account_id,fk_tag_id,description,amount,date,file,status,step,repeat);
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplateObject.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQL, new String[] { "id" });
				ps.setInt(1, user.getUserID());
				ps.setInt(2, transaction.getFt_account_id());
				ps.setInt(3, transaction.getFk_tag_id());
				ps.setString(4, transaction.getDescription());
				ps.setDouble(5, transaction.getAmount());
				ps.setDate(6, transaction.getDate());
				ps.setString(7, "");
				ps.setBoolean(8, true);
				ps.setLong(9, 0);
				ps.setBoolean(10, true);
				ps.setBoolean(11, transaction.getIncome());
				return ps;
			}
		}, keyHolder);
		
		 Account acc = user.getAccount(transaction.getFt_account_id());
		 String SQLacc = "UPDATE accounts  SET balance= ?  WHERE account_id = ?;";
		 
		jdbcTemplateObject.update(SQLacc, acc.getBalance()+transaction.getAmount(), transaction.getFt_account_id());
		
		acc.setBalance(acc.getBalance()+transaction.getAmount());
		
		transaction.setTransaction_id(keyHolder.getKey().intValue());
		user.addTransaction(transaction);

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
	@Transactional
	@Override
	public void delete(User user ,Integer id) {
		Transaction tran = user.getTransaction(id);
		Account acc = user.getAccount(tran.getFt_account_id());
		
		String SQL = "UPDATE accounts  SET balance= ?  WHERE account_id = ?;";
		jdbcTemplateObject.update(SQL, acc.getBalance()-tran.getAmount(),tran.getFt_account_id());
		
		
		SQL = "delete from transactions where transaction_id = ?";
		jdbcTemplateObject.update(SQL, id);
		System.out.println("Delete record transaction with ID " + id);
		acc.setBalance(acc.getBalance()-tran.getAmount());
		user.getTransactions().remove(tran.getTransaction_id());
		
	}
	@Transactional
	@Override
	public void update(User user,Transaction transaction) {
		System.out.println(transaction.toString());
		String SQL = "UPDATE transactions SET fk_user_id=?,ft_account_id=?,fk_tag_id=?,description=?,amount=?,date=?,file=?,status=?,step=?,transaction_repeat=?,income=? WHERE transaction_id = ?";
		jdbcTemplateObject.update(SQL, user.getUserID(), transaction.getFt_account_id(), transaction.getFk_tag_id(), transaction.getDescription(), transaction.getAmount(), transaction.getDate(), "",true,0,true,transaction.getIncome(),
				transaction.getTransaction_id());
		System.out.println("Updated Record transaction with ID = " + transaction.getTransaction_id());
		
		Account acc = user.getAccount(transaction.getFt_account_id());
		SQL = "UPDATE accounts  SET balance= ?  WHERE account_id = ?;";
		jdbcTemplateObject.update(SQL, acc.getBalance()+transaction.getAmount(),transaction.getFt_account_id());
	
		acc.setBalance(acc.getBalance()+transaction.getAmount());
		user.addTransaction(transaction);
		return;

	}

	// Move transactions to default Account
	public void moveToDefaultAccountDB(User user, Integer accountId, Integer defaultAccountId) {
		String SQL = "UPDATE transactions  SET ft_account_id = ?  WHERE ft_account_id = ?;";
		jdbcTemplateObject.update(SQL, defaultAccountId, accountId);

	}
	
	public void moveToDefaultAccountCollection(User user, Integer accountId, Integer defaultAccountId) {
		for (Integer key : user.getTransactions().keySet()) {
			Transaction transaction = user.getTransaction(key);
			if (transaction.getFt_account_id() == accountId) {
				transaction.setFt_account_id(defaultAccountId);
			}
		}
	}	
	

	// Move transactions to default tag
	public void moveToDefaultTagDB(User user, Integer tagId, Integer defaultTagId) {

		String SQL = "UPDATE transactions  SET fk_tag_id = ?  WHERE fk_tag_id = ?;";
		jdbcTemplateObject.update(SQL, defaultTagId, tagId);

	}

	public void moveToDefaultTagCollection(User user, Integer tagId, Integer defaultTagId) {
		for (Integer key : user.getTransactions().keySet()) {
			Transaction transaction = user.getTransaction(key);
			if (transaction.getFk_tag_id() == tagId) {
				transaction.setFk_tag_id(defaultTagId);
			}
		}
	}
	
	public void transactionValidation(Transaction transaction){
		
		
		
	}
}
