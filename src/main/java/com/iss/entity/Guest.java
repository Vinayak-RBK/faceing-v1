package com.iss.entity;

import org.springframework.beans.factory.annotation.Autowired;

import jakarta.persistence.*;

@Entity
@Table(name = "Guests")
public class Guest {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guestSeqGen")
	@SequenceGenerator(name = "guestSeqGen", sequenceName = "guest_sequence", initialValue = 1000000000, allocationSize = 1)
	@Column(name = "guest_id", length = 10, unique = true)
	private Long guestId;

	public void setEndUser(EndUser endUser) {
		this.endUser = endUser;
	}

	@Column(name = "user_email_id", columnDefinition = "TEXT", unique = true)
	private String userEmail;

	@Column(name = "guest_name", columnDefinition = "TEXT")
	private String guestName;

	@Column(name = "guest_gender", columnDefinition = "TEXT")
	private String guestGender;

	@Column(name = "guest_dob", columnDefinition = "TEXT")
	private String guestDOB;

	@Column(name = "guest_weight", columnDefinition = "TEXT")
	private String guestWeight;

	@Column(name = "guest_height", columnDefinition = "TEXT")
	private String guestHeight;

	@Column(name = "guest_image", columnDefinition = "TEXT")
	private String guestImage;

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

	@Column(name = "is_delete", columnDefinition = "TEXT")
	private String isdelete;

	public Guest() {
		super();
	}

	public Guest(Long guestId, String userEmail, String guestName, String guestGender, String guestDOB,
			String guestWeight, String guestHeight, String guestImage, String registDate, String registPName,
			String lastUpdateDate, String lastUpdatePName, EndUser endUser, String isdelete) {
		super();
		this.guestId = guestId;
		this.userEmail = userEmail;
		this.guestName = guestName;
		this.guestGender = guestGender;
		this.guestDOB = guestDOB;
		this.guestWeight = guestWeight;
		this.guestHeight = guestHeight;
		this.guestImage = guestImage;
		this.registDate = registDate;
		this.registPName = registPName;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdatePName = lastUpdatePName;
		this.endUser = endUser;
		this.isdelete = isdelete;
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

	public String getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(String isdelete) {
		this.isdelete = isdelete;
	}

	@Autowired
	public EndUser getEndUser() {
		return endUser;
	}

	@Autowired
	public void setUser(EndUser endUser) {
		this.endUser = endUser;
	}

	@Override
	public String toString() {
		return "Guest [guestId=" + guestId + ", userEmail=" + userEmail + ", guestName=" + guestName + ", guestGender="
				+ guestGender + ", guestDOB=" + guestDOB + ", guestWeight=" + guestWeight + ", guestHeight="
				+ guestHeight + ", guestImage=" + guestImage + ", registDate=" + registDate + ", registPName="
				+ registPName + ", lastUpdateDate=" + lastUpdateDate + ", lastUpdatePName=" + lastUpdatePName
				+ ", endUser=" + endUser + ", isdelete=" + isdelete + "]";
	}

}
