package com.iss.service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iss.dao.AdminOperationResponseDao;
import com.iss.dao.AdminUserPersonalDetailsDao;
import com.iss.dao.PagerRequestDao;
import com.iss.dao.PagerResponseDao;
import com.iss.dao.PersonalDetailsForAdminPanelDao;
import com.iss.dao.Response;
import com.iss.dao.UserDao;
import com.iss.dao.UserHealthAnuraDetailsDao;
import com.iss.dao.UserHealthBinahDetailsDao;
import com.iss.dao.UserPersonalAndHealthDetailsDao;
import com.iss.dao.UserSearchResponseDao;
import com.iss.entity.CommonUserDetailsTable;
import com.iss.entity.EndUser;
import com.iss.entity.UserHealthAnuraDetail;
import com.iss.entity.UserHealthBinahDetail;
import com.iss.entity.UserHealthOnboardingDetail;
import com.iss.entity.UsersPersonalDetails;
import com.iss.repository.LoginUserRepositoryImp;
import com.iss.repository.UserHealthAnuraDetailsRepo;
import com.iss.repository.UserHealthBinahDetailsRepo;
import com.iss.repository.UserPersonalDetailsRepository;
import com.iss.repository.UserRepository;
import com.iss.util.EncryptedDecryptedObjectUtil;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

@Service
public class UserSearchServiceImpl implements UserSearchService {

	@Value("${PERSONAL_DETAILS_NOEXISTS}")
	private String personalDetailsNoexists;

	@Value("${UPDATED_SUCCESS}")
	private String updatedSuccess;

	@Value("${USER_WENT_WRONG}")
	private String Unableupdateuser;

	@Value("${USER_DETAILS}")
	private String userDetails;

	@Value("${BLOCK_USERID}")
	private String blockUserId;

	@Value("${UNBLOCK_USERID}")
	private String UNblockUserId;

	@Value("${DATE_TIME_FORMAT_EXPORTED_FILES}")
	private String dateFormatExportedFiles;

	@Value("${FILES_PATH_EXPORTED}")
	private String filePathExported;

	@Value("${EXCEL_CONTENT_TYPE}")
	private String excepContentType;

	@Value("${SOMETHING_WENT_WRONG}")
	private String someThingWentWrong;

	@Value("${WRONG_USERID_RECEIVED}")
	private String wrongUserId;

	@Value("${SECRET_KEY}")
	private String secretKey;

	@Value("${SECRET_IV}")
	private String secretIv;

	@Value("${IS_ENCRYPT_DECRYPT_ENABLE_DATABASE_DATA}")
	private boolean isEncryptDecryptDatabaseData;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserHealthAnuraDetailsRepo userHealthAnuraRepo;

	@Autowired
	private UserHealthBinahDetailsRepo userHealthBinahRepo;

	@Autowired
	private UserPersonalDetailsRepository userPersDetailsRepo;

	@Autowired
	private LoginUserRepositoryImp loginUserRepositoryImp;

	private List<UserHealthAnuraDetail> userHealthAnuraDetailsList = new ArrayList<UserHealthAnuraDetail>();

	private List<UserHealthBinahDetail> userHealthBinahDetailsList = new ArrayList<UserHealthBinahDetail>();

	private UserPersonalAndHealthDetailsDao listDao = new UserPersonalAndHealthDetailsDao();

	private UsersPersonalDetails userPerDDetails = new UsersPersonalDetails();

	private Response response = new Response();

