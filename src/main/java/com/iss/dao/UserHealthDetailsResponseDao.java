package com.iss.dao;

import java.util.List;

public class UserHealthDetailsResponseDao {

	private String msg;
	private String success;
	private List<UserHealthAnuraDetailsDao> useHealthAnuraList;
	private List<UserHealthBinahDetailsDao> useHealthBinahList;
	private List<UserScannedHealthDataDao> userScannedList;
	private List<String> values;

	public UserHealthDetailsResponseDao() {
		super();
	}

	public UserHealthDetailsResponseDao(String msg, String success, List<UserHealthAnuraDetailsDao> useHealthAnuraList,
			List<UserHealthBinahDetailsDao> useHealthBinahList, List<UserScannedHealthDataDao> userScannedList,
			List<String> values) {
		super();
		this.msg = msg;
		this.success = success;
		this.useHealthAnuraList = useHealthAnuraList;
		this.useHealthBinahList = useHealthBinahList;
		this.userScannedList = userScannedList;
		this.values = values;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public List<UserHealthAnuraDetailsDao> getUseHealthAnuraList() {
		return useHealthAnuraList;
	}

	public void setUseHealthAnuraList(List<UserHealthAnuraDetailsDao> useHealthAnuraList) {
		this.useHealthAnuraList = useHealthAnuraList;
	}

	public List<UserHealthBinahDetailsDao> getUseHealthBinahList() {
		return useHealthBinahList;
	}

	public void setUseHealthBinahList(List<UserHealthBinahDetailsDao> useHealthBinahList) {
		this.useHealthBinahList = useHealthBinahList;
	}

	public List<UserScannedHealthDataDao> getUserScannedList() {
		return userScannedList;
	}

	public void setUserScannedList(List<UserScannedHealthDataDao> userScannedList) {
		this.userScannedList = userScannedList;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "UserHealthDetailsResponseDao [msg=" + msg + ", success=" + success + ", useHealthAnuraList="
				+ useHealthAnuraList + ", useHealthBinahList=" + useHealthBinahList + ", userScannedList="
				+ userScannedList + ", values=" + values + "]";
	}

}
