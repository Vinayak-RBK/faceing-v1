package com.iss.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.iss.dao.CommonUserDetailsDao;
import com.iss.dao.EmployeeDao;
import com.iss.dao.GuestInfoListDao;
import com.iss.dao.Response;
import com.iss.dao.ResponseDao;
import com.iss.dao.SDKinfoDao;
import com.iss.dao.UserDao;
import com.iss.dao.UserProfileRespDao;
import com.iss.entity.AdminUser;
//import com.iss.entity.CommanGuestDetailsTable;
import com.iss.entity.CommonUserDetailsTable;
import com.iss.entity.EndUser;
import com.iss.entity.Guest;
import com.iss.entity.GuestHealthAnuraDetail;
import com.iss.entity.GuestHealthBinahDetail;
import com.iss.entity.SDKTable;
import com.iss.entity.UserHealthAnuraDetail;
import com.iss.entity.UserHealthBinahDetail;
import com.iss.entity.UserHealthOnboardingDetail;
import com.iss.entity.UsersPersonalDetails;
import com.iss.header.HttpHeaders;
import com.iss.properties.FieldNames;
import com.iss.repository.AdminRepository;
import com.iss.repository.GuestHealthAnuraDetailRepository;
import com.iss.repository.GuestHealthBinahDetailRepository;
import com.iss.repository.GuestUserSearchRepository;
import com.iss.repository.LoginUserRespository;
import com.iss.repository.SDKRepository;
import com.iss.repository.UserPersonalDetailsRepository;
import com.iss.util.CommonUtility;
import com.iss.util.EncryptedDecryptedObjectUtil;
import com.iss.util.JwtUtil;

import jakarta.servlet.http.HttpServletResponse;
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

	@Value("${EMPLOYEE_EXISTS}")
	private String employeeExists;

	@Value("${ONBOARDING_INCOMPLETE_EMPLOYEE}")
	private String onBoradingInCompleteEmployee;

	@Value("${ADD_EMPLOYEE_SUCCESS}")
	private String addEmployeeSuccess;

	@Value("${ADD_EMPLOYEE_fail}")
	private String addEmployeeFail;

	@Value("${SOMETHING_WENT_WRONG}")
	private String someThingWentWrong;

	@Value("${WRONG_ADMIN_ID}")
	private String wrongAdminId;

	@Value("${WRONG_USERID_RECEIVED}")
	private String wrongUserId;

	@Value("${ENCRYPT_ALGO}")
	private String encryptAlgo;

	@Value("${SECRET_KEY_FACTORY}")
	private String secretKeyFactory;

	@Value("${SECRET_KEY}")
	private String secretKey;

	@Value("${SECRET_IV}")
	private String secretIv;

	@Value("${IS_ENCRYPT_DECRYPT_ENABLE_DATABASE_DATA}")
	private boolean isEncryptDecryptDatabaseData;
	
	@Value("${DATE_TIME_FORMAT_EXPORTED_FILES}")
	private String dateFormatExportedFiles;
	
