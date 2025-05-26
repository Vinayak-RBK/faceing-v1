package com.iss.service;

import org.springframework.stereotype.Service;

import com.iss.dao.AdminResponseDao;
import com.iss.dao.AdminUserDao;
import com.iss.dao.LegalSettingsDao;
import com.iss.dao.LegalSettingsRespDao;

@Service
public interface AdminAuthService {

	public AdminResponseDao adminLoginService(AdminUserDao dao, String dateTimeFormat) throws Exception;

	public AdminResponseDao adminForgetPasswordService(AdminUserDao dao, String currDatetime) throws Exception;

	public AdminResponseDao adminVerifyForgetPasswordService(AdminUserDao dao, String currDatetime) throws Exception;

	public AdminResponseDao resetAdminPasswordService(AdminUserDao dao, String currDatetime) throws Exception;

	public int findByEmailIdForAdminSignUp(AdminUserDao dao);

	public AdminResponseDao saveAdminWithOTP(AdminUserDao dao, String dateTimeFormat, boolean b);

	public AdminResponseDao verifyOtpForAdminSignup(AdminUserDao dao, String dateTimeFormat);

	public LegalSettingsRespDao getLegalSetting();

	public LegalSettingsRespDao updateLegalSettings(LegalSettingsDao dao, String dateTimeFormat);

	public void checkAndResetLoginAttemptsForAdmin(String dateTimeFormat) throws Exception;
	
	public void resetBlockedUserSiteService(String dateTimeFormat) throws Exception;

}
