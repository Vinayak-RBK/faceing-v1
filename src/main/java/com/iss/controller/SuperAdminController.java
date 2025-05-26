package com.iss.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.iss.dao.AdminResponseDao;
import com.iss.dao.AdminUserDao;
import com.iss.dao.BasicHealthQuestionDao;
import com.iss.dao.LegalSettingsDao;
import com.iss.dao.LegalSettingsRespDao;
import com.iss.header.HttpHeaders;
import com.iss.service.AdminAuthService;
import com.iss.service.BasicHealthQuestionService;
import com.iss.service.LoginUserService;
import com.iss.util.CommonUtility;
import com.iss.util.EncryptedDecryptedObjectUtil;
import com.iss.validate.FieldValidadator;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class SuperAdminController {

	@Value("${DATE_TIME_FORMAT}")
	private String dateTimeFormat;

	@Value("${DATA_SEND_SUCCESS}")
	private String dataSentSuccess;

	@Value("${ACCOUNT_EXISTS}")
	private String accountExists;

	@Value("${ACCOUNT_CREATION_SUCCESS}")
	private String accountCreationSuccess;

	@Value("${ACCOUNT_CREATION_FAILURE}")
	private String accountCreationFailure;

	@Value("${PASSWORD_MISMATCH}")
	private String passwordMismatch;

	@Value("${EMAIL_OTP}")
	private String emailOTP;

	@Value("${OTP_NOT_MATCH}")
	private String otpNotMatch;

	@Value("${OTP_VERIFIED}")
	private String otpVerified;

	@Value("${EMAIL_VERIFY_FAIL}")
	private String emailVerifyFail;

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
	public BasicHealthQuestionService basicHealthQuestionService;

	@Autowired
	private CommonUtility otpUtil;

	@Autowired
	private AdminAuthService adminAuthService;

	@Autowired
	private LoginUserService loginUserService;

	private AdminResponseDao _respDao = new AdminResponseDao();

	private static final Logger logger = LoggerFactory.getLogger(SuperAdminController.class);

	private String _curDate = "";

	@PostMapping("/getOnboardingQuestionsFromAdmin")
	public ResponseEntity<?> adminHealth(@RequestBody List<BasicHealthQuestionDao> questionDao) {
		_curDate = getCurrDateTime();
		_respDao = new AdminResponseDao();

		try {
			// Saving  the On boarding questions here
			basicHealthQuestionService.saveAll(questionDao, dateTimeFormat);

			_respDao.setSuccess(Boolean.toString(true));
			_respDao.setMessage(dataSentSuccess);
			
			logger.info("Got the onboarding questions successfully: {}", questionDao);
			return new ResponseEntity<AdminResponseDao>(_respDao, HttpStatus.OK);
		} catch (Exception e) {
			_respDao.setSuccess(Boolean.toString(false));
			_respDao.setMessage("Some thing went wrong");
			
			logger.error("Exception occurred while getting onboarding Questions ");
			return new ResponseEntity<AdminResponseDao>(_respDao, HttpStatus.BAD_REQUEST);
		}

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/sendAllOnboardingQuestions")
	public ResponseEntity<?> sendOnboardingQuestions() {
		_curDate = getCurrDateTime();

		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		List<BasicHealthQuestionDao> healthList = new ArrayList<BasicHealthQuestionDao>();
		List<BasicHealthQuestionDao> enHealthList = new ArrayList<BasicHealthQuestionDao>();
		BasicHealthQuestionDao healthDao = new BasicHealthQuestionDao();

		try {
			// Fetching all the on boarding questions here
			healthList = basicHealthQuestionService.getAllOnBoardingHealthQuestions();

			respObj.put("message", "Sent Successfully");
			respObj.put("success", Boolean.toString(true));
			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			for (BasicHealthQuestionDao dao : healthList) {
				healthDao = (BasicHealthQuestionDao) EncryptedDecryptedObjectUtil.getEncryptedObject(dao, secretKey,
						secretIv, isEncryptDecryptReqRespData);
				enHealthList.add(healthDao);
			}

			enRespObj.put("list", enHealthList);

			logger.info("Sent all onbaording questions successfully ");
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("success", Boolean.toString(false));

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Exception occurred while encrypting the response- {}", e1);
			}

			logger.error("Exception occurred while Sending onboarding Questions : {}", e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/superAdminLogin")
	public ResponseEntity<?> adminLogin(@RequestBody AdminUserDao dao, HttpServletResponse response) {
		_curDate = getCurrDateTime();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		HttpHeaders header = new HttpHeaders();
		Map<String, String> valFields = new LinkedHashMap<String, String>();
		AdminUserDao decDao = new AdminUserDao();

		try {

			// validation for the fields
			valFields = FieldValidadator.validateFields(dao);

			String res = valFields.get("success");
			if (!Boolean.parseBoolean(res)) {
				System.out.println("Everything is fine");

				respObj.put("msg", valFields.get("msg"));
				respObj.put("success", Boolean.parseBoolean(res));
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			}

			decDao = (AdminUserDao) EncryptedDecryptedObjectUtil.getDecryptedObject(dao, secretKey, secretIv,
					isEncryptDecryptReqRespData);

			_respDao = new AdminResponseDao();
			_respDao = adminAuthService.adminLoginService(decDao, dateTimeFormat);
			if (Boolean.parseBoolean(_respDao.isSuccess())) {
				// Generating tokens once after success
				header = loginUserService.generateToken(_respDao.getAdminId(), _respDao.getEmail(), dateTimeFormat);

				response.setHeader("accessToken", header.getAccessToken());
				response.setHeader("refreshToken", header.getRefreshToken());

				respObj.put("message", _respDao.getMessage());
				respObj.put("adminId", _respDao.getAdminId());
				respObj.put("email", _respDao.getEmail());
				respObj.put("admin", _respDao.isAdmin());
				respObj.put("userManagement", _respDao.isUserManagement());
				respObj.put("questionaries", _respDao.isQuestionaries());
				respObj.put("legalSetting", _respDao.isLegalSetting());
				respObj.put("success", _respDao.isSuccess());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				logger.info("Logging Admin for Email ID : {} - {}", dao.getEmail(), _respDao.getMessage());
				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			} else {

				respObj.put("message", _respDao.getMessage());
				respObj.put("email", dao.getEmail());
				respObj.put("success", _respDao.isSuccess());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
				logger.info("Logging Admin for Email ID : {} - {}", dao.getEmail(), _respDao.getMessage());
				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			}
		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("email", dao.getEmail());
			respObj.put("success", _respDao.isSuccess());

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Exception occurred while encrypting the response- {}", e1);
			}

			logger.error("Exception occurred while Logging In for Email ID : {} - {}", dao.getEmail(), e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/superAdminForgetPassword")
	public ResponseEntity<?> adminForgetPassword(@RequestBody AdminUserDao dao, HttpServletResponse response) {
		_curDate = getCurrDateTime();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		AdminUserDao decDao = new AdminUserDao();

		try {

			decDao = (AdminUserDao) EncryptedDecryptedObjectUtil.getDecryptedObject(dao, secretKey, secretIv,
					isEncryptDecryptReqRespData);

			_respDao = adminAuthService.adminForgetPasswordService(decDao, _curDate);
			logger.info("Admin Forget Password for Email ID : {} - {}", dao.getEmail(), _respDao.getMessage());
			if (Boolean.parseBoolean(_respDao.isSuccess())) {

//				otpUtil.sendOtpToEmail(dao.getEmail(), _respDao.getOtp());

				respObj.put("message", _respDao.getMessage());
				respObj.put("adminId", _respDao.getAdminId());
				respObj.put("email", _respDao.getEmail());
				respObj.put("otp", _respDao.getOtp());
				respObj.put("success", _respDao.isSuccess());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				logger.info("OTP : {} sent successfully to Email ID : {}", _respDao.getOtp(), _respDao.getEmail());
				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			} else {

				respObj.put("message", _respDao.getMessage());
				respObj.put("email", dao.getEmail());
				respObj.put("success", _respDao.isSuccess());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				logger.info("OTP : {} sent successfully to Email ID : {}", _respDao.getOtp(), _respDao.getEmail());
				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			}

		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("email", dao.getEmail());
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Exception occurred while encrypting the response- {}", e1);
			}

			logger.error("Exception occurred while doing Forget Password for Email ID : {} - {}", dao.getEmail(), e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/verifyOtpForSuperAdminForgetPas")
	public ResponseEntity<?> adminVerifyForgetPasswordOtp(@RequestBody AdminUserDao dao) {
		_curDate = getCurrDateTime();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();

		AdminUserDao decDao = new AdminUserDao();

		try {

			decDao = (AdminUserDao) EncryptedDecryptedObjectUtil.getDecryptedObject(dao, secretKey, secretIv,
					isEncryptDecryptReqRespData);
			_respDao = adminAuthService.adminVerifyForgetPasswordService(decDao, dateTimeFormat);
			if (Boolean.parseBoolean(_respDao.isSuccess())) {
				logger.info("Verifying OTP : {} for Admin Password Reset for Email ID : {} - {}", dao.getAdminOTP(),
						dao.getEmail(), _respDao.getMessage());

				respObj.put("message", _respDao.getMessage());
				respObj.put("adminId", _respDao.getAdminId());
				respObj.put("email", _respDao.getEmail());
				respObj.put("success", _respDao.isSuccess());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			} else {

				respObj.put("message", _respDao.getMessage());
				respObj.put("adminId", dao.getAdminId());
				respObj.put("success", _respDao.isSuccess());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				logger.info("Verifying OTP : {} for Admin Password Reset for Email ID : {} - {}", dao.getAdminOTP(),
						dao.getEmail(), _respDao.getMessage());
				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			}

		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("adminId", dao.getAdminId());
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Exception occurred while encrypting the response- {}", e1);
			}

			logger.error("Exception occurred while verifying OTP : {} for resetting Email ID : {}  - {}",
					dao.getAdminOTP(), dao.getEmail(), e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}

	}

	@SuppressWarnings("unchecked")
	@PostMapping("/resetSuperAdminPassword")
	public ResponseEntity<?> resetAdminPassword(@RequestBody AdminUserDao dao) {
		_curDate = getCurrDateTime();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();

		AdminUserDao decDao = new AdminUserDao();

		try {

			decDao = (AdminUserDao) EncryptedDecryptedObjectUtil.getDecryptedObject(dao, secretKey, secretIv,
					isEncryptDecryptReqRespData);

			_respDao = adminAuthService.resetAdminPasswordService(decDao, _curDate);

			if (Boolean.parseBoolean(_respDao.isSuccess())) {

				respObj.put("message", _respDao.getMessage());
				respObj.put("adminId", dao.getAdminId());
				respObj.put("success", _respDao.isSuccess());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				logger.info("While resetting the Admin Forget Password for Email ID : {} - {}", dao.getEmail(),
						_respDao.getMessage());
				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			} else {

				respObj.put("message", _respDao.getMessage());
				respObj.put("adminId", dao.getAdminId());
				respObj.put("success", _respDao.isSuccess());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				logger.info("While resetting the Admin Forget Password for Email ID : {} - {}", dao.getEmail(),
						_respDao.getMessage());
				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			}
		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("adminId", dao.getAdminId());
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Exception occurred while encrypting the response- {}", e1);
			}

			logger.error("Exception occurred while resetting Password :{} for Email ID : {} - {}", dao.getPassword(),
					dao.getEmail(), e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}

	}

	private String getCurrDateTime() {
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());

		return currentDate;
	}

	@PostMapping("/superAdminSignup")
	public ResponseEntity<AdminResponseDao> sineup(@RequestBody AdminUserDao dao, HttpServletResponse response) {
		AdminResponseDao resDao = new AdminResponseDao();
		int count = adminAuthService.findByEmailIdForAdminSignUp(dao);
		try {
			if (count == 1) {
				resDao.setMessage(accountExists);
				resDao.setSuccess(Boolean.toString(false));
				return new ResponseEntity<AdminResponseDao>(resDao, HttpStatus.OK);
			} else {

				// Validate Confirm Password
				if (!dao.getPassword().equals(dao.getConfirmPassword())) {
					resDao.setSuccess(Boolean.toString(false));
					resDao.setMessage(passwordMismatch);
					return ResponseEntity.badRequest().body(resDao);
				}
				// Generate & Send OTP
				try {
					// check whether email id exists in real world or not
//					boolean isExists = CommonUtility.checkExistanceOfMail(dao.getEmail());
//					if (!isExists) {
//						resDao.setSuccess(false);
//						resDao.setMessage("No Such Email ID exists in the World");
//						return new ResponseEntity<AdminResponseDao>(resDao, HttpStatus.OK);
//					}

					if (count == 0) {
						resDao = adminAuthService.saveAdminWithOTP(dao, dateTimeFormat, true);
					} else {
						resDao = adminAuthService.saveAdminWithOTP(dao, dateTimeFormat, false);
					}

//					otpUtil.sendOtpToEmail(dao.getEmail(), resDao.getOtp());

					if (Boolean.parseBoolean(resDao.isSuccess())) {
						resDao.setMessage(emailOTP);
						logger.info("While Signup for Email ID : {} - {}", dao.getEmail(), _respDao.getMessage());
						return new ResponseEntity<AdminResponseDao>(resDao, HttpStatus.OK);
					} else {
						resDao.setMessage(emailVerifyFail);
						logger.info("While Signup for Email ID : {} - {}", dao.getEmail(), _respDao.getMessage());
						return new ResponseEntity<AdminResponseDao>(resDao, HttpStatus.OK);
					}
				} catch (Exception e) {
					System.out.println(e);
					resDao.setSuccess(Boolean.toString(false));
					resDao.setMessage(accountCreationFailure);
					logger.info("Exception occurred while Admin Signup OTP for Email ID : {} - {}", dao.getEmail(),
							e);
					return new ResponseEntity<AdminResponseDao>(resDao, HttpStatus.BAD_REQUEST);
				}

			}
		} catch (Exception e) {
			logger.info("Exception occurred while Admin Signup OTP for Email ID : {} - {}", dao.getEmail(), e);
			return new ResponseEntity<AdminResponseDao>(resDao, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/verifySuperAdminSignupOtp")
	public ResponseEntity<AdminResponseDao> verifyOtpForSignup(@RequestBody AdminUserDao dao) {
		AdminResponseDao resDao = new AdminResponseDao();
		logger.info("Verifying OTP for email: {}", dao.getEmail());
		try {
			resDao = adminAuthService.verifyOtpForAdminSignup(dao, dateTimeFormat);

			if (Boolean.parseBoolean(resDao.isSuccess())) {
				logger.info("Verifying Admin Signup for email: {} - {}", dao.getEmail(), resDao.getMessage());
				return new ResponseEntity<AdminResponseDao>(resDao, HttpStatus.OK);
			} else {
				logger.info("Verifying Admin Signup for email: {} - {}", dao.getEmail(), resDao.getMessage());
				return new ResponseEntity<AdminResponseDao>(resDao, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			logger.info("Some thing went wrong while Verifying Admin Signup OTP for Email ID : {} - {}", dao.getEmail(),
					e);
			return new ResponseEntity<AdminResponseDao>(resDao, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("unchecked")
	@GetMapping("/getLegalSettings")
	public ResponseEntity<?> getLegalSettings() {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		LegalSettingsRespDao respDao = new LegalSettingsRespDao();

		try {
			respDao = adminAuthService.getLegalSetting();
			if (Boolean.parseBoolean(respDao.getSuccess())) {

				respObj.put("policies", respDao.getLegalLists());
				respObj.put("message", respDao.getMsg());
				respObj.put("success", respDao.getSuccess());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				logger.info("Sending Legal Setting : {}", respDao.getMsg());
				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			} else {
				respObj.put("message", respDao.getMsg());
				respObj.put("success", respDao.getSuccess());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
				logger.info("Sending Legal Setting : {}", respDao.getMsg());
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
			logger.error("Exception occurred while getting Legal Setting : {} - {}", someThingWentWrong, e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}
	}

	@SuppressWarnings("unchecked")
	@PutMapping("/updateLegalSettings")
	public ResponseEntity<?> updateLegalSettings(@RequestBody LegalSettingsDao dao) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		LegalSettingsRespDao respDao = new LegalSettingsRespDao();

		try {
			respDao = adminAuthService.updateLegalSettings(dao, dateTimeFormat);
			if (Boolean.parseBoolean(respDao.getSuccess())) {
				respObj.put("message", respDao.getMsg());
				respObj.put("success", respDao.getSuccess());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				logger.info("Sending Legal Setting : {}", respDao.getMsg());
				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			} else {

				respObj.put("message", respDao.getMsg());
				respObj.put("success", respDao.getSuccess());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
				logger.info("Sending Legal Setting : {}", respDao.getMsg());
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
			logger.error("Exception occurred while Updating Legal Setting : {} - {}", someThingWentWrong, e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}
	}

}