package com.iss.service;

import org.springframework.stereotype.Service;

import com.iss.dao.GuestInfoDao;
import com.iss.dao.Response;

@Service
public interface GuestService{
	
	public Response addGuest(GuestInfoDao guestInfoDao, String dateFormat);

}
