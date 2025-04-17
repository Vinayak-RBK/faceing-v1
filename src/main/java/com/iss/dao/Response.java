package com.iss.dao;

public class Response {
	
	private boolean success;
	private String msg;
	private String blockStatus;
	
	public Response() {
		super();
	}

	public Response(boolean success, String msg, String blockStatus) {
		super();
		this.success = success;
		this.msg = msg;
		this.blockStatus = blockStatus;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getBlockStatus() {
		return blockStatus;
	}

	public void setBlockStatus(String blockStatus) {
		this.blockStatus = blockStatus;
	}

	@Override
	public String toString() {
		return "Response [success=" + success + ", msg=" + msg + ", blockStatus=" + blockStatus + "]";
	}

}
