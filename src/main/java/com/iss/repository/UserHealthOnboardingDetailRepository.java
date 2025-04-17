package com.iss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iss.entity.UserHealthOnboardingDetail;

@Repository
public interface UserHealthOnboardingDetailRepository extends JpaRepository<UserHealthOnboardingDetail, String>{

}
