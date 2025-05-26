package com.iss.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iss.dao.Response;
import com.iss.dao.ResponseDao;
import com.iss.dao.UserDao;
import com.iss.dao.UserProfileRespDao;
import com.iss.header.HttpHeaders;
import com.iss.service.LoginUserService;
import com.iss.service.TokenBlacklistService;
import com.iss.service.UserSystemIPAddressService;
import com.iss.util.EncryptedDecryptedObjectUtil;
import com.iss.util.JwtUtil;
import com.iss.validate.FieldValidadator;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class LoginUserController {

	@Value("${USER_NOT_FOUND}")
	private String userNotFound;

	@Value("${USER_WRONG_PASSWORD}")
	private String userWrongPassword;

	@Value("${USER_SUCCESSFUL_LOGIN}")
	private String successLoggedIn;

	@Value("${DELETE_TIME_UNVERIFIED}")
	private int deleteTimeUnverified;

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

	@Autowired
	private LoginUserService loginUserService;

	@Autowired
	public TokenBlacklistService blacklistService;

	@Autowired
	public UserSystemIPAddressService userSystemIPAddressService;

	@Autowired
	private JwtUtil jwtUtil;

	private static final Logger logger = LoggerFactory.getLogger(LoginUserController.class);

	@SuppressWarnings("unchecked")
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> loginUser(@RequestBody UserDao userDao, HttpServletResponse response,
			HttpServletRequest request) {

		String userSystemIp = request.getRemoteAddr();

		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		UserDao decUserDao = new UserDao();
		String path = "";

		ResponseDao resDao = new ResponseDao();
		HttpHeaders header = new HttpHeaders();
		Response resp = new Response();
		Map<String, String> valFields = new LinkedHashMap<String, String>();

		try {

			// Checking empty validation for the fields
			valFields = FieldValidadator.validateFields(userDao);

			// getting the value of key Success
			String res = valFields.get("success");

			if (!Boolean.parseBoolean(res)) {

				respObj.put("msg", valFields.get("msg"));
				respObj.put("success", Boolean.parseBoolean(res));
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				logger.info("While validating the fields - {}", respObj.get("msg"));

				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			}

			// Decrypting the UI request
			decUserDao = (UserDao) EncryptedDecryptedObjectUtil.getDecryptedObject(userDao, secretKey, secretIv,
					isEncryptDecryptReqRespData);

			// Checking whether the revieved site ip is blocked or not
			resp = userSystemIPAddressService.checkReceivedSiteIsBlocked(userSystemIp);

			if (!Boolean.parseBoolean(resp.isSuccess())) {
				respObj.put("msg", resp.getMsg());
				respObj.put("success", resp.isSuccess());

				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);

				logger.info("While Checking the System IP block or not - {}", respObj.get("msg"));
				return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
			}

			// Searching User by Email Id
			resDao = loginUserService.findByEmailId(decUserDao, true, dateTimeFormat);

			if (Boolean.parseBoolean(resDao.getIsSuccess())) {

				if (Boolean.parseBoolean(resDao.getIsOnBoarded()) && Boolean.parseBoolean(resDao.getIsOTPVerified())) {
					path = resDao.getCommonUserDetailsDao().getUserImage().replace("\\", "/");
					path = path.replace(":/", "://");
					respObj.put("sdkInfo", resDao.getSdkInfo());
					resDao.getCommonUserDetailsDao().setUserImage(path);
					respObj.put("commonUserDetailsDao", resDao.getCommonUserDetailsDao());
					respObj.put("pushNotify", resDao.getPushNotify());
				}

				respObj.put("userId", resDao.getUserId());
				respObj.put("emailId", resDao.getEmailId());
				respObj.put("sdkInfo", resDao.getSdkInfo());

				logger.info("User Login for Email ID :{} - {}", decUserDao.getEmailId(), resDao.getMessage());
				logger.info("SuccessFully Generated token for the user :{} - {}", decUserDao.getEmailId());

				// Generating access token
				header = loginUserService.generateToken(resDao.getUserId(), resDao.getEmailId(), dateTimeFormat);

				response.setHeader("accessToken", header.getAccessToken());
//				response.setHeader("refreshToken", header.getRefreshToken());

			} else {
				// Updating End User System IP after wrong attempt
				userSystemIPAddressService.saveOrUpdateUserSystemIpAdressService(userSystemIp, dateTimeFormat);

				logger.info("Unable to Login for Email ID :{} - {}", decUserDao.getEmailId(), resDao.getMessage());
			}

			respObj.put("message", resDao.getMessage());
			respObj.put("otpverified", resDao.getIsOTPVerified());
			respObj.put("onBoarded", resDao.getIsOnBoarded());
			respObj.put("blocked", resDao.getIsBlocked());
			respObj.put("success", resDao.getIsSuccess());

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);

		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("otpverified", resDao.getIsOTPVerified());
			respObj.put("onBoarded", resDao.getIsOnBoarded());
			respObj.put("blocked", resDao.getIsBlocked());
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Error occured while encrypting the response- {}", e1);
			}

			logger.info("Some thing went wrong while Logging In for Email ID : {} - {}", decUserDao.getEmailId(), e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}

	}

	@SuppressWarnings("unchecked")
	@PutMapping(value = "/editProfile/{userId}", consumes = "multipart/form-data")
	public ResponseEntity<?> editUserProfilePicture(@RequestParam("file") MultipartFile fileName,
			@RequestParam("isSignup") boolean isForSignup, @PathVariable String userId) {

		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		UserProfileRespDao respDao = new UserProfileRespDao();

		try {
			// Updating the User Profile Picture
			respDao = loginUserService.updateProfileByUserId(userId, dateTimeFormat, fileName, isForSignup);

			respObj.put("message", respDao.getMessage());
			respObj.put("userId", userId);
			respObj.put("success", respDao.isSuccess());

			if (Boolean.parseBoolean(respDao.isSuccess())) {
				respObj.put("imagePath", respDao.getImagePath());
			}

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			logger.info("Updating user profile picture - {}", respDao.getMessage());
			return new ResponseEntity<>(enRespObj, HttpStatus.OK);

		} catch (Exception e) {
			respObj.put("message", someThingWentWrong);
			respObj.put("userId", userId);
			respObj.put("success", Boolean.toString(false));

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Error occured while encrypting the response- {}", e1);
			}

			return new ResponseEntity<>(enRespObj, HttpStatus.BAD_REQUEST);
		}

	}

	@SuppressWarnings("unchecked")
	@GetMapping("/getTokenByRefreshToken")
	public ResponseEntity<?> getTokensByRefreshToken(@RequestBody HttpHeaders httpHeader,
			HttpServletResponse response) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		Map<String, Object> enRespObj = new LinkedHashMap<String, Object>();
		logger.info("Received request to refresh tokens.");

		try {
			if (!jwtUtil.validateToken(httpHeader.getRefreshToken())) {

				String enMessage = (String) EncryptedDecryptedObjectUtil.getEncryptedString("Refresh token has expired",
						secretKey, secretIv, isEncryptDecryptReqRespData);
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(enMessage);
			}

			if (blacklistService.isTokenBlacklisted(httpHeader.getRefreshToken())) {

				String enMessage = (String) EncryptedDecryptedObjectUtil.getEncryptedString("Invalid refresh token",
						secretKey, secretIv, isEncryptDecryptReqRespData);

				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(enMessage);
			}

			String userId = jwtUtil.extractUsername(httpHeader.getRefreshToken());
			httpHeader.setUserId(userId);

			HttpHeaders header = new HttpHeaders();

			header = loginUserService.generateTokensAfterAccessTokenExpiryByRefreshToken(httpHeader, dateTimeFormat);

			response.setHeader("accessToken", header.getAccessToken());

			respObj.put("msg", "Successfully generated new tokens.");
			respObj.put("success", true);

			enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
					secretIv, isEncryptDecryptReqRespData);

			logger.info("Successfully generated new tokens, access Token : {} & refresh Token : {} ",
					header.getAccessToken(), header.getRefreshToken());
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.OK);
		} catch (Exception e) {

			respObj.put("msg", someThingWentWrong);
			respObj.put("success", false);

			try {
				enRespObj = (Map<String, Object>) EncryptedDecryptedObjectUtil.getEncryptedString(respObj, secretKey,
						secretIv, isEncryptDecryptReqRespData);
			} catch (Exception e1) {
				logger.error("Error occured while encrypting the response- {}", e1);
			}

			logger.error("Error while generating tokens: {}", e.getMessage(), e);
			return new ResponseEntity<Map<String, Object>>(enRespObj, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/logoutUser")
	public ResponseEntity<?> logout(@RequestHeader(name = "Authorization", defaultValue = "") String authHeader,
			@CookieValue(name = "accessToken", defaultValue = "") String accessToken, HttpServletResponse response) {
		String token = "";

		logger.info("Starting the logout Process");

		// Extract and invalidate token (e.g., add to blacklist)
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
		} else if (accessToken != null && !accessToken.isEmpty()) {
			token = accessToken;
		}

		try {
			if (!token.isEmpty()) {
				blacklistService.blacklistToken(token);

				Cookie cookie = new Cookie("accessToken", null);
				cookie.setPath("/"); // Set path to match the original cookie path
				cookie.setHttpOnly(true); // if it was originally HttpOnly
				cookie.setMaxAge(0); // 0 means delete immediately

				response.addCookie(cookie);

				logger.error("while Logging out token : {} for user - {}", token, "Logged out successfully.");
				logger.error("Ending the logout Process");
				return ResponseEntity.ok("Logged out successfully.");
			}

			logger.error("while Logging out token : {} for user - {}", token, "Invalid Authorization header.");
			return ResponseEntity.badRequest().body("Invalid Authorization header");

		} catch (Exception e) {

			logger.error("Exception occurred while Logging out token : {} for user - {}", token, e);
			return ResponseEntity.badRequest().body(someThingWentWrong);
		}
	}

}
