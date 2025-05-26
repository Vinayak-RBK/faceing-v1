package com.iss.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iss.entity.JsonWebToken;
import com.iss.repository.JsonWebTokenServiceRepository;

import jakarta.transaction.Transactional;

@Service
public class JsonWebTokenServiceImpl implements JsonWebTokenService {

	@Autowired
	private JsonWebTokenServiceRepository jwtRepo;

	@Transactional
	@Override
	public void createUSerWithToken(String userId, String accessToken, String refreshToken, String dateTimeFormat,
			Long accessKeyExiryTime, Long refreshKeyExpiryTime) {

		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());

		JsonWebToken jwtEnt = new JsonWebToken();

		jwtEnt.setUserId(userId);
		jwtEnt.setAccessToken(accessToken);
		jwtEnt.setRefreshToken(refreshToken);
		jwtEnt.setExpiryDaysAccessToken(accessKeyExiryTime);
		jwtEnt.setExpiryDaysRefreshToken(refreshKeyExpiryTime);
		jwtEnt.setRegistDate(currentDate);
		jwtEnt.setRegistPName(this.getClass().getSimpleName());
		jwtEnt.setLastUpdateDate(currentDate);
		jwtEnt.setLastUpdatePName(this.getClass().getSimpleName());

		JsonWebToken jwtSavedEnt = jwtRepo.save(jwtEnt);

		if (jwtSavedEnt == null) {
			System.out.println("Error");
		} else {
			System.out.println("Saved Successfully");
		}

	}

}
