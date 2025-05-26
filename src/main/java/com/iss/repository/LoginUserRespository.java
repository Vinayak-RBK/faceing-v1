package com.iss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iss.entity.EndUser;

import jakarta.transaction.Transactional;

@Repository
public interface LoginUserRespository extends JpaRepository<EndUser, String> {

	@Query(value = "Select * from end_user where user_email_id=?1", nativeQuery = true)
	public EndUser findByEmailId(String emailId);

	@Query(value = "Select count(*) from end_user where user_email_id=?1 and user_password=?2 and is_verified='0'", nativeQuery = true)
	public int findByEmailIdAndPassword(String emailId, String password);
	
	@Query(value = "Select count(*) from end_user where is_onboarded=:isOnboraded", nativeQuery = true)
	public int findAllUsers(String isOnboraded);
	
	@Query(value = "Select count(*) from end_user eu inner join users_personal_details up on eu.user_id=up.user_id \r\n"
			+ "where eu.is_onboarded=:isOnboraded and up.user_email_id=:emailId;", nativeQuery = true)
	public int findAllUsersByEmail(String isOnboraded, String emailId);
	
	@Query(value = "Select count(*) from end_user eu inner join users_personal_details up on eu.user_id=up.user_id \r\n"
			+ "where eu.is_onboarded=:isOnboraded and up.user_name=:name;", nativeQuery = true)
	public int findAllUsersByName(String isOnboraded, String name);
	
	@Query(value = "Select count(*) from end_user eu inner join users_personal_details up on eu.user_id=up.user_id \r\n"
			+ "where eu.is_onboarded=:isOnboraded and up.user_name=:name and up.user_email_id=:emailId;", nativeQuery = true)
	public int findAllUsersByEmailAndName(String isOnboraded, String emailId, String name);

	@Modifying
	@Transactional
	@Query(value = "UPDATE end_user SET user_otp = ?2, otp_entry_date = ?3 WHERE user_email_id = ?1", nativeQuery = true)
	int updateOtpByEmail(String emailId, String otp, String otpEntryDate);

	@Query(value = "SELECT * FROM end_user WHERE user_email_id = ?1 and user_otp=?2 and user_id=?3", nativeQuery = true)
	public EndUser findByEmailIdAndOtp(String emailId, String otp, Long userId);

	@Query(value = "SELECT otp_entry_date FROM end_user WHERE user_email_id = ?1", nativeQuery = true)
	String findOtpEntryDateByEmailId(String emailId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE end_user SET user_password = :password,last_update_date=:lastUpdateDate, last_update_pname=:lastUpPName WHERE user_email_id = :emailId and user_id=:userId", nativeQuery = true)
	public int updatePasswordByEmail(String emailId, String userId, String password, String lastUpdateDate,
			String lastUpPName);

	@Query(value = "Select * from end_user where user_email_id=?1", nativeQuery = true)
	public EndUser findUserByEmailId(String emailId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE end_user SET is_verified = :isVerified, last_update_date=:lastUpdateDate, last_update_pname=:lastUpPName  WHERE user_email_id = :emailId", nativeQuery = true)
	public int updateVerifiedByEmailId(String isVerified, String lastUpdateDate, String lastUpPName, String emailId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE end_user SET user_otp=:otp, user_password = :password,otp_entry_date=:otpGenerateDate, last_update_date=:lastUpdateDate, last_update_pname=:lastUpPName  WHERE user_email_id = :emailId", nativeQuery = true)
	public int updateUserByEmailId(String otp, String password, String otpGenerateDate, String lastUpdateDate,
			String lastUpPName, String emailId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE end_user SET is_onboarded=:isOnboarded,last_update_date=:lastUpdateDate, last_update_pname=:lastUpPName WHERE user_id = :userId and user_email_id = :emailId", nativeQuery = true)
	public int updateUserByOnboardedFlag(String isOnboarded, String lastUpdateDate, String lastUpPName, Long userId,
			String emailId);

	@Modifying
	@Transactional
	@Query(value = "delete from end_user where is_onboarded=:onboardPending;", nativeQuery = true)
	public int deleteUsers(String onboardPending);

	@Modifying
	@Transactional
	@Query(value = "UPDATE end_user SET is_login=:isLogin,last_update_date=:lastUpdateDate, last_update_pname=:lastUpPName WHERE user_id = :userId and user_email_id = :emailId", nativeQuery = true)
	public int updateUserLoginFlag(String isLogin, String lastUpdateDate, String lastUpPName, Long userId,
			String emailId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE users_personal_details SET user_image=:imagePath,last_update_date=:lastUpdateDate, last_update_pname=:lastUpPName WHERE user_id = :userId;", nativeQuery = true)
	public int updateUserProfilePictureByUserId(String imagePath, String lastUpdateDate, String lastUpPName,
			Long userId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE end_user SET login_attempt_fail_count=:loginCountFail,max_attempt_fail_block_login=:blockFormaxAttempt,locked_date_time_for_login=:lockedDateTime, "
			+ "login_attempt_max_release_time=:loginAttemptMaxRelTime, last_update_date=:lastUpdateDate, last_update_pname=:lastUpPName WHERE user_id = :userId;", nativeQuery = true)
	public int updateLoginFailCount(String loginCountFail, String blockFormaxAttempt, String lockedDateTime,
			String loginAttemptMaxRelTime, String lastUpdateDate, String lastUpPName, Long userId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE end_user SET login_attempt_max_release_time=:loginAttemptMaxRelTime, last_update_date=:lastUpdateDate, last_update_pname=:lastUpPName WHERE user_id = :userId;", nativeQuery = true)
	public int updateLoginFailTime(String loginAttemptMaxRelTime, String lastUpdateDate, String lastUpPName,
			Long userId);

	@Query(value = "Select * from end_user where max_attempt_fail_block_login=?1", nativeQuery = true)
	public List<EndUser> findAllBlockedUserForLogin(String isblockedUserForMaxAttempts);

}
