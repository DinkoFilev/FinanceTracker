package com.budgetbeat.pojo;

public class Tag {
	private Integer tagId;
	private String name;
	private Integer userId;
	private Integer parentId;

	public Tag(Integer tagId, String name, Integer userId, Integer parentId) {
		this.tagId = tagId;
		this.name = name;
		this.userId = userId;
		this.parentId = parentId;
	}

	public Tag() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userID) {
		this.userId = userID;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

}
