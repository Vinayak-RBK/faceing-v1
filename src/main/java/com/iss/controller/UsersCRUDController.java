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
import com.iss.dao.EmployeeDao;
import com.iss.dao.GuestInfoListDao;
import com.iss.dao.PagerRequestDao;
import com.iss.dao.PagerResponseDao;
import com.iss.dao.Response;
import com.iss.dao.ResponseDao;
import com.iss.dao.UserHealthAnuraDetailsDao;
import com.iss.dao.UserHealthBinahDetailsDao;
import com.iss.dao.UserPersonalAndHealthDetailsDao;
import com.iss.dao.UserSearchResponseDao;
import com.iss.service.GuestUserSearchService;
import com.iss.service.LoginUserService;
import com.iss.service.UserSearchService;
import com.iss.util.CommonUtility;
import com.iss.util.EncryptedDecryptedObjectUtil;
import com.iss.validate.FieldValidadator;

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

	@Value("${ACCOUNT_EXISTS}")
	private String accountExists;

	@Value("${ACCOUNT_CREATION_SUCCESS}")
	private String accountCreationSuccess;

	@Value("${ACCOUNT_CREATION_FAILURE}")
	private String accountCreationFailure;

	@Value("${DUP_EMAIL_ID}")
	private String DupEmailId;

	@Value("${EMAILID_INVALID}")
	private String emailidInvalid;

	@Value("${OTP_EXPIRED}")
	private String otpExpired;

	@Value("${EMAIL_OTP}")
	private String emailOTP;

	@Value("${OTP_NOT_MATCH}")
	private String otpNotMatch;

	@Value("${OTP_VERIFIED}")
	private String otpVerified;

	@Value("${PASSWORD_MISMATCH}")
	private String passwordMismatch;

	@Value("${EMAIL_VERIFY_FAIL}")
	private String emailVerifyFail;

	@Value("${NO_EMAIL_EXIST}")
	private String noEmailExists;

	@Value("{$EMPLOYEE_EXISTS}")
	private String employeeExists;

	@Value("${SECRET_KEY}")
	private String secretKey;

	@Value("${SECRET_IV}")
	private String secretIv;

	@Value("${IS_ENCRYPT_DECRYPT_ENABLE_DATABASE_DATA}")
	private boolean isEncryptDecryptDatabaseData;

	@Value("${IS_ENCRYPT_DECRYPT_REQUEST_RESPONSE_DATA}")
	private boolean isEncryptDecryptReqRespData;

	@Autowired
	UserSearchService userSearchService;

	@Autowired
	GuestUserSearchService guestSearchService;

	@Autowired
	public LoginUserService loginUserService;

	@Autowired
	private CommonUtility otpUtil;

	private static final Logger logger = LoggerFactory.getLogger(UsersCRUDController.class);

	@SuppressWarnings("unchecked")
	@PostMapping("/sendAllUserDetails")
	public ResponseEntity<?> getUsersList(@RequestBody PagerRequestDao pagerDao) {
		PagerResponseDao respDao = new PagerResponseDao();
		List<UserSearchResponseDao> enList = new ArrayList<UserSearchResponseDao>();
		String errorMsg = "";
		Map<String, String> valFields = new LinkedHashMap<String, String>();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();

		try {

			// validation for the fields
			valFields = FieldValidadator.validateFields(pagerDao);

			String res = valFields.get("success");

			if (!Boolean.parseBoolean(res)) {

				respObj.put("msg", valFields.get("msg"));
				respObj.put("success", Boolean.parseBoolean(res));
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			}

			// Encrypting the error message
			errorMsg = (String) EncryptedDecryptedObjectUtil.getEncryptedString(someThingWentWrong, secretKey, secretIv,
					isEncryptDecryptReqRespData);

			// Fetching all the User Details
			respDao = userSearchService.getAllUserDetails(dateTimeFormat, pagerDao);

			if (!Boolean.parseBoolean(respDao.getSuccess())) {
				respObj.put("msg", respDao.getMsg());
				respObj.put("success", respDao.getSuccess());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				logger.info("There is no users data...");
				return new ResponseEntity<>(enRespObj, HttpStatus.OK);

			} else {

				respObj.put("msg", respDao.getMsg());
				respObj.put("success", respDao.getSuccess());
				respObj.put("totalUsersCount", respDao.getTotalCount());
				respObj.put("noOfPages", respDao.getNoOfPages());
				respObj.put("curPageNo", respDao.getCurPageNo());
				respObj.put("pageSize", pagerDao.getPageSize());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				for (UserSearchResponseDao dao : respDao.getPerDetailsList()) {
					UserSearchResponseDao enDao = (UserSearchResponseDao) EncryptedDecryptedObjectUtil
							.getEncryptedObject(dao, secretKey, secretIv, isEncryptDecryptReqRespData);
					enList.add(enDao);
					enRespObj.put("list", enList);
				}

				logger.info("Sending all users data...");
				return new ResponseEntity<>(enRespObj, HttpStatus.OK);
			}
		} catch (Exception e) {
			logger.error("Exception occurred while sending User Data : {} - {}", e.getMessage(), e);
			return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings({ "removal", "unchecked" })
	@PutMapping("/ban/{userId}")
	public ResponseEntity<?> banUser(@PathVariable String userId) {

		Response resDao = new Response();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();

		try {
			// Blocking the User by User Id
			resDao = userSearchService.banByUserId(new Long(userId));

			if (Boolean.parseBoolean(resDao.isSuccess())) {
				respObj.put("status", resDao.getBlockStatus());

			}

			respObj.put("message", resDao.getMsg());
			respObj.put("success", resDao.isSuccess());

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			logger.info("While banning the user : {} - {}", userId, resDao.getMsg());
			return new ResponseEntity<>(enRespObj, HttpStatus.OK);

		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Exception occurred while Encrypting response - {}", e);
			}

			logger.error("Exception occurred while Blocking the User : {} - {}", e.getMessage(), e);
			return new ResponseEntity<>(enRespObj, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings({ "removal", "unchecked" })
	@GetMapping("/getDetail/{userId}")
	public ResponseEntity<?> getUserDetails(@PathVariable Integer userId) {
		UserPersonalAndHealthDetailsDao dao = new UserPersonalAndHealthDetailsDao();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		try {
			// fetching the User Details By User Id
			dao = userSearchService.getUserDetailsByUserId(new Long(userId));
			if (Boolean.parseBoolean(dao.getResponse().isSuccess())) {

				respObj.put("userId", dao.getUserDao().getUserId());
				respObj.put("emailId", dao.getUserDao().getEmailId());
				respObj.put("name", dao.getUserPerDao().getName());
				respObj.put("gender", dao.getUserPerDao().getGender());
				respObj.put("dob", dao.getUserPerDao().getDob());
				respObj.put("weight", dao.getUserPerDao().getWeight());
				respObj.put("height", dao.getUserPerDao().getHeight());
				respObj.put("image", dao.getUserPerDao().getImage());
				respObj.put("age", dao.getUserPerDao().getAge());

				if (dao.getUserHealthAnuraDao().isEmpty()) {
					respObj.put("userAnuraHealthData", new ArrayList<UserHealthAnuraDetailsDao>());
				} else {
					respObj.put("userAnuraHealthData", dao.getUserHealthAnuraDao().get(0));
				}

				if (dao.getUserHealthBinahDao().isEmpty()) {
					respObj.put("userBinahHealthData", new ArrayList<UserHealthBinahDetailsDao>());
				} else {
					respObj.put("userBinahHealthData", dao.getUserHealthBinahDao().get(0));
				}

				respObj.put("success", dao.getResponse().isSuccess());
				respObj.put("msg", dao.getResponse().getMsg());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				logger.info("Sent All User Personal data, Health Data successfully...");
				return new ResponseEntity<>(enRespObj, HttpStatus.OK);
			} else {
				respObj.put("success", dao.getResponse().isSuccess());
				respObj.put("msg", dao.getResponse().getMsg());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
				logger.info("Unable to send All User Personal data, Health Data successfully...");
				return new ResponseEntity<>(enRespObj, HttpStatus.OK);
			}
		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Exception occurred while Encrypting response - {}", e);
			}
			logger.error("Exception occurred while sending the User Perosnal and Health Data : {} - {}", e.getMessage(),
					e);
			return new ResponseEntity<>(enRespObj, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("unchecked")
	@PutMapping("/updateUserByAdmin/{userId}")
	public ResponseEntity<?> updateUserInfoByAdmin(@RequestBody AdminUserPersonalDetailsDao dao,
			@PathVariable String userId) {
		AdminOperationResponseDao respDao = new AdminOperationResponseDao();
		Map<String, Object> respObj = new HashMap<>();
		Map<String, Object> enRespObj = new HashMap<>();
		try {
			dao.setUserId(userId.toString());
			// Updating the User by USer Id
			respDao = userSearchService.updateUserByUserId(dao, dateTimeFormat);

			respObj.put("message", respDao.getMessage());
			respObj.put("success", respDao.isSuccess());

			if (Boolean.parseBoolean(respDao.isSuccess())) {

				respObj.put("userId", respDao.getUserId());

				logger.info("Updated User Data by Admin successfully...");
			} else {
				logger.info("Unable to Update User Data by Admin...");
			}

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			return new ResponseEntity<>(enRespObj, HttpStatus.OK);

		} catch (Exception e) {
			respObj.put("message", "An error occurred while updating the user status.");
			respObj.put("error", e.getMessage());

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Exception occurred while Encrypting response - {}", e);

			}
			logger.error("Exception occurred while Updating the User Perosnal by Admin : {} - {}", e.getMessage(), e);
			return new ResponseEntity<>(enRespObj, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings({ "removal", "unchecked" })
	@GetMapping("/downloadExcel/{userId}")
	public ResponseEntity<?> exportUserDetailsToExcel(@PathVariable String userId, HttpServletResponse response) {
		Response resp = new Response();
		logger.info("Received request to download Excel for userId: {}", userId);

		Map<String, Object> respObj = new HashMap<>();
		Map<String, Object> enRespObj = new HashMap<>();

		try {
			resp = userSearchService.getUserDetailsByExcel(new Long(userId), response);
			logger.info("Successfully generated Excel file for userId: {}", userId);

			respObj.put("message", resp.getMsg());
			respObj.put("status", resp.isSuccess());

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(enRespObj);

		} catch (Exception e) {
			logger.error("Error occurred while generating Excel file for userId: {}: {}", userId, e.getMessage(), e);

			respObj.put("message", "Error occurred while generating Excel file: " + e.getMessage());
			respObj.put("status", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Exception occurred while Encrypting response - {}", e1);
			}

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
					.body(enRespObj);
		}
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/addEmployee")
	public ResponseEntity<?> addEmployeeBySuperAdminOrAdmin(@RequestBody EmployeeDao empDao,
			HttpServletResponse response) {
		ResponseDao resDao = new ResponseDao();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		EmployeeDao decEmpDao = new EmployeeDao();
		Map<String, String> valFields = new LinkedHashMap<String, String>();
//		boolean isEmailSent = false;

		try {

			// validation for the fields
			valFields = FieldValidadator.validateFields(empDao);

			String res = valFields.get("success");
			if (!Boolean.parseBoolean(res)) {

				respObj.put("msg", valFields.get("msg"));
				respObj.put("success", Boolean.parseBoolean(res));
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			}

			decEmpDao = (EmployeeDao) EncryptedDecryptedObjectUtil.getDecryptedObject(empDao, secretKey, secretIv,
					isEncryptDecryptReqRespData);

			// This is to check whether the employee exists are not
			resDao = loginUserService.findByEmailIdForAddEmployee(decEmpDao);
			if (!Boolean.parseBoolean(resDao.getIsSuccess())) {

				respObj.put("message", resDao.getMessage());
				respObj.put("success", resDao.getIsSuccess());

				logger.info("User Signup for Email ID :{} - {}", resDao.getEmailId(), resDao.getMessage());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
			} else {

				// Generate & Send OTP
				try {
					resDao = new ResponseDao();
					resDao = loginUserService.addEmployeeService(decEmpDao, dateTimeFormat);

					if (Boolean.parseBoolean(resDao.getIsSuccess())) {

						// Sending the created Employee details to Email
//						isEmailSent = otpUtil.sendEmployeeDetailsToEmail(decEmpDao.getEmail(), decEmpDao);

//						if (!isEmailSent) {
//							resDao.setMessage("Unable to send an Email");
//						}

					}

					respObj.put("message", resDao.getMessage());
					respObj.put("success", resDao.getIsSuccess());

					enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj,
							secretKey, secretIv, isEncryptDecryptReqRespData);

					logger.info("Creating Employee by Admin : {} - {}", decEmpDao.getEmail());
					return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);

				} catch (Exception e) {
					respObj.put("message", someThingWentWrong);
					respObj.put("success", false);

					enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj,
							secretKey, secretIv, isEncryptDecryptReqRespData);
					logger.error("Exception occurred while creating employee by Admin : {} - {}", decEmpDao.getEmail(),
							e);
					return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
				}

			}
		} catch (Exception e) {
			respObj.put("message", someThingWentWrong);
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			logger.error("Exception occurred while creating employee by Admin : {} - {}", decEmpDao.getEmail(), e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/GuestdownloadExcel/{userId}")
	public ResponseEntity<?> exportGuestDetailsToExcel(@PathVariable String userId, HttpServletResponse response) {
		GuestInfoListDao infoListDao = new GuestInfoListDao();
		logger.info("Received request to download Excel for userId: {}", userId);

		Map<String, Object> respObj = new HashMap<>();
		Map<String, Object> enRespObj = new HashMap<>();

		try {
			infoListDao = guestSearchService.getGuestDetailsByExcel(userId, response);
			logger.info("Successfully generated Excel file for guestId: {}", userId);
			if (Boolean.parseBoolean(infoListDao.getSuccess())) {

				respObj.put("healthInfoList", infoListDao.getHealthInfoDaoList());

			}

			respObj.put("message", infoListDao.getMsg());
			respObj.put("status", infoListDao.getSuccess());

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			respObj.put("userId", userId);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);

		} catch (Exception e) {
			logger.error("Exception occurred while generating Excel file for userId: {}: {}", userId, e.getMessage(),
					e);

			respObj.put("message", "Error occurred while generating Excel file: " + e.getMessage());
			respObj.put("status", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Exception occurred while Encrypting response - {}", e1);
			}
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
					.body(enRespObj);
		}
	}

}
