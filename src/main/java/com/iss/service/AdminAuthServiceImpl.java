package com.iss.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.iss.dao.AdminResponseDao;
import com.iss.dao.AdminUserDao;
import com.iss.dao.LegalSettingsDao;
import com.iss.dao.LegalSettingsRespDao;
import com.iss.entity.AdminUser;
import com.iss.entity.LegalSettings;
import com.iss.entity.UserSystemIPAddress;
import com.iss.repository.AdminAuthRepository;
import com.iss.repository.LegalSettingsRepository;
import com.iss.repository.UserSystemIPAddressRepository;
import com.iss.util.CommonUtility;
import com.iss.util.EncryptedDecryptedObjectUtil;

import jakarta.transaction.Transactional;

@Service
public class AdminAuthServiceImpl implements AdminAuthService {

	@Value("${otp.expiration.minutes}")
	private int otpExpirationMinutes;

	@Value("${OTP_ERROR}")
	private String otpError;

	@Value("${RESEND_OTP}")
	private String resendOtp;

	@Value("${OTP_VERIFIED}")
	private String otpVerifiedSuccess;

	@Value("${USER_SUCCESSFUL_LOGIN}")
	private String loginSuccess;

	@Value("${UNVERIFIED_ACCOUNT}")
	private String unVerified;

	@Value("${USER_NOT_FOUND}")
	private String emailNotFound;

	@Value("${USER_WRONG_PASSWORD}")
	private String wrongpassword;

	@Value("${EMAIL_OTP}")
	private String otpSent;

	@Value("${ACCESS_TOKEN_EXPIRY_DAYS}")
	private Long accessTokenExpiryDays;

	@Value("${REFRESH_TOKEN_EXPIRY_DAYS}")
	private Long refreshTokenExpiryDays;

	@Value("${PASSWORD_MISMATCH}")
	private String passwordMismatch;

	@Value("${PASSWORD_SUCCESS}")
	private String passwordSuccess;

	@Value("${UNABLE_RESET}")
	private String unableReset;

	@Value("${LEGAL_SETTING_NOT_AVAILBLE}")
	private String legalsettingNotAvailble;

	@Value("${SENDING_LEGAL_SETTING}")
	private String sendinglegalSetting;

	@Value("${UNABLE_UPDATE_LEGAL}")
	private String unableUpdateLegal;

	@Value("${UPDATED_SUCCESS}")
	private String updatedSuccess;

	@Value("${MAX_ATTEMPT_COUNT}")
	private int maxAttemptCount;

	@Value("${SUCCESS_LOGIN_AFTER_MAX_ATTEMPTS}")
	private String successLoginAfterMaxAttempts;

	@Value("${LOGIN_ATTEMPT_MAX_RELEASE_MINUTE}")
	private int loginAttemptMaxReleaseMin;

	@Value("${BLOCK_TIME_MAX_FAIL}")
	private String accountBlockForFailAttempts;

	@Value("${USER_WRONG_PASSWORD}")
	private String passwordWrong;

	@Value("${SITE_BLOCK_RELEASE_TIME}")
	private int siteBlockReleaseTime;

	@Value("${SECRET_KEY}")
	private String secretKey;

	@Value("${SECRET_IV}")
	private String secretIv;

	@Value("${IS_ENCRYPT_DECRYPT_ENABLE_DATABASE_DATA}")
	private boolean isEncryptDecryptDatabaseData;

	@Autowired
	private AdminAuthRepository adminAuthRepo;

	@Autowired
	private LegalSettingsRepository legalSetRepo;

	@Autowired
	private UserSystemIPAddressRepository userIpRepository;

	@Autowired
	private SequenceGenerationService sequneceGenerationService;

	private AdminResponseDao _respDao = new AdminResponseDao();

	private AdminUser _adminUser = new AdminUser();

	private static final Logger logger = LoggerFactory.getLogger(AdminAuthServiceImpl.class);

