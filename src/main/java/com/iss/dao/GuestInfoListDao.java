package com.iss.dao;

import java.util.List;

public class GuestInfoListDao {

	private String msg;
	private String success;
	private List<GuestDao> guestListDao;
	private GuestHealthAnuraDetailsDao guestHealthAnuraDetail;
	private GuestHealthBinahDetailsDao guestHealthBinahDetail;
	private List<GuestHealthInfoDao> healthInfoDaoList;

	public GuestInfoListDao() {
		super();
	}

	public GuestInfoListDao(String msg, String success, List<GuestDao> guestListDao,
			GuestHealthAnuraDetailsDao guestHealthAnuraDetail, GuestHealthBinahDetailsDao guestHealthBinahDetail,
			List<GuestHealthInfoDao> healthInfoDaoList) {
		super();
		this.msg = msg;
		this.success = success;
		this.guestListDao = guestListDao;
		this.guestHealthAnuraDetail = guestHealthAnuraDetail;
		this.guestHealthBinahDetail = guestHealthBinahDetail;
		this.healthInfoDaoList = healthInfoDaoList;
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

	public List<GuestDao> getGuestListDao() {
		return guestListDao;
	}

	public void setGuestListDao(List<GuestDao> guestListDao) {
		this.guestListDao = guestListDao;
	}

	public GuestHealthAnuraDetailsDao getGuestHealthAnuraDetail() {
		return guestHealthAnuraDetail;
	}

	public void setGuestHealthAnuraDetail(GuestHealthAnuraDetailsDao guestHealthAnuraDetail) {
		this.guestHealthAnuraDetail = guestHealthAnuraDetail;
	}

	public GuestHealthBinahDetailsDao getGuestHealthBinahDetail() {
		return guestHealthBinahDetail;
	}

	public void setGuestHealthBinahDetail(GuestHealthBinahDetailsDao guestHealthBinahDetail) {
		this.guestHealthBinahDetail = guestHealthBinahDetail;
	}

	public List<GuestHealthInfoDao> getHealthInfoDaoList() {
		return healthInfoDaoList;
	}

	public void setHealthInfoDaoList(List<GuestHealthInfoDao> healthInfoDaoList) {
		this.healthInfoDaoList = healthInfoDaoList;
	}

	@Override
	public String toString() {
		return "GuestInfoListDao [msg=" + msg + ", success=" + success + ", guestListDao=" + guestListDao
				+ ", guestHealthAnuraDetail=" + guestHealthAnuraDetail + ", guestHealthBinahDetail="
				+ guestHealthBinahDetail + ", healthInfoDaoList=" + healthInfoDaoList + "]";
	}

}
