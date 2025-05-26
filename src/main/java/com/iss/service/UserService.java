package com.iss.service;

import org.springframework.stereotype.Service;

import com.iss.dao.UserDao;

@Service
public interface UserService {
	
	public Integer saveUser(UserDao userDao, String dateTimeFormat);
	
}
