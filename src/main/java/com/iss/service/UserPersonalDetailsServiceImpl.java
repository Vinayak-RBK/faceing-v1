package com.iss.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.iss.dao.BasicHealthQuestionDao;
import com.iss.dao.CommonUserDetailsDao;
import com.iss.dao.PersonalDetailsDao;
import com.iss.dao.ResponseDao;
import com.iss.dao.SDKinfoDao;
import com.iss.dao.UserDao;
import com.iss.entity.EndUser;
import com.iss.entity.SDKTable;
import com.iss.entity.UserHealthOnboardingDetail;
import com.iss.entity.UsersPersonalDetails;
import com.iss.repository.LoginUserRespository;
import com.iss.repository.SDKRepository;
import com.iss.repository.UserHealthOnboardingDetailRepository;
import com.iss.repository.UserPersonalDetailsRepository;
import com.iss.repository.UserRepository;
import com.iss.util.EncryptedDecryptedObjectUtil;

import jakarta.transaction.Transactional;

@Service
public class UserPersonalDetailsServiceImpl implements UserPersonalDetailsService {

	@Value("${ACCOUNT_EXISTS}")
	private String accountExists;

	@Value("${ACCOUNT_CREATION_SUCCESS}")
	private String accountCreationSuccess;

	@Value("${ACCOUNT_CREATION_FAILURE}")
	private String accountCreationFailure;

	@Value("${DATE_TIME_FORMAT}")
	private String dateTimeFormat;

	@Value("${DUP_EMAIL_ID}")
	private String DupEmailId;

	@Value("${PROFILE_IMAGE_LOCAL_PATH}")
	private String imagePath;

	@Value("${SECRET_KEY}")
	private String secretKey;

	@Value("${SECRET_IV}")
	private String secretIv;

	@Value("${IS_ENCRYPT_DECRYPT_ENABLE_DATABASE_DATA}")
	private boolean isEncryptDecryptDatabaseData;

	@Autowired
	private UserPersonalDetailsRepository userPersonalDetailsRepository;

	@Autowired
	private LoginUserRespository loginUserRespository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserPersonalDetailsRepository userPerDetailsRepository;

	@Autowired
	private SDKRepository sdkRepository;

	@Autowired
	private UserHealthOnboardingDetailRepository onBoardDetailRepo;

	private ModelMapper modelMapper = new ModelMapper();

	private ResponseDao resDao = new ResponseDao();

