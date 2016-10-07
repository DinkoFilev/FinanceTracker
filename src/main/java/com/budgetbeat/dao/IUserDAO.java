package com.budgetbeat.dao;
import java.util.List;
import javax.sql.DataSource;

import com.budgetbeat.pojo.User;

public interface IUserDAO {

	   public void setDataSource(DataSource dataSource);
	 
	   public void create(String firstName, String lastName, String email, String password);
	 
	   public User getUser(Integer id);
	  
	   public List<User> listUsers();
	  
	   public void delete(Integer id);
	   
	   public void update(Integer userID ,String firstName, String lastName, String email, String password);
	}


