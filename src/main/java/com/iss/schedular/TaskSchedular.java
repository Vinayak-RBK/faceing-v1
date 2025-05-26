package com.iss.schedular;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iss.service.AdminAuthService;
import com.iss.service.LoginUserService;
import com.iss.service.UnVerifiedUserDelete;

@Component
public class TaskSchedular {

	@Value("${DATE_TIME_FORMAT}")
	private String dateTimeFormat;

	@Autowired
	private UnVerifiedUserDelete unVerifiedUserDelete;

	@Autowired
	private LoginUserService loginUserService;

	@Autowired
	private AdminAuthService adminAuthService;

	private static final Logger logger = LoggerFactory.getLogger(TaskSchedular.class);

	// Execute for every set minutes and we can set for hour, seconds, milliseconds
	// as well
	// Deleting all the un verified users
	// now set for 60 minutes=1 hour
	@Scheduled(fixedRate = 24, scheduler = "myScheduler1", timeUnit = TimeUnit.HOURS)
	public void myScheduler11() {
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());
		logger.info("Deleting UnVerified Users : Current Time - {}", currentDate);
		try {
			unVerifiedUserDelete.deleteUserOnboardingPending();
			logger.info("Deleted All UnVerified Users : Current Time - {}", currentDate);
		} catch (Exception e) {
			logger.info("Error occured while deleting UnVerified Users : Current Time - {} - {}", currentDate, e);
		}
	}

	// Reset for End User
	@Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
	public void resetLoginAttemptsAfterTimeOverForEndUser() {
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());
		logger.info("Resetting blocked End Users : Current Time - {}", currentDate);
		try {
			loginUserService.checkAndResetLoginAttemptsForUser(dateTimeFormat);
			logger.info("Resetted All blocked End Users : Current Time - {}", currentDate);
		} catch (Exception e) {
			logger.info("Error occured while Resetting blocked End Users : Current Time - {} - {}", currentDate, e);
		}
	}

	// Reset for Admin User
	@Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
	public void resetLoginAttemptsAfterTimeOverForAdminUser() {
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());
		logger.info("Resetting blocked Admin Users : Current Time - {}", currentDate);
		try {
			adminAuthService.checkAndResetLoginAttemptsForAdmin(dateTimeFormat);
			logger.info("Resetted All blocked Admin Users : Current Time - {}", currentDate);
		} catch (Exception e) {
			logger.info("Error occured while Resetting blocked Admin Users : Current Time - {} - {}", currentDate, e);
		}
	}

	@Scheduled(fixedRate = 5, timeUnit = TimeUnit.MINUTES)
	public void resetBlockedUserSite() {
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());
		logger.info("Resetting blocked Admin Users : Current Time - {}", currentDate);
		try {
			adminAuthService.resetBlockedUserSiteService(dateTimeFormat);
			logger.info("Resetted All blocked Site Users : Current Time - {}", currentDate);
		} catch (Exception e) {
			logger.info("Error occured while deleting Resetting blocked Site Users : Current Time - {} - {}",
					currentDate, e);
		}
	}

}
