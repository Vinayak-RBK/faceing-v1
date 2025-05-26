package com.iss.dao;

import java.util.List;

public class CompleteDetailsDao {
	
	private UserDao userDao;
	private PersonalDetailsDao perDetailsDao;
	private List<BasicHealthQuestionDao> helthDetailsListDao;
	
	public CompleteDetailsDao() {
		super();
	}

	public CompleteDetailsDao(UserDao userDao, PersonalDetailsDao perDetailsDao,
			List<BasicHealthQuestionDao> helthDetailsListDao) {
		super();
		this.userDao = userDao;
		this.perDetailsDao = perDetailsDao;
		this.helthDetailsListDao = helthDetailsListDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public PersonalDetailsDao getPerDetailsDao() {
		return perDetailsDao;
	}

	public void setPerDetailsDao(PersonalDetailsDao perDetailsDao) {
		this.perDetailsDao = perDetailsDao;
	}

	public List<BasicHealthQuestionDao> getHelthDetailsListDao() {
		return helthDetailsListDao;
	}

	public void setHelthDetailsListDao(List<BasicHealthQuestionDao> helthDetailsListDao) {
		this.helthDetailsListDao = helthDetailsListDao;
	}

	@Override
	public String toString() {
		return "CompleteDetailsDao [userDao=" + userDao + ", perDetailsDao=" + perDetailsDao + ", helthDetailsListDao="
				+ helthDetailsListDao + "]";
	}

}
