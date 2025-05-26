package com.iss.dao;

import java.util.List;

public class OnBoradingResDao {

	private String message;
	private String isSuccess;
	private List<BasicHealthQuestionDao> list;

	public OnBoradingResDao() {
		super();
	}

	public OnBoradingResDao(String message, String isSuccess, List<BasicHealthQuestionDao> list) {
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

	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
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
