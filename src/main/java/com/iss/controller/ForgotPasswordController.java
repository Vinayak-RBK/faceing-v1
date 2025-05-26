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
import com.iss.dao.UserDao;
import com.iss.service.LoginUserService;
import com.iss.util.CommonUtility;
import com.iss.util.EncryptedDecryptedObjectUtil;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class ForgotPasswordController {

	private static final Logger logger = LoggerFactory.getLogger(ForgotPasswordController.class);

	@Autowired
	private CommonUtility otpUtil;

	// Generating OTP
	String otp = CommonUtility.generateOTP();

	@Autowired
	LoginUserService loginUserService;

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

	@Value("${PASSWORD_SUCCESS}")
	private String passwordSuccess;

	@Value("${PASSWORD_FAILED}")
	private String passwordFailed;

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

	@SuppressWarnings("unchecked")
	@PostMapping("/forgotPassword")
	public ResponseEntity<Map<String, Object>> checkEmailExists(@RequestBody UserDao userDao,
			HttpServletResponse response) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();

		ResponseDao resDao = new ResponseDao();
		String newOtp = "";
		UserDao enUserDao = new UserDao();
//		boolean isEmailSent = false;

		logger.info("Processing forgot password request for email: {}", userDao.getEmailId());
		try {

			enUserDao = (UserDao) EncryptedDecryptedObjectUtil.getEncryptedObject(userDao, secretKey, secretIv,
					isEncryptDecryptReqRespData);

			resDao = loginUserService.findByEmailId(enUserDao, false, dateTimeFormat);
			if (!Boolean.parseBoolean(resDao.getIsSuccess())) {
				respObj.put("message", resDao.getMessage());
				respObj.put("success", resDao.getIsSuccess());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			}

			// Store or retrieve existing OTP
			newOtp = loginUserService.storeOtpForForgotPassword(userDao, dateTimeFormat);
			logger.info("Stored OTP: {} for email: {}", newOtp, userDao.getEmailId());

			// Sending OTP to an email
//			isEmailSent = otpUtil.sendOtpToEmail(userDao.getEmailId(), newOtp);

//			if (!isEmailSent) {
//				resDao.setMessage("Unable to send an Email");
//			}

			respObj.put("message", resDao.getMessage());
			respObj.put("otp", newOtp);
			respObj.put("userId", resDao.getUserId());
			respObj.put("emailId", resDao.getEmailId());
			respObj.put("success", resDao.getIsSuccess());

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);

		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("userId", userDao.getUserId());
			respObj.put("emailId", userDao.getEmailId());
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Error occured while encrypting the response- {}", e1);
			}

			logger.error("Exception occurred while checking Email ID : {} - {}", userDao.getEmailId(), e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/verifyForGetPasOtp")
	public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody UserDao userDao) {
		ResponseDao resDao = new ResponseDao();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		UserDao decUserDao = new UserDao();

		logger.info("Verifying OTP for email: {}", userDao.getEmailId());
		try {
			// Decrypting the UI Request Here
			decUserDao = (UserDao) EncryptedDecryptedObjectUtil.getEncryptedObject(userDao, secretKey, secretIv,
					isEncryptDecryptReqRespData);
			// Verifying the OTP here
			resDao = loginUserService.verifyOtpForSignup(decUserDao, dateTimeFormat);

			if (Boolean.parseBoolean(resDao.getIsSuccess())) {
				respObj.put("userId", resDao.getUserId());
				respObj.put("emailId", resDao.getEmailId());

			} else {
				respObj.put("userId", userDao.getUserId());
				respObj.put("emailId", userDao.getEmailId());

			}

			respObj.put("message", resDao.getMessage());
			respObj.put("success", resDao.getIsSuccess());

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);

		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("userId", userDao.getUserId());
			respObj.put("emailId", userDao.getEmailId());
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Error occured while encrypting the response- {}", e1);
			}

			logger.error("Exception occurred while Verifying Otp for Email ID : {} - {}", userDao.getEmailId(), e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/resetPassword")
	public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody UserDao userDao) {
		ResponseDao resDao = new ResponseDao();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		UserDao decUserDao = new UserDao();

		logger.info("Received request from UI : {}", userDao);

		try {
			// Decrypting the UI Request here
			decUserDao = (UserDao) EncryptedDecryptedObjectUtil.getEncryptedObject(userDao, secretKey, secretIv,
					isEncryptDecryptReqRespData);

			logger.info("Received request from UI after decryption: {}", decUserDao);

			// Checking the password and confirm password both are same or not
			if (!decUserDao.getPassword().equals(decUserDao.getConfirmPassword())) {

				respObj.put("message", passwordMismatch);
				respObj.put("userId", userDao.getUserId());
				respObj.put("emailId", userDao.getEmailId());
				respObj.put("success", false);

				logger.info("Response to UI before encryption: {}", respObj);

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				logger.info("Response to UI after encryption: {}", enRespObj);

				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			}

			// Updating the password here
			resDao = loginUserService.updatePassword(userDao, dateTimeFormat);
			resDao.setUserId(userDao.getUserId());

			if (Boolean.parseBoolean(resDao.getIsSuccess())) {

				respObj.put("userId", userDao.getUserId());
				respObj.put("emailId", userDao.getEmailId());

			} else {

				respObj.put("userId", userDao.getUserId());
				respObj.put("emailId", userDao.getEmailId());
			}

			respObj.put("message", resDao.getMessage());
			respObj.put("success", resDao.getIsSuccess());

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);

		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("userId", userDao.getUserId());
			respObj.put("emailId", userDao.getEmailId());
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(enRespObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Exception occurred while encrypting the response- {}", e1);
			}

			logger.error("Exception occurred while resetting the password for Email ID : {} - {}", userDao.getEmailId(),
					e);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}

	}

}