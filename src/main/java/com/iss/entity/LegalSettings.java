package com.iss.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Legal_Setting")
public class LegalSettings {

	@Id
	@Column(name = "legal_id", unique = true, length = 255)
	private String legalId;

	@Column(name = "content", columnDefinition = "MEDIUMTEXT", nullable = true)
	private String content;

	@Column(name = "is_Agree", columnDefinition = "TEXT")
	private String isAgree;

	@Column(name = "mod_person", columnDefinition = "TEXT")
	private String lastModifiedPerson;

	@Column(name = "regist_date", columnDefinition = "TEXT")
	private String registDate;

	@Column(name = "regist_pname", columnDefinition = "TEXT")
	private String registPName;

	@Column(name = "last_update_date", columnDefinition = "TEXT")
	private String lastUpdateDate;

	@Column(name = "last_update_pname", columnDefinition = "TEXT")
	private String lastUpdatePName;

	public LegalSettings() {
		super();
	}

	public LegalSettings(String legalId, String content, String isAgree, String lastModifiedPerson, String registDate,
			String registPName, String lastUpdateDate, String lastUpdatePName) {
		super();
		this.legalId = legalId;
		this.content = content;
		this.isAgree = isAgree;
		this.lastModifiedPerson = lastModifiedPerson;
		this.registDate = registDate;
		this.registPName = registPName;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdatePName = lastUpdatePName;
	}

	public String getLegalId() {
		return legalId;
	}

	public void setLegalId(String legalId) {
		this.legalId = legalId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIsAgree() {
		return isAgree;
	}

	public void setIsAgree(String isAgree) {
		this.isAgree = isAgree;
	}

	public String getLastModifiedPerson() {
		return lastModifiedPerson;
	}

	public void setLastModifiedPerson(String lastModifiedPerson) {
		this.lastModifiedPerson = lastModifiedPerson;
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

	@Override
	public String toString() {
		return "LegalSettings [legalId=" + legalId + ", content=" + content + ", isAgree=" + isAgree
				+ ", lastModifiedPerson=" + lastModifiedPerson + ", registDate=" + registDate + ", registPName="
				+ registPName + ", lastUpdateDate=" + lastUpdateDate + ", lastUpdatePName=" + lastUpdatePName + "]";
	}

}
