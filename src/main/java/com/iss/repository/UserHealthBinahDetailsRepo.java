package com.iss.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iss.entity.UserHealthBinahDetail;

public interface UserHealthBinahDetailsRepo extends JpaRepository<UserHealthBinahDetail, Long> {
	
	@Query(value = "Select * from user_health_binah_detail where user_id=:userId order by regist_date DESC", nativeQuery = true)
	public List<UserHealthBinahDetail> findByUserId(String userId);
	
	@Query(value = "Select * from user_health_binah_detail where user_health_binah_detail_id=:healthId and user_id=:userId", nativeQuery = true)
	public List<UserHealthBinahDetail> findByScanIdAndUserId(String healthId,String userId);

}
