package com.iss.dao;

import java.util.List;

public class LegalSettingsRespDao {
	
	private String msg;
	private boolean success;
	private List<LegalSettingsDao> legalLists;
	
	public LegalSettingsRespDao() {
		super();
	}

	public LegalSettingsRespDao(String msg, boolean success, List<LegalSettingsDao> legalLists) {
		super();
		this.msg = msg;
		this.success = success;
		this.legalLists = legalLists;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public List<LegalSettingsDao> getLegalLists() {
		return legalLists;
	}

	public void setLegalLists(List<LegalSettingsDao> legalLists) {
		this.legalLists = legalLists;
	}

	@Override
	public String toString() {
		return "LegalSettingsRespDao [msg=" + msg + ", success=" + success + ", legalLists=" + legalLists + "]";
	}
}
