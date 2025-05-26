package com.iss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iss.entity.AdminUser;
import com.iss.entity.EndUser;

import jakarta.transaction.Transactional;

@Repository
public interface AdminAuthRepository extends JpaRepository<AdminUser, String> {

	@Query(value = "Select * from admin_users where admin_email_id=?1", nativeQuery = true)
	public AdminUser findAdminUserByEmailId(String emailId);

	@Query(value = "Select * from admin_users where admin_email_id=?1 and admin_password=?2", nativeQuery = true)
	public AdminUser findAdminUserByEmailIdAndPassword(String emailId, String password);

	@Modifying
	@Transactional
	@Query(value = "UPDATE admin_users SET admin_otp=:otp, otp_entry_date=:otpGenerateDate, last_update_date=:lastUpdateDate, last_update_pname=:lastUpPName  WHERE admin_email_id = :emailId", nativeQuery = true)
	public int updateAdminOtpUserByEmailId(String otp, String otpGenerateDate, String lastUpdateDate,
			String lastUpPName, String emailId);

	@Query(value = "SELECT * FROM admin_users WHERE admin_otp=?1 and admin_id=?2", nativeQuery = true)
	public AdminUser findAdminUserByAdminIdAndOtp(String otp, Long adminId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE admin_users SET admin_password=:password, last_update_date=:lastUpdateDate, last_update_pname=:lastUpPName  WHERE admin_id = :adminId", nativeQuery = true)
	public int updateAdminPasswordByAdminId(String password, String lastUpdateDate, String lastUpPName, Long adminId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE admin_users SET admin_otp = :newOtp,otp_entry_date=:otpEntryDate, last_update_date=:lastUpdateDate, last_update_pname=:lastUpPName  WHERE admin_email_id = :adminEmail", nativeQuery = true)
	public void updateAdminByEmailId(String newOtp, String otpEntryDate, String lastUpdateDate, String lastUpPName,
			String adminEmail);

	@Modifying
	@Transactional
	@Query(value = "UPDATE admin_users SET is_verified = :isVerified, last_update_date=:lastUpdateDate, last_update_pname=:lastUpPName  WHERE admin_id = :adminId", nativeQuery = true)
	public int updateVerifiedByEmailId(boolean isVerified, String lastUpdateDate, String lastUpPName, Long adminId);

	@Query(value = "SELECT * FROM admin_users WHERE admin_email_id = ?1 and admin_otp=?2 and admin_id=?3", nativeQuery = true)
	public AdminUser findByAdminEmailIdAndOtp(String adminEmail, String adminOTP, Long adminId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE admin_users SET login_attempt_fail_count=:loginCountFail,max_attempt_fail_block_login=:blockFormaxAttempt,locked_date_time_for_login=:lockedDateTime, "
			+ "login_attempt_max_release_time=:loginAttemptMaxRelTime, last_update_date=:lastUpdateDate, last_update_pname=:lastUpPName WHERE admin_id = :adminId;", nativeQuery = true)
	public int updateLoginFailCount(int loginCountFail, boolean blockFormaxAttempt, String lockedDateTime,
			int loginAttemptMaxRelTime, String lastUpdateDate, String lastUpPName, Long adminId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE admin_users SET login_attempt_max_release_time=:loginAttemptMaxRelTime, last_update_date=:lastUpdateDate, last_update_pname=:lastUpPName WHERE admin_id = :adminId;", nativeQuery = true)
	public int updateLoginFailTime(int loginAttemptMaxRelTime, String lastUpdateDate, String lastUpPName, Long adminId);

	@Query(value = "Select * from admin_users where max_attempt_fail_block_login=?1", nativeQuery = true)
	public List<AdminUser> findAllBlockedUserForLogin(boolean isblockedUserForMaxAttempts);

}
