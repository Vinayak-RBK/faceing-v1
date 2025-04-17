package com.iss.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iss.entity.CommonUserDetailsTable;
import com.iss.entity.EndUser;
import com.iss.entity.UserHealthOnboardingDetail;
import com.iss.entity.UserPersonalAndHealthDetails;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<EndUser, Long> {

	@Query(value = " Select * from end_user order by user_id DESC", nativeQuery = true)
	List<EndUser> findAllByOrderByIdDesc();

	@Query(value = "SELECT userp.user_name, userp.user_gender, userp.user_weight, "
			+ "userp.user_dob, userp.user_email_id, euser.user_id, "
			+ "userp.user_height, userp.user_image, userp.user_personal_detail_id,euser.is_blocked "
			+ "FROM users_personal_details userp "
			+ "INNER JOIN end_user euser ON userp.user_id = euser.user_id order by euser.user_id DESC", nativeQuery = true)
	List<CommonUserDetailsTable> searchUserListDetails();

	@Query(value = " Select userd.user_id,userd.user_email_id,userd.user_password,userPerd.user_dob,userPerd.user_gender,\r\n"
			+ " userPerd.user_height,userPerd.user_image,userPerd.user_name,userPerd.user_weight,userHD.user_age,\r\n"
			+ " userHD.user_blood_pressure,userHD.user_body_mass_index,userHD.user_body_shape_index,userHD.user_energy_level,\r\n"
			+ " userHD.user_fitness_level,userHD.user_heart_rate,userHD.user_hemoglobin_level,userHD.user_hrv_data,\r\n"
			+ " userHD.user_oxygen_saturation,userHD.user_relaxation_level,userHD.user_respiration_rate,userHD.user_stress_level\r\n"
			+ " from users_personal_details userPerd\r\n"
			+ " inner join end_user userd on userPerd.user_id=userd.user_id \r\n"
			+ " right join user_health_detail userHD on userPerd.user_personal_detail_id = userHD.user_personal_detail_id where userPerd.user_id=:userId;", nativeQuery = true)
	public List<UserPersonalAndHealthDetails> getUserDetailsByUserId(Long userId);

	@Query(value = "select userd.user_id,userHD.id,userHD.on_boarding_question_name, userHD.user_health_onboarding_id,\r\n"
			+ " userHD.on_boarding_answer_value from end_user userd \r\n"
			+ " right join users_health_onboarding_details userHD \r\n" + " on userd.user_id=userHD.user_id where userHD.user_id=:userId;\r\n"
			+ "", nativeQuery = true)
	public List<UserHealthOnboardingDetail> getUserHealthOnboardingByUserId(Long userId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE users_personal_details SET user_gender=:gender, user_height=:height, user_name=:userName, user_dob=:dob, user_weight=:weight,last_update_date=:lastUpdateDate, last_update_pname=:lastUpdatePName  WHERE user_id =:userId;", nativeQuery = true)
	public int updateUserByUserId(String gender, BigDecimal height, String userName,String dob, BigDecimal weight,
			String lastUpdateDate, String lastUpdatePName, Long userId);
}