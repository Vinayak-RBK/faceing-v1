package com.iss.dao;

import java.util.List;

public class UserPersonalAndHealthDetailsDao {
	private UserDao userDao;
	private PersonalDetailsForAdminPanelDao userPerDao;
	private List<UserHealthAnuraDetailsDao> userHealthAnuraDao;
	private List<UserHealthBinahDetailsDao> userHealthBinahDao;
	private List<BasicHealthQuestionDao> questionsDao;
	private Response response;

	public UserPersonalAndHealthDetailsDao() {
		super();
	}

	public UserPersonalAndHealthDetailsDao(UserDao userDao, PersonalDetailsForAdminPanelDao userPerDao,
			List<UserHealthAnuraDetailsDao> userHealthAnuraDao, List<UserHealthBinahDetailsDao> userHealthBinahDao,
			List<BasicHealthQuestionDao> questionsDao, Response response) {
		super();
		this.userDao = userDao;
		this.userPerDao = userPerDao;
		this.userHealthAnuraDao = userHealthAnuraDao;
		this.userHealthBinahDao = userHealthBinahDao;
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

	public List<UserHealthAnuraDetailsDao> getUserHealthAnuraDao() {
		return userHealthAnuraDao;
	}

	public void setUserHealthAnuraDao(List<UserHealthAnuraDetailsDao> userHealthAnuraDao) {
		this.userHealthAnuraDao = userHealthAnuraDao;
	}

	public List<UserHealthBinahDetailsDao> getUserHealthBinahDao() {
		return userHealthBinahDao;
	}

	public void setUserHealthBinahDao(List<UserHealthBinahDetailsDao> userHealthBinahDao) {
		this.userHealthBinahDao = userHealthBinahDao;
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
		return "UserPersonalAndHealthDetailsDao [userDao=" + userDao + ", userPerDao=" + userPerDao
				+ ", userHealthAnuraDao=" + userHealthAnuraDao + ", userHealthBinahDao=" + userHealthBinahDao
				+ ", questionsDao=" + questionsDao + ", response=" + response + "]";
	}

}
