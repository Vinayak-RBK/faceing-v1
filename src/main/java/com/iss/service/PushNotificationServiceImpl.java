package com.iss.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iss.dao.PushNotifiResponseDao;
import com.iss.entity.EndUser;
import com.iss.repository.UserRepository;

@Service
public class PushNotificationServiceImpl implements PushNotificationService {
	
	@Autowired
	public UserRepository userRepository;

	@Override
	public PushNotifiResponseDao enablePushNotification(Long userId,String dateTimeFormat) {
        Optional<EndUser> endUser = userRepository.findById(userId);
        PushNotifiResponseDao respDao=new PushNotifiResponseDao();
        
        SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());

        if (!endUser.isEmpty()) {
            EndUser user = endUser.get();
            
            if(Boolean.parseBoolean(user.getPushNotify()))
            {
            	 user.setPushNotify(Boolean.toString(false));
            	 respDao.setMessage("Disabled Push notification.");
            }
            else
            {
            	 user.setPushNotify(Boolean.toString(true));
            	 respDao.setMessage("Enabled Push notification.");
            }
            
            user.setLastUpdateDate(currentDate);
            user.setLastUpdatePName(this.getClass().getSimpleName());
            
            userRepository.save(user);
            
            respDao.setSuccess(Boolean.toString(true));
            
            return respDao;
        }
        else
        {
        	respDao.setSuccess(Boolean.toString(false));
        	respDao.setMessage("Wrong user id is received.");
        	return respDao;
        }
	}
}
