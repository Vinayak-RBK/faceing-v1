package com.iss.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iss.dao.ResponseDao;
import com.iss.dao.UserHealthDetailsDao;
import com.iss.service.SDKDataService;
import com.iss.util.CommonUtility;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class SDKController {
	
	@Value("${DATE_TIME_FORMAT}")
	private String dateTimeFormat;
	
	@Value("${SOMETHING_WENT_WRONG}")
	private String someThingWentWrong;
	
	@Autowired
	private SDKDataService sdkDataService;
	
	@Autowired
	private CommonUtility otpUtil;
	
	private static final Logger logger = LoggerFactory.getLogger(SDKController.class);
	
	@PostMapping("/userSDKData/{userId}")
	public ResponseEntity<?> saveUserSDKData(@RequestBody UserHealthDetailsDao dao, @PathVariable String userId)
	{
		Map<String, Object> respObj=new LinkedHashMap<String, Object>();
		ResponseDao resp=new ResponseDao();
		
		try {
		resp=sdkDataService.saveUserSDKData(userId,dao, dateTimeFormat);
		
		if(resp.isSuccess())
		{
			respObj.put("msg", resp.getMessage());
			respObj.put("success", true);
			
			// sending email
//			otpUtil.sendOtpToEmail(resp.getEmailId(),"Sent Health Data Successfully...");
			
			logger.info("While saving the user Health Data for user : {} - {}", resp.getEmailId(),resp.getMessage());
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
		}
		else
		{
			respObj.put("msg", resp.getMessage());
			respObj.put("success", false);
			logger.info("While saving the user Health Data for user : {} - {}", resp.getEmailId(),resp.getMessage());
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.NOT_FOUND);
		}
		}
		catch (Exception e) {
			respObj.put("msg", someThingWentWrong);
			respObj.put("success", false);
			logger.info("{} While saving the user Health Data for user - {}",someThingWentWrong,e);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}
		
	}

}