//	@Value("${GUEST_DETAILS}")
//	private String GuestDetails;

	@Autowired
	private JsonWebTokenService jwtService;

	@Autowired
	private LoginUserRespository loginUserRespository;

	@Autowired
	private UserPersonalDetailsRepository userPerDetailsRepository;
	
	@Autowired
	private GuestUserSearchRepository guestUserRepo;

	@Autowired
	private SDKRepository sdkRepository;

	@Autowired
	private SequenceGenerationService sequneceGenerationService;

	@Autowired
	private MultipartFileCacheService multipartFileCacheService;

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private GuestHealthAnuraDetailRepository guestHealthAnuraDetailRepo;
	
	@Autowired
	private GuestHealthBinahDetailRepository guestHealthBinahDetailRepo;

	private final JwtUtil jwtUtil;

	public LoginUserServiceImpl(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@SuppressWarnings("removal")
	@Transactional
	@Override
	public ResponseDao findByEmailId(UserDao userDao, boolean isForLogin, String dateTimeFormat) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());
		ModelMapper mapper = new ModelMapper();

		CommonUserDetailsTable userCommonDetails = new CommonUserDetailsTable();
		EndUser user = new EndUser();
		ResponseDao resDao = new ResponseDao();
		UserDao enUserDao = new UserDao();
		UserDao decUser = new UserDao();
		String enLoginFailCount = "";
		String enBlockForMaxAttemptFlag = "";
		String enLockedDataTime = "";
		String enUpdatedLoginCountFail = "";
		String encurrentDate = (String) EncryptedDecryptedObjectUtil.getEncryptedString(currentDate, secretKey,
				secretIv, isEncryptDecryptDatabaseData);
		String enUpdatePName = (String) EncryptedDecryptedObjectUtil.getEncryptedString(this.getClass().getSimpleName(),
				secretKey, secretIv, isEncryptDecryptDatabaseData);

		String enEmailId = (String) EncryptedDecryptedObjectUtil.getEncryptedString(userDao.getEmailId(), secretKey,
				secretIv, isEncryptDecryptDatabaseData);
		user = loginUserRespository.findByEmailId(enEmailId);

		if (user == null) {
			resDao.setMessage(userNotFound);
			resDao.setIsOTPVerified(Boolean.toString(false));
			resDao.setIsOnBoarded(Boolean.toString(false));
			resDao.setIsSuccess(Boolean.toString(false));
		} else {
			enUserDao = mapper.map(user, UserDao.class);
			enUserDao.setPassword(user.getUserPassword());
			enUserDao.setEmailId(user.getUserEmail());
			enUserDao.setIsBlocked(user.getIsBlocked());
			enUserDao.setsDKType(user.getsDKType());

			decUser = (UserDao) EncryptedDecryptedObjectUtil.getDecryptedObject(enUserDao, secretKey, secretIv,
					isEncryptDecryptDatabaseData);
			decUser.setUserId(user.getUserId().toString());
			if (isForLogin) {

				if (!userDao.getsDKType().equals(decUser.getsDKType())) {
					resDao.setMessage("UnAuthorized User is trying to Log In.");
					resDao.setIsOTPVerified(Boolean.toString(false));
					resDao.setIsOnBoarded(Boolean.toString(false));
					resDao.setIsSuccess(Boolean.toString(false));
					return resDao;
				}

				if (decUser.getPassword().equals(userDao.getPassword())) {

					// Comparing with max failed count >= maxAttemptCount for success login
					if (Integer.parseInt(decUser.getLoginAttemptFailCount()) >= maxAttemptCount) {
						resDao.setMessage(successLoginAfterMaxAttempts + " "
								+ Integer.parseInt(decUser.getLoginAttemptMaxReleaseTime()) + " minutes");
						resDao.setIsSuccess(Boolean.toString(false));
						resDao.setUserId(decUser.getUserId().toString());
						resDao.setIsOTPVerified(decUser.getIsVerified());
						resDao.setIsOnBoarded(decUser.getIsOnBoarded());
						resDao.setIsBlocked(decUser.getIsBlocked());
						resDao.setEmailId(decUser.getEmailId());

						return resDao;
					}

					resDao = verifyUser(decUser, isForLogin, currentDate, userCommonDetails);
					if (Boolean.parseBoolean(resDao.getIsSuccess())) {

						enLoginFailCount = (String) EncryptedDecryptedObjectUtil.getEncryptedString("0", secretKey,
								secretIv, isEncryptDecryptDatabaseData);
						enBlockForMaxAttemptFlag = (String) EncryptedDecryptedObjectUtil.getEncryptedString(
								Boolean.toString(false), secretKey, secretIv, isEncryptDecryptDatabaseData);
						enLockedDataTime = (String) EncryptedDecryptedObjectUtil.getEncryptedString("", secretKey,
								secretIv, isEncryptDecryptDatabaseData);

						// resetting failed attempts details to null once after successful login
						loginUserRespository.updateLoginFailCount(enLoginFailCount, enBlockForMaxAttemptFlag,
								enLockedDataTime, enLoginFailCount, encurrentDate, enUpdatePName,
								new Long(enUserDao.getUserId()));

						resDao.setIsSuccess(Boolean.toString(true));
					} else {
						resDao.setIsSuccess(Boolean.toString(false));
					}
				} else {

					// Comparing with max failed count = maxAttemptCount
					// for encryption decryprion enable this
					if (Integer.parseInt(decUser.getLoginAttemptFailCount()) == maxAttemptCount) {
						resDao.setMessage(accountBlockForFailAttempts + " " + decUser.getLoginAttemptMaxReleaseTime()
								+ " minutes");
						resDao.setIsSuccess(Boolean.toString(false));

					}
					// Comparing with max failed count > maxAttemptCount
					else if (Integer.parseInt(decUser.getLoginAttemptFailCount()) > maxAttemptCount) {

						enUpdatedLoginCountFail = (String) EncryptedDecryptedObjectUtil.getEncryptedString(
								Integer.toString(loginAttemptMaxReleaseMin
										+ Integer.parseInt(decUser.getLoginAttemptMaxReleaseTime())),
								secretKey, secretIv, isEncryptDecryptDatabaseData);
						int res = loginUserRespository.updateLoginFailTime(enUpdatedLoginCountFail, encurrentDate,
								encurrentDate, new Long(enUserDao.getUserId()));

						if (res != 0) {
							resDao.setMessage(accountBlockForFailAttempts + " "
									+ (Integer.parseInt(decUser.getLoginAttemptMaxReleaseTime())
											+ loginAttemptMaxReleaseMin)
									+ " minutes");
							resDao.setIsSuccess(Boolean.toString(false));
							resDao.setUserId(decUser.getUserId().toString());
							resDao.setIsOTPVerified(decUser.getIsVerified());
							resDao.setIsOnBoarded(decUser.getIsOnBoarded());
							resDao.setIsBlocked(decUser.getIsBlocked());
							resDao.setEmailId(decUser.getEmailId());
							return resDao;
						}
					}
					// Comparing with max failed count < maxAttemptCount
					else if (Integer.parseInt(decUser.getLoginAttemptFailCount()) < maxAttemptCount) {
						resDao.setMessage(passwordWrong);
					}

					boolean maxAttemptBlockFail = false;

					if (Integer.parseInt(decUser.getLoginAttemptFailCount()) >= 3) {
						maxAttemptBlockFail = true;
					}

					enUpdatedLoginCountFail = (String) EncryptedDecryptedObjectUtil.getEncryptedString(
							Integer.toString((Integer.parseInt(decUser.getLoginAttemptFailCount()) + 1)), secretKey,
							secretIv, isEncryptDecryptDatabaseData);
					String enMaxAttemptFailBlock = (String) EncryptedDecryptedObjectUtil.getEncryptedString(
							Boolean.toString(maxAttemptBlockFail), secretKey, secretIv, isEncryptDecryptDatabaseData);
					String enMaxAttemptFailBlockReleaseMin = (String) EncryptedDecryptedObjectUtil.getEncryptedString(
							Integer.toString(loginAttemptMaxReleaseMin), secretKey, secretIv,
							isEncryptDecryptDatabaseData);
					loginUserRespository.updateLoginFailCount(enUpdatedLoginCountFail, enMaxAttemptFailBlock,
							encurrentDate, enMaxAttemptFailBlockReleaseMin, encurrentDate, enUpdatePName,
							new Long(enUserDao.getUserId()));

					resDao.setIsSuccess(Boolean.toString(false));
				}

			} else {

				resDao = verifyUser(decUser, isForLogin, currentDate, userCommonDetails);
			}
			resDao.setUserId(decUser.getUserId().toString());
			resDao.setIsOTPVerified(decUser.getIsVerified());
			resDao.setIsOnBoarded(decUser.getIsOnBoarded());
			resDao.setIsBlocked(decUser.getIsBlocked());
			resDao.setEmailId(decUser.getEmailId());
			resDao.setPushNotify(decUser.getPushNotify());
		}

		return resDao;
	}

	@SuppressWarnings("removal")
	private ResponseDao verifyUser(UserDao user, boolean isForLogin, String currentDate,
			CommonUserDetailsTable userCommonDetails) throws Exception {
		ResponseDao resDao = new ResponseDao();
		CommonUserDetailsDao comUserPerDao = new CommonUserDetailsDao();
		CommonUserDetailsDao decComUserPerDao = new CommonUserDetailsDao();
//		UsersPersonalDetails userperDetailsEnt = new UsersPersonalDetails();
		List<SDKinfoDao> sdkList = new ArrayList<SDKinfoDao>();
		SDKinfoDao sdKinfoDao = new SDKinfoDao();

		ModelMapper modelMapper = new ModelMapper();
		// Checking the Email Id whether OTP is verified or not.
		if (Boolean.parseBoolean(user.getIsVerified())) {
			// Checking the Email Id for on boarding is completed or not
			if (Boolean.parseBoolean(user.getIsOnBoarded())) {
				// checking the Email Id is for block
				if (!Boolean.parseBoolean(user.getIsBlocked())) {
					// Checking is it for User login
					if (isForLogin) {
						resDao.setMessage(userSuccessLogin);

						String enIsLoginFlag = (String) EncryptedDecryptedObjectUtil.getEncryptedString(
								Boolean.toString(true), secretKey, secretIv, isEncryptDecryptDatabaseData);
						String enCurrentDate = (String) EncryptedDecryptedObjectUtil.getEncryptedString(currentDate,
								secretKey, secretIv, isEncryptDecryptDatabaseData);
						String enUpdatePName = (String) EncryptedDecryptedObjectUtil.getEncryptedString(
								this.getClass().getSimpleName(), secretKey, secretIv, isEncryptDecryptDatabaseData);
						String enEmailId = (String) EncryptedDecryptedObjectUtil.getEncryptedString(user.getEmailId(),
								secretKey, secretIv, isEncryptDecryptDatabaseData);

						// Updating User Login Flag
						loginUserRespository.updateUserLoginFlag(enIsLoginFlag, enCurrentDate, enUpdatePName,
								new Long(user.getUserId()), enEmailId);

						// fetching User Personal Details
						UsersPersonalDetails perDetails = userPerDetailsRepository
								.getByUserId(new Long(user.getUserId()));
						if (perDetails != null) {

//						// Copying fetched values from DB to dao.
							comUserPerDao = modelMapper.map(perDetails, CommonUserDetailsDao.class);
							decComUserPerDao = (CommonUserDetailsDao) EncryptedDecryptedObjectUtil.getDecryptedObject(
									comUserPerDao, secretKey, secretIv, isEncryptDecryptDatabaseData);

						}

						// Fetching all the available SDK details from DB.
						List<SDKTable> sdkEntList = sdkRepository.findAll();

						for (SDKTable ent : sdkEntList) {

							// Copying one by one fetched SDK ent to dao
							sdKinfoDao = modelMapper.map(ent, SDKinfoDao.class);
							sdkList.add(sdKinfoDao);
						}

						// Setting fetched user Personal Data and SDK details.
						resDao.setCommonUserDetailsDao(decComUserPerDao);
						resDao.setSdkInfo(sdkList);
						resDao.setIsSuccess(Boolean.toString(true));
					} else {
						resDao.setMessage(emailOtpSendMsg);
					}
					resDao.setIsSuccess(Boolean.toString(true));
				} else {
					resDao.setMessage(blockEmailMsg);
					resDao.setIsSuccess(Boolean.toString(false));
				}
			} else {
				resDao.setMessage(onboardPending);
				resDao.setIsSuccess(Boolean.toString(true));
			}

		} else {
			resDao.setMessage(unVerifiedAccount);
			resDao.setIsSuccess(Boolean.toString(true));
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

	@SuppressWarnings("deprecation")
	@Override
	@Transactional
	public ResponseDao saveUserWithOTP(UserDao userDao, String dateTimeFormat, boolean isNewUser) {

		ResponseDao resDao = new ResponseDao();
		try {

			SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
			String currentDate = sdf.format(new Date());
			String newOtp = CommonUtility.generateOTP();
			EndUser user = new EndUser();
			EndUser encryptedUserDao = new EndUser();
			String enOtp = "";
			String enPassword = "";
			String enCurDate = "";
			String enLastUpPName = "";
			String enEmailId = "";
			enEmailId = (String) EncryptedDecryptedObjectUtil.getEncryptedString(userDao.getEmailId(), secretKey,
					secretIv, isEncryptDecryptDatabaseData);

			if (isNewUser) {

				// setting sequence no with custom sequence format
				user.setUserIdRef(sequneceGenerationService.generateSequenceServiceForEndUser());
				user.setUserEmail(userDao.getEmailId());
				user.setUserPassword(userDao.getPassword());
				user.setUserOTP(newOtp);
				user.setIsVerified(Boolean.toString(true));
				user.setIsOnBoarded(Boolean.toString(false));
				user.setIsAdmin(Boolean.toString(false));
				user.setIsLogin(Boolean.toString(false));
				user.setIsBlocked(Boolean.toString(false));
				user.setPushNotify(Boolean.toString(false));
				user.setsDKType(FieldNames.SDK_ANURA);
				user.setOtpEntryDate(currentDate);
				user.setLoginAttemptFailCount("0");
				user.setLoginAttemptMaxReleaseTime("0");
				user.setLockedDateTimeForLogin("");
				user.setMaxAttemptFailBlockForLogin("0");

				user.setRegistDate(currentDate);
				user.setRegistPName(this.getClass().getSimpleName());
				user.setLastUpdateDate(currentDate);
				user.setLastUpdatePName(this.getClass().getSimpleName());

				encryptedUserDao = (EndUser) EncryptedDecryptedObjectUtil.getEncryptedObject(user, secretKey, secretIv,
						isEncryptDecryptDatabaseData);

				loginUserRespository.save(encryptedUserDao);

			} else {

				enOtp = (String) EncryptedDecryptedObjectUtil.getEncryptedString(newOtp, secretKey, secretIv,
						isEncryptDecryptDatabaseData);
				enPassword = (String) EncryptedDecryptedObjectUtil.getEncryptedString(userDao.getPassword(), secretKey,
						secretIv, isEncryptDecryptDatabaseData);
				enCurDate = (String) EncryptedDecryptedObjectUtil.getEncryptedString(currentDate, secretKey, secretIv,
						isEncryptDecryptDatabaseData);
				enLastUpPName = (String) EncryptedDecryptedObjectUtil.getEncryptedString(
						this.getClass().getSimpleName(), secretKey, secretIv, isEncryptDecryptDatabaseData);
				enEmailId = (String) EncryptedDecryptedObjectUtil.getEncryptedString(userDao.getEmailId(), secretKey,
						secretIv, isEncryptDecryptDatabaseData);

				loginUserRespository.updateUserByEmailId(enOtp, enPassword, enCurDate, enCurDate, enLastUpPName,
						enEmailId);
			}

			user = loginUserRespository.findByEmailId(enEmailId);

			if (!StringUtils.isEmpty(user)) {
				resDao.setUserId(user.getUserId().toString());
			}

			resDao.setOtp(newOtp);
			resDao.setEmailId(userDao.getEmailId());
			resDao.setIsSuccess(Boolean.toString(true));
		} catch (Exception e) {
			resDao.setIsSuccess(Boolean.toString(false));
		}

		return resDao;
	}

	@SuppressWarnings("removal")
	@Override
	@Transactional
	public ResponseDao verifyOtpForSignup(UserDao userDao, String dateTimeFormat) throws Exception {

		EndUser user = new EndUser();
		ResponseDao resDao = new ResponseDao();
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());
		String enEmailId = "";
		String enOtp = "";

		enEmailId = (String) EncryptedDecryptedObjectUtil.getEncryptedString(userDao.getEmailId(), secretKey, secretIv,
				isEncryptDecryptDatabaseData);
		enOtp = (String) EncryptedDecryptedObjectUtil.getEncryptedString(userDao.getOtp(), secretKey, secretIv,
				isEncryptDecryptDatabaseData);

		user = loginUserRespository.findByEmailIdAndOtp(enEmailId, enOtp, new Long(userDao.getUserId()));

		if (user == null) {
			resDao.setIsOTPVerified(Boolean.toString(false));
			resDao.setIsOnBoarded(Boolean.toString(false));
			resDao.setMessage(otpError);
			resDao.setIsSuccess(Boolean.toString(false));
			return resDao;
		} else {
			String decOtpEntryDate = (String) EncryptedDecryptedObjectUtil.getDecryptedString(user.getOtpEntryDate(),
					secretKey, secretIv, isEncryptDecryptDatabaseData);
			LocalDateTime otpEntryDate = LocalDateTime.parse(decOtpEntryDate,
					DateTimeFormatter.ofPattern(dateTimeFormat));
			LocalDateTime currentTime = LocalDateTime.now();

			// Common condition to check OTP expiration
			if (isOtpExpired(otpEntryDate, currentTime)) {
				resDao.setMessage(resendOtp);
				resDao.setIsSuccess(Boolean.toString(false));
				return resDao;
			}

			String enIsVerified = (String) EncryptedDecryptedObjectUtil.getEncryptedString(Boolean.toString(true),
					secretKey, secretIv, isEncryptDecryptDatabaseData);
			String enCyrDate = (String) EncryptedDecryptedObjectUtil.getEncryptedString(currentDate, secretKey,
					secretIv, isEncryptDecryptDatabaseData);

			String enLastUpName = (String) EncryptedDecryptedObjectUtil.getEncryptedString(
					this.getClass().getSimpleName(), secretKey, secretIv, isEncryptDecryptDatabaseData);

			loginUserRespository.updateVerifiedByEmailId(enIsVerified, enCyrDate, enLastUpName, enEmailId);
		}

		resDao.setUserId(user.getUserId().toString());
		resDao.setEmailId(userDao.getEmailId());
		resDao.setIsOTPVerified(user.getIsVerified());
		resDao.setIsOnBoarded(user.getIsOnBoarded());
		resDao.setMessage(otpVerifiedSuccess);
		resDao.setIsSuccess(Boolean.toString(true));

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

			Optional<EndUser> user = loginUserRespository.findById(userDao.getUserId());

			if (user.isEmpty()) {
				resDao.setMessage(wrongUserId);
				resDao.setIsSuccess(Boolean.toString(false));
				return resDao;
			}

			String enEmailId = (String) EncryptedDecryptedObjectUtil.getEncryptedString(userDao.getEmailId(), secretKey,
					secretIv, isEncryptDecryptDatabaseData);
			String enPassword = (String) EncryptedDecryptedObjectUtil.getEncryptedString(userDao.getPassword(),
					secretKey, secretIv, isEncryptDecryptDatabaseData);
			String enCurrentDate = (String) EncryptedDecryptedObjectUtil.getEncryptedString(currentDate, secretKey,
					secretIv, isEncryptDecryptDatabaseData);
			String enUpdatePName = (String) EncryptedDecryptedObjectUtil.getEncryptedString(
					this.getClass().getSimpleName(), secretKey, secretIv, isEncryptDecryptDatabaseData);

			updatedRows = loginUserRespository.updatePasswordByEmail(enEmailId, userDao.getUserId(), enPassword,
					enCurrentDate, enUpdatePName);

		} catch (Exception e) {
			resDao.setMessage(someThingWentWrong + e);
			resDao.setIsSuccess(Boolean.toString(false));
			return resDao;
		}

		if (updatedRows > 0) {
			resDao.setMessage(passwordChangeSuccess);
			resDao.setIsSuccess(Boolean.toString(true));
		} else {
			resDao.setMessage(passwordUpdateFailed);
			resDao.setIsSuccess(Boolean.toString(false));
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

			String enEmailId = (String) EncryptedDecryptedObjectUtil.getEncryptedString(userDao.getEmailId(), secretKey,
					secretIv, isEncryptDecryptDatabaseData);
			String enNewOtp = (String) EncryptedDecryptedObjectUtil.getEncryptedString(newOtp, secretKey, secretIv,
					isEncryptDecryptDatabaseData);
			String enCurrentDate = (String) EncryptedDecryptedObjectUtil.getEncryptedString(currentDate, secretKey,
					secretIv, isEncryptDecryptDatabaseData);

			loginUserRespository.updateOtpByEmail(enEmailId, enNewOtp, enCurrentDate);
		} catch (Exception e) {
		}
		return newOtp;
	}

	@Override
	public ResponseDao findByEmailIdForSignUp(UserDao userDao, ResponseDao resDao) {

		EndUser user = new EndUser();
		try {
			String enEmailId = (String) EncryptedDecryptedObjectUtil.getEncryptedString(userDao.getEmailId(), secretKey,
					secretIv, isEncryptDecryptDatabaseData);
			user = loginUserRespository.findByEmailId(enEmailId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (user == null) {
			resDao.setIsOTPVerified(Boolean.toString(false));
			resDao.setIsOnBoarded(Boolean.toString(false));
			resDao.setIsSuccess(Boolean.toString(true));
			return resDao;
		} else {

			resDao.setIsOTPVerified(user.getIsVerified());
			resDao.setIsOnBoarded(user.getIsOnBoarded());
			resDao.setUserId(user.getUserId().toString());
			resDao.setEmailId(user.getUserEmail());
			resDao.setIsSuccess(Boolean.toString(false));
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
		File file = null;
		String enImagePath = "";
		String encurrentDate = "";
		String enUpdatePName = "";

		try {
			// deleting cahched image before adding
			multipartFileCacheService.deleteCachedFile("userprofile.png");
			file = multipartFileCacheService.cacheFile("userprofile.png", fileName);
		} catch (IOException e) {
			respDao.setUserId(userId);
			respDao.setMessage(someThingWentWrong);
			respDao.setSuccess(Boolean.toString(false));
			return respDao;
		}
		String path = file.getPath();

		if (isSignup) {
			respDao.setUserId(userId);
			respDao.setImagePath(path.toString());
			respDao.setImagePath(respDao.getImagePath().replace("\\", "/"));
			respDao.setImagePath(respDao.getImagePath().replace(":/", "://"));
			respDao.setMessage(successUpdateProfilePicture);
			respDao.setSuccess(Boolean.toString(true));

		} else {
			UsersPersonalDetails perDetails = userPerDetailsRepository.getByUserId(new Long(userId));
			respDao.setImagePath(path.toString());
			if (perDetails != null) {

				try {
					enImagePath = (String) EncryptedDecryptedObjectUtil.getEncryptedString(path, secretKey, secretIv,
							isEncryptDecryptDatabaseData);
					encurrentDate = (String) EncryptedDecryptedObjectUtil.getEncryptedString(currentDate, secretKey,
							secretIv, isEncryptDecryptDatabaseData);
					enUpdatePName = (String) EncryptedDecryptedObjectUtil.getEncryptedString(
							this.getClass().getSimpleName(), secretKey, secretIv, isEncryptDecryptDatabaseData);
				} catch (Exception e) {
					respDao.setUserId(userId);
					respDao.setMessage(someThingWentWrong);
					respDao.setSuccess(Boolean.toString(false));
					return respDao;
				}

				res = loginUserRespository.updateUserProfilePictureByUserId(enImagePath, encurrentDate, enUpdatePName,
						new Long(userId));

				if (res == 0) {
					respDao.setMessage(unableUpdateProfilePicture);
					respDao.setSuccess(Boolean.toString(false));
				} else {
					respDao.setImagePath(respDao.getImagePath().replace("\\", "/"));
					respDao.setImagePath(respDao.getImagePath().replace(":/", "://"));
					respDao.setMessage(successUpdateProfilePicture);
					respDao.setSuccess(Boolean.toString(true));
				}
				respDao.setUserId(userId);
			} else {
				respDao.setUserId(userId);
				respDao.setMessage(nofoundUseDetails);
				respDao.setSuccess(Boolean.toString(false));
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
	public void checkAndResetLoginAttemptsForUser(String dateTimeFormat) throws Exception {
		LocalDateTime blockedDateTime = null;
		LocalDateTime currentTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());

		List<EndUser> usersList = loginUserRespository.findAll();

		for (EndUser user : usersList) {

			if (user.getLockedDateTimeForLogin().isEmpty()) {
				continue;
			}

			String enLockedDateTime = (String) EncryptedDecryptedObjectUtil.getDecryptedString(
					user.getLockedDateTimeForLogin(), secretKey, secretIv, isEncryptDecryptDatabaseData);
			String enLoginAttemptMaxReleaseTime = (String) EncryptedDecryptedObjectUtil.getDecryptedString(
					user.getLoginAttemptMaxReleaseTime(), secretKey, secretIv, isEncryptDecryptDatabaseData);

			blockedDateTime = LocalDateTime.parse(enLockedDateTime, DateTimeFormatter.ofPattern(dateTimeFormat));
			currentTime = LocalDateTime.now();
			// Common condition to check OTP expiration
			if (isUserFailAttemptsTimeOver(blockedDateTime, currentTime,
					Integer.parseInt(enLoginAttemptMaxReleaseTime))) {
				enLoginAttemptMaxReleaseTime = (String) EncryptedDecryptedObjectUtil.getDecryptedString(
						user.getLoginAttemptMaxReleaseTime(), secretKey, secretIv, isEncryptDecryptDatabaseData);

				String enLoginFailCount = (String) EncryptedDecryptedObjectUtil.getEncryptedString("0", secretKey,
						secretIv, isEncryptDecryptDatabaseData);
				String enBlockForMaxAttemptFlag = (String) EncryptedDecryptedObjectUtil
						.getEncryptedString(Boolean.toString(false), secretKey, secretIv, isEncryptDecryptDatabaseData);
				String enLockedDataTime = (String) EncryptedDecryptedObjectUtil.getEncryptedString("", secretKey,
						secretIv, isEncryptDecryptDatabaseData);
				String encurrentDate = (String) EncryptedDecryptedObjectUtil.getEncryptedString(currentDate, secretKey,
						secretIv, isEncryptDecryptDatabaseData);
				String enUpdatePName = (String) EncryptedDecryptedObjectUtil.getEncryptedString(
						this.getClass().getSimpleName(), secretKey, secretIv, isEncryptDecryptDatabaseData);

				// resetting failed attempts details to null once after successful login
				loginUserRespository.updateLoginFailCount(enLoginFailCount, enBlockForMaxAttemptFlag, enLockedDataTime,
						enLoginFailCount, encurrentDate, enUpdatePName, user.getUserId());
			}

		}

	}

	private boolean isUserFailAttemptsTimeOver(LocalDateTime blockedDateTime, LocalDateTime currentTime,
			int blockedTimeInMinutes) {
		// otpExpirationSeconds;
		return ChronoUnit.MINUTES.between(blockedDateTime, currentTime) > loginAttemptMaxReleaseMin;
	}

	@Override
	public HttpHeaders generateTokensAfterAccessTokenExpiryByRefreshToken(HttpHeaders httpHeader, String dateTimeFormat)
			throws Exception {
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
				Long.toString(forAccessToken), Long.toString(forRefreshToken));

		return header;
	}

	@Transactional
	@Override
	public HttpHeaders generateToken(String userId, String emailId, String dateTimeFormat) throws Exception {
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
		}

		// Now setting accessToken and refresh token same later need to generate
		// differently
		header.setAccessToken(accessToken);
		header.setRefreshToken(refreshToken);
		header.setUserId(userId);

		jwtService.createUSerWithToken(userId, accessToken, refreshToken, currentDate, Long.toString(forAccessToken),
				Long.toString(forRefreshToken));

		return header;
	}

	@Override
	public ResponseDao findByEmailIdForAddEmployee(EmployeeDao empDao) {
		ResponseDao resDao = new ResponseDao();
		EndUser user = new EndUser();
		String decIsOnboradedFlag = "";

		try {

			Optional<AdminUser> adminUser = adminRepository.findById(empDao.getId());

			if (adminUser.isEmpty()) {
				resDao.setMessage(wrongAdminId);
				resDao.setIsSuccess(Boolean.toString(false));
				return resDao;
			}

			String enEmailId = (String) EncryptedDecryptedObjectUtil.getEncryptedString(empDao.getEmail(), secretKey,
					secretIv, isEncryptDecryptDatabaseData);
			user = loginUserRespository.findByEmailId(enEmailId);
		} catch (Exception e) {
			resDao.setMessage(someThingWentWrong);
			resDao.setIsSuccess(Boolean.toString(false));
			return resDao;
		}
		if (user != null) {
			try {
				decIsOnboradedFlag = (String) EncryptedDecryptedObjectUtil.getDecryptedString(user.getIsOnBoarded(),
						secretKey, secretIv, isEncryptDecryptDatabaseData);
			} catch (Exception e) {
				resDao.setMessage(someThingWentWrong);
				resDao.setIsSuccess(Boolean.toString(false));
				return resDao;
			}
			if (Boolean.parseBoolean(decIsOnboradedFlag)) {
				resDao.setMessage(employeeExists);
				resDao.setIsSuccess(Boolean.toString(false));
				return resDao;
			} else {
				resDao.setMessage(onBoradingInCompleteEmployee);
				resDao.setIsSuccess(Boolean.toString(false));
				return resDao;
			}

		} else {
			resDao.setIsSuccess(Boolean.toString(true));
			return resDao;
		}
	}

	@Override
	public ResponseDao addEmployeeService(EmployeeDao empDao, String dateTimeFormat) {
		ResponseDao resDao = new ResponseDao();
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());
		EndUser user = new EndUser();
		EndUser encryptedUserDao = new EndUser();

		// setting sequence no with custom sequence format
		user.setUserIdRef(sequneceGenerationService.generateSequenceServiceForEndUser());
		user.setUserEmail(empDao.getEmail());
		user.setUserPassword(empDao.getPassword());
		user.setAdminId(empDao.getId());
		user.setName(empDao.getName());
		user.setJobRole(empDao.getJobRole());
		user.setMobileNumber(empDao.getMobileNumber());
		user.setUserOTP("");
		user.setIsVerified(Boolean.toString(true));
		user.setIsOnBoarded(Boolean.toString(false));
		user.setIsAdmin(Boolean.toString(false));
		user.setIsLogin(Boolean.toString(false));
		user.setIsBlocked(Boolean.toString(false));
		user.setPushNotify(Boolean.toString(false));
		user.setsDKType(FieldNames.SDK_ANURA);
		user.setOtpEntryDate(currentDate);
		user.setLoginAttemptFailCount("0");
		user.setLoginAttemptMaxReleaseTime("0");
		user.setLockedDateTimeForLogin("");
		user.setMaxAttemptFailBlockForLogin(Boolean.toString(false));

		user.setRegistDate(currentDate);
		user.setRegistPName(this.getClass().getSimpleName());
		user.setLastUpdateDate(currentDate);
		user.setLastUpdatePName(this.getClass().getSimpleName());

		try {
			encryptedUserDao = (EndUser) EncryptedDecryptedObjectUtil.getEncryptedObject(user, secretKey, secretIv,
					isEncryptDecryptDatabaseData);
		} catch (Exception e) {
			resDao.setEmailId(empDao.getEmail());
			resDao.setIsSuccess(Boolean.toString(true));
			resDao.setMessage(addEmployeeFail);
		}

		loginUserRespository.save(encryptedUserDao);

		resDao.setEmailId(empDao.getEmail());
		resDao.setIsSuccess(Boolean.toString(true));
		resDao.setMessage(addEmployeeSuccess);

		return resDao;
	}

}