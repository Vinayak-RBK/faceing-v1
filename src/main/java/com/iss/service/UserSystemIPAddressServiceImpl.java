package com.iss.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iss.dao.Response;
import com.iss.entity.UserSystemIPAddress;
import com.iss.repository.UserSystemIPAddressRepository;
import com.iss.util.EncryptedDecryptedObjectUtil;

import jakarta.transaction.Transactional;

@Service
public class UserSystemIPAddressServiceImpl implements UserSystemIPAddressService {

	@Autowired
	private UserSystemIPAddressRepository userSystemIPAddressRepository;

	@Value("${SITE_BLOCK_MAX_ATTEMPT}")
	private int maxAttemptForSiteBlock;

	@Value("${SITE_BLOCK_RELEASE_TIME}")
	private int siteBlockMaxReleaseTime;

	@Value("${IP_ADDRESS_BLOCK}")
	private String ipAddressBlock;

	@Value("${IP_ADDRESS_BLOCK_TEMPORORILY}")
	private String ipAddressBlockTemporarily;

	@Value("${IP_ADDRESS_BLOCK_NOT_BLCOK}")
	private String ipAddressNotBlock;

	@Value("${SECRET_KEY}")
	private String secretKey;

	@Value("${SECRET_IV}")
	private String secretIv;

	@Value("${IS_ENCRYPT_DECRYPT_ENABLE_DATABASE_DATA}")
	private boolean isEncryptDecryptDatabaseData;
	
	private static final Logger logger = LoggerFactory.getLogger(UserSystemIPAddressServiceImpl.class);

	@Transactional
	@Override
	public void saveOrUpdateUserSystemIpAdressService(String ipaddress, String dateTimeFormat) throws Exception {

		String enIpAddress = (String) EncryptedDecryptedObjectUtil.getEncryptedString(ipaddress, secretKey, secretIv,
				isEncryptDecryptDatabaseData);
		UserSystemIPAddress userAddress = userSystemIPAddressRepository.findbyIpAdress(enIpAddress);
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());

		if (userAddress == null) {
			UserSystemIPAddress userIPAddressEnt = new UserSystemIPAddress();

			userIPAddressEnt.setAttemptFailCount("1");
			userIPAddressEnt.setBlocked(Boolean.toString(false));
			userIPAddressEnt.setLockedDateTimeForLogin("");
			userIPAddressEnt.setIpAdress(ipaddress);
			userIPAddressEnt.setRegistDate(currentDate);
			userIPAddressEnt.setRegistPName(this.getClass().getSimpleName());
			userIPAddressEnt.setLastUpdateDate(currentDate);
			userIPAddressEnt.setLastUpdatePName(this.getClass().getSimpleName());

			UserSystemIPAddress enUserIpAdressEnt = (UserSystemIPAddress) EncryptedDecryptedObjectUtil
					.getEncryptedObject(userIPAddressEnt, secretKey, secretIv, isEncryptDecryptDatabaseData);

			userSystemIPAddressRepository.save(enUserIpAdressEnt);

		} else {
			String decAttempFailCount = (String) EncryptedDecryptedObjectUtil.getDecryptedString(
					userAddress.getAttemptFailCount(), secretKey, secretIv, isEncryptDecryptDatabaseData);
			userAddress.setIpAdress(enIpAddress);
			userAddress.setBlocked(Boolean.toString(false));
			// Checking max attempt fail and blocking if it reaches max attempts
			if (maxAttemptForSiteBlock == Integer.parseInt(decAttempFailCount) + 1) {
				userAddress.setBlocked(Boolean.toString(true));
				userAddress.setLoginAttemptMaxReleaseTime(Integer.toString(siteBlockMaxReleaseTime));
				userAddress.setLockedDateTimeForLogin(currentDate);

			}

			userAddress.setAttemptFailCount((Integer.toString(Integer.parseInt(decAttempFailCount) + 1)));
			userAddress.setLastUpdateDate(currentDate);
			userAddress.setLastUpdatePName(this.getClass().getSimpleName());

			UserSystemIPAddress enUserAddress = (UserSystemIPAddress) EncryptedDecryptedObjectUtil
					.getEncryptedObject(userAddress, secretKey, secretIv, isEncryptDecryptDatabaseData);

			userSystemIPAddressRepository.save(enUserAddress);

		}

	}

	@Override
	public Response checkReceivedSiteIsBlocked(String ipAddress) {
		Response resp = new Response();
		UserSystemIPAddress userAddress = userSystemIPAddressRepository.findbyIpAdress(ipAddress);

		if (userAddress == null) {
			resp.setMsg(ipAddressBlock);
			resp.setSuccess(Boolean.toString(true));
		} else {
			if (Boolean.parseBoolean(userAddress.isBlocked())) {
				resp.setMsg(ipAddressBlockTemporarily);
				resp.setSuccess(Boolean.toString(false));
			} else {
				resp.setMsg(ipAddressNotBlock);
				resp.setSuccess(Boolean.toString(true));
			}
		}

		logger.info("While checking in checkReceivedSiteIsBlocked() method - {}",resp.getMsg());
		return resp;
	}

}
