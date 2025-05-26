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

import com.iss.dao.CompleteDetailsDao;
import com.iss.dao.ResponseDao;
import com.iss.dao.UserDao;
import com.iss.header.HttpHeaders;
import com.iss.service.LoginUserService;
import com.iss.service.UserPersonalDetailsService;
import com.iss.service.UserService;
import com.iss.util.CommonUtility;
import com.iss.util.EncryptedDecryptedObjectUtil;
import com.iss.validate.FieldValidadator;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class SignUpController {
	private static final Logger logger = LoggerFactory.getLogger(SignUpController.class);

	@Autowired
	private CommonUtility otpUtil;

	// Generating OTP
	String otp = CommonUtility.generateOTP();

	@Autowired
	public UserPersonalDetailsService userPersonalDetailsService;

	@Autowired
	public LoginUserService loginUserService;

	@Autowired
	public UserService userService;

	@Value("${ACCOUNT_EXISTS}")
	private String accountExists;

	@Value("${ACCOUNT_CREATION_SUCCESS}")
	private String accountCreationSuccess;

	@Value("${ACCOUNT_CREATION_FAILURE}")
	private String accountCreationFailure;

	@Value("${DATE_TIME_FORMAT}")
	private String dateTimeFormat;

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

	ResponseDao _resDao = new ResponseDao();

	@SuppressWarnings("unchecked")
	@PostMapping("/signup")
	public ResponseEntity<Map<String, Object>> signup(@RequestBody UserDao userDao) {
		ResponseDao resDao = new ResponseDao();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		UserDao decUserDao = new UserDao();
//		boolean isEmailSent = false;

		try {

			// Decrypting the UI Request here
			decUserDao = (UserDao) EncryptedDecryptedObjectUtil.getDecryptedObject(userDao, secretKey, secretIv,
					isEncryptDecryptReqRespData);

			// Searching the User Email Id here
			resDao = loginUserService.findByEmailIdForSignUp(decUserDao, resDao);
			if (Boolean.parseBoolean(resDao.getIsOnBoarded()) && Boolean.parseBoolean(resDao.getIsOTPVerified())) {

				respObj.put("message", accountExists);
				respObj.put("success", resDao.getIsSuccess());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
				logger.info("User Signup for Email ID :{} - {}", resDao.getEmailId(), resDao.getMessage());
				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			} else {

				// Validate Confirm Password
				if (!decUserDao.getPassword().equals(decUserDao.getConfirmPassword())) {
					resDao.setIsSuccess(Boolean.toString(false));
					resDao.setMessage(passwordMismatch);

					respObj.put("message", passwordMismatch);
					respObj.put("success", resDao.getIsSuccess());

					enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj,
							secretKey, secretIv, isEncryptDecryptReqRespData);
					logger.info("For :{} - {}", resDao.getEmailId(), resDao.getMessage());
					return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
				}
				// Generate & Send OTP
				try {
					// check whether email id exists in real world or not
//					boolean isExists = CommonUtility.checkExistanceOfMail(userDao.getEmailId());
//					if (!isExists) {
////						resDao.setSuccess(false);
////						resDao.setMessage(noEmailExists);
//
//						respObj.put("message", noEmailExists);
//						respObj.put("success", false);
//
//						logger.info("For :{} - {}", resDao.getEmailId(), resDao.getMessage());
//						return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
//					}

					if (Boolean.parseBoolean(resDao.getIsSuccess())) {
						_resDao = loginUserService.saveUserWithOTP(decUserDao, dateTimeFormat, true);

					} else {
						_resDao = loginUserService.saveUserWithOTP(decUserDao, dateTimeFormat, false);
					}

					// Sending OTP to an email
//					isEmailSent = otpUtil.sendOtpToEmail(userDao.getEmailId(), _resDao.getOtp());
					
//					if(!isEmailSent)
//					{
//						resDao.setMessage("Unable to send an Email");
//					}

					if (Boolean.parseBoolean(_resDao.getIsSuccess())) {
						resDao.setMessage(emailOTP);
						resDao.setIsSuccess(Boolean.toString(true));
					} else {
						resDao.setMessage(emailVerifyFail);
					}

					resDao.setOtp(_resDao.getOtp());
					resDao.setUserId(_resDao.getUserId());
					resDao.setEmailId(_resDao.getEmailId());

					respObj.put("message", resDao.getMessage());
					respObj.put("otp", resDao.getOtp());
					respObj.put("userId", resDao.getUserId());
					respObj.put("emailId", resDao.getEmailId());
					respObj.put("success", resDao.getIsSuccess());

					enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj,
							secretKey, secretIv, isEncryptDecryptReqRespData);

				} catch (Exception e) {

					respObj.put("message", someThingWentWrong);
					respObj.put("success", false);

					enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj,
							secretKey, secretIv, isEncryptDecryptReqRespData);
					logger.info("Some thing went wrong while Signing In for Email ID : {} - {}",
							decUserDao.getEmailId(), e);
					return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
				}

			}
		} catch (Exception e) {
			respObj.put("message", someThingWentWrong);
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Exception occurred while encrypting the response- {}", e1);
			}

			logger.error("Exception occurred while Signing In for Email ID : {} - {}", decUserDao.getEmailId(), e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}

		logger.info("For :{} - {}", resDao.getEmailId(), resDao.getMessage());
		return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/verifySignupOtp")
	public ResponseEntity<Map<String, Object>> verifyOtpForSignup(@RequestBody UserDao userDao,
			HttpServletResponse response) {
		ResponseDao resDao = new ResponseDao();
		HttpHeaders header = new HttpHeaders();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		UserDao decUserDao = new UserDao();

		try {
			// Decrypting the UI Request here
			decUserDao = (UserDao) EncryptedDecryptedObjectUtil.getDecryptedObject(userDao, secretKey, secretIv,
					isEncryptDecryptReqRespData);
			logger.info("Verifying OTP for email: {}", decUserDao.getEmailId());

			// Verifying the OTP here
			resDao = loginUserService.verifyOtpForSignup(decUserDao, dateTimeFormat);

			if (Boolean.parseBoolean(resDao.getIsSuccess())) {
				resDao.setIsOTPVerified(Boolean.toString(true));

				// generating token
				header = loginUserService.generateToken(resDao.getUserId(), resDao.getEmailId(), dateTimeFormat);

				response.setHeader("accessToken", header.getAccessToken());

				respObj.put("message", resDao.getMessage());
				respObj.put("userId", resDao.getUserId());
				respObj.put("emailId", resDao.getEmailId());
				respObj.put("success", resDao.getIsSuccess());
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				logger.info("Verifying OTP for email: {}", decUserDao.getEmailId());
				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			} else {
				respObj.put("message", resDao.getMessage());
				respObj.put("success", resDao.getIsSuccess());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				logger.info("Verifying OTP for email: {}", decUserDao.getEmailId());
				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			}
		} catch (Exception e) {
			respObj.put("message", someThingWentWrong);
			respObj.put("success", resDao.getIsSuccess());

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Exception occurred while encrypting the response- {}", e1);
			}

			logger.error("Exception occurred while verifying SignIn OTP for Email ID : {} - {}",
					decUserDao.getEmailId(), e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/continueSignup")
	public ResponseEntity<Map<String, Object>> continueSinupUser(@RequestBody CompleteDetailsDao compDetailsDao) {
		ResponseDao resDao = new ResponseDao();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		CompleteDetailsDao decCompDetailsDao = new CompleteDetailsDao();
		Map<String, String> valFields = new LinkedHashMap<String, String>();
//		boolean isEmailSent = false;

		try {

			// validation for the fields
			valFields = FieldValidadator.validateFields(compDetailsDao.getPerDetailsDao());

			String res = valFields.get("success");
			if (!Boolean.parseBoolean(res)) {

				respObj.put("msg", valFields.get("msg"));
				respObj.put("success", Boolean.parseBoolean(res));
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			}

			decCompDetailsDao = (CompleteDetailsDao) EncryptedDecryptedObjectUtil.getDecryptedObject(compDetailsDao,
					secretKey, secretIv, isEncryptDecryptReqRespData);

			resDao = userPersonalDetailsService.insertUserPersonalData(decCompDetailsDao.getUserDao(),
					decCompDetailsDao.getPerDetailsDao(), decCompDetailsDao.getHelthDetailsListDao(), dateTimeFormat);

			if (Boolean.parseBoolean(resDao.getIsSuccess())) {
				// sending created profile data to an email
//				isEmailSent=otpUtil.sendOnboardingSuccessToEmail(compDetailsDao.getUserDao().getEmailId());

//				if (!isEmailSent) {
//					resDao.setMessage("Unable to send an Email");
//				}
				
				respObj.put("message", resDao.getMessage());
				respObj.put("userId", resDao.getUserId());
				respObj.put("emailId", resDao.getEmailId());
//				respObj.put("sdkInfo", resDao.getSdkInfo());
				respObj.put("commonUserDetailsDao", resDao.getCommonUserDetailsDao());
				respObj.put("success", resDao.getIsSuccess());
				
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				logger.info("Creating account : {}", decCompDetailsDao.getUserDao().getEmailId());
				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			} else {

				respObj.put("message", resDao.getMessage());
				respObj.put("success", resDao.getIsSuccess());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				logger.info("Creating account : {}", decCompDetailsDao.getUserDao().getEmailId());
				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			}
		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Exception occurred while encrypting the response- {}", e1);
			}

			logger.error("Exception occurred while verifying SignIn OTP for Email ID : {} - {}",
					decCompDetailsDao.getUserDao().getEmailId(), e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}

	}

}