	@SuppressWarnings("removal")
	@Override
	public PagerResponseDao getAllUserDetails(String dateTimeFormat, PagerRequestDao pagerDao) {
		PagerResponseDao respDao = new PagerResponseDao();
		CommonUserDetailsTable enEnt = new CommonUserDetailsTable();
		UserSearchResponseDao dto = new UserSearchResponseDao();
		List<UserSearchResponseDao> dtoList = new ArrayList<>();
		List<CommonUserDetailsTable> entList = new ArrayList<CommonUserDetailsTable>();
		int totalUsersCount = 0;

		// offset Value
		int pageNo = new Integer(pagerDao.getPageNo());

		// size
		int size = new Integer(pagerDao.getPageSize());

		totalUsersCount = (int) loginUserRepositoryImp.getCountOfUsers("true", pagerDao);

		if (totalUsersCount == 0) {
			respDao.setSuccess(Boolean.toString(false));
			respDao.setMsg("User records not found...");
			return respDao;
		}

		entList = loginUserRepositoryImp.getPersonalDetailsOfUsers("true", pagerDao, size, (pageNo - 1) * size);

		for (CommonUserDetailsTable ent : entList) {
			enEnt = new CommonUserDetailsTable();
			dto = new UserSearchResponseDao();
			dto.setId(ent.getUserId());

			try {
				enEnt = (CommonUserDetailsTable) EncryptedDecryptedObjectUtil.getDecryptedObject(ent, secretKey,
						secretIv, isEncryptDecryptDatabaseData);
			} catch (Exception e) {
				return null;
			}

			dto.setName(enEnt.getUserName());
			dto.setPassword(enEnt.getPassword());
			dto.setGender(enEnt.getUserGender());
			dto.setWeight(enEnt.getUserWeight());
			dto.setEmail(enEnt.getUserEmail());
			dto.setDob(enEnt.getUserDOB());
			dto.setHeight(enEnt.getUserHeight());
//			dto.setImage(enEnt.getUserImage());
			dto.setStatus(enEnt.getIsBlocked());
			dto.setAge(Float.toString(getAgeDifferencesByDates(enEnt.getUserDOB())));
			dto.setRole(enEnt.getJobRole());

			dtoList.add(dto);
		}

		respDao.setSuccess("true");
		if (dtoList.isEmpty()) {
			respDao.setMsg("User records not found...");
			respDao.setSuccess("false");
		} else {
			respDao.setPerDetailsList(dtoList);
			respDao.setTotalCount(Integer.toString(totalUsersCount));
			double b = (double) totalUsersCount / (double) size;
			respDao.setNoOfPages((new Integer((int) Math.ceil(b))).toString());
			respDao.setCurPageNo(pagerDao.getPageNo());
			respDao.setMsg("Successfully sent...");
			respDao.setSuccess("true");
		}

		return respDao;
	}

	private float getAgeDifferencesByDates(String dob) {
		@SuppressWarnings("deprecation")
		Date date = new Date(dob);
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		int month = localDate.getMonthValue();
		int dat = localDate.getDayOfMonth();
		int year = localDate.getYear();

		LocalDate inputDate = LocalDate.of(year, month, dat);
		LocalDate currentDate = LocalDate.now();
		float dobYears = Period.between(inputDate, currentDate).getYears();
		float dobMonths = Period.between(inputDate, currentDate).getMonths();

		float diffAge = (dobYears + (dobMonths / 12));
		return Math.round(diffAge * 100.0) / 100.0f;
	}

	@Override
	public Response banByUserId(Long userId) {
		Response resDao = new Response();
		Optional<EndUser> user = userRepository.findById(userId);

		if (user.isEmpty()) {
			resDao.setSuccess(Boolean.toString(false));
			resDao.setMsg(wrongUserId);
			return resDao;
		}
		String decIsBlocked = "";
		String enIsBlocked = "";
		Boolean isBlocked = false;

		try {
			decIsBlocked = (String) EncryptedDecryptedObjectUtil.getDecryptedString(user.get().getIsBlocked(),
					secretKey, secretIv, isEncryptDecryptDatabaseData);

			isBlocked = Boolean.parseBoolean(decIsBlocked);

			if (isBlocked) {
				enIsBlocked = (String) EncryptedDecryptedObjectUtil.getEncryptedString(Boolean.toString(false),
						secretKey, secretIv, isEncryptDecryptDatabaseData);
				user.get().setIsBlocked(enIsBlocked);
			} else {
				enIsBlocked = (String) EncryptedDecryptedObjectUtil.getEncryptedString(Boolean.toString(true),
						secretKey, secretIv, isEncryptDecryptDatabaseData);
				user.get().setIsBlocked(enIsBlocked);
			}
			userRepository.save(user.get());

		} catch (Exception e) {
			resDao.setSuccess(Boolean.toString(false));
			resDao.setMsg(someThingWentWrong + e);
			return resDao;
		}

		String status = isBlocked ? "Active" : "Inactive";

		resDao.setBlockStatus(status);

		if (isBlocked) {
			resDao.setSuccess(Boolean.toString(true));
			resDao.setMsg(UNblockUserId);
			return resDao;
		} else {
			resDao.setSuccess(Boolean.toString(true));
			resDao.setMsg(blockUserId);
			return resDao;
		}

	}

