package com.iss.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "common_table")
public class CommonTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commonSeqNo")
	@SequenceGenerator(name = "commonSeqNo", sequenceName = "common_sequence", initialValue = 1000000000, allocationSize = 1)
	@Column(name = "guest_id")
	private Long guestId;

	@Column(name = "user_email_id", nullable = true,  columnDefinition = "TEXT")
	private String userEmail;

	@Column(name = "guest_name", columnDefinition = "TEXT")
	private String guestName;

	@Column(name = "guest_gender",  columnDefinition = "TEXT")
	private String guestGender;

	@Column(name = "guest_dob", columnDefinition = "TEXT")
	private String guestDOB;

	@Column(name = "guest_weight",columnDefinition = "TEXT")
	private String guestWeight;

	@Column(name = "guest_height",columnDefinition = "TEXT")
	private String guestHeight;

	@Column(name = "guest_image",columnDefinition = "TEXT")
	private String guestImage;

	@Column(name = "guest_current_health_cond",columnDefinition = "TEXT")
	private String guestCurrentHealthCond;

	@Column(name = "guest_current_medication",columnDefinition = "TEXT")
	private String guestCurrentMedication;

	@Column(name = "guest_drink_alcohol",columnDefinition = "TEXT")
	private String guestDrinkAlcohol;

	@Column(name = "guest_respiration_rate",columnDefinition = "TEXT")
	private String guestRespirationRate;

	@Column(name = "guest_blood_pressure",columnDefinition = "TEXT")
	private String guestBloodPressure;

	@Column(name = "guest_heart_rate",columnDefinition = "TEXT")
	private String guestHeartRate;

	@Column(name = "guest_oxygen_saturation",columnDefinition = "TEXT")
	private String guestOxygenSaturation;

	@Column(name = "guest_hrv_data",columnDefinition = "TEXT")
	private String guestHRVData;

	@Column(name = "guest_stress_level",columnDefinition = "TEXT")
	private String guestStressLevel;

	@Column(name = "guest_relaxation_level",columnDefinition = "TEXT")
	private String guestRelaxationLevel;

	@Column(name = "guest_energy_level",columnDefinition = "TEXT")
	private String guestEnergyLevel;

	@Column(name = "guest_body_shape_index",columnDefinition = "TEXT")
	private String guestBodyShapeIndex;

	@Column(name = "guest_body_mass_index",columnDefinition = "TEXT")
	private String guestBodyMassIndex;

	@Column(name = "guest_age",columnDefinition = "TEXT")
	private Integer guestAge;

	@Column(name = "guest_hemoglobin_level",columnDefinition = "TEXT")
	private String guestHemoglobinLevel;

	@Column(name = "guest_fitness_level",columnDefinition = "TEXT")
	private String guestFitnessLevel;
	
	@Column(name = "user_id",columnDefinition = "TEXT")
	private String userId;

	@Column(name = "regist_date",columnDefinition = "TEXT")
	private String registDate;

	@Column(name = "regist_pname",columnDefinition = "TEXT")
	private String registPName;

	@Column(name = "last_update_date",columnDefinition = "TEXT")
	private String lastUpdateDate;

	@Column(name = "last_update_pname",columnDefinition = "TEXT")
	private String lastUpdatePName;
	
	public CommonTable() {
		super();
	}

	public CommonTable(Long guestId, String userEmail, String guestName, String guestGender, String guestDOB,
			String guestWeight, String guestHeight, String guestImage, String guestCurrentHealthCond,
			String guestCurrentMedication, String guestDrinkAlcohol, String guestRespirationRate,
			String guestBloodPressure, String guestHeartRate, String guestOxygenSaturation, String guestHRVData,
			String guestStressLevel, String guestRelaxationLevel, String guestEnergyLevel, String guestBodyShapeIndex,
			String guestBodyMassIndex, Integer guestAge, String guestHemoglobinLevel, String guestFitnessLevel,
			String userId, String registDate, String registPName, String lastUpdateDate, String lastUpdatePName) {
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
		this.userId = userId;
		this.registDate = registDate;
		this.registPName = registPName;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdatePName = lastUpdatePName;
	}

	public Long getGuestId() {
		return guestId;
	}

	public void setGuestId(Long guestId) {
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

	public String getGuestDOB() {
		return guestDOB;
	}

	public void setGuestDOB(String guestDOB) {
		this.guestDOB = guestDOB;
	}

	public String getGuestWeight() {
		return guestWeight;
	}

	public void setGuestWeight(String guestWeight) {
		this.guestWeight = guestWeight;
	}

	public String getGuestHeight() {
		return guestHeight;
	}

	public void setGuestHeight(String guestHeight) {
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

	public String getGuestRespirationRate() {
		return guestRespirationRate;
	}

	public void setGuestRespirationRate(String guestRespirationRate) {
		this.guestRespirationRate = guestRespirationRate;
	}

	public String getGuestBloodPressure() {
		return guestBloodPressure;
	}

	public void setGuestBloodPressure(String guestBloodPressure) {
		this.guestBloodPressure = guestBloodPressure;
	}

	public String getGuestHeartRate() {
		return guestHeartRate;
	}

	public void setGuestHeartRate(String guestHeartRate) {
		this.guestHeartRate = guestHeartRate;
	}

	public String getGuestOxygenSaturation() {
		return guestOxygenSaturation;
	}

	public void setGuestOxygenSaturation(String guestOxygenSaturation) {
		this.guestOxygenSaturation = guestOxygenSaturation;
	}

	public String getGuestHRVData() {
		return guestHRVData;
	}

	public void setGuestHRVData(String guestHRVData) {
		this.guestHRVData = guestHRVData;
	}

	public String getGuestStressLevel() {
		return guestStressLevel;
	}

	public void setGuestStressLevel(String guestStressLevel) {
		this.guestStressLevel = guestStressLevel;
	}

	public String getGuestRelaxationLevel() {
		return guestRelaxationLevel;
	}

	public void setGuestRelaxationLevel(String guestRelaxationLevel) {
		this.guestRelaxationLevel = guestRelaxationLevel;
	}

	public String getGuestEnergyLevel() {
		return guestEnergyLevel;
	}

	public void setGuestEnergyLevel(String guestEnergyLevel) {
		this.guestEnergyLevel = guestEnergyLevel;
	}

	public String getGuestBodyShapeIndex() {
		return guestBodyShapeIndex;
	}

	public void setGuestBodyShapeIndex(String guestBodyShapeIndex) {
		this.guestBodyShapeIndex = guestBodyShapeIndex;
	}

	public String getGuestBodyMassIndex() {
		return guestBodyMassIndex;
	}

	public void setGuestBodyMassIndex(String guestBodyMassIndex) {
		this.guestBodyMassIndex = guestBodyMassIndex;
	}

	public Integer getGuestAge() {
		return guestAge;
	}

	public void setGuestAge(Integer guestAge) {
		this.guestAge = guestAge;
	}

	public String getGuestHemoglobinLevel() {
		return guestHemoglobinLevel;
	}

	public void setGuestHemoglobinLevel(String guestHemoglobinLevel) {
		this.guestHemoglobinLevel = guestHemoglobinLevel;
	}

	public String getGuestFitnessLevel() {
		return guestFitnessLevel;
	}

	public void setGuestFitnessLevel(String guestFitnessLevel) {
		this.guestFitnessLevel = guestFitnessLevel;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	@Override
	public String toString() {
		return "CommonTable [guestId=" + guestId + ", userEmail=" + userEmail + ", guestName=" + guestName
				+ ", guestGender=" + guestGender + ", guestDOB=" + guestDOB + ", guestWeight=" + guestWeight
				+ ", guestHeight=" + guestHeight + ", guestImage=" + guestImage + ", guestCurrentHealthCond="
				+ guestCurrentHealthCond + ", guestCurrentMedication=" + guestCurrentMedication + ", guestDrinkAlcohol="
				+ guestDrinkAlcohol + ", guestRespirationRate=" + guestRespirationRate + ", guestBloodPressure="
				+ guestBloodPressure + ", guestHeartRate=" + guestHeartRate + ", guestOxygenSaturation="
				+ guestOxygenSaturation + ", guestHRVData=" + guestHRVData + ", guestStressLevel=" + guestStressLevel
				+ ", guestRelaxationLevel=" + guestRelaxationLevel + ", guestEnergyLevel=" + guestEnergyLevel
				+ ", guestBodyShapeIndex=" + guestBodyShapeIndex + ", guestBodyMassIndex=" + guestBodyMassIndex
				+ ", guestAge=" + guestAge + ", guestHemoglobinLevel=" + guestHemoglobinLevel + ", guestFitnessLevel="
				+ guestFitnessLevel + ", userId=" + userId + ", registDate=" + registDate + ", registPName="
				+ registPName + ", lastUpdateDate=" + lastUpdateDate + ", lastUpdatePName=" + lastUpdatePName + "]";
	}

}
