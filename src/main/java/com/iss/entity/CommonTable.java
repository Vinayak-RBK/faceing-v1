package com.iss.entity;

import java.math.BigDecimal;
import java.sql.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "common_table")
public class CommonTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "commonSeqNo")
	@SequenceGenerator(name = "commonSeqNo", sequenceName = "common_sequence", initialValue = 1000000000, allocationSize = 1)
	@Column(name = "guest_id")
	private Long guestId;

	@Column(name = "user_email_id", nullable = true, length = 100)
	private String userEmail;

	@Column(name = "guest_name", nullable = true, length = 40)
	private String guestName;

	@Column(name = "guest_gender", nullable = true, length = 10)
	private String guestGender;

	@Column(name = "guest_dob", nullable = true)
	@Temporal(TemporalType.DATE)
	private Date guestDOB;

	@Column(name = "guest_weight")
	private Double guestWeight;

	@Column(name = "guest_height")
	private Double guestHeight;

	@Column(name = "guest_image", length = 100)
	private String guestImage;

	@Column(name = "guest_current_health_cond", length = 50)
	private String guestCurrentHealthCond;

	@Column(name = "guest_current_medication", length = 50)
	private String guestCurrentMedication;

	@Column(name = "guest_drink_alcohol", length = 50)
	private String guestDrinkAlcohol;

//	@Column(name = "guest_health_detail_id", length = 10)
//	private Integer guestHealthDetailId;

	@Column(name = "guest_respiration_rate")
	private BigDecimal guestRespirationRate;

	@Column(name = "guest_blood_pressure")
	private BigDecimal guestBloodPressure;

	@Column(name = "guest_heart_rate")
	private Integer guestHeartRate;

	@Column(name = "guest_oxygen_saturation")
	private BigDecimal guestOxygenSaturation;

	@Column(name = "guest_hrv_data")
	private BigDecimal guestHRVData;

	@Column(name = "guest_stress_level")
	private BigDecimal guestStressLevel;

	@Column(name = "guest_relaxation_level")
	private BigDecimal guestRelaxationLevel;

	@Column(name = "guest_energy_level")
	private BigDecimal guestEnergyLevel;

	@Column(name = "guest_body_shape_index")
	private Integer guestBodyShapeIndex;

	@Column(name = "guest_body_mass_index")
	private BigDecimal guestBodyMassIndex;

	@Column(name = "guest_age")
	private Integer guestAge;

	@Column(name = "guest_hemoglobin_level")
	private Double guestHemoglobinLevel;

	@Column(name = "guest_fitness_level")
	private BigDecimal guestFitnessLevel;
	
	@Column(name = "user_id", length = 10)
	private Integer userId;

//	@Column(name = "user_password", length = 20, nullable = false)
//	private String userPassword;
//
//	@Column(name = "user_otp", length = 5, nullable = true)
//	private String userOTP;
	
//	@Column(name = "is_admin", columnDefinition = "TINYINT(1)")
//	private Boolean isAdmin;

//	@Column(name = "otp_entry_date", length = 20)
//	private String otpEntryDate;
//
//	@Column(name = "term_cond", columnDefinition = "TINYINT(1)")
//	private Boolean termCond;
//
//	@Column(name = "user_on_session", columnDefinition = "TINYINT(1)")
//	private Boolean userOnSession;

