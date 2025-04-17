package com.iss.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iss.dao.ResponseDao;
import com.iss.dao.UserHealthDetailsDao;
import com.iss.entity.CommonUserDetailsTable;
import com.iss.entity.UserHealthDetail;
import com.iss.entity.UsersPersonalDetails;
import com.iss.repository.UserHealthDetailsRepo;
import com.iss.repository.UserPersonalDetailsRepository;

import jakarta.transaction.Transactional;

@Service
public class SDKDataServiceImpl implements SDKDataService {

	@Autowired
	private UserHealthDetailsRepo userHealthDetRepo;

	@Autowired
	private UserPersonalDetailsRepository userPerDetrailRespo;

	@Transactional
	@Override
	public ResponseDao saveUserSDKData(String userId, UserHealthDetailsDao dao, String dateTimeFormat) {
		ResponseDao resp = new ResponseDao();
		ModelMapper mapper = new ModelMapper();

		SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat);
		String currentDate = sdf.format(new Date());

		UserHealthDetail userHealth = new UserHealthDetail();

		@SuppressWarnings("removal")
		CommonUserDetailsTable userPerDetails = userPerDetrailRespo.findUserPersonalDetailsByUserId(new Long(userId));

		if (userPerDetails == null) {
			resp.setMessage("Wrong User ID is recieved");
			resp.setSuccess(false);
			return resp;
		}

		Optional<UsersPersonalDetails> userPerData = userPerDetrailRespo
				.findById(userPerDetails.getUserPersonalDetailId().toString());

		if (userPerData.isEmpty()) {
			resp.setMessage("User Personal Details doesnot exists.");
			resp.setSuccess(false);
			return resp;
		}
		userHealth = mapper.map(dao, UserHealthDetail.class);

		userHealth.setUserPersonalDetails(userPerData.get());
		userHealth.setRegistDate(currentDate);
		userHealth.setRegistPName(this.getClass().getSimpleName());
		userHealth.setLastUpdateDate(currentDate);
		userHealth.setLastUpdatePName(this.getClass().getSimpleName());

		UserHealthDetail userHealthData = userHealthDetRepo.save(userHealth);

		if (userHealthData != null) {
			resp.setMessage("User Health Data is saved Successfullyy...");
			resp.setSuccess(true);
		} else {
			resp.setMessage("Unable to create User Health Data.");
			resp.setSuccess(false);
		}

		resp.setEmailId(userPerDetails.getUserEmail());

		return resp;
	}

}
