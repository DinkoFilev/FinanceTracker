package com.budgetbeat.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class UserMapper implements RowMapper<User> {
   public User mapRow(ResultSet rs, int rowNum) throws SQLException {
	   User user = new User();
	   user.setUserID(rs.getInt("user_id"));
	   user.setFirstName(rs.getString("first_name"));
	   user.setLastName(rs.getString("last_name"));
	   user.setEmail(rs.getString("email"));
	   user.setPassword(rs.getString("password"));
      return user;
   }
}