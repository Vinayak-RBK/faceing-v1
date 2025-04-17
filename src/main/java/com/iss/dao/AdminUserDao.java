package com.iss.dao;

public class AdminUserDao {

	private Integer adminId;
	private String email;
	private String password;
	private String confirmPassword;
	private String adminOTP;
	private Boolean isAdmin;
	private int role;
	private String otpEntryDate;
	private Boolean termCond;
	private Boolean adminOnSession;
	private Boolean isVerified;
	
	public AdminUserDao() {
		super();
	}

	public AdminUserDao(Integer adminId, String email, String password, String confirmPassword, String adminOTP,
			Boolean isAdmin, int role, String otpEntryDate, Boolean termCond, Boolean adminOnSession,
			Boolean isVerified) {
		super();
		this.adminId = adminId;
		this.email = email;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.adminOTP = adminOTP;
		this.isAdmin = isAdmin;
		this.role = role;
		this.otpEntryDate = otpEntryDate;
		this.termCond = termCond;
		this.adminOnSession = adminOnSession;
		this.isVerified = isVerified;
	}

	public Integer getAdminId() {
		return adminId;
	}

	public void setAdminId(Integer adminId) {
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

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getOtpEntryDate() {
		return otpEntryDate;
	}

	public void setOtpEntryDate(String otpEntryDate) {
		this.otpEntryDate = otpEntryDate;
	}

	public Boolean getTermCond() {
		return termCond;
	}

	public void setTermCond(Boolean termCond) {
		this.termCond = termCond;
	}

	public Boolean getAdminOnSession() {
		return adminOnSession;
	}

	public void setAdminOnSession(Boolean adminOnSession) {
		this.adminOnSession = adminOnSession;
	}

	public Boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	@Override
	public String toString() {
		return "AdminUserDao [adminId=" + adminId + ", email=" + email + ", password=" + password + ", confirmPassword="
				+ confirmPassword + ", adminOTP=" + adminOTP + ", isAdmin=" + isAdmin + ", role=" + role
				+ ", otpEntryDate=" + otpEntryDate + ", termCond=" + termCond + ", adminOnSession=" + adminOnSession
				+ ", isVerified=" + isVerified + "]";
	}

}
