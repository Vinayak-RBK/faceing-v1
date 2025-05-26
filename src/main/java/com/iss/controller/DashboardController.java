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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iss.dao.DashboardDataDao;
import com.iss.service.DashboardDataService;
import com.iss.util.EncryptedDecryptedObjectUtil;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class DashboardController {

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
	private DashboardDataService dashboardDataService;

	private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

	@SuppressWarnings("unchecked")
	@GetMapping("/getDashBoradData")
	public ResponseEntity<?> getDashboardData() {
		logger.info("Start - Sending Dashboard Data.");
		DashboardDataDao respDao = new DashboardDataDao();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		try {
			respDao = dashboardDataService.getDashBoardDataService();

			respObj.put("userCount", respDao.getTotalUserCount());
			respObj.put("guestCount", respDao.getTotalGuestCount());
			respObj.put("anuraScanCount", respDao.getTotalAnuraScanCount());
			respObj.put("binahScanCount", respDao.getTotalBinahScanCount());
			respObj.put("msg", respDao.getMsg());
			respObj.put("success", respDao.getSuccess());

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			logger.info("Sending Dashboard Data - {}", respDao.getMsg());
			logger.info("End - Sending Dashboard Data.");
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);

		} catch (Exception e) {
			respObj.put("msg", someThingWentWrong);
			respObj.put("success", Boolean.toString(false));

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Exception occurred while encrypting the response- {}", e1);
			}
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}

	}

}
