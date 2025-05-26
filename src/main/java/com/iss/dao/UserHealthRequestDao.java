package com.iss.dao;

public class UserHealthRequestDao {

	private String userId;
	private String fieldName;
	private String startDate;
	private String endDate;

	public UserHealthRequestDao() {
		super();
	}

	public UserHealthRequestDao(String userId, String fieldName, String startDate, String endDate) {
		super();
		this.userId = userId;
		this.fieldName = fieldName;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "UserHealthRequestDao [userId=" + userId + ", fieldName=" + fieldName + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}

}
