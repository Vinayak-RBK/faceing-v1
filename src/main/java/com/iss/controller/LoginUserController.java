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

import com.iss.dao.ResponseDao;
import com.iss.dao.UserDao;
import com.iss.dao.UserProfileRespDao;
import com.iss.header.HttpHeaders;
import com.iss.service.LoginUserService;
import com.iss.service.TokenBlacklistService;
import com.iss.util.EncryptDecryptData;
import com.iss.util.JwtUtil;

import jakarta.servlet.http.Cookie;
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

	@Autowired
	private LoginUserService loginUserService;

	@Autowired
	public TokenBlacklistService blacklistService;

	@Autowired
	private JwtUtil jwtUtil;

	private static final Logger logger = LoggerFactory.getLogger(LoginUserController.class);

	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> loginUser(@RequestBody UserDao dao, HttpServletResponse response) {

		Map<String, Object> respObj = new LinkedHashMap<String, Object>();

		ResponseDao resDao = new ResponseDao();
		SecretKey secretKey = null;
		HttpHeaders header = new HttpHeaders();
		try {
			secretKey = EncryptDecryptData.generateKey();
			resDao = loginUserService.findByEmailId(dao, true, dateTimeFormat, secretKey);

			if (resDao.isSuccess()) {

				if (resDao.isOnBoarded() && resDao.isOTPVerified()) {
					respObj.put("message", resDao.getMessage());
					respObj.put("userId", resDao.getUserId());
					respObj.put("emailId", resDao.getEmailId());
					respObj.put("otpverified", resDao.isOTPVerified());
					respObj.put("onBoarded", resDao.isOnBoarded());
					respObj.put("sdkInfo", resDao.getSdkInfo());
					respObj.put("commonUserDetailsDao", resDao.getCommonUserDetailsDao());
					respObj.put("blocked", resDao.isBlocked());
					respObj.put("blocked", resDao.isBlocked());
					respObj.put("success", resDao.isSuccess());
				} else {
					respObj.put("message", resDao.getMessage());
					respObj.put("userId", resDao.getUserId());
					respObj.put("emailId", resDao.getEmailId());
					respObj.put("otpverified", resDao.isOTPVerified());
					respObj.put("onBoarded", resDao.isOnBoarded());
					respObj.put("blocked", resDao.isBlocked());
					respObj.put("success", resDao.isSuccess());
				}

				logger.info("User Login for Email ID :{} - {}", dao.getEmailId(), resDao.getMessage());
				logger.info("SuccessFully Generated token for the user :{} - {}", dao.getEmailId());

				header = loginUserService.generateToken(resDao.getUserId(), resDao.getEmailId(), dateTimeFormat);

				response.setHeader("accessToken", header.getAccessToken());
				response.setHeader("refreshToken", header.getRefreshToken());

				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
			} else {

				respObj.put("message", resDao.getMessage());
//				respObj.put("userId", resDao.getUserId());
//				respObj.put("emailId", resDao.getEmailId());
				respObj.put("otpverified", resDao.isOTPVerified());
				respObj.put("onBoarded", resDao.isOnBoarded());
				respObj.put("blocked", resDao.isBlocked());
				respObj.put("success", resDao.isSuccess());

				logger.info("Unable to Login for Email ID :{} - {}", dao.getEmailId(), resDao.getMessage());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {

			respObj.put("message", someThingWentWrong);
			respObj.put("otpverified", resDao.isOTPVerified());
			respObj.put("onBoarded", resDao.isOnBoarded());
			respObj.put("blocked", resDao.isBlocked());
			respObj.put("success", resDao.isSuccess());

			logger.info("Some thing went wrong while Logging In for Email ID : {} - {}", dao.getEmailId(), e);
			System.out.println(e);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}

	}

	@PutMapping(value = "/editProfile/{userId}", consumes = "multipart/form-data")
	public ResponseEntity<UserProfileRespDao> editUserProfilePicture(@RequestParam("file") MultipartFile fileName,
			@RequestParam("isSignup") boolean isForSignup, @PathVariable String userId) {

		UserProfileRespDao respDao = new UserProfileRespDao();

		try {
			respDao = loginUserService.updateProfileByUserId(userId, dateTimeFormat, fileName, isForSignup);

			if (respDao.isSuccess()) {
				logger.info("Updating user profile picture - {}", respDao.getMessage());
				return new ResponseEntity<UserProfileRespDao>(respDao, HttpStatus.OK);
			} else {
				logger.info("Updating user profile picture - {}", respDao.getMessage());
				return new ResponseEntity<UserProfileRespDao>(respDao, HttpStatus.NOT_FOUND);
			}

		} catch (Exception e) {
			respDao.setMessage("Some thing went wrong...");
			respDao.setSuccess(true);
			return new ResponseEntity<UserProfileRespDao>(respDao, HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/getTokenByRefreshToken")
	public ResponseEntity<?> getTokensByRefreshToken(@RequestBody HttpHeaders httpHeader,
			HttpServletResponse response) {
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();
		logger.info("Received request to refresh tokens.");

		if (!jwtUtil.validateToken(httpHeader.getRefreshToken())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Refresh token has expired");
		}

		if (blacklistService.isTokenBlacklisted(httpHeader.getRefreshToken())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
		}

		String userId = jwtUtil.extractUsername(httpHeader.getRefreshToken());
		System.out.println("Extracted Token is : " + userId);
		httpHeader.setUserId(userId);

		HttpHeaders header = new HttpHeaders();
		try {
			header = loginUserService.generateTokensAfterAccessTokenExpiryByRefreshToken(httpHeader, dateTimeFormat);

			response.setHeader("accessToken", header.getAccessToken());
			response.setHeader("refreshToken", header.getRefreshToken());

			respObj.put("msg", "Successfully generated new tokens.");
			respObj.put("success", true);

			logger.info("Successfully generated new tokens, access Token : {} & refresh Token : {} ",
					header.getAccessToken(), header.getRefreshToken());
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
		} catch (Exception e) {

			respObj.put("msg", someThingWentWrong);
			respObj.put("success", false);

			logger.error("Error while generating tokens: {}", e.getMessage(), e);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/logoutUser")
	public ResponseEntity<?> logout(@RequestHeader(name = "Authorization", defaultValue = "") String authHeader,
			@CookieValue(name = "accessToken", defaultValue = "") String accessToken, HttpServletResponse response) {
		String token = "";
		
		// Extract and invalidate token (e.g., add to blacklist)
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			token = authHeader.substring(7);
		} else if (accessToken != null && !accessToken.isEmpty()) {
			token = accessToken;
		}

		if (!token.isEmpty()) {
			blacklistService.blacklistToken(token);

			Cookie cookie = new Cookie("accessToken", null);
			cookie.setPath("/"); // Set path to match the original cookie path
			cookie.setHttpOnly(true); // if it was originally HttpOnly
			cookie.setMaxAge(0); // 0 means delete immediately

			response.addCookie(cookie);

			return ResponseEntity.ok("Logged out successfully.");
		}

		return ResponseEntity.badRequest().body("Invalid Authorization header");
	}

}
