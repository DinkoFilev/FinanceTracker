package com.budgetbeat.pojo;

public class Tag {
		private String name;
		private Integer userID;
		private Integer parentId;
		
		public Tag(Integer userID, String name, Integer parentId) {
			this.userID = userID;
			this.name = name;
			this.parentId = parentId;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getUserID() {
			return userID;
		}
		public void setUserID(Integer userID) {
			this.userID = userID;
		}
		public Integer getParentId() {
			return parentId;
		}
		public void setParentId(Integer parentId) {
			this.parentId = parentId;
		}
				
}
