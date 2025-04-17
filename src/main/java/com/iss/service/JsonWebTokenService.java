package com.iss.service;

import org.springframework.stereotype.Service;


@Service
public interface JsonWebTokenService {

	public void createUSerWithToken(String userId, String accessToken, String refreshToken, String dateTimeFormat,
			Long accessKeyExiryTime, Long refreshKeyExpiryTime);

}
