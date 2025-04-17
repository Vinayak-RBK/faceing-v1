package com.iss.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "SDK_TABLE")
public class SDKTable {

	@Id
	@Column(name = "PRIMARY_ID", length = 50, nullable = false)
	private String primaryId;
	
	@Column(name = "SDK_ID", length = 50, nullable = false)
	private String sdkId;
	
	@Column(name = "SDK_NAME", length = 50, nullable = false)
	private String sdkName;
	
	@Column(name = "regist_date", length = 20, nullable = true)
	private String registDate;

	@Column(name = "regist_pname", length = 50)
	private String registPName;

	@Column(name = "last_update_date", length = 20, nullable = true)
	private String lastUpdateDate;

	@Column(name = "last_update_pname", length = 50)
	private String lastUpdatePName;
	
	public SDKTable() {
		super();
	}

	public SDKTable(String primaryId, String sdkId, String sdkName, String registDate, String registPName,
			String lastUpdateDate, String lastUpdatePName) {
		super();
		this.primaryId = primaryId;
		this.sdkId = sdkId;
		this.sdkName = sdkName;
		this.registDate = registDate;
		this.registPName = registPName;
		this.lastUpdateDate = lastUpdateDate;
		this.lastUpdatePName = lastUpdatePName;
	}

	public String getPrimaryId() {
		return primaryId;
	}

	public void setPrimaryId(String primaryId) {
		this.primaryId = primaryId;
	}

	public String getSdkId() {
		return sdkId;
	}

	public void setSdkId(String sdkId) {
		this.sdkId = sdkId;
	}

	public String getSdkName() {
		return sdkName;
	}

	public void setSdkName(String sdkName) {
		this.sdkName = sdkName;
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
		return "SDKTable [primaryId=" + primaryId + ", sdkId=" + sdkId + ", sdkName=" + sdkName + ", registDate="
				+ registDate + ", registPName=" + registPName + ", lastUpdateDate=" + lastUpdateDate
				+ ", lastUpdatePName=" + lastUpdatePName + "]";
	}
	
}
