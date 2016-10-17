package com.budgetbeat.dao;


import java.util.List;

import javax.sql.DataSource;

import com.budgetbeat.pojo.User;

public interface IUserDAO {

	public void setDataSource(DataSource dataSource);

	public int create(String firstName, String lastName, String email, String password);

	public User getUserbyID(Integer id);

	public User getUserbyEmail(String email);

	public List<User> listUsers();

	public void delete(Integer id);

	public void update(User olduser ,Integer userID, String firstName, String lastName, String email, String password);
}
