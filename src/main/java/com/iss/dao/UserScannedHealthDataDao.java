package com.iss.dao;

public class UserScannedHealthDataDao {

	private String scanId;
	private String dateOfScan;

	public UserScannedHealthDataDao() {
		super();
	}

	public UserScannedHealthDataDao(String scanId, String dateOfScan) {
		super();
		this.scanId = scanId;
		this.dateOfScan = dateOfScan;
	}

	public String getScanId() {
		return scanId;
	}

	public void setScanId(String scanId) {
		this.scanId = scanId;
	}

	public String getDateOfScan() {
		return dateOfScan;
	}

	public void setDateOfScan(String dateOfScan) {
		this.dateOfScan = dateOfScan;
	}

	@Override
	public String toString() {
		return "UserScannedHealthData [scanId=" + scanId + ", dateOfScan=" + dateOfScan + "]";
	}
}
