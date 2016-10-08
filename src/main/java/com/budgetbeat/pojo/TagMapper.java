package com.budgetbeat.pojo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TagMapper implements RowMapper<Tag> {
	@Override
	public Tag mapRow(ResultSet rs, int rowNum) throws SQLException {
		Tag tag = new Tag();
		tag.setTagId(rs.getInt("tag_id"));
		tag.setName(rs.getString("name"));
		tag.setUserId(rs.getInt("fk_user_id"));
		tag.setParentId(rs.getInt("fk_parent_id"));
		return tag;
	}
}


