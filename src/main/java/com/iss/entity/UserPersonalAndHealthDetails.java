package com.iss.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_personal_health_details")
public class UserPersonalAndHealthDetails {

//	@Id
//	@Column(name = "id", length = 10, unique = true)
//	private Integer id;
	
	@Id
	@Column(name = "user_id", length = 10, unique = true)
	private String userId;

	@Column(name = "user_email_id", length = 50, nullable = false, unique = true)
	private String userEmail;

	@Column(name = "user_password", length = 20, nullable = false)
	private String userPassword;

	@Column(name = "user_name", nullable = true, length = 40)
	private String userName;

	@Column(name = "user_gender", nullable = true, length = 10)
	private String userGender;

	@Column(name = "user_dob")
	private String userDOB;

	@Column(name = "user_weight", precision = 5, scale = 2, nullable = true)
	private BigDecimal userWeight;

	@Column(name = "user_height", precision = 5, scale = 2, nullable = true)
	private BigDecimal userHeight;

	@Column(name = "user_image", length = 100, nullable = true)
	private String userImage;
	
	@Column(name = "user_respiration_rate")
	private BigDecimal userRespirationRate;

	@Column(name = "user_blood_pressure")
	private BigDecimal userBloodPressure;

	@Column(name = "user_heart_rate")
	private Integer userHeartRate;

	@Column(name = "user_oxygen_saturation")
	private BigDecimal userOxygenSaturation;

	@Column(name = "user_hrv_data")
	private BigDecimal userHRVData;

	@Column(name = "user_stress_level")
	private BigDecimal userStressLevel;

	@Column(name = "user_relaxation_level")
	private BigDecimal userRelaxationLevel;

	@Column(name = "user_energy_level")
	private BigDecimal userEnergyLevel;

	@Column(name = "user_body_shape_index")
	private Integer userBodyShapeIndex;

	@Column(name = "user_body_mass_index")
	private BigDecimal userBodyMassIndex;

	@Column(name = "user_age")
	private Integer userAge;

	@Column(name = "user_hemoglobin_level")
	private BigDecimal userHemoglobinLevel;

	@Column(name = "user_fitness_level")
	private BigDecimal userFitnessLevel;
	
	public UserPersonalAndHealthDetails() {
		super();
	}

