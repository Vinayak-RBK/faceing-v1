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

	@Column(name = "user_email_id", length = 50, nullable = false, unique = true)
	private String userEmail;

	@Column(name = "user_password", length = 20, nullable = false)
	private String userPassword;

	@Column(name = "user_otp", length = 5, nullable = true)
	private String userOTP;

	@Column(name = "is_admin", columnDefinition = "TINYINT(1)")
	private Boolean isAdmin;

	@Column(name = "otp_entry_date", length = 20)
	private String otpEntryDate;

	@Column(name = "term_cond", columnDefinition = "TINYINT(1)")
	private Boolean termCond;

	@Column(name = "user_on_session", columnDefinition = "TINYINT(1)")
	private Boolean userOnSession;

	@Column(name = "is_verified", columnDefinition = "TINYINT(1)")
	private Boolean isVerified;

	@Column(name = "is_onboarded", columnDefinition = "TINYINT(1)")
	private Boolean isOnBoarded;

	@Column(name = "is_Login", columnDefinition = "TINYINT(1)")
	private Boolean isLogin;

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

	@OneToOne(mappedBy = "endUser", cascade = CascadeType.ALL)
	private UsersPersonalDetails userperDetails;

	@Column(name = "is_Blocked", columnDefinition = "TINYINT(1)")
	private Boolean isBlocked;

	@OneToMany(mappedBy = "endUser", cascade = CascadeType.ALL)
	private List<Guest> guestList;

	@OneToMany(mappedBy = "endUser", cascade = CascadeType.ALL)
	private List<UserHealthOnboardingDetail> userHealthOnboardingDetailsList;

	public EndUser() {
		super();
	}

	public EndUser(Long userId, String userEmail, String userPassword, String userOTP, Boolean isAdmin,
			String otpEntryDate, Boolean termCond, Boolean userOnSession, Boolean isVerified, Boolean isOnBoarded,
			Boolean isLogin, int loginAttemptFailCount, int loginAttemptMaxReleaseTime, String lockedDateTimeForLogin,
			Boolean maxAttemptFailBlockForLogin, String registDate, String registPName, String lastUpdateDate,
			String lastUpdatePName, UsersPersonalDetails userperDetails, Boolean isBlocked, List<Guest> guestList,
			List<UserHealthOnboardingDetail> userHealthOnboardingDetailsList) {
		super();
		this.userId = userId;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userOTP = userOTP;
		this.isAdmin = isAdmin;
		this.otpEntryDate = otpEntryDate;
		this.termCond = termCond;
		this.userOnSession = userOnSession;
		this.isVerified = isVerified;
		this.isOnBoarded = isOnBoarded;
		this.isLogin = isLogin;
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
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
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

	public Boolean getUserOnSession() {
		return userOnSession;
	}

	public void setUserOnSession(Boolean userOnSession) {
		this.userOnSession = userOnSession;
	}

	public Boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Boolean isVerified) {
		this.isVerified = isVerified;
	}

	public Boolean getIsOnBoarded() {
		return isOnBoarded;
	}

	public void setIsOnBoarded(Boolean isOnBoarded) {
		this.isOnBoarded = isOnBoarded;
	}

	public Boolean getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(Boolean isLogin) {
		this.isLogin = isLogin;
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

	public UsersPersonalDetails getUserperDetails() {
		return userperDetails;
	}

	public void setUserperDetails(UsersPersonalDetails userperDetails) {
		this.userperDetails = userperDetails;
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

	public Boolean getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(Boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	@Override
	public String toString() {
		return "EndUser [userId=" + userId + ", userEmail=" + userEmail + ", userPassword=" + userPassword
				+ ", userOTP=" + userOTP + ", isAdmin=" + isAdmin + ", otpEntryDate=" + otpEntryDate + ", termCond="
				+ termCond + ", userOnSession=" + userOnSession + ", isVerified=" + isVerified + ", isOnBoarded="
				+ isOnBoarded + ", isLogin=" + isLogin + ", loginAttemptFailCount=" + loginAttemptFailCount
				+ ", loginAttemptMaxReleaseTime=" + loginAttemptMaxReleaseTime + ", lockedDateTimeForLogin="
				+ lockedDateTimeForLogin + ", maxAttemptFailBlockForLogin=" + maxAttemptFailBlockForLogin
				+ ", registDate=" + registDate + ", registPName=" + registPName + ", lastUpdateDate=" + lastUpdateDate
				+ ", lastUpdatePName=" + lastUpdatePName + ", userperDetails=" + userperDetails + ", isBlocked="
				+ isBlocked + ", guestList=" + guestList + ", userHealthOnboardingDetailsList="
				+ userHealthOnboardingDetailsList + "]";
	}
}
