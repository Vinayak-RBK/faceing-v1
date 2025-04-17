package com.iss.service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class TokenBlacklistServiceImpl implements TokenBlacklistService{

	private Set<String> blacklist = ConcurrentHashMap.newKeySet();

	@Override
    public void blacklistToken(String token) {
        blacklist.add(token);
    }

	@Override
    public boolean isTokenBlacklisted(String token) {
        return blacklist.contains(token);
    }
	
}
