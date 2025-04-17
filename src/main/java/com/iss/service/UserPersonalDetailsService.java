package com.iss.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.iss.dao.BasicHealthQuestionDao;
import com.iss.dao.PersonalDetailsDao;
import com.iss.dao.ResponseDao;
import com.iss.dao.UserDao;

@Service
public interface UserPersonalDetailsService {

	public ResponseDao insertUserPersonalData(UserDao userDao, PersonalDetailsDao perDetailsDao,
			List<BasicHealthQuestionDao> healthDetailsListDao, String dateTimeFormat);

}
