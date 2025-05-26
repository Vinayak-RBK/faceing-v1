package com.iss.dao;

import java.util.List;

public class ResponseDao {

	private String message;
	private String isSuccess;
	private String isOTPVerified;
	private String isOnBoarded;
	private String isBlocked;
	private String otp;
	private String userId;
	private String emailId;
	private String pushNotify;
	private List<SDKinfoDao> sdkInfo;
	private CommonUserDetailsDao commonUserDetailsDao;

	public ResponseDao() {
		super();
	}

	public ResponseDao(String message, String isSuccess, String isOTPVerified, String isOnBoarded, String isBlocked,
			String otp, String userId, String emailId, String pushNotify, List<SDKinfoDao> sdkInfo,
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
		this.pushNotify = pushNotify;
		this.sdkInfo = sdkInfo;
		this.commonUserDetailsDao = commonUserDetailsDao;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getIsOTPVerified() {
		return isOTPVerified;
	}

	public void setIsOTPVerified(String isOTPVerified) {
		this.isOTPVerified = isOTPVerified;
	}

	public String getIsOnBoarded() {
		return isOnBoarded;
	}

	public void setIsOnBoarded(String isOnBoarded) {
		this.isOnBoarded = isOnBoarded;
	}

	public String getIsBlocked() {
		return isBlocked;
	}

	public void setIsBlocked(String isBlocked) {
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

	public String getPushNotify() {
		return pushNotify;
	}

	public void setPushNotify(String pushNotify) {
		this.pushNotify = pushNotify;
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
				+ ", emailId=" + emailId + ", pushNotify=" + pushNotify + ", sdkInfo=" + sdkInfo
				+ ", commonUserDetailsDao=" + commonUserDetailsDao + "]";
	}

	}
