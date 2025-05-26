package com.iss.dao;

public class HealthRequestDao {

	private String value;
	private String scannedDate;

	public HealthRequestDao() {
		super();
	}

	public HealthRequestDao(String value, String scannedDate) {
		super();
		this.value = value;
		this.scannedDate = scannedDate;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getScannedDate() {
		return scannedDate;
	}

	public void setScannedDate(String scannedDate) {
		this.scannedDate = scannedDate;
	}

	@Override
	public String toString() {
		return "HealthRequestDao [value=" + value + ", scannedDate=" + scannedDate + "]";
	}

}
