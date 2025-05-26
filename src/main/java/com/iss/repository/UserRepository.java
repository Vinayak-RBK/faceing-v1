package com.iss.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iss.entity.CommonUserDetailsTable;
import com.iss.entity.EndUser;
import com.iss.entity.UserHealthOnboardingDetail;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<EndUser, Long> {

	@Query(value = " Select * from end_user order by user_id DESC", nativeQuery = true)
	List<EndUser> findAllByOrderByIdDesc();

	@Query(value = "SELECT userp.user_name, userp.user_gender, userp.user_weight, "
			+ "userp.user_dob, userp.user_email_id, euser.user_id, "
			+ "userp.user_height, userp.user_image, userp.user_personal_detail_id,euser.is_blocked ,euser.sdk_type, euser.user_password, euser.job_role  "
			+ "FROM users_personal_details userp "
			+ "INNER JOIN end_user euser ON userp.user_id = euser.user_id order by euser.user_id DESC", nativeQuery = true)
	List<CommonUserDetailsTable> searchUserListDetails(Pageable pageable);

	@Query(value = "SELECT userp.user_name, userp.user_gender, userp.user_weight, "
			+ "userp.user_dob, userp.user_email_id, euser.user_id, "
			+ "userp.user_height, userp.user_image, userp.user_personal_detail_id,euser.is_blocked ,euser.sdk_type, euser.user_password, euser.job_role  "
			+ "FROM users_personal_details userp "
			+ "INNER JOIN end_user euser ON userp.user_id = euser.user_id where userp.user_name like %:orderByNameValue% order by euser.user_id DESC", nativeQuery = true)
	List<CommonUserDetailsTable> searchUserListDetailsSearchByName(Pageable pageable, String orderByNameValue);

//	@Query(value = " Select userd.user_id,userd.user_email_id,userd.user_password,userPerd.user_dob,userPerd.user_gender,\r\n"
//			+ " userPerd.user_height,userPerd.user_image,userPerd.user_name,userPerd.user_weight,userHD.absi,\r\n"
//			+ " userHD.age,userHD.bmi_calc,userHD.bp_bpm,userHD.bp_cvd,\r\n"
//			+ " userHD.bp_rpp,userHD.bp_stroke,userHD.bp_systolic,userHD.bp_tau,\r\n"
//			+ " userHD.bp_heart_attack,userHD.gender,userHD.hr_bpm,userHD.hrv_sdnn,\r\n"
//			+ " userHD.health_score,userHD.height,userHD.msi,userHD.mental_score,\r\n"
//			+ " userHD.physical_score,userHD.risk_score,userHD.snr,userHD.thbcount,\r\n"
//			+ " userHD.vital_score,userHD.waist_circum\r\n" + " from users_personal_details userPerd\r\n"
//			+ " inner join end_user userd on userPerd.user_id=userd.user_id \r\n"
//			+ " right join user_personal_health_details userHD on userPerd.user_personal_detail_id = userHD.user_id where userPerd.user_id=:userId;", nativeQuery = true)
//	public List<UserPersonalAndHealthDetails> getUserDetailsByUserId(Long userId);

	@Query(value = "select userd.user_id,userHD.id,userHD.on_boarding_question_name, userHD.user_health_onboarding_id,\r\n"
			+ " userHD.on_boarding_answer_value from end_user userd \r\n"
			+ " right join users_health_onboarding_details userHD \r\n"
			+ " on userd.user_id=userHD.user_id where userHD.user_id=:userId;\r\n" + "", nativeQuery = true)
	public List<UserHealthOnboardingDetail> getUserHealthOnboardingByUserId(Long userId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE users_personal_details SET user_gender=:gender, user_height=:height, user_name=:userName, user_dob=:dob, user_weight=:weight,last_update_date=:lastUpdateDate, last_update_pname=:lastUpdatePName  WHERE user_id =:userId;", nativeQuery = true)
	public int updateUserByUserId(String gender, String height, String userName, String dob, String weight,
			String lastUpdateDate, String lastUpdatePName, Long userId);
}