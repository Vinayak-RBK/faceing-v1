package com.iss.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = " end_user")
public class EndUser {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeqGen")
	@SequenceGenerator(name = "userSeqGen", sequenceName = "user_sequence", initialValue = 1000000000, allocationSize = 1)
	@Column(name = "user_id", length = 10, unique = true)
	private Long userId;

	@Column(name = "admin_id", columnDefinition = "TEXT")
	private String adminId;

	@Column(name = "user_id_ref", columnDefinition = "TEXT")
	private String userIdRef;

	@Column(name = "user_email_id", columnDefinition = "TEXT", unique = true)
	private String userEmail;

	@Column(name = "user_password", columnDefinition = "TEXT")
	private String userPassword;

	@Column(name = "user_name", columnDefinition = "TEXT")
	private String name;

	@Column(name = "mobile_number", columnDefinition = "TEXT")
	private String mobileNumber;

	@Column(name = "job_role", columnDefinition = "TEXT")
	private String jobRole;

	@Column(name = "user_otp", columnDefinition = "TEXT")
	private String userOTP;

	@Column(name = "is_admin", columnDefinition = "TEXT")
	private String isAdmin;

	@Column(name = "sdk_type", columnDefinition = "TEXT")
	private String sDKType;

	@Column(name = "otp_entry_date", columnDefinition = "TEXT")
	private String otpEntryDate;

	@Column(name = "term_cond", columnDefinition = "TEXT")
	private String termCond;

	@Column(name = "user_on_session", columnDefinition = "TEXT")
	private String userOnSession;

	@Column(name = "is_verified", columnDefinition = "TEXT")
	private String isVerified;

	@Column(name = "is_onboarded", columnDefinition = "TEXT")
	private String isOnBoarded;

	@Column(name = "is_Login", columnDefinition = "TEXT")
	private String isLogin;

	@Column(name = "push_notification", columnDefinition = "TEXT")
	private String pushNotify;

	@Column(name = "login_attempt_fail_count", columnDefinition = "TEXT")
	private String loginAttemptFailCount;

	@Column(name = "login_attempt_max_release_time", columnDefinition = "TEXT")
	private String loginAttemptMaxReleaseTime;

	@Column(name = "locked_Date_Time_for_login", columnDefinition = "TEXT")
	private String lockedDateTimeForLogin;

	@Column(name = "max_attempt_fail_block_login", columnDefinition = "TEXT")
	private String maxAttemptFailBlockForLogin;

	@Column(name = "regist_date", columnDefinition = "TEXT")
	private String registDate;

	@Column(name = "regist_pname", columnDefinition = "TEXT")
	private String registPName;

	@Column(name = "last_update_date", columnDefinition = "TEXT")
	private String lastUpdateDate;

	@Column(name = "last_update_pname", columnDefinition = "TEXT")
	private String lastUpdatePName;

	@OneToOne(mappedBy = "endUser", cascade = CascadeType.ALL)
	private UsersPersonalDetails userperDetails;

	@Column(name = "is_Blocked", columnDefinition = "TEXT")
	private String isBlocked;

	@OneToMany(mappedBy = "endUser", cascade = CascadeType.ALL)
	private List<Guest> guestList;

	@OneToMany(mappedBy = "endUser", cascade = CascadeType.ALL)
	private List<UserHealthOnboardingDetail> userHealthOnboardingDetailsList;

	@OneToMany(mappedBy = "endUser", cascade = CascadeType.ALL)
	private List<UserImages> userImageList;

	public EndUser() {
		super();
	}

