package com.iss.dao;

import org.springframework.stereotype.Component;

@Component
public class LoginUserDao {
//
	private String emailId;
	private String password;
	private String message;
	private String isEmailExists;

	public LoginUserDao() {
		super();
	}

	public LoginUserDao(String emailId, String password, String message, String isEmailExists) {
		super();
		this.emailId = emailId;
		this.password = password;
		this.message = message;
		this.isEmailExists = isEmailExists;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIsEmailExists() {
		return isEmailExists;
	}

	public void setIsEmailExists(String isEmailExists) {
		this.isEmailExists = isEmailExists;
	}

	@Override
	public String toString() {
		return "LoginUserDao [emailId=" + emailId + ", password=" + password + ", message=" + message
				+ ", isEmailExists=" + isEmailExists + "]";
	}

}
