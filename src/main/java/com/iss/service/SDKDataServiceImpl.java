package com.iss.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iss.dao.ResponseDao;
import com.iss.dao.UserHealthInfoDao;
import com.iss.entity.CommonUserDetailsTable;
import com.iss.entity.UserHealthAnuraDetail;
import com.iss.entity.UserHealthBinahDetail;
import com.iss.entity.UsersPersonalDetails;
import com.iss.properties.FieldNames;
import com.iss.repository.LoginUserRespository;
import com.iss.repository.UserHealthAnuraDetailsRepo;
import com.iss.repository.UserHealthBinahDetailsRepo;
import com.iss.repository.UserPersonalDetailsRepository;
import com.iss.util.EncryptedDecryptedObjectUtil;

import jakarta.transaction.Transactional;

@Service
public class SDKDataServiceImpl implements SDKDataService {

	@Value("${SECRET_KEY}")
	private String secretKey;

	@Value("${SECRET_IV}")
	private String secretIv;

	@Value("${IS_ENCRYPT_DECRYPT_ENABLE_DATABASE_DATA}")
	private boolean isEncryptDecryptDatabaseData;

	@Autowired
	private UserHealthAnuraDetailsRepo userHealthAnuraDetRepo;

	@Autowired
	private UserHealthBinahDetailsRepo userHealthBinahDetRepo;

	@Autowired
	private UserPersonalDetailsRepository userPerDetrailRespo;

	@Autowired
	private LoginUserRespository loginUserRespository;

	private static final Logger logger = LoggerFactory.getLogger(SDKDataServiceImpl.class);

	@Transactional
	@Override
	public ResponseDao saveUserSDKData(UserHealthInfoDao dao, String dateTimeFormat) {
		ResponseDao resp = new ResponseDao();
		ModelMapper mapper = new ModelMapper();

		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());

		UserHealthAnuraDetail userHealthAnuraDetail = new UserHealthAnuraDetail();
		UserHealthAnuraDetail enUserHealthAnuraDetail = new UserHealthAnuraDetail();

		UserHealthBinahDetail userHealthBinahDetail = new UserHealthBinahDetail();
		UserHealthBinahDetail enUserHealthBinahDetail = new UserHealthBinahDetail();

		String decSdkType = "";

		@SuppressWarnings("removal")
		CommonUserDetailsTable userPerDetails = userPerDetrailRespo
				.findUserPersonalDetailsByUserId(new Long(dao.getUserId()));

		if (userPerDetails == null) {
			resp.setMessage("Wrong User ID is recieved");
			resp.setIsSuccess(Boolean.toString(false));

			logger.info("While saving user sdk health data in saveUserSDKData() method - {}", resp.getMessage());
			return resp;
		}

		Optional<UsersPersonalDetails> userPerData = userPerDetrailRespo
				.findById(userPerDetails.getUserPersonalDetailId().toString());

		if (userPerData.isEmpty()) {
			resp.setMessage("User Personal Details doesnot exists.");
			resp.setIsSuccess(Boolean.toString(false));

			logger.info("While saving user sdk health data in saveUserSDKData() method - {}", resp.getMessage());
			return resp;
		}

		try {
			decSdkType = (String) EncryptedDecryptedObjectUtil.getDecryptedString(userPerDetails.getsDKType(),
					secretKey, secretIv, isEncryptDecryptDatabaseData);
		} catch (Exception e1) {
			resp.setMessage("Error Occurred During Encryption");
			resp.setIsSuccess(Boolean.toString(false));

			logger.error("Exception occured While saving user sdk health data in saveUserSDKData() method - {}", e1);
			return resp;
		}

		if (FieldNames.SDK_ANURA.equals(decSdkType)) {
			userHealthAnuraDetail = mapper.map(dao.getAnuraDetails(), UserHealthAnuraDetail.class);

			userHealthAnuraDetail.setRegistDate(currentDate);
			userHealthAnuraDetail.setRegistPName(this.getClass().getSimpleName());
			userHealthAnuraDetail.setLastUpdateDate(currentDate);
			userHealthAnuraDetail.setLastUpdatePName(this.getClass().getSimpleName());

			try {
				enUserHealthAnuraDetail = (UserHealthAnuraDetail) EncryptedDecryptedObjectUtil
						.getEncryptedObject(userHealthAnuraDetail, secretKey, secretIv, isEncryptDecryptDatabaseData);
				userHealthAnuraDetail.setEndUser(loginUserRespository.findById(dao.getUserId()).get());
				userHealthAnuraDetail.setUserEmail(userPerDetails.getUserEmail());
			} catch (Exception e) {
				resp.setMessage("Error Occurred During Encryption");
				resp.setIsSuccess(Boolean.toString(false));

				logger.info("While saving user sdk health data in saveUserSDKData() method - {}", resp.getMessage());
				return resp;
			}

			UserHealthAnuraDetail userHealthAnuraData = userHealthAnuraDetRepo.save(enUserHealthAnuraDetail);

			if (userHealthAnuraData != null) {
				resp.setMessage("User Health Data is saved Successfullyy...");
				resp.setIsSuccess(Boolean.toString(true));
			} else {
				resp.setMessage("Unable to create User Health Data.");
				resp.setIsSuccess(Boolean.toString(false));
			}
		} else if (FieldNames.SDK_BINAH.equals(decSdkType)) {
			userHealthBinahDetail = mapper.map(dao.getBinahDetails(), UserHealthBinahDetail.class);

			userHealthBinahDetail.setRegistDate(currentDate);
			userHealthBinahDetail.setRegistPName(this.getClass().getSimpleName());
			userHealthBinahDetail.setLastUpdateDate(currentDate);
			userHealthBinahDetail.setLastUpdatePName(this.getClass().getSimpleName());

			try {
				enUserHealthBinahDetail = (UserHealthBinahDetail) EncryptedDecryptedObjectUtil
						.getEncryptedObject(userHealthBinahDetail, secretKey, secretIv, isEncryptDecryptDatabaseData);
				userHealthBinahDetail.setEndUser(loginUserRespository.findById(dao.getUserId()).get());
				userHealthBinahDetail.setUserEmail(userPerDetails.getUserEmail());
			} catch (Exception e) {
				resp.setMessage("Error Occurred During Encryption");
				resp.setIsSuccess(Boolean.toString(false));

				logger.error(
						"Exception occured during encryption While saving user sdk health data in saveUserSDKData() method - {}",
						e);
				return resp;
			}

			UserHealthBinahDetail userHealthBinahData = userHealthBinahDetRepo.save(enUserHealthBinahDetail);

			if (userHealthBinahData != null) {
				resp.setMessage("User Health Data is saved Successfullyy...");
				resp.setIsSuccess(Boolean.toString(true));
			} else {
				resp.setMessage("Unable to create User Health Data.");
				resp.setIsSuccess(Boolean.toString(false));
			}
		}

		resp.setEmailId(userPerDetails.getUserEmail());

		logger.info("While saving user sdk health data in saveUserSDKData() method - {}", resp.getMessage());
		return resp;
	}

}
