package com.iss.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.SecretKey;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.iss.dao.CommonUserDetailsDao;
import com.iss.dao.FileObjectDao;
import com.iss.dao.ResponseDao;
import com.iss.dao.SDKinfoDao;
import com.iss.dao.UserDao;
import com.iss.dao.UserProfileRespDao;
import com.iss.entity.CommonUserDetailsTable;
import com.iss.entity.EndUser;
import com.iss.entity.SDKTable;
import com.iss.entity.UsersPersonalDetails;
import com.iss.header.HttpHeaders;
import com.iss.repository.LoginUserRespository;
import com.iss.repository.SDKRepository;
import com.iss.repository.UserPersonalDetailsRepository;
import com.iss.util.CommonUtility;
import com.iss.util.CreateProfileImagesUtil;
import com.iss.util.DigitalOceanStorageUtil;
import com.iss.util.JwtUtil;

import jakarta.transaction.Transactional;

@Service
public class LoginUserServiceImpl implements LoginUserService {

	@Value("${otp.expiration.minutes}")
	private int otpExpirationMinutes;

	@Value("${OTP_ERROR}")
	private String otpError;

	@Value("${RESEND_OTP}")
	private String resendOtp;

	@Value("${OTP_VERIFIED}")
	private String otpVerifiedSuccess;

	@Value("${PASSWORD_SUCCESS}")
	private String passwordChangeSuccess;

	@Value("${PASSWORD_FAILED}")
	private String passwordUpdateFailed;

	@Value("${USER_WRONG_PASSWORD}")
	private String passwordWrong;

	@Value("${USER_SUCCESSFUL_LOGIN}")
	private String userSuccessLogin;

	@Value("${UNVERIFIED_ACCOUNT}")
	private String unVerifiedAccount;

	@Value("${USER_NOT_FOUND}")
	private String userNotFound;

	@Value("${EMAIL_OTP}")
	private String emailOtpSendMsg;

	@Value("${ONBOARDING_PENDING}")
	private String onboardPending;

	@Value("${BLOCK_EMAIL}")
	private String blockEmailMsg;

	@Value("${ACCESS_TOKEN_EXPIRY_DAYS}")
	private Long accessTokenExpiryDays;

	@Value("${REFRESH_TOKEN_EXPIRY_DAYS}")
	private Long refreshTokenExpiryDays;

	@Value("${LOGIN_ATTEMPT_MAX_RELEASE_MINUTE}")
	private int loginAttemptMaxReleaseMin;

	@Value("${BLOCK_TIME_MAX_FAIL}")
	private String accountBlockForFailAttempts;

	@Value("${MAX_ATTEMPT_COUNT}")
	private int maxAttemptCount;

	@Value("${SUCCESS_LOGIN_AFTER_MAX_ATTEMPTS}")
	private String successLoginAfterMaxAttempts;

	@Value("${UNABLE_UPDATE_PROFILE}")
	private String unableUpdateProfilePicture;

	@Value("${SUCCESS_UPDATE_PROFILE_PICTURE}")
	private String successUpdateProfilePicture;

	@Value("${USER_DETAILS_NOFOUND}")
	private String nofoundUseDetails;

	@Value("${PROFILE_IMAGE_LOCAL_PATH}")
	private String imagePath;

	@Autowired
	private JsonWebTokenService jwtService;

	@Autowired
	private LoginUserRespository loginUserRespository;

	@Autowired
	private UserPersonalDetailsRepository userPerDetailsRepository;

	@Autowired
	private SDKRepository sdkRepository;

	private final JwtUtil jwtUtil;

