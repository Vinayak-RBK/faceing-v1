package com.iss.dao;

import java.math.BigDecimal;

public class UserHealthDetailsDao {

	private int age;
	private String gender;
	private BigDecimal height;
	private BigDecimal waistCircum;
	private BigDecimal bMICalc;
	private BigDecimal aBSI;
	private BigDecimal hRBPM;
	private BigDecimal bPSystolic;
	private BigDecimal hRVSDNN;
	private BigDecimal bPRPP;
	private BigDecimal bPTau;
	private BigDecimal bPBPM;
	private BigDecimal tHBCount;
	private BigDecimal healthScore;
	private BigDecimal mentalScore;
	private BigDecimal vitalScore;
	private BigDecimal physicalScore;
	private BigDecimal mSI;
	private BigDecimal bpHeartAttack;
	private BigDecimal bPStroke;
	private BigDecimal bPCVD;
	private BigDecimal risksScore;
	private BigDecimal sNR;

	public UserHealthDetailsDao() {
		super();
	}

	public UserHealthDetailsDao(int age, String gender, BigDecimal height, BigDecimal waistCircum, BigDecimal bMICalc,
			BigDecimal aBSI, BigDecimal hRBPM, BigDecimal bPSystolic, BigDecimal hRVSDNN, BigDecimal bPRPP,
			BigDecimal bPTau, BigDecimal bPBPM, BigDecimal tHBCount, BigDecimal healthScore, BigDecimal mentalScore,
			BigDecimal vitalScore, BigDecimal physicalScore, BigDecimal mSI, BigDecimal bpHeartAttack,
			BigDecimal bPStroke, BigDecimal bPCVD, BigDecimal risksScore, BigDecimal sNR) {
		super();
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

	@Override
	public String toString() {
		return "UserHealthDetailsDao [age=" + age + ", gender=" + gender + ", height=" + height + ", waistCircum="
				+ waistCircum + ", bMICalc=" + bMICalc + ", aBSI=" + aBSI + ", hRBPM=" + hRBPM + ", bPSystolic="
				+ bPSystolic + ", hRVSDNN=" + hRVSDNN + ", bPRPP=" + bPRPP + ", bPTau=" + bPTau + ", bPBPM=" + bPBPM
				+ ", tHBCount=" + tHBCount + ", healthScore=" + healthScore + ", mentalScore=" + mentalScore
				+ ", vitalScore=" + vitalScore + ", physicalScore=" + physicalScore + ", mSI=" + mSI
				+ ", bpHeartAttack=" + bpHeartAttack + ", bPStroke=" + bPStroke + ", bPCVD=" + bPCVD + ", risksScore="
				+ risksScore + ", sNR=" + sNR + "]";
	}
}

