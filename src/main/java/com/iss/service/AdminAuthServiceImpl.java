package com.iss.service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import com.iss.repository.AdminAuthRepository;
import com.iss.repository.LegalSettingsRepository;
import com.iss.util.CommonUtility;

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

	@Autowired
	private AdminAuthRepository adminAuthRepo;

	@Autowired
	private LegalSettingsRepository legalSetRepo;

	private AdminResponseDao _respDao = new AdminResponseDao();

	private AdminUser _adminUser = new AdminUser();

	@Transactional
	@Override
	public AdminResponseDao adminLoginService(AdminUserDao dao, String dateTimeFormat) {

		_adminUser = adminAuthRepo.findAdminUserByEmailId(dao.getEmail());
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());

		_respDao = new AdminResponseDao();

		if (_adminUser != null) {
//			_adminUser = adminAuthRepo.findAdminUserByEmailIdAndPassword(dao.getEmail(), dao.getPassword());
			if (!_adminUser.getAdminPassword().equals(dao.getPassword())) {
				_respDao.setMessage(wrongpassword);
				_respDao.setSuccess(false);

				// [Start] adding code to block after max attempts
				// Comparing with max failed count = maxAttemptCount
				if (_adminUser.getLoginAttemptFailCount() == maxAttemptCount) {
					_respDao.setMessage(accountBlockForFailAttempts + " " + _adminUser.getLoginAttemptMaxReleaseTime()
							+ " minutes");
					_respDao.setSuccess(false);

				}
				// Comparing with max failed count > maxAttemptCount
				else if (_adminUser.getLoginAttemptFailCount() > maxAttemptCount) {

					int res = adminAuthRepo.updateLoginFailTime(
							loginAttemptMaxReleaseMin + _adminUser.getLoginAttemptMaxReleaseTime(), currentDate,
							this.getClass().getSimpleName(), _adminUser.getAdminId());

					if (res != 0) {
						_respDao.setMessage(accountBlockForFailAttempts + " "
								+ (_adminUser.getLoginAttemptMaxReleaseTime() + loginAttemptMaxReleaseMin)
								+ " minutes");
						_respDao.setSuccess(false);
						_respDao.setAdminId(_adminUser.getAdminId().toString());
//						_respDao.setOTPVerified(_adminUser.getIsVerified());
//						_respDao.setOnBoarded(_adminUser.getIsOnBoarded());
//						_respDao.setBlocked(_adminUser.getIsBlocked());
						_respDao.setEmail(_adminUser.getAdminEmail());
						return _respDao;
					}
				}
				// Comparing with max failed count < maxAttemptCount
				else if (_adminUser.getLoginAttemptFailCount() < maxAttemptCount) {
					_respDao.setMessage(passwordWrong);
				}

				boolean maxAttemptBlockFail = false;

				if (_adminUser.getLoginAttemptFailCount() >= 3) {
					maxAttemptBlockFail = true;
				}

				adminAuthRepo.updateLoginFailCount(_adminUser.getLoginAttemptFailCount() + 1, maxAttemptBlockFail,
						currentDate, loginAttemptMaxReleaseMin, currentDate, this.getClass().getSimpleName(),
						_adminUser.getAdminId());

				_respDao.setSuccess(false);
				// [End] adding code to block after max attempts
			} else {

				// Comparing with max failed count >= maxAttemptCount for success login
				if (_adminUser.getLoginAttemptFailCount() >= maxAttemptCount) {
					_respDao.setMessage(successLoginAfterMaxAttempts + " " + _adminUser.getLoginAttemptMaxReleaseTime()
							+ " minutes");
					_respDao.setSuccess(false);
					_respDao.setAdminId(_adminUser.getAdminId().toString());
//					_respDao.setOTPVerified(_adminUser.getIsVerified());
//					resDao.setOnBoarded(_adminUser.getIsOnBoarded());
//					resDao.setBlocked(_adminUser.getIsBlocked());
					_respDao.setEmail(_adminUser.getAdminEmail());

					return _respDao;
				}

				if (_adminUser.getIsVerified()) {
					_respDao.setMessage(loginSuccess);
					_respDao.setAdminId(_adminUser.getAdminId().toString());
					_respDao.setEmail(_adminUser.getAdminEmail());
					_respDao.setAdmin(_adminUser.getIsAdmin());
					_respDao.setUserManagement(_adminUser.getIsUserManagement());
					_respDao.setQuestionaries(_adminUser.getIsQuestionaries());
					_respDao.setLegalSetting(_adminUser.getIsLegalSetting());
					_respDao.setSuccess(true);
				} else {
					_respDao.setMessage(unVerified);
					_respDao.setAdminId(_adminUser.getAdminId().toString());
					_respDao.setEmail(_adminUser.getAdminEmail());
					_respDao.setAdmin(_adminUser.getIsAdmin());
					_respDao.setUserManagement(_adminUser.getIsUserManagement());
					_respDao.setQuestionaries(_adminUser.getIsQuestionaries());
					_respDao.setLegalSetting(_adminUser.getIsLegalSetting());
					_respDao.setSuccess(false);
				}

				adminAuthRepo.updateLoginFailCount(0, false, "", 0, currentDate, this.getClass().getSimpleName(),
						_adminUser.getAdminId());

			}
		} else {
			_respDao.setMessage(emailNotFound);
			_respDao.setSuccess(false);
		}
		return _respDao;

	}

	@Transactional
	@Override
	public AdminResponseDao adminForgetPasswordService(AdminUserDao dao, String curDateTime) {
		String newOtp = "";
		_adminUser = adminAuthRepo.findAdminUserByEmailId(dao.getEmail());

		if (_adminUser == null) {
			_respDao.setMessage(emailNotFound);
			_respDao.setSuccess(false);
		} else {

			if (_adminUser.getIsVerified()) {

				newOtp = CommonUtility.generateOTP();
				adminAuthRepo.updateAdminOtpUserByEmailId(newOtp, curDateTime, curDateTime,
						this.getClass().getSimpleName(), dao.getEmail());

				_respDao.setEmail(_adminUser.getAdminEmail());
				_respDao.setMessage(otpSent);
				_respDao.setSuccess(true);
				_respDao.setOtp(newOtp);
				_respDao.setAdminId(_adminUser.getAdminId().toString());
			} else {
				_respDao.setEmail(_adminUser.getAdminEmail());
				_respDao.setMessage(unVerified);
				_respDao.setSuccess(false);
				_respDao.setAdminId(_adminUser.getAdminId().toString());
			}
		}

		return _respDao;
	}

	@SuppressWarnings("removal")
	@Override
	public AdminResponseDao adminVerifyForgetPasswordService(AdminUserDao dao, String dateTimeFormat) {

		_adminUser = adminAuthRepo.findAdminUserByAdminIdAndOtp(dao.getAdminOTP(), new Long(dao.getAdminId()));

		if (_adminUser == null) {
			_respDao.setMessage(otpError);
			_respDao.setSuccess(false);
		} else {

			LocalDateTime otpEntryDate = LocalDateTime.parse(_adminUser.getOtpEntryDate(),
					DateTimeFormatter.ofPattern(dateTimeFormat));
			LocalDateTime currentTime = LocalDateTime.now();

			// Common condition to check OTP expiration
			if (isOtpExpired(otpEntryDate, currentTime)) {
				_respDao.setMessage(resendOtp);
				_respDao.setSuccess(false);
				return _respDao;
			}
			_respDao.setEmail(_adminUser.getAdminEmail());
			_respDao.setMessage(otpVerifiedSuccess);
			_respDao.setSuccess(true);
			_respDao.setAdminId(dao.getAdminId().toString());
		}

		return _respDao;
	}

	@SuppressWarnings({ "removal" })
	@Transactional
	@Override
	public AdminResponseDao resetAdminPasswordService(AdminUserDao dao, String curDateTime) {
		int success = 0;

		Optional<AdminUser> adminUser = adminAuthRepo.findById(dao.getAdminId().toString());

		if (adminUser.isEmpty()) {
			_respDao.setMessage(emailNotFound);
			_respDao.setSuccess(false);
			return _respDao;
		}

		if (!dao.getPassword().equals(dao.getConfirmPassword())) {
			_respDao.setMessage(passwordMismatch);
			_respDao.setSuccess(false);
			return _respDao;
		}

		success = adminAuthRepo.updateAdminPasswordByAdminId(dao.getPassword(), curDateTime,
				this.getClass().getSimpleName(), new Long(dao.getAdminId()));

		if (success == 1) {
			_respDao.setEmail(_adminUser.getAdminEmail());
			_respDao.setMessage(passwordSuccess);
			_respDao.setSuccess(true);
			_respDao.setAdminId(dao.getAdminId().toString());
		} else {
			_respDao.setEmail(_adminUser.getAdminEmail());
			_respDao.setMessage(unableReset);
			_respDao.setSuccess(false);
			_respDao.setAdminId(dao.getAdminId().toString());
		}

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
			if (user.getIsVerified()) {
				System.out.println("return1");
				return 1;
			} else {
				System.out.println("return2");
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
			AdminUser user = new AdminUser();

			if (isNewAdmin) {

				// Generate new OTP if expired or not found
				user.setAdminEmail(dao.getEmail());
				user.setAdminPassword(dao.getPassword());
				user.setAdminOTP(newOtp);
				user.setIsVerified(false);
				user.setIsAdmin(true);
				user.setIsUserManagement(true);
				user.setIsQuestionaries(true);
				user.setIsLegalSetting(true);
				user.setOtpEntryDate(currentDate);
				user.setLoginAttemptFailCount(0);
				user.setLoginAttemptMaxReleaseTime(0);
				user.setLockedDateTimeForLogin("");

				user.setRegistDate(currentDate);
				user.setRegistPName(this.getClass().getSimpleName());
				user.setLastUpdateDate(currentDate);
				user.setLastUpdatePName(this.getClass().getSimpleName());

				adminAuthRepo.save(user);
			} else {
				adminAuthRepo.updateAdminByEmailId(newOtp, currentDate, currentDate, this.getClass().getSimpleName(),
						dao.getEmail());
			}

			user = adminAuthRepo.findAdminUserByEmailId(dao.getEmail());

			resDao.setAdminId(user.getAdminId().toString());
			resDao.setOtp(newOtp);
			resDao.setEmail(dao.getEmail());
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
	public AdminResponseDao verifyOtpForAdminSignup(AdminUserDao dao, String dateTimeFormat) {

		AdminUser user = new AdminUser();
		AdminResponseDao resDao = new AdminResponseDao();
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());
		user = adminAuthRepo.findByAdminEmailIdAndOtp(dao.getEmail(), dao.getAdminOTP(), new Long(dao.getAdminId()));

		if (user == null) {
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

			adminAuthRepo.updateVerifiedByEmailId(true, currentDate, this.getClass().getSimpleName(),
					new Long(dao.getAdminId()));
		}

		resDao.setAdminId(user.getAdminId().toString());
		resDao.setQuestionaries(user.getIsQuestionaries());
		resDao.setUserManagement(user.getIsUserManagement());
		resDao.setLegalSetting(user.getIsLegalSetting());
		resDao.setAdmin(user.getIsAdmin());
		resDao.setEmail(dao.getEmail());
		resDao.setMessage(otpVerifiedSuccess);
		resDao.setSuccess(true);

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
			resDao.setSuccess(true);
		} else {
			resDao.setMsg(legalsettingNotAvailble);
			resDao.setSuccess(false);
		}
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
			respDao.setSuccess(false);
		} else {
			res = legalSetRepo.updateContentByLegalId(legalSettingdao.getContent(), currentDate,
					this.getClass().getSimpleName(), new Long(legalSettingdao.getLegalId()));

			if (res == 0) {
				respDao.setMsg(unableUpdateLegal);
				respDao.setSuccess(false);
			} else {
				respDao.setMsg(updatedSuccess);
				respDao.setSuccess(true);
			}
		}
		return respDao;
	}

	@Override
	public void checkAndResetLoginAttemptsForAdmin(String dateTimeFormat) {
		LocalDateTime blockedDateTime = null;
		LocalDateTime currentTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());

		List<AdminUser> admiUusersList = adminAuthRepo.findAllBlockedUserForLogin(true);

		for (AdminUser adminUser : admiUusersList) {
			blockedDateTime = LocalDateTime.parse(adminUser.getLockedDateTimeForLogin(),
					DateTimeFormatter.ofPattern(dateTimeFormat));
			currentTime = LocalDateTime.now();
			// Common condition to check OTP expiration
			if (isUserFailAttemptsTimeOver(blockedDateTime, currentTime, adminUser.getLoginAttemptMaxReleaseTime())) {
				// resetting failed attempts details to null once after successful login
				adminAuthRepo.updateLoginFailCount(0, false, "", 0, currentDate, this.getClass().getSimpleName(),
						adminUser.getAdminId());
			}

		}

	}

	private boolean isUserFailAttemptsTimeOver(LocalDateTime blockedDateTime, LocalDateTime currentTime,
			int blockedTimeInMinutes) {
		// otpExpirationSeconds;
		return ChronoUnit.MINUTES.between(blockedDateTime, currentTime) > loginAttemptMaxReleaseMin;
	}

}
