package com.iss.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iss.dao.UserHealthAnuraDetailsDao;
import com.iss.dao.UserHealthBinahDetailsDao;
import com.iss.dao.UserHealthDetailsResponseDao;
import com.iss.dao.UserHealthRequestDao;
import com.iss.dao.UserScannedHealthDataDao;
import com.iss.entity.EndUser;
import com.iss.entity.UserHealthAnuraDetail;
import com.iss.entity.UserHealthBinahDetail;
import com.iss.properties.FieldNames;
import com.iss.repository.LoginUserRepositoryImp;
import com.iss.repository.LoginUserRespository;
import com.iss.repository.UserHealthAnuraDetailsRepo;
import com.iss.repository.UserHealthBinahDetailsRepo;
import com.iss.util.DateFormatUtil;
import com.iss.util.EncryptedDecryptedObjectUtil;

@Service
public class UserFaceScanHistoryServiceImpl implements UserFaceScanHistoryService {

	@Value("${SECRET_KEY}")
	private String secretKey;

	@Value("${SECRET_IV}")
	private String secretIv;

	@Value("${IS_ENCRYPT_DECRYPT_ENABLE_DATABASE_DATA}")
	private boolean isEncryptDecryptDatabaseData;

	@Autowired
	private UserHealthAnuraDetailsRepo userHealthAnuraDetailsRepo;

	@Autowired
	private UserHealthBinahDetailsRepo userHealthBinahDetailsRepo;

	@Autowired
	private LoginUserRespository loginUserRespository;

	@Autowired
	private LoginUserRepositoryImp loginRepoImpl;

	private static final Logger logger = LoggerFactory.getLogger(UserFaceScanHistoryServiceImpl.class);

	@Override
	public UserHealthDetailsResponseDao getUserAllFaceScanHistoryService(String userId) {
		UserHealthDetailsResponseDao resDao = new UserHealthDetailsResponseDao();

		UserScannedHealthDataDao userScanHealthData = new UserScannedHealthDataDao();

		List<UserHealthAnuraDetail> userHealthAnuraDetailsList = new ArrayList<UserHealthAnuraDetail>();
		List<UserScannedHealthDataDao> userScanHealthList = new ArrayList<UserScannedHealthDataDao>();
		List<UserHealthBinahDetail> userHealthBinahDetailsList = new ArrayList<UserHealthBinahDetail>();

		String enSdkType = "";

		Optional<EndUser> user = loginUserRespository.findById(userId);

		if (user.isEmpty()) {
			resDao.setMsg("Wrong User Id is Received");
			resDao.setSuccess(Boolean.toString(false));

			logger.info("While getting User face scan Health History in getUserAllFaceScanHistoryService() method - {}",
					resDao.getMsg());
			return resDao;
		}

		try {
			enSdkType = (String) EncryptedDecryptedObjectUtil.getDecryptedString(user.get().getsDKType(), secretKey,
					secretIv, isEncryptDecryptDatabaseData);
		} catch (Exception e) {
			resDao.setMsg("Error Occured During encryption " + e);
			resDao.setSuccess(Boolean.toString(false));

			logger.error(
					"Exception occured While getting User face scan Health History in getUserAllFaceScanHistoryService() method - {}",
					e);
			return resDao;
		}

		if (FieldNames.SDK_ANURA.equals(enSdkType)) {
			userHealthAnuraDetailsList = userHealthAnuraDetailsRepo.findByUserId(userId);

			if (userHealthAnuraDetailsList.isEmpty()) {
				resDao.setMsg("No User Health History found");
				resDao.setSuccess(Boolean.toString(false));

				logger.info(
						"While getting User face scan Health History in getUserAllFaceScanHistoryService() method - {}",
						resDao.getMsg());
				return resDao;
			} else {
				for (UserHealthAnuraDetail ent : userHealthAnuraDetailsList) {
					userScanHealthData = new UserScannedHealthDataDao();

					userScanHealthData.setScanId(ent.getUserHealthAnuraDetailId().toString());
					userScanHealthData.setDateOfScan(DateFormatUtil.getFormatedDateToString(ent.getRegistDate()));

					userScanHealthList.add(userScanHealthData);

				}

			}
		}

		else if (FieldNames.SDK_BINAH.equals(enSdkType)) {
			userHealthBinahDetailsList = userHealthBinahDetailsRepo.findByUserId(userId);

			if (userHealthBinahDetailsList.isEmpty()) {
				resDao.setMsg("No User Health History found");
				resDao.setSuccess(Boolean.toString(false));
				return resDao;
			} else {
				for (UserHealthBinahDetail ent : userHealthBinahDetailsList) {
					userScanHealthData = new UserScannedHealthDataDao();

					userScanHealthData.setScanId(ent.getUserHealthBinahDetailId().toString());
					userScanHealthData.setDateOfScan(DateFormatUtil.getFormatedDateToString(ent.getRegistDate()));

					userScanHealthList.add(userScanHealthData);

				}

			}
		}

		resDao.setMsg("Successfully sent Data");
		resDao.setSuccess(Boolean.toString(true));
		resDao.setUserScannedList(userScanHealthList);

		logger.info("While getting User face scan Health History in getUserAllFaceScanHistoryService() method - {}",
				resDao.getMsg());
		return resDao;
	}

