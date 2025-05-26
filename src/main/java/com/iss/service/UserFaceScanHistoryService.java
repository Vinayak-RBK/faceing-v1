package com.iss.service;

import org.springframework.stereotype.Service;

import com.iss.dao.UserHealthDetailsResponseDao;
import com.iss.dao.UserHealthRequestDao;

@Service
public interface UserFaceScanHistoryService {
	
	public UserHealthDetailsResponseDao getUserAllFaceScanHistoryService(String userId);
	
	public UserHealthDetailsResponseDao getUserFaceScanHistoryByScanId(String userId, String healthScanId,boolean isGetByUserIdOnly);
	
	public UserHealthDetailsResponseDao getUserHealthHistoryList(UserHealthRequestDao reqDao);

}
