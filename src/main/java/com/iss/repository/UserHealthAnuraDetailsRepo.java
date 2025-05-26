package com.iss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iss.entity.UserHealthAnuraDetail;

@Repository
public interface UserHealthAnuraDetailsRepo extends JpaRepository<UserHealthAnuraDetail, Long> {

	@Query(value = "Select userHD.user_personal_detail_id, userHD.user_health_detail_id,userHD.user_email_id,userHD.user_respiration_rate,userHD.user_blood_pressure"
			+ ",userHD.user_heart_rate,userHD.user_oxygen_saturation,userHD.user_hrv_data,userHD.user_stress_level,"
			+ "userHD.user_relaxation_level,userHD.user_energy_level,userHD.user_body_shape_index,userHD.user_body_mass_index,"
			+ "userHD.user_age,userHD.user_hemoglobin_level,userHD.user_fitness_level,userHD.regist_date,userHD.regist_pname,"
			+ "userHD.last_update_date,userHD.last_update_pname from user_health_detail userHD"
			+ " left join users_personal_details userPD "
			+ " on userHD.user_personal_detail_id=userPD.user_personal_detail_id where userPD.user_id=:userId;", nativeQuery = true)
	public List<UserHealthAnuraDetail> findAllFaceSacnHistoryByUserId(String userId);
	
	@Query(value = "Select userHD.user_personal_detail_id, userHD.user_health_detail_id,userHD.user_email_id,userHD.user_respiration_rate,userHD.user_blood_pressure"
			+ ",userHD.user_heart_rate,userHD.user_oxygen_saturation,userHD.user_hrv_data,userHD.user_stress_level,"
			+ "userHD.user_relaxation_level,userHD.user_energy_level,userHD.user_body_shape_index,userHD.user_body_mass_index,"
			+ "userHD.user_age,userHD.user_hemoglobin_level,userHD.user_fitness_level,userHD.regist_date,userHD.regist_pname,"
			+ "userHD.last_update_date,userHD.last_update_pname from user_health_detail userHD"
			+ " left join users_personal_details userPD "
			+ " on userHD.user_personal_detail_id=userPD.user_personal_detail_id where userPD.user_id=:userId and userHD.user_health_detail_id=:scanId;", nativeQuery = true)
	public List<UserHealthAnuraDetail> findUserFaceScanHistoryByScanId(String userId, String scanId);
	
	@Query(value = "Select * from user_health_anura_detail where user_personal_detail_id=:userPerDetailsId", nativeQuery = true)
	public List<UserHealthAnuraDetail> findUserHealthByPersonalDetailsId(String userPerDetailsId);
	
	@Query(value = "Select * from user_health_anura_detail where user_id=:userId order by regist_date DESC", nativeQuery = true)
	public List<UserHealthAnuraDetail> findByUserId(String userId);
	
	@Query(value = "Select * from user_health_anura_detail where user_health_anura_detail_id=:healthId and user_id=:userId", nativeQuery = true)
	public List<UserHealthAnuraDetail> findByScanIdAndUserId(String healthId,String userId);
	

}