	public UserPersonalAndHealthDetails(String userId, String userEmail, String userPassword,
			String userName, String userGender, String userDOB, BigDecimal userWeight, BigDecimal userHeight,
			String userImage, BigDecimal userRespirationRate, BigDecimal userBloodPressure, Integer userHeartRate,
			BigDecimal userOxygenSaturation, BigDecimal userHRVData, BigDecimal userStressLevel,
			BigDecimal userRelaxationLevel, BigDecimal userEnergyLevel, Integer userBodyShapeIndex,
			BigDecimal userBodyMassIndex, Integer userAge, BigDecimal userHemoglobinLevel,
			BigDecimal userFitnessLevel) {
		super();
		this.userId = userId;
		this.userEmail = userEmail;
		this.userPassword = userPassword;
		this.userName = userName;
		this.userGender = userGender;
		this.userDOB = userDOB;
		this.userWeight = userWeight;
		this.userHeight = userHeight;
		this.userImage = userImage;
		this.userRespirationRate = userRespirationRate;
		this.userBloodPressure = userBloodPressure;
		this.userHeartRate = userHeartRate;
		this.userOxygenSaturation = userOxygenSaturation;
		this.userHRVData = userHRVData;
		this.userStressLevel = userStressLevel;
		this.userRelaxationLevel = userRelaxationLevel;
		this.userEnergyLevel = userEnergyLevel;
		this.userBodyShapeIndex = userBodyShapeIndex;
		this.userBodyMassIndex = userBodyMassIndex;
		this.userAge = userAge;
		this.userHemoglobinLevel = userHemoglobinLevel;
		this.userFitnessLevel = userFitnessLevel;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserDOB() {
		return userDOB;
	}

	public void setUserDOB(String userDOB) {
		this.userDOB = userDOB;
	}

	public BigDecimal getUserWeight() {
		return userWeight;
	}

	public void setUserWeight(BigDecimal userWeight) {
		this.userWeight = userWeight;
	}

	public BigDecimal getUserHeight() {
		return userHeight;
	}

	public void setUserHeight(BigDecimal userHeight) {
		this.userHeight = userHeight;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public BigDecimal getUserRespirationRate() {
		return userRespirationRate;
	}

	public void setUserRespirationRate(BigDecimal userRespirationRate) {
		this.userRespirationRate = userRespirationRate;
	}

	public BigDecimal getUserBloodPressure() {
		return userBloodPressure;
	}

	public void setUserBloodPressure(BigDecimal userBloodPressure) {
		this.userBloodPressure = userBloodPressure;
	}

	public Integer getUserHeartRate() {
		return userHeartRate;
	}

	public void setUserHeartRate(Integer userHeartRate) {
		this.userHeartRate = userHeartRate;
	}

	public BigDecimal getUserOxygenSaturation() {
		return userOxygenSaturation;
	}

	public void setUserOxygenSaturation(BigDecimal userOxygenSaturation) {
		this.userOxygenSaturation = userOxygenSaturation;
	}

	public BigDecimal getUserHRVData() {
		return userHRVData;
	}

	public void setUserHRVData(BigDecimal userHRVData) {
		this.userHRVData = userHRVData;
	}

	public BigDecimal getUserStressLevel() {
		return userStressLevel;
	}

	public void setUserStressLevel(BigDecimal userStressLevel) {
		this.userStressLevel = userStressLevel;
	}

	public BigDecimal getUserRelaxationLevel() {
		return userRelaxationLevel;
	}

	public void setUserRelaxationLevel(BigDecimal userRelaxationLevel) {
		this.userRelaxationLevel = userRelaxationLevel;
	}

	public BigDecimal getUserEnergyLevel() {
		return userEnergyLevel;
	}

	public void setUserEnergyLevel(BigDecimal userEnergyLevel) {
		this.userEnergyLevel = userEnergyLevel;
	}

	public Integer getUserBodyShapeIndex() {
		return userBodyShapeIndex;
	}

	public void setUserBodyShapeIndex(Integer userBodyShapeIndex) {
		this.userBodyShapeIndex = userBodyShapeIndex;
	}

	public BigDecimal getUserBodyMassIndex() {
		return userBodyMassIndex;
	}

	public void setUserBodyMassIndex(BigDecimal userBodyMassIndex) {
		this.userBodyMassIndex = userBodyMassIndex;
	}

	public Integer getUserAge() {
		return userAge;
	}

	public void setUserAge(Integer userAge) {
		this.userAge = userAge;
	}

	public BigDecimal getUserHemoglobinLevel() {
		return userHemoglobinLevel;
	}

	public void setUserHemoglobinLevel(BigDecimal userHemoglobinLevel) {
		this.userHemoglobinLevel = userHemoglobinLevel;
	}

	public BigDecimal getUserFitnessLevel() {
		return userFitnessLevel;
	}

	public void setUserFitnessLevel(BigDecimal userFitnessLevel) {
		this.userFitnessLevel = userFitnessLevel;
	}

	@Override
	public String toString() {
		return "UserPersonalAndHealthDetails [userId=" + userId + ", userEmail=" + userEmail
				+ ", userPassword=" + userPassword + ", userName=" + userName + ", userGender=" + userGender
				+ ", userDOB=" + userDOB + ", userWeight=" + userWeight + ", userHeight=" + userHeight + ", userImage="
				+ userImage + ", userRespirationRate=" + userRespirationRate + ", userBloodPressure="
				+ userBloodPressure + ", userHeartRate=" + userHeartRate + ", userOxygenSaturation="
				+ userOxygenSaturation + ", userHRVData=" + userHRVData + ", userStressLevel=" + userStressLevel
				+ ", userRelaxationLevel=" + userRelaxationLevel + ", userEnergyLevel=" + userEnergyLevel
				+ ", userBodyShapeIndex=" + userBodyShapeIndex + ", userBodyMassIndex=" + userBodyMassIndex
				+ ", userAge=" + userAge + ", userHemoglobinLevel=" + userHemoglobinLevel + ", userFitnessLevel="
				+ userFitnessLevel + "]";
	}
	
}
