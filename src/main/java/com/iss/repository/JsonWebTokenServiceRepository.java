package com.iss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iss.entity.JsonWebToken;

@Repository
public interface JsonWebTokenServiceRepository extends JpaRepository<JsonWebToken, String>{

}
