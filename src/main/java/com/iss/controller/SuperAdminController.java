package com.iss.controller;

import java.text.SimpleDateFormat;
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
import com.iss.dao.OnBoradingResDao;
import com.iss.header.HttpHeaders;
import com.iss.service.AdminAuthService;
import com.iss.service.BasicHealthQuestionService;
import com.iss.service.LoginUserService;
import com.iss.util.CommonUtility;

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

	@Autowired
	public BasicHealthQuestionService basicHealthQuestionService;

	@Autowired
	private CommonUtility otpUtil;

	@Autowired
	private AdminAuthService adminAuthService;

	@Autowired
	private LoginUserService loginUserService;

	private AdminResponseDao _respDao = new AdminResponseDao();

	private OnBoradingResDao _onBoardingResDao = new OnBoradingResDao();

	private static final Logger logger = LoggerFactory.getLogger(SuperAdminController.class);

	private String _curDate = "";

	@PostMapping("/getOnboardingQuestionsFromAdmin")
	public ResponseEntity<AdminResponseDao> adminHealth(@RequestBody List<BasicHealthQuestionDao> questionDao) {
		_curDate = getCurrDateTime();
		_respDao = new AdminResponseDao();
		try {
			basicHealthQuestionService.saveAll(questionDao, dateTimeFormat);

			_respDao.setSuccess(true);
			_respDao.setMessage(dataSentSuccess);
			logger.info("Got the onboarding questions successfully: {}", questionDao);
			return new ResponseEntity<AdminResponseDao>(_respDao, HttpStatus.OK);
		} catch (Exception e) {
			_respDao.setSuccess(false);
			_respDao.setMessage("Some thing went wrong");
			logger.info("Some thing went wrong while getting onboarding Questions ");
			return new ResponseEntity<AdminResponseDao>(_respDao, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/sendAllOnboardingQuestions")
	public ResponseEntity<OnBoradingResDao> sendOnboardingQuestions() {
		_curDate = getCurrDateTime();
		_onBoardingResDao = new OnBoradingResDao();
		try {
			List<BasicHealthQuestionDao> healList = basicHealthQuestionService.getAllOnBoardingHealthQuestions();
			_onBoardingResDao.setMessage("Sent Successfully");
			_onBoardingResDao.setSuccess(true);
			_onBoardingResDao.setList(healList);
			logger.info("Sent all onbaording questions successfully ");
			return new ResponseEntity<OnBoradingResDao>(_onBoardingResDao, HttpStatus.OK);
		} catch (Exception e) {
			_onBoardingResDao.setMessage("Something went wrong");
			_onBoardingResDao.setSuccess(true);
			logger.info("Some thing went wrong while Sending onboarding Questions : {}", e);
			return new ResponseEntity<OnBoradingResDao>(_onBoardingResDao, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/superAdminLogin")
	public ResponseEntity<Map<String, Object>> adminLogin(@RequestBody AdminUserDao dao, HttpServletResponse response) {
		_curDate = getCurrDateTime();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		HttpHeaders header = new HttpHeaders();
		try {
			_respDao = new AdminResponseDao();
			_respDao = adminAuthService.adminLoginService(dao, dateTimeFormat);
			if (_respDao.isSuccess()) {
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

				logger.info("Logging Admin for Email ID : {} - {}", dao.getEmail(), _respDao.getMessage());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
			} else {

				respObj.put("message", _respDao.getMessage());
				respObj.put("email", dao.getEmail());
				respObj.put("success", _respDao.isSuccess());
				logger.info("Logging Admin for Email ID : {} - {}", dao.getEmail(), _respDao.getMessage());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("email", dao.getEmail());
			respObj.put("success", _respDao.isSuccess());

			logger.info("Some thing went wrong while Logging In for Email ID : {} - {}", dao.getEmail(), e);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/superAdminForgetPassword")
	public ResponseEntity<Map<String, Object>> adminForgetPassword(@RequestBody AdminUserDao dao,
			HttpServletResponse response) {
		_curDate = getCurrDateTime();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		try {
			_respDao = adminAuthService.adminForgetPasswordService(dao, _curDate);
			logger.info("Admin Forget Password for Email ID : {} - {}", dao.getEmail(), _respDao.getMessage());
			if (_respDao.isSuccess()) {

//				otpUtil.sendOtpToEmail(dao.getEmail(), _respDao.getOtp());

				respObj.put("message", _respDao.getMessage());
				respObj.put("adminId", _respDao.getAdminId());
				respObj.put("email", _respDao.getEmail());
				respObj.put("otp", _respDao.getOtp());
				respObj.put("success", _respDao.isSuccess());

				logger.info("OTP : {} sent successfully to Email ID : {}", _respDao.getOtp(), _respDao.getEmail());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
			} else {

				respObj.put("message", _respDao.getMessage());
				respObj.put("email", dao.getEmail());
				respObj.put("success", _respDao.isSuccess());

				logger.info("OTP : {} sent successfully to Email ID : {}", _respDao.getOtp(), _respDao.getEmail());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("email", dao.getEmail());
			respObj.put("success", false);

			logger.info("Some thing went wrong while doing Forget Password for Email ID : {} - {}", dao.getEmail(), e);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/verifyOtpForSuperAdminForgetPas")
	public ResponseEntity<Map<String, Object>> adminVerifyForgetPasswordOtp(@RequestBody AdminUserDao dao) {
		_curDate = getCurrDateTime();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		try {
			_respDao = adminAuthService.adminVerifyForgetPasswordService(dao, dateTimeFormat);
			if (_respDao.isSuccess()) {
				logger.info("Verifying OTP : {} for Admin Password Reset for Email ID : {} - {}", dao.getAdminOTP(),
						dao.getEmail(), _respDao.getMessage());

				respObj.put("message", _respDao.getMessage());
				respObj.put("adminId", _respDao.getAdminId());
				respObj.put("email", _respDao.getEmail());
				respObj.put("success", _respDao.isSuccess());

				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
			} else {

				respObj.put("message", _respDao.getMessage());
				respObj.put("adminId", dao.getAdminId());
//				respObj.put("email", dao.getEmail());
				respObj.put("success", _respDao.isSuccess());

				logger.info("Verifying OTP : {} for Admin Password Reset for Email ID : {} - {}", dao.getAdminOTP(),
						dao.getEmail(), _respDao.getMessage());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("adminId", dao.getAdminId());
//			respObj.put("email", dao.getEmail());
			respObj.put("success", false);

			logger.info("Some thing went wrong while verifying OTP : {} for resetting Email ID : {}  - {}",
					dao.getAdminOTP(), dao.getEmail(), e);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/resetSuperAdminPassword")
	public ResponseEntity<Map<String, Object>> resetAdminPassword(@RequestBody AdminUserDao dao) {
		_curDate = getCurrDateTime();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		try {
			_respDao = adminAuthService.resetAdminPasswordService(dao, _curDate);
			if (_respDao.isSuccess()) {

				respObj.put("message", _respDao.getMessage());
				respObj.put("adminId", dao.getAdminId());
//				respObj.put("email", dao.getEmail());
//				respObj.put("admin", _respDao.isAdmin());
//				respObj.put("legalSetting", _respDao.isLegalSetting());
//				respObj.put("questionaries", _respDao.isQuestionaries());
//				respObj.put("userManagement", _respDao.isUserManagement());
				respObj.put("success", _respDao.isSuccess());

				logger.info("While resetting the Admin Forget Password for Email ID : {} - {}", dao.getEmail(),
						_respDao.getMessage());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
			} else {

				respObj.put("message", _respDao.getMessage());
				respObj.put("adminId", dao.getAdminId());
				respObj.put("success", _respDao.isSuccess());

				logger.info("While resetting the Admin Forget Password for Email ID : {} - {}", dao.getEmail(),
						_respDao.getMessage());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("adminId", dao.getAdminId());
			respObj.put("success", false);

			logger.info("Some thing went wrong while resetting Password :{} for Email ID : {} - {}", dao.getPassword(),
					dao.getEmail(), e);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
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
				resDao.setSuccess(false);
				return new ResponseEntity<AdminResponseDao>(resDao, HttpStatus.OK);
			} else {

				// Validate Confirm Password
				if (!dao.getPassword().equals(dao.getConfirmPassword())) {
					resDao.setSuccess(false);
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

					if (resDao.isSuccess()) {
						resDao.setMessage(emailOTP);
						logger.info("While Signup for Email ID : {} - {}", dao.getEmail(), _respDao.getMessage());
						return new ResponseEntity<AdminResponseDao>(resDao, HttpStatus.OK);
					} else {
						resDao.setMessage(emailVerifyFail);
						logger.info("While Signup for Email ID : {} - {}", dao.getEmail(), _respDao.getMessage());
						return new ResponseEntity<AdminResponseDao>(resDao, HttpStatus.NOT_FOUND);
					}
				} catch (Exception e) {
					System.out.println(e);
					resDao.setSuccess(false);
					resDao.setMessage(accountCreationFailure);
					logger.info("Some thing went wrong while Admin Signup OTP for Email ID : {} - {}", dao.getEmail(),
							e);
					return new ResponseEntity<AdminResponseDao>(resDao, HttpStatus.BAD_REQUEST);
				}

			}
		} catch (Exception e) {
			logger.info("Some thing went wrong while Admin Signup OTP for Email ID : {} - {}", dao.getEmail(), e);
			return new ResponseEntity<AdminResponseDao>(resDao, HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/verifySuperAdminSignupOtp")
	public ResponseEntity<AdminResponseDao> verifyOtpForSignup(@RequestBody AdminUserDao dao) {
		AdminResponseDao resDao = new AdminResponseDao();
		logger.info("Verifying OTP for email: {}", dao.getEmail());
		try {
			resDao = adminAuthService.verifyOtpForAdminSignup(dao, dateTimeFormat);

			if (resDao.isSuccess()) {
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

	@GetMapping("/getLegalSettings")
	public ResponseEntity<Map<String, Object>> getLegalSettings() {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		LegalSettingsRespDao respDao = new LegalSettingsRespDao();
		try {
			respDao = adminAuthService.getLegalSetting();
			if (respDao.isSuccess()) {

				respObj.put("policies", respDao.getLegalLists());
				respObj.put("message", respDao.getMsg());
				respObj.put("success", respDao.isSuccess());

				logger.info("Sending Legal Setting : {}", respDao.getMsg());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
			} else {
				respObj.put("message", respDao.getMsg());
				respObj.put("success", respDao.isSuccess());
				logger.info("Sending Legal Setting : {}", respDao.getMsg());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			respObj.put("message", someThingWentWrong);
			respObj.put("success", false);
			logger.info("Some thing went wrong while getting Legal Setting : {} - {}", someThingWentWrong, e);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/updateLegalSettings")
	public ResponseEntity<Map<String, Object>> updateLegalSettings(@RequestBody LegalSettingsDao dao) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		LegalSettingsRespDao respDao = new LegalSettingsRespDao();
		try {
			respDao = adminAuthService.updateLegalSettings(dao, dateTimeFormat);
			if (respDao.isSuccess()) {
				respObj.put("message", respDao.getMsg());
				respObj.put("success", respDao.isSuccess());

				logger.info("Sending Legal Setting : {}", respDao.getMsg());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
			} else {

				respObj.put("message", respDao.getMsg());
				respObj.put("success", respDao.isSuccess());
				logger.info("Sending Legal Setting : {}", respDao.getMsg());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("success", false);
			logger.info("Some thing went wrong while Updating Legal Setting : {} - {}", someThingWentWrong, e);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}
	}

}