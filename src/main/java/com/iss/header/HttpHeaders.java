package com.iss.header;

public class HttpHeaders {
	
	private String accessToken;
	private String refreshToken;
	private String userId;
	
	public HttpHeaders() {
		super();
	}

	public HttpHeaders(String accessToken, String refreshToken, String userId) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.userId = userId;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "HttpHeaders [accessToken=" + accessToken + ", refreshToken=" + refreshToken + ", userId=" + userId
				+ "]";
	}

}
