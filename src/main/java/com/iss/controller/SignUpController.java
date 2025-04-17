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

	ResponseDao _resDao = new ResponseDao();

	@PostMapping("/signup")
	public ResponseEntity<Map<String, Object>> sineup(@RequestBody UserDao userDao) {
		ResponseDao resDao = new ResponseDao();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();

		resDao = loginUserService.findByEmailIdForSignUp(userDao, resDao);
		try {
			if (resDao.isOnBoarded() && resDao.isOTPVerified()) {

				respObj.put("message", accountExists);
				respObj.put("success", resDao.isSuccess());

//				resDao.setMessage(accountExists);
//				resDao.setSuccess(false);
				logger.info("User Signup for Email ID :{} - {}", resDao.getEmailId(), resDao.getMessage());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
			} else {

				// Validate Confirm Password
				if (!userDao.getPassword().equals(userDao.getConfirmPassword())) {
					resDao.setSuccess(false);
					resDao.setMessage(passwordMismatch);

					respObj.put("message", passwordMismatch);
					respObj.put("success", resDao.isSuccess());

//					return ResponseEntity.badRequest().body(resDao);
					logger.info("For :{} - {}", resDao.getEmailId(), resDao.getMessage());
					return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
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

					if (resDao.isSuccess()) {
						_resDao = loginUserService.saveUserWithOTP(userDao, dateTimeFormat, true);

					} else {
						_resDao = loginUserService.saveUserWithOTP(userDao, dateTimeFormat, false);
					}

//					otpUtil.sendOtpToEmail(userDao.getEmailId(), _resDao.getOtp());

					if (_resDao.isSuccess()) {
						resDao.setMessage(emailOTP);
						resDao.setSuccess(true);
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
//					respObj.put("blocked", resDao.isBlocked());
//					respObj.put("onBoarded", resDao.isOnBoarded());
//					respObj.put("otpverified", resDao.isOTPVerified());
					respObj.put("success", resDao.isSuccess());

				} catch (Exception e) {
					System.out.println(e);
//					resDao.setSuccess(false);
//					resDao.setMessage(accountCreationFailure);

					respObj.put("message", someThingWentWrong);
					respObj.put("success", false);
					
					logger.info("Some thing went wrong while Signing In for Email ID : {} - {}", userDao.getEmailId(), e);
					return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
				}

			}
		} catch (Exception e) {
			System.out.println(e);

			respObj.put("message", someThingWentWrong);
			respObj.put("success", false);

			logger.info("Some thing went wrong while Signing In for Email ID : {} - {}", userDao.getEmailId(), e);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}
		logger.info("For :{} - {}", resDao.getEmailId(), resDao.getMessage());
		return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
	}

	@PostMapping("/verifySignupOtp")
	public ResponseEntity<Map<String, Object>> verifyOtpForSignup(@RequestBody UserDao userDao,
			HttpServletResponse response) {
		ResponseDao resDao = new ResponseDao();
		HttpHeaders header = new HttpHeaders();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		try {
			logger.info("Verifying OTP for email: {}", userDao.getEmailId());
			resDao = loginUserService.verifyOtpForSignup(userDao, dateTimeFormat);

			if (resDao.isSuccess()) {
				resDao.setOTPVerified(true);

				// generating token
				header = loginUserService.generateToken(_resDao.getUserId(), _resDao.getEmailId(), dateTimeFormat);

				response.setHeader("accessToken", header.getAccessToken());
				response.setHeader("refreshToken", header.getRefreshToken());

//				response.addCookie(new Cookie("accessToken", header.getAccessToken()));
//				response.addCookie(new Cookie("refreshToken", header.getRefreshToken()));

				respObj.put("message", resDao.getMessage());
				respObj.put("userId", resDao.getUserId());
				respObj.put("emailId", resDao.getEmailId());
//				respObj.put("otpverified", resDao.isOTPVerified());
//				respObj.put("blocked", resDao.isBlocked());
//				respObj.put("onBoarded", resDao.isOnBoarded());
				respObj.put("success", resDao.isSuccess());

				logger.info("Verifying OTP for email: {}", userDao.getEmailId());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
			} else {
				respObj.put("message", resDao.getMessage());
//				respObj.put("userId", resDao.getUserId());
//				respObj.put("emailId", resDao.getEmailId());
//				respObj.put("otpverified", resDao.isOTPVerified());
//				respObj.put("blocked", resDao.isBlocked());
//				respObj.put("onBoarded", resDao.isOnBoarded());
				respObj.put("success", resDao.isSuccess());

				logger.info("Verifying OTP for email: {}", userDao.getEmailId());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			respObj.put("message", someThingWentWrong);
			respObj.put("success", resDao.isSuccess());
			logger.info("Some thing went wrong while verifying SignIn OTP for Email ID : {} - {}", userDao.getEmailId(),
					e);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/continueSignup")
	public ResponseEntity<Map<String, Object>> continueSinupUser(
			@RequestBody CompleteDetailsDao compDetailsDao) {
		ResponseDao resDao = new ResponseDao();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();

		try {
			resDao = userPersonalDetailsService.insertUserPersonalData(compDetailsDao.getUserDao(),
					compDetailsDao.getPerDetailsDao(), compDetailsDao.getHelthDetailsListDao(), dateTimeFormat);

			if (resDao.isSuccess()) {

				respObj.put("message", resDao.getMessage());
				respObj.put("userId", resDao.getUserId());
				respObj.put("emailId", resDao.getEmailId());
				respObj.put("sdkInfo", resDao.getSdkInfo());
				respObj.put("commonUserDetailsDao", resDao.getCommonUserDetailsDao());
//				respObj.put("otpverified", resDao.getEmailId());
//				respObj.put("blocked", resDao.getEmailId());
//				respObj.put("onBoarded", resDao.getEmailId());
				respObj.put("success", resDao.isSuccess());

				logger.info("Creating account : {}", compDetailsDao.getUserDao().getEmailId());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
			} else {

				respObj.put("message", resDao.getMessage());
//				respObj.put("userId", resDao.getUserId());
//				respObj.put("emailId", resDao.getEmailId());
//				respObj.put("sdkInfo", resDao.getEmailId());
//				respObj.put("commonUserDetailsDao", resDao.getEmailId());
				respObj.put("success", resDao.isSuccess());

				logger.info("Creating account : {}", compDetailsDao.getUserDao().getEmailId());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("success", false);

			logger.info("Some thing went wrong after verifying SignIn OTP for Email ID : {} - {}",
					compDetailsDao.getUserDao().getEmailId(), e);
			System.out.println(e);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}

	}

}
