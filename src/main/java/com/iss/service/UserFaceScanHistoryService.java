package com.iss.service;

import org.springframework.stereotype.Service;

import com.iss.dao.UserHealthDetailsResponseDao;

@Service
public interface UserFaceScanHistoryService {
	
	public UserHealthDetailsResponseDao getUserAllFaceScanHistoryService(String userId);
	
	public UserHealthDetailsResponseDao getUserFaceScanHistoryByScanId(String userId, String healthScanId);

}