	public EndUser(Long userId, String adminId, String userIdRef, String userEmail, String userPassword, String name,
			String mobileNumber, String jobRole, String userOTP, String isAdmin, String sDKType, String otpEntryDate,
			String termCond, String userOnSession, String isVerified, String isOnBoarded, String isLogin,
			String pushNotify, String loginAttemptFailCount, String loginAttemptMaxReleaseTime,
			String lockedDateTimeForLogin, String maxAttemptFailBlockForLogin, String registDate, String registPName,
			String lastUpdateDate, String lastUpdatePName, UsersPersonalDetails userperDetails, String isBlocked,
			List<Guest> guestList, List<UserHealthOnboardingDetail> userHealthOnboardingDetailsList,
			List<UserImages> userImageList) {
		super();
		this.userId = userId;
		this.adminId = adminId;
		this.userIdRef = userIdRef;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.name = name;
		this.mobileNumber = mobileNumber;
		this.jobRole = jobRole;
		this.userOTP = userOTP;
		this.isAdmin = isAdmin;
		this.sDKType = sDKType;
		this.otpEntryDate = otpEntryDate;
		this.termCond = termCond;
		this.userOnSession = userOnSession;
		this.isVerified = isVerified;
		this.isOnBoarded = isOnBoarded;
		this.isLogin = isLogin;
		this.pushNotify = pushNotify;
		this.loginAttemptFailCount = loginAttemptFailCount;
		this.loginAttemptMaxReleaseTime = loginAttemptMaxReleaseTime;
		this.lockedDateTimeForLogin = lockedDateTimeForLogin;
		this.maxAttemptFailBlockForLogin = maxAttemptFailBlockForLogin;
		this.registDate = registDate;
		this.registPName = registPName;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdatePName = lastUpdatePName;
		this.userperDetails = userperDetails;
		this.isBlocked = isBlocked;
		this.guestList = guestList;
		this.userHealthOnboardingDetailsList = userHealthOnboardingDetailsList;
		this.userImageList = userImageList;
	}

	public Long getUserId() {
		return userId;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}

	public String getUserIdRef() {
		return userIdRef;
	}

	public void setUserIdRef(String userIdRef) {
		this.userIdRef = userIdRef;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserOTP() {
		return userOTP;
	}

	public void setUserOTP(String userOTP) {
		this.userOTP = userOTP;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getsDKType() {
		return sDKType;
	}

	public void setsDKType(String sDKType) {
		this.sDKType = sDKType;
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

	public String getUserOnSession() {
		return userOnSession;
	}

	public void setUserOnSession(String userOnSession) {
		this.userOnSession = userOnSession;
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

	public UsersPersonalDetails getUserperDetails() {
		return userperDetails;
	}

	public void setUserperDetails(UsersPersonalDetails userperDetails) {
		this.userperDetails = userperDetails;
	}

	public String getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(String isBlocked) {
		this.isBlocked = isBlocked;
	}

	public List<Guest> getGuestList() {
		return guestList;
	}

	public void setGuestList(List<Guest> guestList) {
		this.guestList = guestList;
	}

	public List<UserHealthOnboardingDetail> getUserHealthOnboardingDetailsList() {
		return userHealthOnboardingDetailsList;
	}

	public void setUserHealthOnboardingDetailsList(List<UserHealthOnboardingDetail> userHealthOnboardingDetailsList) {
		this.userHealthOnboardingDetailsList = userHealthOnboardingDetailsList;
	}

	public List<UserImages> getUserImageList() {
		return userImageList;
	}

	public void setUserImageList(List<UserImages> userImageList) {
		this.userImageList = userImageList;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "EndUser [userId=" + userId + ", adminId=" + adminId + ", userIdRef=" + userIdRef + ", userEmail="
				+ userEmail + ", userPassword=" + userPassword + ", name=" + name + ", mobileNumber=" + mobileNumber
				+ ", jobRole=" + jobRole + ", userOTP=" + userOTP + ", isAdmin=" + isAdmin + ", sDKType=" + sDKType
				+ ", otpEntryDate=" + otpEntryDate + ", termCond=" + termCond + ", userOnSession=" + userOnSession
				+ ", isVerified=" + isVerified + ", isOnBoarded=" + isOnBoarded + ", isLogin=" + isLogin
				+ ", pushNotify=" + pushNotify + ", loginAttemptFailCount=" + loginAttemptFailCount
				+ ", loginAttemptMaxReleaseTime=" + loginAttemptMaxReleaseTime + ", lockedDateTimeForLogin="
				+ lockedDateTimeForLogin + ", maxAttemptFailBlockForLogin=" + maxAttemptFailBlockForLogin
				+ ", registDate=" + registDate + ", registPName=" + registPName + ", lastUpdateDate=" + lastUpdateDate
				+ ", lastUpdatePName=" + lastUpdatePName + ", userperDetails=" + userperDetails + ", isBlocked="
				+ isBlocked + ", guestList=" + guestList + ", userHealthOnboardingDetailsList="
				+ userHealthOnboardingDetailsList + ", userImageList=" + userImageList + "]";
	}

}
