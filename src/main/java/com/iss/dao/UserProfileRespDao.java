package com.iss.dao;

public class UserProfileRespDao {

	private String userId;
	private String imagePath;
	private String message;
	private boolean success;

	public UserProfileRespDao() {
		super();
	}

	public UserProfileRespDao(String userId, String imagePath, String message, boolean success) {
		super();
		this.userId = userId;
		this.imagePath = imagePath;
		this.message = message;
		this.success = success;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "UserProfileRespDao [userId=" + userId + ", imagePath=" + imagePath + ", message=" + message
				+ ", success=" + success + "]";
	}

}