//	@Column(name = "user_health_detail_id", nullable = false)
//	private Long userHealthDetailId;
//
//	@Column(name = "user_respiration_rate")
//	private BigDecimal userRespirationRate;
//
//	@Column(name = "user_blood_pressure")
//	private BigDecimal userBloodPressure;
//
//	@Column(name = "user_heart_rate")
//	private Integer userHeartRate;
//
//	@Column(name = "user_oxygen_saturation")
//	private BigDecimal userOxygenSaturation;
//
//	@Column(name = "user_hrv_data")
//	private BigDecimal userHRVData;
//
//	@Column(name = "user_stress_level")
//	private BigDecimal userStressLevel;
//
//	@Column(name = "user_relaxation_level")
//	private BigDecimal userRelaxationLevel;
//
//	@Column(name = "user_energy_level")
//	private BigDecimal userEnergyLevel;
//
//	@Column(name = "user_body_shape_index")
//	private Integer userBodyShapeIndex;
//
//	@Column(name = "user_body_mass_index")
//	private BigDecimal userBodyMassIndex;
//
//	@Column(name = "user_age")
//	private Integer userAge;
//
//	@Column(name = "user_hemoglobin_level")
//	private BigDecimal userHemoglobinLevel;
//
//	@Column(name = "user_fitness_level")
//	private BigDecimal userFitnessLevel;
//
//	@Column(name = "user_personal_detail_id", length = 10)
//	private Integer userPersonalDetailId;
//	
//	@Column(name = "user_name", nullable = false, length = 40)
//	private String userName;
//
//	@Column(name = "user_gender", nullable = false, length = 10)
//	private String userGender;
//
//	@Column(name = "user_dob")
//	@Temporal(TemporalType.DATE)
//	@DateTimeFormat(pattern = "dd-MM-yyyy")
//	private Date userDOB;
//
//	@Column(name = "user_weight", precision = 5, scale = 2, nullable = true)
//	private BigDecimal userWeight;
//
//	@Column(name = "user_height", precision = 5, scale = 2, nullable = true)
//	private BigDecimal userHeight;
//
//	@Column(name = "user_image", length = 100, nullable = true)
//	private String userImage;
//
//	@Column(name = "user_current_health_cond", length = 50, nullable = false)
//	private String userCurrentHealthCond;
//
//	@Column(name = "user_current_medication", length = 50, nullable = false)
//	private String userCurrentMedication;
//
//	@Column(name = "user_drink_alcohol", nullable = false, length = 50)
//	private String userDringAlcohol;
	
	@Column(name = "regist_date", nullable = true)
	private String registDate;

	@Column(name = "regist_pname", nullable = true, length = 50)
	private String registPName;

	@Column(name = "last_update_date", nullable = true)
	private String lastUpdateDate;

	@Column(name = "last_update_pname", length = 50)
	private String lastUpdatePName;
	
	public CommonTable() {
		super();
	}

	public CommonTable(Long guestId, String userEmail, String guestName, String guestGender,
			Date guestDOB, Double guestWeight, Double guestHeight, String guestImage, String guestCurrentHealthCond,
			String guestCurrentMedication, String guestDrinkAlcohol, Integer guestHealthDetailId,
			BigDecimal guestRespirationRate, BigDecimal guestBloodPressure, Integer guestHeartRate,
			BigDecimal guestOxygenSaturation, BigDecimal guestHRVData, BigDecimal guestStressLevel,
			BigDecimal guestRelaxationLevel, BigDecimal guestEnergyLevel, Integer guestBodyShapeIndex,
			BigDecimal guestBodyMassIndex, Integer guestAge, Double guestHemoglobinLevel, BigDecimal guestFitnessLevel,
			Integer userId, String userPassword, String userOTP, Boolean isAdmin, String otpEntryDate, Boolean termCond,
			Boolean userOnSession, Long userHealthDetailId, BigDecimal userRespirationRate,
			BigDecimal userBloodPressure, Integer userHeartRate, BigDecimal userOxygenSaturation,
			BigDecimal userHRVData, BigDecimal userStressLevel, BigDecimal userRelaxationLevel,
			BigDecimal userEnergyLevel, Integer userBodyShapeIndex, BigDecimal userBodyMassIndex, Integer userAge,
			BigDecimal userHemoglobinLevel, BigDecimal userFitnessLevel, Integer userPersonalDetailId, String userName,
			String userGender, Date userDOB, BigDecimal userWeight, BigDecimal userHeight, String userImage,
			String userCurrentHealthCond, String userCurrentMedication, String userDringAlcohol, String registDate,
			String registPName, String lastUpdateDate, String lastUpdatePName) {
		super();
//		this.common_id = common_id;
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
//		this.guestHealthDetailId = guestHealthDetailId;
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
//		this.userPassword = userPassword;
//		this.userOTP = userOTP;
//		this.isAdmin = isAdmin;
//		this.otpEntryDate = otpEntryDate;
//		this.termCond = termCond;
//		this.userOnSession = userOnSession;
//		this.userHealthDetailId = userHealthDetailId;
//		this.userRespirationRate = userRespirationRate;
//		this.userBloodPressure = userBloodPressure;
//		this.userHeartRate = userHeartRate;
//		this.userOxygenSaturation = userOxygenSaturation;
//		this.userHRVData = userHRVData;
//		this.userStressLevel = userStressLevel;
//		this.userRelaxationLevel = userRelaxationLevel;
//		this.userEnergyLevel = userEnergyLevel;
//		this.userBodyShapeIndex = userBodyShapeIndex;
//		this.userBodyMassIndex = userBodyMassIndex;
//		this.userAge = userAge;
//		this.userHemoglobinLevel = userHemoglobinLevel;
//		this.userFitnessLevel = userFitnessLevel;
//		this.userPersonalDetailId = userPersonalDetailId;
//		this.userName = userName;
//		this.userGender = userGender;
//		this.userDOB = userDOB;
//		this.userWeight = userWeight;
//		this.userHeight = userHeight;
//		this.userImage = userImage;
//		this.userCurrentHealthCond = userCurrentHealthCond;
//		this.userCurrentMedication = userCurrentMedication;
//		this.userDringAlcohol = userDringAlcohol;
		this.registDate = registDate;
		this.registPName = registPName;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdatePName = lastUpdatePName;
	}

