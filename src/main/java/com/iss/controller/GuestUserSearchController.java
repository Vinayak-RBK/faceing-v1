package com.iss.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iss.dao.GuestInfoListDao;
import com.iss.service.GuestUserSearchService;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
public class GuestUserSearchController {

	private static final Logger logger = LoggerFactory.getLogger(GuestUserSearchController.class);

	@Autowired
	private GuestUserSearchService guestUserSearchService;

	@PostMapping("/listOfGuest/{userId}")
	public ResponseEntity<Map<String, Object>> searchGuset(@PathVariable String userId) {
		GuestInfoListDao guestListRespDao = new GuestInfoListDao();
		Map<String, Object> respObj = new LinkedHashMap<String, Object>();

		try {
			guestListRespDao = guestUserSearchService.findAllGuest(userId);

			if (guestListRespDao.isSuccess()) {
				
				respObj.put("msg", guestListRespDao.getMsg());
				respObj.put("GuestList", guestListRespDao.getGuestListDao());
				respObj.put("success", guestListRespDao.isSuccess());
				
				logger.info("Searching guests for user :{} - {}", userId, guestListRespDao.getMsg());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.OK);
			} else {
				
				respObj.put("msg", guestListRespDao.getMsg());
				respObj.put("success", guestListRespDao.isSuccess());
				
				logger.info("Searching guests for user :{} - {}", userId, guestListRespDao.getMsg());
				return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			
			respObj.put("msg", guestListRespDao.getMsg());
			respObj.put("success", guestListRespDao.isSuccess());
			
			logger.info("Searching guests for user :{} - {}", userId, guestListRespDao.getMsg(), e);
			return new ResponseEntity<Map<String, Object>>(respObj, HttpStatus.BAD_REQUEST);
		}
	}

//	@PostMapping("/listOfGuest/{userId}")
//	public ResponseEntity<List<GuestDao>> searchGuest(@PathVariable String userId) {
//	    List<GuestDao> guestList = guestUserSearchService.findAllGuest(userId);
//	    System.out.println("List is : " + guestList);
//	    return new ResponseEntity<>(guestList, HttpStatus.OK);
//	}

}
