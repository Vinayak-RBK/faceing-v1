package com.iss.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iss.dao.GuestHealthDetailsDao;
import com.iss.entity.GuestHealthDetail;
import com.iss.repository.GuestHealthDetailRepository;

@Service
public class GuestHealthDetailsServiceImpl implements GuestHealthDetailsService {

	@Autowired
	private GuestHealthDetailRepository guestHealthDetailRepository;

	@Override
	public boolean addGuestHealthDetailService(GuestHealthDetailsDao guestHealthDetailsDao, String dateTimeFormat) {

		try {
			GuestHealthDetail guestHealthDetail = new GuestHealthDetail();

			ModelMapper modelMapper = new ModelMapper();

			guestHealthDetail = modelMapper.map(guestHealthDetailsDao, GuestHealthDetail.class);

			guestHealthDetailRepository.save(guestHealthDetail);
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}

		return false;
	}

}