	public LoginUserServiceImpl(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Transactional
	@SuppressWarnings("deprecation")
	@Override
	public ResponseDao findByEmailId(UserDao userDao, boolean isForLogin, String dateTimeFormat, SecretKey secretKey) {

		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());

		CommonUserDetailsTable userCommonDetails = new CommonUserDetailsTable();
		EndUser user = new EndUser();
		ResponseDao resDao = new ResponseDao();

		user = loginUserRespository.findByEmailId(userDao.getEmailId());

		if (StringUtils.isEmpty(user)) {
			resDao.setMessage(userNotFound);
			resDao.setOTPVerified(false);
			resDao.setOnBoarded(false);
			resDao.setSuccess(false);
		} else {
			if (isForLogin) {
				if (user.getUserPassword().equals(userDao.getPassword())) {

					// Comparing with max failed count >= maxAttemptCount for success login
					if (user.getLoginAttemptFailCount() >= maxAttemptCount) {
						resDao.setMessage(
								successLoginAfterMaxAttempts + " " + user.getLoginAttemptMaxReleaseTime() + " minutes");
						resDao.setSuccess(false);
						resDao.setUserId(user.getUserId().toString());
						resDao.setOTPVerified(user.getIsVerified());
						resDao.setOnBoarded(user.getIsOnBoarded());
						resDao.setBlocked(user.getIsBlocked());
						resDao.setEmailId(user.getUserEmail());

						return resDao;
					}

					resDao = verifyUser(user, isForLogin, currentDate, userCommonDetails);
					if (resDao.isSuccess()) {

						// resetting failed attempts details to null once after successful login
						loginUserRespository.updateLoginFailCount(0, false, "", 0, currentDate,
								this.getClass().getSimpleName(), user.getUserId());

						resDao.setSuccess(true);
					} else {
						resDao.setSuccess(false);
					}
				} else {

					// Comparing with max failed count = maxAttemptCount
					if (user.getLoginAttemptFailCount() == maxAttemptCount) {
						resDao.setMessage(
								accountBlockForFailAttempts + " " + user.getLoginAttemptMaxReleaseTime() + " minutes");
						resDao.setSuccess(false);

					}
					// Comparing with max failed count > maxAttemptCount
					else if (user.getLoginAttemptFailCount() > maxAttemptCount) {

						int res = loginUserRespository.updateLoginFailTime(
								loginAttemptMaxReleaseMin + user.getLoginAttemptMaxReleaseTime(), currentDate,
								this.getClass().getSimpleName(), user.getUserId());

						if (res != 0) {
							resDao.setMessage(accountBlockForFailAttempts + " "
									+ (user.getLoginAttemptMaxReleaseTime() + loginAttemptMaxReleaseMin) + " minutes");
							resDao.setSuccess(false);
							resDao.setUserId(user.getUserId().toString());
							resDao.setOTPVerified(user.getIsVerified());
							resDao.setOnBoarded(user.getIsOnBoarded());
							resDao.setBlocked(user.getIsBlocked());
							resDao.setEmailId(user.getUserEmail());
							return resDao;
						}
					}
					// Comparing with max failed count < maxAttemptCount
					else if (user.getLoginAttemptFailCount() < maxAttemptCount) {
						resDao.setMessage(passwordWrong);
					}

					boolean maxAttemptBlockFail = false;

					if (user.getLoginAttemptFailCount() >= 3) {
						maxAttemptBlockFail = true;
					}

					loginUserRespository.updateLoginFailCount(user.getLoginAttemptFailCount() + 1, maxAttemptBlockFail,
							currentDate, loginAttemptMaxReleaseMin, currentDate, this.getClass().getSimpleName(),
							user.getUserId());

					resDao.setSuccess(false);
				}

			} else {

				resDao = verifyUser(user, isForLogin, currentDate, userCommonDetails);
			}
			resDao.setUserId(user.getUserId().toString());
			resDao.setOTPVerified(user.getIsVerified());
			resDao.setOnBoarded(user.getIsOnBoarded());
			resDao.setBlocked(user.getIsBlocked());
			resDao.setEmailId(user.getUserEmail());
		}

		return resDao;
	}

