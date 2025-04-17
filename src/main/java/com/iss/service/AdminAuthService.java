package com.iss.service;

import org.springframework.stereotype.Service;

import com.iss.dao.AdminResponseDao;
import com.iss.dao.AdminUserDao;
import com.iss.dao.LegalSettingsDao;
import com.iss.dao.LegalSettingsRespDao;

@Service
public interface AdminAuthService {

	public AdminResponseDao adminLoginService(AdminUserDao dao, String dateTimeFormat);

	public AdminResponseDao adminForgetPasswordService(AdminUserDao dao, String currDatetime);

	public AdminResponseDao adminVerifyForgetPasswordService(AdminUserDao dao, String currDatetime);

	public AdminResponseDao resetAdminPasswordService(AdminUserDao dao, String currDatetime);

	public int findByEmailIdForAdminSignUp(AdminUserDao dao);

	public AdminResponseDao saveAdminWithOTP(AdminUserDao dao, String dateTimeFormat, boolean b);

	public AdminResponseDao verifyOtpForAdminSignup(AdminUserDao dao, String dateTimeFormat);

	public LegalSettingsRespDao getLegalSetting();

	public LegalSettingsRespDao updateLegalSettings(LegalSettingsDao dao, String dateTimeFormat);

	public void checkAndResetLoginAttemptsForAdmin(String dateTimeFormat);

}
