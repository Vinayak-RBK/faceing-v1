package com.iss.dao;

import java.util.List;

public class OnBoradingResDao {
	
	private String message;
	private boolean isSuccess;
	private List<BasicHealthQuestionDao> list;
	
	public OnBoradingResDao() {
		super();
	}

	public OnBoradingResDao(String message, boolean isSuccess, List<BasicHealthQuestionDao> list) {
		super();
		this.message = message;
		this.isSuccess = isSuccess;
		this.list = list;
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

	public List<BasicHealthQuestionDao> getList() {
		return list;
	}

	public void setList(List<BasicHealthQuestionDao> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "OnBoradingResDao [message=" + message + ", isSuccess=" + isSuccess + ", list=" + list + "]";
	}

}
