package com.iss.dao;

import com.iss.header.HttpHeaders;

public class AdminResponseDao {

	private String message;
	private boolean isSuccess;
	private String otp;
	private String adminId;
	private String email;
	private String id;
	private boolean isAdmin;
	private boolean isUserManagement;
	private boolean isQuestionaries;
	private boolean isLegalSetting;
	private HttpHeaders headers;
	
	public AdminResponseDao() {
		super();
	}

	public AdminResponseDao(String message, boolean isSuccess, String otp, String adminId, String email, String id,
			boolean isAdmin, boolean isUserManagement, boolean isQuestionaries, boolean isLegalSetting,
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

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
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

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isUserManagement() {
		return isUserManagement;
	}

	public void setUserManagement(boolean isUserManagement) {
		this.isUserManagement = isUserManagement;
	}

	public boolean isQuestionaries() {
		return isQuestionaries;
	}

	public void setQuestionaries(boolean isQuestionaries) {
		this.isQuestionaries = isQuestionaries;
	}

	public boolean isLegalSetting() {
		return isLegalSetting;
	}

	public void setLegalSetting(boolean isLegalSetting) {
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
