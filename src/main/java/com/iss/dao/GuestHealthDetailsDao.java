package com.iss.dao;

import java.math.BigDecimal;

public class GuestHealthDetailsDao {

	private String userEmail;
	private BigDecimal guestRespirationRate;
	private BigDecimal guestBloodPressure;
	private Integer guestHeartRate;
	private BigDecimal guestOxygenSaturation;
	private BigDecimal guestHRVData;
	private BigDecimal guestStressLevel;
	private BigDecimal guestRelaxationLevel;
	private BigDecimal guestEnergyLevel;
	private Integer guestBodyShapeIndex;
	private BigDecimal guestBodyMassIndex;
	private Integer guestAge;
	private BigDecimal guestHemoglobinLevel;
	private BigDecimal guestFitnessLevel;
	
	public GuestHealthDetailsDao() {
		super();
	}

	public GuestHealthDetailsDao(String userEmail, BigDecimal guestRespirationRate, BigDecimal guestBloodPressure,
			Integer guestHeartRate, BigDecimal guestOxygenSaturation, BigDecimal guestHRVData,
			BigDecimal guestStressLevel, BigDecimal guestRelaxationLevel, BigDecimal guestEnergyLevel,
			Integer guestBodyShapeIndex, BigDecimal guestBodyMassIndex, Integer guestAge, BigDecimal guestHemoglobinLevel,
			BigDecimal guestFitnessLevel) {
		super();
		this.userEmail = userEmail;
		this.guestRespirationRate = guestRespirationRate;
		this.guestBloodPressure = guestBloodPressure;
		this.guestHeartRate = guestHeartRate;
		this.guestOxygenSaturation = guestOxygenSaturation;
		this.guestHRVData = guestHRVData;
		this.guestStressLevel = guestStressLevel;
		this.guestRelaxationLevel = guestRelaxationLevel;
		this.guestEnergyLevel = guestEnergyLevel;
		this.guestBodyShapeIndex = guestBodyShapeIndex;
		this.guestBodyMassIndex = guestBodyMassIndex;
		this.guestAge = guestAge;
		this.guestHemoglobinLevel = guestHemoglobinLevel;
		this.guestFitnessLevel = guestFitnessLevel;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public BigDecimal getGuestRespirationRate() {
		return guestRespirationRate;
	}

	public void setGuestRespirationRate(BigDecimal guestRespirationRate) {
		this.guestRespirationRate = guestRespirationRate;
	}

	public BigDecimal getGuestBloodPressure() {
		return guestBloodPressure;
	}

	public void setGuestBloodPressure(BigDecimal guestBloodPressure) {
		this.guestBloodPressure = guestBloodPressure;
	}

	public Integer getGuestHeartRate() {
		return guestHeartRate;
	}

	public void setGuestHeartRate(Integer guestHeartRate) {
		this.guestHeartRate = guestHeartRate;
	}

	public BigDecimal getGuestOxygenSaturation() {
		return guestOxygenSaturation;
	}

	public void setGuestOxygenSaturation(BigDecimal guestOxygenSaturation) {
		this.guestOxygenSaturation = guestOxygenSaturation;
	}

	public BigDecimal getGuestHRVData() {
		return guestHRVData;
	}

	public void setGuestHRVData(BigDecimal guestHRVData) {
		this.guestHRVData = guestHRVData;
	}

	public BigDecimal getGuestStressLevel() {
		return guestStressLevel;
	}

	public void setGuestStressLevel(BigDecimal guestStressLevel) {
		this.guestStressLevel = guestStressLevel;
	}

	public BigDecimal getGuestRelaxationLevel() {
		return guestRelaxationLevel;
	}

	public void setGuestRelaxationLevel(BigDecimal guestRelaxationLevel) {
		this.guestRelaxationLevel = guestRelaxationLevel;
	}

	public BigDecimal getGuestEnergyLevel() {
		return guestEnergyLevel;
	}

	public void setGuestEnergyLevel(BigDecimal guestEnergyLevel) {
		this.guestEnergyLevel = guestEnergyLevel;
	}

	public Integer getGuestBodyShapeIndex() {
		return guestBodyShapeIndex;
	}

	public void setGuestBodyShapeIndex(Integer guestBodyShapeIndex) {
		this.guestBodyShapeIndex = guestBodyShapeIndex;
	}

	public BigDecimal getGuestBodyMassIndex() {
		return guestBodyMassIndex;
	}

	public void setGuestBodyMassIndex(BigDecimal guestBodyMassIndex) {
		this.guestBodyMassIndex = guestBodyMassIndex;
	}

	public Integer getGuestAge() {
		return guestAge;
	}

	public void setGuestAge(Integer guestAge) {
		this.guestAge = guestAge;
	}

	public BigDecimal getGuestHemoglobinLevel() {
		return guestHemoglobinLevel;
	}

	public void setGuestHemoglobinLevel(BigDecimal guestHemoglobinLevel) {
		this.guestHemoglobinLevel = guestHemoglobinLevel;
	}

	public BigDecimal getGuestFitnessLevel() {
		return guestFitnessLevel;
	}

	public void setGuestFitnessLevel(BigDecimal guestFitnessLevel) {
		this.guestFitnessLevel = guestFitnessLevel;
	}

	@Override
	public String toString() {
		return "GuestHealthDetailsDao [userEmail=" + userEmail + ", guestRespirationRate=" + guestRespirationRate
				+ ", guestBloodPressure=" + guestBloodPressure + ", guestHeartRate=" + guestHeartRate
				+ ", guestOxygenSaturation=" + guestOxygenSaturation + ", guestHRVData=" + guestHRVData
				+ ", guestStressLevel=" + guestStressLevel + ", guestRelaxationLevel=" + guestRelaxationLevel
				+ ", guestEnergyLevel=" + guestEnergyLevel + ", guestBodyShapeIndex=" + guestBodyShapeIndex
				+ ", guestBodyMassIndex=" + guestBodyMassIndex + ", guestAge=" + guestAge + ", guestHemoglobinLevel="
				+ guestHemoglobinLevel + ", guestFitnessLevel=" + guestFitnessLevel + "]";
	}
	
}
