package com.iss.dao;

import java.math.BigDecimal;

public class CommonUserDetailsDao {
	private Integer userId;
	private String userEmail;
	private Integer userPersonalDetailId;
	private String userName;
	private String userGender;
	private String userDOB;
	private BigDecimal userWeight;
	private BigDecimal userHeight;
	private String userImage;
		
	public CommonUserDetailsDao() {
		super();
	}

	public CommonUserDetailsDao(Integer userId, String userEmail, Integer userPersonalDetailId, String userName,
			String userGender, String userDOB, BigDecimal userWeight, BigDecimal userHeight, String userImage) {
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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Integer getUserPersonalDetailId() {
		return userPersonalDetailId;
	}

	public void setUserPersonalDetailId(Integer userPersonalDetailId) {
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

	public BigDecimal getUserWeight() {
		return userWeight;
	}

	public void setUserWeight(BigDecimal userWeight) {
		this.userWeight = userWeight;
	}

	public BigDecimal getUserHeight() {
		return userHeight;
	}

	public void setUserHeight(BigDecimal userHeight) {
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
