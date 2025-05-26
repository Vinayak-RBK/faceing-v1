package com.iss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iss.entity.AdminUser;

@Repository
public interface AdminRepository extends JpaRepository<AdminUser, String>{

}
