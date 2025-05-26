package com.iss.dao;

public class UserDao {
	
	private String userId;
	private String emailId;
	private String password;
	private String confirmPassword;
	private String OTP;
	private String otpEntryDate;
	private boolean termCond;
	private boolean onSession;
	
	public UserDao() {
		super();
	}

	public UserDao(String userId, String emailId, String password, String confirmPassword, String oTP,
			String otpEntryDate, boolean termCond, boolean onSession) {
		super();
		this.userId = userId;
		this.emailId = emailId;
		this.password = password;
		this.confirmPassword = confirmPassword;
		OTP = oTP;
		this.otpEntryDate = otpEntryDate;
		this.termCond = termCond;
		this.onSession = onSession;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String oTP) {
		OTP = oTP;
	}

	public String getOtpEntryDate() {
		return otpEntryDate;
	}

	public void setOtpEntryDate(String otpEntryDate) {
		this.otpEntryDate = otpEntryDate;
	}

	public boolean isTermCond() {
		return termCond;
	}

	public void setTermCond(boolean termCond) {
		this.termCond = termCond;
	}

	public boolean isOnSession() {
		return onSession;
	}

	public void setOnSession(boolean onSession) {
		this.onSession = onSession;
	}

	@Override
	public String toString() {
		return "UserDao [userId=" + userId + ", emailId=" + emailId + ", password=" + password + ", confirmPassword="
				+ confirmPassword + ", OTP=" + OTP + ", otpEntryDate=" + otpEntryDate + ", termCond=" + termCond
				+ ", onSession=" + onSession + "]";
	}

}
