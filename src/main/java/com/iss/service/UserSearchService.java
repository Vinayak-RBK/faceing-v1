package com.iss.service;


import org.springframework.stereotype.Service;

import com.iss.dao.AdminOperationResponseDao;
import com.iss.dao.AdminUserPersonalDetailsDao;
import com.iss.dao.PagerRequestDao;
import com.iss.dao.PagerResponseDao;
import com.iss.dao.Response;
import com.iss.dao.UserPersonalAndHealthDetailsDao;

import jakarta.servlet.http.HttpServletResponse;


@Service
public interface UserSearchService {

	public PagerResponseDao getAllUserDetails(String dateTimeFormat,PagerRequestDao pagerDao);

	public Response banByUserId(Long userId);
	
	public UserPersonalAndHealthDetailsDao getUserDetailsByUserId(Long userId);
	
	public AdminOperationResponseDao updateUserByUserId(AdminUserPersonalDetailsDao dao, String dateTimeFormat);

	public Response getUserDetailsByExcel(Long userId,HttpServletResponse response);

}
