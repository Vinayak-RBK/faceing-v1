package com.iss.service;

public interface TokenBlacklistService {

    public void blacklistToken(String token); 

    public boolean isTokenBlacklisted(String token);
	
}
