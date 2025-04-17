package com.iss.dao;

import java.util.List;

public class UserHealthDetailsResponseDao {

	private String msg;
	private boolean success;
	private List<UserHealthDetailsDao> useHealthList;

	public UserHealthDetailsResponseDao() {
		super();
	}

	public UserHealthDetailsResponseDao(String msg, boolean success, List<UserHealthDetailsDao> useHealthList) {
		super();
		this.msg = msg;
		this.success = success;
		this.useHealthList = useHealthList;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<UserHealthDetailsDao> getUseHealthList() {
		return useHealthList;
	}

	public void setUseHealthList(List<UserHealthDetailsDao> useHealthList) {
		this.useHealthList = useHealthList;
	}

	@Override
	public String toString() {
		return "UserHealthDetailsResponseDao [msg=" + msg + ", success=" + success + ", useHealthList=" + useHealthList
				+ "]";
	}
}
