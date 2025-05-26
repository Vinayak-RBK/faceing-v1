package com.iss.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "Guest_Health_Detail")
public class GuestHealthDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guestHealthDetailSeqGen")
	@SequenceGenerator(name = "guestHealthDetailSeqGen", sequenceName = "guest_health_detail_sequence", initialValue = 1000000000, allocationSize = 1)
	@Column(name = "guest_health_detail_id", length = 10, unique = true)
	private Long guestHealthDetailId;

	@Column(name = "user_email_id", nullable = true, length = 100)
	private String userEmail;

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
	private BigDecimal guestHemoglobinLevel;

	@Column(name = "guest_fitness_level")
	private BigDecimal guestFitnessLevel;

	@Column(name = "regist_date", nullable = true)
	private String registDate;

	@Column(name = "regist_pname", nullable = true, length = 50)
	private String registPName;

	@Column(name = "last_update_date", nullable = true)
	private String lastUpdateDate;

	@Column(name = "last_update_pname", length = 50)
	private String lastUpdatePName;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "guest_id", referencedColumnName = "guest_id")
	private Guest guest; 

	public GuestHealthDetail() {
		super();
	}

	public GuestHealthDetail(Long guestHealthDetailId, String userEmail, BigDecimal guestRespirationRate,
			BigDecimal guestBloodPressure, Integer guestHeartRate, BigDecimal guestOxygenSaturation,
			BigDecimal guestHRVData, BigDecimal guestStressLevel, BigDecimal guestRelaxationLevel,
			BigDecimal guestEnergyLevel, Integer guestBodyShapeIndex, BigDecimal guestBodyMassIndex, Integer guestAge,
			BigDecimal guestHemoglobinLevel, BigDecimal guestFitnessLevel, String registDate, String registPName,
			String lastUpdateDate, String lastUpdatePName, Guest guest) {
		super();
		this.guestHealthDetailId = guestHealthDetailId;
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
		this.registDate = registDate;
		this.registPName = registPName;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdatePName = lastUpdatePName;
		this.guest = guest;
	}

	public Long getGuestHealthDetailId() {
		return guestHealthDetailId;
	}

	public void setGuestHealthDetailId(Long guestHealthDetailId) {
		this.guestHealthDetailId = guestHealthDetailId;
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

	@Autowired
	public Guest getGuest() {
		return guest;
	}

	@Autowired
	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	@Override
	public String toString() {
		return "GuestHealthDetail [guestHealthDetailId=" + guestHealthDetailId + ", userEmail=" + userEmail
				+ ", guestRespirationRate=" + guestRespirationRate + ", guestBloodPressure=" + guestBloodPressure
				+ ", guestHeartRate=" + guestHeartRate + ", guestOxygenSaturation=" + guestOxygenSaturation
				+ ", guestHRVData=" + guestHRVData + ", guestStressLevel=" + guestStressLevel
				+ ", guestRelaxationLevel=" + guestRelaxationLevel + ", guestEnergyLevel=" + guestEnergyLevel
				+ ", guestBodyShapeIndex=" + guestBodyShapeIndex + ", guestBodyMassIndex=" + guestBodyMassIndex
				+ ", guestAge=" + guestAge + ", guestHemoglobinLevel=" + guestHemoglobinLevel + ", guestFitnessLevel="
				+ guestFitnessLevel + ", registDate=" + registDate + ", registPName=" + registPName
				+ ", lastUpdateDate=" + lastUpdateDate + ", lastUpdatePName=" + lastUpdatePName + ", guest=" + guest
				+ "]";
	}

}