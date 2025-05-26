package com.iss.service;


import org.springframework.stereotype.Service;

import com.iss.dao.GuestInfoListDao;
import com.iss.dao.PagerRequestDao;
import com.iss.dao.PagerResponseDao;

import jakarta.servlet.http.HttpServletResponse;

@Service
public interface GuestUserSearchService {
	
	public GuestInfoListDao findAllGuest(String userId);
	
	public GuestInfoListDao findGuestByGuestIdAndUserId(String guestId, String userId);// findAllGuestByUserId
	
	public PagerResponseDao findAllGuestByUserId(PagerRequestDao pagerDao);

	public GuestInfoListDao getGuestDetailsByExcel(String userId, HttpServletResponse response);

}
