package com.iss.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Users_Personal_Details")
public class UsersPersonalDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userPersonalDetailSeqGen")
	@SequenceGenerator(name = "userPersonalDetailSeqGen", sequenceName = "user_personal_details_sequence", initialValue = 1000000000, allocationSize = 1)
	@Column(name = "user_personal_detail_id", length = 10, unique = true)
	private Long userPersonalDetailId;
	
	@Column(name = "user_email_id", columnDefinition = "TEXT", unique = true)
	private String userEmail;

	@Column(name = "user_name",columnDefinition = "TEXT")
	private String userName;

	@Column(name = "user_gender",columnDefinition = "TEXT")
	private String userGender;

	@Column(name = "user_dob",columnDefinition = "TEXT")
	private String userDOB;

	@Column(name = "user_weight", columnDefinition = "TEXT")
	private String userWeight;

	@Column(name = "user_height", columnDefinition = "TEXT")
	private String userHeight;

	@Column(name = "user_image", columnDefinition = "TEXT")
	private String userImage;

	@Column(name = "regist_date",columnDefinition = "TEXT")
	private String registDate;

	@Column(name = "regist_pname", columnDefinition = "TEXT")
	private String registPName;

	@Column(name = "last_update_date", columnDefinition = "TEXT")
	private String lastUpdateDate;

	@Column(name = "last_update_pname",columnDefinition = "TEXT")
	private String lastUpdatePName;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private EndUser endUser; 

	public UsersPersonalDetails() {
		super();
	}

	public UsersPersonalDetails(Long userPersonalDetailId, String userEmail, String userName, String userGender,
			String userDOB, String userWeight, String userHeight, String userImage, String registDate,
			String registPName, String lastUpdateDate, String lastUpdatePName, EndUser endUser) {
		super();
		this.userPersonalDetailId = userPersonalDetailId;
		this.userEmail = userEmail;
		this.userName = userName;
		this.userGender = userGender;
		this.userDOB = userDOB;
		this.userWeight = userWeight;
		this.userHeight = userHeight;
		this.userImage = userImage;
		this.registDate = registDate;
		this.registPName = registPName;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdatePName = lastUpdatePName;
		this.endUser = endUser;
	}

	public Long getUserPersonalDetailId() {
		return userPersonalDetailId;
	}

	public void setUserPersonalDetailId(Long userPersonalDetailId) {
		this.userPersonalDetailId = userPersonalDetailId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	public String getRegistPName() {
		return registPName;
	}

	public void setRegistPName(String registPName) {
		this.registPName = registPName;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getLastUpdatePName() {
		return lastUpdatePName;
	}

	public void setLastUpdatePName(String lastUpdatePName) {
		this.lastUpdatePName = lastUpdatePName;
	}

	public EndUser getEndUser() {
		return endUser;
	}

	public void setEndUser(EndUser endUser) {
		this.endUser = endUser;
	}

	@Override
	public String toString() {
		return "UsersPersonalDetails [userPersonalDetailId=" + userPersonalDetailId + ", userEmail=" + userEmail
				+ ", userName=" + userName + ", userGender=" + userGender + ", userDOB=" + userDOB + ", userWeight="
				+ userWeight + ", userHeight=" + userHeight + ", userImage=" + userImage + ", registDate=" + registDate
				+ ", registPName=" + registPName + ", lastUpdateDate=" + lastUpdateDate + ", lastUpdatePName="
				+ lastUpdatePName + ", endUser=" + endUser + "]";
	}
}