//	public Integer getCommon_id() {
//		return common_id;
//	}
//
//	public void setCommon_id(Integer common_id) {
//		this.common_id = common_id;
//	}

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

//	public Integer getGuestHealthDetailId() {
//		return guestHealthDetailId;
//	}
//
//	public void setGuestHealthDetailId(Integer guestHealthDetailId) {
//		this.guestHealthDetailId = guestHealthDetailId;
//	}

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

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

//	public String getUserPassword() {
//		return userPassword;
//	}
//
//	public void setUserPassword(String userPassword) {
//		this.userPassword = userPassword;
//	}
//
//	public String getUserOTP() {
//		return userOTP;
//	}
//
//	public void setUserOTP(String userOTP) {
//		this.userOTP = userOTP;
//	}

//	public Boolean getIsAdmin() {
//		return isAdmin;
//	}
//
//	public void setIsAdmin(Boolean isAdmin) {
//		this.isAdmin = isAdmin;
//	}

//	public String getOtpEntryDate() {
//		return otpEntryDate;
//	}
//
//	public void setOtpEntryDate(String otpEntryDate) {
//		this.otpEntryDate = otpEntryDate;
//	}
//
//	public Boolean getTermCond() {
//		return termCond;
//	}
//
//	public void setTermCond(Boolean termCond) {
//		this.termCond = termCond;
//	}
//
//	public Boolean getUserOnSession() {
//		return userOnSession;
//	}
//
//	public void setUserOnSession(Boolean userOnSession) {
//		this.userOnSession = userOnSession;
//	}

