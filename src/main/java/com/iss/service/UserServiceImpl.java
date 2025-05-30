package com.iss.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iss.dao.UserDao;
import com.iss.entity.EndUser;
import com.iss.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserRepository userRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public Integer saveUser(UserDao userDao, String dateTimeFormat) {
		Integer result = 0;
		EndUser user = new EndUser();
		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);

		String currentDate = sdf.format(new Date());

		try {
			user.setUserEmail(userDao.getEmailId());
			user.setUserPassword(userDao.getPassword());
			user.setRegistDate(currentDate);
			user.setRegistPName(this.getClass().getSimpleName());
			user.setLastUpdateDate(currentDate);
			user.setLastUpdatePName(this.getClass().getSimpleName());

			Long userId = userRepository.save(user).getUserId();

			if (userId!=0) {
				result = 1;
			}

		} catch (Exception e) {
			logger.error("Exception occured while saving user in  saveUser() method - {}",e);
		}

		logger.info("Saved user successfully");
		return result;
	}

}
