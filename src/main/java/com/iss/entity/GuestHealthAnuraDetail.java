package com.iss.entity;

import jakarta.persistence.*;

import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "Guest_Health_Anura_Detail")
public class GuestHealthAnuraDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guestHealthAnuraDetailSeqGen")
	@SequenceGenerator(name = "guestHealthAnuraDetailSeqGen", sequenceName = "guest_health_Anura_detail_sequence", initialValue = 1000000000, allocationSize = 1)
	@Column(name = "guest_health_Anura_detail_id", length = 10, unique = true)
	private Long guestHealthAnuraDetailId;

	@Column(name = "user_email_id", nullable = true, columnDefinition = "TEXT")
	private String userEmail;

	@Column(name = "age", columnDefinition = "TEXT")
	private String age;

	@Column(name = "hr_bpm", columnDefinition = "TEXT")
	private String hRBPM;

	@Column(name = "bp_systolic", columnDefinition = "TEXT")
	private String bPSystolic;

	@Column(name = "hrv_sdnn", columnDefinition = "TEXT")
	private String hRVSDNN;

	@Column(name = "bp_rpp", columnDefinition = "TEXT")
	private String bPRPP;

	@Column(name = "bp_tau", columnDefinition = "TEXT")
	private String bPTau;

	@Column(name = "health_score", columnDefinition = "TEXT")
	private String healthScore;

	@Column(name = "mental_score", columnDefinition = "TEXT")
	private String mentalScore;

	@Column(name = "vital_score", columnDefinition = "TEXT")
	private String vitalScore;

	@Column(name = "pgysical_score", columnDefinition = "TEXT")
	private String physicalScore;

	@Column(name = "msi", columnDefinition = "TEXT")
	private String mSI;

	@Column(name = "bp_heart_attack", columnDefinition = "TEXT")
	private String bpHeartAttack;

	@Column(name = "bp_stroke", columnDefinition = "TEXT")
	private String bPStroke;

	@Column(name = "bp_cvd", columnDefinition = "TEXT")
	private String bPCVD;

	@Column(name = "risk_score", columnDefinition = "TEXT")
	private String risksScore;

	@Column(name = "snr", columnDefinition = "TEXT")
	private String sNR;

	@Column(name = "br_bpm", columnDefinition = "TEXT")
	private String bRBPM;

	@Column(name = "bp_diastolic", columnDefinition = "TEXT")
	private String bpDiastolic;

	@Column(name = "ihb_count", columnDefinition = "TEXT")
	private String iHBCount;

	@Column(name = "hba1c_risk_prob", columnDefinition = "TEXT")
	private String hBA1CRiskProb;

	@Column(name = "mfbg_risk_prob", columnDefinition = "TEXT")
	private String mFBGRiskProb;

	@Column(name = "dbt_risk_prob", columnDefinition = "TEXT")
	private String dBTRiskProb;

	@Column(name = "fldr_risk_prob", columnDefinition = "TEXT")
	private String fLDRiskProb;

	@Column(name = "hdltc_risk_prob", columnDefinition = "TEXT")
	private String hDLTCRiskProb;

	@Column(name = "hpt_risk_prob", columnDefinition = "TEXT")
	private String hPTRiskProb;

	@Column(name = "overall_metabolic_risk_prob", columnDefinition = "TEXT")
	private String overallMetabolicRiskProb;

	@Column(name = "tg_risk_prob", columnDefinition = "TEXT")
	private String tGRiskProb;

	@Column(name = "physio_score", columnDefinition = "TEXT")
	private String physioScore;

	@Column(name = "regist_date", columnDefinition = "TEXT")
	private String registDate;

	@Column(name = "regist_pname", columnDefinition = "TEXT")
	private String registPName;

	@Column(name = "last_update_date", columnDefinition = "TEXT")
	private String lastUpdateDate;

	@Column(name = "last_update_pname", columnDefinition = "TEXT")
	private String lastUpdatePName;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "guest_id", referencedColumnName = "guest_id")
	private Guest guest;

	public GuestHealthAnuraDetail() {
		super();
	}

	public GuestHealthAnuraDetail(Long guestHealthAnuraDetailId, String userEmail, String age, String hRBPM,
			String bPSystolic, String hRVSDNN, String bPRPP, String bPTau, String healthScore, String mentalScore,
			String vitalScore, String physicalScore, String mSI, String bpHeartAttack, String bPStroke, String bPCVD,
			String risksScore, String sNR, String bRBPM, String bpDiastolic, String iHBCount, String hBA1CRiskProb,
			String mFBGRiskProb, String dBTRiskProb, String fLDRiskProb, String hDLTCRiskProb, String hPTRiskProb,
			String overallMetabolicRiskProb, String tGRiskProb, String physioScore, String registDate,
			String registPName, String lastUpdateDate, String lastUpdatePName, Guest guest) {
		super();
		this.guestHealthAnuraDetailId = guestHealthAnuraDetailId;
		this.userEmail = userEmail;
		this.age = age;
		this.hRBPM = hRBPM;
		this.bPSystolic = bPSystolic;
		this.hRVSDNN = hRVSDNN;
		this.bPRPP = bPRPP;
		this.bPTau = bPTau;
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
		this.bRBPM = bRBPM;
		this.bpDiastolic = bpDiastolic;
		this.iHBCount = iHBCount;
		this.hBA1CRiskProb = hBA1CRiskProb;
		this.mFBGRiskProb = mFBGRiskProb;
		this.dBTRiskProb = dBTRiskProb;
		this.fLDRiskProb = fLDRiskProb;
		this.hDLTCRiskProb = hDLTCRiskProb;
		this.hPTRiskProb = hPTRiskProb;
		this.overallMetabolicRiskProb = overallMetabolicRiskProb;
		this.tGRiskProb = tGRiskProb;
		this.physioScore = physioScore;
		this.registDate = registDate;
		this.registPName = registPName;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdatePName = lastUpdatePName;
		this.guest = guest;
	}

	public Long getGuestHealthAnuraDetailId() {
		return guestHealthAnuraDetailId;
	}

	public void setGuestHealthAnuraDetailId(Long guestHealthAnuraDetailId) {
		this.guestHealthAnuraDetailId = guestHealthAnuraDetailId;
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

	public String getbRBPM() {
		return bRBPM;
	}

	public void setbRBPM(String bRBPM) {
		this.bRBPM = bRBPM;
	}

	public String getBpDiastolic() {
		return bpDiastolic;
	}

	public void setBpDiastolic(String bpDiastolic) {
		this.bpDiastolic = bpDiastolic;
	}

	public String getiHBCount() {
		return iHBCount;
	}

	public void setiHBCount(String iHBCount) {
		this.iHBCount = iHBCount;
	}

	public String gethBA1CRiskProb() {
		return hBA1CRiskProb;
	}

	public void sethBA1CRiskProb(String hBA1CRiskProb) {
		this.hBA1CRiskProb = hBA1CRiskProb;
	}

	public String getmFBGRiskProb() {
		return mFBGRiskProb;
	}

	public void setmFBGRiskProb(String mFBGRiskProb) {
		this.mFBGRiskProb = mFBGRiskProb;
	}

	public String getdBTRiskProb() {
		return dBTRiskProb;
	}

	public void setdBTRiskProb(String dBTRiskProb) {
		this.dBTRiskProb = dBTRiskProb;
	}

	public String getfLDRiskProb() {
		return fLDRiskProb;
	}

	public void setfLDRiskProb(String fLDRiskProb) {
		this.fLDRiskProb = fLDRiskProb;
	}

	public String gethDLTCRiskProb() {
		return hDLTCRiskProb;
	}

	public void sethDLTCRiskProb(String hDLTCRiskProb) {
		this.hDLTCRiskProb = hDLTCRiskProb;
	}

	public String gethPTRiskProb() {
		return hPTRiskProb;
	}

	public void sethPTRiskProb(String hPTRiskProb) {
		this.hPTRiskProb = hPTRiskProb;
	}

	public String getOverallMetabolicRiskProb() {
		return overallMetabolicRiskProb;
	}

	public void setOverallMetabolicRiskProb(String overallMetabolicRiskProb) {
		this.overallMetabolicRiskProb = overallMetabolicRiskProb;
	}

	public String gettGRiskProb() {
		return tGRiskProb;
	}

	public void settGRiskProb(String tGRiskProb) {
		this.tGRiskProb = tGRiskProb;
	}

	public String getPhysioScore() {
		return physioScore;
	}

	public void setPhysioScore(String physioScore) {
		this.physioScore = physioScore;
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
		return "GuestHealthAnuraDetail [guestHealthAnuraDetailId=" + guestHealthAnuraDetailId + ", userEmail="
				+ userEmail + ", age=" + age + ", hRBPM=" + hRBPM + ", bPSystolic=" + bPSystolic + ", hRVSDNN="
				+ hRVSDNN + ", bPRPP=" + bPRPP + ", bPTau=" + bPTau + ", healthScore=" + healthScore + ", mentalScore="
				+ mentalScore + ", vitalScore=" + vitalScore + ", physicalScore=" + physicalScore + ", mSI=" + mSI
				+ ", bpHeartAttack=" + bpHeartAttack + ", bPStroke=" + bPStroke + ", bPCVD=" + bPCVD + ", risksScore="
				+ risksScore + ", sNR=" + sNR + ", bRBPM=" + bRBPM + ", bpDiastolic=" + bpDiastolic + ", iHBCount="
				+ iHBCount + ", hBA1CRiskProb=" + hBA1CRiskProb + ", mFBGRiskProb=" + mFBGRiskProb + ", dBTRiskProb="
				+ dBTRiskProb + ", fLDRiskProb=" + fLDRiskProb + ", hDLTCRiskProb=" + hDLTCRiskProb + ", hPTRiskProb="
				+ hPTRiskProb + ", overallMetabolicRiskProb=" + overallMetabolicRiskProb + ", tGRiskProb=" + tGRiskProb
				+ ", physioScore=" + physioScore + ", registDate=" + registDate + ", registPName=" + registPName
				+ ", lastUpdateDate=" + lastUpdateDate + ", lastUpdatePName=" + lastUpdatePName + ", guest=" + guest
				+ "]";
	}

}