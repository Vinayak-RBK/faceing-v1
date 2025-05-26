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

import com.iss.dao.GuestInfoDao;
import com.iss.dao.Response;
import com.iss.service.GuestService;

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

	private static final Logger logger = LoggerFactory.getLogger(AddGuestController.class);

	@Autowired
	private GuestService guestService;

	@PostMapping("/addGuest")
	public ResponseEntity<Map<String, Object>> addGuest(@RequestBody GuestInfoDao guestInfoDao) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Response resp = new Response();
		try {
			resp = guestService.addGuest(guestInfoDao, dateTimeFormat);

			if (resp.isSuccess()) {
				respObj.put("msg", resp.getMsg());
				respObj.put("success", resp.isSuccess());

				logger.info("Adding guest to user :{} - {}", guestInfoDao.getGuestDao().getUserId(), resp.getMsg());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
			} else {
				respObj.put("msg", resp.getMsg());
				respObj.put("success", resp.isSuccess());
				logger.info("Adding guest to user :{} - {}", guestInfoDao.getGuestDao().getUserId(), resp.getMsg());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			respObj.put("message", someThingWentWrong);
			respObj.put("success", false);
			logger.info("Adding guest to user :{} - {}", guestInfoDao.getGuestDao().getUserId(), someThingWentWrong);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}

	}

}
