package com.iss.dao;

public class PersonalDetailsDao {

	private String userEmail;
	private String password;
	private String userName;
	private String userGender;
	private String userDOB;
	private double userWeight;
	private double userHeight;
	private String userImage;
	
	public PersonalDetailsDao() {
		super();
	}

	public PersonalDetailsDao(String userEmail, String password, String userName, String userGender, String userDOB,
			double userWeight, double userHeight, String userImage) {
		super();
		this.userEmail = userEmail;
		this.password = password;
		this.userName = userName;
		this.userGender = userGender;
		this.userDOB = userDOB;
		this.userWeight = userWeight;
		this.userHeight = userHeight;
		this.userImage = userImage;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public double getUserWeight() {
		return userWeight;
	}

	public void setUserWeight(double userWeight) {
		this.userWeight = userWeight;
	}

	public double getUserHeight() {
		return userHeight;
	}

	public void setUserHeight(double userHeight) {
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
		return "PersonalDetailsDao [userEmail=" + userEmail + ", password=" + password + ", userName=" + userName
				+ ", userGender=" + userGender + ", userDOB=" + userDOB + ", userWeight=" + userWeight + ", userHeight="
				+ userHeight + ", userImage=" + userImage + "]";
	}
	
}