	@SuppressWarnings("removal")
	private ResponseDao verifyUser(EndUser user, boolean isForLogin, String currentDate,
			CommonUserDetailsTable userCommonDetails) {
		ResponseDao resDao = new ResponseDao();
		CommonUserDetailsDao comUserPerDao = new CommonUserDetailsDao();
		List<SDKinfoDao> sdkList = new ArrayList<SDKinfoDao>();
		SDKinfoDao sdKinfoDao = new SDKinfoDao();

		ModelMapper modelMapper = new ModelMapper();
		// Checking the Email Id whether OTP is verified or not.
		if (user.getIsVerified()) {
			// Checking the Email Id for on boarding is completed or not
			if (user.getIsOnBoarded()) {
				// checking the Email Id is for block
				if (!user.getIsBlocked()) {
					// Checking is it for User login
					if (isForLogin) {
						resDao.setMessage(userSuccessLogin);

						// Updating User Login Flag
						loginUserRespository.updateUserLoginFlag(true, currentDate, this.getClass().getSimpleName(),
								new Long(user.getUserId()), user.getUserEmail());
						// fetching User Personal Details
						userCommonDetails = userPerDetailsRepository
								.findUserPersonalDetailsByUserId(new Long(user.getUserId()));

						// Copying fetched values from DB to dao.
						comUserPerDao = modelMapper.map(userCommonDetails, CommonUserDetailsDao.class);

						// Fetching all the available SDK details from DB.
						List<SDKTable> sdkEntList = sdkRepository.findAll();

						for (SDKTable ent : sdkEntList) {
							// Copying one by one fetched SDK ent to dao
							sdKinfoDao = modelMapper.map(ent, SDKinfoDao.class);
							sdkList.add(sdKinfoDao);
						}

						// Setting fetched user Personal Data and SDK details.
						resDao.setCommonUserDetailsDao(comUserPerDao);
						resDao.setSdkInfo(sdkList);
						resDao.setSuccess(true);
					} else {
						resDao.setMessage(emailOtpSendMsg);
					}
					resDao.setSuccess(true);
				} else {
					resDao.setMessage(blockEmailMsg);
					resDao.setSuccess(true);
				}
			} else {
				resDao.setMessage(onboardPending);
				resDao.setSuccess(true);
			}

		} else {
			resDao.setMessage(unVerifiedAccount);
			resDao.setSuccess(true);
		}

		return resDao;
	}

	@Override
	public int findByEmailIdAndPassword(String emailId, String password) {
		int countOfEmail = loginUserRespository.findByEmailIdAndPassword(emailId, password);
		return countOfEmail;
	}

	@Override
	public String getCurrentTimestamp() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	@Override
	@Transactional
	public ResponseDao saveUserWithOTP(UserDao userDao, String dateTimeFormat, boolean isNewUser) {

		ResponseDao resDao = new ResponseDao();
		try {

			SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
			String currentDate = sdf.format(new Date());
			String newOtp = CommonUtility.generateOTP();
			EndUser user = new EndUser();

			if (isNewUser) {

				// Generate new OTP if expired or not found
				user.setUserEmail(userDao.getEmailId());
				user.setUserPassword(userDao.getPassword());
				user.setUserOTP(newOtp);
				user.setIsVerified(false);
				user.setIsOnBoarded(false);
				user.setIsAdmin(false);
				user.setIsLogin(false);
				user.setIsBlocked(false);
				user.setOtpEntryDate(currentDate);
				user.setLoginAttemptFailCount(0);
				user.setLoginAttemptMaxReleaseTime(0);
				user.setLockedDateTimeForLogin("");

				user.setRegistDate(currentDate);
				user.setRegistPName(this.getClass().getSimpleName());
				user.setLastUpdateDate(currentDate);
				user.setLastUpdatePName(this.getClass().getSimpleName());

				loginUserRespository.save(user);

			} else {
				loginUserRespository.updateUserByEmailId(newOtp, userDao.getPassword(), currentDate, currentDate,
						this.getClass().getSimpleName(), userDao.getEmailId());
			}

			user = loginUserRespository.findByEmailId(userDao.getEmailId());

			resDao.setUserId(user.getUserId().toString());
			resDao.setOtp(newOtp);
			resDao.setEmailId(userDao.getEmailId());
			resDao.setSuccess(true);
		} catch (Exception e) {
			System.out.println(e);
			resDao.setSuccess(false);
		}

		return resDao;
	}

	@SuppressWarnings("removal")
	@Override
	@Transactional
	public ResponseDao verifyOtpForSignup(UserDao userDao, String dateTimeFormat) {

		EndUser user = new EndUser();
		ResponseDao resDao = new ResponseDao();
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());
		user = loginUserRespository.findByEmailIdAndOtp(userDao.getEmailId(), userDao.getOTP(),
				new Long(userDao.getUserId()));

