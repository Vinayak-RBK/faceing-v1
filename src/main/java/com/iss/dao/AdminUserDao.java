package com.iss.dao;

public class AdminUserDao {

	private String adminId;
	private String email;
	private String password;
	private String confirmPassword;
	private String adminOTP;
	private String isAdmin;
	private String isUserManagement;
	private String isQuestionaries;
	private String isLegalSetting;
	private String role;
	private String otpEntryDate;
	private String termCond;
	private String adminOnSession;
	private String isVerified;
	private String loginAttemptFailCount;
	private String loginAttemptMaxReleaseTime;
	private String lockedDateTimeForLogin;
	private String maxAttemptFailBlockForLogin;
	private String isBlocked;

	public AdminUserDao() {
		super();
	}

	public AdminUserDao(String adminId, String email, String password, String confirmPassword, String adminOTP,
			String isAdmin, String isUserManagement, String isQuestionaries, String isLegalSetting, String role,
			String otpEntryDate, String termCond, String adminOnSession, String isVerified,
			String loginAttemptFailCount, String loginAttemptMaxReleaseTime, String lockedDateTimeForLogin,
			String maxAttemptFailBlockForLogin, String isBlocked) {
		super();
		this.adminId = adminId;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.adminOTP = adminOTP;
		this.isAdmin = isAdmin;
		this.isUserManagement = isUserManagement;
		this.isQuestionaries = isQuestionaries;
		this.isLegalSetting = isLegalSetting;
		this.role = role;
		this.otpEntryDate = otpEntryDate;
		this.termCond = termCond;
		this.adminOnSession = adminOnSession;
		this.isVerified = isVerified;
		this.loginAttemptFailCount = loginAttemptFailCount;
		this.loginAttemptMaxReleaseTime = loginAttemptMaxReleaseTime;
		this.lockedDateTimeForLogin = lockedDateTimeForLogin;
		this.maxAttemptFailBlockForLogin = maxAttemptFailBlockForLogin;
		this.isBlocked = isBlocked;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getAdminOTP() {
		return adminOTP;
	}

	public void setAdminOTP(String adminOTP) {
		this.adminOTP = adminOTP;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getIsUserManagement() {
		return isUserManagement;
	}

	public void setIsUserManagement(String isUserManagement) {
		this.isUserManagement = isUserManagement;
	}

	public String getIsQuestionaries() {
		return isQuestionaries;
	}

	public void setIsQuestionaries(String isQuestionaries) {
		this.isQuestionaries = isQuestionaries;
	}

	public String getIsLegalSetting() {
		return isLegalSetting;
	}

	public void setIsLegalSetting(String isLegalSetting) {
		this.isLegalSetting = isLegalSetting;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
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

	public String getAdminOnSession() {
		return adminOnSession;
	}

	public void setAdminOnSession(String adminOnSession) {
		this.adminOnSession = adminOnSession;
	}

	public String getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(String isVerified) {
		this.isVerified = isVerified;
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

	@Override
	public String toString() {
		return "AdminUserDao [adminId=" + adminId + ", email=" + email + ", password=" + password + ", confirmPassword="
				+ confirmPassword + ", adminOTP=" + adminOTP + ", isAdmin=" + isAdmin + ", isUserManagement="
				+ isUserManagement + ", isQuestionaries=" + isQuestionaries + ", isLegalSetting=" + isLegalSetting
				+ ", role=" + role + ", otpEntryDate=" + otpEntryDate + ", termCond=" + termCond + ", adminOnSession="
				+ adminOnSession + ", isVerified=" + isVerified + ", loginAttemptFailCount=" + loginAttemptFailCount
				+ ", loginAttemptMaxReleaseTime=" + loginAttemptMaxReleaseTime + ", lockedDateTimeForLogin="
				+ lockedDateTimeForLogin + ", maxAttemptFailBlockForLogin=" + maxAttemptFailBlockForLogin
				+ ", isBlocked=" + isBlocked + "]";
	}

}
