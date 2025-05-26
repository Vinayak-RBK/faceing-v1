package com.iss.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Common_Users_Personal_Details_Table")
public class CommonUserDetailsTable {

	@Id
	@Column(name = "user_id", unique = true, length = 255)
	private String userId;

	@Column(name = "user_email_id", unique = true, columnDefinition = "TEXT")
	private String userEmail;

	@Column(name = "user_personal_detail_id", unique = true, columnDefinition = "TEXT")
	private String userPersonalDetailId;

	@Column(name = "user_name", columnDefinition = "TEXT")
	private String userName;

	@Column(name = "user_gender", columnDefinition = "TEXT")
	private String userGender;

	@Column(name = "user_dob", columnDefinition = "TEXT")
	private String userDOB;

	@Column(name = "user_weight", columnDefinition = "TEXT")
	private String userWeight;

	@Column(name = "user_height", columnDefinition = "TEXT")
	private String userHeight;

	@Column(name = "user_image", columnDefinition = "TEXT")
	private String userImage;

	@Column(name = "is_Blocked", columnDefinition = "TEXT")
	private String isBlocked;

	@Column(name = "sdk_type", columnDefinition = "TEXT")
	private String sDKType;

	@Column(name = "user_password", columnDefinition = "TEXT")
	private String password;

	@Column(name = "job_role", columnDefinition = "TEXT")
	private String jobRole;

	public CommonUserDetailsTable() {
		super();
	}

	public CommonUserDetailsTable(String isBlocked) {
		super();
		this.isBlocked = isBlocked;
	}

	public CommonUserDetailsTable(String userId, String userEmail, String userPersonalDetailId, String userName,
			String userGender, String userDOB, String userWeight, String userHeight, String userImage, String isBlocked,
			String sDKType, String password, String jobRole) {
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
		this.isBlocked = isBlocked;
		this.sDKType = sDKType;
		this.password = password;
		this.jobRole = jobRole;
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

	public String getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(String isBlocked) {
		this.isBlocked = isBlocked;
	}

	public String getsDKType() {
		return sDKType;
	}

	public void setsDKType(String sDKType) {
		this.sDKType = sDKType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}

	@Override
	public String toString() {
		return "CommonUserDetailsTable [userId=" + userId + ", userEmail=" + userEmail + ", userPersonalDetailId="
				+ userPersonalDetailId + ", userName=" + userName + ", userGender=" + userGender + ", userDOB="
				+ userDOB + ", userWeight=" + userWeight + ", userHeight=" + userHeight + ", userImage=" + userImage
				+ ", isBlocked=" + isBlocked + ", sDKType=" + sDKType + ", password=" + password + ", jobRole="
				+ jobRole + "]";
	}

}
