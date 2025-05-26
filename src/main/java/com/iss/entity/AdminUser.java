package com.iss.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "AdminUsers")
public class AdminUser {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adminSeqGen")
	@SequenceGenerator(name = "adminSeqGen", sequenceName = "admin_sequence", initialValue = 1000000000, allocationSize = 1)
//	@GenericGenerator(name = "userSeqGen", strategy = "com.iss.sequencegenerator.UserSequenceGenerator",
//	parameters = {
//			@Parameter(name=UserSequenceGenerator.INCREMENT_PARAM,value = "50"),
//			@Parameter(name=UserSequenceGenerator.VALUE_PREFIX_PARAMETER, value = "B_"),
//			@Parameter(name = UserSequenceGenerator.NUMBER_FORMAT_PARAMETER, value = "%o5d")
//	}
//			)
	@Column(name = "admin_id", length = 16, unique = true)
	private Long adminId;

	@Column(name = "admin_email_id", length = 50, nullable = false, unique = true)
	private String adminEmail;

	@Column(name = "admin_password", length = 20, nullable = false)
	private String adminPassword;

	@Column(name = "admin_otp", length = 5, nullable = true)
	private String adminOTP;

	@Column(name = "is_admin", columnDefinition = "TINYINT(1)")
	private Boolean isAdmin;

	@Column(name = "is_userManagement", columnDefinition = "TINYINT(1)")
	private Boolean isUserManagement;

	@Column(name = "is_questionaries", columnDefinition = "TINYINT(1)")
	private Boolean isQuestionaries;

	@Column(name = "is_legal_setting", columnDefinition = "TINYINT(1)")
	private Boolean isLegalSetting;

	@Column(name = "role", length = 10)
	private int role;

	@Column(name = "otp_entry_date", length = 20)
	private String otpEntryDate;

	@Column(name = "term_cond", columnDefinition = "TINYINT(1)")
	private Boolean termCond;

	@Column(name = "admin_on_session", columnDefinition = "TINYINT(1)")
	private Boolean adminOnSession;

	@Column(name = "is_verified", columnDefinition = "TINYINT(1)")
	private Boolean isVerified;

	@Column(name = "login_attempt_fail_count")
	private int loginAttemptFailCount;

	@Column(name = "login_attempt_max_release_time")
	private int loginAttemptMaxReleaseTime;

	@Column(name = "locked_Date_Time_for_login", length = 20, nullable = true)
	private String lockedDateTimeForLogin;

	@Column(name = "max_attempt_fail_block_login", columnDefinition = "TINYINT(1)")
	private Boolean maxAttemptFailBlockForLogin;

	@Column(name = "regist_date", length = 20, nullable = true)
	private String registDate;

	@Column(name = "regist_pname", length = 50)
	private String registPName;

	@Column(name = "last_update_date", length = 20, nullable = true)
	private String lastUpdateDate;

	@Column(name = "last_update_pname", length = 50)
	private String lastUpdatePName;

	public AdminUser() {
		super();
	}

	public AdminUser(Long adminId, String adminEmail, String adminPassword, String adminOTP, Boolean isAdmin,
			Boolean isUserManagement, Boolean isQuestionaries, Boolean isLegalSetting, int role, String otpEntryDate,
			Boolean termCond, Boolean adminOnSession, Boolean isVerified, int loginAttemptFailCount,
			int loginAttemptMaxReleaseTime, String lockedDateTimeForLogin, Boolean maxAttemptFailBlockForLogin,
			String registDate, String registPName, String lastUpdateDate, String lastUpdatePName) {
		super();
		this.adminId = adminId;
		this.adminEmail = adminEmail;
		this.adminPassword = adminPassword;
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
		this.registDate = registDate;
		this.registPName = registPName;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdatePName = lastUpdatePName;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
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

	public Boolean getIsUserManagement() {
		return isUserManagement;
	}

	public void setIsUserManagement(Boolean isUserManagement) {
		this.isUserManagement = isUserManagement;
	}

	public Boolean getIsQuestionaries() {
		return isQuestionaries;
	}

	public void setIsQuestionaries(Boolean isQuestionaries) {
		this.isQuestionaries = isQuestionaries;
	}

	public Boolean getIsLegalSetting() {
		return isLegalSetting;
	}

	public void setIsLegalSetting(Boolean isLegalSetting) {
		this.isLegalSetting = isLegalSetting;
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

	public int getLoginAttemptFailCount() {
		return loginAttemptFailCount;
	}

	public void setLoginAttemptFailCount(int loginAttemptFailCount) {
		this.loginAttemptFailCount = loginAttemptFailCount;
	}

	public int getLoginAttemptMaxReleaseTime() {
		return loginAttemptMaxReleaseTime;
	}

	public void setLoginAttemptMaxReleaseTime(int loginAttemptMaxReleaseTime) {
		this.loginAttemptMaxReleaseTime = loginAttemptMaxReleaseTime;
	}

	public String getLockedDateTimeForLogin() {
		return lockedDateTimeForLogin;
	}

	public void setLockedDateTimeForLogin(String lockedDateTimeForLogin) {
		this.lockedDateTimeForLogin = lockedDateTimeForLogin;
	}

	public Boolean getMaxAttemptFailBlockForLogin() {
		return maxAttemptFailBlockForLogin;
	}

	public void setMaxAttemptFailBlockForLogin(Boolean maxAttemptFailBlockForLogin) {
		this.maxAttemptFailBlockForLogin = maxAttemptFailBlockForLogin;
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

	@Override
	public String toString() {
		return "AdminUser [adminId=" + adminId + ", adminEmail=" + adminEmail + ", adminPassword=" + adminPassword
				+ ", adminOTP=" + adminOTP + ", isAdmin=" + isAdmin + ", isUserManagement=" + isUserManagement
				+ ", isQuestionaries=" + isQuestionaries + ", isLegalSetting=" + isLegalSetting + ", role=" + role
				+ ", otpEntryDate=" + otpEntryDate + ", termCond=" + termCond + ", adminOnSession=" + adminOnSession
				+ ", isVerified=" + isVerified + ", loginAttemptFailCount=" + loginAttemptFailCount
				+ ", loginAttemptMaxReleaseTime=" + loginAttemptMaxReleaseTime + ", lockedDateTimeForLogin="
				+ lockedDateTimeForLogin + ", maxAttemptFailBlockForLogin=" + maxAttemptFailBlockForLogin
				+ ", registDate=" + registDate + ", registPName=" + registPName + ", lastUpdateDate=" + lastUpdateDate
				+ ", lastUpdatePName=" + lastUpdatePName + "]";
	}

}
