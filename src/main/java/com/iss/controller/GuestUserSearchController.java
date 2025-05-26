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
import com.iss.dao.GuestInfoListDao;
import com.iss.dao.PagerRequestDao;
import com.iss.dao.PagerResponseDao;
import com.iss.service.GuestUserSearchService;
import com.iss.util.EncryptedDecryptedObjectUtil;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class GuestUserSearchController {

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

	private static final Logger logger = LoggerFactory.getLogger(GuestUserSearchController.class);

	@Autowired
	private GuestUserSearchService guestUserSearchService;

	@SuppressWarnings("unchecked")
	@PostMapping("/listOfGuest")
	public ResponseEntity<Map<String, Object>> searchGuest(@RequestBody GuestDao guestDao) {
		GuestInfoListDao guestListRespDao = new GuestInfoListDao();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();

		logger.info("searchGuest() from GuestUserSearchController class");

		try {
			// Searching the all the guest by User Id here
			guestListRespDao = guestUserSearchService.findAllGuest(guestDao.getUserId());

			if (Boolean.parseBoolean(guestListRespDao.getSuccess())) {

				respObj.put("userId", guestDao.getUserId());
				respObj.put("GuestList", guestListRespDao.getGuestListDao());

			}

			respObj.put("msg", guestListRespDao.getMsg());
			respObj.put("success", guestListRespDao.getSuccess());

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			logger.info("Searching guests for user :{} - {}", guestDao.getUserId(), guestListRespDao.getMsg());
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

			logger.error("Exception occurred while sending Guest Health Data for User : {} - {}", guestDao.getUserId(),
					e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/listOfGuestByUserId")
	public ResponseEntity<Map<String, Object>> searchGuestByUserId(@RequestBody PagerRequestDao dao) {
		GuestInfoListDao guestListRespDao = new GuestInfoListDao();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		PagerResponseDao respDao = new PagerResponseDao();

		logger.info("searchGuestByUserId() from GuestUserSearchController class");

		try {
			// Searching the guest here and setting the pagination values
			respDao = guestUserSearchService.findAllGuestByUserId(dao);

			if (Boolean.parseBoolean(respDao.getSuccess())) {
				respObj.put("totalUsersCount", respDao.getTotalCount());
				respObj.put("noOfPages", respDao.getNoOfPages());
				respObj.put("curPageNo", respDao.getCurPageNo());
				respObj.put("pageSize", dao.getPageSize());
				respObj.put("GuestList", respDao.getGuestListDao());

			}

			respObj.put("msg", respDao.getMsg());
			respObj.put("success", respDao.getSuccess());

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			logger.info("Searching guests for user :{} - {}", dao.getUserId(), guestListRespDao.getMsg());
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

			logger.error("Exception occurred while sending Guest Health Data for User : {} - {}", dao.getUserId(), e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/perGuestHistory")
	public ResponseEntity<Map<String, Object>> getUserHealthRecordByHealthId(@RequestBody GuestDao guestDao) {
		GuestInfoListDao guestListRespDao = new GuestInfoListDao();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();

		logger.info("getUserHealthRecordByHealthId() from GuestUserSearchController class");

		try {
			// Searching the Guest health detail by Guest Id here
			guestListRespDao = guestUserSearchService.findGuestByGuestIdAndUserId(guestDao.getGuestId(),
					guestDao.getUserId().toString());

			if (Boolean.parseBoolean(guestListRespDao.getSuccess())) {
				respObj.put("guestHealthAnuraHistory", guestListRespDao.getGuestHealthAnuraDetail());
				respObj.put("guestHealthBinahHistory", guestListRespDao.getGuestHealthBinahDetail());
			}

			respObj.put("msg", guestListRespDao.getMsg());
			respObj.put("success", guestListRespDao.getSuccess());

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			logger.info("Sending User Health History for User and Health Id :{} and {} - {}", guestDao.getGuestId(),
					guestDao.getUserId().toString(), guestListRespDao.getMsg());

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

			logger.error("Exception occurred while sending user Health Data for User : {} - {}", guestDao.getGuestId(),
					guestDao.getUserId().toString(), e);

			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}

	}

}
