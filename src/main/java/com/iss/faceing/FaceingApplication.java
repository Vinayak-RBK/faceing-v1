package com.iss.faceing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import com.iss.entity.AdminUser;
import com.iss.repository.AdminAuthRepository;
import com.iss.util.EncryptedDecryptedObjectUtil;

@SpringBootApplication
@ComponentScan("com.iss")
@EntityScan("com.iss.entity")
@EnableJpaRepositories("com.iss.repository")
@EnableScheduling
@EnableCaching
public class FaceingApplication {

	@Value("${SECRET_KEY}")
	private String secretKey;

	@Value("${SECRET_IV}")
	private String secretIv;

	@Value("${IS_ENCRYPT_DECRYPT_ENABLE_DATABASE_DATA}")
	private boolean isEncryptDecryptDatabaseData;

	@Value("${IS_ENCRYPT_DECRYPT_REQUEST_RESPONSE_DATA}")
	private boolean isEncryptDecryptReqRespData;
	
	private static final Logger logger = LoggerFactory.getLogger(FaceingApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(FaceingApplication.class, args);
	}

	@Bean
	@Qualifier("myScheduler1")
	public TaskScheduler taskScheduler1() {

		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setThreadNamePrefix("myScheduler11");
		return scheduler;
	}

	@Bean
	@Qualifier("myScheduler2")
	public TaskScheduler taskScheduler12() {

		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setThreadNamePrefix("myScheduler12");
		return scheduler;
	}

	@Bean
	public CommandLineRunner insertDefaultAdmin(AdminAuthRepository adminRepo) {
		return args -> {

			logger.info("Start insertDefaultAdmin");
			AdminUser admin = new AdminUser();
			AdminUser enAdmin = new AdminUser();

			String emailId = "iss@gmail.com";
			String password = "1212";
			String enEmailId = "";

			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String dateTimeStr = LocalDateTime.now().format(dateTimeFormatter);

			enEmailId = (String) EncryptedDecryptedObjectUtil.getEncryptedString(emailId, secretKey, secretIv,
					isEncryptDecryptDatabaseData);

			AdminUser existingAdmin = adminRepo.findAdminUserByEmailId(enEmailId);

			if (existingAdmin == null) {

				admin.setAdminEmail(emailId);
				admin.setAdminPassword(password);
				admin.setAdminIdRef("ADMIN_001");
				admin.setAdminOTP("000000");
				admin.setIsAdmin("true");
				admin.setIsUserManagement("true");
				admin.setIsQuestionaries("true");
				admin.setIsLegalSetting("true");
				admin.setRole("1");
				admin.setOtpEntryDate(dateTimeStr);
				admin.setTermCond("1");
				admin.setAdminOnSession("false");
				admin.setIsVerified("true");
				admin.setLoginAttemptFailCount("0");
				admin.setLoginAttemptMaxReleaseTime("0");
				admin.setLockedDateTimeForLogin("");
				admin.setMaxAttemptFailBlockForLogin("5");
				admin.setRegistDate(dateTimeStr);
				admin.setRegistPName("adminUserAuth");
				admin.setLastUpdateDate(dateTimeStr);
				admin.setLastUpdatePName("adminUserAuth");

				enAdmin = (AdminUser) EncryptedDecryptedObjectUtil.getEncryptedObject(admin, secretKey, secretIv,
						isEncryptDecryptDatabaseData);

				adminRepo.save(enAdmin);
				logger.info("Admin Record before encryption is - {}",admin);
				logger.info("Admin Record after encryption is - {}",enAdmin);
				logger.info("Created Admin Record");
			} else {
				logger.info("Admin Record already exists");
			}
			
			logger.info("End insertDefaultAdmin");
		};
	}

}
