package com.iss.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class TemporaryCacheService {

	 private final Map<String, String> cache = new ConcurrentHashMap<>();

	    public void put(String key, String value) {
	        cache.put(key, value);
	    }

	    public String get(String key) {
	        return cache.get(key);
	    }

	    public void remove(String key) {
	        cache.remove(key);
	    }
	
}
