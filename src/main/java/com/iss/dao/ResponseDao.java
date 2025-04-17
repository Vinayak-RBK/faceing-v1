package com.iss.dao;

import java.util.List;

public class ResponseDao {

	private String message;
	private boolean isSuccess;
	private boolean isOTPVerified;
	private boolean isOnBoarded;
	private boolean isBlocked;
	private String otp;
	private String userId;
	private String emailId;
	private List<SDKinfoDao> sdkInfo;
	private CommonUserDetailsDao commonUserDetailsDao;

	public ResponseDao() {
		super();
	}

	public ResponseDao(String message, boolean isSuccess, boolean isOTPVerified, boolean isOnBoarded, boolean isBlocked,
			String otp, String userId, String emailId, List<SDKinfoDao> sdkInfo,
			CommonUserDetailsDao commonUserDetailsDao) {
		super();
		this.message = message;
		this.isSuccess = isSuccess;
		this.isOTPVerified = isOTPVerified;
		this.isOnBoarded = isOnBoarded;
		this.isBlocked = isBlocked;
		this.otp = otp;
		this.userId = userId;
		this.emailId = emailId;
		this.sdkInfo = sdkInfo;
		this.commonUserDetailsDao = commonUserDetailsDao;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public boolean isOTPVerified() {
		return isOTPVerified;
	}

	public void setOTPVerified(boolean isOTPVerified) {
		this.isOTPVerified = isOTPVerified;
	}

	public boolean isOnBoarded() {
		return isOnBoarded;
	}

	public void setOnBoarded(boolean isOnBoarded) {
		this.isOnBoarded = isOnBoarded;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
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

	public List<SDKinfoDao> getSdkInfo() {
		return sdkInfo;
	}

	public void setSdkInfo(List<SDKinfoDao> sdkInfo) {
		this.sdkInfo = sdkInfo;
	}

	public CommonUserDetailsDao getCommonUserDetailsDao() {
		return commonUserDetailsDao;
	}

	public void setCommonUserDetailsDao(CommonUserDetailsDao commonUserDetailsDao) {
		this.commonUserDetailsDao = commonUserDetailsDao;
	}

	@Override
	public String toString() {
		return "ResponseDao [message=" + message + ", isSuccess=" + isSuccess + ", isOTPVerified=" + isOTPVerified
				+ ", isOnBoarded=" + isOnBoarded + ", isBlocked=" + isBlocked + ", otp=" + otp + ", userId=" + userId
				+ ", emailId=" + emailId + ", sdkInfo=" + sdkInfo + ", commonUserDetailsDao=" + commonUserDetailsDao
				+ "]";
	}
	
	}
