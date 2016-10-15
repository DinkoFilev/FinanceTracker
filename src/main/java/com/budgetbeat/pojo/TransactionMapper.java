package com.budgetbeat.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import org.springframework.jdbc.core.RowMapper;

public class TransactionMapper implements RowMapper<Transaction> {
	public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
		Transaction transaction = new Transaction();
		transaction.setTransaction_id(rs.getInt("transaction_id"));
		transaction.setFk_user_id(rs.getInt("fk_user_id"));
		transaction.setFt_account_id(rs.getInt("ft_account_id"));
		transaction.setFk_tag_id(rs.getInt("fk_tag_id"));
		transaction.setDescription(rs.getString("description"));
		transaction.setAmount(rs.getDouble("amount"));
		transaction.setDate(rs.getDate("date"));
		transaction.setFile(rs.getString("file"));
		transaction.setStatus(rs.getBoolean("status"));
		transaction.setStep(rs.getLong("step"));
		transaction.setRepeat(rs.getBoolean("transaction_repeat"));
		transaction.setIncome(rs.getBoolean("income"));
		return transaction;
	}

}
