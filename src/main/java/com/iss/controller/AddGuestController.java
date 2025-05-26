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

import com.iss.dao.GuestDao;
import com.iss.dao.GuestHealthInfoDao;
import com.iss.dao.Response;
import com.iss.service.GuestService;
import com.iss.util.EncryptedDecryptedObjectUtil;
import com.iss.validate.FieldValidadator;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class AddGuestController {

	@Value("${DATE_TIME_FORMAT}")
	private String dateTimeFormat;

	@Value("${ADDED_GUEST_USER}")
	private String addedGuestUser;

	@Value("${UNABLE_TO_ADD_GUEST}")
	private String unableToAddGuest;

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

	private static final Logger logger = LoggerFactory.getLogger(AddGuestController.class);

	@Autowired
	private GuestService guestService;

	@SuppressWarnings("unchecked")
	@PostMapping("/addGuest")
	public ResponseEntity<Map<String, Object>> addGuest(@RequestBody GuestHealthInfoDao guestInfoDao) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		Response resp = new Response();
		GuestHealthInfoDao decGuestInfoDao = new GuestHealthInfoDao();
		Map<String, String> valFields = new LinkedHashMap<String, String>();

		try {

			// validation for the fields
			valFields = FieldValidadator.validateFields(guestInfoDao);

			String res = valFields.get("success");
			if (!Boolean.parseBoolean(res)) {

				respObj.put("msg", valFields.get("msg"));
				respObj.put("success", Boolean.parseBoolean(res));
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			}

			// Decrypting the Request Data from UI
			decGuestInfoDao = (GuestHealthInfoDao) EncryptedDecryptedObjectUtil.getDecryptedObject(guestInfoDao,
					secretKey, secretIv, isEncryptDecryptReqRespData);

			// adding the guest here
			resp = guestService.addGuest(decGuestInfoDao, dateTimeFormat);

			logger.info("Adding guest to user :{} - {}", decGuestInfoDao.getGuestDao().getUserId(), resp.getMsg());
			respObj.put("msg", resp.getMsg());
			respObj.put("success", resp.isSuccess());

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
		} catch (Exception e) {
			respObj.put("message", someThingWentWrong);
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Error occured while encrypting the response- {}", e1);
			}

			logger.error("Exception occurred while adding guest for :{} - {}",
					decGuestInfoDao.getGuestDao().getUserId(), e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/removeGuest")
	public ResponseEntity<Map<String, Object>> removeGuest(@RequestBody GuestDao guestDao) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		Response resp = new Response();
		try {
			// Removing the guest here
			resp = guestService.removeGuest(guestDao.getGuestId(), guestDao.getUserId().toString(), dateTimeFormat);

			logger.info("removing guest {} for user :{} - {}", guestDao.getGuestId(), guestDao.getUserId().toString(),
					resp.getMsg());

			respObj.put("msg", resp.getMsg());
			respObj.put("success", resp.isSuccess());

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);

		} catch (Exception e) {
			respObj.put("message", someThingWentWrong);
			respObj.put("success", false);
			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Error occured while encrypting the response- {}", e1);
			}
			logger.error("Exception occurred While removing guest {} for user {} - {}", guestDao.getGuestId(),
					guestDao.getUserId().toString(), e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}

	}

}
