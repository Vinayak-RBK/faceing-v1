package com.iss.dao;

public class FileObjectDao {
	
	private String profileImagePath;
	private boolean success;
	
	public FileObjectDao() {
		super();
	}

	public FileObjectDao(String profileImagePath, boolean success) {
		super();
		this.profileImagePath = profileImagePath;
		this.success = success;
	}

	public String getProfileImagePath() {
		return profileImagePath;
	}

	public void setProfileImagePath(String profileImagePath) {
		this.profileImagePath = profileImagePath;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "FileObjectDao [profileImagePath=" + profileImagePath + ", success=" + success + "]";
	}
	
}
