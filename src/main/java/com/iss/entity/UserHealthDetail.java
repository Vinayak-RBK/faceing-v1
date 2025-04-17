package com.iss.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "user_health_detail")
public class UserHealthDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userHealthDetailSeqGen")
	@SequenceGenerator(name = "userHealthDetailSeqGen", sequenceName = "user_health_details_sequence", initialValue = 1000000000, allocationSize = 1)
	@Column(name = "user_health_detail_id", length = 10, unique = true)
	private Long userHealthDetailId;

	@Column(name = "age")
	private int age;

	@Column(name = "gender")
	private String gender;

	@Column(name = "height")
	private BigDecimal height;

	@Column(name = "waist_circum")
	private BigDecimal waistCircum;

	@Column(name = "bmi_calc")
	private BigDecimal bMICalc;

	@Column(name = "absi")
	private BigDecimal aBSI;

	@Column(name = "hr_bpm")
	private BigDecimal hRBPM;

	@Column(name = "bp_systolic")
	private BigDecimal bPSystolic;

	@Column(name = "hrv_sdnn")
	private BigDecimal hRVSDNN;

	@Column(name = "bp_rpp")
	private BigDecimal bPRPP;

	@Column(name = "bp_tau")
	private BigDecimal bPTau;

	@Column(name = "bp_bpm")
	private BigDecimal bPBPM;

	@Column(name = "tHBCount")
	private BigDecimal tHBCount;

	@Column(name = "health_score")
	private BigDecimal healthScore;

	@Column(name = "mental_score")
	private BigDecimal mentalScore;

	@Column(name = "vital_score")
	private BigDecimal vitalScore;

	@Column(name = "pgysical_score")
	private BigDecimal physicalScore;

	@Column(name = "msi")
	private BigDecimal mSI;

	@Column(name = "bp_heart_attack")
	private BigDecimal bpHeartAttack;

	@Column(name = "bp_stroke")
	private BigDecimal bPStroke;

	@Column(name = "bp_cvd")
	private BigDecimal bPCVD;

	@Column(name = "risk_score")
	private BigDecimal risksScore;

	@Column(name = "snr")
	private BigDecimal sNR;

	@Column(name = "regist_date")
	private String registDate;

	@Column(name = "regist_pname")
	private String registPName;

	@Column(name = "last_update_date")
	private String lastUpdateDate;

	@Column(name = "last_update_pname")
	private String lastUpdatePName;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_personal_detail_id", referencedColumnName = "user_personal_detail_id")
	private UsersPersonalDetails userPersonalDetails;

	// Default constructor
	public UserHealthDetail() {
		super();
	}

	public UserHealthDetail(Long userHealthDetailId, int age, String gender, BigDecimal height, BigDecimal waistCircum,
			BigDecimal bMICalc, BigDecimal aBSI, BigDecimal hRBPM, BigDecimal bPSystolic, BigDecimal hRVSDNN,
			BigDecimal bPRPP, BigDecimal bPTau, BigDecimal bPBPM, BigDecimal tHBCount, BigDecimal healthScore,
			BigDecimal mentalScore, BigDecimal vitalScore, BigDecimal physicalScore, BigDecimal mSI,
			BigDecimal bpHeartAttack, BigDecimal bPStroke, BigDecimal bPCVD, BigDecimal risksScore, BigDecimal sNR,
			String registDate, String registPName, String lastUpdateDate, String lastUpdatePName,
			UsersPersonalDetails userPersonalDetails) {
		super();
		this.userHealthDetailId = userHealthDetailId;
		this.age = age;
		this.gender = gender;
		this.height = height;
		this.waistCircum = waistCircum;
		this.bMICalc = bMICalc;
		this.aBSI = aBSI;
		this.hRBPM = hRBPM;
		this.bPSystolic = bPSystolic;
		this.hRVSDNN = hRVSDNN;
		this.bPRPP = bPRPP;
		this.bPTau = bPTau;
		this.bPBPM = bPBPM;
		this.tHBCount = tHBCount;
		this.healthScore = healthScore;
		this.mentalScore = mentalScore;
		this.vitalScore = vitalScore;
		this.physicalScore = physicalScore;
		this.mSI = mSI;
		this.bpHeartAttack = bpHeartAttack;
		this.bPStroke = bPStroke;
		this.bPCVD = bPCVD;
		this.risksScore = risksScore;
		this.sNR = sNR;
		this.registDate = registDate;
		this.registPName = registPName;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdatePName = lastUpdatePName;
		this.userPersonalDetails = userPersonalDetails;
	}

	public Long getUserHealthDetailId() {
		return userHealthDetailId;
	}

	public void setUserHealthDetailId(Long userHealthDetailId) {
		this.userHealthDetailId = userHealthDetailId;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public BigDecimal getWaistCircum() {
		return waistCircum;
	}

	public void setWaistCircum(BigDecimal waistCircum) {
		this.waistCircum = waistCircum;
	}

	public BigDecimal getbMICalc() {
		return bMICalc;
	}

	public void setbMICalc(BigDecimal bMICalc) {
		this.bMICalc = bMICalc;
	}

	public BigDecimal getaBSI() {
		return aBSI;
	}

	public void setaBSI(BigDecimal aBSI) {
		this.aBSI = aBSI;
	}

	public BigDecimal gethRBPM() {
		return hRBPM;
	}

	public void sethRBPM(BigDecimal hRBPM) {
		this.hRBPM = hRBPM;
	}

	public BigDecimal getbPSystolic() {
		return bPSystolic;
	}

	public void setbPSystolic(BigDecimal bPSystolic) {
		this.bPSystolic = bPSystolic;
	}

	public BigDecimal gethRVSDNN() {
		return hRVSDNN;
	}

	public void sethRVSDNN(BigDecimal hRVSDNN) {
		this.hRVSDNN = hRVSDNN;
	}

	public BigDecimal getbPRPP() {
		return bPRPP;
	}

	public void setbPRPP(BigDecimal bPRPP) {
		this.bPRPP = bPRPP;
	}

	public BigDecimal getbPTau() {
		return bPTau;
	}

	public void setbPTau(BigDecimal bPTau) {
		this.bPTau = bPTau;
	}

	public BigDecimal getbPBPM() {
		return bPBPM;
	}

	public void setbPBPM(BigDecimal bPBPM) {
		this.bPBPM = bPBPM;
	}

	public BigDecimal gettHBCount() {
		return tHBCount;
	}

	public void settHBCount(BigDecimal tHBCount) {
		this.tHBCount = tHBCount;
	}

	public BigDecimal getHealthScore() {
		return healthScore;
	}

	public void setHealthScore(BigDecimal healthScore) {
		this.healthScore = healthScore;
	}

	public BigDecimal getMentalScore() {
		return mentalScore;
	}

	public void setMentalScore(BigDecimal mentalScore) {
		this.mentalScore = mentalScore;
	}

	public BigDecimal getVitalScore() {
		return vitalScore;
	}

	public void setVitalScore(BigDecimal vitalScore) {
		this.vitalScore = vitalScore;
	}

	public BigDecimal getPhysicalScore() {
		return physicalScore;
	}

	public void setPhysicalScore(BigDecimal physicalScore) {
		this.physicalScore = physicalScore;
	}

	public BigDecimal getmSI() {
		return mSI;
	}

	public void setmSI(BigDecimal mSI) {
		this.mSI = mSI;
	}

	public BigDecimal getBpHeartAttack() {
		return bpHeartAttack;
	}

	public void setBpHeartAttack(BigDecimal bpHeartAttack) {
		this.bpHeartAttack = bpHeartAttack;
	}

	public BigDecimal getbPStroke() {
		return bPStroke;
	}

	public void setbPStroke(BigDecimal bPStroke) {
		this.bPStroke = bPStroke;
	}

	public BigDecimal getbPCVD() {
		return bPCVD;
	}

	public void setbPCVD(BigDecimal bPCVD) {
		this.bPCVD = bPCVD;
	}

	public BigDecimal getRisksScore() {
		return risksScore;
	}

	public void setRisksScore(BigDecimal risksScore) {
		this.risksScore = risksScore;
	}

	public BigDecimal getsNR() {
		return sNR;
	}

	public void setsNR(BigDecimal sNR) {
		this.sNR = sNR;
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

	public UsersPersonalDetails getUserPersonalDetails() {
		return userPersonalDetails;
	}

	public void setUserPersonalDetails(UsersPersonalDetails userPersonalDetails) {
		this.userPersonalDetails = userPersonalDetails;
	}

	@Override
	public String toString() {
		return "UserHealthDetail [userHealthDetailId=" + userHealthDetailId + ", age=" + age + ", gender=" + gender
				+ ", height=" + height + ", waistCircum=" + waistCircum + ", bMICalc=" + bMICalc + ", aBSI=" + aBSI
				+ ", hRBPM=" + hRBPM + ", bPSystolic=" + bPSystolic + ", hRVSDNN=" + hRVSDNN + ", bPRPP=" + bPRPP
				+ ", bPTau=" + bPTau + ", bPBPM=" + bPBPM + ", tHBCount=" + tHBCount + ", healthScore=" + healthScore
				+ ", mentalScore=" + mentalScore + ", vitalScore=" + vitalScore + ", physicalScore=" + physicalScore
				+ ", mSI=" + mSI + ", bpHeartAttack=" + bpHeartAttack + ", bPStroke=" + bPStroke + ", bPCVD=" + bPCVD
				+ ", risksScore=" + risksScore + ", sNR=" + sNR + ", registDate=" + registDate + ", registPName="
				+ registPName + ", lastUpdateDate=" + lastUpdateDate + ", lastUpdatePName=" + lastUpdatePName
				+ ", userPersonalDetails=" + userPersonalDetails + "]";
	}

}
