package com.iss.dao;

public class GuestInfoDao {

	private GuestDao guestDao;
	private GuestHealthDetailsDao guestHealthDao;

	public GuestInfoDao() {
		super();
	}

	public GuestInfoDao(GuestDao guestDao, GuestHealthDetailsDao guestHealthDao) {
		super();
		this.guestDao = guestDao;
		this.guestHealthDao = guestHealthDao;
	}

	public GuestDao getGuestDao() {
		return guestDao;
	}

	public void setGuestDao(GuestDao guestDao) {
		this.guestDao = guestDao;
	}

	public GuestHealthDetailsDao getGuestHealthDao() {
		return guestHealthDao;
	}

	public void setGuestHealthDao(GuestHealthDetailsDao guestHealthDao) {
		this.guestHealthDao = guestHealthDao;
	}

	@Override
	public String toString() {
		return "GuestInfoDao [guestDao=" + guestDao + ", guestHealthDao=" + guestHealthDao + "]";
	}

}
