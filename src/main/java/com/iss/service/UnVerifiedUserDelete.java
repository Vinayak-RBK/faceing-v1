package com.iss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iss.repository.LoginUserRespository;

@Service
public class UnVerifiedUserDelete {
	
	@Autowired
	private LoginUserRespository loginUserRespository;
	
	public void deleteUserUnVerified()
	{
		loginUserRespository.deleteUsers();
	}

}
