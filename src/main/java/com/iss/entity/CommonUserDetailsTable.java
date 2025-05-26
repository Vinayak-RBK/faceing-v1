package com.iss.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Common_Users_Personal_Details_Table")
public class CommonUserDetailsTable {

	@Id
	@Column(name = "user_id", length = 10, unique = true)
	private String userId;

	@Column(name = "user_email_id", length = 50, nullable = false, unique = true)
	private String userEmail;

	@Column(name = "user_personal_detail_id", length = 10, unique = true)
	private Integer userPersonalDetailId;

	@Column(name = "user_name", nullable = false, length = 40)
	private String userName;

	@Column(name = "user_gender", nullable = false, length = 10)
	private String userGender;

	@Column(name = "user_dob")
	private String userDOB;

	@Column(name = "user_weight", precision = 5, scale = 2, nullable = true)
	private BigDecimal userWeight;

	@Column(name = "user_height", precision = 5, scale = 2, nullable = true)
	private BigDecimal userHeight;

	@Column(name = "user_image", length = 100, nullable = true)
	private String userImage;

	@Column(name = "is_Blocked", columnDefinition = "TINYINT(1)")
	private Boolean isBlocked;

	public CommonUserDetailsTable(Boolean isBlocked) {
		super();
		this.isBlocked = isBlocked;
	}

	public CommonUserDetailsTable(String userId, String userEmail, Integer userPersonalDetailId, String userName,
			String userGender, String userDOB, BigDecimal userWeight, BigDecimal userHeight, String userImage,
			Boolean isBlocked) {
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
	}

	public Boolean getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public CommonUserDetailsTable() {
		super();
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
		return "CommonUserDetailsTable [userId=" + userId + ", userEmail=" + userEmail + ", userPersonalDetailId="
				+ userPersonalDetailId + ", userName=" + userName + ", userGender=" + userGender + ", userDOB="
				+ userDOB + ", userWeight=" + userWeight + ", userHeight=" + userHeight + ", userImage=" + userImage
				+ ", isBlocked=" + isBlocked + "]";
	}

}
