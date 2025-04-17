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
import com.iss.entity.CommonUserDetailsTable;
import com.iss.entity.EndUser;
import com.iss.entity.SDKTable;
import com.iss.entity.UserHealthOnboardingDetail;
import com.iss.entity.UsersPersonalDetails;
import com.iss.repository.LoginUserRespository;
import com.iss.repository.SDKRepository;
import com.iss.repository.UserHealthOnboardingDetailRepository;
import com.iss.repository.UserPersonalDetailsRepository;
import com.iss.repository.UserRepository;

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
		List<SDKinfoDao> sdkList = new ArrayList<SDKinfoDao>();
		CommonUserDetailsTable userCommonDetails = new CommonUserDetailsTable();
		SDKinfoDao sdKinfoDao = new SDKinfoDao();

		try {
			if (!StringUtils.isEmpty(userDao)) {

				boolean result = false;
				SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
				String currentDate = sdf.format(new Date());

				Optional<EndUser> user = userRepository.findById(new Long(userDao.getUserId()));

				UsersPersonalDetails det = new UsersPersonalDetails();

				det = modelMapper.map(perDetailsDao, UsersPersonalDetails.class);
				
				if(user.isEmpty())
				{
					resDao.setMessage("Received wrong credentials");
					resDao.setSuccess(false);
					return resDao;
				}
				
				det.setEndUser(user.get());
				det.setRegistDate(currentDate);
				det.setRegistPName(this.getClass().getSimpleName());
				det.setLastUpdateDate(currentDate);
				det.setLastUpdatePName(this.getClass().getSimpleName());
				
//				// creating folder and storing in the given image path
//				fileObjDao=CreateProfileImagesUtil.storeFile(fileName, imagePath);
//
//				// storing image in bucket and getting link and storing that in db image column
//				String imagePath=DigitalOceanStorageUtil.uploadImage(fileObjDao.getProfileImagePath(), "uploads/userprofile/"+userDao.getUserId().toString()+".jpg");
//				det.setUserImage(imagePath);
				
				// Creating Personal Details of user
				userPersonalDetailsRepository.save(det);

				// Updating onboarded flag after completing Personal Info and questionaries
				loginUserRespository.updateUserByOnboardedFlag(true, currentDate, this.getClass().getSimpleName(),
						new Long(userDao.getUserId()), userDao.getEmailId());

//				loginUserRespository.updateUserLoginFlag(true,currentDate,this.getClass().getSimpleName(),user.getUserId().toString(), user.getUserEmail());
				// fetching User Personal Details
				userCommonDetails = userPerDetailsRepository
						.findUserPersonalDetailsByUserId(new Long(userDao.getUserId()));

				// Copying fetched values from DB to dao.
				comUserPerDao = modelMapper.map(userCommonDetails, CommonUserDetailsDao.class);

				// Fetching all the available SDK details from DB.
				List<SDKTable> sdkEntList = sdkRepository.findAll();

				UserHealthOnboardingDetail entOnBorading = new UserHealthOnboardingDetail();
				for (BasicHealthQuestionDao dao : healthDetailsListDao) {
					entOnBorading = new UserHealthOnboardingDetail();
					entOnBorading.setUserHealthOnboardingId(new Integer(dao.getId()));
					entOnBorading.setOnboardingQuestionName(dao.getOnBoardingQuestionName());
					entOnBorading.setOnboardingAnswerValue(dao.getAnswer());
					entOnBorading.setEndUser(user.get());

					// Creating records for onboarding for the user
					onBoardDetailRepo.save(entOnBorading);

				}

				for (SDKTable ent : sdkEntList) {
					// Copying one by one fetched SDK ent to dao
					sdKinfoDao = modelMapper.map(ent, SDKinfoDao.class);
					sdkList.add(sdKinfoDao);
				}

				// Setting fetched user Personal Data and SDK details.
				resDao.setCommonUserDetailsDao(comUserPerDao);
				resDao.setSdkInfo(sdkList);
				resDao.setEmailId(userDao.getEmailId());
				resDao.setUserId(userDao.getUserId());
				resDao.setEmailId(user.get().getUserEmail());

				result = true;

				if (result) {
					resDao.setMessage(accountCreationSuccess);
					resDao.setSuccess(true);
				} else {
					resDao.setMessage(accountCreationFailure);
					resDao.setSuccess(false);
				}
			} else {
				resDao.setMessage(DupEmailId);
				resDao.setSuccess(false);
			}
			resDao.setUserId(userDao.getUserId().toString());
		} catch (Exception e) {
			resDao.setMessage(DupEmailId);
			resDao.setSuccess(false);
			System.out.println(e);
		}

		return resDao;
	}

}
