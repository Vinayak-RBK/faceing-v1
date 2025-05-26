package com.iss.service;

import org.springframework.stereotype.Service;

import com.iss.dao.PushNotifiResponseDao;

@Service
public interface PushNotificationService {

	PushNotifiResponseDao enablePushNotification(Long userId, String dateTimeFormat);

}