	@Transactional
	@SuppressWarnings({ "removal", "deprecation" })
	public ResponseDao insertUserPersonalData(UserDao userDao, PersonalDetailsDao perDetailsDao,
			List<BasicHealthQuestionDao> healthDetailsListDao, String dateTimeFormat) {
		CommonUserDetailsDao comUserPerDao = new CommonUserDetailsDao();
		CommonUserDetailsDao decComUserPerDao = new CommonUserDetailsDao();
		List<SDKinfoDao> sdkList = new ArrayList<SDKinfoDao>();
		SDKinfoDao sdKinfoDao = new SDKinfoDao();
		UsersPersonalDetails det = new UsersPersonalDetails();
		String enCurrentDate = "";
		String enPName = "";
		String enIsOnboarded = "";
		String enEmailId = "";
		String decEmailId="";

		try {
			if (!StringUtils.isEmpty(userDao)) {

				boolean result = false;
				SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
				String currentDate = sdf.format(new Date());

				Optional<EndUser> user = userRepository.findById(new Long(userDao.getUserId()));

				if (user.isEmpty()) {
					resDao.setMessage("Received wrong credentials");
					resDao.setIsSuccess(Boolean.toString(false));
					return resDao;
				}
				
				det = modelMapper.map(perDetailsDao, UsersPersonalDetails.class);
				
				EncryptedDecryptedObjectUtil.getEncryptedObject(det, secretKey, secretIv, isEncryptDecryptDatabaseData);

				enCurrentDate = (String) EncryptedDecryptedObjectUtil.getEncryptedString(currentDate, secretKey,
						secretIv, isEncryptDecryptDatabaseData);
				enPName = (String) EncryptedDecryptedObjectUtil.getEncryptedString(this.getClass().getSimpleName(),
						secretKey, secretIv, isEncryptDecryptDatabaseData);
				
				decEmailId = (String) EncryptedDecryptedObjectUtil.getDecryptedString(user.get().getUserEmail(), secretKey,
						secretIv, isEncryptDecryptDatabaseData);

				det.setUserEmail(decEmailId);
				det.setEndUser(user.get());
				det.setRegistDate(enCurrentDate);
				det.setRegistPName(enPName);
				det.setLastUpdateDate(enCurrentDate);
				det.setLastUpdatePName(enPName);

				// Creating Personal Details of user
				userPersonalDetailsRepository.save(det);

				enIsOnboarded = (String) EncryptedDecryptedObjectUtil.getEncryptedString(Boolean.toString(true),
						secretKey, secretIv, isEncryptDecryptDatabaseData);
				enEmailId = (String) EncryptedDecryptedObjectUtil.getEncryptedString(userDao.getEmailId(), secretKey,
						secretIv, isEncryptDecryptDatabaseData);

				// Updating onboarded flag after completing Personal Info and questionaries
				loginUserRespository.updateUserByOnboardedFlag(enIsOnboarded, enCurrentDate, enPName,
						new Long(userDao.getUserId()), enEmailId);

				// Fetching all the available SDK details from DB.
				List<SDKTable> sdkEntList = sdkRepository.findAll();

				UserHealthOnboardingDetail entOnBorading = new UserHealthOnboardingDetail();
				for (BasicHealthQuestionDao dao : healthDetailsListDao) {
					entOnBorading = new UserHealthOnboardingDetail();
					entOnBorading.setUserHealthOnboardingId(dao.getId());
					entOnBorading.setOnboardingQuestionName(dao.getOnBoardingQuestionName());
					entOnBorading.setOnboardingAnswerValue(dao.getAnswer());
					EncryptedDecryptedObjectUtil.getEncryptedObject(entOnBorading, secretKey, secretIv,
							isEncryptDecryptDatabaseData);
					entOnBorading.setEndUser(user.get());

					// Creating records for onboarding for the user
					onBoardDetailRepo.save(entOnBorading);

				}

				for (SDKTable ent : sdkEntList) {
					// Copying one by one fetched SDK ent to dao
					sdKinfoDao = modelMapper.map(ent, SDKinfoDao.class);
					sdkList.add(sdKinfoDao);
				}

				// fetching User Personal Details
				UsersPersonalDetails perDetails = userPerDetailsRepository.getByUserId(new Long(userDao.getUserId()));
				if (perDetails != null) {
//				// Copying fetched values from DB to dao.
					comUserPerDao = modelMapper.map(perDetails, CommonUserDetailsDao.class);
					String userPerDetailsId=comUserPerDao.getUserPersonalDetailId();
					decComUserPerDao=(CommonUserDetailsDao) EncryptedDecryptedObjectUtil.getDecryptedObject(comUserPerDao, secretKey, secretIv,
							isEncryptDecryptDatabaseData);
					decComUserPerDao.setUserPersonalDetailId(userPerDetailsId);
					
				}

				// Setting fetched user Personal Data and SDK details.
				resDao.setCommonUserDetailsDao(decComUserPerDao);
				resDao.setSdkInfo(sdkList);
				resDao.setEmailId(userDao.getEmailId());
				resDao.setUserId(userDao.getUserId());
//				resDao.setEmailId(userDao.getEmailId());
				resDao.setPushNotify(user.get().getPushNotify());

				result = true;

				if (result) {
					resDao.setMessage(accountCreationSuccess);
					resDao.setIsSuccess(Boolean.toString(true));
				} else {
					resDao.setMessage(accountCreationFailure);
					resDao.setIsSuccess(Boolean.toString(false));
				}
			} else {
				resDao.setMessage(DupEmailId);
				resDao.setIsSuccess(Boolean.toString(false));
			}
			resDao.setUserId(userDao.getUserId().toString());
		} catch (Exception e) {
			resDao.setMessage(DupEmailId);
			resDao.setIsSuccess(Boolean.toString(false));
			System.out.println(e);
		}

		return resDao;
	}

}
