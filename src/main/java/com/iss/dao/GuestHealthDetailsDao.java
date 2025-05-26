package com.iss.dao;

public class GuestHealthDetailsDao {

	private String userEmail;
	private String age;
	private String gender;
	private String height;
	private String waistCircum;
	private String bMICalc;
	private String aBSI;
	private String hRBPM;
	private String bPSystolic;
	private String hRVSDNN;
	private String bPRPP;
	private String bPTau;
	private String bPBPM;
	private String tHBCount;
	private String healthScore;
	private String mentalScore;
	private String vitalScore;
	private String physicalScore;
	private String mSI;
	private String bpHeartAttack;
	private String bPStroke;
	private String bPCVD;
	private String risksScore;
	private String sNR;
	
	public GuestHealthDetailsDao() {
		super();
	}

	public GuestHealthDetailsDao(String userEmail, String age, String gender, String height, String waistCircum,
			String bMICalc, String aBSI, String hRBPM, String bPSystolic, String hRVSDNN,
			String bPRPP, String bPTau, String bPBPM, String tHBCount, String healthScore,
			String mentalScore, String vitalScore, String physicalScore, String mSI,
			String bpHeartAttack, String bPStroke, String bPCVD, String risksScore, String sNR) {
		super();
		this.userEmail = userEmail;
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

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
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
		return "GuestHealthDetailsDao [userEmail=" + userEmail + ", age=" + age + ", gender=" + gender + ", height="
				+ height + ", waistCircum=" + waistCircum + ", bMICalc=" + bMICalc + ", aBSI=" + aBSI + ", hRBPM="
				+ hRBPM + ", bPSystolic=" + bPSystolic + ", hRVSDNN=" + hRVSDNN + ", bPRPP=" + bPRPP + ", bPTau="
				+ bPTau + ", bPBPM=" + bPBPM + ", tHBCount=" + tHBCount + ", healthScore=" + healthScore
				+ ", mentalScore=" + mentalScore + ", vitalScore=" + vitalScore + ", physicalScore=" + physicalScore
				+ ", mSI=" + mSI + ", bpHeartAttack=" + bpHeartAttack + ", bPStroke=" + bPStroke + ", bPCVD=" + bPCVD
				+ ", risksScore=" + risksScore + ", sNR=" + sNR + "]";
	}

}
