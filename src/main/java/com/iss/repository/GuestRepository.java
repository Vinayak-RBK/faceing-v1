package com.iss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iss.entity.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long>{
	
	

}
