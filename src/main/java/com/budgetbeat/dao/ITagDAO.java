package com.budgetbeat.dao;

import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;

import com.budgetbeat.pojo.Tag;
import com.budgetbeat.pojo.User;

public interface ITagDAO {

	public void setDataSource(DataSource ds);

	public Tag create(Tag tag);

	public Tag getTag(Integer tagId);

	public List<Tag> listTgs(Integer userId);

	public void update(Integer tagId, String name);

	public void delete(User userm,Integer tagId,Integer defaultTagId) throws DataAccessException;
}
