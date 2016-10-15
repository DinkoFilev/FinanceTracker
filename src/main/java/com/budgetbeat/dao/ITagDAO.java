package com.budgetbeat.dao;

import java.util.List;

import javax.sql.DataSource;

import com.budgetbeat.pojo.Tag;

public interface ITagDAO {

	public void setDataSource(DataSource ds);

	public Tag create(Tag tag);

	public Tag getTag(Integer tagId);

	public List<Tag> listTgs(Integer userId);

	public void delete(Integer tagId, Integer defaultTagId );

	public void update(Integer tagId, String name);
}
