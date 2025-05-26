package com.iss.dao;

public class Response {
	
	private String success;
	private String msg;
	private String blockStatus;
	private String userId;
	
	public Response() {
		super();
	}

	public Response(String success, String msg, String blockStatus, String userId) {
		super();
		this.success = success;
		this.msg = msg;
		this.blockStatus = blockStatus;
		this.userId = userId;
	}

	public String isSuccess() {
		return success;
	}

	public void setSuccess(String success) {
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "Response [success=" + success + ", msg=" + msg + ", blockStatus=" + blockStatus + ", userId=" + userId
				+ "]";
	}

}
