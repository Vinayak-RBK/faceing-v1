package com.iss.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;


import jakarta.persistence.EntityManager;

@Repository
public class SequenceRepository {
	
	@Autowired
	public EntityManager em;
	
	@Value("${DOMAIN_NAME}")
	private String domainName;
	
	private String year=String.format("%02d", LocalDate.now().getYear() % 100);;
	
	// generating sequence reference for End User
	public String getNextSequenceForEndUser()
	{
		System.out.println("Domain name is : "+domainName);
		String sqlQuery = "SELECT next_val FROM user_sequence";
	    
	    @SuppressWarnings("unchecked")
		List<Object[]> resultList = em.createNativeQuery(sqlQuery).getResultList();
	    
		return domainName+year+resultList.get(0);
	}
	
	// generating sequence reference for Super Admin
	public String getNextSequenceForSuperAdmin()
	{
		String sqlQuery = "SELECT next_val FROM admin_sequence";
	    
	    @SuppressWarnings("unchecked")
		List<Object[]> resultList = em.createNativeQuery(sqlQuery).getResultList();
	    
		return domainName+year+resultList.get(0);
	}

}
