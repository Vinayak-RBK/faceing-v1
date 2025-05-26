package com.iss.dao;

import java.util.List;

public class PagerResponseDao {

	private String noOfPages;
	private String totalCount;
	private List<UserSearchResponseDao> perDetailsList;
	private List<GuestDao> guestListDao;
	private String curPageNo;
	private String success;
	private String msg;

	public PagerResponseDao() {
		super();
	}

	public PagerResponseDao(String noOfPages, String totalCount, List<UserSearchResponseDao> perDetailsList,
			List<GuestDao> guestListDao, String curPageNo, String success, String msg) {
		super();
		this.noOfPages = noOfPages;
		this.totalCount = totalCount;
		this.perDetailsList = perDetailsList;
		this.guestListDao = guestListDao;
		this.curPageNo = curPageNo;
		this.success = success;
		this.msg = msg;
	}

	public String getNoOfPages() {
		return noOfPages;
	}

	public void setNoOfPages(String noOfPages) {
		this.noOfPages = noOfPages;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public List<UserSearchResponseDao> getPerDetailsList() {
		return perDetailsList;
	}

	public void setPerDetailsList(List<UserSearchResponseDao> perDetailsList) {
		this.perDetailsList = perDetailsList;
	}

	public List<GuestDao> getGuestListDao() {
		return guestListDao;
	}

	public void setGuestListDao(List<GuestDao> guestListDao) {
		this.guestListDao = guestListDao;
	}

	public String getCurPageNo() {
		return curPageNo;
	}

	public void setCurPageNo(String curPageNo) {
		this.curPageNo = curPageNo;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "PagerResponseDao [noOfPages=" + noOfPages + ", totalCount=" + totalCount + ", perDetailsList="
				+ perDetailsList + ", guestListDao=" + guestListDao + ", curPageNo=" + curPageNo + ", success="
				+ success + ", msg=" + msg + "]";
	}

}
