package com.iss.dao;

import java.math.BigDecimal;

public class CommonUserDetailsDao {
	private String userId;
	private String userEmail;
	private String userPersonalDetailId;
	private String userName;
	private String userGender;
	private String userDOB;
	private String userWeight;
	private String userHeight;
	private String userImage;
		
	public CommonUserDetailsDao() {
		super();
	}

	public CommonUserDetailsDao(String userId, String userEmail, String userPersonalDetailId, String userName,
			String userGender, String userDOB, String userWeight, String userHeight, String userImage) {
		super();
		this.userId = userId;
		this.userEmail = userEmail;
		this.userPersonalDetailId = userPersonalDetailId;
		this.userName = userName;
		this.userGender = userGender;
		this.userDOB = userDOB;
		this.userWeight = userWeight;
		this.userHeight = userHeight;
		this.userImage = userImage;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPersonalDetailId() {
		return userPersonalDetailId;
	}

	public void setUserPersonalDetailId(String userPersonalDetailId) {
		this.userPersonalDetailId = userPersonalDetailId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserDOB() {
		return userDOB;
	}

	public void setUserDOB(String userDOB) {
		this.userDOB = userDOB;
	}

	public String getUserWeight() {
		return userWeight;
	}

	public void setUserWeight(String userWeight) {
		this.userWeight = userWeight;
	}

	public String getUserHeight() {
		return userHeight;
	}

	public void setUserHeight(String userHeight) {
		this.userHeight = userHeight;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	@Override
	public String toString() {
		return "CommonUserDetailsDao [userId=" + userId + ", userEmail=" + userEmail + ", userPersonalDetailId="
				+ userPersonalDetailId + ", userName=" + userName + ", userGender=" + userGender + ", userDOB="
				+ userDOB + ", userWeight=" + userWeight + ", userHeight=" + userHeight + ", userImage=" + userImage
				+ "]";
	}

	
}
