package com.iss.dao;

import org.springframework.stereotype.Component;

@Component
public class LoginUserDao {
//
	private String emailId;
	private String password;
	private String message;
	private boolean isEmailExists;

	public LoginUserDao() {
		super();
	}
	
	public LoginUserDao(String emailId, String password, String message, boolean isEmailExists) {
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

	public boolean isEmailExists() {
		return isEmailExists;
	}

	public void setEmailExists(boolean isEmailExists) {
		this.isEmailExists = isEmailExists;
	}

	@Override
	public String toString() {
		return "LoginUserDao [emailId=" + emailId + ", password=" + password + ", message=" + message
				+ ", isEmailExists=" + isEmailExists + "]";
	}
	
	

}
