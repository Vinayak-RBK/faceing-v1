package com.iss.service;

import org.springframework.stereotype.Service;

import com.iss.dao.GuestHealthAnuraDetailsDao;

@Service
public interface GuestHealthDetailsService {
	
	public boolean addGuestHealthDetailService(GuestHealthAnuraDetailsDao guestHealthDetailsDao,String dateTimeFormat);
	

}