//	public Long getUserHealthDetailId() {
//		return userHealthDetailId;
//	}
//
//	public void setUserHealthDetailId(Long userHealthDetailId) {
//		this.userHealthDetailId = userHealthDetailId;
//	}
//
//	public BigDecimal getUserRespirationRate() {
//		return userRespirationRate;
//	}
//
//	public void setUserRespirationRate(BigDecimal userRespirationRate) {
//		this.userRespirationRate = userRespirationRate;
//	}
//
//	public BigDecimal getUserBloodPressure() {
//		return userBloodPressure;
//	}
//
//	public void setUserBloodPressure(BigDecimal userBloodPressure) {
//		this.userBloodPressure = userBloodPressure;
//	}
//
//	public Integer getUserHeartRate() {
//		return userHeartRate;
//	}
//
//	public void setUserHeartRate(Integer userHeartRate) {
//		this.userHeartRate = userHeartRate;
//	}
//
//	public BigDecimal getUserOxygenSaturation() {
//		return userOxygenSaturation;
//	}
//
//	public void setUserOxygenSaturation(BigDecimal userOxygenSaturation) {
//		this.userOxygenSaturation = userOxygenSaturation;
//	}
//
//	public BigDecimal getUserHRVData() {
//		return userHRVData;
//	}
//
//	public void setUserHRVData(BigDecimal userHRVData) {
//		this.userHRVData = userHRVData;
//	}
//
//	public BigDecimal getUserStressLevel() {
//		return userStressLevel;
//	}
//
//	public void setUserStressLevel(BigDecimal userStressLevel) {
//		this.userStressLevel = userStressLevel;
//	}
//
//	public BigDecimal getUserRelaxationLevel() {
//		return userRelaxationLevel;
//	}
//
//	public void setUserRelaxationLevel(BigDecimal userRelaxationLevel) {
//		this.userRelaxationLevel = userRelaxationLevel;
//	}
//
//	public BigDecimal getUserEnergyLevel() {
//		return userEnergyLevel;
//	}
//
//	public void setUserEnergyLevel(BigDecimal userEnergyLevel) {
//		this.userEnergyLevel = userEnergyLevel;
//	}
//
//	public Integer getUserBodyShapeIndex() {
//		return userBodyShapeIndex;
//	}
//
//	public void setUserBodyShapeIndex(Integer userBodyShapeIndex) {
//		this.userBodyShapeIndex = userBodyShapeIndex;
//	}
//
//	public BigDecimal getUserBodyMassIndex() {
//		return userBodyMassIndex;
//	}
//
//	public void setUserBodyMassIndex(BigDecimal userBodyMassIndex) {
//		this.userBodyMassIndex = userBodyMassIndex;
//	}
//
//	public Integer getUserAge() {
//		return userAge;
//	}
//
//	public void setUserAge(Integer userAge) {
//		this.userAge = userAge;
//	}
//
//	public BigDecimal getUserHemoglobinLevel() {
//		return userHemoglobinLevel;
//	}
//
//	public void setUserHemoglobinLevel(BigDecimal userHemoglobinLevel) {
//		this.userHemoglobinLevel = userHemoglobinLevel;
//	}
//
//	public BigDecimal getUserFitnessLevel() {
//		return userFitnessLevel;
//	}
//
//	public void setUserFitnessLevel(BigDecimal userFitnessLevel) {
//		this.userFitnessLevel = userFitnessLevel;
//	}
//
//	public Integer getUserPersonalDetailId() {
//		return userPersonalDetailId;
//	}
//
//	public void setUserPersonalDetailId(Integer userPersonalDetailId) {
//		this.userPersonalDetailId = userPersonalDetailId;
//	}
//
//	public String getUserName() {
//		return userName;
//	}
//
//	public void setUserName(String userName) {
//		this.userName = userName;
//	}
//
//	public String getUserGender() {
//		return userGender;
//	}
//
//	public void setUserGender(String userGender) {
//		this.userGender = userGender;
//	}
//
//	public Date getUserDOB() {
//		return userDOB;
//	}
//
//	public void setUserDOB(Date userDOB) {
//		this.userDOB = userDOB;
//	}
//
//	public BigDecimal getUserWeight() {
//		return userWeight;
//	}
//
//	public void setUserWeight(BigDecimal userWeight) {
//		this.userWeight = userWeight;
//	}
//
//	public BigDecimal getUserHeight() {
//		return userHeight;
//	}
//
//	public void setUserHeight(BigDecimal userHeight) {
//		this.userHeight = userHeight;
//	}
//
//	public String getUserImage() {
//		return userImage;
//	}
//
//	public void setUserImage(String userImage) {
//		this.userImage = userImage;
//	}
//
//	public String getUserCurrentHealthCond() {
//		return userCurrentHealthCond;
//	}
//
//	public void setUserCurrentHealthCond(String userCurrentHealthCond) {
//		this.userCurrentHealthCond = userCurrentHealthCond;
//	}
//
//	public String getUserCurrentMedication() {
//		return userCurrentMedication;
//	}
//
//	public void setUserCurrentMedication(String userCurrentMedication) {
//		this.userCurrentMedication = userCurrentMedication;
//	}
//
//	public String getUserDringAlcohol() {
//		return userDringAlcohol;
//	}
//
//	public void setUserDringAlcohol(String userDringAlcohol) {
//		this.userDringAlcohol = userDringAlcohol;
//	}

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

}
