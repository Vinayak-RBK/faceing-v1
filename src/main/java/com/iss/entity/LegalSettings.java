package com.iss.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Legal_Setting")
public class LegalSettings {

	@Id
	@Column(name = "legal_id", length = 10, unique = true)
	private String legalId;

	@Column(name = "content", columnDefinition = "MEDIUMTEXT", length = 20, nullable = true)
	private String content;

	@Column(name = "is_Agree", columnDefinition = "TINYINT(1)")
	private String isAgree;

	@Column(name = "mod_person", length = 20, nullable = true)
	private String lastModifiedPerson;

	@Column(name = "regist_date", length = 20, nullable = true)
	private String registDate;

	@Column(name = "regist_pname", length = 50)
	private String registPName;

	@Column(name = "last_update_date", length = 20, nullable = true)
	private String lastUpdateDate;

	@Column(name = "last_update_pname", length = 50)
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
