package com.iss.dao;

import com.iss.header.HttpHeaders;

public class AdminResponseDao {

	private String message;
	private String isSuccess;
	private String otp;
	private String adminId;
	private String email;
	private String id;
	private String isAdmin;
	private String isUserManagement;
	private String isQuestionaries;
	private String isLegalSetting;
	private HttpHeaders headers;
	
	public AdminResponseDao() {
		super();
	}

	public AdminResponseDao(String message, String isSuccess, String otp, String adminId, String email, String id,
			String isAdmin, String isUserManagement, String isQuestionaries, String isLegalSetting,
			HttpHeaders headers) {
		super();
		this.message = message;
		this.isSuccess = isSuccess;
		this.otp = otp;
		this.adminId = adminId;
		this.email = email;
		this.id = id;
		this.isAdmin = isAdmin;
		this.isUserManagement = isUserManagement;
		this.isQuestionaries = isQuestionaries;
		this.isLegalSetting = isLegalSetting;
		this.headers = headers;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String isSuccess() {
		return isSuccess;
	}

	public void setSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String isAdmin() {
		return isAdmin;
	}

	public void setAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String isUserManagement() {
		return isUserManagement;
	}

	public void setUserManagement(String isUserManagement) {
		this.isUserManagement = isUserManagement;
	}

	public String isQuestionaries() {
		return isQuestionaries;
	}

	public void setQuestionaries(String isQuestionaries) {
		this.isQuestionaries = isQuestionaries;
	}

	public String isLegalSetting() {
		return isLegalSetting;
	}

	public void setLegalSetting(String isLegalSetting) {
		this.isLegalSetting = isLegalSetting;
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	}

	@Override
	public String toString() {
		return "AdminResponseDao [message=" + message + ", isSuccess=" + isSuccess + ", otp=" + otp + ", adminId="
				+ adminId + ", email=" + email + ", id=" + id + ", isAdmin=" + isAdmin + ", isUserManagement="
				+ isUserManagement + ", isQuestionaries=" + isQuestionaries + ", isLegalSetting=" + isLegalSetting
				+ ", headers=" + headers + "]";
	}

}
