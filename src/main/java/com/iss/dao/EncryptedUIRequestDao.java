package com.iss.dao;

public class EncryptedUIRequestDao {

	private String enData;

	public EncryptedUIRequestDao() {
		super();
	}

	public EncryptedUIRequestDao(String enData) {
		super();
		this.enData = enData;
	}

	public String getEnData() {
		return enData;
	}

	public void setEnData(String enData) {
		this.enData = enData;
	}

	@Override
	public String toString() {
		return "EncryptedUIRequestDao [enData=" + enData + "]";
	}

}
