package com.iss.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iss.dao.AdminOperationResponseDao;
import com.iss.dao.AdminUserPersonalDetailsDao;
import com.iss.dao.Response;
import com.iss.dao.UserPersonalAndHealthDetailsDao;
import com.iss.dao.UserSearchResponseDao;

import jakarta.servlet.http.HttpServletResponse;


@Service
public interface UserSearchService {

	public List<UserSearchResponseDao> getAllUserDetails(String dateTimeFormat);

	public Response banByUserId(Long userId);
	
	public UserPersonalAndHealthDetailsDao getUserDetailsByUserId(Long userId);
	
	public AdminOperationResponseDao updateUserByUserId(AdminUserPersonalDetailsDao dao, String dateTimeFormat);

	public Response getUserDetailsByExcel(Long userId,HttpServletResponse response);

}
