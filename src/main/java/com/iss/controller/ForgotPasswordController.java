package com.iss.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.crypto.SecretKey;

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
import com.iss.util.EncryptDecryptData;

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

	@PostMapping("/forgotPassword")
	public ResponseEntity<Map<String, Object>> checkEmailExists(@RequestBody UserDao userDao,HttpServletResponse response) {
		Map<String, Object> respObj=new LinkedHashMap<String, Object>();
		ResponseDao resDao = new ResponseDao();
		String newOtp = "";
		SecretKey secretKey = null;

		logger.info("Processing forgot password request for email: {}", userDao.getEmailId());
		try {
			secretKey = EncryptDecryptData.generateKey();
			resDao = loginUserService.findByEmailId(userDao, false, dateTimeFormat, secretKey);
			if (!resDao.isSuccess()) {
				respObj.put("message", resDao.getMessage());
				respObj.put("success", resDao.isSuccess());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
			}

			// Store or retrieve existing OTP
			newOtp = loginUserService.storeOtpForForgotPassword(userDao, dateTimeFormat);
			logger.info("Stored OTP: {} for email: {}", newOtp, userDao.getEmailId());

//			otpUtil.sendOtpToEmail(userDao.getEmailId(), newOtp);
			
			respObj.put("message", resDao.getMessage());
			respObj.put("otp", newOtp);
			respObj.put("userId", resDao.getUserId());
			respObj.put("emailId", resDao.getEmailId());
			respObj.put("success", resDao.isSuccess());
			
		} catch (Exception e) {
			
			respObj.put("message", someThingWentWrong);
			respObj.put("userId", userDao.getUserId());
			respObj.put("emailId", userDao.getEmailId());
			respObj.put("success", false);
			
			logger.info("Some thing went wrong while checking Email ID : {} - {}", userDao.getEmailId(), e);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
	}

	@PostMapping("/verifyForGetPasOtp")
	public ResponseEntity<Map<String, Object>> verifyOtp(@RequestBody UserDao userDao) {
		ResponseDao resDao = new ResponseDao();
		Map<String, Object> respObj=new LinkedHashMap<String, Object>();

		logger.info("Verifying OTP for email: {}", userDao.getEmailId());
		try {
			
			resDao = loginUserService.verifyOtpForSignup(userDao, dateTimeFormat);
			
			if(resDao.isSuccess())
			{
				respObj.put("message", resDao.getMessage());
//				respObj.put("otp", newOtp);
				respObj.put("userId", resDao.getUserId());
				respObj.put("emailId", resDao.getEmailId());
				respObj.put("success", resDao.isSuccess());
				
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
			}
			else
			{
				respObj.put("message", resDao.getMessage());
//				respObj.put("otp", newOtp);
				respObj.put("userId", userDao.getUserId());
				respObj.put("emailId", userDao.getEmailId());
				respObj.put("success", resDao.isSuccess());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.NOT_FOUND);
			}
			

		} catch (Exception e) {
			
			respObj.put("message", someThingWentWrong);
//			respObj.put("otp", newOtp);
			respObj.put("userId", userDao.getUserId());
			respObj.put("emailId", userDao.getEmailId());
			respObj.put("success", false);
			logger.info("Some thing went wrong while Verifying Otp for Email ID : {} - {}", userDao.getEmailId(), e);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<Map<String, Object>> resetPassword(@RequestBody UserDao userDao) {
		ResponseDao resDao = new ResponseDao();
		Map<String, Object> respObj=new LinkedHashMap<String, Object>();

		if (!userDao.getPassword().equals(userDao.getConfirmPassword())) {
//			resDao.setMessage(passwordMismatch);
//			resDao.setSuccess(false);
			
			respObj.put("message", passwordMismatch);
//			respObj.put("otp", newOtp);
			respObj.put("userId", userDao.getUserId());
			respObj.put("emailId", userDao.getEmailId());
			respObj.put("success", false);
			
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.NOT_FOUND);
		}
		try {

			resDao = loginUserService.updatePassword(userDao, dateTimeFormat);
			resDao.setUserId(userDao.getUserId());
			if (resDao.isSuccess()) {
				
				respObj.put("message", resDao.getMessage());
//				respObj.put("otp", newOtp);
				respObj.put("userId", userDao.getUserId());
				respObj.put("emailId", userDao.getEmailId());
				respObj.put("success", resDao.isSuccess());
				
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
			} else {
				
				respObj.put("message", resDao.getMessage());
//				respObj.put("otp", newOtp);
				respObj.put("userId", userDao.getUserId());
				respObj.put("emailId", userDao.getEmailId());
				respObj.put("success", resDao.isSuccess());
				
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (Exception e) {
			
			respObj.put("message", someThingWentWrong);
			respObj.put("userId", userDao.getUserId());
			respObj.put("emailId", userDao.getEmailId());
			respObj.put("success", false);
			
			logger.info("Some thing went wrong while resetting the password for Email ID : {} - {}",
					userDao.getEmailId(), e);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}

	}

}