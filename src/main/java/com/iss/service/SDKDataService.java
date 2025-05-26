package com.iss.service;

import org.springframework.stereotype.Service;

import com.iss.dao.ResponseDao;
import com.iss.dao.UserHealthAnuraDetailsDao;
import com.iss.dao.UserHealthInfoDao;

@Service
public interface SDKDataService {
	
	public ResponseDao saveUserSDKData(UserHealthInfoDao dao, String dateTimeFormat);

}
