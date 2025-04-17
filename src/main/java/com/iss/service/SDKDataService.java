package com.iss.service;

import org.springframework.stereotype.Service;

import com.iss.dao.ResponseDao;
import com.iss.dao.UserHealthDetailsDao;

@Service
public interface SDKDataService {
	
	public ResponseDao saveUserSDKData(String userId,UserHealthDetailsDao dao, String dateTimeFormat);

}
