package com.iss.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_personal_health_details")
public class UserPersonalAndHealthDetails {

	@Id
	@Column(name = "user_id", unique = true, length = 255)
	private String userId;

	@Column(name = "user_email_id",unique = true, columnDefinition = "TEXT")
	private String userEmail;

	@Column(name = "user_password",  columnDefinition = "TEXT")
	private String userPassword;

	@Column(name = "user_name", columnDefinition = "TEXT")
	private String userName;

	@Column(name = "user_gender", columnDefinition = "TEXT")
	private String userGender;

	@Column(name = "user_dob", columnDefinition = "TEXT")
	private String userDOB;

	@Column(name = "user_weight", columnDefinition = "TEXT")
	private String userWeight;

	@Column(name = "user_height", columnDefinition = "TEXT")
	private String userHeight;

	@Column(name = "user_image", columnDefinition = "TEXT")
	private String userImage;
	
	@Column(name = "age",columnDefinition = "TEXT")
	private String age;

	@Column(name = "gender",columnDefinition = "TEXT")
	private String gender;

	@Column(name = "height",columnDefinition = "TEXT")
	private String height;

	@Column(name = "waist_circum",columnDefinition = "TEXT")
	private String waistCircum;

	@Column(name = "bmi_calc",columnDefinition = "TEXT")
	private String bMICalc;

	@Column(name = "absi",columnDefinition = "TEXT")
	private String aBSI;

	@Column(name = "hr_bpm",columnDefinition = "TEXT")
	private String hRBPM;

	@Column(name = "bp_systolic",columnDefinition = "TEXT")
	private String bPSystolic;

	@Column(name = "hrv_sdnn",columnDefinition = "TEXT")
	private String hRVSDNN;

	@Column(name = "bp_rpp",columnDefinition = "TEXT")
	private String bPRPP;

	@Column(name = "bp_tau",columnDefinition = "TEXT")
	private String bPTau;

	@Column(name = "bp_bpm",columnDefinition = "TEXT")
	private String bPBPM;

	@Column(name = "tHBCount",columnDefinition = "TEXT")
	private String tHBCount;

	@Column(name = "health_score",columnDefinition = "TEXT")
	private String healthScore;

	@Column(name = "mental_score",columnDefinition = "TEXT")
	private String mentalScore;

	@Column(name = "vital_score",columnDefinition = "TEXT")
	private String vitalScore;

	@Column(name = "physical_score",columnDefinition = "TEXT")
	private String physicalScore;

	@Column(name = "msi",columnDefinition = "TEXT")
	private String mSI;

	@Column(name = "bp_heart_attack",columnDefinition = "TEXT")
	private String bpHeartAttack;

	@Column(name = "bp_stroke",columnDefinition = "TEXT")
	private String bPStroke;

	@Column(name = "bp_cvd",columnDefinition = "TEXT")
	private String bPCVD;

	@Column(name = "risk_score",columnDefinition = "TEXT")
	private String risksScore;

	@Column(name = "snr",columnDefinition = "TEXT")
	private String sNR;
	
	public UserPersonalAndHealthDetails() {
		super();
	}

