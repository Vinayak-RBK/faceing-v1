package com.iss.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_system_ip")
public class UserSystemIPAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "systemIpSeqGen")
	@SequenceGenerator(name = "systemIpSeqGen", sequenceName = "system_ip_sequence", initialValue = 1000000000, allocationSize = 1)
	@Column(name = "user_system_ip_id", length = 10, unique = true)
	private Long userSystemIPId;

	@Column(name = "ip_address", columnDefinition = "TEXT")
	private String ipAdress;

	@Column(name = "is_blocked", columnDefinition = "TEXT")
	private String isBlocked;

	@Column(name = "attempt_fail_count", columnDefinition = "TEXT")
	private String attemptFailCount;

	@Column(name = "login_attempt_max_release_time", columnDefinition = "TEXT")
	private String loginAttemptMaxReleaseTime;

	@Column(name = "locked_Date_Time_for_login", columnDefinition = "TEXT")
	private String lockedDateTimeForLogin;

	@Column(name = "regist_date", columnDefinition = "TEXT")
	private String registDate;

	@Column(name = "regist_pname", columnDefinition = "TEXT")
	private String registPName;

	@Column(name = "last_update_date", columnDefinition = "TEXT")
	private String lastUpdateDate;

	@Column(name = "last_update_pname", columnDefinition = "TEXT")
	private String lastUpdatePName;

	public UserSystemIPAddress() {
		super();
	}

	public UserSystemIPAddress(Long userSystemIPId, String ipAdress, String isBlocked, String attemptFailCount,
			String loginAttemptMaxReleaseTime, String lockedDateTimeForLogin, String registDate, String registPName,
			String lastUpdateDate, String lastUpdatePName) {
		super();
		this.userSystemIPId = userSystemIPId;
		this.ipAdress = ipAdress;
		this.isBlocked = isBlocked;
		this.attemptFailCount = attemptFailCount;
		this.loginAttemptMaxReleaseTime = loginAttemptMaxReleaseTime;
		this.lockedDateTimeForLogin = lockedDateTimeForLogin;
		this.registDate = registDate;
		this.registPName = registPName;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdatePName = lastUpdatePName;
	}

	public Long getUserSystemIPId() {
		return userSystemIPId;
	}

	public void setUserSystemIPId(Long userSystemIPId) {
		this.userSystemIPId = userSystemIPId;
	}

	public String getIpAdress() {
		return ipAdress;
	}

	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}

	public String isBlocked() {
		return isBlocked;
	}

	public void setBlocked(String isBlocked) {
		this.isBlocked = isBlocked;
	}

	public String getAttemptFailCount() {
		return attemptFailCount;
	}

	public void setAttemptFailCount(String attemptFailCount) {
		this.attemptFailCount = attemptFailCount;
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
		return "UserSystemIPAddress [userSystemIPId=" + userSystemIPId + ", ipAdress=" + ipAdress + ", isBlocked="
				+ isBlocked + ", attemptFailCount=" + attemptFailCount + ", loginAttemptMaxReleaseTime="
				+ loginAttemptMaxReleaseTime + ", lockedDateTimeForLogin=" + lockedDateTimeForLogin + ", registDate="
				+ registDate + ", registPName=" + registPName + ", lastUpdateDate=" + lastUpdateDate
				+ ", lastUpdatePName=" + lastUpdatePName + "]";
	}

}
