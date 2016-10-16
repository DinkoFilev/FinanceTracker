package com.budgetbeat.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.annotation.Transactional;

import com.budgetbeat.SpringWebConfig;
import com.budgetbeat.dao.ITagDAO;
import com.budgetbeat.pojo.Tag;
import com.budgetbeat.pojo.TagMapper;
import com.budgetbeat.pojo.Transaction;
import com.budgetbeat.pojo.User;

public class TagManager implements ITagDAO {

	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;
	
	@Override
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	@Override
	public Tag create(Tag tag) {
		String SQL = "insert into tags (name, fk_user_id) values (?, ?)";

		System.out.println(tag);

		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplateObject.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(SQL, new String[] { "tag_id" });
				ps.setString(1, tag.getName());
				ps.setInt(2, tag.getUserId());
				// ps.setInt(3, tag.getParentId());
				return ps;
			}
		}, keyHolder);
		tag.setTagId(keyHolder.getKey().intValue());

		return tag;

	}

	public void setInitialTags(Integer fk_user_id) {
		// Adds No tag
		String SQL = "insert into tags (name, fk_user_id) values (?, ?)";
		jdbcTemplateObject.update(SQL, "No tag", fk_user_id);

		// Adds top X tags
		//SQL = "INSERT INTO tags (name, fk_user_id) select name, ? from (SELECT count(name) as count, tag_id, name, fk_parent_id, fk_user_id  FROM finance_tracker.tags group by tag_id, name, fk_parent_id, fk_user_id order by count desc limit ?) as t";
		//jdbcTemplateObject.update(SQL, fk_user_id, 3);
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
	@Transactional
	public void delete(User user,Integer tagId,Integer defaultTagId) {
		// Change all accounts to default tag
		TransactionManager tranManager = (TransactionManager) SpringWebConfig.context.getBean("TransactionManager");
		//tranManager.moveToDefaultTagDB(user, tagId, defaultTagId);
		
		String SQL = "UPDATE transactions  SET fk_tag_id = ?  WHERE fk_tag_id = ?;";
		jdbcTemplateObject.update(SQL, defaultTagId, tagId);
		 SQL = "delete from tags where tag_id = ?";
		jdbcTemplateObject.update(SQL, tagId);
		System.out.println("Deleted Tag with ID = " + tagId);
		tranManager.moveToDefaultTagCollection(user, tagId, user.getTags().lastKey());
		user.getTags().remove(tagId);
		
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
