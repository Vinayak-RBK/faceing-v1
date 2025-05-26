package com.iss.service;

import org.springframework.stereotype.Service;

import com.iss.dao.DashboardDataDao;

@Service
public interface DashboardDataService {

	public DashboardDataDao getDashBoardDataService();

}
