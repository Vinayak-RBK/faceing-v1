package com.iss.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iss.dao.GuestHealthAnuraDetailsDao;
import com.iss.entity.GuestHealthAnuraDetail;
import com.iss.repository.GuestHealthAnuraDetailRepository;

@Service
public class GuestHealthDetailsServiceImpl implements GuestHealthDetailsService {

	@Autowired
	private GuestHealthAnuraDetailRepository guestHealthDetailRepository;

	@Override
	public boolean addGuestHealthDetailService(GuestHealthAnuraDetailsDao guestHealthDetailsDao, String dateTimeFormat) {

		try {
			GuestHealthAnuraDetail guestHealthDetail = new GuestHealthAnuraDetail();

			ModelMapper modelMapper = new ModelMapper();

			guestHealthDetail = modelMapper.map(guestHealthDetailsDao, GuestHealthAnuraDetail.class);

			guestHealthDetailRepository.save(guestHealthDetail);
			return true;
		} catch (Exception e) {
			System.out.println(e);
		}

		return false;
	}

}
