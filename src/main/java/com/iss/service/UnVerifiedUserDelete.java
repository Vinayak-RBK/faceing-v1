package com.iss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iss.repository.LoginUserRespository;
import com.iss.util.EncryptedDecryptedObjectUtil;

@Service
public class UnVerifiedUserDelete {
	
	@Value("${SECRET_KEY}")
	private String secretKey;
	
	@Value("${SECRET_IV}")
	private String secretIv;
	
	@Value("${IS_ENCRYPT_DECRYPT_ENABLE_DATABASE_DATA}")
	private boolean isEncryptDecryptDatabaseData;
	
	@Autowired
	private LoginUserRespository loginUserRespository;
	
	public void deleteUserOnboardingPending() throws Exception
	{
		 String enOnbaordPendingFlag = (String) EncryptedDecryptedObjectUtil.getEncryptedString(Boolean.toString(false),  secretKey, secretIv,
				 isEncryptDecryptDatabaseData);
		loginUserRespository.deleteUsers(enOnbaordPendingFlag);
	}

}
