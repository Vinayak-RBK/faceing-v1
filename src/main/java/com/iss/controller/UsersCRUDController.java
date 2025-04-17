package com.iss.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iss.dao.AdminOperationResponseDao;
import com.iss.dao.AdminUserPersonalDetailsDao;
import com.iss.dao.Response;
import com.iss.dao.UserPersonalAndHealthDetailsDao;
import com.iss.dao.UserSearchResponseDao;
import com.iss.service.UserSearchService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UsersCRUDController {

	@Value("${BLOCK_USERID}")
	private String blockUserId;

	@Value("${UNBLOCK_USERID}")
	private String UNblockUserId;

	@Value("${DATE_TIME_FORMAT}")
	private String dateTimeFormat;

	@Value("${EXCEL_FILE}")
	private String excelFile;

	@Value("${SOMETHING_WENT_WRONG}")
	private String someThingWentWrong;

	@Autowired
	UserSearchService userSearchService;

	private static final Logger logger = LoggerFactory.getLogger(UsersCRUDController.class);

	@PostMapping("/sendAllUserDetails")
	public ResponseEntity<List<UserSearchResponseDao>> getUsersList() {
		List<UserSearchResponseDao> list = new ArrayList<UserSearchResponseDao>();
		try {
			list = userSearchService.getAllUserDetails(dateTimeFormat);
			if (list.isEmpty()) {
				logger.info("There is no users data...");
				return new ResponseEntity<>(list, HttpStatus.NOT_FOUND);
			} else {
				logger.info("Sending all users data...");
				return new ResponseEntity<>(list, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("Error while sending User Data : {} - {}", e.getMessage(), e);
			return new ResponseEntity<>(list, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("removal")
	@PutMapping("/ban/{userId}")
	public ResponseEntity<Map<String, Object>> banUser(@PathVariable String userId) {

		Response resObj = new Response();
		Map<String, Object> response = new LinkedHashMap<String, Object>();

		try {
			resObj = userSearchService.banByUserId(new Long(userId));

			if (resObj.isSuccess()) {
				response.put("message", resObj.getMsg());
				response.put("status", resObj.getBlockStatus());
				response.put("success", resObj.isSuccess());
				logger.info("While banning the user : {} - {}", userId, resObj.getMsg());
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.put("message", resObj.getMsg());
//				response.put("status", resObj.getBlockStatus());
				response.put("success", resObj.isSuccess());
				logger.info("While banning the user : {} - {}", userId, resObj.getMsg());
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {

			response.put("message", someThingWentWrong);
			response.put("success", false);
			logger.error("Error while Blocking the User : {} - {}", e.getMessage(), e);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("removal")
	@GetMapping("/getDetail/{userId}")
	public ResponseEntity<Map<String, Object>> getUserDetails(@PathVariable Integer userId) {
		UserPersonalAndHealthDetailsDao dao = new UserPersonalAndHealthDetailsDao();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		try {
			dao = userSearchService.getUserDetailsByUserId(new Long(userId));
			if (dao.getResponse().isSuccess()) {

				respObj.put("userId", dao.getUserDao().getUserId());
				respObj.put("emailId", dao.getUserDao().getEmailId());
				respObj.put("name", dao.getUserPerDao().getName());
				respObj.put("gender", dao.getUserPerDao().getGender());
				respObj.put("dob", dao.getUserPerDao().getDob());
				respObj.put("weight", dao.getUserPerDao().getWeight());
				respObj.put("height", dao.getUserPerDao().getHeight());
				respObj.put("image", dao.getUserPerDao().getImage());

				respObj.put("userHealthDao", dao.getUserHealthDao());
				respObj.put("success", dao.getResponse().isSuccess());
				respObj.put("msg", dao.getResponse().getMsg());

				logger.info("Sent All User Personal data, Health Data successfully...");
				return new ResponseEntity<>(respObj, HttpStatus.OK);
			} else {
				respObj.put("success", dao.getResponse().isSuccess());
				respObj.put("msg", dao.getResponse().getMsg());
				logger.info("Unable to send All User Personal data, Health Data successfully...");
				return new ResponseEntity<>(respObj, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("success", false);
			logger.error("Error while sending the User Perosnal and Health Data : {} - {}", e.getMessage(), e);
			return new ResponseEntity<>(respObj, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/updateUserByAdmin/{userId}")
	public ResponseEntity<AdminOperationResponseDao> updateUserInfoByAdmin(@RequestBody AdminUserPersonalDetailsDao dao,
			@PathVariable String userId) {
		AdminOperationResponseDao respDao = new AdminOperationResponseDao();
		try {
			dao.setUserId(userId.toString());
			respDao = userSearchService.updateUserByUserId(dao, dateTimeFormat);

			if (respDao.isSuccess()) {
				logger.info("Updated User Data by Admin successfully...");
				return new ResponseEntity<>(respDao, HttpStatus.OK);
			} else {
				logger.info("Unable to Update User Data by Admin...");
				return new ResponseEntity<>(respDao, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			Map<String, Object> errorResponse = new HashMap<>();
			errorResponse.put("message", "An error occurred while updating the user status.");
			errorResponse.put("error", e.getMessage());
			logger.error("Error while Updating the User Perosnal by Admin : {} - {}", e.getMessage(), e);
			return new ResponseEntity<>(respDao, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("removal")
	@GetMapping("/downloadExcel/{userId}")
	public ResponseEntity<?> exportUserDetailsToExcel(@PathVariable String userId, HttpServletResponse response) {
		Response resp = new Response();
		logger.info("Received request to download Excel for userId: {}", userId);

		Map<String, Object> respObj = new HashMap<>();

		try {
			resp = userSearchService.getUserDetailsByExcel(new Long(userId), response);
			logger.info("Successfully generated Excel file for userId: {}", userId);

			respObj.put("message", resp.getMsg());
			respObj.put("status", resp.isSuccess());

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(respObj);

		} catch (RuntimeException e) {
			logger.error("Error occurred while generating Excel file for userId: {}: {}", userId, e.getMessage(), e);

			respObj.put("message", "Error occurred while generating Excel file: " + e.getMessage());
			respObj.put("status", false);

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
					.body(respObj);
		}
	}

}
