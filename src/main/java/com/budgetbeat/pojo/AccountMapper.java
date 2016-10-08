package com.budgetbeat.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class AccountMapper implements RowMapper<Account> {
	@Override
	public Account mapRow(ResultSet rs, int rowNum) throws SQLException {
		Account account = new Account();
		account.setAccountId(rs.getInt("account_id"));
		account.setName(rs.getString("name"));
		account.setFk_userId(rs.getInt("fk_user_id"));
		account.setBalance(rs.getDouble("balance"));
		account.setInstitution(rs.getString("institution"));
		account.setStatus(rs.getBoolean("status"));
		return account;
	}
}
