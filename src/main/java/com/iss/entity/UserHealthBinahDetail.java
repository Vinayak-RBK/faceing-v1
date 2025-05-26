package com.iss.entity;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_health_binah_detail")
public class UserHealthBinahDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userHealthBinaDetailSeqGen")
	@SequenceGenerator(name = "userHealthBinaDetailSeqGen", sequenceName = "user_health_binah_detail_sequence", initialValue = 1000000000, allocationSize = 1)
	@Column(name = "user_health_Binah_detail_id", length = 10, unique = true)
	private Long userHealthBinahDetailId;

	@Column(name = "user_email_id", nullable = true, columnDefinition = "TEXT")
	private String userEmail;

	@Column(name = "pulse_rate", columnDefinition = "TEXT")
	private String pulseRate;

	@Column(name = "respiration_rate", columnDefinition = "TEXT")
	private String respirationRate;

	@Column(name = "oxygen_saturation", columnDefinition = "TEXT")
	private String oxygenSaturation;

	@Column(name = "sdnn", columnDefinition = "TEXT")
	private String sdnn;

	@Column(name = "stress_level", columnDefinition = "TEXT")
	private String stressLevel;

	@Column(name = "blood_pressure", columnDefinition = "TEXT")
	private String bloodPressure;

	@Column(name = "stress_index", columnDefinition = "TEXT")
	private String stressIndex;

	@Column(name = "mean_rri", columnDefinition = "TEXT")
	private String meanRri;

	@Column(name = "rmssd", columnDefinition = "TEXT")
	private String rmssd;

	@Column(name = "sd1", columnDefinition = "TEXT")
	private String sd1;

	@Column(name = "sd2", columnDefinition = "TEXT")
	private String sd2;

	@Column(name = "prq", columnDefinition = "TEXT")
	private String prq;

	@Column(name = "pns_index", columnDefinition = "TEXT")
	private String pnsIndex;

	@Column(name = "pns_zone", columnDefinition = "TEXT")
	private String pnsZone;

	@Column(name = "sns_index", columnDefinition = "TEXT")
	private String snsIndex;

	@Column(name = "sns_zone", columnDefinition = "TEXT")
	private String snsZone;

	@Column(name = "wellness_index", columnDefinition = "TEXT")
	private String wellnessIndex;

	@Column(name = "wellness_level", columnDefinition = "TEXT")
	private String wellnessLevel;

	@Column(name = "lfhf", columnDefinition = "TEXT")
	private String lfhf;

	@Column(name = "hemoglobin", columnDefinition = "TEXT")
	private String hemoglobin;

	@Column(name = "hemoglobin_a1c", columnDefinition = "TEXT")
	private String hemoglobinA1C;

	@Column(name = "high_hemoglobin_a1C_risk", columnDefinition = "TEXT")
	private String highHemoglobinA1CRisk;

	@Column(name = "high_blood_pressure_risk", columnDefinition = "TEXT")
	private String highBloodPressureRisk;

	@Column(name = "ascvd_risk", columnDefinition = "TEXT")
	private String ascvdRisk;

	@Column(name = "normalized_stress_index", columnDefinition = "TEXT")
	private String normalizedStressIndex;

	@Column(name = "heart_age", columnDefinition = "TEXT")
	private String heartAge;

	@Column(name = "highTotal_cholesterol_risk", columnDefinition = "TEXT")
	private String highTotalCholesterolRisk;

	@Column(name = "high_fasting_glucose_risk", columnDefinition = "TEXT")
	private String highFastingGlucoseRisk;

	@Column(name = "low_hemoglobin_risk", columnDefinition = "TEXT")
	private String lowHemoglobinRisk;

	@Column(name = "regist_date", columnDefinition = "TEXT")
	private String registDate;

	@Column(name = "regist_pname", columnDefinition = "TEXT")
	private String registPName;

	@Column(name = "last_update_date", columnDefinition = "TEXT")
	private String lastUpdateDate;

	@Column(name = "last_update_pname", columnDefinition = "TEXT")
	private String lastUpdatePName;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id", referencedColumnName = "user_id")
	private EndUser endUser;

	public UserHealthBinahDetail() {
		super();
	}

	public UserHealthBinahDetail(Long userHealthBinahDetailId, String userEmail, String pulseRate,
			String respirationRate, String oxygenSaturation, String sdnn, String stressLevel, String bloodPressure,
			String stressIndex, String meanRri, String rmssd, String sd1, String sd2, String prq, String pnsIndex,
			String pnsZone, String snsIndex, String snsZone, String wellnessIndex, String wellnessLevel, String lfhf,
			String hemoglobin, String hemoglobinA1C, String highHemoglobinA1CRisk, String highBloodPressureRisk,
			String ascvdRisk, String normalizedStressIndex, String heartAge, String highTotalCholesterolRisk,
			String highFastingGlucoseRisk, String lowHemoglobinRisk, String registDate, String registPName,
			String lastUpdateDate, String lastUpdatePName, EndUser endUser) {
		super();
		this.userHealthBinahDetailId = userHealthBinahDetailId;
		this.userEmail = userEmail;
		this.pulseRate = pulseRate;
		this.respirationRate = respirationRate;
		this.oxygenSaturation = oxygenSaturation;
		this.sdnn = sdnn;
		this.stressLevel = stressLevel;
		this.bloodPressure = bloodPressure;
		this.stressIndex = stressIndex;
		this.meanRri = meanRri;
		this.rmssd = rmssd;
		this.sd1 = sd1;
		this.sd2 = sd2;
		this.prq = prq;
		this.pnsIndex = pnsIndex;
		this.pnsZone = pnsZone;
		this.snsIndex = snsIndex;
		this.snsZone = snsZone;
		this.wellnessIndex = wellnessIndex;
		this.wellnessLevel = wellnessLevel;
		this.lfhf = lfhf;
		this.hemoglobin = hemoglobin;
		this.hemoglobinA1C = hemoglobinA1C;
		this.highHemoglobinA1CRisk = highHemoglobinA1CRisk;
		this.highBloodPressureRisk = highBloodPressureRisk;
		this.ascvdRisk = ascvdRisk;
		this.normalizedStressIndex = normalizedStressIndex;
		this.heartAge = heartAge;
		this.highTotalCholesterolRisk = highTotalCholesterolRisk;
		this.highFastingGlucoseRisk = highFastingGlucoseRisk;
		this.lowHemoglobinRisk = lowHemoglobinRisk;
		this.registDate = registDate;
		this.registPName = registPName;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdatePName = lastUpdatePName;
		this.endUser = endUser;
	}

	public Long getUserHealthBinahDetailId() {
		return userHealthBinahDetailId;
	}

	public void setUserHealthBinahDetailId(Long userHealthBinahDetailId) {
		this.userHealthBinahDetailId = userHealthBinahDetailId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPulseRate() {
		return pulseRate;
	}

	public void setPulseRate(String pulseRate) {
		this.pulseRate = pulseRate;
	}

	public String getRespirationRate() {
		return respirationRate;
	}

	public void setRespirationRate(String respirationRate) {
		this.respirationRate = respirationRate;
	}

	public String getOxygenSaturation() {
		return oxygenSaturation;
	}

	public void setOxygenSaturation(String oxygenSaturation) {
		this.oxygenSaturation = oxygenSaturation;
	}

	public String getSdnn() {
		return sdnn;
	}

	public void setSdnn(String sdnn) {
		this.sdnn = sdnn;
	}

	public String getStressLevel() {
		return stressLevel;
	}

	public void setStressLevel(String stressLevel) {
		this.stressLevel = stressLevel;
	}

	public String getBloodPressure() {
		return bloodPressure;
	}

	public void setBloodPressure(String bloodPressure) {
		this.bloodPressure = bloodPressure;
	}

	public String getStressIndex() {
		return stressIndex;
	}

	public void setStressIndex(String stressIndex) {
		this.stressIndex = stressIndex;
	}

	public String getMeanRri() {
		return meanRri;
	}

	public void setMeanRri(String meanRri) {
		this.meanRri = meanRri;
	}

	public String getRmssd() {
		return rmssd;
	}

	public void setRmssd(String rmssd) {
		this.rmssd = rmssd;
	}

	public String getSd1() {
		return sd1;
	}

	public void setSd1(String sd1) {
		this.sd1 = sd1;
	}

	public String getSd2() {
		return sd2;
	}

	public void setSd2(String sd2) {
		this.sd2 = sd2;
	}

	public String getPrq() {
		return prq;
	}

	public void setPrq(String prq) {
		this.prq = prq;
	}

	public String getPnsIndex() {
		return pnsIndex;
	}

	public void setPnsIndex(String pnsIndex) {
		this.pnsIndex = pnsIndex;
	}

	public String getPnsZone() {
		return pnsZone;
	}

	public void setPnsZone(String pnsZone) {
		this.pnsZone = pnsZone;
	}

	public String getSnsIndex() {
		return snsIndex;
	}

	public void setSnsIndex(String snsIndex) {
		this.snsIndex = snsIndex;
	}

	public String getSnsZone() {
		return snsZone;
	}

	public void setSnsZone(String snsZone) {
		this.snsZone = snsZone;
	}

	public String getWellnessIndex() {
		return wellnessIndex;
	}

	public void setWellnessIndex(String wellnessIndex) {
		this.wellnessIndex = wellnessIndex;
	}

	public String getWellnessLevel() {
		return wellnessLevel;
	}

	public void setWellnessLevel(String wellnessLevel) {
		this.wellnessLevel = wellnessLevel;
	}

	public String getLfhf() {
		return lfhf;
	}

	public void setLfhf(String lfhf) {
		this.lfhf = lfhf;
	}

	public String getHemoglobin() {
		return hemoglobin;
	}

	public void setHemoglobin(String hemoglobin) {
		this.hemoglobin = hemoglobin;
	}

	public String getHemoglobinA1C() {
		return hemoglobinA1C;
	}

	public void setHemoglobinA1C(String hemoglobinA1C) {
		this.hemoglobinA1C = hemoglobinA1C;
	}

	public String getHighHemoglobinA1CRisk() {
		return highHemoglobinA1CRisk;
	}

	public void setHighHemoglobinA1CRisk(String highHemoglobinA1CRisk) {
		this.highHemoglobinA1CRisk = highHemoglobinA1CRisk;
	}

	public String getHighBloodPressureRisk() {
		return highBloodPressureRisk;
	}

	public void setHighBloodPressureRisk(String highBloodPressureRisk) {
		this.highBloodPressureRisk = highBloodPressureRisk;
	}

	public String getAscvdRisk() {
		return ascvdRisk;
	}

	public void setAscvdRisk(String ascvdRisk) {
		this.ascvdRisk = ascvdRisk;
	}

	public String getNormalizedStressIndex() {
		return normalizedStressIndex;
	}

	public void setNormalizedStressIndex(String normalizedStressIndex) {
		this.normalizedStressIndex = normalizedStressIndex;
	}

	public String getHeartAge() {
		return heartAge;
	}

	public void setHeartAge(String heartAge) {
		this.heartAge = heartAge;
	}

	public String getHighTotalCholesterolRisk() {
		return highTotalCholesterolRisk;
	}

	public void setHighTotalCholesterolRisk(String highTotalCholesterolRisk) {
		this.highTotalCholesterolRisk = highTotalCholesterolRisk;
	}

	public String getHighFastingGlucoseRisk() {
		return highFastingGlucoseRisk;
	}

	public void setHighFastingGlucoseRisk(String highFastingGlucoseRisk) {
		this.highFastingGlucoseRisk = highFastingGlucoseRisk;
	}

	public String getLowHemoglobinRisk() {
		return lowHemoglobinRisk;
	}

	public void setLowHemoglobinRisk(String lowHemoglobinRisk) {
		this.lowHemoglobinRisk = lowHemoglobinRisk;
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
	public EndUser getEndUser() {
		return endUser;
	}

	@Autowired
	public void setEndUser(EndUser endUser) {
		this.endUser = endUser;
	}

	@Override
	public String toString() {
		return "UserHealthBinahDetail [userHealthBinahDetailId=" + userHealthBinahDetailId + ", userEmail=" + userEmail
				+ ", pulseRate=" + pulseRate + ", respirationRate=" + respirationRate + ", oxygenSaturation="
				+ oxygenSaturation + ", sdnn=" + sdnn + ", stressLevel=" + stressLevel + ", bloodPressure="
				+ bloodPressure + ", stressIndex=" + stressIndex + ", meanRri=" + meanRri + ", rmssd=" + rmssd
				+ ", sd1=" + sd1 + ", sd2=" + sd2 + ", prq=" + prq + ", pnsIndex=" + pnsIndex + ", pnsZone=" + pnsZone
				+ ", snsIndex=" + snsIndex + ", snsZone=" + snsZone + ", wellnessIndex=" + wellnessIndex
				+ ", wellnessLevel=" + wellnessLevel + ", lfhf=" + lfhf + ", hemoglobin=" + hemoglobin
				+ ", hemoglobinA1C=" + hemoglobinA1C + ", highHemoglobinA1CRisk=" + highHemoglobinA1CRisk
				+ ", highBloodPressureRisk=" + highBloodPressureRisk + ", ascvdRisk=" + ascvdRisk
				+ ", normalizedStressIndex=" + normalizedStressIndex + ", heartAge=" + heartAge
				+ ", highTotalCholesterolRisk=" + highTotalCholesterolRisk + ", highFastingGlucoseRisk="
				+ highFastingGlucoseRisk + ", lowHemoglobinRisk=" + lowHemoglobinRisk + ", registDate=" + registDate
				+ ", registPName=" + registPName + ", lastUpdateDate=" + lastUpdateDate + ", lastUpdatePName="
				+ lastUpdatePName + ", endUser=" + endUser + "]";
	}

}
