package com.iss.dao;

public class UserHealthInfoDao {

	private String userId;
	private UserHealthAnuraDetailsDao anuraDetails;
	private UserHealthBinahDetailsDao binahDetails;

	public UserHealthInfoDao() {
		super();
	}

	public UserHealthInfoDao(String userId, UserHealthAnuraDetailsDao anuraDetails,
			UserHealthBinahDetailsDao binahDetails) {
		super();
		this.userId = userId;
		this.anuraDetails = anuraDetails;
		this.binahDetails = binahDetails;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserHealthAnuraDetailsDao getAnuraDetails() {
		return anuraDetails;
	}

	public void setAnuraDetails(UserHealthAnuraDetailsDao anuraDetails) {
		this.anuraDetails = anuraDetails;
	}

	public UserHealthBinahDetailsDao getBinahDetails() {
		return binahDetails;
	}

	public void setBinahDetails(UserHealthBinahDetailsDao binahDetails) {
		this.binahDetails = binahDetails;
	}

	@Override
	public String toString() {
		return "UserHealthInfoDao [userId=" + userId + ", anuraDetails=" + anuraDetails + ", binahDetails="
				+ binahDetails + "]";
	}

}
