package com.iss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iss.entity.GuestHealthAnuraDetail;

@Repository
public interface GuestHealthAnuraDetailRepository extends JpaRepository<GuestHealthAnuraDetail, String>{
	
	@Query(value = "Select * from guest_health_anura_detail where guest_id=:guestId", nativeQuery = true)
	public GuestHealthAnuraDetail findByGuestId(String guestId);

}