		if (user == null) {
			resDao.setOTPVerified(false);
			resDao.setOnBoarded(false);
			resDao.setMessage(otpError);
			resDao.setSuccess(false);
			return resDao;
		} else {
			LocalDateTime otpEntryDate = LocalDateTime.parse(user.getOtpEntryDate(),
					DateTimeFormatter.ofPattern(dateTimeFormat));
			LocalDateTime currentTime = LocalDateTime.now();

			// Common condition to check OTP expiration
			if (isOtpExpired(otpEntryDate, currentTime)) {
				resDao.setMessage(resendOtp);
				resDao.setSuccess(false);
				return resDao;
			}

			loginUserRespository.updateVerifiedByEmailId(true, currentDate, this.getClass().getSimpleName(),
					userDao.getEmailId());
		}

		resDao.setUserId(user.getUserId().toString());
		resDao.setEmailId(userDao.getEmailId());
		resDao.setOTPVerified(user.getIsVerified());
		resDao.setOnBoarded(user.getIsOnBoarded());
		resDao.setMessage(otpVerifiedSuccess);
		resDao.setSuccess(true);

		return resDao;
	}

	private boolean isOtpExpired(LocalDateTime otpEntryDate, LocalDateTime currentTime) {
		// otpExpirationSeconds;
		return ChronoUnit.MINUTES.between(otpEntryDate, currentTime) > otpExpirationMinutes;
	}

	@Override
	public ResponseDao updatePassword(UserDao userDao, String dateTimeFormat) {
		int updatedRows = 0;
		ResponseDao resDao = new ResponseDao();
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());
		try {
			updatedRows = loginUserRespository.updatePasswordByEmail(userDao.getEmailId(), userDao.getPassword(),
					currentDate, currentDate);

		} catch (Exception e) {
			System.out.println(e);
		}

		if (updatedRows > 0) {
			resDao.setMessage(passwordChangeSuccess);
			resDao.setSuccess(true);
		} else {
			resDao.setMessage(passwordUpdateFailed);
			resDao.setSuccess(false);
		}

		return resDao;
	}

	@Override
	public String storeOtpForForgotPassword(UserDao userDao, String dateTimeFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());
		ResponseDao resDao = new ResponseDao();
		String newOtp = CommonUtility.generateOTP();
		resDao.setOtp(newOtp);

		try {
			loginUserRespository.updateOtpByEmail(userDao.getEmailId(), newOtp, currentDate);
		} catch (Exception e) {
			System.out.println(e);
		}
		return newOtp;
	}

	@Override
	public ResponseDao findByEmailIdForSignUp(UserDao userDao, ResponseDao resDao) {

		EndUser user = loginUserRespository.findByEmailId(userDao.getEmailId());
		if (user == null) {
			resDao.setOTPVerified(false);
			resDao.setOnBoarded(false);
			resDao.setSuccess(true);
			return resDao;
		} else {

			resDao.setOTPVerified(user.getIsVerified());
			resDao.setOnBoarded(user.getIsOnBoarded());
			resDao.setUserId(user.getUserId().toString());
			resDao.setEmailId(user.getUserEmail());
			resDao.setSuccess(false);
			return resDao;

		}
	}

	@SuppressWarnings("removal")
	@Transactional
	@Override
	public UserProfileRespDao updateProfileByUserId(String userId, String dateTimeFormat, MultipartFile fileName,
			boolean isSignup) {
		UserProfileRespDao respDao = new UserProfileRespDao();
		int res = 0;
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());
		FileObjectDao fileObjDao = new FileObjectDao();

		// creating folder and storing in the given image path
		fileObjDao = CreateProfileImagesUtil.storeFile(fileName, imagePath);

		// storing image in bucket and getting link and storing that in db image column
		String imagePath = DigitalOceanStorageUtil.uploadImage(fileObjDao.getProfileImagePath(),
				"uploads/userprofile/" + userId + ".jpg");
