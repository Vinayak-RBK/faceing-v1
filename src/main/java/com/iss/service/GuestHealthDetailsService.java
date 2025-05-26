package com.iss.service;

import org.springframework.stereotype.Service;

import com.iss.dao.GuestHealthDetailsDao;

@Service
public interface GuestHealthDetailsService {
	
	public boolean addGuestHealthDetailService(GuestHealthDetailsDao guestHealthDetailsDao,String dateTimeFormat);
	

}
