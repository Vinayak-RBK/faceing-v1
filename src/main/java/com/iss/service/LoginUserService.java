package com.iss.service;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.iss.dao.ResponseDao;
import com.iss.dao.UserDao;
import com.iss.dao.UserProfileRespDao;
import com.iss.header.HttpHeaders;

import jakarta.transaction.Transactional;

@Service
public interface LoginUserService {

	public ResponseDao findByEmailId(UserDao userDao,boolean isForLogin,String dateTimeFormat,SecretKey secretKey) throws Exception;
	
	public ResponseDao findByEmailIdForSignUp(UserDao userDao,ResponseDao resDao);

	public int findByEmailIdAndPassword(String emailId, String password);

//	@Transactional
	public ResponseDao saveUserWithOTP(UserDao userDao,String dateTimeFormat,boolean isNewUser);
	
	public String storeOtpForForgotPassword(UserDao userDao,String dateTimeFormat);

	String getCurrentTimestamp();

//	@Transactional
	public ResponseDao verifyOtpForSignup(UserDao userDao,String dateTimeFormat);

	@Transactional
	public ResponseDao updatePassword(UserDao userDao,String dateTimeFormat);
	
	public UserProfileRespDao updateProfileByUserId(String userId, String dateTimeFormat,MultipartFile fileName,boolean isSignup);
	
	public void checkAndResetLoginAttemptsForUser(String dateTimeFormat);
	
	public HttpHeaders generateTokensAfterAccessTokenExpiryByRefreshToken(HttpHeaders httpHeader ,String dateTimeFormat);
	
	public HttpHeaders generateToken(String userId,String emailId,String dateTimeFormat);

}
