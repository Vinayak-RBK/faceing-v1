package com.iss.dao;

public class LegalSettingsDao {
	private String legalId;
	private String content;

	public LegalSettingsDao() {
		super();
	}

	public LegalSettingsDao(String legalId, String content) {
		super();
		this.legalId = legalId;
		this.content = content;
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

	@Override
	public String toString() {
		return "LegalSettingsDao [legalId=" + legalId + ", content=" + content + "]";
	}

}
