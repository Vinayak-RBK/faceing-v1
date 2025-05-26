package com.iss.dao;

public class UserDao {

	private String userId;
	private String emailId;
	private String password;
	private String confirmPassword;
	private String otp;
	private String otpEntryDate;
	private String termCond;
	private String onSession;
	private String isVerified;
	private String isOnBoarded;
	private String isLogin;
	private String pushNotify;
	private String loginAttemptFailCount;
	private String loginAttemptMaxReleaseTime;
	private String lockedDateTimeForLogin;
	private String maxAttemptFailBlockForLogin;
	private String isBlocked;
	private String sDKType;
	private String healthId;

	public UserDao() {
		super();
	}

	public UserDao(String userId, String emailId, String password, String confirmPassword, String otp,
			String otpEntryDate, String termCond, String onSession, String isVerified, String isOnBoarded,
			String isLogin, String pushNotify, String loginAttemptFailCount, String loginAttemptMaxReleaseTime,
			String lockedDateTimeForLogin, String maxAttemptFailBlockForLogin, String isBlocked, String sDKType,
			String healthId) {
		super();
		this.userId = userId;
		this.emailId = emailId;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.otp = otp;
		this.otpEntryDate = otpEntryDate;
		this.termCond = termCond;
		this.onSession = onSession;
		this.isVerified = isVerified;
		this.isOnBoarded = isOnBoarded;
		this.isLogin = isLogin;
		this.pushNotify = pushNotify;
		this.loginAttemptFailCount = loginAttemptFailCount;
		this.loginAttemptMaxReleaseTime = loginAttemptMaxReleaseTime;
		this.lockedDateTimeForLogin = lockedDateTimeForLogin;
		this.maxAttemptFailBlockForLogin = maxAttemptFailBlockForLogin;
		this.isBlocked = isBlocked;
		this.sDKType = sDKType;
		this.healthId = healthId;
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

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getOtpEntryDate() {
		return otpEntryDate;
	}

	public void setOtpEntryDate(String otpEntryDate) {
		this.otpEntryDate = otpEntryDate;
	}

	public String getTermCond() {
		return termCond;
	}

	public void setTermCond(String termCond) {
		this.termCond = termCond;
	}

	public String getOnSession() {
		return onSession;
	}

	public void setOnSession(String onSession) {
		this.onSession = onSession;
	}

	public String getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(String isVerified) {
		this.isVerified = isVerified;
	}

	public String getIsOnBoarded() {
		return isOnBoarded;
	}

	public void setIsOnBoarded(String isOnBoarded) {
		this.isOnBoarded = isOnBoarded;
	}

	public String getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(String isLogin) {
		this.isLogin = isLogin;
	}

	public String getPushNotify() {
		return pushNotify;
	}

	public void setPushNotify(String pushNotify) {
		this.pushNotify = pushNotify;
	}

	public String getLoginAttemptFailCount() {
		return loginAttemptFailCount;
	}

	public void setLoginAttemptFailCount(String loginAttemptFailCount) {
		this.loginAttemptFailCount = loginAttemptFailCount;
	}

	public String getLoginAttemptMaxReleaseTime() {
		return loginAttemptMaxReleaseTime;
	}

	public void setLoginAttemptMaxReleaseTime(String loginAttemptMaxReleaseTime) {
		this.loginAttemptMaxReleaseTime = loginAttemptMaxReleaseTime;
	}

	public String getLockedDateTimeForLogin() {
		return lockedDateTimeForLogin;
	}

	public void setLockedDateTimeForLogin(String lockedDateTimeForLogin) {
		this.lockedDateTimeForLogin = lockedDateTimeForLogin;
	}

	public String getMaxAttemptFailBlockForLogin() {
		return maxAttemptFailBlockForLogin;
	}

	public void setMaxAttemptFailBlockForLogin(String maxAttemptFailBlockForLogin) {
		this.maxAttemptFailBlockForLogin = maxAttemptFailBlockForLogin;
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

	public String getHealthId() {
		return healthId;
	}

	public void setHealthId(String healthId) {
		this.healthId = healthId;
	}

	@Override
	public String toString() {
		return "UserDao [userId=" + userId + ", emailId=" + emailId + ", password=" + password + ", confirmPassword="
				+ confirmPassword + ", otp=" + otp + ", otpEntryDate=" + otpEntryDate + ", termCond=" + termCond
				+ ", onSession=" + onSession + ", isVerified=" + isVerified + ", isOnBoarded=" + isOnBoarded
				+ ", isLogin=" + isLogin + ", pushNotify=" + pushNotify + ", loginAttemptFailCount="
				+ loginAttemptFailCount + ", loginAttemptMaxReleaseTime=" + loginAttemptMaxReleaseTime
				+ ", lockedDateTimeForLogin=" + lockedDateTimeForLogin + ", maxAttemptFailBlockForLogin="
				+ maxAttemptFailBlockForLogin + ", isBlocked=" + isBlocked + ", sDKType=" + sDKType + ", healthId="
				+ healthId + "]";
	}

}
