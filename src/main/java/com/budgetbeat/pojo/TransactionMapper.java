package com.budgetbeat.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

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
		   transaction.setDate(dateToCalendar(rs.getDate("date")));
		   transaction.setFile(rs.getString("file"));
		   transaction.setStatus(rs.getBoolean("status"));
		   transaction.setStep(rs.getLong("step"));
		   transaction.setRepeat(rs.getBoolean("repeat"));
	      return transaction;
	   }
	   
	   public static Calendar dateToCalendar(Date date){
		   if(date == null){
			  Calendar cal = Calendar.getInstance();
			  cal.setTime(cal.getTime());
			  return cal;
		   }
		   Calendar cal = Calendar.getInstance();
		   cal.setTime(date);
		   return cal;
		 }
	}

