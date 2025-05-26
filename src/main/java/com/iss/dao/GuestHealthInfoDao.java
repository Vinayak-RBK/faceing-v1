package com.iss.dao;

public class GuestHealthInfoDao {

	private GuestDao guestDao;
	private GuestHealthAnuraDetailsDao anuraDetails;
	private GuestHealthBinahDetailsDao binahDetails;

	public GuestHealthInfoDao() {
		super();
	}

	public GuestHealthInfoDao(GuestDao guestDao, GuestHealthAnuraDetailsDao anuraDetails,
			GuestHealthBinahDetailsDao binahDetails) {
		super();
		this.guestDao = guestDao;
		this.anuraDetails = anuraDetails;
		this.binahDetails = binahDetails;
	}

	public GuestDao getGuestDao() {
		return guestDao;
	}

	public void setGuestDao(GuestDao guestDao) {
		this.guestDao = guestDao;
	}

	public GuestHealthAnuraDetailsDao getAnuraDetails() {
		return anuraDetails;
	}

	public void setAnuraDetails(GuestHealthAnuraDetailsDao anuraDetails) {
		this.anuraDetails = anuraDetails;
	}

	public GuestHealthBinahDetailsDao getBinahDetails() {
		return binahDetails;
	}

	public void setBinahDetails(GuestHealthBinahDetailsDao binahDetails) {
		this.binahDetails = binahDetails;
	}

	@Override
	public String toString() {
		return "GuestInfoDao [guestDao=" + guestDao + ", anuraDetails=" + anuraDetails + ", binahDetails="
				+ binahDetails + "]";
	}
}
