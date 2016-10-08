package com.budgetbeat.dao;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.budgetbeat.pojo.Tag;
import com.budgetbeat.pojo.TagMapper;

public class TagDAO implements ITagDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public void create(String name, Integer userId, Integer parentId) {
		String SQL = "insert into tags (name, fk_user_id, fk_parent_id) values (?, ?, ?)";

		jdbcTemplateObject.update(SQL, name, userId, parentId);
		System.out.println("Created Tag Name = " + name + " ParentID = " + parentId + " UserID = " + userId);
		return;
	}

	@Override
	public Tag getTag(Integer tagId) {
		String SQL = "select * from tags where tag_id = ?";
		Tag tag = jdbcTemplateObject.queryForObject(SQL, new Object[] { tagId }, new TagMapper());
		return tag;
	}

	@Override
	public List<Tag> listTgs(Integer userId) {
		String SQL = "select * from tags where fk_user_id = ?";
		List<Tag> tags = jdbcTemplateObject.query(SQL, new Object[] { userId }, new TagMapper());
		return tags;
	}

	public List<String> listTopTagNames(Integer top) {
		if (top < 5)
			top = 5;
		String SQL = "SELECT count(name) as count, tag_id, name, fk_parent_id, fk_user_id  FROM finance_tracker.tags group by tag_id, name, fk_parent_id, fk_user_id order by count desc limit ?";
		List<Tag> tags = jdbcTemplateObject.query(SQL, new Object[] { top }, new TagMapper());
		List<String> result = new ArrayList<>();
		tags.forEach(item -> result.add(item.getName()));
		return result;
	}

	/*
	 * usersSELECT `tags`.`tag_id`, `tags`.`name`, `tags`.`fk_parent_id`,
	 * `tags`.`fk_user_id` FROM `finance_tracker`.`tags`;
	 */

	@Override
	public void delete(Integer tagId) {
		String SQL = "delete from tags where tag_id = ?";
		jdbcTemplateObject.update(SQL, tagId);
		System.out.println("Deleted Tag with ID = " + tagId);
		return;
	}

	@Override
	public void update(Integer tagId, String name) {
		String SQL = "update tags set name = ? where tag_id = ?";
		jdbcTemplateObject.update(SQL, name, tagId);
		System.out.println("Updated Record with ID = " + tagId);
		return;
	}
}
