package com.iss.dao;

import java.util.List;

public class GuestInfoListDao {
	
	private String msg;
	private boolean success;
	private List<GuestDao> guestListDao;
	
	public GuestInfoListDao() {
		super();
	}

	public GuestInfoListDao(String msg, boolean success, List<GuestDao> guestListDao) {
		super();
		this.msg = msg;
		this.success = success;
		this.guestListDao = guestListDao;
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

	public List<GuestDao> getGuestListDao() {
		return guestListDao;
	}

	public void setGuestListDao(List<GuestDao> guestListDao) {
		this.guestListDao = guestListDao;
	}

	@Override
	public String toString() {
		return "GuestInfoListDao [msg=" + msg + ", success=" + success + ", guestListDao=" + guestListDao + "]";
	}
	
}
