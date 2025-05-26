package com.iss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.iss.entity.UserSystemIPAddress;

@Repository
public interface UserSystemIPAddressRepository extends JpaRepository<UserSystemIPAddress, Long>{
	
	@Query(value = "Select * from user_system_ip where ip_address=:ipAdress", nativeQuery = true)
	public UserSystemIPAddress findbyIpAdress(String ipAdress);

}