//			det.setUserImage(imagePath);

		if (isSignup) {
			respDao.setImagePath(imagePath);
			respDao.setMessage(successUpdateProfilePicture);
			respDao.setSuccess(true);

		} else {
			UsersPersonalDetails perDetails = userPerDetailsRepository.getByUserId(new Long(userId));
			if (perDetails != null) {
				res = loginUserRespository.updateUserProfilePictureByUserId(imagePath, currentDate,
						this.getClass().getSimpleName(), new Long(userId));

				if (res == 0) {
					respDao.setMessage(unableUpdateProfilePicture);
					respDao.setSuccess(false);
				} else {
					respDao.setImagePath(imagePath);
					respDao.setMessage(successUpdateProfilePicture);
					respDao.setSuccess(true);
				}
				respDao.setUserId(userId);
			} else {
				respDao.setUserId(userId);
				respDao.setMessage(nofoundUseDetails);
				respDao.setSuccess(false);
			}
		}

		return respDao;
	}

	private String getTokenById(String id, Long tokenExpiryTime) {
		// generating token
		String token = jwtUtil.generateToken(id, tokenExpiryTime);

		return token;
	}

	@Override
	public void checkAndResetLoginAttemptsForUser(String dateTimeFormat) {
		LocalDateTime blockedDateTime = null;
		LocalDateTime currentTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());

		List<EndUser> usersList = loginUserRespository.findAllBlockedUserForLogin(true);

		for (EndUser user : usersList) {
			blockedDateTime = LocalDateTime.parse(user.getLockedDateTimeForLogin(),
					DateTimeFormatter.ofPattern(dateTimeFormat));
			currentTime = LocalDateTime.now();
			// Common condition to check OTP expiration
			if (isUserFailAttemptsTimeOver(blockedDateTime, currentTime, user.getLoginAttemptMaxReleaseTime())) {
				// resetting failed attempts details to null once after successful login
				loginUserRespository.updateLoginFailCount(0, false, "", 0, currentDate, this.getClass().getSimpleName(),
						user.getUserId());
			}

		}

	}

	private boolean isUserFailAttemptsTimeOver(LocalDateTime blockedDateTime, LocalDateTime currentTime,
			int blockedTimeInMinutes) {
		// otpExpirationSeconds;
		return ChronoUnit.MINUTES.between(blockedDateTime, currentTime) > loginAttemptMaxReleaseMin;
	}

	@Override
	public HttpHeaders generateTokensAfterAccessTokenExpiryByRefreshToken(HttpHeaders httpHeader,
			String dateTimeFormat) {
		HttpHeaders header = new HttpHeaders();
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());

		// setting the time limit for token
		Long forAccessToken = accessTokenExpiryDays * 24 * 60 * 60 * 1000;
		Long forRefreshToken = refreshTokenExpiryDays * 24 * 60 * 60 * 1000;

		// generating token
		String accessToken = getTokenById(httpHeader.getUserId().toString(), forAccessToken);
		String refreshToken = getTokenById(httpHeader.getUserId() + "iss".toString(), forRefreshToken);

		// Now setting accessToken and refresh token same later need to generate
		// differently
		header.setAccessToken(accessToken);
		header.setRefreshToken(refreshToken);
		httpHeader.setUserId(httpHeader.getUserId());

		jwtService.createUSerWithToken(httpHeader.getUserId().toString(), accessToken, refreshToken, currentDate,
				forAccessToken, forRefreshToken);

		return header;
	}

	@Transactional
	@Override
	public HttpHeaders generateToken(String userId, String emailId, String dateTimeFormat) {
		HttpHeaders header = new HttpHeaders();
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());

		// setting the time limit for token
		Long forAccessToken = accessTokenExpiryDays * 24 * 60 * 60 * 1000;
		Long forRefreshToken = refreshTokenExpiryDays * 24 * 60 * 60 * 1000;

		// generating token
		String accessToken = getTokenById(userId + emailId, forAccessToken);
		String refreshToken = getTokenById(userId + emailId, forRefreshToken);

		if (accessToken.equals(refreshToken)) {
			System.out.println("Both are equal");
		}

		// Now setting accessToken and refresh token same later need to generate
		// differently
		header.setAccessToken(accessToken);
		header.setRefreshToken(refreshToken);
		header.setUserId(userId);

		jwtService.createUSerWithToken(userId, accessToken, refreshToken, currentDate, forAccessToken, forRefreshToken);

		return header;
	}

}