	@SuppressWarnings("deprecation")
	@Transactional
	@Override
	public UserPersonalAndHealthDetailsDao getUserDetailsByUserId(Long userId) {

		UserDao userDao = new UserDao();
		UserDao enUserDao = new UserDao();
		PersonalDetailsForAdminPanelDao perDao = new PersonalDetailsForAdminPanelDao();

		UserHealthAnuraDetailsDao userHealthAnuraDao = new UserHealthAnuraDetailsDao();
		UserHealthAnuraDetailsDao enUserHealthAnuraDao = new UserHealthAnuraDetailsDao();
		List<UserHealthAnuraDetailsDao> userHealthAnuraDaoList = new ArrayList<UserHealthAnuraDetailsDao>();

		UserHealthBinahDetailsDao userHealthBinahDao = new UserHealthBinahDetailsDao();
		UserHealthBinahDetailsDao decUserHealthBinahDao = new UserHealthBinahDetailsDao();
		List<UserHealthBinahDetailsDao> userHealthBinahDaoList = new ArrayList<UserHealthBinahDetailsDao>();

		ModelMapper mapper = new ModelMapper();

		try {
			Optional<EndUser> user = userRepository.findById(userId);
			if (user.isEmpty()) {
				response.setSuccess(Boolean.toString(false));
				response.setMsg("Records doesnot exists");
				listDao.setResponse(response);
				return listDao;
			} else {
				userPerDDetails = userPersDetailsRepo.getByUserId(user.get().getUserId());

				if (StringUtils.isEmpty(userPerDDetails)) {
					response.setSuccess(Boolean.toString(false));
					response.setMsg("Onboarding is not done for the user");
					listDao.setResponse(response);
					return listDao;
				}
			}

			String sDKType = (String) EncryptedDecryptedObjectUtil.getDecryptedString(user.get().getsDKType(),
					secretKey, secretIv, isEncryptDecryptDatabaseData);

			if (userPerDDetails != null) {
				perDao.setEmail(userPerDDetails.getUserEmail());
				perDao.setPassword(user.get().getUserPassword());
				perDao.setDob(userPerDDetails.getUserDOB());
				perDao.setGender(userPerDDetails.getUserGender());
				perDao.setHeight(userPerDDetails.getUserHeight());
				perDao.setWeight(userPerDDetails.getUserWeight());
				perDao.setName(userPerDDetails.getUserName());
				perDao.setImage(userPerDDetails.getUserImage());
				perDao.setAge(Float.toString(getAgeDifferencesByDates(userPerDDetails.getUserDOB())));
			}

			else {
				response.setSuccess(Boolean.toString(false));
				response.setMsg("Personal details not found.");
				return listDao;
			}

			userDao.setUserId(user.get().getUserId().toString());
			userDao.setEmailId(user.get().getUserEmail());

			if ("ANURA".equals(sDKType)) {
				userHealthAnuraDetailsList = userHealthAnuraRepo.findByUserId(user.get().getUserId().toString());

				for (UserHealthAnuraDetail ent : userHealthAnuraDetailsList) {

					userHealthAnuraDao = new UserHealthAnuraDetailsDao();

					// mapping user health data from entity to dao class
					userHealthAnuraDao = mapper.map(ent, UserHealthAnuraDetailsDao.class);

					enUserHealthAnuraDao = (UserHealthAnuraDetailsDao) EncryptedDecryptedObjectUtil
							.getDecryptedObject(userHealthAnuraDao, secretKey, secretIv, isEncryptDecryptDatabaseData);

					userHealthAnuraDaoList.add(enUserHealthAnuraDao);
				}
			} else if ("BINAH".equals(sDKType)) {

				userHealthBinahDetailsList = userHealthBinahRepo.findByUserId(user.get().getUserId().toString());

				for (UserHealthBinahDetail ent : userHealthBinahDetailsList) {

					userHealthBinahDao = new UserHealthBinahDetailsDao();

					// mapping user health data from entity to dao class
					userHealthBinahDao = mapper.map(ent, UserHealthBinahDetailsDao.class);

					decUserHealthBinahDao = (UserHealthBinahDetailsDao) EncryptedDecryptedObjectUtil
							.getDecryptedObject(userHealthBinahDao, secretKey, secretIv, isEncryptDecryptDatabaseData);

					userHealthBinahDaoList.add(decUserHealthBinahDao);
				}

			}

			enUserDao = (UserDao) EncryptedDecryptedObjectUtil.getDecryptedObject(userDao, secretKey, secretIv,
					isEncryptDecryptDatabaseData);
			listDao.setUserDao(enUserDao);
			listDao.setUserPerDao(perDao);

			listDao.setUserHealthAnuraDao(userHealthAnuraDaoList);
			listDao.setUserHealthBinahDao(userHealthBinahDaoList);
			response.setSuccess(Boolean.toString(true));
			response.setMsg("Sending records");
			listDao.setResponse(response);

			return listDao;
		} catch (Exception e) {
			response.setSuccess(Boolean.toString(false));
			response.setMsg(someThingWentWrong + e);
			listDao.setResponse(response);
			return listDao;
		}
	}

