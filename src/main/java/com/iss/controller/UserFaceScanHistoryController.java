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

import com.iss.dao.UserDao;
import com.iss.dao.UserHealthDetailsResponseDao;
import com.iss.dao.UserHealthRequestDao;
import com.iss.service.UserFaceScanHistoryService;
import com.iss.util.EncryptedDecryptedObjectUtil;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class UserFaceScanHistoryController {

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

	private static final Logger logger = LoggerFactory.getLogger(UserFaceScanHistoryController.class);

	@Autowired
	private UserFaceScanHistoryService userHistoryService;

	@SuppressWarnings("unchecked")
	@PostMapping("/userHealthHistory")
	public ResponseEntity<?> getUserFaceScanHistory(@RequestBody UserDao useDao) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		UserHealthDetailsResponseDao resDao = new UserHealthDetailsResponseDao();
		try {
			resDao = userHistoryService.getUserAllFaceScanHistoryService(useDao.getUserId());

			respObj.put("msg", resDao.getMsg());
			respObj.put("success", resDao.getSuccess());
			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			enRespObj.put("userHealthList", resDao.getUserScannedList());

			logger.info("Sending User Health History List for User :{} - {}", useDao.getUserId(), resDao.getMsg());
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);

		} catch (Exception e) {
			respObj.put("msg", someThingWentWrong);
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Exception occurred while encrypting the response- {}", e1);
			}
			logger.error("Exception occurred while sending user Health History List for User : {} - {}",
					useDao.getUserId(), e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/perHealthHistory")
	public ResponseEntity<Map<String, Object>> getUserHealthRecordByHealthId(@RequestBody UserDao userDao) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		UserHealthDetailsResponseDao resDao = new UserHealthDetailsResponseDao();

		try {
			resDao = userHistoryService.getUserFaceScanHistoryByScanId(userDao.getUserId(), userDao.getHealthId(),
					true);

			if (Boolean.parseBoolean(resDao.getSuccess())) {

				if (resDao.getUseHealthAnuraList().isEmpty()) {
					enRespObj.put("userHealthBinahDetail", resDao.getUseHealthBinahList().get(0));
				} else {

					enRespObj.put("userHealthAnuraDetail", resDao.getUseHealthAnuraList().get(0));
				}

			}

			respObj.put("msg", resDao.getMsg());
			respObj.put("success", resDao.getSuccess());

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);
			logger.info("Sending User Health Data for  User and Health Id :{} and {} - {}", userDao.getUserId(),
					userDao.getHealthId(), resDao.getMsg());
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);

		} catch (Exception e) {
			respObj.put("msg", someThingWentWrong);
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Exception occurred while encrypting the response- {}", e1);
			}
			logger.error("Exception occurred while sending user Health Data for User : {} - {}", userDao.getUserId(),
					userDao.getHealthId(), e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/getHealthHistoryList")
	public ResponseEntity<Map<String, Object>> getUserHealthHistoryListForSingleValue(
			@RequestBody UserHealthRequestDao reqDao) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		UserHealthDetailsResponseDao resDao = new UserHealthDetailsResponseDao();

		try {
			resDao = userHistoryService.getUserHealthHistoryList(reqDao);

			if (Boolean.parseBoolean(resDao.getSuccess())) {
				respObj.put("fieldName", reqDao.getFieldName());
				respObj.put("values", resDao.getValues());

			} else {
				respObj.put("msg", resDao.getMsg());
				respObj.put("success", resDao.getSuccess());
			}

			respObj.put("msg", resDao.getMsg());
			respObj.put("success", resDao.getSuccess());

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			logger.info("Sending User Health History for User {} and for column {} - {}", reqDao.getUserId(),
					reqDao.getFieldName(), resDao.getMsg());

			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
		} catch (Exception e) {
			respObj.put("msg", someThingWentWrong);
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Exception occurred while encrypting the response- {}", e1);
			}
			logger.error("Exception occurred while sending user Health Data for User {} and for column {}  - {}",
					reqDao.getUserId(), reqDao.getFieldName(), e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}

	}

}
