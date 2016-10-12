package com.budgetbeat.manager;

import java.util.Calendar;
import java.sql.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.budgetbeat.dao.ITransactionDAO;
import com.budgetbeat.pojo.Transaction;
import com.budgetbeat.pojo.TransactionMapper;
import com.budgetbeat.pojo.User;
import com.budgetbeat.pojo.UserMapper;


public class TransactionManager implements ITransactionDAO{
	private ConcurrentHashMap<Integer, Transaction> userTransactions = new ConcurrentHashMap<>();
	private JdbcTemplate jdbcTemplateObject;
	private DataSource dataSource;
	
	
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
		
	}

	@Override
	public void create(Integer fk_user_id, Integer ft_account_id, Integer fk_tag_id, String description, Double amount,
			Date date, String file, Boolean status, Long step, Boolean repeat) {
		String SQL = "INSERT INTO transactions (fk_user_id,ft_account_id,fk_tag_id,description,amount,date,file,status,step,transaction_repeat) values (?,?,?,?,?,?,?,?,?,?)";
			
		jdbcTemplateObject.update(SQL,fk_user_id,ft_account_id,fk_tag_id,description,amount,date,file,status,step,repeat);
		System.out.println("Created Record Transacton : FK USER = " + fk_user_id + " FT ACCOUNT = " + ft_account_id + " FK TAG = " + fk_tag_id
				+ "  Description, = " +  description +" Amount : "+ amount + " Date "+date+" file "+file+" status "+status+" step "+step+" repeat "+repeat);
		
		return;
		
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
	 * @author CaveMan
	 *	List of all transactions made by all users
	 */
	@Override
	public List<Transaction> listOfAllTransaction() {
		String SQL = "SELECT * from transactions";
		List<Transaction> transaction = jdbcTemplateObject.query(SQL, new TransactionMapper());
		return transaction;
	}
	
	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Integer transaction_id, Integer fk_user_id, Integer ft_account_id, Integer fk_tag_id,
			String description, Double amount, Calendar date, String file, Boolean status, Long step, Boolean repeat) {
		// TODO Auto-generated method stub
		
	}
	
	

}
