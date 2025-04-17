package com.iss.dao;

public class UserProfileDao {

	private String userId;
	private String imagePath;

	public UserProfileDao() {
		super();
	}

	public UserProfileDao(String userId, String imagePath) {
		super();
		this.userId = userId;
		this.imagePath = imagePath;
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

	@Override
	public String toString() {
		return "UserProfileDao [userId=" + userId + ", imagePath=" + imagePath + "]";
	}

}
