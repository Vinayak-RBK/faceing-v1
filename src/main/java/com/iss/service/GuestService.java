package com.iss.service;

import org.springframework.stereotype.Service;

import com.iss.dao.GuestHealthInfoDao;
import com.iss.dao.Response;

@Service
public interface GuestService{
	
	public Response addGuest(GuestHealthInfoDao guestInfoDao, String dateTimeFormat);
	
	public Response removeGuest(String guestId, String userId,String dateTimeFormat);

}
