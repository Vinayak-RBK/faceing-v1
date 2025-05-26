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
import org.springframework.web.bind.annotation.RestController;

import com.iss.dao.PushNotifiResponseDao;
import com.iss.service.PushNotificationService;
import com.iss.util.EncryptedDecryptedObjectUtil;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class PushNotificationController {

	@Value("${SOMETHING_WENT_WRONG}")
	private String someThingWentWrong;

	@Value("${DATE_TIME_FORMAT}")
	private String dateTimeFormat;

	@Value("${SECRET_KEY}")
	private String secretKey;

	@Value("${SECRET_IV}")
	private String secretIv;

	@Value("${IS_ENCRYPT_DECRYPT_ENABLE_DATABASE_DATA}")
	private boolean isEncryptDecryptDatabaseData;

	@Value("${IS_ENCRYPT_DECRYPT_REQUEST_RESPONSE_DATA}")
	private boolean isEncryptDecryptReqRespData;

	private static final Logger logger = LoggerFactory.getLogger(PushNotificationController.class);

	@Autowired
	public PushNotificationService pushNotificationService;

	@SuppressWarnings("unchecked")
	@PostMapping("/pushNotification/{userId}")
	public ResponseEntity<?> enablePushNotification(@PathVariable Long userId) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();

		logger.info("Enable or disable for the userId : {}", userId);
		try {
			PushNotifiResponseDao response = pushNotificationService.enablePushNotification(userId, dateTimeFormat);

			respObj.put("msg", response.getMessage());
			respObj.put("success", response.getSuccess());

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			logger.info("While Enabling/Disabling Push Notification for the UserId :{} - {}", userId,
					response.getMessage());
			return new ResponseEntity<>(enRespObj, HttpStatus.OK);

		} catch (Exception e) {
			respObj.put("msg", someThingWentWrong);
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Exception occurred while encrypting the response- {}", e1);
			}

			logger.error("Exception occurred while Enabling/Disabling Push Notification for the UserId : {} - {}",
					userId, e);

			return new ResponseEntity<>(enRespObj, HttpStatus.BAD_REQUEST);
		}

	}

}
