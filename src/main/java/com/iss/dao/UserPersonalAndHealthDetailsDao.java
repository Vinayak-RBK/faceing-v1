package com.iss.dao;

import java.util.List;

public class UserPersonalAndHealthDetailsDao {
//	private Integer userId;
//	private String userEmail;
//	private String userPassword;
//	private String userName;
//	private String userGender;
//	private String userDOB;
//	private BigDecimal userWeight;
//	private BigDecimal userHeight;
//	private String userImage;
//	private BigDecimal userRespirationRate;
//	private BigDecimal userBloodPressure;
//	private Integer userHeartRate;
//	private BigDecimal userOxygenSaturation;
//	private BigDecimal userHRVData;
//	private BigDecimal userStressLevel;
//	private BigDecimal userRelaxationLevel;
//	private BigDecimal userEnergyLevel;
//	private Integer userBodyShapeIndex;
//	private BigDecimal userBodyMassIndex;
//	private Integer userAge;
//	private BigDecimal userHemoglobinLevel;
//	private BigDecimal userFitnessLevel;
	private UserDao userDao;
	private PersonalDetailsForAdminPanelDao userPerDao;
	private List<UserHealthDetailsDao> userHealthDao;
	private List<BasicHealthQuestionDao> questionsDao;
	private Response response;

	public UserPersonalAndHealthDetailsDao() {
		super();
	}

	public UserPersonalAndHealthDetailsDao(UserDao userDao, PersonalDetailsForAdminPanelDao userPerDao,
			List<UserHealthDetailsDao> userHealthDao, List<BasicHealthQuestionDao> questionsDao, Response response) {
		super();
		this.userDao = userDao;
		this.userPerDao = userPerDao;
		this.userHealthDao = userHealthDao;
		this.questionsDao = questionsDao;
		this.response = response;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public PersonalDetailsForAdminPanelDao getUserPerDao() {
		return userPerDao;
	}

	public void setUserPerDao(PersonalDetailsForAdminPanelDao userPerDao) {
		this.userPerDao = userPerDao;
	}

	public List<UserHealthDetailsDao> getUserHealthDao() {
		return userHealthDao;
	}

	public void setUserHealthDao(List<UserHealthDetailsDao> userHealthDao) {
		this.userHealthDao = userHealthDao;
	}

	public List<BasicHealthQuestionDao> getQuestionsDao() {
		return questionsDao;
	}

	public void setQuestionsDao(List<BasicHealthQuestionDao> questionsDao) {
		this.questionsDao = questionsDao;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "UserPersonalAndHealthDetailsDao [userDao=" + userDao + ", userPerDao=" + userPerDao + ", userHealthDao="
				+ userHealthDao + ", questionsDao=" + questionsDao + ", response=" + response + "]";
	}

}