	@SuppressWarnings("removal")
	@Transactional
	@Override
	public AdminResponseDao adminLoginService(AdminUserDao dao, String dateTimeFormat) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());
		AdminUserDao enDao = new AdminUserDao();
		AdminUserDao decDao = new AdminUserDao();
		ModelMapper mapper = new ModelMapper();
		_respDao = new AdminResponseDao();

		String enEmailId = "";
		String enLoginFailCount = "";
		String enBlockForMaxAttemptFlag = "";
		String enLockedDataTime = "";
		String encurrentDate = "";
		String enUpdatePName = "";
		String enLoginFailCountFromDB = "";
		String enLoginFailMaxReleaseTimeFromDB = "";
		String enPassword = "";

		enEmailId = (String) EncryptedDecryptedObjectUtil.getEncryptedString(dao.getEmail(), secretKey, secretIv,
				isEncryptDecryptDatabaseData);
		encurrentDate = (String) EncryptedDecryptedObjectUtil.getEncryptedString(currentDate, secretKey, secretIv,
				isEncryptDecryptDatabaseData);
		enUpdatePName = (String) EncryptedDecryptedObjectUtil.getEncryptedString(this.getClass().getSimpleName(),
				secretKey, secretIv, isEncryptDecryptDatabaseData);
		enPassword = (String) EncryptedDecryptedObjectUtil.getEncryptedString(dao.getPassword(), secretKey, secretIv,
				isEncryptDecryptDatabaseData);

		_adminUser = adminAuthRepo.findAdminUserByEmailId(enEmailId);

		if (_adminUser != null) {

			enLoginFailCountFromDB = (String) EncryptedDecryptedObjectUtil.getDecryptedString(
					_adminUser.getLoginAttemptFailCount(), secretKey, secretIv, isEncryptDecryptDatabaseData);

			enLoginFailMaxReleaseTimeFromDB = (String) EncryptedDecryptedObjectUtil.getDecryptedString(
					_adminUser.getLoginAttemptMaxReleaseTime(), secretKey, secretIv, isEncryptDecryptDatabaseData);

			if (!_adminUser.getAdminPassword().equals(enPassword)) {
				_respDao.setMessage(wrongpassword);
				_respDao.setSuccess(Boolean.toString(false));

				// [Start] adding code to block after max attempts
				// Comparing with max failed count = maxAttemptCount
				encurrentDate = (String) EncryptedDecryptedObjectUtil.getEncryptedString(currentDate, secretKey,
						secretIv, isEncryptDecryptDatabaseData);
				enUpdatePName = (String) EncryptedDecryptedObjectUtil.getEncryptedString(
						this.getClass().getSimpleName(), secretKey, secretIv, isEncryptDecryptDatabaseData);

				if (Integer.parseInt(enLoginFailCountFromDB) == maxAttemptCount) {
					_respDao.setMessage(accountBlockForFailAttempts + " " + _adminUser.getLoginAttemptMaxReleaseTime()
							+ " minutes");
					_respDao.setSuccess(Boolean.toString(false));

				}
				// Comparing with max failed count > maxAttemptCount
				else if (Integer.parseInt(enLoginFailCountFromDB) > maxAttemptCount) {

					int res = adminAuthRepo.updateLoginFailTime(
							Integer.toString(
									loginAttemptMaxReleaseMin + Integer.parseInt(enLoginFailMaxReleaseTimeFromDB)),
							encurrentDate, enUpdatePName, _adminUser.getAdminId());

					if (res != 0) {
						_respDao.setMessage(accountBlockForFailAttempts + " "
								+ (Integer.parseInt(enLoginFailMaxReleaseTimeFromDB) + loginAttemptMaxReleaseMin)
								+ " minutes");
						_respDao.setSuccess(Boolean.toString(false));
						_respDao.setAdminId(_adminUser.getAdminId().toString());
						_respDao.setEmail(_adminUser.getAdminEmail());
						return _respDao;
					}
				}
				// Comparing with max failed count < maxAttemptCount
				else if (Integer.parseInt(enLoginFailCountFromDB) < maxAttemptCount) {
					_respDao.setMessage(passwordWrong);
				}

				boolean maxAttemptBlockFail = false;

				if (Integer.parseInt(enLoginFailCountFromDB) >= 3) {
					maxAttemptBlockFail = true;
				}

				String enTotalLoginFailCountUpdate = (String) EncryptedDecryptedObjectUtil.getEncryptedString(
						Integer.toString(Integer.parseInt(enLoginFailCountFromDB) + 1), secretKey, secretIv,
						isEncryptDecryptDatabaseData);

				String enMaxAttemptBlockFailUpdate = (String) EncryptedDecryptedObjectUtil.getEncryptedString(
						Boolean.toString(maxAttemptBlockFail), secretKey, secretIv, isEncryptDecryptDatabaseData);

				String enLoginAttemptMaxReleaseMin = (String) EncryptedDecryptedObjectUtil.getEncryptedString(
						Integer.toString(loginAttemptMaxReleaseMin), secretKey, secretIv, isEncryptDecryptDatabaseData);

				adminAuthRepo.updateLoginFailCount(enTotalLoginFailCountUpdate, enMaxAttemptBlockFailUpdate,
						encurrentDate, enLoginAttemptMaxReleaseMin, encurrentDate, enUpdatePName,
						_adminUser.getAdminId());

				_respDao.setSuccess(Boolean.toString(false));
				// [End] adding code to block after max attempts
			} else {
//				/
				enDao = mapper.map(_adminUser, AdminUserDao.class);
				enDao.setEmail(_adminUser.getAdminEmail());

				decDao = (AdminUserDao) EncryptedDecryptedObjectUtil.getDecryptedObject(enDao, secretKey, secretIv,
						isEncryptDecryptDatabaseData);
				decDao.setAdminId(_adminUser.getAdminId().toString());

				// Comparing with max failed count >= maxAttemptCount for success login
				if (Integer.parseInt(enLoginFailCountFromDB) >= maxAttemptCount) {
					_respDao.setMessage(successLoginAfterMaxAttempts + " "
							+ Integer.parseInt(enLoginFailMaxReleaseTimeFromDB) + " minutes");
					_respDao.setSuccess(Boolean.toString(false));
					_respDao.setAdminId(_adminUser.getAdminId().toString());
					_respDao.setEmail(decDao.getEmail());

					return _respDao;
				}

				if (Boolean.parseBoolean(decDao.getIsVerified())) {
					_respDao.setMessage(loginSuccess);
					_respDao.setAdminId(decDao.getAdminId().toString());
					_respDao.setEmail(decDao.getEmail());
					_respDao.setAdmin(decDao.getIsAdmin());
					_respDao.setUserManagement(decDao.getIsUserManagement());
					_respDao.setQuestionaries(decDao.getIsQuestionaries());
					_respDao.setLegalSetting(decDao.getIsLegalSetting());
					_respDao.setSuccess(Boolean.toString(true));
				} else {
					_respDao.setMessage(unVerified);
					_respDao.setAdminId(decDao.getAdminId());
					_respDao.setEmail(decDao.getEmail());
					_respDao.setAdmin(decDao.getIsAdmin());
					_respDao.setUserManagement(decDao.getIsUserManagement());
					_respDao.setQuestionaries(decDao.getIsQuestionaries());
					_respDao.setLegalSetting(decDao.getIsLegalSetting());
					_respDao.setSuccess(Boolean.toString(false));
				}

				enLoginFailCount = (String) EncryptedDecryptedObjectUtil.getEncryptedString("0", secretKey, secretIv,
						isEncryptDecryptDatabaseData);
				enBlockForMaxAttemptFlag = (String) EncryptedDecryptedObjectUtil
						.getEncryptedString(Boolean.toString(false), secretKey, secretIv, isEncryptDecryptDatabaseData);
				enLockedDataTime = (String) EncryptedDecryptedObjectUtil.getEncryptedString("", secretKey, secretIv,
						isEncryptDecryptDatabaseData);
				adminAuthRepo.updateLoginFailCount(enLoginFailCount, enBlockForMaxAttemptFlag, enLockedDataTime,
						enLoginFailCount, encurrentDate, enUpdatePName, new Long(decDao.getAdminId()));

			}
		} else {
			_respDao.setMessage(emailNotFound);
			_respDao.setSuccess(Boolean.toString(false));
		}

		logger.info("While admin Login - {}", _respDao.getMessage());
		return _respDao;

	}

	@Transactional
	@Override
	public AdminResponseDao adminForgetPasswordService(AdminUserDao dao, String curDateTime) throws Exception {
		String newOtp = "";
		String enEmailId = "";
		String encurrentDate = "";
		String enUpdatePName = "";
		String enNewOtp = "";

		enEmailId = (String) EncryptedDecryptedObjectUtil.getEncryptedString(dao.getEmail(), secretKey, secretIv,
				isEncryptDecryptDatabaseData);
		encurrentDate = (String) EncryptedDecryptedObjectUtil.getEncryptedString(curDateTime, secretKey, secretIv,
				isEncryptDecryptDatabaseData);

		enUpdatePName = (String) EncryptedDecryptedObjectUtil.getEncryptedString(this.getClass().getSimpleName(),
				secretKey, secretIv, isEncryptDecryptDatabaseData);

		_adminUser = adminAuthRepo.findAdminUserByEmailId(enEmailId);

		if (_adminUser == null) {
			_respDao.setMessage(emailNotFound);
			_respDao.setSuccess(Boolean.toString(false));
		} else {

			String decIsverified = (String) EncryptedDecryptedObjectUtil.getDecryptedString(_adminUser.getIsVerified(),
					secretKey, secretIv, isEncryptDecryptDatabaseData);
			if (Boolean.parseBoolean(decIsverified)) {

				newOtp = CommonUtility.generateOTP();

				enNewOtp = (String) EncryptedDecryptedObjectUtil.getEncryptedString(newOtp, secretKey, secretIv,
						isEncryptDecryptDatabaseData);

				adminAuthRepo.updateAdminOtpUserByEmailId(enNewOtp, encurrentDate, encurrentDate, enUpdatePName,
						enEmailId);

				_respDao.setMessage(otpSent);
				_respDao.setSuccess(Boolean.toString(true));
				_respDao.setOtp(newOtp);
			} else {
				_respDao.setMessage(unVerified);
				_respDao.setSuccess(Boolean.toString(false));
			}

			_respDao.setEmail(dao.getEmail());
			_respDao.setAdminId(_adminUser.getAdminId().toString());
		}

		logger.info("While admin Forget Password - {}", _respDao.getMessage());
		return _respDao;
	}

	@SuppressWarnings("removal")
	@Override
	public AdminResponseDao adminVerifyForgetPasswordService(AdminUserDao dao, String dateTimeFormat) throws Exception {

		String enOtpEntrytDate = "";
		String enEmailId = "";
		String enNewOtp = "";

		enNewOtp = (String) EncryptedDecryptedObjectUtil.getEncryptedString(dao.getAdminOTP(), secretKey, secretIv,
				isEncryptDecryptDatabaseData);

		_adminUser = adminAuthRepo.findAdminUserByAdminIdAndOtp(enNewOtp, new Long(dao.getAdminId()));

		if (_adminUser == null) {
			_respDao.setMessage(otpError);
			_respDao.setSuccess(Boolean.toString(false));
		} else {

			enOtpEntrytDate = (String) EncryptedDecryptedObjectUtil.getDecryptedString(_adminUser.getOtpEntryDate(),
					secretKey, secretIv, isEncryptDecryptDatabaseData);

			LocalDateTime otpEntryDate = LocalDateTime.parse(enOtpEntrytDate,
					DateTimeFormatter.ofPattern(dateTimeFormat));
			LocalDateTime currentTime = LocalDateTime.now();

			// Common condition to check OTP expiration
			if (isOtpExpired(otpEntryDate, currentTime)) {
				_respDao.setMessage(resendOtp);
				_respDao.setSuccess(Boolean.toString(false));
				return _respDao;
			}

			enEmailId = (String) EncryptedDecryptedObjectUtil.getDecryptedString(_adminUser.getAdminEmail(), secretKey,
					secretIv, isEncryptDecryptDatabaseData);

			_respDao.setEmail(enEmailId);
			_respDao.setMessage(otpVerifiedSuccess);
			_respDao.setSuccess(Boolean.toString(true));
			_respDao.setAdminId(dao.getAdminId().toString());
		}

		logger.info("While Verifying admin Forgetpassword OTP - {}", _respDao.getMessage());
		return _respDao;
	}

	@SuppressWarnings({ "removal" })
	@Transactional
	@Override
	public AdminResponseDao resetAdminPasswordService(AdminUserDao dao, String curDateTime) throws Exception {
		int success = 0;
		String enPassword = "";
		String encurrentDate = "";
		String enUpdatePName = "";
		String decEmailId = "";

		Optional<AdminUser> adminUser = adminAuthRepo.findById(dao.getAdminId());

		if (adminUser.isEmpty()) {
			_respDao.setMessage(emailNotFound);
			_respDao.setSuccess(Boolean.toString(false));

			logger.info("While resetting the Admin password - {}", emailNotFound);
			return _respDao;
		}

		if (!dao.getPassword().equals(dao.getConfirmPassword())) {
			_respDao.setMessage(passwordMismatch);
			_respDao.setSuccess(Boolean.toString(false));

			logger.info("While resetting the Admin password - {}", passwordMismatch);
			return _respDao;
		}

		encurrentDate = (String) EncryptedDecryptedObjectUtil.getEncryptedString(curDateTime, secretKey, secretIv,
				isEncryptDecryptDatabaseData);
		enUpdatePName = (String) EncryptedDecryptedObjectUtil.getEncryptedString(this.getClass().getSimpleName(),
				secretKey, secretIv, isEncryptDecryptDatabaseData);
		enPassword = (String) EncryptedDecryptedObjectUtil.getEncryptedString(dao.getPassword(), secretKey, secretIv,
				isEncryptDecryptDatabaseData);

		success = adminAuthRepo.updateAdminPasswordByAdminId(enPassword, encurrentDate, enUpdatePName,
				new Long(dao.getAdminId()));

		decEmailId = (String) EncryptedDecryptedObjectUtil.getDecryptedString(adminUser.get().getAdminEmail(),
				secretKey, secretIv, isEncryptDecryptDatabaseData);

		if (success == 1) {
			_respDao.setMessage(passwordSuccess);
			_respDao.setSuccess(Boolean.toString(true));
		} else {
			_respDao.setMessage(unableReset);
			_respDao.setSuccess(Boolean.toString(false));
		}

		_respDao.setEmail(decEmailId);
		_respDao.setAdminId(dao.getAdminId());

		logger.info("While resetting the Admin password - {}", _respDao.getMessage());
		return _respDao;
	}

	private boolean isOtpExpired(LocalDateTime otpEntryDate, LocalDateTime currentTime) {
		// otpExpirationSeconds;
		return ChronoUnit.MINUTES.between(otpEntryDate, currentTime) > otpExpirationMinutes;
	}

	@SuppressWarnings("deprecation")
	@Override
	public int findByEmailIdForAdminSignUp(AdminUserDao dao) {

		AdminUser user = new AdminUser();
		user = adminAuthRepo.findAdminUserByEmailId(dao.getEmail());
		if (StringUtils.isEmpty(user)) {
			System.out.println("return0");
			return 0;
		} else {
			if (Boolean.parseBoolean(user.getIsVerified())) {
				return 1;
			} else {
				return 2;
			}
		}
	}

	@Override
	@Transactional
	public AdminResponseDao saveAdminWithOTP(AdminUserDao dao, String dateTimeFormat, boolean isNewAdmin) {
		AdminResponseDao resDao = new AdminResponseDao();
		try {

			SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
			String currentDate = sdf.format(new Date());
			String newOtp = CommonUtility.generateOTP();
			AdminUser admin = new AdminUser();

			if (isNewAdmin) {

				// setting sequence no with custom sequence format
				admin.setAdminIdRef(sequneceGenerationService.generateSequenceServiceForSuperAdmin());
				admin.setAdminEmail(dao.getEmail());
				admin.setAdminPassword(dao.getPassword());
				admin.setAdminOTP(newOtp);
				admin.setIsVerified(Boolean.toString(false));
				admin.setIsAdmin(Boolean.toString(true));
				admin.setIsUserManagement(Boolean.toString(true));
				admin.setIsQuestionaries(Boolean.toString(true));
				admin.setIsLegalSetting(Boolean.toString(true));
				admin.setOtpEntryDate(currentDate);
				admin.setLoginAttemptFailCount("0");
				admin.setLoginAttemptMaxReleaseTime("0");
				admin.setLockedDateTimeForLogin("");

				admin.setRegistDate(currentDate);
				admin.setRegistPName(this.getClass().getSimpleName());
				admin.setLastUpdateDate(currentDate);
				admin.setLastUpdatePName(this.getClass().getSimpleName());

				adminAuthRepo.save(admin);
			} else {
				adminAuthRepo.updateAdminByEmailId(newOtp, currentDate, currentDate, this.getClass().getSimpleName(),
						dao.getEmail());
			}

			admin = adminAuthRepo.findAdminUserByEmailId(dao.getEmail());

			resDao.setAdminId(admin.getAdminId().toString());
			resDao.setOtp(newOtp);
			resDao.setEmail(dao.getEmail());
			resDao.setSuccess(Boolean.toString(true));

		} catch (Exception e) {

			logger.error("Exception occurred while saving Admin forget password OTP - {}", e);
			resDao.setSuccess(Boolean.toString(false));
		}

		logger.info("while saving Admin forget password OTP - {}", resDao.getMessage());
		return resDao;
	}

	@SuppressWarnings("removal")
	@Override
	@Transactional
	public AdminResponseDao verifyOtpForAdminSignup(AdminUserDao dao, String dateTimeFormat) {

		AdminUser user = new AdminUser();
		AdminResponseDao resDao = new AdminResponseDao();
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());
		user = adminAuthRepo.findByAdminEmailIdAndOtp(dao.getEmail(), dao.getAdminOTP(), new Long(dao.getAdminId()));

		if (user == null) {
			resDao.setMessage(otpError);
			resDao.setSuccess(Boolean.toString(false));

			logger.info("while verifying OTP for Admin Signup OTP - {}", otpError);
			return resDao;
		} else {

			LocalDateTime otpEntryDate = LocalDateTime.parse(user.getOtpEntryDate(),
					DateTimeFormatter.ofPattern(dateTimeFormat));
			LocalDateTime currentTime = LocalDateTime.now();

			// Common condition to check OTP expiration
			if (isOtpExpired(otpEntryDate, currentTime)) {
				resDao.setMessage(resendOtp);
				resDao.setSuccess(Boolean.toString(false));
				return resDao;
			}

			adminAuthRepo.updateVerifiedByEmailId(Boolean.toString(true), currentDate, this.getClass().getSimpleName(),
					new Long(dao.getAdminId()));
		}

		resDao.setAdminId(user.getAdminId().toString());
		resDao.setQuestionaries(user.getIsQuestionaries());
		resDao.setUserManagement(user.getIsUserManagement());
		resDao.setLegalSetting(user.getIsLegalSetting());
		resDao.setAdmin(user.getIsAdmin());
		resDao.setEmail(dao.getEmail());
		resDao.setMessage(otpVerifiedSuccess);
		resDao.setSuccess(Boolean.toString(true));

		logger.info("while verifying OTP for Admin Signup OTP - {}", otpVerifiedSuccess);
		return resDao;
	}

	@Override
	public LegalSettingsRespDao getLegalSetting() {
		LegalSettingsRespDao resDao = new LegalSettingsRespDao();
		List<LegalSettingsDao> daoList = new ArrayList<LegalSettingsDao>();
		LegalSettingsDao dao = new LegalSettingsDao();

		List<LegalSettings> legalList = new ArrayList<LegalSettings>();

		legalList = legalSetRepo.findAll();

		if (!legalList.isEmpty()) {
			for (LegalSettings ent : legalList) {
				dao = new LegalSettingsDao();
				dao.setLegalId(ent.getLegalId());
				dao.setContent(ent.getContent());

				daoList.add(dao);
			}
			resDao.setLegalLists(daoList);
			resDao.setMsg(sendinglegalSetting);
			resDao.setSuccess(Boolean.toString(true));
		} else {
			resDao.setMsg(legalsettingNotAvailble);
			resDao.setSuccess(Boolean.toString(false));
		}

		logger.info("while getting Legal setting - {}", resDao.getMsg());
		return resDao;
	}

	@SuppressWarnings("removal")
	@Transactional
	@Override
	public LegalSettingsRespDao updateLegalSettings(LegalSettingsDao legalSettingdao, String dateTimeFormat) {
		LegalSettingsRespDao respDao = new LegalSettingsRespDao();

		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());
		int res = 0;
		Optional<LegalSettings> legalSetting = legalSetRepo.findById(legalSettingdao.getLegalId());

		if (legalSetting.isEmpty()) {
			respDao.setMsg(legalsettingNotAvailble);
			respDao.setSuccess(Boolean.toString(false));
		} else {
			res = legalSetRepo.updateContentByLegalId(legalSettingdao.getContent(), currentDate,
					this.getClass().getSimpleName(), new Long(legalSettingdao.getLegalId()));

			if (res == 0) {
				respDao.setMsg(unableUpdateLegal);
				respDao.setSuccess(Boolean.toString(false));
			} else {
				respDao.setMsg(updatedSuccess);
				respDao.setSuccess(Boolean.toString(true));
			}
		}

		logger.info("while updating Legal setting - {}", respDao.getMsg());
		return respDao;
	}

	@Transactional
	@Override
	public void checkAndResetLoginAttemptsForAdmin(String dateTimeFormat) throws Exception {
		LocalDateTime blockedDateTime = null;
		LocalDateTime currentTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());

		List<AdminUser> admiUusersList = adminAuthRepo.findAll();

		for (AdminUser adminUser : admiUusersList) {

			if (adminUser.getLockedDateTimeForLogin().isEmpty()) {
				continue;
			}

			String enLockedDateTime = (String) EncryptedDecryptedObjectUtil.getDecryptedString(
					adminUser.getLockedDateTimeForLogin(), secretKey, secretIv, isEncryptDecryptDatabaseData);
			String enLoginAttemptMaxReleaseTime = (String) EncryptedDecryptedObjectUtil.getDecryptedString(
					adminUser.getLoginAttemptMaxReleaseTime(), secretKey, secretIv, isEncryptDecryptDatabaseData);

			blockedDateTime = LocalDateTime.parse(enLockedDateTime, DateTimeFormatter.ofPattern(dateTimeFormat));
			currentTime = LocalDateTime.now();

			String enLoginFailCount = (String) EncryptedDecryptedObjectUtil.getEncryptedString("0", secretKey, secretIv,
					isEncryptDecryptDatabaseData);
			String enBlockForMaxAttemptFlag = (String) EncryptedDecryptedObjectUtil
					.getEncryptedString(Boolean.toString(false), secretKey, secretIv, isEncryptDecryptDatabaseData);
			String enLockedDataTime = (String) EncryptedDecryptedObjectUtil.getEncryptedString("", secretKey, secretIv,
					isEncryptDecryptDatabaseData);
			String encurrentDate = (String) EncryptedDecryptedObjectUtil.getEncryptedString(currentDate, secretKey,
					secretIv, isEncryptDecryptDatabaseData);
			String enUpdatePName = (String) EncryptedDecryptedObjectUtil.getEncryptedString(
					this.getClass().getSimpleName(), secretKey, secretIv, isEncryptDecryptDatabaseData);

			// Common condition to check OTP expiration
			if (isUserFailAttemptsTimeOver(blockedDateTime, currentTime,
					Integer.parseInt(enLoginAttemptMaxReleaseTime))) {
				// resetting failed attempts details to null once after successful login
				adminAuthRepo.updateLoginFailCount(enLoginFailCount, enBlockForMaxAttemptFlag, enLockedDataTime,
						enLoginFailCount, encurrentDate, enUpdatePName, adminUser.getAdminId());
			}

		}

	}

	// method to check time over for blocked user or not
	private boolean isUserFailAttemptsTimeOver(LocalDateTime blockedDateTime, LocalDateTime currentTime,
			int blockedTimeInMinutes) {
		// otpExpirationSeconds;
		return ChronoUnit.MINUTES.between(blockedDateTime, currentTime) > loginAttemptMaxReleaseMin;
	}

	@Transactional
	@Override
	public void resetBlockedUserSiteService(String dateTimeFormat) throws Exception {
		LocalDateTime blockedDateTime = null;
		LocalDateTime currentTime = null;

		List<UserSystemIPAddress> userIpAddresses = userIpRepository.findAll();

		if (!userIpAddresses.isEmpty()) {
			for (UserSystemIPAddress ip : userIpAddresses) {
				if (ip.getLockedDateTimeForLogin().isEmpty()) {
					continue;
				}

				String enLockedDateTime = (String) EncryptedDecryptedObjectUtil.getDecryptedString(
						ip.getLockedDateTimeForLogin(), secretKey, secretIv, isEncryptDecryptDatabaseData);
				String enLoginAttemptMaxReleaseTime = (String) EncryptedDecryptedObjectUtil.getDecryptedString(
						ip.getLoginAttemptMaxReleaseTime(), secretKey, secretIv, isEncryptDecryptDatabaseData);

				blockedDateTime = LocalDateTime.parse(enLockedDateTime, DateTimeFormatter.ofPattern(dateTimeFormat));
				currentTime = LocalDateTime.now();

				if (isReleaseBlockedSite(blockedDateTime, currentTime,
						Integer.parseInt(enLoginAttemptMaxReleaseTime))) {
					// Deleting the Blocked Sites after blocked time is over
					userIpRepository.deleteById(ip.getUserSystemIPId());
				}

			}
		}

	}

// method to check time over for blocked site or not
	private boolean isReleaseBlockedSite(LocalDateTime blockedDateTime, LocalDateTime currentTime,
			int blockedTimeInMinutes) {
		// otpExpirationSeconds;
		return ChronoUnit.MINUTES.between(blockedDateTime, currentTime) > siteBlockReleaseTime;
	}

}
