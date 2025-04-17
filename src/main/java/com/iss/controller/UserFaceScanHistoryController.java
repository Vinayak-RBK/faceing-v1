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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iss.dao.UserHealthDetailsResponseDao;
import com.iss.service.UserFaceScanHistoryService;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class UserFaceScanHistoryController {

	@Value("${SOMETHING_WENT_WRONG}")
	private String someThingWentWrong;

	private static final Logger logger = LoggerFactory.getLogger(UserFaceScanHistoryController.class);

	@Autowired
	private UserFaceScanHistoryService userHistoryService;

	@GetMapping("/userHealthHistory/{userId}")
	public ResponseEntity<Map<String, Object>> getUserFaceScanHistory(@PathVariable String userId) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		UserHealthDetailsResponseDao resDao = new UserHealthDetailsResponseDao();
		try {
			resDao = userHistoryService.getUserAllFaceScanHistoryService(userId);

			if (resDao.isSuccess()) {
				respObj.put("msg", resDao.getMsg());
				respObj.put("userHealthHistoryList", resDao.getUseHealthList());
				respObj.put("success", resDao.isSuccess());
				logger.info("Sending User Health History List for User :{} - {}", userId, resDao.getMsg());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
			} else {
				respObj.put("msg", resDao.getMsg());
				respObj.put("success", resDao.isSuccess());
				logger.info("Sending User Health History List for User :{} - {}", userId, resDao.getMsg());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			respObj.put("msg", someThingWentWrong);
			respObj.put("success", false);
			logger.info("Something went wrong while sending user Health History List for User : {} - {}", userId, e);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/perHealthHistory")
	public ResponseEntity<Map<String, Object>> getUserHealthRecordByHealthId(@RequestParam("userId") String userId,
			@RequestParam("healthId") String healthId) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		UserHealthDetailsResponseDao resDao = new UserHealthDetailsResponseDao();
		try {
			resDao = userHistoryService.getUserFaceScanHistoryByScanId(userId, healthId);

			if (resDao.isSuccess()) {
				respObj.put("msg", resDao.getMsg());
				respObj.put("userHealthHistory", resDao.getUseHealthList().get(0));
				respObj.put("success", resDao.isSuccess());
				logger.info("Sending User Health History for User and Health Id :{} and {} - {}", userId, healthId,
						resDao.getMsg());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
			} else {
				respObj.put("msg", resDao.getMsg());
				respObj.put("success", resDao.isSuccess());
				logger.info("Sending User Health Data for  User and Health Id :{} and {} - {}", userId, healthId,
						resDao.getMsg());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			respObj.put("msg", someThingWentWrong);
			respObj.put("success", false);
			logger.info("Something went wrong while sending user Health Data for User : {} - {}", userId, healthId, e);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}

	}

}
