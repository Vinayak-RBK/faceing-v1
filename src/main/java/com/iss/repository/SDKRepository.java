package com.iss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iss.entity.SDKTable;

@Repository
public interface SDKRepository extends JpaRepository<SDKTable, String>{
	
	

}
