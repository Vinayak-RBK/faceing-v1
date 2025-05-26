package com.iss.entity;

import java.math.BigInteger;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SPRING_SESSION")
public class SpringSession {
	
	@Id
	@Column(name = "PRIMARY_ID", length = 50, nullable = false)
	private String primaryId;
	
	@Column(name = "SESSION_ID", length = 50, nullable = false)
	private String sessionID;
	
	@Column(name = "CREATION_TIME", length = 50, nullable = false)
	private BigInteger creationTime;
	
	@Column(name = "LAST_ACCESS_TIME", length = 50, nullable = false)
	private BigInteger lastAccessTime;
	
	@Column(name = "MAX_INACTIVE_INTERVAL", nullable = false)
	private int maxInactiveInterval;
	
	@Column(name = "EXPIRY_TIME", nullable = false)
	private BigInteger expiryTime;
	
	@Column(name = "PRINCIPAL_NAME", nullable = true)
	private String principalName;
	
	public SpringSession() {
		super();
	}

	public SpringSession(String primaryId, String sessionID, BigInteger creationTime, BigInteger lastAccessTime,
			int maxInactiveInterval, BigInteger expiryTime, String principalName) {
		super();
		this.primaryId = primaryId;
		this.sessionID = sessionID;
		this.creationTime = creationTime;
		this.lastAccessTime = lastAccessTime;
		this.maxInactiveInterval = maxInactiveInterval;
		this.expiryTime = expiryTime;
		this.principalName = principalName;
	}

	public String getPrimaryId() {
		return primaryId;
	}

	public void setPrimaryId(String primaryId) {
		this.primaryId = primaryId;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public BigInteger getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(BigInteger creationTime) {
		this.creationTime = creationTime;
	}

	public BigInteger getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(BigInteger lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public int getMaxInactiveInterval() {
		return maxInactiveInterval;
	}

	public void setMaxInactiveInterval(int maxInactiveInterval) {
		this.maxInactiveInterval = maxInactiveInterval;
	}

	public BigInteger getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(BigInteger expiryTime) {
		this.expiryTime = expiryTime;
	}

	public String getPrincipalName() {
		return principalName;
	}

	public void setPrincipalName(String principalName) {
		this.principalName = principalName;
	}

	@Override
	public String toString() {
		return "SpringSession [primaryId=" + primaryId + ", sessionID=" + sessionID + ", creationTime=" + creationTime
				+ ", lastAccessTime=" + lastAccessTime + ", maxInactiveInterval=" + maxInactiveInterval
				+ ", expiryTime=" + expiryTime + ", principalName=" + principalName + "]";
	}
	
}
