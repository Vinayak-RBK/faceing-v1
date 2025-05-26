package com.iss.dao;

import java.math.BigDecimal;
import java.sql.Date;

public class GuestUserSearchDao {

	private Integer guestId;
	private String userEmail;
	private String guestName;
	private String guestGender;
	private Date guestDOB;
	private Double guestWeight;
	private Double guestHeight;
	private String guestImage;
	private String guestCurrentHealthCond;
	private String guestCurrentMedication;
	private String guestDrinkAlcohol;
	private String registDate;
	private String registPName;
	private String lastUpdateDate;
	private String lastUpdatePName;
	private Integer guestHealthDetailId;
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
	private Double guestHemoglobinLevel;
	private BigDecimal guestFitnessLevel;
	
	public GuestUserSearchDao() {
		super();
	}

	public GuestUserSearchDao(Integer guestId, String userEmail, String guestName, String guestGender, Date guestDOB,
			Double guestWeight, Double guestHeight, String guestImage, String guestCurrentHealthCond,
			String guestCurrentMedication, String guestDrinkAlcohol, String registDate, String registPName,
			String lastUpdateDate, String lastUpdatePName, Integer guestHealthDetailId, BigDecimal guestRespirationRate,
			BigDecimal guestBloodPressure, Integer guestHeartRate, BigDecimal guestOxygenSaturation,
			BigDecimal guestHRVData, BigDecimal guestStressLevel, BigDecimal guestRelaxationLevel,
			BigDecimal guestEnergyLevel, Integer guestBodyShapeIndex, BigDecimal guestBodyMassIndex, Integer guestAge,
			Double guestHemoglobinLevel, BigDecimal guestFitnessLevel) {
		super();
		this.guestId = guestId;
		this.userEmail = userEmail;
		this.guestName = guestName;
		this.guestGender = guestGender;
		this.guestDOB = guestDOB;
		this.guestWeight = guestWeight;
		this.guestHeight = guestHeight;
		this.guestImage = guestImage;
		this.guestCurrentHealthCond = guestCurrentHealthCond;
		this.guestCurrentMedication = guestCurrentMedication;
		this.guestDrinkAlcohol = guestDrinkAlcohol;
		this.registDate = registDate;
		this.registPName = registPName;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdatePName = lastUpdatePName;
		this.guestHealthDetailId = guestHealthDetailId;
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

	public Integer getGuestId() {
		return guestId;
	}

	public void setGuestId(Integer guestId) {
		this.guestId = guestId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getGuestGender() {
		return guestGender;
	}

	public void setGuestGender(String guestGender) {
		this.guestGender = guestGender;
	}

	public Date getGuestDOB() {
		return guestDOB;
	}

	public void setGuestDOB(Date guestDOB) {
		this.guestDOB = guestDOB;
	}

	public Double getGuestWeight() {
		return guestWeight;
	}

	public void setGuestWeight(Double guestWeight) {
		this.guestWeight = guestWeight;
	}

	public Double getGuestHeight() {
		return guestHeight;
	}

	public void setGuestHeight(Double guestHeight) {
		this.guestHeight = guestHeight;
	}

	public String getGuestImage() {
		return guestImage;
	}

	public void setGuestImage(String guestImage) {
		this.guestImage = guestImage;
	}

	public String getGuestCurrentHealthCond() {
		return guestCurrentHealthCond;
	}

	public void setGuestCurrentHealthCond(String guestCurrentHealthCond) {
		this.guestCurrentHealthCond = guestCurrentHealthCond;
	}

	public String getGuestCurrentMedication() {
		return guestCurrentMedication;
	}

	public void setGuestCurrentMedication(String guestCurrentMedication) {
		this.guestCurrentMedication = guestCurrentMedication;
	}

	public String getGuestDrinkAlcohol() {
		return guestDrinkAlcohol;
	}

	public void setGuestDrinkAlcohol(String guestDrinkAlcohol) {
		this.guestDrinkAlcohol = guestDrinkAlcohol;
	}

	public String getRegistDate() {
		return registDate;
	}

	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}

	public String getRegistPName() {
		return registPName;
	}

	public void setRegistPName(String registPName) {
		this.registPName = registPName;
	}

	public String getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getLastUpdatePName() {
		return lastUpdatePName;
	}

	public void setLastUpdatePName(String lastUpdatePName) {
		this.lastUpdatePName = lastUpdatePName;
	}

	public Integer getGuestHealthDetailId() {
		return guestHealthDetailId;
	}

	public void setGuestHealthDetailId(Integer guestHealthDetailId) {
		this.guestHealthDetailId = guestHealthDetailId;
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

	public Double getGuestHemoglobinLevel() {
		return guestHemoglobinLevel;
	}

	public void setGuestHemoglobinLevel(Double guestHemoglobinLevel) {
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
		return "GuestUserSearchDao [guestId=" + guestId + ", userEmail=" + userEmail + ", guestName=" + guestName
				+ ", guestGender=" + guestGender + ", guestDOB=" + guestDOB + ", guestWeight=" + guestWeight
				+ ", guestHeight=" + guestHeight + ", guestImage=" + guestImage + ", guestCurrentHealthCond="
				+ guestCurrentHealthCond + ", guestCurrentMedication=" + guestCurrentMedication + ", guestDrinkAlcohol="
				+ guestDrinkAlcohol + ", registDate=" + registDate + ", registPName=" + registPName
				+ ", lastUpdateDate=" + lastUpdateDate + ", lastUpdatePName=" + lastUpdatePName
				+ ", guestHealthDetailId=" + guestHealthDetailId + ", guestRespirationRate=" + guestRespirationRate
				+ ", guestBloodPressure=" + guestBloodPressure + ", guestHeartRate=" + guestHeartRate
				+ ", guestOxygenSaturation=" + guestOxygenSaturation + ", guestHRVData=" + guestHRVData
				+ ", guestStressLevel=" + guestStressLevel + ", guestRelaxationLevel=" + guestRelaxationLevel
				+ ", guestEnergyLevel=" + guestEnergyLevel + ", guestBodyShapeIndex=" + guestBodyShapeIndex
				+ ", guestBodyMassIndex=" + guestBodyMassIndex + ", guestAge=" + guestAge + ", guestHemoglobinLevel="
				+ guestHemoglobinLevel + ", guestFitnessLevel=" + guestFitnessLevel + "]";
	}
}
