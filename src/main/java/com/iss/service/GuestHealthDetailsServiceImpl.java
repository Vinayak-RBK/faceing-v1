package com.iss.service;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iss.dao.GuestHealthAnuraDetailsDao;
import com.iss.entity.GuestHealthAnuraDetail;
import com.iss.repository.GuestHealthAnuraDetailRepository;

@Service
public class GuestHealthDetailsServiceImpl implements GuestHealthDetailsService {

	@Autowired
	private GuestHealthAnuraDetailRepository guestHealthDetailRepository;
	
	private static final Logger logger = LoggerFactory.getLogger(GuestHealthDetailsServiceImpl.class);

	@Override
	public boolean addGuestHealthDetailService(GuestHealthAnuraDetailsDao guestHealthDetailsDao, String dateTimeFormat) {

		try {
			GuestHealthAnuraDetail guestHealthDetail = new GuestHealthAnuraDetail();

			ModelMapper modelMapper = new ModelMapper();

			guestHealthDetail = modelMapper.map(guestHealthDetailsDao, GuestHealthAnuraDetail.class);

			guestHealthDetailRepository.save(guestHealthDetail);
			
			logger.info("Saving guest Health Details");
			
			return true;
		} catch (Exception e) {
			logger.info("Exception occurred while Saving guest Health Details - {}",e);
		}

		return false;
	}

}