	public UserPersonalAndHealthDetails(String userId, String userEmail, String userPassword, String userName,
			String userGender, String userDOB, String userWeight, String userHeight, String userImage, String age,
			String gender, String height, String waistCircum, String bMICalc, String aBSI, String hRBPM,
			String bPSystolic, String hRVSDNN, String bPRPP, String bPTau, String bPBPM, String tHBCount,
			String healthScore, String mentalScore, String vitalScore, String physicalScore, String mSI,
			String bpHeartAttack, String bPStroke, String bPCVD, String risksScore, String sNR) {
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

	public String getUserWeight() {
		return userWeight;
	}

	public void setUserWeight(String userWeight) {
		this.userWeight = userWeight;
	}

	public String getUserHeight() {
		return userHeight;
	}

	public void setUserHeight(String userHeight) {
		this.userHeight = userHeight;
	}

	public String getUserImage() {
		return userImage;
	}

	public void setUserImage(String userImage) {
		this.userImage = userImage;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getWaistCircum() {
		return waistCircum;
	}

	public void setWaistCircum(String waistCircum) {
		this.waistCircum = waistCircum;
	}

	public String getbMICalc() {
		return bMICalc;
	}

	public void setbMICalc(String bMICalc) {
		this.bMICalc = bMICalc;
	}

	public String getaBSI() {
		return aBSI;
	}

	public void setaBSI(String aBSI) {
		this.aBSI = aBSI;
	}

	public String gethRBPM() {
		return hRBPM;
	}

	public void sethRBPM(String hRBPM) {
		this.hRBPM = hRBPM;
	}

	public String getbPSystolic() {
		return bPSystolic;
	}

	public void setbPSystolic(String bPSystolic) {
		this.bPSystolic = bPSystolic;
	}

	public String gethRVSDNN() {
		return hRVSDNN;
	}

	public void sethRVSDNN(String hRVSDNN) {
		this.hRVSDNN = hRVSDNN;
	}

	public String getbPRPP() {
		return bPRPP;
	}

	public void setbPRPP(String bPRPP) {
		this.bPRPP = bPRPP;
	}

	public String getbPTau() {
		return bPTau;
	}

	public void setbPTau(String bPTau) {
		this.bPTau = bPTau;
	}

	public String getbPBPM() {
		return bPBPM;
	}

	public void setbPBPM(String bPBPM) {
		this.bPBPM = bPBPM;
	}

	public String gettHBCount() {
		return tHBCount;
	}

	public void settHBCount(String tHBCount) {
		this.tHBCount = tHBCount;
	}

	public String getHealthScore() {
		return healthScore;
	}

	public void setHealthScore(String healthScore) {
		this.healthScore = healthScore;
	}

	public String getMentalScore() {
		return mentalScore;
	}

	public void setMentalScore(String mentalScore) {
		this.mentalScore = mentalScore;
	}

	public String getVitalScore() {
		return vitalScore;
	}

	public void setVitalScore(String vitalScore) {
		this.vitalScore = vitalScore;
	}

	public String getPhysicalScore() {
		return physicalScore;
	}

	public void setPhysicalScore(String physicalScore) {
		this.physicalScore = physicalScore;
	}

	public String getmSI() {
		return mSI;
	}

	public void setmSI(String mSI) {
		this.mSI = mSI;
	}

	public String getBpHeartAttack() {
		return bpHeartAttack;
	}

	public void setBpHeartAttack(String bpHeartAttack) {
		this.bpHeartAttack = bpHeartAttack;
	}

	public String getbPStroke() {
		return bPStroke;
	}

	public void setbPStroke(String bPStroke) {
		this.bPStroke = bPStroke;
	}

	public String getbPCVD() {
		return bPCVD;
	}

	public void setbPCVD(String bPCVD) {
		this.bPCVD = bPCVD;
	}

	public String getRisksScore() {
		return risksScore;
	}

	public void setRisksScore(String risksScore) {
		this.risksScore = risksScore;
	}

	public String getsNR() {
		return sNR;
	}

	public void setsNR(String sNR) {
		this.sNR = sNR;
	}

	@Override
	public String toString() {
		return "UserPersonalAndHealthDetails [userId=" + userId + ", userEmail=" + userEmail + ", userPassword="
				+ userPassword + ", userName=" + userName + ", userGender=" + userGender + ", userDOB=" + userDOB
				+ ", userWeight=" + userWeight + ", userHeight=" + userHeight + ", userImage=" + userImage + ", age="
				+ age + ", gender=" + gender + ", height=" + height + ", waistCircum=" + waistCircum + ", bMICalc="
				+ bMICalc + ", aBSI=" + aBSI + ", hRBPM=" + hRBPM + ", bPSystolic=" + bPSystolic + ", hRVSDNN="
				+ hRVSDNN + ", bPRPP=" + bPRPP + ", bPTau=" + bPTau + ", bPBPM=" + bPBPM + ", tHBCount=" + tHBCount
				+ ", healthScore=" + healthScore + ", mentalScore=" + mentalScore + ", vitalScore=" + vitalScore
				+ ", physicalScore=" + physicalScore + ", mSI=" + mSI + ", bpHeartAttack=" + bpHeartAttack
				+ ", bPStroke=" + bPStroke + ", bPCVD=" + bPCVD + ", risksScore=" + risksScore + ", sNR=" + sNR + "]";
	}
}
