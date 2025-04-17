package com.iss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iss.entity.GuestHealthDetail;

@Repository
public interface GuestHealthDetailRepository extends JpaRepository<GuestHealthDetail, String>{

}
