package com.iss.dao;

public class AdminOperationResponseDao {
	
	private boolean success;
	private String message;
	private String userId;
	
	public AdminOperationResponseDao() {
		super();
	}

	public AdminOperationResponseDao(boolean success, String message, String userId) {
		super();
		this.success = success;
		this.message = message;
		this.userId = userId;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "AdminOperationResponseDao [success=" + success + ", message=" + message + ", userId=" + userId + "]";
	}

}
