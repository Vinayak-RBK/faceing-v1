package com.iss.service;

import org.springframework.stereotype.Service;

import com.iss.dao.Response;

@Service
public interface UserSystemIPAddressService {
	
	public void saveOrUpdateUserSystemIpAdressService(String ipaddress, String dateTimeFormat) throws Exception;
	
	public Response checkReceivedSiteIsBlocked(String ipAddress);

}
