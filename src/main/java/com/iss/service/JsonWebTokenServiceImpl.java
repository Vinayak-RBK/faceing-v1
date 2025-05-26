package com.iss.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iss.entity.JsonWebToken;
import com.iss.repository.JsonWebTokenServiceRepository;
import com.iss.util.EncryptedDecryptedObjectUtil;

import jakarta.transaction.Transactional;

@Service
public class JsonWebTokenServiceImpl implements JsonWebTokenService {

	@Autowired
	private JsonWebTokenServiceRepository jwtRepo;
	
	@Value("${SECRET_KEY}")
	private String secretKey;

	@Value("${SECRET_IV}")
	private String secretIv;

	@Value("${IS_ENCRYPT_DECRYPT_ENABLE_DATABASE_DATA}")
	private boolean isEncryptDecryptDatabaseData;

	@Transactional
	@Override
	public void createUSerWithToken(String userId, String accessToken, String refreshToken, String dateTimeFormat,
			String accessKeyExiryTime, String refreshKeyExpiryTime) throws Exception {

		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());

		JsonWebToken jwtEnt = new JsonWebToken();
		
//		String enUserId=(String) EncryptedDecryptedObjectUtil.getEncryptedString(userId, secretKey,
//				secretIv, isEncryptDecryptDatabaseData);
		String enAccessToken=(String) EncryptedDecryptedObjectUtil.getEncryptedString(accessToken, secretKey,
				secretIv, isEncryptDecryptDatabaseData);
		String enRefreshToken=(String) EncryptedDecryptedObjectUtil.getEncryptedString(refreshToken, secretKey,
				secretIv, isEncryptDecryptDatabaseData);
		String enCurDate=(String) EncryptedDecryptedObjectUtil.getEncryptedString(currentDate, secretKey,
				secretIv, isEncryptDecryptDatabaseData);
		String enForAccessTokenExpiry=(String) EncryptedDecryptedObjectUtil.getEncryptedString(accessKeyExiryTime, secretKey,
				secretIv, isEncryptDecryptDatabaseData);
		String enForRefreshTokenExpiry=(String) EncryptedDecryptedObjectUtil.getEncryptedString(refreshKeyExpiryTime, secretKey,
				secretIv, isEncryptDecryptDatabaseData);
		
		String enForUpdatePName=(String) EncryptedDecryptedObjectUtil.getEncryptedString(this.getClass().getSimpleName(), secretKey,
				secretIv, isEncryptDecryptDatabaseData);

		jwtEnt.setUserId(userId);
		jwtEnt.setAccessToken(enAccessToken);
		jwtEnt.setRefreshToken(enRefreshToken);
		jwtEnt.setExpiryDaysAccessToken(enForAccessTokenExpiry);
		jwtEnt.setExpiryDaysRefreshToken(enForRefreshTokenExpiry);
		jwtEnt.setRegistDate(enCurDate);
		jwtEnt.setRegistPName(enForUpdatePName);
		jwtEnt.setLastUpdateDate(enCurDate);
		jwtEnt.setLastUpdatePName(enForUpdatePName);

		JsonWebToken jwtSavedEnt = jwtRepo.save(jwtEnt);

		if (jwtSavedEnt == null) {
			System.out.println("Error");
		} else {
			System.out.println("Saved Successfully");
		}

	}

}
