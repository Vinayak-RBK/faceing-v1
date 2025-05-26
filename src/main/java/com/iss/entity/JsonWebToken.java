package com.iss.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "json_web_token")
public class JsonWebToken {
	
	@Id
	@Column(name = "user_id", length = 255)
	private String userId;
	
	@Column(name = "access_token", columnDefinition = "TEXT")
	private String accessToken;
	
	@Column(name = "refresh_token", columnDefinition = "TEXT")
	private String refreshToken;
	
	@Column(name = "expiry_days_access_token", columnDefinition = "TEXT")
	private String expiryDaysAccessToken;
	
	@Column(name = "expiry_days_refresh_token", columnDefinition = "TEXT")
	private String expiryDaysRefreshToken;
	
	@Column(name = "regist_date", columnDefinition = "TEXT")
	private String registDate;

	@Column(name = "regist_pname", columnDefinition = "TEXT")
	private String registPName;

	@Column(name = "last_update_date", columnDefinition = "TEXT")
	private String lastUpdateDate;

	@Column(name = "last_update_pname", columnDefinition = "TEXT")
	private String lastUpdatePName;
	
	public JsonWebToken() {
		super();
	}

	public JsonWebToken(String userId, String accessToken, String refreshToken, String expiryDaysAccessToken,
			String expiryDaysRefreshToken, String registDate, String registPName, String lastUpdateDate,
			String lastUpdatePName) {
		super();
		this.userId = userId;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiryDaysAccessToken = expiryDaysAccessToken;
		this.expiryDaysRefreshToken = expiryDaysRefreshToken;
		this.registDate = registDate;
		this.registPName = registPName;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdatePName = lastUpdatePName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getExpiryDaysAccessToken() {
		return expiryDaysAccessToken;
	}

	public void setExpiryDaysAccessToken(String expiryDaysAccessToken) {
		this.expiryDaysAccessToken = expiryDaysAccessToken;
	}

	public String getExpiryDaysRefreshToken() {
		return expiryDaysRefreshToken;
	}

	public void setExpiryDaysRefreshToken(String expiryDaysRefreshToken) {
		this.expiryDaysRefreshToken = expiryDaysRefreshToken;
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
		return "JsonWebToken [userId=" + userId + ", accessToken=" + accessToken + ", refreshToken=" + refreshToken
				+ ", expiryDaysAccessToken=" + expiryDaysAccessToken + ", expiryDaysRefreshToken="
				+ expiryDaysRefreshToken + ", registDate=" + registDate + ", registPName=" + registPName
				+ ", lastUpdateDate=" + lastUpdateDate + ", lastUpdatePName=" + lastUpdatePName + "]";
	}

}