	@Override
	public UserHealthDetailsResponseDao getUserFaceScanHistoryByScanId(String userId, String healthScanId,
			boolean isGetByUserIdOnly) {
		ModelMapper mapper = new ModelMapper();
		UserHealthDetailsResponseDao resDao = new UserHealthDetailsResponseDao();

		UserHealthAnuraDetailsDao uHealthAnuraDao = new UserHealthAnuraDetailsDao();
		UserHealthAnuraDetailsDao decUHealthAnuraDao = new UserHealthAnuraDetailsDao();
		List<UserHealthAnuraDetail> userHealthAnuraDetailsList = new ArrayList<UserHealthAnuraDetail>();
		List<UserHealthAnuraDetailsDao> userHealthAnuraDetailsDaosList = new ArrayList<UserHealthAnuraDetailsDao>();

		UserHealthBinahDetailsDao uHealthBinahDao = new UserHealthBinahDetailsDao();
		UserHealthBinahDetailsDao decUHealthBinahDao = new UserHealthBinahDetailsDao();
		List<UserHealthBinahDetail> userHealthBinahDetailsList = new ArrayList<UserHealthBinahDetail>();
		List<UserHealthBinahDetailsDao> userHealthBinahDetailsDaosList = new ArrayList<UserHealthBinahDetailsDao>();

		String enSdkType = "";

		Optional<EndUser> user = loginUserRespository.findById(userId);

		if (user.isEmpty()) {
			resDao.setMsg("User Doesnot Exists");
			resDao.setSuccess(Boolean.toString(false));
			return resDao;
		} else {
			if (!isGetByUserIdOnly) {
				if (healthScanId.isEmpty() || healthScanId == null) {
					resDao.setMsg("Invalid User Health Id is received");
					resDao.setSuccess(Boolean.toString(false));

					logger.info(
							"While getting User face scan Health History in getUserFaceScanHistoryByScanId() method - {}",
							resDao.getMsg());
					return resDao;
				}
			} else {
				// do nothing
			}
		}

		try {
			enSdkType = (String) EncryptedDecryptedObjectUtil.getDecryptedString(user.get().getsDKType(), secretKey,
					secretIv, isEncryptDecryptDatabaseData);
		} catch (Exception e) {
			resDao.setMsg("Error Occured During encryption " + e);
			resDao.setSuccess(Boolean.toString(false));

			logger.error(
					"Exception occured While getting User face scan Health History in getUserFaceScanHistoryByScanId() method - {}",
					e);
			return resDao;
		}

		if (FieldNames.SDK_ANURA.equals(enSdkType)) {
			if (isGetByUserIdOnly) {
				userHealthAnuraDetailsList = userHealthAnuraDetailsRepo.findByUserId(userId);
			} else {
				userHealthAnuraDetailsList = userHealthAnuraDetailsRepo.findByScanIdAndUserId(healthScanId, userId);
			}

			if (userHealthAnuraDetailsList.isEmpty()) {
				resDao.setMsg("No User Health History found");
				resDao.setSuccess(Boolean.toString(false));

				logger.info(
						"While getting User face scan Health History in getUserFaceScanHistoryByScanId() method - {}",
						resDao.getMsg());
				return resDao;
			} else {
				for (UserHealthAnuraDetail ent : userHealthAnuraDetailsList) {
					uHealthAnuraDao = new UserHealthAnuraDetailsDao();

					uHealthAnuraDao = mapper.map(ent, UserHealthAnuraDetailsDao.class);

					try {
						decUHealthAnuraDao = (UserHealthAnuraDetailsDao) EncryptedDecryptedObjectUtil
								.getDecryptedObject(uHealthAnuraDao, secretKey, secretIv, isEncryptDecryptDatabaseData);
					} catch (Exception e) {
						logger.error(
								"Exception occured While getting User face scan Health History in getUserFaceScanHistoryByScanId() method - {}",
								e);
					}

					userHealthAnuraDetailsDaosList.add(decUHealthAnuraDao);
					if (isGetByUserIdOnly) {
						break;
					}

				}

			}
		} else if (FieldNames.SDK_BINAH.equals(enSdkType)) {

			if (isGetByUserIdOnly) {
				userHealthBinahDetailsList = userHealthBinahDetailsRepo.findByUserId(userId);
			} else {
				userHealthBinahDetailsList = userHealthBinahDetailsRepo.findByScanIdAndUserId(healthScanId, userId);
			}

			if (userHealthBinahDetailsList.isEmpty()) {
				resDao.setMsg("No User Health History found");
				resDao.setSuccess(Boolean.toString(false));

				logger.info(
						"While getting User face scan Health History in getUserFaceScanHistoryByScanId() method - {}",
						resDao.getMsg());
				return resDao;
			} else {
				for (UserHealthBinahDetail ent : userHealthBinahDetailsList) {
					uHealthBinahDao = new UserHealthBinahDetailsDao();

					uHealthBinahDao = mapper.map(ent, UserHealthBinahDetailsDao.class);

					try {
						decUHealthBinahDao = (UserHealthBinahDetailsDao) EncryptedDecryptedObjectUtil
								.getDecryptedObject(uHealthBinahDao, secretKey, secretIv, isEncryptDecryptDatabaseData);
					} catch (Exception e) {
						logger.error(
								" Exception occured during encryption While getting User face scan Health History in getUserFaceScanHistoryByScanId() method - {}",
								resDao.getMsg());
					}

					userHealthBinahDetailsDaosList.add(decUHealthBinahDao);
					if (isGetByUserIdOnly) {
						break;
					}

				}

			}
		}

		resDao.setMsg("Successfully sent Data");
		resDao.setSuccess(Boolean.toString(true));
		resDao.setUseHealthAnuraList(userHealthAnuraDetailsDaosList);
		resDao.setUseHealthBinahList(userHealthBinahDetailsDaosList);

		logger.info("While getting User face scan Health History in getUserFaceScanHistoryByScanId() method - {}",
				resDao.getMsg());
		return resDao;
	}

	@Override
	public UserHealthDetailsResponseDao getUserHealthHistoryList(UserHealthRequestDao reqDao) {
		UserHealthDetailsResponseDao respDao = new UserHealthDetailsResponseDao();
		respDao = loginRepoImpl.getUserHealthHistoryList(reqDao);

		if (!respDao.getValues().isEmpty()) {
			respDao.setMsg("Sending Data Successfully...");
			respDao.setSuccess(Boolean.toString(true));
		} else {
			respDao.setMsg("Records not found.");
			respDao.setSuccess(Boolean.toString(false));
		}

		logger.info("While getting User face scan Health History List in getUserHealthHistoryList() method - {}",
				respDao.getMsg());
		return respDao;
	}

}
