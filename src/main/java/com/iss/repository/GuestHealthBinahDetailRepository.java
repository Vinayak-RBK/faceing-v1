package com.iss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iss.entity.GuestHealthBinahDetail;

@Repository
public interface GuestHealthBinahDetailRepository extends JpaRepository<GuestHealthBinahDetail, String>{
	
	@Query(value = "Select * from guest_health_binah_detail where guest_id=:guestId", nativeQuery = true)
	public GuestHealthBinahDetail findByGuestId(String guestId);

}
