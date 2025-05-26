package com.iss.service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iss.dao.AdminOperationResponseDao;
import com.iss.dao.AdminUserPersonalDetailsDao;
import com.iss.dao.BasicHealthQuestionDao;
import com.iss.dao.PersonalDetailsForAdminPanelDao;
import com.iss.dao.Response;
import com.iss.dao.UserDao;
import com.iss.dao.UserHealthDetailsDao;
import com.iss.dao.UserPersonalAndHealthDetailsDao;
import com.iss.dao.UserSearchResponseDao;
import com.iss.entity.CommonUserDetailsTable;
import com.iss.entity.EndUser;
import com.iss.entity.UserHealthOnboardingDetail;
import com.iss.entity.UserPersonalAndHealthDetails;
import com.iss.entity.UsersPersonalDetails;
import com.iss.repository.UserPersonalDetailsRepository;
import com.iss.repository.UserRepository;

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

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserPersonalDetailsRepository userPersDetailsRepo;

	private List<UserPersonalAndHealthDetails> perHealthDetailsList = new ArrayList<UserPersonalAndHealthDetails>();

	private List<UserHealthOnboardingDetail> userHOnBoardDetailsList = new ArrayList<UserHealthOnboardingDetail>();

	private UserPersonalAndHealthDetailsDao listDao = new UserPersonalAndHealthDetailsDao();

	private UsersPersonalDetails userPerDDetails = new UsersPersonalDetails();

	private Response response = new Response();

	@Override
	public List<UserSearchResponseDao> getAllUserDetails(String dateTimeFormat) {
		List<CommonUserDetailsTable> entList = userRepository.searchUserListDetails();
		List<UserSearchResponseDao> dtoList = new ArrayList<>();

		for (CommonUserDetailsTable ent : entList) {
			UserSearchResponseDao dto = new UserSearchResponseDao();
			dto.setName(ent.getUserName());
			dto.setGender(ent.getUserGender());
			dto.setWeight(ent.getUserWeight());
			dto.setEmail(ent.getUserEmail());
			dto.setDob(ent.getUserDOB());
			dto.setHeight(ent.getUserHeight());
			dto.setId(ent.getUserId());
			dto.setImage(ent.getUserImage());
			dto.setStatus(ent.getIsBlocked());
			dto.setAge(getAgeDifferencesByDates(ent.getUserDOB()));

			dtoList.add(dto);
		}
		return dtoList;
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
			resDao.setSuccess(false);
			resDao.setMsg("Unable To Block/Unblock User");
			return resDao;
		}

		Boolean isBlocked = user.get().getIsBlocked();

		if (isBlocked) {
			user.get().setIsBlocked(false);
		} else {
			user.get().setIsBlocked(true);
		}
		userRepository.save(user.get());

		String status = isBlocked ? "Active" : "Inactive";

		resDao.setBlockStatus(status);

		if (isBlocked) {
			resDao.setSuccess(true);
			resDao.setMsg(UNblockUserId);
			return resDao;
		} else {
			resDao.setSuccess(true);
			resDao.setMsg(blockUserId);
			return resDao;
		}

	}

	@SuppressWarnings("deprecation")
	@Transactional
	@Override
	public UserPersonalAndHealthDetailsDao getUserDetailsByUserId(Long userId) {

		UserDao userDao = new UserDao();
		PersonalDetailsForAdminPanelDao perDao = new PersonalDetailsForAdminPanelDao();
		UserHealthDetailsDao userHealthDao = new UserHealthDetailsDao();
		List<UserHealthDetailsDao> userHealthDaoList = new ArrayList<UserHealthDetailsDao>();
		List<BasicHealthQuestionDao> quesListDao = new ArrayList<BasicHealthQuestionDao>();

		Optional<EndUser> user = userRepository.findById(userId);
		if (user.isEmpty()) {
			response.setSuccess(false);
			response.setMsg("Records doesnot exists");
			listDao.setResponse(response);
			return listDao;
		} else {
			userPerDDetails = userPersDetailsRepo.getByUserId(user.get().getUserId());

			if (StringUtils.isEmpty(userPerDDetails)) {
				response.setSuccess(false);
				response.setMsg("Onboarding is not done for the user");
				listDao.setResponse(response);
				return listDao;
			}
		}

		perHealthDetailsList = userRepository.getUserDetailsByUserId(userId);
		userHOnBoardDetailsList = userRepository.getUserHealthOnboardingByUserId(userId);

		userDao.setUserId(user.get().getUserId().toString());
		userDao.setEmailId(user.get().getUserEmail());

		if (userPerDDetails != null) {
			perDao.setEmail(userPerDDetails.getUserEmail());
			perDao.setPassword(user.get().getUserPassword());
			perDao.setDob(userPerDDetails.getUserDOB());
			perDao.setGender(userPerDDetails.getUserGender());
			perDao.setHeight(userPerDDetails.getUserHeight().doubleValue());
			perDao.setWeight(userPerDDetails.getUserWeight().doubleValue());
			perDao.setName(userPerDDetails.getUserName());
			perDao.setImage(userPerDDetails.getUserImage());
		}

		for (UserPersonalAndHealthDetails ent : perHealthDetailsList) {
			userHealthDao = new UserHealthDetailsDao();
			userHealthDao.setAge(ent.getUserAge());
//			userHealthDao.setBloodPressure(ent.getUserBloodPressure());
//			userHealthDao.setBodyMassIndex(ent.getUserBodyMassIndex());
//			userHealthDao.setBodyShapeIndex(ent.getUserBodyShapeIndex());
//			userHealthDao.setEnergyLevel(ent.getUserEnergyLevel());
//			userHealthDao.setVo2Max(ent.getUserFitnessLevel());
//			userHealthDao.setUserHeartRate(ent.getUserHeartRate());
//			userHealthDao.setSkinTemperature(ent.getUserOxygenSaturation());
//			userHealthDao.setUserHRVData(ent.getUserHRVData());
//			userHealthDao.setOxygenSaturation(ent.getUserOxygenSaturation());
//			userHealthDao.setRelaxationLevel(ent.getUserRelaxationLevel());
//			userHealthDao.setHemoglobinLevel(ent.getUserHemoglobinLevel());
//			userHealthDao.setStressLevel(ent.getUserStressLevel());
//			userHealthDao.setRespirationRate(ent.getUserRespirationRate());
//			userHealthDaoList.add(userHealthDao);
		}

		for (UserHealthOnboardingDetail ent : userHOnBoardDetailsList) {
			BasicHealthQuestionDao dao = new BasicHealthQuestionDao();
			dao.setId(ent.getId().toString());
			dao.setAnswer(ent.getOnboardingAnswerValue());
			quesListDao.add(dao);

		}

		listDao.setUserDao(userDao);
		listDao.setUserPerDao(perDao);
		listDao.setUserHealthDao(userHealthDaoList);
		listDao.setQuestionsDao(quesListDao);
		response.setSuccess(true);
		response.setMsg("Sending records");
		listDao.setResponse(response);

		return listDao;
	}

	@SuppressWarnings("removal")
	@Transactional
	@Override
	public AdminOperationResponseDao updateUserByUserId(AdminUserPersonalDetailsDao dao, String dateTimeFormat) {
		AdminOperationResponseDao respdao = new AdminOperationResponseDao();
		int res = 0;
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());

		UsersPersonalDetails user = userPersDetailsRepo.getByUserId(new Long(dao.getUserId()));

		if (user == null) {
			respdao.setMessage(personalDetailsNoexists);
			respdao.setSuccess(false);
			return respdao;
		} else {
			// converting age into dob
			String convertedDobFromAge = updateDobByAge(dao.getAge());

			res = userRepository.updateUserByUserId(dao.getGender(), new BigDecimal(dao.getHeight()), dao.getName(),
					convertedDobFromAge, new BigDecimal(dao.getWeight()), currentDate, this.getClass().getSimpleName(),
					new Long(dao.getUserId()));

			if (res == 0) {
				respdao.setMessage(Unableupdateuser);
				respdao.setSuccess(false);
			} else {
				respdao.setMessage(updatedSuccess);
				respdao.setSuccess(true);
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
		return inputDate.toString();
	}

	@Override
	public Response getUserDetailsByExcel(Long userId, HttpServletResponse response) {
		Response resp = new Response();

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatExportedFiles);
		String formattedDate = now.format(formatter);

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			CommonUserDetailsTable user = userPersDetailsRepo.findUserPersonalDetailsByUserId(userId);
			List<UserPersonalAndHealthDetails> userDetailsList = userRepository.getUserDetailsByUserId(userId);
			List<UserHealthOnboardingDetail> userHealthOnboardingList = userRepository
					.getUserHealthOnboardingByUserId(userId);

			if (user == null) {
				resp.setMsg("No user data found.");
				resp.setSuccess(false);
				return resp;
			}

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
				headersList.add(ent.getOnboardingQuestionName());
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
			healthHeaderList.add("UserBloodPressure");
			healthHeaderList.add("UserBodyMassIndex");
			healthHeaderList.add("UserEnergyLevel");
			healthHeaderList.add("UserFitnessLevel");
			healthHeaderList.add("UserRelaxationLevel");
			healthHeaderList.add("UserHemoglobinLevel");
			healthHeaderList.add("UserHRVData");
			healthHeaderList.add("UserOxygenSaturation");
			healthHeaderList.add("UserRespirationRate");
			healthHeaderList.add("UserStressLevel");

			String[] healthHeaders = healthHeaderList.toArray(new String[0]);

			// Write health headers
			Row healthHeaderRow = sheet.createRow(++rowNum);
			for (int i = 0; i < healthHeaders.length; i++) {
				healthHeaderRow.createCell(i).setCellValue(healthHeaders[i]);
			}
			rowNum = rowNum + 1;
			for (UserPersonalAndHealthDetails userHealth : userDetailsList) {
				Row rowHealth = sheet.createRow(rowNum++);
				writeHealthDetails(rowHealth, userHealth);
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
			resp.setSuccess(true);

		} catch (IOException e) {
			resp.setMsg("Unbale to export the file.");
			resp.setSuccess(false);
		}
		return resp;
	}

	private void writeUserDetails(Row row, CommonUserDetailsTable user, List<UserHealthOnboardingDetail> healthList) {

		ObjectMapper objectMapper = new ObjectMapper();
		row.createCell(0).setCellValue(user.getUserName());
		row.createCell(1).setCellValue(user.getUserEmail());
		row.createCell(2).setCellValue(user.getUserGender());
		row.createCell(3).setCellValue(user.getUserDOB());
		row.createCell(4).setCellValue(user.getUserHeight().toString());
		row.createCell(5).setCellValue(user.getUserWeight().toString());

		for (int i = 0; i < healthList.size(); i++) {
			try {
				row.createCell(6 + i)
						.setCellValue(objectMapper.writeValueAsString(healthList.get(i).getOnboardingAnswerValue()));
			} catch (JsonProcessingException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void writeHealthDetails(Row row, UserPersonalAndHealthDetails user) {
		row.createCell(0).setCellValue(user.getUserBloodPressure().toString());
		row.createCell(1).setCellValue(user.getUserBodyMassIndex().toString());
		row.createCell(2).setCellValue(user.getUserEnergyLevel().toString());
		row.createCell(3).setCellValue(user.getUserFitnessLevel().toString());
		row.createCell(4).setCellValue(user.getUserRelaxationLevel().toString());
		row.createCell(5).setCellValue(user.getUserHemoglobinLevel().toString());
		row.createCell(6).setCellValue(user.getUserHRVData().toString());
		row.createCell(7).setCellValue(user.getUserOxygenSaturation().toString());
		row.createCell(8).setCellValue(user.getUserRespirationRate().toString());
		row.createCell(9).setCellValue(user.getUserStressLevel().toString());
	}

}
