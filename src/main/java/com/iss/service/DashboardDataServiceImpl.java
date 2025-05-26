package com.iss.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iss.dao.DashboardDataDao;
import com.iss.repository.GuestHealthAnuraDetailRepository;
import com.iss.repository.GuestHealthBinahDetailRepository;
import com.iss.repository.GuestRepository;
import com.iss.repository.LoginUserRespository;

@Service
public class DashboardDataServiceImpl implements DashboardDataService {

	@Autowired
	private LoginUserRespository loginUserRespository;

	@Autowired
	private GuestRepository guestRepository;

	@Autowired
	private GuestHealthBinahDetailRepository guestBinahRepo;

	@Autowired
	private GuestHealthAnuraDetailRepository guestAnuraRepo;
	
	private static final Logger logger = LoggerFactory.getLogger(DashboardDataServiceImpl.class);

	@SuppressWarnings("removal")
	@Override
	public DashboardDataDao getDashBoardDataService() {

		DashboardDataDao dao = new DashboardDataDao();

		Long totalUserCount = new Long(0);
		Long totalGuestCount = new Long(0);
		Long totalAnuraCount = new Long(0);
		Long totalBinahCount = new Long(0);

		totalUserCount = loginUserRespository.count();
		totalGuestCount = guestRepository.count();
		totalAnuraCount = guestAnuraRepo.count();
		totalBinahCount = guestBinahRepo.count();

		dao.setTotalUserCount(totalUserCount.toString());
		dao.setTotalGuestCount(totalGuestCount.toString());
		dao.setTotalAnuraScanCount(totalAnuraCount.toString());
		dao.setTotalBinahScanCount(totalBinahCount.toString());

		dao.setMsg("Sending the Dashboard Data...");
		dao.setSuccess(Boolean.toString(true));
		
		logger.info("While getting onboarding data - {}",dao.getMsg());

		return dao;
	}

}
