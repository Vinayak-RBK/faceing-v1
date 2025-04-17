package com.iss.dao;

public class HealthDetailsDao {
	
	private String emailId;
	private String userCurrentHealthCond;
	private String userCurrentMedication;
	private String userDringAlcohol;
	
	public HealthDetailsDao() {
		super();
	}

	public HealthDetailsDao(String emailId, String userCurrentHealthCond, String userCurrentMedication,
			String userDringAlcohol) {
		super();
		this.emailId = emailId;
		this.userCurrentHealthCond = userCurrentHealthCond;
		this.userCurrentMedication = userCurrentMedication;
		this.userDringAlcohol = userDringAlcohol;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getUserCurrentHealthCond() {
		return userCurrentHealthCond;
	}

	public void setUserCurrentHealthCond(String userCurrentHealthCond) {
		this.userCurrentHealthCond = userCurrentHealthCond;
	}

	public String getUserCurrentMedication() {
		return userCurrentMedication;
	}

	public void setUserCurrentMedication(String userCurrentMedication) {
		this.userCurrentMedication = userCurrentMedication;
	}

	public String getUserDringAlcohol() {
		return userDringAlcohol;
	}

	public void setUserDringAlcohol(String userDringAlcohol) {
		this.userDringAlcohol = userDringAlcohol;
	}

	@Override
	public String toString() {
		return "HealthDetailsDao [emailId=" + emailId + ", userCurrentHealthCond=" + userCurrentHealthCond
				+ ", userCurrentMedication=" + userCurrentMedication + ", userDringAlcohol=" + userDringAlcohol + "]";
	}
	
}
