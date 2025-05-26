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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iss.dao.ResponseDao;
import com.iss.dao.UserHealthInfoDao;
import com.iss.service.SDKDataService;
import com.iss.util.CommonUtility;
import com.iss.util.EncryptedDecryptedObjectUtil;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class SDKController {

	@Value("${DATE_TIME_FORMAT}")
	private String dateTimeFormat;

	@Value("${SOMETHING_WENT_WRONG}")
	private String someThingWentWrong;

	@Value("${SECRET_KEY}")
	private String secretKey;

	@Value("${SECRET_IV}")
	private String secretIv;

	@Value("${IS_ENCRYPT_DECRYPT_ENABLE_DATABASE_DATA}")
	private boolean isEncryptDecryptDatabaseData;

	@Value("${IS_ENCRYPT_DECRYPT_REQUEST_RESPONSE_DATA}")
	private boolean isEncryptDecryptReqRespData;

	@Autowired
	private SDKDataService sdkDataService;

	@Autowired
	private CommonUtility otpUtil;

	private static final Logger logger = LoggerFactory.getLogger(SDKController.class);

	@SuppressWarnings("unchecked")
	@PostMapping("/userSDKData")
	public ResponseEntity<?> saveUserSDKData(@RequestBody UserHealthInfoDao dao) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		UserHealthInfoDao enDao = new UserHealthInfoDao();
		ResponseDao resp = new ResponseDao();
//		boolean isEmailSent = false;

		try {

			enDao = (UserHealthInfoDao) EncryptedDecryptedObjectUtil.getDecryptedObject(dao, secretKey, secretIv,
					isEncryptDecryptReqRespData);

			resp = sdkDataService.saveUserSDKData(enDao, dateTimeFormat);

			if (Boolean.parseBoolean(resp.getIsSuccess())) {
				// sending email
//				isEmailSent = otpUtil.sendOtpToEmail(resp.getEmailId(), "Sent Health Data Successfully...");
				
//				if (!isEmailSent) {
//					resp.setMessage("Unable to send an Email");
//				}
			}

			respObj.put("msg", resp.getMessage());
			respObj.put("success", resp.getIsSuccess());

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			logger.info("While saving the user Health Data for user : {} - {}", resp.getEmailId(), resp.getMessage());
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);

		} catch (Exception e) {
			respObj.put("msg", someThingWentWrong);
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.info("Exception occurred while encrypting the response- {}", e1);
			}
			logger.info("Exception occurred While saving the user Health Data for user - {}", e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}

	}

}