	@SuppressWarnings("removal")
	@Transactional
	@Override
	public AdminOperationResponseDao updateUserByUserId(AdminUserPersonalDetailsDao dao, String dateTimeFormat) {
		AdminOperationResponseDao respdao = new AdminOperationResponseDao();
		int res = 0;
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());
		//String enConvertedDobFromAge = "";
		String enGender = "";
		String enHeight = "";
		String enName = "";
		String enWeight = "";
		String encurrentDate = "";
		String enUpdatePName = "";
		
		String enDob="";
		 
		UsersPersonalDetails user = userPersDetailsRepo.getByUserId(new Long(dao.getUserId()));

		if (user == null) {
			respdao.setMessage(personalDetailsNoexists);
			respdao.setSuccess(Boolean.toString(false));
			return respdao;
		} else {
			// converting age into dob
//			String convertedDobFromAge = updateDobByAge(Integer.parseInt(dao.getAge()));

			try {
//				enConvertedDobFromAge = (String) EncryptedDecryptedObjectUtil.getEncryptedString(convertedDobFromAge,
//						secretKey, secretIv, isEncryptDecryptDatabaseData);
				enGender = (String) EncryptedDecryptedObjectUtil.getEncryptedString(dao.getGender(), secretKey,
						secretIv, isEncryptDecryptDatabaseData);
				enDob = (String) EncryptedDecryptedObjectUtil.getEncryptedString(dao.getDob(), secretKey,
						secretIv, isEncryptDecryptDatabaseData);
				enHeight = (String) EncryptedDecryptedObjectUtil.getEncryptedString(dao.getHeight(), secretKey,
						secretIv, isEncryptDecryptDatabaseData);
				enName = (String) EncryptedDecryptedObjectUtil.getEncryptedString(dao.getName(), secretKey, secretIv,
						isEncryptDecryptDatabaseData);
				enWeight = (String) EncryptedDecryptedObjectUtil.getEncryptedString(dao.getWeight(), secretKey,
						secretIv, isEncryptDecryptDatabaseData);
				encurrentDate = (String) EncryptedDecryptedObjectUtil.getEncryptedString(currentDate, secretKey,
						secretIv, isEncryptDecryptDatabaseData);
				enUpdatePName = (String) EncryptedDecryptedObjectUtil.getEncryptedString(
						this.getClass().getSimpleName(), secretKey, secretIv, isEncryptDecryptDatabaseData);
			} catch (Exception e) {
				respdao.setMessage("Error occurred while encrypting the Data to Database");
				respdao.setSuccess(Boolean.toString(false));

				return respdao;
			}

			res = userRepository.updateUserByUserId(enGender, enHeight, enName, enDob, enWeight,
					encurrentDate, enUpdatePName, new Long(dao.getUserId()));

			if (res == 0) {
				respdao.setMessage(Unableupdateuser);
				respdao.setSuccess(Boolean.toString(false));
			} else {
				respdao.setMessage(updatedSuccess);
				respdao.setSuccess(Boolean.toString(true));
			}
			respdao.setUserId(dao.getUserId());

			return respdao;
		}
	}

	// method to convert given value of age into dob in yyyy/MM/dd format
	private String updateDobByAge(int age) {
		Date date = new Date();
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int month = localDate.getMonthValue();
		int dat = localDate.getDayOfMonth();
		int year = localDate.getYear();

		LocalDate inputDate = LocalDate.of(year - age, month, dat);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		return inputDate.format(formatter);
//		return inputDate.toString();
	}

	@Override
	public Response getUserDetailsByExcel(Long userId, HttpServletResponse response) {
		Response resp = new Response();
		boolean isSDKTypeAnura = false;

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatExportedFiles);
		String formattedDate = now.format(formatter);

		List<UserHealthAnuraDetail> userHealthAnuraList = new ArrayList<UserHealthAnuraDetail>();
		List<UserHealthBinahDetail> userHealthBinahList = new ArrayList<UserHealthBinahDetail>();

		UserHealthAnuraDetail enUserHealthAnuraDetail = new UserHealthAnuraDetail();
		UserHealthBinahDetail enUserHealthBinahDetail = new UserHealthBinahDetail();

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CommonUserDetailsTable user = userPersDetailsRepo.findUserPersonalDetailsByUserId(userId);
//			List<UserPersonalAndHealthDetails> userDetailsList = userRepository.getUserDetailsByUserId(userId);

			List<UserHealthOnboardingDetail> userHealthOnboardingList = userRepository
					.getUserHealthOnboardingByUserId(userId);

			if (user == null) {
				resp.setMsg("No user data found.");
				resp.setSuccess(Boolean.toString(false));
				return resp;
			}

			String enSDKType = (String) EncryptedDecryptedObjectUtil.getEncryptedString(user.getsDKType(), secretKey,
					secretIv, isEncryptDecryptDatabaseData);

			// Create a single sheet
			Sheet sheet = workbook.createSheet(userDetails);

			// Set up headers
			List<String> headersList = new ArrayList<>();
			headersList.add("User Name");
			headersList.add("Email");
			headersList.add("Gender");
			headersList.add("DOB");
			headersList.add("Height");
			headersList.add("Weight");

			for (UserHealthOnboardingDetail ent : userHealthOnboardingList) {
//				UserHealthOnboardingDetail enEnt = new UserHealthOnboardingDetail();
				String enQName = "";
				try {

//					enEnt = (UserHealthOnboardingDetail) EncryptedDecryptedObjectUtil.getDecryptedObject(ent, secretKey,
//							secretIv, isEncryptDecryptDatabaseData);

					enQName = (String) EncryptedDecryptedObjectUtil.getDecryptedString(ent.getOnboardingQuestionName(),
							secretKey, secretIv, isEncryptDecryptDatabaseData);
				} catch (Exception e) {
					resp.setMsg(someThingWentWrong);
					resp.setSuccess(Boolean.toString(false));
					return resp;
				}
				headersList.add(enQName);
			}

			String[] headers = headersList.toArray(new String[0]);

			// Write header row
			Row headerRow = sheet.createRow(0);
			for (int i = 0; i < headers.length; i++) {
				headerRow.createCell(i).setCellValue(headers[i]);
			}

			// user details rows
			int rowNum = 1;
			Row row = sheet.createRow(rowNum++);
			writeUserDetails(row, user, userHealthOnboardingList);

			List<String> healthHeaderList = new ArrayList<>();

			// Setting header either for Anura or Binah based on the SDK Type
			setHeaderList(enSDKType, healthHeaderList);

			String[] healthHeaders = healthHeaderList.toArray(new String[0]);

			// Write health headers
			Row healthHeaderRow = sheet.createRow(++rowNum);
			for (int i = 0; i < healthHeaders.length; i++) {
				healthHeaderRow.createCell(i).setCellValue(healthHeaders[i]);
			}
			rowNum = rowNum + 1;

			// to fetch health details either Anura or Binah

			if ("ANURA".equals(enSDKType)) {
				isSDKTypeAnura = true;
				userHealthAnuraList = userHealthAnuraRepo.findByUserId(userId.toString());

				for (UserHealthAnuraDetail userHealth : userHealthAnuraList) {
					Row rowHealth = sheet.createRow(rowNum++);
					try {
						userHealth.setEndUser(null);
						enUserHealthAnuraDetail = (UserHealthAnuraDetail) EncryptedDecryptedObjectUtil
								.getDecryptedObject(userHealth, secretKey, secretIv, isEncryptDecryptDatabaseData);
					} catch (Exception e) {
						resp.setMsg(someThingWentWrong);
						resp.setSuccess(Boolean.toString(false));
						return resp;
					}
					writeHealthDetails(rowHealth, enUserHealthAnuraDetail, isSDKTypeAnura);
				}
			} else if ("BINAH".equals(enSDKType)) {
				userHealthBinahList = userHealthBinahRepo.findByUserId(userId.toString());

				for (UserHealthBinahDetail userHealth : userHealthBinahList) {
					Row rowHealth = sheet.createRow(rowNum++);
					try {
						userHealth.setEndUser(null);
						enUserHealthBinahDetail = (UserHealthBinahDetail) EncryptedDecryptedObjectUtil
								.getDecryptedObject(userHealth, secretKey, secretIv, isEncryptDecryptDatabaseData);
					} catch (Exception e) {
						resp.setMsg(someThingWentWrong);
						resp.setSuccess(Boolean.toString(false));
						return resp;
					}
					writeHealthDetails(rowHealth, enUserHealthBinahDetail, isSDKTypeAnura);
				}
			}

			// Convert workbook to byte array
			workbook.write(out);

			// File name without extension
			String fileNameWithoutExtension = filePathExported + userId + "_" + formattedDate;
			String fullFileName = fileNameWithoutExtension + ".xlsx";
			String downLoadedName = userId + "_" + formattedDate;

			// Optionally save to file system
			try (FileOutputStream fileOut = new FileOutputStream(fullFileName)) {
				fileOut.write(out.toByteArray());
			}

			// Set response headers
			response.setContentType(excepContentType);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + downLoadedName + ".xlsx");

			// Write the output to the response output stream
			workbook.write(response.getOutputStream());

			resp.setMsg("Excel file is generated successfully.");
			resp.setSuccess(Boolean.toString(true));

		} catch (Exception e) {
			resp.setMsg("Unbale to export the file.");
			resp.setSuccess(Boolean.toString(false));
		}
		return resp;
	}

	private void setHeaderList(String enSDKType, List<String> healthHeaderList) {
		if ("ANURA".equals(enSDKType)) {

			healthHeaderList.add("Age");
			healthHeaderList.add("hRBPM");
			healthHeaderList.add("bPSystolic");
			healthHeaderList.add("hRVSDNN");
			healthHeaderList.add("bPRPP");
			healthHeaderList.add("bPTau");
			healthHeaderList.add("healthScore");
			healthHeaderList.add("mentalScore");
			healthHeaderList.add("vitalScore");
			healthHeaderList.add("physicalScore");
			healthHeaderList.add("mSI");
			healthHeaderList.add("bpHeartAttack");
			healthHeaderList.add("bPStroke");
			healthHeaderList.add("bPCVD");
			healthHeaderList.add("risksScore");
			healthHeaderList.add("sNR");
			healthHeaderList.add("bRBPM");
			healthHeaderList.add("bpDiastolic");
			healthHeaderList.add("iHBCount");
			healthHeaderList.add("hBA1CRiskProb");
			healthHeaderList.add("mFBGRiskProb");
			healthHeaderList.add("dBTRiskProb");
			healthHeaderList.add("fLDRiskProb");
			healthHeaderList.add("hDLTCRiskProb");
			healthHeaderList.add("hPTRiskProb");
			healthHeaderList.add("overallMetabolicRiskProb");
			healthHeaderList.add("tGRiskProb");
			healthHeaderList.add("physioScore");
		} else if ("BINAH".equals(enSDKType)) {
			healthHeaderList.add("pulseRate");
			healthHeaderList.add("respirationRate");
			healthHeaderList.add("oxygenSaturation");
			healthHeaderList.add("sdnn");
			healthHeaderList.add("stressLevel");
			healthHeaderList.add("rri");
			healthHeaderList.add("bloodPressure");
			healthHeaderList.add("stressIndex");
			healthHeaderList.add("meanRri");
			healthHeaderList.add("rmssd");
			healthHeaderList.add("sd1");
			healthHeaderList.add("sd2");
			healthHeaderList.add("prq");
			healthHeaderList.add("pnsIndex");
			healthHeaderList.add("pnsZone");
			healthHeaderList.add("snsIndex");
			healthHeaderList.add("snsZone");
			healthHeaderList.add("wellnessIndex");
			healthHeaderList.add("wellnessLevel");
			healthHeaderList.add("lfhf");
			healthHeaderList.add("hemoglobin");
			healthHeaderList.add("hemoglobinA1C");
			healthHeaderList.add("highHemoglobinA1CRisk");
			healthHeaderList.add("highBloodPressureRisk");
			healthHeaderList.add("ascvdRisk");
			healthHeaderList.add("normalizedStressIndex");
			healthHeaderList.add("heartAge");
			healthHeaderList.add("highTotalCholesterolRisk");
			healthHeaderList.add("highFastingGlucoseRisk");
			healthHeaderList.add("lowHemoglobinRisk");
		}
	}

	private void writeUserDetails(Row row, CommonUserDetailsTable user, List<UserHealthOnboardingDetail> healthList) {

		CommonUserDetailsTable enUser = new CommonUserDetailsTable();
		try {
			enUser = (CommonUserDetailsTable) EncryptedDecryptedObjectUtil.getDecryptedObject(user, secretKey, secretIv,
					isEncryptDecryptDatabaseData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ObjectMapper objectMapper = new ObjectMapper();
		row.createCell(0).setCellValue(enUser.getUserName());
		row.createCell(1).setCellValue(enUser.getUserEmail());
		row.createCell(2).setCellValue(enUser.getUserGender());
		row.createCell(3).setCellValue(enUser.getUserDOB());
		row.createCell(4).setCellValue(enUser.getUserHeight());
		row.createCell(5).setCellValue(enUser.getUserWeight());

		for (int i = 0; i < healthList.size(); i++) {
			try {
				row.createCell(6 + i)
						.setCellValue(objectMapper.writeValueAsString(healthList.get(i).getOnboardingAnswerValue()));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	private void writeHealthDetails(Row row, Object obj, boolean isSDKTypeAnura) {
		UserHealthAnuraDetail userHealthAnuraDetail = new UserHealthAnuraDetail();
		UserHealthBinahDetail userHealthBinahDetail = new UserHealthBinahDetail();

		if (isSDKTypeAnura) {
			userHealthAnuraDetail = (UserHealthAnuraDetail) obj;

			row.createCell(0).setCellValue(userHealthAnuraDetail.getAge());
			row.createCell(1).setCellValue(userHealthAnuraDetail.gethRBPM());
			row.createCell(2).setCellValue(userHealthAnuraDetail.getbPSystolic());
			row.createCell(3).setCellValue(userHealthAnuraDetail.gethRVSDNN());
			row.createCell(4).setCellValue(userHealthAnuraDetail.getbPRPP());
			row.createCell(5).setCellValue(userHealthAnuraDetail.getbPTau());
			row.createCell(6).setCellValue(userHealthAnuraDetail.getHealthScore());
			row.createCell(7).setCellValue(userHealthAnuraDetail.getMentalScore());
			row.createCell(8).setCellValue(userHealthAnuraDetail.getVitalScore());
			row.createCell(9).setCellValue(userHealthAnuraDetail.getPhysicalScore());
			row.createCell(10).setCellValue(userHealthAnuraDetail.getmSI());
			row.createCell(11).setCellValue(userHealthAnuraDetail.getBpHeartAttack());
			row.createCell(12).setCellValue(userHealthAnuraDetail.getbPStroke());
			row.createCell(13).setCellValue(userHealthAnuraDetail.getbPCVD());
			row.createCell(14).setCellValue(userHealthAnuraDetail.getRisksScore());
			row.createCell(15).setCellValue(userHealthAnuraDetail.getsNR());
			row.createCell(16).setCellValue(userHealthAnuraDetail.getbRBPM());
			row.createCell(17).setCellValue(userHealthAnuraDetail.getBpDiastolic());
			row.createCell(18).setCellValue(userHealthAnuraDetail.getiHBCount());
			row.createCell(19).setCellValue(userHealthAnuraDetail.gethBA1CRiskProb());
			row.createCell(20).setCellValue(userHealthAnuraDetail.getmFBGRiskProb());
			row.createCell(21).setCellValue(userHealthAnuraDetail.getdBTRiskProb());
			row.createCell(22).setCellValue(userHealthAnuraDetail.getfLDRiskProb());
			row.createCell(23).setCellValue(userHealthAnuraDetail.gethDLTCRiskProb());
			row.createCell(24).setCellValue(userHealthAnuraDetail.gethPTRiskProb());
			row.createCell(25).setCellValue(userHealthAnuraDetail.getOverallMetabolicRiskProb());
			row.createCell(26).setCellValue(userHealthAnuraDetail.gettGRiskProb());
			row.createCell(26).setCellValue(userHealthAnuraDetail.getPhysioScore());

		} else {
			userHealthBinahDetail = (UserHealthBinahDetail) obj;

			row.createCell(0).setCellValue(userHealthBinahDetail.getPulseRate());
			row.createCell(1).setCellValue(userHealthBinahDetail.getRespirationRate());
			row.createCell(2).setCellValue(userHealthBinahDetail.getOxygenSaturation());
			row.createCell(3).setCellValue(userHealthBinahDetail.getSdnn());
			row.createCell(4).setCellValue(userHealthBinahDetail.getStressLevel());
			row.createCell(6).setCellValue(userHealthBinahDetail.getBloodPressure());
			row.createCell(7).setCellValue(userHealthBinahDetail.getStressIndex());
			row.createCell(8).setCellValue(userHealthBinahDetail.getMeanRri());
			row.createCell(9).setCellValue(userHealthBinahDetail.getRmssd());
			row.createCell(10).setCellValue(userHealthBinahDetail.getSd1());
			row.createCell(11).setCellValue(userHealthBinahDetail.getSd2());
			row.createCell(12).setCellValue(userHealthBinahDetail.getPrq());
			row.createCell(13).setCellValue(userHealthBinahDetail.getPnsIndex());
			row.createCell(14).setCellValue(userHealthBinahDetail.getPnsZone());
			row.createCell(15).setCellValue(userHealthBinahDetail.getSnsIndex());
			row.createCell(16).setCellValue(userHealthBinahDetail.getSnsZone());
			row.createCell(17).setCellValue(userHealthBinahDetail.getWellnessIndex());
			row.createCell(18).setCellValue(userHealthBinahDetail.getWellnessLevel());
			row.createCell(19).setCellValue(userHealthBinahDetail.getLfhf());
			row.createCell(20).setCellValue(userHealthBinahDetail.getHemoglobin());
			row.createCell(21).setCellValue(userHealthBinahDetail.getHemoglobinA1C());
			row.createCell(22).setCellValue(userHealthBinahDetail.getHighHemoglobinA1CRisk());
			row.createCell(23).setCellValue(userHealthBinahDetail.getHighBloodPressureRisk());
			row.createCell(24).setCellValue(userHealthBinahDetail.getAscvdRisk());
			row.createCell(25).setCellValue(userHealthBinahDetail.getNormalizedStressIndex());
			row.createCell(26).setCellValue(userHealthBinahDetail.getHeartAge());
			row.createCell(27).setCellValue(userHealthBinahDetail.getHighTotalCholesterolRisk());
			row.createCell(28).setCellValue(userHealthBinahDetail.getHighFastingGlucoseRisk());
			row.createCell(29).setCellValue(userHealthBinahDetail.getLowHemoglobinRisk());
		}

	}

}
