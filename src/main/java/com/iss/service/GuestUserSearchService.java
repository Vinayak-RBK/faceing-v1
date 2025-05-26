package com.iss.service;


import org.springframework.stereotype.Service;

import com.iss.dao.GuestInfoListDao;

@Service
public interface GuestUserSearchService {
	
	public GuestInfoListDao findAllGuest(String userId);

}
