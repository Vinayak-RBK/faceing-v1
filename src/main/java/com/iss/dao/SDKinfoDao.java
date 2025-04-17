package com.iss.dao;

public class SDKinfoDao {
	
	private String primaryId;
	private String sdkId;
	private String sdkName;
	
	public SDKinfoDao() {
		super();
	}

	public SDKinfoDao(String primaryId, String sdkId, String sdkName) {
		super();
		this.primaryId = primaryId;
		this.sdkId = sdkId;
		this.sdkName = sdkName;
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

	@Override
	public String toString() {
		return "SDKinfoDao [primaryId=" + primaryId + ", sdkId=" + sdkId + ", sdkName=" + sdkName + "]";
	}
}
