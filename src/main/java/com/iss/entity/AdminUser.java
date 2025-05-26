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
	@Column(name = "admin_id", unique = true)
	private Long adminId;
	
	@Column(name = "admin_id_ref",columnDefinition = "TEXT")
	private String adminIdRef;

	@Column(name = "admin_email_id",  unique = true,columnDefinition = "TEXT")
	private String adminEmail;

	@Column(name = "admin_password", columnDefinition = "TEXT")
	private String adminPassword;

	@Column(name = "admin_otp", nullable = true,columnDefinition = "TEXT")
	private String adminOTP;

	@Column(name = "is_admin", columnDefinition = "TEXT")
	private String isAdmin;

	@Column(name = "is_userManagement", columnDefinition = "TEXT")
	private String isUserManagement;

	@Column(name = "is_questionaries", columnDefinition = "TEXT")
	private String isQuestionaries;

	@Column(name = "is_legal_setting", columnDefinition = "TEXT")
	private String isLegalSetting;

	@Column(name = "role",columnDefinition = "TEXT")
	private String role;

	@Column(name = "otp_entry_date",columnDefinition = "TEXT")
	private String otpEntryDate;

	@Column(name = "term_cond", columnDefinition = "TEXT")
	private String termCond;

	@Column(name = "admin_on_session", columnDefinition = "TEXT")
	private String adminOnSession;

	@Column(name = "is_verified",columnDefinition = "TEXT")
	private String isVerified;

	@Column(name = "login_attempt_fail_count",columnDefinition = "TEXT")
	private String loginAttemptFailCount;

	@Column(name = "login_attempt_max_release_time",columnDefinition = "TEXT")
	private String loginAttemptMaxReleaseTime;

	@Column(name = "locked_Date_Time_for_login",columnDefinition = "TEXT")
	private String lockedDateTimeForLogin;

	@Column(name = "max_attempt_fail_block_login",columnDefinition = "TEXT")
	private String maxAttemptFailBlockForLogin;

	@Column(name = "regist_date",columnDefinition = "TEXT")
	private String registDate;

	@Column(name = "regist_pname",columnDefinition = "TEXT")
	private String registPName;

	@Column(name = "last_update_date",columnDefinition = "TEXT")
	private String lastUpdateDate;

	@Column(name = "last_update_pname",columnDefinition = "TEXT")
	private String lastUpdatePName;

	public AdminUser() {
		super();
	}

	public AdminUser(Long adminId, String adminIdRef, String adminEmail, String adminPassword, String adminOTP,
			String isAdmin, String isUserManagement, String isQuestionaries, String isLegalSetting, String role,
			String otpEntryDate, String termCond, String adminOnSession, String isVerified,
			String loginAttemptFailCount, String loginAttemptMaxReleaseTime, String lockedDateTimeForLogin,
			String maxAttemptFailBlockForLogin, String registDate, String registPName, String lastUpdateDate,
			String lastUpdatePName) {
		super();
		this.adminId = adminId;
		this.adminIdRef = adminIdRef;
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
	
	public String getAdminIdRef() {
		return adminIdRef;
	}

	public void setAdminIdRef(String adminIdRef) {
		this.adminIdRef = adminIdRef;
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
		return "AdminUser [adminId=" + adminId + ", adminIdRef=" + adminIdRef + ", adminEmail=" + adminEmail
				+ ", adminPassword=" + adminPassword + ", adminOTP=" + adminOTP + ", isAdmin=" + isAdmin
				+ ", isUserManagement=" + isUserManagement + ", isQuestionaries=" + isQuestionaries
				+ ", isLegalSetting=" + isLegalSetting + ", role=" + role + ", otpEntryDate=" + otpEntryDate
				+ ", termCond=" + termCond + ", adminOnSession=" + adminOnSession + ", isVerified=" + isVerified
				+ ", loginAttemptFailCount=" + loginAttemptFailCount + ", loginAttemptMaxReleaseTime="
				+ loginAttemptMaxReleaseTime + ", lockedDateTimeForLogin=" + lockedDateTimeForLogin
				+ ", maxAttemptFailBlockForLogin=" + maxAttemptFailBlockForLogin + ", registDate=" + registDate
				+ ", registPName=" + registPName + ", lastUpdateDate=" + lastUpdateDate + ", lastUpdatePName="
				+ lastUpdatePName + "]";
	}

}
