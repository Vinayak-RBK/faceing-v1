package com.iss.dao;

public class PushNotifiResponseDao {

	private String success;
	private String message;

	public PushNotifiResponseDao() {
		super();
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "PushNotifiResponseDao [success=" + success + ", message=" + message + "]";
	}

	public PushNotifiResponseDao(String success, String message) {
		super();
		this.success = success;
		this.message = message;
	}

}
